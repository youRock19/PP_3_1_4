package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService service;
    
    @GetMapping
    public String getAllUser(Model model) {
        model.addAttribute("userlist", service.allUsers());
        return "users";
    }
    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", service.getRoles());
        return "new";
    }
    
    @PostMapping
    public String createUser(@ModelAttribute("newUser") User user) {
        service.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }
    
    @GetMapping("/edit/{id}")
    public String updateUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", service.readUser(id));
        model.addAttribute("roles", service.getRoles());
        return "edit";
    }
    
    @PostMapping("{id}")
    public String update(@ModelAttribute("user") User user) {
        service.saveUser(user);
        return "redirect:/admin";
    }
}
