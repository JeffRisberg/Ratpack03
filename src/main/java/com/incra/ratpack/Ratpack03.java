package com.incra.ratpack;

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
                        }
                )
                .registry(Guice.registry(bindingsSpec ->
                        bindingsSpec
                                .module(HikariModule.class, c -> {
                                    c.setDriverClassName("com.mysql.jdbc.Driver");
                                    c.setJdbcUrl("jdbc:mysql://localhost:3306/ratpack03");
                                    c.setUsername("developer");
                                    c.setPassword("123456");
                                })
                                .module(HandlebarsModule.class)
                                .module(EventModule.class)
                                .module(MetricModule.class)
                                .module(UserModule.class)
                ))
                .handlers(chain -> chain
                        .path("events", EventHandler.class)
                        .path("metrics", MetricHandler.class)
                        .path("users", UserHandler.class)
                )
        );
    }
}