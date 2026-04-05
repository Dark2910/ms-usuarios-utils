package com.eespindola.ms_usuarios_utils.service.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.exception.impl.Error500;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.model.Usuario;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import com.eespindola.ms_usuarios_utils.utils.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Service("notepadServieImpl")
public class NotepadServieImpl implements ArchivoService {
  private static final Logger LOG = LoggerFactory.getLogger(NotepadServieImpl.class);

  private final UsuarioDao usuarioDaoJdbc;
  private final AppProperties properties;

  @Autowired
  public NotepadServieImpl(
          @Qualifier("usuariosDaoJdbcImpl") UsuarioDao usuarioDaoJdbcImpl,
          AppProperties appProperties
  ) {
    this.usuarioDaoJdbc = usuarioDaoJdbcImpl;
    this.properties = appProperties;
  }

  @Override
  public Result<Void> crearArchivo() {
    LOG.info("Creando Archivo");
    try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(createFile()), StandardCharsets.UTF_8))) {
      for (Usuario usuario : consultaUsuarios()) {
        String text = joinData(usuario);
        writer.write(text);
        writer.newLine();
      }
      return ResultFactory.success();
    }
    catch (Exception e) {
      LOG.info("Incidencia al crear archivo");
      throw new Error500(List.of("Incidencia al crearArchivo - notepad"), e);
    }
  }

  private File createFile(){
    String fileName = String.format(properties.localNotepadNamePattern(), getFechaHora("yyyy-MM-dd"));
    return new File(Paths.get(properties.localPath(), fileName).toString());
  }

  private String getFechaHora(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.now().format(formatter);
  }

  private List<Usuario> consultaUsuarios() {
    return usuarioDaoJdbc.consultarUsuarios();
  }

  private static String joinData(Usuario usuario) {
    StringJoiner joiner = new StringJoiner(" | ");
    joiner.add(usuario.getFolioId());
    joiner.add(usuario.getNombre());
    joiner.add(usuario.getApellidoPaterno());
    joiner.add(usuario.getApellidoMaterno());
    joiner.add(usuario.getFechaNacimiento());
    joiner.add(usuario.getUsername());
    joiner.add(usuario.getEmail());
    joiner.add(usuario.getPassword());
    joiner.add(usuario.getStatus());
    return joiner.toString();
  }

}
