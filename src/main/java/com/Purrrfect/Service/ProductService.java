package com.Purrrfect.Service;

import com.Purrrfect.Model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addProduct(String name, Double price, String description, MultipartFile image) throws IOException;

    Product addPetProduct(
            String name, Double price, String description, MultipartFile image,
            String breed, String age, String gender, String weight, String color,
            Boolean vaccinated, Boolean dewormed, String diet
    ) throws IOException;

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product updateProduct(Long id, String name, Double price, String description, MultipartFile image) throws IOException;

    Product updatePetProduct(
            Long id, String name, Double price, String description, MultipartFile image,
            String breed, String age, String gender, String weight, String color,
            Boolean vaccinated, Boolean dewormed, String diet
    ) throws IOException;

    boolean deleteProductById(Long id);
}