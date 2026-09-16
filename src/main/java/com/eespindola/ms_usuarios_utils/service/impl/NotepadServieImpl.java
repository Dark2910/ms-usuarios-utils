package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.exception.impl.Error500;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.model.UsuarioExportDto;
import com.eespindola.ms_usuarios_utils.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

@Service("notepadServieImpl")
public class NotepadServieImpl extends AbstractDocumentService {

  @Autowired
  public NotepadServieImpl(
          @Qualifier("usuariosDaoJdbcImpl") UsuarioDao usuarioDaoJdbcImpl,
          AppProperties appProperties
  ) {
    super(usuarioDaoJdbcImpl, appProperties);
  }

  @Override
  public Result<Void> crearArchivo() {
    LOG.info("Creando archivo Notepad");
    try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(createFile(properties.localNotepadNamePattern())), StandardCharsets.UTF_8))) {
      for (UsuarioExportDto usuario : consultarUsuariosExport()) {
        String text = joinData(usuario);
        writer.write(text);
        writer.newLine();
      }
      LOG.info("Archivo Notepad creado exitosamente");
      return ResultFactory.success();
    }
    catch (Exception e) {
      LOG.error("Error al crear archivo Notepad", e);
      throw new Error500(List.of("Incidencia al crearArchivo - notepad"), e);
    }
  }

  private static String joinData(UsuarioExportDto usuario) {
    StringJoiner joiner = new StringJoiner(" | ");
    joiner.add(usuario.getFolioId());
    joiner.add(usuario.getNombre());
    joiner.add(usuario.getApellidoPaterno());
    joiner.add(usuario.getApellidoMaterno());
    joiner.add(usuario.getFechaNacimiento());
    joiner.add(usuario.getUsername());
    joiner.add(usuario.getEmail());
    joiner.add(usuario.getStatus());
    return joiner.toString();
  }

}
