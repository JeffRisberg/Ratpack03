package com.incra.ratpack.models;

import com.incra.ratpack.database.AbstractDatabaseItem;
import javax.persistence.*;
import java.util.Date;
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
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends AbstractDatabaseItem {

  @Column(name = "event_date")
  private Date eventDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "event_type")
  private EventType eventType;

  @Column(name = "user_email")
  String userEmail;

  @Column() String page;

  @Column() String detail;
}
