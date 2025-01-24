package com.example.tiendaonline.entidades;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @Column(nullable = false)
	@NotEmpty(message = "El campo no puede estar vacío")
    private String nombre;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String apellidos;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String email;

    @Column(name="tipo_usuario")
    private String tipoUsuario;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String dni;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String direccion;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    @NotNull
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    @NotEmpty(message = "El campo no puede estar vacío")
    private String password;

    @Column(name="cuenta_operativa",nullable = false)
    private boolean cuentaOperativa;

	public Usuario(long id, @NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty String email,
			@NotEmpty String tipoUsuario, @NotEmpty String dni, @NotEmpty String direccion, @NotEmpty String telefono,
			@NotNull LocalDate fechaNacimiento, @NotEmpty String password, boolean cuentaOperativa) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.password = password;
		this.cuentaOperativa = cuentaOperativa;
	}

	public Usuario(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty String email,
			@NotEmpty String tipoUsuario, @NotEmpty String dni, @NotEmpty String direccion, @NotEmpty String telefono,
			@NotNull LocalDate fechaNacimiento, @NotEmpty String password, boolean cuentaOperativa) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.password = password;
		this.cuentaOperativa = cuentaOperativa;
	}

	public Usuario() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCuentaOperativa() {
		return cuentaOperativa;
	}

	public void setCuentaOperativa(boolean cuentaOperativa) {
		this.cuentaOperativa = cuentaOperativa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, cuentaOperativa, direccion, dni, email, fechaNacimiento, id, nombre, password,
				telefono, tipoUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellidos, other.apellidos) && cuentaOperativa == other.cuentaOperativa
				&& Objects.equals(direccion, other.direccion) && Objects.equals(dni, other.dni)
				&& Objects.equals(email, other.email) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& id == other.id && Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
				&& Objects.equals(telefono, other.telefono) && Objects.equals(tipoUsuario, other.tipoUsuario);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", tipoUsuario=" + tipoUsuario + ", dni=" + dni + ", direccion=" + direccion + ", telefono="
				+ telefono + ", fechaNacimiento=" + fechaNacimiento + ", password=" + password + ", cuentaOperativa="
				+ cuentaOperativa + "]";
	}
    
    
    
	
	
    
    
    
}
