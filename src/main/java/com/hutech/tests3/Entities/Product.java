package com.hutech.tests3.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String product_id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int rOM;
    private int rAM;
    private String gPU;
    private boolean enabled;
    private String brand;
    private String cPU;
    @ManyToOne
    @JoinColumn( name= "cate_id")
    private Category category;

}