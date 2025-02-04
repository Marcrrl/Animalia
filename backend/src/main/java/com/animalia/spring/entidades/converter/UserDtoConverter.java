package com.animalia.spring.entidades.converter;

import org.springframework.stereotype.Component;

import com.animalia.spring.entidades.UsuarioDTO;
import com.animalia.spring.entidades.Usuarios;

@Component
public class UserDtoConverter {

	public UsuarioDTO convertUserEntityToGetUserDto(Usuarios user) {
		UsuarioDTO newUser = new UsuarioDTO();
		newUser.setEmail(user.getEmail());
		newUser.setTipo_usuario(user.getTipo_usuario());
		return newUser;
	}
}
