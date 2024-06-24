package com.hutech.tests3.Repositories;

import com.hutech.tests3.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("select p from Product p where concat(p.name,p.brand,p.gPU,p.cPU,p.rAM,p.rOM) like %?1%")
    List<Product> queryProduct(String q);
    @Query("select max(p.price) from Product p")
    long getMaxPrice();
    @Query("select min(p.price) from Product p")
    long getMinPrice();
    @Query("select p.gPU from Product p group by p.gPU")
    List<String> getAllGPU();
    @Query("select p.cPU from Product p group by p.cPU")
    List<String> getAllCPU();
}
