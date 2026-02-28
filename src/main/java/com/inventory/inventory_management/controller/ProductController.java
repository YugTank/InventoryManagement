package com.inventory.inventory_management.controller;

import com.inventory.inventory_management.dto.request.ProductRequest;
import com.inventory.inventory_management.dto.request.ProductUpdateRequst;
import com.inventory.inventory_management.dto.response.ProductResponse;
import com.inventory.inventory_management.entity.Product;
import com.inventory.inventory_management.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        Product newProduct = productService.createProduct(request);
        ProductResponse productResponse = ProductResponse.fromEntity(newProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return ProductResponse.fromEntity(product);
    }
    @GetMapping("/sku/{sku}")
    public ProductResponse getProductsBySku(@PathVariable String sku){
        Product product = productService.getProductBySku(sku);
        return ProductResponse.fromEntity(product);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProductByName(@RequestParam String name){
        return productService.searchProduct(name)
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        return productService.searchProductByCategory(categoryId)
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<ProductResponse> getLowStockProducts(){
        return productService.getLowStockProducts()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(@PathVariable Long id,@Valid @RequestBody ProductUpdateRequst request){
        return ProductResponse.fromEntity(productService.updateProduct(id,request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
