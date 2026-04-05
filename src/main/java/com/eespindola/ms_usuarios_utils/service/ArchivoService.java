package com.eespindola.ms_usuarios_utils.service;

import com.eespindola.ms_usuarios_utils.model.Result;

public interface ArchivoService {

  Result<Void> crearArchivo();
//  Result<Void> crearArchivo(String fileName, String localPath);
//  Result<Void> crearArchivo(InputStream inputStream, String filename, String localPath);

}
