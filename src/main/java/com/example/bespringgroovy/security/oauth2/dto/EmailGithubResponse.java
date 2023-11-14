package com.example.bespringgroovy.security.oauth2.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Getter
@Setter
public class EmailGithubResponse {
  private String email;
  private boolean primary;
  private boolean verified;
  private String visibility;
}
