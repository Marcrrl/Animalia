package com.animalia.spring.controladores;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.UsuarioDTO;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.converter.UserDtoConverter;
import com.animalia.spring.seguridad.JWT.JwtProvider;
import com.animalia.spring.seguridad.JWT.model.JwtUserResponse;
import com.animalia.spring.seguridad.JWT.model.LoginRequest;
import com.animalia.spring.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autentificacion", description = "Validacion usuarios")
public class LoginController {

    @Autowired
    private UsuarioServicio usuariosServicio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final UserDtoConverter converter;

    @PostMapping("/login")
    @Operation(summary = "Inicio Sesion")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuarios user = (Usuarios) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserEntityAndTokenToJwtUserResponse(user, jwtToken));
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(Usuarios user, String jwtToken) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .token(jwtToken)
                .build();
    }

    @GetMapping("/user/me")
    @Operation(summary = "Comprobacion Usuario", description = "Devuelve el usurio que esta iniciada la sesion")
    public UsuarioDTO me(@AuthenticationPrincipal Usuarios user) {
        return converter.convertUserEntityToGetUserDto(user);
    }

    @PostMapping("/add")
    @Operation(summary = "Registro", description = "Creacion de usuarios")
    public ResponseEntity<Void> addUser(@RequestBody Usuarios entity) {
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
        usuariosServicio.guardarUsuario(entity);
        return ResponseEntity.ok().build();
    }

}
