package com.hutech.tests3.Services;

import com.hutech.tests3.Entities.Product;
import com.hutech.tests3.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public Product getProduct(String id) {
        return productRepository.findById(id).get();
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> getProductsByQuery(String q) {
        return productRepository.queryProduct(q);
    }
    public long getMaxPrice(){
        return productRepository.getMaxPrice();
    }
    public long getMinPrice(){
        return productRepository.getMinPrice();
    }
    public List<String> getListCPU(){
        return productRepository.getAllCPU();
    }
    public List<String> getListGPU(){
        return productRepository.getAllGPU();
    }
    public List<Product> getProducts(Long maxPrice,Long minPrice,List<String> CPUs,List<String> GPUs) {
        List<Product> listResult = productRepository.findAll().stream().filter(
                p->(maxPrice==null||p.getPrice()<=maxPrice)
        ).filter(
                p->(minPrice==null||p.getPrice()>=minPrice)
        ).filter(
                p->(CPUs==null||CPUs.contains(p.getCPU()))
        ).filter(
                p->(GPUs==null||GPUs.contains(p.getGPU()))
        ).toList();
        return listResult;
    }


}