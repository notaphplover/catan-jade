package io.notaphplover.catan.jade.serialization.player;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.notaphplover.catan.jade.serialization.request.RequestTypeMap;
import java.io.IOException;

public class PlayerSerializer extends StdSerializer<IPlayer> {

  private static final long serialVersionUID = -573148453890269800L;

  public PlayerSerializer() {
    super((Class<IPlayer>) null);
  }

  @Override
  public void serialize(IPlayer value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeStartObject();

    gen.writeNumberField(PlayerFields.FIELD_ID, value.getId());

    gen.writeArrayFieldStart(PlayerFields.FIELD_REQUESTS);

    for (IRequest request : value.getMissing()) {

      provider
          .findValueSerializer(RequestTypeMap.getClassOfType(request.getType()))
          .serialize(request, gen, provider);
    }

    gen.writeEndArray();

    gen.writeFieldName(PlayerFields.FIELD_RESOURCES);

    provider
        .findValueSerializer(IResourceManager.class)
        .serialize(value.getResourceManager(), gen, provider);

    gen.writeEndObject();
  }
}
