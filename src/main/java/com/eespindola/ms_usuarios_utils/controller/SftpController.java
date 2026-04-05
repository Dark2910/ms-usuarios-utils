package com.eespindola.ms_usuarios_utils.controller;

import com.eespindola.ms_usuarios_utils.service.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sftp")
public class SftpController {

  private final SftpService sftpService;

  @Autowired
  public SftpController(
          SftpService sftpServiceImpl
  ){
    this.sftpService = sftpServiceImpl;
  }

  @GetMapping("/share-file")
  public ResponseEntity<Void> getFile(){

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
