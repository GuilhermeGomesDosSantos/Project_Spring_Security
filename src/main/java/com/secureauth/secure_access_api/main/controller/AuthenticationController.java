package com.secureauth.secure_access_api.main.controller;

import com.secureauth.secure_access_api.main.domain.user.DTOauthentication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    AuthenticationManager manager;
    @PostMapping
    public ResponseEntity authenticationUser(@RequestBody @Valid DTOauthentication dtOauthentication) {
        var token = new UsernamePasswordAuthenticationToken(dtOauthentication.login(), dtOauthentication.password());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
