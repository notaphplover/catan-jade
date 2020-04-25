package io.github.notaphplover.catan.serialization.command;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.player.IPlayer;
import java.io.IOException;

public abstract class BaseCommandSerializer<C extends ICommand> extends StdSerializer<C> {

  private static final long serialVersionUID = -8975770854939946502L;

  protected BaseCommandSerializer() {
    super((Class<C>) null);
  }

  @Override
  public void serialize(C value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();

    innertSerializeContent(value, gen, provider);

    gen.writeEndObject();
  }

  protected void innertSerializeContent(C value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeFieldName(CommandFields.FIELD_DESTINATARY);

    provider.findValueSerializer(IPlayer.class).serialize(value.getDestinatary(), gen, provider);

    gen.writeStringField(CommandFields.FIELD_TYPE, value.getType().toString());
  }
}
