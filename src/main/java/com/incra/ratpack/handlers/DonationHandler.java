package com.incra.ratpack.handlers;

import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.database.DatabaseItemManager;
import com.incra.ratpack.models.Donation;
import com.incra.ratpack.models.LoggingEvent;
import com.incra.ratpack.models.User;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ratpack.jackson.Jackson.json;

/**
 * @author Jeff Risberg
 * @since 12/13/16
 */
@Singleton
public class DonationHandler extends BaseHandler implements Handler {

    @Inject
    public DonationHandler() {
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.byMethod(metricSpec ->
                metricSpec
                        .post(() -> this.handlePost(ctx))
                        .get(() -> this.handleGet(ctx)));
    }

    private void handlePost(Context ctx) throws Exception {
        DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);
        String userIdStr = ctx.getRequest().getQueryParams()
                .getOrDefault("userId", "1");
        Integer userId = Integer.parseInt(userIdStr);
        String charity = ctx.getRequest().getQueryParams()
                .getOrDefault("charity", "ClickCount");
        String amountStr = ctx.getRequest().getQueryParams()
                .getOrDefault("amount", "100");
        Integer amount = Integer.parseInt(amountStr);

        Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

            User user = dbTransaction.getObject(User.class, "from User u where id=" + userId);
            dbTransaction.create(new Donation(user, charity, amount));
            dbTransaction.commit();
            return true;
        }).onError(t -> {
            ctx.getResponse().status(400);
            ctx.render(json(getResponseMap(false, t.getMessage())));
        }).then(r ->
                ctx.render(json(getResponseMap(true, null))));
    }

    private void handleGet(Context ctx) throws Exception {
        DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

        Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

            List<Donation> donationList = dbTransaction.getObjects(Donation.class, "Select m from Donation m", null);

            dbTransaction.commit();

            return donationList;
        }).then(donationList -> {
            Map response = new HashMap();
            response.put("data", donationList);
            ctx.render(json(response));
        });
    }
}