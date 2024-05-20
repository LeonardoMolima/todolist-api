package com.leonardoexpedito.todosimple.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthenticationDTO authenticationDTO) {
        var usuarioAutenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        authenticationManager.authenticate(usuarioAutenticationToken);
        return authenticationService.getTokenJWT(authenticationDTO);
    }
}
