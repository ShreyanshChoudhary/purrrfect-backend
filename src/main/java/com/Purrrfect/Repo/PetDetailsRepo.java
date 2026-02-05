package com.Purrrfect.Repo;

import com.Purrrfect.Model.PetDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDetailsRepo extends JpaRepository<PetDetails, Long> {
    // Add custom query methods if needed
}