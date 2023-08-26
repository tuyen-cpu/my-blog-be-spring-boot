package com.example.bespringgroovy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Comment Entity Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "comment")
public class Comment extends BaseAuditing<String> {
  private String content;
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  @ManyToOne(targetEntity = Post.class)
  @JoinColumn(name = "post_id", nullable = false)
  @JsonIgnore
  private Post post;

  @Override
  public String toString() {
    return "Category={id=" + id + " " +
      "content=" + content;
  }
}
