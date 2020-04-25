package io.github.notaphplover.catan.serialization.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.serialization.player.PlayerDeserializer;
import io.github.notaphplover.catan.serialization.player.PlayerSerializer;
import io.github.notaphplover.catan.serialization.resource.ResourceManagerDeserializer;
import io.github.notaphplover.catan.serialization.resource.ResourceStorageSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RequestDeserializer tests")
public class RequestDeserializerTest {

  @DisplayName("RequestDeserializer deserialization")
  @Nested
  class Deserialization {

    @DisplayName("It must deserialize a request")
    @Test
    public void itMustDeserializeARequest() throws JsonProcessingException {

      int playerId = 1;
      IResourceManager playerResources = new ResourceManager();

      IPlayer player = new Player(playerId, playerResources);

      RequestType requestType = RequestType.START_TURN;

      ObjectMapper objectSerializer = new ObjectMapper();

      SimpleModule serializerModule = new SimpleModule();

      serializerModule
          .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
          .addSerializer(IPlayer.class, new PlayerSerializer());

      objectSerializer.registerModule(serializerModule);

      String serializedPlayer = objectSerializer.writeValueAsString(player);

      String serializedEntity =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s }",
              RequestFields.FIELD_TYPE,
              requestType.toString(),
              RequestFields.FIELD_PLAYER,
              serializedPlayer);

      ObjectMapper objectDeserializer = new ObjectMapper();

      objectDeserializer.registerModule(
          new SimpleModule()
              .addDeserializer(
                  IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class))
              .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
              .addDeserializer(IRequest.class, new RequestDeserializer(IRequest.class)));

      IRequest request = objectDeserializer.readValue(serializedEntity, IRequest.class);

      assertSame(requestType, request.getType());
      assertSame(player.getId(), request.getPlayer().getId());
      assertEquals(player.getResourceManager(), request.getPlayer().getResourceManager());
    }
  }
}
