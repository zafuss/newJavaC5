package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.Category;
import com.hutech.tests3.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    private String getAllCategories(Model model) {
        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("categories", categoryList);
        return "Layout/Category/index";
    }

    @GetMapping("/new")
    private String getNewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "Layout/Category/new";
    }
    @PostMapping("/new")
    private String saveNewCategory(Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

}