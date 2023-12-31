package com.example.bespringgroovy.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * BaseAuditing Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditing<U> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @LastModifiedBy
  @Column(name = "updated_by")
  protected U updatedBy;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  protected U createdBy;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  protected Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  protected Date updatedAt;
}
