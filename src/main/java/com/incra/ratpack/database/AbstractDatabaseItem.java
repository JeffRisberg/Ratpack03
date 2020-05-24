package com.incra.ratpack.database;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract database item, which has an id field.
 *
 * @author Jeff Risberg
 * @since late 2016
 */
@MappedSuperclass
public abstract class AbstractDatabaseItem implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public AbstractDatabaseItem() {
    super();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public boolean isPersisted() {
    return id != null;
  }

  public String toString() {
    return "(" + this.getClass().getSimpleName() + " (" + id + "))";
  }
}
