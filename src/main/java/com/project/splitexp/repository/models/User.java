package com.project.splitexp.repository.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "split_exp_user")
@Table(name = "split_exp_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
  @GenericGenerator(name = "pg-uuid", strategy = "uuid2", parameters = @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "com.vladmihalcea.book.hpjp.hibernate.identifier.uuid.PostgreSQLUUIDGenerationStrategy"))
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @Column(name = "email", columnDefinition = "text", nullable = false)
  private String email;

  @Column(name = "name", columnDefinition = "text", nullable = false)
  private String name;
}
