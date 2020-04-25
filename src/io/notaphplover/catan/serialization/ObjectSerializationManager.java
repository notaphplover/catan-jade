package io.notaphplover.catan.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.notaphplover.catan.serialization.command.CommandDeserializer;
import io.notaphplover.catan.serialization.command.CommandSerializer;
import io.notaphplover.catan.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.serialization.player.PlayerSerializer;
import io.notaphplover.catan.serialization.request.BuildConnectionRequestSerializer;
import io.notaphplover.catan.serialization.request.RequestDeserializer;
import io.notaphplover.catan.serialization.request.RequestSerializer;
import io.notaphplover.catan.serialization.request.RequestTypeMap;
import io.notaphplover.catan.serialization.request.StructureRelatedRequestSerializer;
import io.notaphplover.catan.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.serialization.resource.ResourceStorageDeserializer;
import io.notaphplover.catan.serialization.resource.ResourceStorageSerializer;

public class ObjectSerializationManager implements IObjectSerialializationManager {

  private ObjectMapper objectMapper;

  @SuppressWarnings("unchecked")
  public ObjectSerializationManager() {

    objectMapper =
        new ObjectMapper()
            .registerModule(
                new SimpleModule()
                    .addDeserializer(ICommand.class, new CommandDeserializer(ICommand.class))
                    .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
                    .addDeserializer(IRequest.class, new RequestDeserializer(IRequest.class))
                    .addDeserializer(
                        IResourceManager.class,
                        new ResourceManagerDeserializer(IResourceManager.class))
                    .addDeserializer(
                        IResourceStorage.class,
                        new ResourceStorageDeserializer(IResourceStorage.class))
                    .addSerializer(ICommand.class, new CommandSerializer())
                    .addSerializer(IPlayer.class, new PlayerSerializer())
                    .addSerializer(
                        (Class<IBuildConnectionRequest>)
                            RequestTypeMap.getClassOfType(RequestType.BUILD_CONNECTION),
                        new BuildConnectionRequestSerializer())
                    .addSerializer(
                        (Class<IBuildConnectionRequest>)
                            RequestTypeMap.getClassOfType(RequestType.BUILD_INITIAL_CONNECTION),
                        new BuildConnectionRequestSerializer())
                    .addSerializer(
                        (Class<IStructureRelatedRequest>)
                            RequestTypeMap.getClassOfType(RequestType.BUILD_INITIAL_STRUCTURE),
                        new StructureRelatedRequestSerializer())
                    .addSerializer(
                        (Class<IEndTurnRequest>)
                            RequestTypeMap.getClassOfType(RequestType.END_TURN),
                        new RequestSerializer())
                    .addSerializer(
                        (Class<IStartTurnRequest>)
                            RequestTypeMap.getClassOfType(RequestType.START_TURN),
                        new RequestSerializer())
                    .addSerializer(
                        (Class<IStructureRelatedRequest>)
                            RequestTypeMap.getClassOfType(RequestType.BUILD_STRUCTURE),
                        new StructureRelatedRequestSerializer())
                    .addSerializer(
                        (Class<IStructureRelatedRequest>)
                            RequestTypeMap.getClassOfType(RequestType.UPGRADE_STRUCTURE),
                        new StructureRelatedRequestSerializer())
                    .addSerializer(IResourceStorage.class, new ResourceStorageSerializer()));
  }

  @Override
  public ICommand deserializeCommand(String serializedCommand)
      throws JsonMappingException, JsonProcessingException {
    return objectMapper.readValue(serializedCommand, ICommand.class);
  }

  @Override
  public IRequest deserializeRequest(String serializedRequest)
      throws JsonMappingException, JsonProcessingException {
    return objectMapper.readValue(serializedRequest, IRequest.class);
  }

  @Override
  public String serializeCommand(ICommand command) throws JsonProcessingException {
    return objectMapper.writeValueAsString(command);
  }

  @Override
  public String serializeRequest(IRequest request) throws JsonProcessingException {
    return objectMapper.writeValueAsString(request);
  }
}
