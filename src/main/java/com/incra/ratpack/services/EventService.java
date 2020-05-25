package com.incra.ratpack.services;

import com.google.inject.Inject;
import com.incra.ratpack.binding.annotation.DB2;
import com.incra.ratpack.database.DBException;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.Event;
import com.incra.ratpack.models.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

/**
 * The <i>EventService</i> handles creating and fetching Logging Users.
 *
 * @author Jeff Risberg
 * @since 05/03/17
 */
public class EventService {
  private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);

  private DBService dbService;

  @Inject
  public EventService(@DB2 DBService dbService) {
    this.dbService = dbService;
  }

  public Event createEvent(EventType eventType, String userEmail, String page, String detail) {
    Date eventDate = new Date(System.currentTimeMillis());
    Event event = new Event(eventDate, eventType, userEmail, page, detail);

    try {
      DBTransaction transaction = dbService.getTransaction();

      transaction.create(event);

      transaction.commit();
      transaction.close();
    } catch (DBException e) {
      e.printStackTrace();
    }
    return event;
  }

  /** Used mostly in test cases and admin screens */
  public List<Event> getEvents(int offset, int limit) throws DBException {
    DBTransaction transaction = dbService.getTransaction();
    List<Event> result =
        transaction.getObjects(Event.class, "select e from Event e", null, offset, limit);

    transaction.close();
    return result;
  }

  public Event getEventById(int id) throws DBException {
    DBTransaction transaction = dbService.getTransaction();
    Event result = transaction.getObjectById(Event.class, id);

    transaction.close();
    return result;
  }
}
