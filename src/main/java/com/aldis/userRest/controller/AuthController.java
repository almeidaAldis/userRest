package com.aldis.userRest.controller;


import com.aldis.userRest.repository.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@Tag(name = "auth", description = "API para gestión de productos")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> login(
            @RequestHeader("userName") final String userName,
            @RequestHeader("password") final String password ) {
        return userService.login(userName, password);
    }

    @PostMapping("/verify-access-token")
    public ResponseEntity<Map<String,String>> verifyAccessToken(
            @RequestHeader("token") final String token
            )  {
        return  userService.verifyToken(token);
    }
}
