package io.notaphplover.catan.serialization.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.notaphplover.catan.serialization.player.PlayerDeserializer;
import io.notaphplover.catan.serialization.player.PlayerSerializer;
import io.notaphplover.catan.serialization.resource.ResourceManagerDeserializer;
import io.notaphplover.catan.serialization.resource.ResourceStorageSerializer;

public abstract class BuildElementRequestTestProvider<R extends IBuildElementRequest> {

  public void itMustDeserialializeARequest() throws JsonProcessingException {

    String serializedRequest = buildSerializedRequest();

    ObjectMapper objectMapper = buildObjectMapper();

    @SuppressWarnings("unchecked")
    R deserializedRequest = (R) objectMapper.readValue(serializedRequest, IRequest.class);

    itMustDeserializeARequestAssertions(deserializedRequest);
  }

  public void itMustSerializeARequest() throws JsonProcessingException {

    R request = buildRequest();

    ObjectMapper objectMapper = buildObjectMapper();

    String serializedRequest = objectMapper.writeValueAsString(request);

    @SuppressWarnings("unchecked")
    R deserializedRequest = (R) objectMapper.readValue(serializedRequest, IRequest.class);

    itMustSerializeARequestAssertions(request, deserializedRequest);
  }

  protected abstract R buildRequest();

  protected abstract JsonSerializer<R> buildRequestSerializer();

  protected abstract String buildSerializedRequest() throws JsonProcessingException;

  protected abstract BoardElementType getSerializedRequestBoardElementType();

  protected abstract IPlayer getSerializedRequestPlayer();

  protected abstract RequestType getSerializedRequestType();

  protected abstract int getSerializedRequestX();

  protected abstract int getSerializedRequestY();

  protected abstract Class<R> getSerializerType();

  protected ObjectMapper buildObjectMapper() {

    return new ObjectMapper()
        .registerModule(
            new SimpleModule()
                .addSerializer(IPlayer.class, new PlayerSerializer())
                .addSerializer(getSerializerType(), buildRequestSerializer())
                .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
                .addDeserializer(IPlayer.class, new PlayerDeserializer(IPlayer.class))
                .addDeserializer(IRequest.class, new RequestDeserializer(IRequest.class))
                .addDeserializer(
                    IResourceManager.class,
                    new ResourceManagerDeserializer(IResourceManager.class)));
  }

  protected String getSerializedRequestPlayerSerialized() throws JsonProcessingException {

    return new ObjectMapper()
        .registerModule(
            new SimpleModule()
                .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
                .addSerializer(IPlayer.class, new PlayerSerializer()))
        .writeValueAsString(getSerializedRequestPlayer());
  }

  protected void itMustDeserializeARequestAssertions(R actual) {

    IPlayer expectedPlayer = getSerializedRequestPlayer();

    assertSame(getSerializedRequestType(), actual.getType());
    assertSame(expectedPlayer.getId(), actual.getPlayer().getId());
    assertEquals(expectedPlayer.getResourceManager(), actual.getPlayer().getResourceManager());
    assertSame(getSerializedRequestBoardElementType(), actual.getElementType());
    assertSame(getSerializedRequestType(), actual.getType());
    assertSame(getSerializedRequestX(), actual.getX());
    assertSame(getSerializedRequestY(), actual.getY());
  }

  protected void itMustSerializeARequestAssertions(R expected, R actual) {

    assertSame(expected.getType(), actual.getType());
    assertSame(expected.getPlayer().getId(), actual.getPlayer().getId());
    assertEquals(
        expected.getPlayer().getResourceManager(), actual.getPlayer().getResourceManager());
    assertSame(expected.getElementType(), actual.getElementType());
    assertSame(expected.getX(), actual.getX());
    assertSame(expected.getY(), actual.getY());
  }
}
