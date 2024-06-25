package com.hutech.tests3.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_menu;
    private String name;
    private String url;
    private boolean enabled;
    @ManyToOne
    private Menu parent;
    @JsonBackReference
    @OneToMany(mappedBy = "parent")
    private List<Menu> children;
}