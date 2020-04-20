package io.notaphplover.catan.jade.serialization.request;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.BuildConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialStructureRequest;
import io.github.notaphplover.catan.core.request.BuildStructureRequest;
import io.github.notaphplover.catan.core.request.EndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.notaphplover.catan.jade.serialization.request.builder.IRequestBuilder;
import io.notaphplover.catan.jade.serialization.request.builder.RequestBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RequestDeserializer extends StdDeserializer<IRequest> {

  private static final long serialVersionUID = 5097257206107976425L;

  private Map<String, Consumer<IRequestPropertyParserParams>> consumersMap;

  public RequestDeserializer(Class<? extends IRequest> vc) {
    super(vc);

    addRequestConsumers();
  }

  @Override
  public IRequest deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    if (p.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new JsonParseException(p, String.format("Expected a %s token", JsonToken.START_OBJECT));
    }

    IRequestBuilder builder = new RequestBuilder();

    p.nextToken();

    while (p.currentToken() != JsonToken.END_OBJECT) {

      extractProperty(builder, p, ctxt);
    }

    return createRequestFromBuilder(p, builder);
  }

  private static void extractBoardElementTypeProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.currentToken() != JsonToken.VALUE_STRING) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setBoardElementType(BoardElementType.valueOf(parser.getText()));
      parser.nextToken();
    } catch (IOException e) {
      params.setException(
          new JsonParseException(parser, "Error processing the board element type", e));
      return;
    }
  }

  private static void extractConnectionTypeProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.currentToken() != JsonToken.VALUE_STRING) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setConnectionType(ConnectionType.valueOf(parser.getText()));
      parser.nextToken();
    } catch (IOException e) {
      params.setException(
          new JsonParseException(parser, "Error processing the connection type", e));
      return;
    }
  }

  private static void extractPlayerProperty(IRequestPropertyParserParams params) {

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

    params.getBuilder().setPlayer(player);
  }

  private static void extractRequestTypeProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.currentToken() != JsonToken.VALUE_STRING) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setType(RequestType.valueOf(parser.getText()));
      parser.nextToken();
    } catch (IOException e) {
      params.setException(new JsonParseException(parser, "Error processing the request type", e));
      return;
    }
  }

  private static void extractStructureTypeProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.currentToken() != JsonToken.VALUE_STRING) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setStructureType(StructureType.valueOf(parser.getText()));
      parser.nextToken();
    } catch (IOException e) {
      params.setException(
          new JsonParseException(parser, "Error processing the connection type", e));
      return;
    }
  }

  private static void extractXProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setX(parser.getIntValue());
      parser.nextToken();
    } catch (IOException e) {
      params.setException(new JsonParseException(parser, "Error processing the x property", e));
      return;
    }
  }

  private static void extractYProperty(IRequestPropertyParserParams params) {

    JsonParser parser = params.getParser();

    if (parser.getCurrentToken() != JsonToken.VALUE_NUMBER_INT) {
      params.setException(
          new JsonParseException(
              parser, String.format("Unexpected %s token", parser.currentToken().toString())));
      return;
    }

    try {
      params.getBuilder().setY(parser.getIntValue());
      parser.nextToken();
    } catch (IOException e) {
      params.setException(new JsonParseException(parser, "Error processing the x property", e));
      return;
    }
  }

  private void addRequestConsumers() {

    this.consumersMap = new HashMap<>();

    this.consumersMap.put(
        RequestFields.FIELD_CONNECTION_TYPE,
        (IRequestPropertyParserParams params) -> {
          extractConnectionTypeProperty(params);
        });

    this.consumersMap.put(
        RequestFields.FIELD_ELEMENT_TYPE,
        (IRequestPropertyParserParams params) -> {
          extractBoardElementTypeProperty(params);
        });

    this.consumersMap.put(
        RequestFields.FIELD_PLAYER,
        (IRequestPropertyParserParams params) -> {
          extractPlayerProperty(params);
        });

    this.consumersMap.put(
        RequestFields.FIELD_STRUCTURE_TYPE,
        (IRequestPropertyParserParams params) -> {
          extractStructureTypeProperty(params);
        });

    this.consumersMap.put(
        RequestFields.FIELD_TYPE,
        (IRequestPropertyParserParams params) -> {
          extractRequestTypeProperty(params);
        });
    this.consumersMap.put(
        RequestFields.FIELD_X,
        (IRequestPropertyParserParams params) -> {
          extractXProperty(params);
        });

    this.consumersMap.put(
        RequestFields.FIELD_Y,
        (IRequestPropertyParserParams params) -> {
          extractYProperty(params);
        });
  }

  private IRequest createRequestFromBuilder(JsonParser p, IRequestBuilder builder)
      throws JsonParseException {
    switch (builder.getType()) {
      case BUILD_CONNECTION:
        return new BuildConnectionRequest(
            builder.getPlayer(), builder.getConnectionType(), builder.getX(), builder.getY());
      case BUILD_INITIAL_CONNECTION:
        return new BuildInitialConnectionRequest(
            builder.getPlayer(), builder.getConnectionType(), builder.getX(), builder.getY());
      case BUILD_INITIAL_STRUCTURE:
        return new BuildInitialStructureRequest(
            builder.getPlayer(), builder.getStructureType(), builder.getX(), builder.getY());
      case BUILD_STRUCTURE:
        return new BuildStructureRequest(
            builder.getPlayer(), builder.getStructureType(), builder.getX(), builder.getY());
      case END_TURN:
        return new EndTurnRequest(builder.getPlayer());
      case START_TURN:
        return new StartTurnRequest(builder.getPlayer());
      default:
        throw new JsonParseException(
            p, String.format("Request type %s not supported", builder.getType()));
    }
  }

  private void extractProperty(IRequestBuilder builder, JsonParser p, DeserializationContext ctxt)
      throws IOException {

    if (p.currentToken() != JsonToken.FIELD_NAME) {
      throw new JsonParseException(p, String.format("Unexpected %s token", p.currentToken()));
    }

    String fieldName = p.getText();

    Consumer<IRequestPropertyParserParams> consumer = consumersMap.get(fieldName);

    if (consumer == null) {
      throw new JsonParseException(
          p, String.format("No processor was found for property %s", fieldName));
    }

    p.nextToken();

    IRequestPropertyParserParams params = new RequestPropertyParserParams(builder, ctxt, p);

    consumer.accept(params);

    if (params.getException() != null) {
      throw params.getException();
    }
  }
}
