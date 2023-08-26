package com.example.bespringgroovy.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * BaseException Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{
  private HttpStatus httpStatus;

  public BaseException(HttpStatus httpStatus, String message, Throwable e) {
    super(message, e);
    this.httpStatus = httpStatus;
  }
  public BaseException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
