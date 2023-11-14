package com.example.bespringgroovy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

  public List<String> getAllowedMethods() {
    String methods = env.getProperty("web.cors.allowed-methods", "");
    return Arrays.asList(methods.split(","));
  }
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
