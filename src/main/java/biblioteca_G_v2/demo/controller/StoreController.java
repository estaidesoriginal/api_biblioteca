package biblioteca_G_v2.demo.controller;

import biblioteca_G_v2.demo.model.Product;
import biblioteca_G_v2.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class StoreController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(UUID.randomUUID().toString());
        }
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setPrice(productDetails.getPrice());

                product.setCategories(productDetails.getCategories());
                
                product.setStock(productDetails.getStock());
                product.setImageUrl(productDetails.getImageUrl());
                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(updatedProduct);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        return productRepository.findById(id)
            .map(product -> {
                productRepository.delete(product);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

