package io.notaphplover.catan.serialization.player;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.notaphplover.catan.serialization.player.builder.IPlayerBuilder;
import io.notaphplover.catan.serialization.player.builder.PlayerBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PlayerDeserializer extends StdDeserializer<IPlayer> {

  public PlayerDeserializer(Class<? extends IPlayer> vc) {
    super(vc);
  }

  private static final long serialVersionUID = -2870447339559736830L;

  @Override
  public IPlayer deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    if (p.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new JsonParseException(p, String.format("Expected a %s token", JsonToken.START_OBJECT));
    }

    IPlayerBuilder builder = new PlayerBuilder();

    p.nextToken();

    while (p.currentToken() != JsonToken.END_OBJECT) {
      extractProperty(builder, p, ctxt);
    }

    p.nextToken();

    Player player = new Player(builder.getId(), builder.getResourceManager());

    if (builder.getMissing() != null) {
      for (IRequest request : builder.getMissing()) {
        player.registerMiss(request);
      }
    }

    return player;
  }

  private void extractPlayerIdProperty(IPlayerBuilder builder, JsonParser p) throws IOException {

    if (p.currentToken() != JsonToken.VALUE_NUMBER_INT) {
      throw new JsonParseException(
          p, String.format("Unexpected token %s", p.currentToken().toString()));
    }

    builder.setId(p.getIntValue());
    p.nextToken();
  }

  private void extractPlayerRequestsProperty(
      IPlayerBuilder builder, JsonParser p, DeserializationContext ctxt)
      throws JsonMappingException, JsonProcessingException, IOException {

    if (p.currentToken() != JsonToken.START_ARRAY) {
      throw new JsonParseException(
          p, String.format("Unexpected token %s", p.currentToken().toString()));
    }

    Collection<IRequest> requests = new ArrayList<>();

    p.nextToken();

    while (p.currentToken() != JsonToken.END_ARRAY) {
      IRequest request =
          (IRequest)
              ctxt.findNonContextualValueDeserializer(
                      ctxt.getTypeFactory().constructType(IRequest.class))
                  .deserialize(p, ctxt);
      requests.add(request);
    }

    builder.setMissing(requests);

    p.nextToken();
  }

  private void extractPlayerResourcesProperty(
      IPlayerBuilder builder, JsonParser p, DeserializationContext ctxt) throws IOException {
    IResourceManager resourceManager =
        (IResourceManager)
            ctxt.findNonContextualValueDeserializer(
                    ctxt.getTypeFactory().constructType(IResourceManager.class))
                .deserialize(p, ctxt);
    builder.setResourceManager(resourceManager);
  }

  private void extractProperty(IPlayerBuilder builder, JsonParser p, DeserializationContext ctxt)
      throws IOException {

    if (p.currentToken() != JsonToken.FIELD_NAME) {
      throw new JsonParseException(p, String.format("Unexpected %s token", p.currentToken()));
    }

    String fieldName = p.getText();

    p.nextToken();

    if (fieldName.equals(PlayerFields.FIELD_ID)) {
      extractPlayerIdProperty(builder, p);
    } else if (fieldName.equals(PlayerFields.FIELD_REQUESTS)) {
      extractPlayerRequestsProperty(builder, p, ctxt);
    } else if (fieldName.equals(PlayerFields.FIELD_RESOURCES)) {
      extractPlayerResourcesProperty(builder, p, ctxt);
    } else {
      throw new JsonParseException(p, String.format("Unexpected field %s", fieldName));
    }
  }
}
