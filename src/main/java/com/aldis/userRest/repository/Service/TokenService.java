package com.aldis.userRest.repository.Service;

import com.aldis.userRest.entity.Token;
import com.aldis.userRest.repository.TokenRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Service
public class TokenService {

    private TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    public Token findByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() -> new AuthenticationServiceException("Usuario no autorizado"));
    }
}
