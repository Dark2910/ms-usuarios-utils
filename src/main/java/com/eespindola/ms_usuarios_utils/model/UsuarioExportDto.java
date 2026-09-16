package com.eespindola.ms_usuarios_utils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
