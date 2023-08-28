package com.example.bespringgroovy.testBean;

import com.example.bespringgroovy.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
//@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
//  @Autowired
//  private UserDetailsService userDetailsService;
//  @Autowired
//  private JwtUtils jwtUtils;
  @Override
  public void run(String... args) throws Exception {
//   User user = (User) userDetailsService.loadUserByUsername("tuyen");
//    ResponseCookie responseCookie =  jwtUtils.generateJwtCookie(user);
//    jwtUtils.getUserNameFromJwtToken(responseCookie.getValue());
//
//    boolean valida = jwtUtils.validateJwtToken(responseCookie.getValue());
  }
}
