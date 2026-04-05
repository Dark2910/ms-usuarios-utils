package com.eespindola.ms_usuarios_utils.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "utils")
public record AppProperties(
        String localNotepadNamePattern,
        String localExcelNamePattern,
        String localPath,

        String remoteNotepadNamePattern,
        String remoteExcelNamePattern,
        String remotePath,

        String sftpHost,
        String sftpPort,
        String sftpUser,
        String sftpPassword,

        String gestorUsuarioServiceUrl
) {
}
