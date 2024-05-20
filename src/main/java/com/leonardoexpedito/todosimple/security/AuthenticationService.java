package com.leonardoexpedito.todosimple.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.leonardoexpedito.todosimple.models.User;
import com.leonardoexpedito.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationService implements UserDetailsService {

    @Value("${auth.jwt.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String getTokenJWT(AuthenticationDTO authenticationDTO) {
        User user = userRepository.findByUsername(authenticationDTO.username());
        return generateTokenJWT(user);
    }

    public String generateTokenJWT(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("todolist-api")
                    .withSubject(user.getUsername())
                    .withClaim("user_id", user.getId())
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(tokenExpireGenerate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw  new RuntimeException("Erro ao gerar token de acesso! " + exception.getMessage());
        }
    }

    private Instant tokenExpireGenerate() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String tokenValidation(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return  JWT.require(algorithm)
                    .withIssuer("todolist-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }


    }
}
