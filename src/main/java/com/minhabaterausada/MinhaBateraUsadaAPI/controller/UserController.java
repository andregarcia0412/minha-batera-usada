package com.minhabaterausada.MinhaBateraUsadaAPI.controller;

import com.minhabaterausada.MinhaBateraUsadaAPI.connection.DatabaseConnection;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.LoginRequest;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.User;
import com.minhabaterausada.MinhaBateraUsadaAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAll(){
        return userService.listAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @PostMapping("")
    public String addUser(@RequestBody User body){
        return userService.addUser(body);
    }

    @PostMapping("/login")
    public boolean verifyLogin(@RequestBody LoginRequest loginRequest){
        return userService.verifyLogin(loginRequest);
    }

}
