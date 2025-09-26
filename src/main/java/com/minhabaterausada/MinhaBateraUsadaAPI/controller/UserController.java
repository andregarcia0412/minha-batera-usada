package com.minhabaterausada.MinhaBateraUsadaAPI.controller;

import com.minhabaterausada.MinhaBateraUsadaAPI.connection.DatabaseConnection;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.LoginRequest;
import com.minhabaterausada.MinhaBateraUsadaAPI.domain.User;
import com.minhabaterausada.MinhaBateraUsadaAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAll(@RequestHeader(value = "X-API-PASSWORD", required = false) String password){
        return userService.listAllUsers(password);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id, @RequestHeader(value = "X-API-PASSWORD", required = false) String password){
        return userService.getUser(id, password);
    }

    @PostMapping("")
    public boolean addUser(@RequestBody User body, @RequestHeader(value = "X-API-PASSWORD", required = false) String password){
        return userService.addUser(body, password);
    }

    @PostMapping("/login")
    public boolean verifyLogin(@RequestBody LoginRequest loginRequest, @RequestHeader(value = "X-API-PASSWORD", required = false) String password){
        return userService.verifyLogin(loginRequest, password);
    }

}
