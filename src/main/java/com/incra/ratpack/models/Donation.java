package com.incra.ratpack.models;

import com.incra.ratpack.database.AbstractDatedDatabaseItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Data
@Entity
@Table(name = "donation")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Donation extends AbstractDatedDatabaseItem {

  @ManyToOne()
  @JoinColumn(name = "user_id")
  User user;

  @Column(name = "charity_name")
  String charityName;

  @Column() Integer amount;
}
