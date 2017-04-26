package com.incra.ratpack.handlers;

import com.incra.ratpack.database.DBSessionFactory;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.database.DatabaseItemManager;
import com.incra.ratpack.models.User;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ratpack.jackson.Jackson.json;

/**
 * @author Jeff Risberg
 * @since 12/13/16
 */
@Singleton
public class UserHandler extends BaseHandler implements Handler {

    @Inject
    public UserHandler() {
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.byMethod(spec ->
                spec
                        .get(() -> this.handleGet(ctx))
                        .put(() -> this.handlePut(ctx))
                        .post(() -> this.handlePost(ctx))
                        .delete(() -> this.handleDelete(ctx)));
    }

    private void handleGet(Context ctx) throws Exception {
        DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

        Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

            String idStr = ctx.getPathTokens().get("id");

            List<User> userList;

            if (idStr != null && idStr.length() > 0) {
                userList = dbTransaction.getObjects
                        (User.class, "Select u from User u where id=" + idStr, null);
            } else {
                userList = dbTransaction.getObjects
                        (User.class, "Select u from User u", null);
            }

            dbTransaction.commit();

            return userList;
        }).then(userList -> {
            Map response = new HashMap();
            response.put("data", userList);
            ctx.render(json(response));
        });
    }

    private void handlePut(Context ctx) throws Exception {
        ctx.parse(User.class).then(revisedUser -> {
            DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

            Blocking.get(() -> {
                DataSource dataSource = ctx.get(DataSource.class);
                DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

                String idStr = ctx.getPathTokens().get("id");

                List<User> userList = dbTransaction.getObjects
                        (User.class, "Select u from User u where id=" + idStr, null);

                User user = userList.get(0);

                user.setEmail(revisedUser.getEmail());
                user.setFirstname(revisedUser.getFirstname());
                user.setLastname(revisedUser.getLastname());
                user.setCity(revisedUser.getCity());
                user.setState(revisedUser.getState());
                user.setLastUpdated(new Date(System.currentTimeMillis()));

                dbTransaction.commit();
                dbTransaction.close(); // transaction has changes, so close it

                return userList;
            }).then(userList -> {
                Map response = new HashMap();
                response.put("data", userList);
                ctx.render(json(response));
            });
        });
    }

    private void handlePost(Context ctx) throws Exception {
        ctx.parse(User.class).then(newUser -> {
            DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

            Blocking.get(() -> {
                DataSource dataSource = ctx.get(DataSource.class);
                DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

                newUser.setLastUpdated(new Date(System.currentTimeMillis()));
                newUser.setDateCreated(new Date(System.currentTimeMillis()));

                dbTransaction.create(newUser);
                dbTransaction.commit();
                dbTransaction.close(); // transaction has changes, so close it

                return true;
            }).onError(t -> {
                ctx.getResponse().status(400);
                ctx.render(json(getResponseMap(false, t.getMessage())));
            }).then(r ->
                    ctx.render(json(getResponseMap(true, null))));
        });
    }

    private void handleDelete(Context ctx) throws Exception {
        DatabaseItemManager dbManager = DatabaseItemManager.getInstance(ctx);

        Blocking.get(() -> {
            DataSource dataSource = ctx.get(DataSource.class);
            DBTransaction dbTransaction = dbManager.getTransaction(dataSource, persistanceUnitName);

            String idStr = ctx.getPathTokens().get("id");

            List<User> userList = dbTransaction.getObjects
                    (User.class, "Select u from User u where id=" + idStr, null);

            User user = userList.get(0);

            dbTransaction.delete(user);
            dbTransaction.commit();
            dbTransaction.close(); // transaction has changes, so close it

            return user;
        }).then(userList -> {
            Map response = new HashMap();
            response.put("data", userList);
            ctx.render(json(response));
        });
    }
}
