package io.notaphplover.catan.serialization.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.notaphplover.catan.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.serialization.player.PlayerSerializer;
import io.notaphplover.catan.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.serialization.resource.ResourceStorageSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RequestSerializerTest")
public class RequestSerializerTest {

  @Nested
  @DisplayName("RequestTest serialization")
  class Serialization {

    @DisplayName("It must serialize a request")
    @Test
    public void itMustSerializeARequest() throws JsonProcessingException {
      IPlayer player = new Player(1, new ResourceManager());
      StartTurnRequest request = new StartTurnRequest(player);

      ObjectMapper objectMapper = new ObjectMapper();

      SimpleModule module = new SimpleModule();

      module
          .addSerializer(IPlayer.class, new PlayerSerializer())
          .addSerializer(IRequest.class, new RequestSerializer())
          .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
          .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
          .addDeserializer(IRequest.class, new RequestDeserializer(IRequest.class))
          .addDeserializer(
              IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class));

      objectMapper.registerModule(module);

      String serializedJson = objectMapper.writeValueAsString(request);

      IRequest deserializedRequest = objectMapper.readValue(serializedJson, IRequest.class);

      assertSame(request.getPlayer().getId(), deserializedRequest.getPlayer().getId());
      assertEquals(
          request.getPlayer().getResourceManager(),
          deserializedRequest.getPlayer().getResourceManager());

      assertSame(request.getType(), deserializedRequest.getType());
    }
  }
}
