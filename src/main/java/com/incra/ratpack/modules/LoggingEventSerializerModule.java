package com.incra.ratpack.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incra.ratpack.models.LoggingEvent;
import com.incra.ratpack.models.User;

import java.io.IOException;

/**
 * @author Jeff Risberg
 * @since 02/12/17
 */
public class LoggingEventSerializerModule extends SimpleModule {
    private static final String NAME = "LoggingEventSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    public LoggingEventSerializerModule() {
        super(NAME, VERSION_UTIL.version());

        addSerializer(LoggingEvent.class, new JsonSerializer<LoggingEvent>() {
            @Override
            public void serialize(LoggingEvent loggingEvent, JsonGenerator jGen, SerializerProvider serializerProvider)
                    throws IOException {

                User user = loggingEvent.getUser();

                jGen.writeStartObject();
                jGen.writeNumberField("id", loggingEvent.getId());
                jGen.writeStringField("firstname", user.getFirstname());
                jGen.writeStringField("lastname", user.getLastname());

                jGen.writeNumberField("dateCreated", loggingEvent.getDateCreated().getTime());
                jGen.writeNumberField("lastUpdated", loggingEvent.getLastUpdated().getTime());
                jGen.writeEndObject();
            }
        });
    }
}
