package com.example.bespringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Post Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "post")
public class Post extends BaseAuditing<String> {
  private String title;
  private String slug;
  private String content;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  @ManyToMany
    (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "post_has_category", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  Set<Category> categories = new HashSet<>();

  private void addCategory(Category category) {
    this.categories.add(category);
  }

  private void addCategories(Set<Category> categories) {
    this.categories.addAll(categories);
  }

  @Override
  public String toString() {
    return "Post={id=" + id + " " +
      "title=" + title;
  }
}
