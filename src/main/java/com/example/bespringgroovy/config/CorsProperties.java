package com.example.bespringgroovy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CorsProperties Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix="web.cors")
public class CorsProperties {
  private List<String> allowedOrigins;

  private List<String> allowedMethods;
}
