package io.github.notaphplover.catan.serialization.command;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.serialization.command.builder.CommandBuilder;
import io.github.notaphplover.catan.serialization.command.builder.ICommandBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandDeserializer extends StdDeserializer<ICommand> {

  private static final long serialVersionUID = -5428647797915522816L;

  private Map<String, Consumer<ICommandPropertyParserParams>> consumersMap;

  public CommandDeserializer(Class<? extends ICommand> vc) {
    super(vc);

    addConsumers();
  }

  @Override
  public ICommand deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    if (p.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new JsonParseException(p, String.format("Expected a %s token", JsonToken.START_OBJECT));
    }

    ICommandBuilder builder = new CommandBuilder();

    p.nextToken();

    while (p.currentToken() != JsonToken.END_OBJECT) {

      extractProperty(builder, p, ctxt);
    }

    return createCommandFromBuilder(p, builder);
  }

  private static void extractPlayerProperty(ICommandPropertyParserParams params) {

    DeserializationContext context = params.getContext();
    JsonDeserializer<Object> playerDeserializer;
    try {
      playerDeserializer =
          context.findNonContextualValueDeserializer(
              context.getTypeFactory().constructType(IPlayer.class));
    } catch (JsonMappingException e) {
      params.setException(
          new JsonParseException(params.getParser(), "Error finding an IPlayer serializer", e));
      return;
    }
    IPlayer player;

    try {
      player = (IPlayer) playerDeserializer.deserialize(params.getParser(), context);
    } catch (IOException e) {
      params.setException(
          new JsonParseException(params.getParser(), "Error deserializing a player", e));
      return;
    }

    params.getBuilder().setDestinatary(player);
  }

  private static void extractTypeProperty(ICommandPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.currentToken() != JsonToken.VALUE_STRING) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setType(CommandType.valueOf(parser.getText()));
      parser.nextToken();
    } catch (IOException e) {
      params.setException(new JsonParseException(parser, "Error processing the request type", e));
      return;
    }
  }

  private void addConsumers() {

    this.consumersMap = new HashMap<>();

    this.consumersMap.put(
        CommandFields.FIELD_DESTINATARY,
        (ICommandPropertyParserParams params) -> {
          extractPlayerProperty(params);
        });

    this.consumersMap.put(
        CommandFields.FIELD_TYPE,
        (ICommandPropertyParserParams params) -> {
          extractTypeProperty(params);
        });
  }

  private ICommand createCommandFromBuilder(JsonParser p, ICommandBuilder builder)
      throws JsonParseException {
    switch (builder.getType()) {
      case DIE:
      case SEND_FOUNDATION_REQUEST:
      case SEND_NORMAL_REQUEST:
      case START_TURN:
        return new Command(builder.getDestinatary(), builder.getType());
      default:
        throw new JsonParseException(
            p, String.format("Request type %s not supported", builder.getType()));
    }
  }

  private void extractProperty(ICommandBuilder builder, JsonParser p, DeserializationContext ctxt)
      throws IOException {

    if (p.currentToken() != JsonToken.FIELD_NAME) {
      throw new JsonParseException(p, String.format("Unexpected %s token", p.currentToken()));
    }

    String fieldName = p.getText();

    Consumer<ICommandPropertyParserParams> consumer = consumersMap.get(fieldName);

    if (consumer == null) {
      throw new JsonParseException(
          p, String.format("No processor was found for property %s", fieldName));
    }

    p.nextToken();

    ICommandPropertyParserParams params = new CommandPropertyParserParams(builder, ctxt, p);

    consumer.accept(params);

    if (params.getException() != null) {
      throw params.getException();
    }
  }
}
