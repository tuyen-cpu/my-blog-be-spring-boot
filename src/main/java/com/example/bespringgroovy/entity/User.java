package com.example.bespringgroovy.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "user")
public class User extends BaseAuditing<String> {
  private String userName;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private UserStatus status;
  @ManyToMany
    (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "user_has_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<Role> roles = new HashSet<>();

  private void addRole(Role role) {
    this.roles.add(role);
  }
  private void addRoles(Set<Role> roles) {
    this.roles.addAll(roles);
  }
  @Override
  public String toString() {
    return "User={id=" + id + " " +
      "userName=" + userName;
  }
  public enum UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED

  }
}
