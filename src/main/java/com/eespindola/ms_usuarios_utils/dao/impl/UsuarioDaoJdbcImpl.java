package com.eespindola.ms_usuarios_utils.dao.impl;

import com.eespindola.ms_usuarios_utils.configuration.AppProperties;
import com.eespindola.ms_usuarios_utils.dao.UsuarioDao;
import com.eespindola.ms_usuarios_utils.exception.impl.Error503;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Repository("usuariosDaoJdbcImpl")
public class UsuarioDaoJdbcImpl implements UsuarioDao {
  private static final Logger LOG = LoggerFactory.getLogger(UsuarioDaoJdbcImpl.class);

  private final WebClient webClient;
  private final AppProperties properties;

  @Autowired
  public UsuarioDaoJdbcImpl(
          WebClient webClientBean,
          AppProperties appProperties
  ) {
    this.webClient = webClientBean;
    this.properties = appProperties;
  }

  @Override
  public List<Usuario> consultarUsuarios() {
    try {
      Result<Usuario> response = webClient.get()
              .uri(properties.gestorUsuarioServiceUrl())
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<Result<Usuario>>() {
              })
              .block();
      return (response != null && response.getDataList() != null)
              ? response.getDataList()
              : Collections.emptyList();
    }
    catch (Exception e) {
      LOG.error("Incidencia al consultar el microservicio de usuarios en: {}", properties.gestorUsuarioServiceUrl());
      throw new Error503(List.of("Incidencia al consultar registros"), e);
    }
  }

}
