package com.eespindola.ms_usuarios_utils.dao;

public interface SftpDao {

  void enviarArchivo(String localFilenamePath, String remoteFilenamePath);

  void descargarArchivo(String remoteFilenamePath, String localFilenamePath);

}
