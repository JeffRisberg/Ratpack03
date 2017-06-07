package com.incra.ratpack.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incra.ratpack.models.Event;

import java.io.IOException;

/**
 * @author Jeff Risberg
 * @since 02/12/17
 */
public class EventSerializerModule extends SimpleModule {
    private static final String NAME = "EventSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    public EventSerializerModule() {
        super(NAME, VERSION_UTIL.version());

        addSerializer(Event.class, new JsonSerializer<Event>() {
            @Override
            public void serialize(Event event, JsonGenerator jGen, SerializerProvider serializerProvider)
                    throws IOException {

                jGen.writeStartObject();
                jGen.writeNumberField("id", event.getId());
                if (event.getEventDate() != null) {
                    jGen.writeNumberField("eventDate", event.getEventDate().getTime());
                }
                jGen.writeStringField("type", event.getEventType().name());
                jGen.writeStringField("page", event.getPage());
                jGen.writeStringField("detail", event.getDetail());
                jGen.writeStringField("userEmail", event.getUserEmail());
                jGen.writeEndObject();
            }
        });
    }
}
