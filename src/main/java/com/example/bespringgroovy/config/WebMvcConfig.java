package com.example.bespringgroovy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Autowired
  private CorsProperties corsProperties;
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
      .allowedMethods(corsProperties.getAllowedMethods().toArray(new String[0]))
      .allowedHeaders("*")
      .allowCredentials(true);
//      .maxAge(MAX_AGE_SECS);
  }
}
