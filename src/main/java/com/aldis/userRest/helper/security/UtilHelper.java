package com.aldis.userRest.helper.security;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import com.aldis.userRest.constants.ParamConstant;
import com.aldis.userRest.entity.Param;
import com.aldis.userRest.repository.Service.ParamService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class UtilHelper {

    public static String createLoginToken(
            final Long userId,
            final LocalDateTime dueDate,
            ParamService paramService
    ){
        final Date date = new Date(System.currentTimeMillis());

        final Date expirationDate = Date.from(dueDate.atZone(ZoneId.systemDefault()).toInstant());

        Optional<Param> paramOptional = paramService.getFindByName(ParamConstant.ENCRYPTION_KEY);

        final JwtBuilder jwtBuilder = Jwts.builder().
                signWith(SignatureAlgorithm.HS256, paramOptional.get().getValue()).
                setId(userId.toString())
                .setIssuedAt(date).
                setExpiration(expirationDate);

        return jwtBuilder.compact();
    }

    public static String encryptPassword(String password) {
        if (password == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (Exception ex) {
            return null;
        }
    }

    public static LocalDateTime generateDueDateLoginToken(ParamService paramService) {
        Optional<Param> paramOptional = paramService.getFindByName(ParamConstant.TOKEN_EXPIRATION_TIME_MINUTES);
        if(paramOptional.isEmpty()){
            new Exception("Parametro no encontrado");
        }

        Integer time = Integer.parseInt(paramOptional.get().getValue());
        return LocalDateTime
                .now()
                .plusMinutes(time);

    }
}
