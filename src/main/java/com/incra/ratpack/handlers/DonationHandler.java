package com.incra.ratpack.handlers;

import com.incra.ratpack.binding.annotation.DB1;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.Donation;
import com.incra.ratpack.models.User;
import com.incra.ratpack.services.EventService;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ratpack.jackson.Jackson.json;

/**
 * @author Jeff Risberg
 * @since 12/13/16
 */
@Singleton
public class DonationHandler extends BaseHandler implements Handler {
    protected DBService dbService;
    protected EventService eventService;

    @Inject
    public DonationHandler(@DB1 DBService dbService, EventService eventService) {
        this.dbService = dbService;
        this.eventService = eventService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.byMethod(metricSpec ->
                metricSpec
                        .post(() -> this.handlePost(ctx))
                        .get(() -> this.handleGet(ctx)));
    }

    private void handlePost(Context ctx) throws Exception {
        ctx.parse(Donation.class).then(newDonation -> {
            String userIdStr = ctx.getRequest().getQueryParams()
                    .getOrDefault("userId", "1");
            Integer userId = Integer.parseInt(userIdStr);

            Blocking.get(() -> {
                DBTransaction dbTransaction = dbService.getTransaction();

                User user = dbTransaction.getObject(User.class, "from User u where id=" + userId);
                if (user != null) {
                    newDonation.setUser(user);
                    dbTransaction.create(newDonation);

                    dbTransaction.commit();
                    dbTransaction.close(); // transaction has changes, so close it

                    eventService.createEvent(user.getEmail(), "Donation", "create");
                    return true;
                } else {
                    return false;
                }

            }).onError(t -> {
                ctx.getResponse().status(400);
                ctx.render(json(getResponseMap(false, t.getMessage())));
            }).then(r ->
                    ctx.render(json(getResponseMap(true, null))));
        });
    }

    private void handleGet(Context ctx) throws Exception {
        Blocking.get(() -> {
            DBTransaction dbTransaction = dbService.getTransaction();

            List<Donation> donationList = dbTransaction.getObjects(Donation.class, "select d from Donation d", null);

            dbTransaction.commit();
            dbTransaction.close();

            return donationList;
        }).then(donationList -> {
            Map response = new HashMap();
            response.put("data", donationList);
            ctx.render(json(response));
        });
    }
}