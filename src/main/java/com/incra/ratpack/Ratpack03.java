package com.incra.ratpack;

import com.incra.ratpack.config.DatabaseConfig;
import com.incra.ratpack.handlers.EventHandler;
import com.incra.ratpack.handlers.MetricHandler;
import com.incra.ratpack.handlers.UserHandler;
import com.incra.ratpack.modules.EventModule;
import com.incra.ratpack.modules.MetricModule;
import com.incra.ratpack.modules.UserModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.handlebars.HandlebarsModule;
import ratpack.hikari.HikariModule;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

/**
 * @author Jeff Risberg
 * @since 01/03/17
 */
public class Ratpack03 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ratpack03.class);

    public static void main(String[] args) throws Exception {
        RatpackServer.start(spec -> spec
                .serverConfig(ctx -> {
                            ctx.baseDir(BaseDir.find());
                            ctx.json("databaseConfig.json");
                            ctx.require("/database", DatabaseConfig.class);
                        }
                )
                .registry(Guice.registry(bindingsSpec -> {
                    ServerConfig serverConfig = bindingsSpec.getServerConfig();
                    DatabaseConfig databaseConfig = serverConfig.get("/database", DatabaseConfig.class);

                    String server = databaseConfig.getServer();
                    Integer portNumber = databaseConfig.getPortNumber();
                    String databaseName = databaseConfig.getDatabaseName();
                    String url = String.format("jdbc:mysql://%s:%d/%s?allowMultiQueries=true&characterEncoding=utf8",
                            server, portNumber, databaseName);

                    bindingsSpec
                            .module(HikariModule.class, c -> {
                                c.setDriverClassName("com.mysql.jdbc.Driver");
                                c.setJdbcUrl(url);
                                c.setUsername(databaseConfig.getUsername());
                                c.setPassword(databaseConfig.getPassword());
                            })
                            .module(HandlebarsModule.class)
                            .module(EventModule.class)
                            .module(MetricModule.class)
                            .module(UserModule.class);
                }))
                .handlers(chain -> chain
                        .path("events", EventHandler.class)
                        .path("metrics", MetricHandler.class)
                        .path("users", UserHandler.class)
                        .all(ctx -> ctx.render("root handler!"))
                )
        );
    }
}