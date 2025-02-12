package com.animalia.spring.entidades.converter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.animalia.spring.entidades.Empresas;
import com.animalia.spring.entidades.UsuarioJWTDTO;
import com.animalia.spring.entidades.UsuarioRegistroDTO;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.servicios.EmpresasServicio;

@Component
public class UserDtoConverter {

	@Autowired
	private EmpresasServicio empresasServicio;

	public UsuarioJWTDTO convertUserEntityToGetUserJWTDto(Usuarios user) {
		UsuarioJWTDTO newUser = new UsuarioJWTDTO();
		newUser.setId(user.getId());
		newUser.setTipo_usuario(user.getTipo_usuario());
		return newUser;
	}

	public UsuarioRegistroDTO convertUserEntityToGetUserRegistroDto(Usuarios user) {
		UsuarioRegistroDTO newUser = new UsuarioRegistroDTO();
		newUser.setNombre(user.getNombre());
		newUser.setApellido(user.getApellido());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setTelefono(user.getTelefono());
		newUser.setDireccion(user.getDireccion());
		return newUser;
	}

	public Usuarios convertUserRegistroDtoToUserEntity(UsuarioRegistroDTO userDto) {
		Usuarios user = new Usuarios();
		user.setNombre(userDto.getNombre());
		user.setApellido(userDto.getApellido());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setTelefono(userDto.getTelefono());
		user.setDireccion(userDto.getDireccion());
		user.setUrl_foto_perfil("iconoBase.png");
		user.setTipo_usuario(userDto.getTipo_usuario());
		user.setFecha_registro(LocalDate.now());
		if (userDto.getIdEmpresa() != null) {
			Empresas e = empresasServicio.obtenerEmpresaPorId(userDto.getIdEmpresa());
			user.setEmpresa(e);
		}else{
			user.setEmpresa(null);
		}
		user.setCantidad_rescates(0);
		return user;
	}
}
