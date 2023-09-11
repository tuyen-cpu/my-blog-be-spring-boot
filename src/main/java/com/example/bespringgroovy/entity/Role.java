package com.example.bespringgroovy.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/**
 * Role Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false, exclude = "users")
@Data
@Entity
@Table(name = "role")
public class Role extends BaseAuditing<String>{
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  Set<User> users = new HashSet<>();

  @Override
  public String toString() {
    return "Role={id=" + id + " " +
      "name=" + name;
  }
}
