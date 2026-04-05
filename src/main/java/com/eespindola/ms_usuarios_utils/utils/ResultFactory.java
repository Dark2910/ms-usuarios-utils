package com.eespindola.ms_usuarios_utils.utils;

import com.eespindola.ms_usuarios_utils.model.Result;

import java.util.List;

public class ResultFactory {

  public static final String SUCCESS_MSG = "Operacion exitosa.";
  public static final String ERROR_MSG = "Incidencia en la operacion.";

  private static final Result<Void> DEFAULT_SUCCESS = Result.<Void>builder()
          .success(true)
          .message(SUCCESS_MSG)
          .build();

  private static <T> Result.ResultBuilder<T> prepare(Boolean isSuccess, String message) {
    return Result.<T>builder()
            .success(isSuccess)
            .message(message);
  }

  public static Result<Void> success() {
    return DEFAULT_SUCCESS;
  }

  public static Result<Void> success(String message) {
    return ResultFactory.<Void>prepare(true, message).build();
  }

  public static <T> Result<T> success(String message, T data) {
    return ResultFactory.<T>prepare(true, message).data(data).build();
  }

  public static <T> Result<T> success(String message, List<T> dataList) {
    return ResultFactory.<T>prepare(true, message).dataList(dataList).build();
  }

  public static Result<Void> error(String message) {
    return ResultFactory.<Void>prepare(false, message).build();
  }

  public static Result<Void> error(String message, Integer errorCode, List<String> errorDescription) {
    return ResultFactory.<Void>prepare(false, message).errorCode(errorCode).errorDescription(errorDescription)
            .build();
  }

  private ResultFactory() {
    throw new IllegalArgumentException("Util class");
  }

}