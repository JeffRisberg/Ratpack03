package com.incra.ratpack.handlers;

import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.database.DatabaseItemManager;
import com.incra.ratpack.models.LoggingEvent;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
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

    @Inject
    public LoggingEventHandler() {
    }

    @Override
    public void handle(Context ctx) throws Exception {
        DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

        Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

            List<LoggingEvent> eventList = dbTransaction.getObjects(LoggingEvent.class, "Select e from LoggingEvent e", null);

            dbTransaction.commit();

            return eventList;
        }).then(eventList -> {
            Map m = new HashMap();
            m.put("events", eventList);

            ctx.render(handlebarsTemplate("eventSummary.html", m, "text/html"));
        });
    }
}

