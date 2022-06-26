package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
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
    public String getAllUser(Principal principal, Model model) {
        model.addAttribute("userlist", service.allUsers());
        model.addAttribute("thisuser", service.findUser(principal.getName()));
        return "users";
    }   
}
