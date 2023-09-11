package com.example.bespringgroovy.security.services;

import com.example.bespringgroovy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@AllArgsConstructor
public class UserDetailsCustom implements UserDetails, OAuth2User {
  @Getter
  private Long id;
  private String username;
  @Getter
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;
  private static List<SimpleGrantedAuthority> getAuthorities(User user) {
    return user.getRoles().stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .toList();
  }

  public static UserDetailsCustom build(User user) {
    return new UserDetailsCustom(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), getAuthorities(user), null);
  }
  public static UserDetailsCustom build(User user,  Map<String, Object> attributes) {
    return new UserDetailsCustom(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), getAuthorities(user), attributes);
  }
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }
}
