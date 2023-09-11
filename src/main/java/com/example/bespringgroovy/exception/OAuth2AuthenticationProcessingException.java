package com.example.bespringgroovy.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
  public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
    super(msg, t);
  }

  public OAuth2AuthenticationProcessingException(String msg) {
    super(msg);
  }
}