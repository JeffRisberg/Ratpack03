package com.incra.ratpack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.incra.ratpack.database.DBTransaction;
import com.incra.ratpack.database.DatabaseItemManager;
import com.incra.ratpack.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Blocking;
import ratpack.guice.Guice;
import ratpack.hikari.HikariModule;
import ratpack.jackson.JsonRender;
import ratpack.server.RatpackServer;
import ratpack.server.Service;
import ratpack.server.StartEvent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static ratpack.jackson.Jackson.json;

/**
 * @author Jeff Risberg
 * @since May 2016
 */
public class Ratpack03 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ratpack03.class);

    public static void main(String[] args) throws Exception {
        RatpackServer.start(spec -> spec
                        .registry(Guice.registry(bindingsSpec ->
                                bindingsSpec
                                        .module(HikariModule.class, c -> {
                                            c.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
                                            c.addDataSourceProperty("URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
                                            c.setUsername("sa");
                                            c.setPassword("");
                                        })
                                        .bindInstance(new Service() {
                                            @Override
                                            public void onStart(StartEvent event) throws Exception {
                                                DataSource dataSource = event.getRegistry().get(DataSource.class);
                                                try (Connection connection = dataSource.getConnection()) {
                                                    connection.createStatement()
                                                            .execute("CREATE TABLE USER (ID INT PRIMARY KEY AUTO_INCREMENT, " +
                                                                    "DATE_CREATED DATE, " +
                                                                    "LAST_UPDATED DATE, " +
                                                                    "USERNAME VARCHAR(255), " +
                                                                    "EMAIL VARCHAR(255));");
                                                    connection.createStatement()
                                                            .execute("INSERT INTO USER (DATE_CREATED, USERNAME, EMAIL) VALUES(now(), 'Luke Daley','luke@gmail.com')");
                                                    connection.createStatement()
                                                            .execute("INSERT INTO USER (DATE_CREATED, USERNAME, EMAIL) VALUES(now(), 'Rob Fletch','rob@gmail.com')");
                                                    connection.createStatement()
                                                            .execute("INSERT INTO USER (DATE_CREATED, USERNAME, EMAIL) VALUES(now(), 'Dan Woods','dan@gmail.com')");
                                                }
                                            }
                                        })))
                        .handlers(chain -> chain
                                .get("charities", ctx -> {
                                    ctx.render("charities");
                                })
                                .get(ctx -> {
                                    DatabaseItemManager dbManager = DatabaseItemManager.getInstance();

                                    Blocking.get(() -> {
                                        DBTransaction dbTransaction = dbManager.getTransaction();

                                        List<User> listUsers = dbTransaction.getObjects(User.class, "Select u from User u", null);

                                        List<Map<String, String>> personList = Lists.newArrayList();
                                        for (User user : listUsers) {
                                            Map<String, String> person = Maps.newHashMap();
                                            person.put("id", "" + user.getId());
                                            person.put("dateCreated", "" + user.getDateCreated());
                                            person.put("lastUpdated", "" + user.getLastUpdated());
                                            person.put("username", user.getUsername());
                                            person.put("email", user.getEmail());
                                            personList.add(person);
                                        }
                                        dbTransaction.commit();

                                        return personList;
                                    }).then(personList -> ctx.render(json(personList)));
                                })
                                .post("create", ctx -> {
                                            DatabaseItemManager dbManager = DatabaseItemManager.getInstance();

                                            Blocking.get(() -> {
                                                DBTransaction dbTransaction = dbManager.getTransaction();

                                                User newUser = new User();
                                                newUser.setUsername("Han Solo");
                                                newUser.setEmail("han@rebels.org");
                                                dbTransaction.create(newUser);

                                                /*
                                                DataSource dataSource = ctx.get(DataSource.class);
                                                try (Connection connection = dataSource.getConnection()) {
                                                    PreparedStatement pstmt = connection.prepareStatement(
                                                            "INSERT INTO USER (USERNAME,EMAIL) VALUES(?1, 'email@gmail.com');");
                                                    pstmt.setString(1, "Bob");
                                                    pstmt.execute();
                                                }
                                                */
                                                dbTransaction.commit();
                                                return true;
                                            }).onError(t -> {
                                                ctx.getResponse().status(400);
                                                ctx.render(json(getResponseMap(false, t.getMessage())));
                                            }).then(r ->
                                                    ctx.render(json(getResponseMap(true, null))));
                                        }
                                ))
        );
    }

    class DatabaseConfig {
        String host = "localhost";
        String user = "root";
        String password;
        String db = "myDB";
    }

    private static Map<String, Object> getResponseMap(Boolean status, String message) {
        Map<String, Object> response = Maps.newHashMap();
        response.put("success", status);
        response.put("error", message);
        return response;
    }
}

