package com.incra.ratpack.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incra.ratpack.models.LoggingEvent;

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

                jGen.writeStartObject();
                if (loggingEvent.getEventDate() != null) {
                    jGen.writeNumberField("eventDate", loggingEvent.getEventDate().getTime());
                }
                jGen.writeNumberField("id", loggingEvent.getId());
                jGen.writeStringField("type", loggingEvent.getType());
                jGen.writeStringField("detail", loggingEvent.getDetail());
                jGen.writeStringField("userEmail", loggingEvent.getUserEmail());
                jGen.writeEndObject();
            }
        });
    }
}
