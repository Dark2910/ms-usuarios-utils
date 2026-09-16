package com.eespindola.ms_usuarios_utils.configuration;

import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "utils")
@Validated
public record AppProperties(

        @NotBlank(message = "Patrón notepad requerido")
        @Pattern(regexp = ".*%s.*", message = "Debe contener %s para la fecha")
        String localNotepadNamePattern,

        @NotBlank(message = "Patrón excel requerido")
        @Pattern(regexp = ".*%s.*", message = "Debe contener %s para la fecha")
        String localExcelNamePattern,

        @NotBlank(message = "Path local requerido")
        String localPath,

        @NotBlank(message = "Patrón notepad remoto requerido")
        @Pattern(regexp = ".*%s.*", message = "Debe contener %s para la fecha")
        String remoteNotepadNamePattern,

        @NotBlank(message = "Patrón excel remoto requerido")
        @Pattern(regexp = ".*%s.*", message = "Debe contener %s para la fecha")
        String remoteExcelNamePattern,

        @NotBlank(message = "Path remoto requerido")
        String remotePath,

        @NotBlank(message = "Host SFTP requerido")
        String sftpHost,

        @Min(value = 1, message = "Puerto debe ser mayor a 0")
        @Max(value = 65535, message = "Puerto debe ser menor a 65536")
        Integer sftpPort,

        @NotBlank(message = "Usuario SFTP requerido")
        String sftpUser,

        @NotBlank(message = "Contraseña SFTP requerida")
        String sftpPassword,

        @NotBlank(message = "URL del servicio de usuarios requerida")
        @Pattern(regexp = "^https?://.*", message = "Debe ser una URL válida (http/https)")
        String gestorUsuarioServiceUrl
) {
}
