package com.eespindola.ms_usuarios_utils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private T data;

  @JsonProperty("dataList")
  private List<T> dataList;

  @JsonProperty("errorCode")
  private Integer errorCode;

  @JsonProperty("errorDescription")
  private List<String> errorDescription;

    // Patron Builder
    public Result(ResultBuilder<T> builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.data = builder.data;
        this.dataList = builder.dataList;
        this.errorCode = builder.errorCode;
        this.errorDescription = builder.errorDescription;
    }

  public static <T> ResultBuilder<T> builder(){
    return new ResultBuilder<>();
  }

  public static class ResultBuilder<T>{
    private Boolean success;
    private String message;
    private T data;
    private List<T> dataList;
    private Integer errorCode;
    private List<String> errorDescription;


    public ResultBuilder<T> success(Boolean success) {
        this.success = success;
      return this;
    }

    public ResultBuilder<T> message(String message){
      this.message = message;
      return this;
    }

    public ResultBuilder<T> data(T data){
      this.data = data;
      return this;
    }

    public ResultBuilder<T> dataList(List<T> dataList){
      this.dataList = dataList;
      return this;
    }

    public ResultBuilder<T> errorCode(Integer errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public ResultBuilder<T> errorDescription(List<String> errorDescription){
        this.errorDescription = errorDescription;
        return this;
    }

    public Result<T> build(){
        return new Result<>(this);
    }

  }

}
