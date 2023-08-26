package com.example.bespringgroovy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Category Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "category")
public class Category extends BaseAuditing<String> {
  private String title;
  private String content;
  private String slug;

  @ManyToMany(mappedBy = "categories")
  Set<Post> posts = new HashSet<>();

  @Override
  public String toString() {
    return "Category={id=" + id + " " +
      "title=" + title;
  }
}
