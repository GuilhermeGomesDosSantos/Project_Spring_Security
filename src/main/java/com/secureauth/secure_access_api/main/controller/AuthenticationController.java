package com.secureauth.secure_access_api.main.controller;

import com.secureauth.secure_access_api.main.domain.user.*;
import com.secureauth.secure_access_api.main.infra.security.DTOtokenJWT;
import com.secureauth.secure_access_api.main.infra.security.tokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    AuthenticationManager manager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    tokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity authenticationUser(@RequestBody @Valid DTOauthentication dtOauthentication) {
        var token = new UsernamePasswordAuthenticationToken(dtOauthentication.login(), dtOauthentication.password());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DTOtokenJWT(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid DTOregister dtOregister, UriComponentsBuilder uriComponentsBuilder){
        if (userRepository.findByLogin(dtOregister.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dtOregister.password());

        User newUser = new User(dtOregister.login(), encryptedPassword);

        userRepository.save(newUser);

        var uri = uriComponentsBuilder.path("/register/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(uri).body(new DTOuserRegister(newUser.getLogin()));
    }
}
