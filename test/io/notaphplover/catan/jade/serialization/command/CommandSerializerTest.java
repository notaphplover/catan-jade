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
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.notaphplover.catan.jade.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.jade.serialization.player.PlayerSerializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceStorageSerializer;

@DisplayName("Command serializer")
public class CommandSerializerTest {

  @Nested
  @DisplayName("Command serialization")
  class Serialization {
    
    @DisplayName("It must serialize a request")
    @Test
    public void itMustSerializeARequest() throws JsonProcessingException {
      IPlayer player = new Player(1, new ResourceManager());
      Command command = new Command(player, CommandType.DIE);

      ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(
          new SimpleModule()
            .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
            .addDeserializer(IResourceManager.class, new ResourceManagerDeserializer(IResourceManager.class))
            .addDeserializer(ICommand.class, new CommandDeserializer(ICommand.class))
            .addSerializer(IPlayer.class, new PlayerSerializer())
            .addSerializer(ICommand.class, new CommandSerializer())
            .addSerializer(IResourceStorage.class, new ResourceStorageSerializer())
        );

      String serializedCommand = objectMapper.writeValueAsString(command);

      ICommand deserializedCommand = (ICommand)objectMapper.readValue(serializedCommand, ICommand.class);

      assertSame(command.getType(), deserializedCommand.getType());
      assertSame(command.getDestinatary().getId(), deserializedCommand.getDestinatary().getId());
      assertEquals(command.getDestinatary().getResourceManager(), deserializedCommand.getDestinatary().getResourceManager());
    }
  }
}