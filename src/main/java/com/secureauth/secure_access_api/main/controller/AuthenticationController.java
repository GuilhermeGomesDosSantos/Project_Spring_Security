package com.secureauth.secure_access_api.main.controller;

import com.secureauth.secure_access_api.main.domain.user.DTOauthentication;
import com.secureauth.secure_access_api.main.domain.user.User;
import com.secureauth.secure_access_api.main.infra.security.DTOtokenJWT;
import com.secureauth.secure_access_api.main.infra.security.tokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    AuthenticationManager manager;

    @Autowired
    tokenService tokenService;

    @PostMapping
    public ResponseEntity authenticationUser(@RequestBody @Valid DTOauthentication dtOauthentication) {
        var token = new UsernamePasswordAuthenticationToken(dtOauthentication.login(), dtOauthentication.password());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DTOtokenJWT(tokenJWT));
    }
}
