package com.Purrrfect.Service;

import com.Purrrfect.Model.Product;
import com.Purrrfect.Model.PetDetails;
import com.Purrrfect.Repo.PetDetailsRepo;
import com.Purrrfect.Repo.ProductRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private PetDetailsRepo petDetailsRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public Product addProduct(String name, Double price, String description, MultipartFile image) throws IOException {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = cloudinaryService.uploadFile(image);
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product addPetProduct(
            String name, Double price, String description, MultipartFile image,
            String breed, String age, String gender, String weight, String color,
            Boolean vaccinated, Boolean dewormed, String diet) throws IOException {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = cloudinaryService.uploadFile(image);
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);

        PetDetails petDetails = new PetDetails();
        petDetails.setBreed(breed);
        petDetails.setAge(age);
        petDetails.setGender(gender);
        petDetails.setWeight(weight);
        petDetails.setColor(color);
        petDetails.setVaccinated(vaccinated != null ? vaccinated : false);
        petDetails.setDewormed(dewormed != null ? dewormed : false);
        petDetails.setDiet(diet);

        // Set up bidirectional relationship
        petDetails.setProduct(product);
        product.setPetDetails(petDetails);

        productRepository.save(product);
        petDetailsRepository.save(petDetails);

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, String name, Double price, String description, MultipartFile image) throws IOException {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);

            if (image != null && !image.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(image);
                product.setImageUrl(imageUrl);
            }

            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public Product updatePetProduct(
            Long id, String name, Double price, String description, MultipartFile image,
            String breed, String age, String gender, String weight, String color,
            Boolean vaccinated, Boolean dewormed, String diet) throws IOException {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);

            if (image != null && !image.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(image);
                product.setImageUrl(imageUrl);
            }

            // Get or create pet details
            PetDetails petDetails = product.getPetDetails();
            if (petDetails == null) {
                petDetails = new PetDetails();
                petDetails.setProduct(product);
                product.setPetDetails(petDetails);
            }

            // Update pet details
            if (breed != null) petDetails.setBreed(breed);
            if (age != null) petDetails.setAge(age);
            if (gender != null) petDetails.setGender(gender);
            if (weight != null) petDetails.setWeight(weight);
            if (color != null) petDetails.setColor(color);
            if (vaccinated != null) petDetails.setVaccinated(vaccinated);
            if (dewormed != null) petDetails.setDewormed(dewormed);
            if (diet != null) petDetails.setDiet(diet);

            productRepository.save(product);
            petDetailsRepository.save(petDetails);

            return product;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
