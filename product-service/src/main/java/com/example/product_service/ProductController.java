package com.example.product_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.http.HttpStatus;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

private final List<Map<String, Object>> products = new ArrayList<>(List.of(
        Map.of("id", 1, "name", "Mouse", "price", 150000),
        Map.of("id", 2, "name", "Keyboard", "price", 250000),
        Map.of("id", 3, "name", "Monitor", "price", 2500000),
        Map.of("id", 4, "name", "Headphone", "price", 450000),
        Map.of("id", 5, "name", "Webcam", "price", 600000),
        Map.of("id", 6, "name", "USB Hub", "price", 200000),
        Map.of("id", 7, "name", "Laptop Stand", "price", 300000),
        Map.of("id", 8, "name", "SSD 512GB", "price", 1200000),
        Map.of("id", 9, "name", "RAM 16GB", "price", 1100000),
        Map.of("id", 10, "name", "Mouse Pad", "price", 80000)
));
    @GetMapping
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //tìm theo tên
    @GetMapping("/search")
    public List<Map<String,Object>>searchByName(@RequestParam (defaultValue = "")String name){
        String keyword=name.trim().toLowerCase();
        return products.stream()
        .filter(p->p.get("name").toString().toLowerCase().contains(keyword))
        .toList();
    }

    //thêm sản phẩm:
    @PostMapping
    public ResponseEntity<Map<String,Object>>
    addProduct(@RequestBody Map<String,Object>body){
        int nextId=products.stream().mapToInt(p->(int)
    p.get("id")).max().orElse(0)+1;
    Map<String,Object>newProduct=Map.of(
        "id",nextId,
        "name",body.get("name"),
        "price",body.get("price"));
        products.add(newProduct);
        return ResponseEntity.status(201).body(newProduct);
    }

    //xoa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id){
        boolean removed=products.removeIf(p->p.get("id").equals(id));
        return removed ? ResponseEntity.noContent().build():
        ResponseEntity.notFound().build();
    }

}