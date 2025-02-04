package com.animalia.spring.seguridad.JWT.model;


import com.animalia.spring.entidades.UsuarioDTO;
import com.animalia.spring.entidades.Usuarios.TipoUsuario;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UsuarioDTO{

	private String token;
	
	@Builder(builderMethodName="jwtUserResponseBuilder")
	public JwtUserResponse(String username, TipoUsuario roles, String token) {
		super(username, roles);
		this.token = token;
	}
}
