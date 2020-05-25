package com.incra.ratpack.models;

import com.incra.ratpack.database.AbstractDatedDatabaseItem;
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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractDatedDatabaseItem {
  @Column(name = "email")
  protected String email;

  @Column(name = "first_name")
  protected String firstName;

  @Column(name = "last_name")
  protected String lastName;

  @Column(name = "address1")
  protected String addressLine1;

  @Column(name = "address2")
  protected String addressLine2;

  @Column(name = "city")
  protected String city;

  @Column(name = "state")
  protected String state;
}
