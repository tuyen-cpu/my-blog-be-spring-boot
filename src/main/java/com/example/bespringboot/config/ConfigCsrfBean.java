package com.example.bespringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * ConfigCsrfBean Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Component
public class ConfigCsrfBean {

  private final Environment env;

  @Autowired
  public ConfigCsrfBean(Environment env) {
    this.env = env;
  }
  public List<String> getAllowedOrigins() {
    String origins = env.getProperty("web.cors.allowed-origins", "");
    return Arrays.asList(origins.split(","));
  }

  public List<String> getAllowedMethod() {
    String methods = env.getProperty("web.cors.allowed-methods", "");
    return Arrays.asList(methods.split(","));
  }
}
