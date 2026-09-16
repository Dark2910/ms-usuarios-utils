package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.model.UsuarioExportDto;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class AbstractDocumentService implements ArchivoService {

  protected static final Logger LOG = LoggerFactory.getLogger(AbstractDocumentService.class);

  protected final UsuarioDao usuarioDaoJdbc;
  protected final AppProperties properties;

  protected AbstractDocumentService(UsuarioDao usuarioDaoJdbc, AppProperties properties) {
    this.usuarioDaoJdbc = usuarioDaoJdbc;
    this.properties = properties;
  }

  protected List<UsuarioExportDto> consultarUsuariosExport() {
    return usuarioDaoJdbc.consultarUsuarios()
            .stream()
            .map(UsuarioExportDto::fromUsuario)
            .toList();
  }

  protected File createFile(String pattern) {
    String fileName = String.format(pattern, getFechaHora("yyyy-MM-dd"));
    File file = new File(Paths.get(properties.localPath(), fileName).toString());
    ensureDirectoryExists(file.getParentFile());
    return file;
  }

  protected String getFechaHora(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.now().format(formatter);
  }

  private void ensureDirectoryExists(File directory) {
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  @Override
  public abstract Result<Void> crearArchivo();

}
