package com.example.bespringgroovy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Role Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "role")
public class Role extends BaseAuditing<String>{
  private String name;

  @ManyToMany(mappedBy = "roles")
  Set<User> users = new HashSet<>();

  @Override
  public String toString() {
    return "Role={id=" + id + " " +
      "name=" + name;
  }
}
