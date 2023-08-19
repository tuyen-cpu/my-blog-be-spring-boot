package com.example.bespringboot.exception;

import org.springframework.http.HttpStatus;

/**
 * ResourceNotFoundException Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public class ResourceNotFoundException extends BaseException {
  public ResourceNotFoundException(String message, Throwable e) {
    super(HttpStatus.NOT_FOUND, message, e);
  }

  public ResourceNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
