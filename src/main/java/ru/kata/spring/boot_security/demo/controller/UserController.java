package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/user")
    public String getUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findUser(principal.getName()));
        return "user";
    }

}
