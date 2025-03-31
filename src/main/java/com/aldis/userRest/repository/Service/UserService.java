package com.aldis.userRest.repository.Service;


import com.aldis.userRest.entity.Token;
import com.aldis.userRest.entity.User;
import com.aldis.userRest.helper.security.UtilHelper;
import com.aldis.userRest.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private UserRepository repository;
    private ParamService paramService;
    private TokenService tokenService;

    public UserService(UserRepository userRepository, ParamService paramService,TokenService tokenService) {
        this.paramService = paramService;
        this.repository = userRepository;
        this.tokenService = tokenService;
    }

    public List<User> getAll() {
        return repository.findAll().stream().toList();
    }

    public User findByUserName(String userName) {
        return repository.findByUserName(userName).orElseThrow(() -> new AuthenticationServiceException("Usuario no autorizado"));
    }


    public ResponseEntity<Map<String,String>> verifyToken(String token) {
        Token tokenS = tokenService.findByToken(token);
        if(tokenS.getDueDate().isBefore(LocalDateTime.now())) {
            throw new AuthenticationServiceException("Unauthorized user");
        }
        Map<String,String> response = new HashMap<>();
        response.put("valid","true");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> login(String userName, String password ) {
        User user = findByUserName(userName);
        if(!user.getPassword().equals(UtilHelper.encryptPassword(password))) {
            throw new AuthenticationServiceException("Unauthorized user");
        }
        final LocalDateTime dueDate = UtilHelper.generateDueDateLoginToken(paramService);
        final String tokenString = UtilHelper.createLoginToken(user.getId(),dueDate,paramService);

        Token token = new Token();
        token.setUser_id(user.getId());
        token.setToken(tokenString);
        token.setDueDate(dueDate);
        tokenService.create(token);

        Map<String,String> response = new HashMap<>();
        response.put("token",tokenString);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
