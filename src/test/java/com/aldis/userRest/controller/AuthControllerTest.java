package com.aldis.userRest.controller;

import com.aldis.userRest.repository.Service.UserService;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

class AuthControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    AuthController authController;

    final String TOKEN = "token";
    final String USERNAME = "admin";
    final String PASSWORD = "admin";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link AuthController#login(String, String)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        Map<String,String> responseTest = new HashMap<>();
        responseTest.put("token",TOKEN);

        Mockito.when(userService.login(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ResponseEntity<>(responseTest, HttpStatus.OK));

        ResponseEntity<Map<String, String>> response =  authController.login(USERNAME, PASSWORD);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Method under test: {@link AuthController#verifyAccessToken(String)}
     */
    @Test
    void verifyAccessToken() throws AuthenticationException {

        Map<String,String> responseTest = new HashMap<>();
        responseTest.put("valid","true");

        Mockito.when(userService.verifyToken(Mockito.anyString()))
                .thenReturn(new ResponseEntity<>(responseTest, HttpStatus.OK));

        ResponseEntity<Map<String, String>> response =  authController.verifyAccessToken(TOKEN);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}