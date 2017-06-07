package com.incra.ratpack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incra.ratpack.config.DatabaseConfig;
import com.incra.ratpack.handlers.DonationHandler;
import com.incra.ratpack.handlers.EventHandler;
import com.incra.ratpack.handlers.UserHandler;
import com.incra.ratpack.modules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.handlebars.HandlebarsModule;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

/**
 * @author Jeff Risberg
 * @since 03/13/17
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

                    bindingsSpec
                            .add(ObjectMapper.class, new ObjectMapper()
                                    .registerModule(new UserSerializerModule())
                                    .registerModule(new EventSerializerModule())
                                    .registerModule(new DonationSerializerModule()));

                    bindingsSpec
                            .module(new Ratpack03Module(serverConfig));

                    bindingsSpec
                            .module(HandlebarsModule.class)
                            .module(EventModule.class)
                            .module(DonationModule.class)
                            .module(UserModule.class);
                }))
                .handlers(chain -> chain
                        .prefix("api", subchain -> subchain
                                .path("events", EventHandler.class)
                                .path("donations", DonationHandler.class)
                                .prefix("users", usersChain -> usersChain
                                        .path(":id", UserHandler.class)
                                        .all(UserHandler.class)
                                )
                        )
                        .files(files -> files.dir("static").indexFiles("index.html"))
                )
        );
    }
}