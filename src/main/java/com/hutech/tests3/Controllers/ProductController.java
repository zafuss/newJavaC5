package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.Category;
import com.hutech.tests3.Entities.Product;
import com.hutech.tests3.Services.CategoryService;
import com.hutech.tests3.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String getAllProduct( Model model) {
        List<Product> products = productService.getProducts();;
        model.addAttribute("products", products);
        return "Layout/Product/index";
    }
    @GetMapping("/filter")
    public String Filter(@RequestParam(name = "max_price", required = false) long maxPriceInput,
                         @RequestParam(name = "min_price", required = false) long minPriceInput,
                         @RequestParam(name = "cpu", required = false) List<String> CPUs,
                         @RequestParam(name = "gpu", required = false) List<String> GPUs, Model model){
        long maxPrice = productService.getMaxPrice();
        long minPrice = productService.getMinPrice();
        List<String> allCPU = productService.getListCPU();
        List<String> allGPU = productService.getListGPU();
        model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("allCPU",allCPU);
        model.addAttribute("allGPU",allGPU);
        List<Product> products = productService.getProducts(maxPrice,minPrice,CPUs,GPUs);;
        model.addAttribute("products", products);
        return "Layout/Product/index";
    }
    @GetMapping("/new")
    public String newProduct(Model model) {
        Product product = new Product() ;
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "Layout/Product/new";
    }
    @PostMapping("/new")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

}