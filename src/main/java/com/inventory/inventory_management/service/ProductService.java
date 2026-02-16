package com.inventory.inventory_management.service;

import com.inventory.inventory_management.dto.request.ProductRequest;
import com.inventory.inventory_management.dto.request.ProductUpdateRequst;
import com.inventory.inventory_management.entity.Category;
import com.inventory.inventory_management.entity.Product;
import com.inventory.inventory_management.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;

import java.util.*;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisService redisService;

    private static final String CACHE_KEY_PRODUCT="product:id:";
    private static final long CACHE_TTL=10;

    @Transactional
    public Product createProduct(ProductRequest request){
        log.info("Creating product "+request.getName());
        if(productRepository.existsBySku(request.getSku())){
            throw new IllegalArgumentException("Product with given SKU already exists");
        }

        Category category=categoryService.findById(request.getCategoryId());

        Product product=new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setCategory(category);
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity()!=null?request.getQuantity():0);
        product.setLowStockThreshold(request.getLowStockThreshold()!=null?request.getLowStockThreshold():10);

        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        Product[] products=redisService.get("product:all", Product[].class);
        if(products!=null){
            log.info("Getting all products from Cache");
            return Arrays.asList(products);
        }
        List<Product> allProducts=productRepository.findAll();
        redisService.set("product:all", allProducts.toArray(), CACHE_TTL);
        log.info("Getting all products from DB");
        return allProducts;

    }

    public Product getProductById(Long id){
        log.info("Getting product by {} ",id);

        Product product=redisService.get(CACHE_KEY_PRODUCT+id,Product.class);

        if(product!=null){
            log.info("Found product by {} from Redis cache ",id);
            return product;
        }
        log.info("Fetching product {} from DB",id);
        Product product1=productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Product with id "+id+" not found"));
        redisService.set(CACHE_KEY_PRODUCT+id,product1,CACHE_TTL);
        return product1;
    }

    public Product getProductBySku(String sku){
        log.info("Getting product by sku "+sku);
        return productRepository.findBySku(sku)
                .orElseThrow(()->new IllegalArgumentException("Product with id "+sku+" not found"));
    }

    public List<Product> searchProduct(String name){
        log.info("Searching product by name "+name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product>  searchProductByCategory(Long categoryId){
        log.info("Searching product by category "+categoryId);
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getLowStockProducts(){
        log.info("Getting low stock products");
        return productRepository.findLowStockProducts();
    }
    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequst request){
        log.info("Updating product "+request.getName());
        Product product=productRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Product with id "+id+" not found"));

        if(request.getName()!=null){
            product.setName(request.getName());
        }
        if(request.getDescription()!=null){
            product.setDescription(request.getDescription());
        }
        if(request.getPrice()!=null){
            product.setPrice(request.getPrice());
        }
        if(request.getLowStockThreshold()!=null){
            product.setLowStockThreshold(request.getLowStockThreshold());
        }
        if(request.getCategoryId()!=null){
            Category category=categoryService.findById(request.getCategoryId());
            product.setCategory(category);
        }
        log.info("Updated name: {}", product.getName());

        redisService.evict(CACHE_KEY_PRODUCT+product.getId());
        redisService.evict("product:all");
        log.info("Evicting product {} from Redis cache ",id);

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(Long id){
        log.info("Deleting product {}",id);
        redisService.evict(CACHE_KEY_PRODUCT+id);
        redisService.evict("product:all");
        productRepository.deleteById(id);
    }
}
