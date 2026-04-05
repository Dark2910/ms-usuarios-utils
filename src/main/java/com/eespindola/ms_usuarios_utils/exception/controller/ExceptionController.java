package com.eespindola.ms_usuarios_utils.exception.controller;

import com.eespindola.ms_usuarios_utils.exception.impl.GenericRuntimeException;
import com.eespindola.ms_usuarios_utils.model.Result;
import com.eespindola.ms_usuarios_utils.utils.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

  @ExceptionHandler(GenericRuntimeException.class)
  public ResponseEntity<Result<Void>> genericExceptionController(GenericRuntimeException e) {
    return ResponseEntity.status(e.getErrorEnum().getStatus())
            .body(ResultFactory.error(e.getErrorEnum().getMessage(), e.getErrorEnum().getErrorCode(),
                                      e.getDescription()));
  }

}
