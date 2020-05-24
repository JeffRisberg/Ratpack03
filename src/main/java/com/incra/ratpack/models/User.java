package com.incra.ratpack.models;

import com.incra.ratpack.database.DatedDBItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Jeff Risberg
 * @since 11/26/16
 */
@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends DatedDBItem {
  @Column(name = "email")
  protected String email;

  @Column(name = "firstname")
  protected String firstname;

  @Column(name = "lastname")
  protected String lastname;

  @Column(name = "validated")
  protected Boolean validated = false;

  @Column(name = "address1")
  protected String addressLine1;

  @Column(name = "address2")
  protected String addressLine2;

  @Column(name = "city")
  protected String city;

  @Column(name = "state")
  protected String state;
}
