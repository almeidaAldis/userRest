package com.aldis.userRest.repository.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.aldis.userRest.constants.ParamConstant;
import com.aldis.userRest.entity.Param;
import com.aldis.userRest.entity.Token;
import com.aldis.userRest.entity.User;
import com.aldis.userRest.helper.security.UtilHelper;
import com.aldis.userRest.repository.TokenRepository;
import com.aldis.userRest.repository.UserRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import javax.naming.AuthenticationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;


class UserServiceTest {
    @Mock
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private ParamService paramService;;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    final String TOKEN = "token";
    final String USERNAME = "admin";
    final String PASSWORD = "admin";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**
     * Method under test: {@link UserService#login(String, String)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        // Arrange
        User user = new User();
        user.setCreatedAt(LocalDate.of(1996, 1, 1).atStartOfDay());
        user.setId(1L);
        user.setUserName(USERNAME);
        String passwordEncryp = UtilHelper.encryptPassword(PASSWORD);
        user.setPassword(passwordEncryp);

        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));
        Param param = new Param();
        param.setValue("100");

        Param paramKey = new Param();
        paramKey.setValue("8Ea6vY7HyX29qAiQ/ACBtDu8n6cRul3rePtC8qtkvK4=");

        Mockito.when(paramService.getFindByName(ParamConstant.TOKEN_EXPIRATION_TIME_MINUTES)).thenReturn(Optional.of(param));
        Mockito.when(paramService.getFindByName(ParamConstant.ENCRYPTION_KEY)).thenReturn(Optional.of(paramKey));

        ResponseEntity<Map<String, String>> response =  userService.login(USERNAME, PASSWORD);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Method under test: {@link UserService#login(String, String)}
     */
    @Test
    void testLoginUNAUTHORIZED() throws AuthenticationException {
        // Arrange
        User user = new User();
        user.setCreatedAt(LocalDate.of(1996, 1, 1).atStartOfDay());
        user.setId(1L);
        user.setUserName(USERNAME);
        user.setPassword(PASSWORD);//le coloco password sin cifrado para que no sean iguales

        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));
        Param param = new Param();
        param.setValue("100");

        Param paramKey = new Param();
        paramKey.setValue("8Ea6vY7HyX29qAiQ/ACBtDu8n6cRul3rePtC8qtkvK4=");

        Mockito.when(paramService.getFindByName(ParamConstant.TOKEN_EXPIRATION_TIME_MINUTES)).thenReturn(Optional.of(param));
        Mockito.when(paramService.getFindByName(ParamConstant.ENCRYPTION_KEY)).thenReturn(Optional.of(paramKey));

        assertThrows(AuthenticationException.class, () -> {
            userService.login(USERNAME, PASSWORD);
        });
    }


/**
 * Method under test: {@link UserService#verifyToken(String)}
 */
    @Test
    void verifyToken() throws AuthenticationException {
        Token token = new Token();
        token.setToken(TOKEN);
        token.setDueDate(LocalDate.now().plusDays(3).atStartOfDay());
        Mockito.when(tokenService.findByToken(Mockito.anyString())).thenReturn(token);

        ResponseEntity<Map<String, String>> response =  userService.verifyToken(TOKEN);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * Method under test: {@link UserService#verifyToken(String)}
     */
    @Test
    void verifyTokenUNAUTHORIZED() throws AuthenticationException {
        Token token = new Token();
        token.setToken(TOKEN);
        token.setDueDate(LocalDate.now().plusDays(-3).atStartOfDay());
        Mockito.when(tokenService.findByToken(Mockito.anyString())).thenReturn(token);
       assertThrows(AuthenticationException.class, () -> {
            userService.verifyToken(TOKEN);
        });
    }
}