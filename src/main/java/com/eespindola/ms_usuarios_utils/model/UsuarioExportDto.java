package com.eespindola.ms_usuarios_utils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioExportDto {

	@JsonProperty("idUsuario")
	private Integer idUsuario;

	@JsonProperty("folioId")
	private String folioId;

	@JsonProperty("nombre")
	private String nombre;

	@JsonProperty("apellidoPaterno")
	private String apellidoPaterno;

	@JsonProperty("apellidoMaterno")
	private String apellidoMaterno;

	@JsonProperty("fechaNacimiento")
	private String fechaNacimiento;

	@JsonProperty("username")
	private String username;

	@JsonProperty("email")
	private String email;

	@JsonProperty("status")
	private String status;

	public UsuarioExportDto() {
		super();
	}

	public UsuarioExportDto(UsuarioBuilder builder) {
	    this.idUsuario = builder.idUsuario;
	    this.folioId = builder.folioId;
	    this.nombre = builder.nombre;
	    this.apellidoPaterno = builder.apellidoPaterno;
	    this.apellidoMaterno = builder.apellidoMaterno;
	    this.fechaNacimiento = builder.fechaNacimiento;
	    this.username = builder.username;
	    this.email = builder.email;
	    this.status = builder.status;
	}

	public static UsuarioBuilder builder() {
		return new UsuarioBuilder();
	}

	public static UsuarioExportDto fromUsuario(Usuario usuario) {
		return UsuarioExportDto.builder()
				.idUsuario(usuario.getIdUsuario())
				.folioId(usuario.getFolioId())
				.nombre(usuario.getNombre())
				.apellidoPaterno(usuario.getApellidoPaterno())
				.apellidoMaterno(usuario.getApellidoMaterno())
				.fechaNacimiento(usuario.getFechaNacimiento())
				.username(usuario.getUsername())
				.email(usuario.getEmail())
				.status(usuario.getStatus())
				.build();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getFolioId() {
		return folioId;
	}

	public void setFolioId(String folioId) {
		this.folioId = folioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static class UsuarioBuilder {

		private Integer idUsuario;
		private String folioId;
		private String nombre;
		private String apellidoPaterno;
		private String apellidoMaterno;
		private String fechaNacimiento;
		private String username;
		private String email;
		private String status;

		public UsuarioBuilder idUsuario(Integer idUsuario) {
			this.idUsuario = idUsuario;
			return this;
		}

		public UsuarioBuilder folioId(String folioId) {
			this.folioId = folioId;
			return this;
		}

		public UsuarioBuilder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public UsuarioBuilder apellidoPaterno(String apellidoPaterno) {
			this.apellidoPaterno = apellidoPaterno;
			return this;
		}

		public UsuarioBuilder apellidoMaterno(String apellidoMaterno) {
			this.apellidoMaterno = apellidoMaterno;
			return this;
		}

		public UsuarioBuilder fechaNacimiento(String fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
			return this;
		}

		public UsuarioBuilder username(String username) {
			this.username = username;
			return this;
		}

		public UsuarioBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UsuarioBuilder status(String status) {
			this.status = status;
			return this;
		}

		public UsuarioExportDto build() {
			return new UsuarioExportDto(this);
		}
	}
}
