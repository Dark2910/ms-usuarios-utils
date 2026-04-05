package com.eespindola.ms_usuarios_utils.exception.impl;

import com.eespindola.ms_usuarios_utils.exception.enums.ErrorEnum;

import java.util.List;

public class Error500 extends GenericRuntimeException {
  // Internal Server Error
  public Error500(List<String> description) {
    super(description, ErrorEnum.ERROR_500);
  }

  public Error500(List<String> description, Throwable e) {
    super(description, ErrorEnum.ERROR_500, e);
  }

}
