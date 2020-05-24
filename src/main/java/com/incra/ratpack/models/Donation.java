package com.incra.ratpack.models;

import com.incra.ratpack.database.DatedDBItem;

import javax.persistence.*;
import lombok.Data;

/**
 * @author Jeff Risberg
 * @since 12/30/16
 */
@Data
@Entity
@Table(name = "donation")
public class Donation extends DatedDBItem {

  @ManyToOne()
  @JoinColumn(name = "user_id")
  User user;

  @Column(name = "charity_name")
  String charityName;

  @Column() Integer amount;
}
