package com.hutech.tests3.Controllers.APIs;


import com.hutech.tests3.Entities.Menu;
import com.hutech.tests3.Services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class RestMenu {
    @Autowired
    private MenuService menuService;
    @GetMapping("")
    public List<Menu> getMenus() {
        return menuService.getMenus();
    }
}
