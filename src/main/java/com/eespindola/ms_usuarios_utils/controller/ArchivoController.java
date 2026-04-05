package com.eespindola.ms_usuarios_utils.controller;

import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archivo")
public class ArchivoController {

  private final ArchivoService notepadService;
  private final ArchivoService excelService;

  @Autowired
  public ArchivoController(
          @Qualifier("notepadServieImpl") ArchivoService notepad,
          @Qualifier("excelServiceImpl") ArchivoService excel
  ){
    this.notepadService = notepad;
    this.excelService = excel;
  }

  @GetMapping("/notepad")
  public ResponseEntity<Result<Void>> getNotepad(){
    Result<Void> result = notepadService.crearArchivo();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/excel")
  public ResponseEntity<Result<Void>> getExcel(){
    Result<Void> result = excelService.crearArchivo();
    return ResponseEntity.ok(result);
  }

}
