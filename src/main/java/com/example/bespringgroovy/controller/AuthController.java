package com.example.bespringgroovy.controller;

import com.example.bespringgroovy.dto.request.LoginRequest;
import com.example.bespringgroovy.dto.response.UserResponse;
import com.example.bespringgroovy.security.jwt.JwtUtils;
import com.example.bespringgroovy.security.services.UserDetailsCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtils jwtUtils;
  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    List<String> roles = getRoles(userDetails);
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
      .body(new UserResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }
  private List<String> getRoles(UserDetails userDetails){
    return userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
  }

}
