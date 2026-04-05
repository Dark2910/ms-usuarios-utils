package com.eespindola.ms_usuarios_utils.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "utils")
public record AppProperties(
        String localNotepadName,
        String localExcelName,
        String localPath,

        String remoteNotepadName,
        String remoteExcelName,
        String remotePath,

        String gestorUsuarioServiceUrl
){}
