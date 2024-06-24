package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.Product;
import com.hutech.tests3.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String Search(@RequestParam(name = "q",required = false) String query, Model model){
        if(query == null){
            query=" ";
        }
        List<Product> products = productService.getProductsByQuery(query);
        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "Layout/Product/search";
    }
}
