package com.incra.ratpack.services;

import com.google.inject.Inject;
import com.incra.ratpack.binding.annotation.DB2;
import com.incra.ratpack.database.DBException;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

/**
 * The <i>EventService</i> handles creating and fetching Logging Events.
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

    public LoggingEvent createEvent(String userEmail, String type, String detail) {
        Date eventDate = new Date(System.currentTimeMillis());
        LoggingEvent event = new LoggingEvent(eventDate, userEmail, type, detail);

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

    /**
     * Used mostly in test cases and admin screens
     */
    public List<LoggingEvent> getEvents(int offset, int limit) throws DBException {
        DBTransaction transaction = dbService.getTransaction();
        List<LoggingEvent> result = transaction.getObjects
                (LoggingEvent.class, "select e from LoggingEvent e", null, offset, limit);

        transaction.close();
        return result;
    }

    public LoggingEvent getEventById(int id) throws DBException {
        DBTransaction transaction = dbService.getTransaction();
        LoggingEvent result = transaction.getObjectById(LoggingEvent.class, id);

        transaction.close();
        return result;
    }
}
