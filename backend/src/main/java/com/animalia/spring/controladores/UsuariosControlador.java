package com.animalia.spring.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animalia.spring.entidades.UsuarioDTO;
import com.animalia.spring.entidades.Usuarios;
import com.animalia.spring.entidades.Usuarios.TipoUsuario;
import com.animalia.spring.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuariosControlador {

    @Autowired
    private UsuarioServicio usuariosServicio;

    @GetMapping("/todos")
    @Operation(summary = "Mostrar todos los usuarios del sistema", description = "Devuelve una lista con todos los usuarios del sistema")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        List<UsuarioDTO> usuarios = usuariosServicio.obtenerUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/tipo/{tipoUsuario}")
    @Operation(summary = "Mostrar todos los usuarios del sistema por tipo", description = "Devuelve una lista con todos los usuarios del sistema por tipo")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios(@PathVariable TipoUsuario tipoUsuario) {
        List<UsuarioDTO> usuarios = usuariosServicio.obtenerUsuariosPorTipo(tipoUsuario);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping
    @Operation(summary = "Mostrar todos los usuarios del sistema", description = "Devuelve una lista con todos los usuarios del sistema")
    public ResponseEntity<Page<UsuarioDTO>> obtenerUsuariosPagebale(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {

        Page<UsuarioDTO> usuarios = usuariosServicio.obtenerUsuariosPaginacion(pageable);

        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario", description = "Buscar un usuario a partir de su id")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable long id) {
        UsuarioDTO u = usuariosServicio.obtenerUsuarioDTOPorId(id);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }

    @GetMapping("/imagen/{nombreImagen}")
    @Operation(summary = "Buscar una imagen", description = "Buscar una imagen de usuario a partir de su nombre")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreImagen) {
        try {
            Resource resource = new ClassPathResource("static/img/" + nombreImagen);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema poniendo el id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable long id) {
        usuariosServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Editar usuario", description = "Editar usuario en el sistema.")
    @PutMapping
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestBody Usuarios usuario) {
        UsuarioDTO usuarioActualizado = usuariosServicio.actualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }
}
