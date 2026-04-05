package com.eespindola.ms_usuarios_utils.exception.impl;

import com.eespindola.ms_usuarios_utils.exception.enums.ErrorEnum;

import java.util.List;

public class Error503 extends GenericRuntimeException {
  // Service Unavailable
  public Error503(List<String> description) {
    super(description, ErrorEnum.ERROR_503);
  }

  public Error503(List<String> description, Throwable e) {
    super(description, ErrorEnum.ERROR_503, e);
  }

}
