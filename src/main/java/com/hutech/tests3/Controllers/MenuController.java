package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.Menu;
import com.hutech.tests3.Services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("")
    public String index(Model model){
        List<Menu> menus = menuService.getMenus();
        model.addAttribute("menus", menus);
        return "Layout/Menu/index";
    }
    @GetMapping("/new")
    public String newMenu(Model model){
        Menu menu = new Menu();
        List<Menu> menus = menuService.getMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("menu", menu);
        return "Layout/Menu/new";
    }
    @PostMapping("new")
    public String newMenu(Menu menu){
        menuService.saveMenu(menu);
        return "redirect:/menus";
    }
}