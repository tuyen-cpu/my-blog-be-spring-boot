package com.example.bespringgroovy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
