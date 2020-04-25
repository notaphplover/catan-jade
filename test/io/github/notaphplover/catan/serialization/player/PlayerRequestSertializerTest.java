package io.github.notaphplover.catan.serialization.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.serialization.resource.ResourceManagerDeserializer;
import io.github.notaphplover.catan.serialization.resource.ResourceStorageSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("PlayerRequestSerializer test")
public class PlayerRequestSertializerTest {

  @DisplayName("PlayerRequestSerializer serialization")
  @Nested
  class Serialization {

    @DisplayName("It must serialize a player with no requests")
    @Test
    public void itMustSerializeAPlayerWithNoRequests() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      ObjectMapper objectMapper = new ObjectMapper();

      SimpleModule module = new SimpleModule();
      module
          .addSerializer(IPlayer.class, new PlayerSerializer())
          .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
          .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
          .addDeserializer(
              IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class));
      objectMapper.registerModule(module);

      String serializedJson = objectMapper.writeValueAsString(player);

      IPlayer deserializedPlayer = (IPlayer) objectMapper.readValue(serializedJson, IPlayer.class);
      assertSame(player.getId(), deserializedPlayer.getId());
      assertEquals(player.getResourceManager(), deserializedPlayer.getResourceManager());
    }
  }
}
