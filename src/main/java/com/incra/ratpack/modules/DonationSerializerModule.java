package com.incra.ratpack.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incra.ratpack.models.Donation;
import com.incra.ratpack.models.User;

import java.io.IOException;

/**
 * @author Jeff Risberg
 * @since 02/12/17
 */
public class DonationSerializerModule extends SimpleModule {
    private static final String NAME = "DonationSerializerModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    public DonationSerializerModule() {
        super(NAME, VERSION_UTIL.version());

        addSerializer(Donation.class, new JsonSerializer<Donation>() {
            @Override
            public void serialize(Donation donation, JsonGenerator jGen, SerializerProvider serializerProvider)
                    throws IOException {

                User user = donation.getUser();

                jGen.writeStartObject();
                jGen.writeNumberField("id", donation.getId());
                jGen.writeStringField("username", user.getFirstname() + " " + user.getLastname());
                jGen.writeStringField("charityName", donation.getCharityName());
                jGen.writeNumberField("amount", donation.getAmount());

                jGen.writeNumberField("dateCreated", donation.getDateCreated().getTime());
                jGen.writeNumberField("lastUpdated", donation.getLastUpdated().getTime());
                jGen.writeEndObject();
            }
        });
    }
}
