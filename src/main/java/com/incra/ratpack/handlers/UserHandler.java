package com.incra.ratpack.handlers;

import static ratpack.jackson.Jackson.json;

import com.incra.ratpack.binding.annotation.DB1;
import com.incra.ratpack.database.DBService;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.models.EventType;
import com.incra.ratpack.models.User;
import com.incra.ratpack.services.EventService;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import ratpack.exec.Blocking;
import ratpack.handling.Context;
import ratpack.handling.Handler;

/**
 * @author Jeff Risberg
 * @since 12/13/16
 */
@Singleton
public class UserHandler extends BaseHandler implements Handler {
  protected DBService dbService;
  protected EventService eventService;

  @Inject
  public UserHandler(@DB1 DBService dbService, EventService eventService) {
    if (dbService == null) {
      throw new IllegalArgumentException("unknown dbService");
    }
    this.dbService = dbService;
    this.eventService = eventService;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    ctx.byMethod(
        spec ->
            spec.get(() -> this.handleGet(ctx))
                .put(() -> this.handlePut(ctx))
                .post(() -> this.handlePost(ctx))
                .delete(() -> this.handleDelete(ctx)));
  }

  private void handleGet(Context ctx) throws Exception {
    Blocking.get(
            () -> {
              DBTransaction dbTransaction = dbService.getTransaction();

              String idStr = ctx.getPathTokens().get("id");

              List<User> userList;

              if (idStr != null && idStr.length() > 0) {
                userList =
                    dbTransaction.getObjects(
                        User.class, "select u from User u where id=" + idStr, null);
              } else {
                userList = dbTransaction.getObjects(User.class, "select u from User u", null);
              }

              dbTransaction.commit();
              dbTransaction.close();

              return userList;
            })
        .then(
            userList -> {
              Map response = new HashMap();
              response.put("data", userList);
              ctx.render(json(response));
            });
  }

  private void handlePut(Context ctx) throws Exception {
    ctx.parse(User.class)
        .then(
            revisedUser -> {
              Blocking.get(
                      () -> {
                        DBTransaction dbTransaction = dbService.getTransaction();

                        String idStr = ctx.getPathTokens().get("id");

                        List<User> userList =
                            dbTransaction.getObjects(
                                User.class, "select u from User u where id=" + idStr, null);

                        User user = userList.get(0);

                        user.setEmail(revisedUser.getEmail());
                        user.setFirstName(revisedUser.getFirstName());
                        user.setLastName(revisedUser.getLastName());
                        user.setAddressLine1(revisedUser.getAddressLine1());
                        user.setAddressLine2(revisedUser.getAddressLine2());
                        user.setCity(revisedUser.getCity());
                        user.setState(revisedUser.getState());
                        user.setLastUpdated(new Date(System.currentTimeMillis()));

                        dbTransaction.commit();
                        dbTransaction.close(); // transaction has changes, so close it

                        eventService.createEvent(
                            EventType.Update, user.getEmail(), "User", "update");

                        return userList;
                      })
                  .then(
                      userList -> {
                        Map response = new HashMap();
                        response.put("data", userList);
                        ctx.render(json(response));
                      });
            });
  }

  private void handlePost(Context ctx) throws Exception {
    ctx.parse(User.class)
        .then(
            newUser -> {
              Blocking.get(
                      () -> {
                        DBTransaction dbTransaction = dbService.getTransaction();

                        newUser.setLastUpdated(new Date(System.currentTimeMillis()));
                        newUser.setDateCreated(new Date(System.currentTimeMillis()));

                        dbTransaction.create(newUser);
                        dbTransaction.commit();
                        dbTransaction.close(); // transaction has changes, so close it

                        eventService.createEvent(
                            EventType.Register, newUser.getEmail(), "User", "create");

                        return true;
                      })
                  .onError(
                      t -> {
                        ctx.getResponse().status(400);
                        ctx.render(json(getResponseMap(false, t.getMessage())));
                      })
                  .then(r -> ctx.render(json(getResponseMap(true, null))));
            });
  }

  private void handleDelete(Context ctx) throws Exception {
    Blocking.get(
            () -> {
              DBTransaction dbTransaction = dbService.getTransaction();

              String idStr = ctx.getPathTokens().get("id");

              List<User> userList =
                  dbTransaction.getObjects(
                      User.class, "select u from User u where id=" + idStr, null);

              User user = userList.get(0);

              dbTransaction.delete(user);
              dbTransaction.commit();
              dbTransaction.close(); // transaction has changes, so close it

              eventService.createEvent(EventType.Delete, user.getEmail(), "User", "delete");

              return user;
            })
        .then(
            userList -> {
              Map response = new HashMap();
              response.put("data", userList);
              ctx.render(json(response));
            });
  }
}
