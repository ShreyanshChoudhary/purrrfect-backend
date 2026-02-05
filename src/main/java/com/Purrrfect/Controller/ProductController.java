package com.Purrrfect.Controller;

import com.Purrrfect.Model.Product;
import com.Purrrfect.Model.PetDetails;
import com.Purrrfect.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // ✅ Add Product with Image Upload
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        try {
            logger.info("Adding new product: {}", name);
            Product product = productService.addProduct(name, price, description, image);
            logger.info("Product added successfully: {}", product.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IOException e) {
            logger.error("Error uploading product image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // ✅ Add Pet Product with Image Upload and Pet Details
    @PostMapping("/add/pet")
    public ResponseEntity<?> addPetProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) String age,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "weight", required = false) String weight,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "vaccinated", required = false, defaultValue = "false") Boolean vaccinated,
            @RequestParam(value = "dewormed", required = false, defaultValue = "false") Boolean dewormed,
            @RequestParam(value = "diet", required = false) String diet) {

        try {
            logger.info("Adding new pet product: {}", name);
            Product product = productService.addPetProduct(
                    name, price, description, image,
                    breed, age, gender, weight, color,
                    vaccinated, dewormed, diet
            );
            logger.info("Pet product added successfully: {}", product.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IOException e) {
            logger.error("Error uploading pet product image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // ✅ Get All Products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Fetching all products...");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            logger.warn("No products found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        }
        logger.info("Products retrieved successfully.");
        return ResponseEntity.ok(products);
    }

    // ✅ Get Product by ID
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            logger.error("Product not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
        }
        logger.info("Product retrieved successfully: {}", product.get().getName());
        return ResponseEntity.ok(product.get());
    }

    // ✅ Update Product (with optional image upload)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            logger.info("Updating product with ID: {}", id);
            Product updatedProduct = productService.updateProduct(id, name, price, description, image);
            logger.info("Product updated successfully: {}", updatedProduct.getName());
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException e) {
            logger.error("Error updating product image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // ✅ Update Pet Product (with optional image upload and pet details)
    @PutMapping("/update/pet/{id}")
    public ResponseEntity<?> updatePetProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) String age,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "weight", required = false) String weight,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "vaccinated", required = false) Boolean vaccinated,
            @RequestParam(value = "dewormed", required = false) Boolean dewormed,
            @RequestParam(value = "diet", required = false) String diet) {

        try {
            logger.info("Updating pet product with ID: {}", id);
            Product updatedProduct = productService.updatePetProduct(
                    id, name, price, description, image,
                    breed, age, gender, weight, color,
                    vaccinated, dewormed, diet
            );
            logger.info("Pet product updated successfully: {}", updatedProduct.getName());
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException e) {
            logger.error("Error updating pet product image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    // ✅ Delete Product by ID
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        boolean isDeleted = productService.deleteProductById(id);
        if (!isDeleted) {
            logger.error("Product not found or could not be deleted: ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found or could not be deleted: ID " + id);
        }
        logger.info("Product deleted successfully: ID {}", id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}