package com.example.bespringgroovy.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Getter
@Setter
public class LoginRequest {
  @NotBlank(message = "Username is required field")
  private String email;
  @NotBlank(message = "Password is required field")
  private String password;
}
