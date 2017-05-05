package com.incra.ratpack.handlers;

import com.incra.ratpack.binding.annotation.DB2;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.LoggingEvent;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ratpack.handlebars.Template.handlebarsTemplate;

/**
 * @author Jeff Risberg
 * @since 12/13/16
 */
@Singleton
public class LoggingEventHandler extends BaseHandler implements Handler {
    protected DBService dbService;

    @Inject
    public LoggingEventHandler(@DB2 DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Blocking.get(() -> {
            DBTransaction dbTransaction = dbService.getTransaction();

            List<LoggingEvent> eventList = dbTransaction.getObjects(LoggingEvent.class, "select e from LoggingEvent e", null);

            dbTransaction.commit();
            dbTransaction.close();

            return eventList;
        }).then(eventList -> {
            Map m = new HashMap();
            m.put("events", eventList);

            ctx.render(handlebarsTemplate("eventSummary.html", m, "text/html"));
        });
    }
}

