package ru.kata.spring.boot_security.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class RestController {
@Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.allUsers();
    }
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id){
        return userService.findUserById(id);
    }
   
    
    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }
    
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userService.saveUser(user);
    }
    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
    }
}
