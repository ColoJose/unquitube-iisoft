package com.iisoft.unquitube.backend.controller;

import com.iisoft.unquitube.backend.dto.UserDTO;
import com.iisoft.unquitube.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.iisoft.unquitube.backend.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/init")
    public String init(){
        return "<h1>Hola pepe</h1>";
    }

    @PutMapping("/user")
    public boolean createUser(@RequestBody @Valid UserDTO userDTO){
        return userService.create(userDTO);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.get();
    }
}
