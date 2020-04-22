package io.notaphplover.catan.jade.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.notaphplover.catan.jade.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.jade.serialization.player.PlayerSerializer;
import io.notaphplover.catan.jade.serialization.request.BuildConnectionRequestSerializer;
import io.notaphplover.catan.jade.serialization.request.RequestDeserializer;
import io.notaphplover.catan.jade.serialization.request.RequestSerializer;
import io.notaphplover.catan.jade.serialization.request.RequestTypeMap;
import io.notaphplover.catan.jade.serialization.request.StructureRelatedRequestSerializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceStorageDeserializer;
import io.notaphplover.catan.jade.serialization.resource.ResourceStorageSerializer;

public class ObjectSerializationManager implements IObjectSerialializationManager {

  private ObjectMapper objectMapper;

  @SuppressWarnings("unchecked")
  public ObjectSerializationManager() {

    objectMapper =
        new ObjectMapper()
            .registerModule(
                new SimpleModule()
                    .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
                    .addDeserializer(IRequest.class, new RequestDeserializer(IRequest.class))
                    .addDeserializer(
                        IResourceManager.class,
                        new ResourceManagerDeserializer(IResourceManager.class))
                    .addDeserializer(
                        IResourceStorage.class,
                        new ResourceStorageDeserializer(IResourceStorage.class))
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
  public String serializeRequest(IRequest request) throws JsonProcessingException {
    return objectMapper.writeValueAsString(request);
  }

  @Override
  public IRequest deserializeRequest(String serializedRequest)
      throws JsonMappingException, JsonProcessingException {
    return objectMapper.readValue(serializedRequest, IRequest.class);
  }
}
