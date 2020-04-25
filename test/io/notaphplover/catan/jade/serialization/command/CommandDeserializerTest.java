package io.notaphplover.catan.jade.serialization.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.notaphplover.catan.jade.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.jade.serialization.player.PlayerSerializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceStorageSerializer;

@DisplayName("CommandDeserializer tests")
public class CommandDeserializerTest {

  @DisplayName("CommandDeserializer deserialization")
  @Nested
  class Deserialization {

    @DisplayName("It must deserialize a command")
    @Test
    public void itMustDeserializeACommand() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());
      ICommand command = new Command(player, CommandType.SEND_NORMAL_REQUEST);

      ObjectMapper objectSerializer = new ObjectMapper().registerModule(new SimpleModule()
          .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
          .addSerializer(IPlayer.class, new PlayerSerializer()));

      String serializedPlayer = objectSerializer.writeValueAsString(player);

      String serializedEntity =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s }",
              CommandFields.FIELD_TYPE,
              command.getType().toString(),
              CommandFields.FIELD_DESTINATARY,
              serializedPlayer);
      
      ObjectMapper objectDeserializer = new ObjectMapper().registerModule(
          new SimpleModule()
              .addDeserializer(
                  IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class))
              .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
              .addDeserializer(ICommand.class, new CommandDeserializer(ICommand.class)));

      ICommand deserializedCommand = objectDeserializer.readValue(serializedEntity, ICommand.class);

      assertSame(command.getType(), deserializedCommand.getType());
      assertSame(command.getDestinatary().getId(), deserializedCommand.getDestinatary().getId());
      assertEquals(command.getDestinatary().getResourceManager(), deserializedCommand.getDestinatary().getResourceManager());
    }
  }
}
