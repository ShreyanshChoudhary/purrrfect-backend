package com.Purrrfect.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pet_details")
@Data
public class PetDetails {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private String breed;

    @Column
    private String age;

    @Column
    private String gender;

    @Column
    private String weight;

    @Column
    private String color;

    @Column
    private Boolean vaccinated = false;

    @Column
    private Boolean dewormed = false;

    @Column
    private String diet;
}