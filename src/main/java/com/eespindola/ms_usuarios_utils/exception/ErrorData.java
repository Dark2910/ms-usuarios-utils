package com.eespindola.ms_usuarios_utils.exception;

import com.eespindola.ms_usuarios_utils.exception.enums.ErrorEnum;

import java.util.List;

public interface ErrorData {

  List<String> getDescription();
  ErrorEnum getErrorEnum();

}
