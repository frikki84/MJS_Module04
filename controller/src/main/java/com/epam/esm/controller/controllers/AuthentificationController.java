package com.epam.esm.controller.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.entity.AuthentificationRequestDto;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.service.mapper.impl.UserDtoMapperImpl;
import com.epam.esm.service.security.JwtTokenProvider;

@RestController
@RequestMapping("/v2/auth")
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDtoMapperImpl userDtoMapper;

    public AuthentificationController(AuthenticationManager authenticationManager, UserService userService,
            JwtTokenProvider jwtTokenProvider, UserDtoMapperImpl userDtoMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDtoMapper = userDtoMapper;
    }

    @PostMapping("/login")
    public ResponseEntity authentificatt(@RequestBody AuthentificationRequestDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));
        UserDto user = userService.findUserByName(dto.getName());
        String token = jwtTokenProvider.createToken(dto.getName(), "user");
        Map<Object, Object> response = new HashMap<>();
        response.put("name", dto.getName());
        response.put("token", token);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registaration(){

    }

}
