package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.exception.impl.Error500;
import com.eespindola.ms_usuarios_utils.exception.impl.GenericRuntimeException;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import com.eespindola.ms_usuarios_utils.service.SftpService;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("SftpServiceImpl")
public class SftpServiceImpl implements SftpService {
  private static final Logger LOG = LoggerFactory.getLogger(SftpServiceImpl.class);

  private final ArchivoService notepadService;
  private final ArchivoService excelService;
  private final AppProperties properties;

  private Session session;
  private ChannelSftp channelSftp;

  private boolean isConnected = false;

  @Autowired
  public SftpServiceImpl(
          @Qualifier("notepadServieImpl") ArchivoService notepadService,
          @Qualifier("excelServiceImpl") ArchivoService excelService,
          AppProperties appProperties
  ) {
    this.notepadService = notepadService;
    this.excelService = excelService;
    this.properties = appProperties;
  }

  @Override
  public void descargarNotepad() {
    String remoteFileName = getFileName(properties.remoteNotepadNamePattern());
    String remoteFilenamePath = properties.remotePath() + "/" + remoteFileName;

    String localFileName = getFileName(properties.localNotepadNamePattern());
    Path localFilenamePath = Paths.get(properties.localPath(), localFileName);

    descargarArchivo(remoteFilenamePath, localFilenamePath.toString());
  }

  @Override
  public void descargarExcel() {
    String remoteFileName = getFileName(properties.remoteExcelNamePattern());
    String remoteFilenamePath = properties.remotePath() + "/" + remoteFileName;

    String localFileName = getFileName(properties.localExcelNamePattern());
    Path localFilenamePath = Paths.get(properties.localPath(), localFileName);

    descargarArchivo(remoteFilenamePath, localFilenamePath.toString());
  }

  private String getFileName(String namePattern) {
    return String.format(namePattern, getFechaHora("yyyy-MM-dd"));
  }

  private String getFechaHora(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.now().format(formatter);
  }

  private Boolean getConnection() {
    if (!isConnected) {
      openConnection();
    }
    return isConnected;
  }

  private void openConnection() {
    try {
      JSch jSch = new JSch();
      session = jSch.getSession(properties.sftpUser(), properties.sftpHost(), Integer.parseInt(properties.sftpPort()));
      session.setConfig("StrictHostKeyChecking", "no");

      LOG.info("Iniciando sesion");
      session.setPassword(properties.sftpPassword());
      LOG.info("Conectando sesion");
      session.connect(15000);

      LOG.info("Abriendo canal");
      channelSftp = (ChannelSftp) session.openChannel("sftp");
      LOG.info("Conectando canal");
      channelSftp.connect(15000);

      LOG.info("Conexion establecida");
      this.isConnected = true;
    }
    catch (Throwable e) {
      LOG.info("Insidencia al establecer conexion");
      this.isConnected = false;
      throw new Error500(List.of("Insidencia al establecer conexion - SFTP"), e);
    }
  }

  private void closeConnection() throws GenericRuntimeException {
    try {
      if (channelSftp != null && channelSftp.isConnected()) {
        LOG.info("Cerrando canal");
        channelSftp.disconnect();
      }
      if (session != null && session.isConnected()) {
        LOG.info("Cerrando sesion");
        session.disconnect();
      }
    }
    catch (Exception e) {
      LOG.info("Insidencia al cerrar recursos - SFTP", e);
    }
    finally {
      this.isConnected = false;
      this.session = null;
      this.channelSftp = null;
    }
  }

  private void descargarArchivo(String remoteFilenamePath, String localFilenamePath) {
    try {
      if (getConnection()) {
        LOG.info("Descargando archivo");
        channelSftp.get(remoteFilenamePath, localFilenamePath);
        LOG.info("Descarga completada");
      }
    }
    catch (Throwable e) {
      LOG.info("Insidencia al descargar archivo");
      throw new Error500(List.of("Insidencia al descargar archivo"), e);
    }
    finally {
      closeConnection();
    }
  }

  @Override
  public void descargarDirectorio() {

  }

}
