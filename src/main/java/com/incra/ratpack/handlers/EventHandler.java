package com.incra.ratpack.handlers;

import com.incra.ratpack.binding.annotation.DB2;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.Event;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
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
public class EventHandler extends BaseHandler implements Handler {
    protected DBService dbService;

    @Inject
    public EventHandler(@DB2 DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Blocking.get(() -> {
            DBTransaction dbTransaction = dbService.getTransaction();

            List<Event> eventList = dbTransaction.getObjects(Event.class, "select e from Event e", null, 0, 20);

            dbTransaction.commit();
            dbTransaction.close();

            return eventList;
        }).then(eventList -> {
            Map m = new HashMap();
            m.put("events", eventList);

            HikariDataSource hds = dbService.getDataSource();
            HikariPoolMXBean poolMXBean = hds.getHikariPoolMXBean();
            int idleConnections = poolMXBean.getIdleConnections();
            int activeConnections = poolMXBean.getActiveConnections();
            int threadsAwaitingConnections = poolMXBean.getThreadsAwaitingConnection();
            int totalConnections = poolMXBean.getTotalConnections();

            m.put("idleConnections", idleConnections);
            m.put("activeConnections", activeConnections);
            m.put("threadsAwaitingConnections", threadsAwaitingConnections);
            m.put("totalConnections", totalConnections);

            ctx.render(handlebarsTemplate("eventSummary.html", m, "text/html"));
        });
    }
}

