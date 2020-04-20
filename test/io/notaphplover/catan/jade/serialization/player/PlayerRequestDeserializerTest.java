package io.notaphplover.catan.jade.serialization.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.notaphplover.catan.jade.serialization.resource.ResourceManagerDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("PlayerRequestDeserializer test")
public class PlayerRequestDeserializerTest {

  @DisplayName("PlayerRequestSerializer serialization")
  @Nested
  class Deserialization {

    @DisplayName("It must deserialize a player with no requests")
    @Test
    public void itMustSerializeAPlayerWithNoRequests() throws JsonProcessingException {

      int playerId = 1;

      String serializedPlayer =
          String.format(
              "{ \"%s\": %d, \"%s\": [], \"%s\": {} }",
              PlayerFields.FIELD_ID,
              playerId,
              PlayerFields.FIELD_REQUESTS,
              PlayerFields.FIELD_RESOURCES);

      ObjectMapper objectMapper = new ObjectMapper();

      SimpleModule module = new SimpleModule();
      module
          .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
          .addDeserializer(
              IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class));
      objectMapper.registerModule(module);

      IPlayer deserializedPlayer =
          (IPlayer) objectMapper.readValue(serializedPlayer, IPlayer.class);
      assertSame(playerId, deserializedPlayer.getId());
      assertEquals(new ResourceManager(), deserializedPlayer.getResourceManager());
    }
  }
}
