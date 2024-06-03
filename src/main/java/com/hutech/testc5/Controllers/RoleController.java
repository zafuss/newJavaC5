package com.hutech.testc5.Controllers;

import com.hutech.testc5.Entities.Role;
import com.hutech.testc5.RequestEntities.RequestRole;
import com.hutech.testc5.Services.RoleService;
import com.hutech.testc5.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/new")
    public String newRole(Model model) {
        model.addAttribute("role", new RequestRole());
        return "Layout/Role/new";
    }
    @PostMapping("/save")
    public String saveRole(RequestRole requestRole) {
        roleService.createRole(requestRole);
        return "redirect:/roles";
    }
    @GetMapping("")
    public String index(Model model) {
        List<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("roles", listRoles);
        return "Layout/Role/index";
    }
}