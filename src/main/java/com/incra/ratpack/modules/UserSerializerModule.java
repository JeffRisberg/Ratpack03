package com.incra.ratpack.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.incra.ratpack.models.User;
import java.io.IOException;

/**
 * @author Jeff Risberg
 * @since 02/12/17
 */
public class UserSerializerModule extends SimpleModule {
  private static final String NAME = "UserSerializerModule";

  public UserSerializerModule() {
    super(NAME);

    addSerializer(
        User.class,
        new JsonSerializer<User>() {
          @Override
          public void serialize(
              User user, JsonGenerator jGen, SerializerProvider serializerProvider)
              throws IOException {

            jGen.writeStartObject();
            jGen.writeNumberField("id", user.getId());
            jGen.writeStringField("email", user.getEmail());
            jGen.writeStringField("firstname", user.getFirstname());
            jGen.writeStringField("lastname", user.getLastname());
            jGen.writeBooleanField("validated", user.getValidated());
            jGen.writeStringField("addressLine1", user.getAddressLine1());
            jGen.writeStringField("addressLine2", user.getAddressLine2());
            jGen.writeStringField("city", user.getCity());
            jGen.writeStringField("state", user.getState());
            if (user.getDateCreated() != null) {
              jGen.writeNumberField("dateCreated", user.getDateCreated().getTime());
            }
            if (user.getLastUpdated() != null) {
              jGen.writeNumberField("lastUpdated", user.getLastUpdated().getTime());
            }
            jGen.writeEndObject();
          }
        });
  }
}
