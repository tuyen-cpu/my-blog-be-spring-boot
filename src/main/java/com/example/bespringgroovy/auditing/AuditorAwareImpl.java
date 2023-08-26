package com.example.bespringgroovy.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

/**
 * AuditorAwareImpl Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext()
      .getAuthentication();

    if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
      return Optional.empty();
    }
    return Optional.of(getUser());
  }

  private String getUser() {
    return "ANONYMOUS";
  }
}
