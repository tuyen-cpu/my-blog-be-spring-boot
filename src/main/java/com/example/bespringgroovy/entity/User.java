package com.example.bespringgroovy.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false, exclude = "roles")
@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseAuditing<String> {
  private String username;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private UserStatus status;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private AuthProvider provider;

  private String name;
  @ManyToMany
    (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<Role> roles = new HashSet<>();

  @Builder
  public User(String username,String name, String email, Set<Role> roles,
              String password, AuthProvider provider, UserStatus status ) {
    this.username = username;
    this.name = name;
    this.email = email;
    this.roles = roles;
    this.password = password;
    this.provider = provider;
    this.status = status;
  }
  @Override
  public String toString() {
    return "User{" +
      "id='" + id + '\'' +
      ", username='" + username + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
  public enum UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED

  }
}
