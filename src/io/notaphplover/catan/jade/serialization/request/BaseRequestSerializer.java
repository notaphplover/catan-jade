package io.notaphplover.catan.jade.serialization.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IRequest;
import java.io.IOException;

public abstract class BaseRequestSerializer<R extends IRequest> extends StdSerializer<R> {

  private static final long serialVersionUID = 7166033008393313764L;

  protected BaseRequestSerializer() {
    super((Class<R>) null);
  }

  @Override
  public void serialize(R value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();

    innertSerializeContent(value, gen, provider);

    gen.writeEndObject();
  }

  protected void innertSerializeContent(R value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeFieldName(RequestFields.FIELD_PLAYER);

    provider.findValueSerializer(IPlayer.class).serialize(value.getPlayer(), gen, provider);

    gen.writeStringField(RequestFields.FIELD_TYPE, value.getType().toString());
  }
}
