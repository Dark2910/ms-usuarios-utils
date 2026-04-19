package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.SftpDao;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import com.eespindola.ms_usuarios_utils.service.SftpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

@Service
public class SftpServiceImpl implements SftpService {

  private static final Logger LOG = LoggerFactory.getLogger(SftpServiceImpl.class);

  private final SftpDao sftpDao;
  private final ArchivoService notepadService;
  private final ArchivoService excelService;
  private final AppProperties properties;

  @Autowired
  public SftpServiceImpl(
          SftpDao sftpDao,
          @Qualifier("notepadServieImpl") ArchivoService notepadService,
          @Qualifier("excelServiceImpl") ArchivoService excelService,
          AppProperties appProperties
  ) {
    this.sftpDao = sftpDao;
    this.notepadService = notepadService;
    this.excelService = excelService;
    this.properties = appProperties;
  }

  @Override
  public void enviarNotepad() {
    notepadService.crearArchivo();
    procesarArchivo(properties.localNotepadNamePattern(), properties.remoteNotepadNamePattern(), sftpDao::enviarArchivo);
  }

  @Override
  public void enviarExcel() {
    excelService.crearArchivo();
    procesarArchivo(properties.localExcelNamePattern(), properties.remoteExcelNamePattern(), sftpDao::enviarArchivo);
  }

  @Override
  public void descargarNotepad() {
    procesarArchivo(properties.localNotepadNamePattern(), properties.remoteNotepadNamePattern(),
                    (local, remote) -> sftpDao.descargarArchivo(remote, local));
  }

  @Override
  public void descargarExcel() {
    procesarArchivo(properties.localExcelNamePattern(), properties.remoteExcelNamePattern(),
                    (local, remote) -> sftpDao.descargarArchivo(remote, local));
  }

  private void procesarArchivo(String localPattern, String remotePattern, BiConsumer<String, String> accion) {
    String localFileName = getFileName(localPattern);
    String localPath = Paths.get(properties.localPath(), localFileName).toString();

    String remoteFileName = getFileName(remotePattern);
    String remotePath = buildRemotePath(properties.remotePath(), remoteFileName);

    accion.accept(localPath, remotePath);
  }

  private String getFileName(String namePattern) {
    return String.format(namePattern, getFechaHora("yyyy-MM-dd"));
  }

  private String getFechaHora(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.now().format(formatter);
  }

  private String buildRemotePath(String path, String fileName) {
    String normalizedBase = path.replace("\\", "/");
    if (!normalizedBase.endsWith("/")) {
      normalizedBase += "/";
    }
    return normalizedBase + fileName;
  }

}
