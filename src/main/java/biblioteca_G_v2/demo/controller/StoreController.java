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
        if (product.getId() == null) {
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
                product.setCategory(productDetails.getCategory());
                product.setStock(productDetails.getStock());
                product.setImageUrl(productDetails.getImageUrl());
                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(updatedProduct);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    // Endpoint para borrar productos (FALTABA ESTE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build(); // Devuelve 200 OK si se borró
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no existía
        }
    }
}
