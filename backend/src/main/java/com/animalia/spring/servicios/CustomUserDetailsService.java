package com.animalia.spring.servicios;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.animalia.spring.entidades.Usuarios;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioServicio userEntityServicio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return Optional.ofNullable(userEntityServicio.obtenerUsuarioPorCorreo(username))
				.map(user -> (UserDetails) user)
				.orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
	}
	
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		Usuarios user = userEntityServicio.obtenerUsuarioPorId(id);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario con ID: " + id + " no encontrado");
		}
		return (UserDetails) user;
	}

}