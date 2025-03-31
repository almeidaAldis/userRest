package com.aldis.userRest.controller;


import com.aldis.userRest.repository.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestController
@RequestMapping("auth")
@Tag(name = "auth", description = "API para gestión de user")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> login(
            @RequestHeader("userName")  String userName,
            @RequestHeader("password")  String password ) throws AuthenticationException {
        return userService.login(userName,password);
    }

    @PostMapping("/verify-access-token")
    public ResponseEntity<Map<String,String>> verifyAccessToken(
            @RequestHeader("token") final String token
            ) throws AuthenticationException {
        return  userService.verifyToken(token);
    }
}
