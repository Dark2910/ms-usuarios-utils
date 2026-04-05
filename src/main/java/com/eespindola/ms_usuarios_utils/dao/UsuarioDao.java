package com.eespindola.ms_usuarios_utils.dao;

import com.eespindola.ms_usuarios_utils.model.Usuario;

import java.util.List;

public interface UsuarioDao {

  List<Usuario> consultarUsuarios();

}
