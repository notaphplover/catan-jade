package io.notaphplover.catan.jade.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.BuildConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialStructureRequest;
import io.github.notaphplover.catan.core.request.BuildStructureRequest;
import io.github.notaphplover.catan.core.request.EndTurnRequest;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import io.github.notaphplover.catan.core.request.IUpgradeStructureRequest;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.github.notaphplover.catan.core.request.UpgradeStructureRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.notaphplover.catan.jade.serialization.player.PlayerSerializer;
import io.notaphplover.catan.jade.serialization.request.RequestFields;
import io.notaphplover.catan.jade.serialization.request.RequestTypeMap;
import io.notaphplover.catan.jade.serialization.resource.ResourceStorageSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ObjectSerializationManager tests")
public class ObjectSerializationManagerTest {

  private void serializationAssert(IRequest expected, IRequest actual)
      throws JsonProcessingException {

    Class<? extends IRequest> mappedClass = RequestTypeMap.getClassOfType(expected.getType());

    assertTrue(mappedClass.isAssignableFrom(actual.getClass()));
    assertSame(expected.getPlayer().getId(), actual.getPlayer().getId());
    assertEquals(
        expected.getPlayer().getResourceManager(), actual.getPlayer().getResourceManager());
    assertSame(expected.getType(), actual.getType());
  }

  private void serializationAssert(IBuildElementRequest expected, IBuildElementRequest actual)
      throws JsonProcessingException {
    serializationAssert((IRequest) expected, (IRequest) actual);

    assertSame(expected.getElementType(), actual.getElementType());
    assertSame(expected.getX(), actual.getX());
    assertSame(expected.getY(), actual.getY());
  }

  private void serializationAssert(IBuildConnectionRequest expected, IBuildConnectionRequest actual)
      throws JsonProcessingException {
    serializationAssert((IBuildElementRequest) expected, (IBuildElementRequest) actual);

    assertSame(expected.getConnectionType(), actual.getConnectionType());
  }

  private void serializationAssert(
      IStructureRelatedRequest expected, IStructureRelatedRequest actual)
      throws JsonProcessingException {
    serializationAssert((IBuildElementRequest) expected, (IBuildElementRequest) actual);

    assertSame(expected.getStructureType(), actual.getStructureType());
  }

  @DisplayName("ObjectSerializationManager.deserializeRequest tests")
  @Nested
  class DeserializeRequest {

    @DisplayName("It must deserialize a build connection request")
    @Test
    public void itMustDeserializeABuildConnectionRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IBuildConnectionRequest request =
          new BuildConnectionRequest(player, ConnectionType.ROAD, 6, 2);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player),
              RequestFields.FIELD_ELEMENT_TYPE,
              request.getElementType().toString(),
              RequestFields.FIELD_X,
              request.getX(),
              RequestFields.FIELD_Y,
              request.getY(),
              RequestFields.FIELD_CONNECTION_TYPE,
              request.getConnectionType());
      ;

      IBuildConnectionRequest deserializedRequest =
          (IBuildConnectionRequest)
              new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize a build initial connection request")
    @Test
    public void itMustDeserializeABuildInitialConnectionRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IBuildConnectionRequest request =
          new BuildInitialConnectionRequest(player, ConnectionType.ROAD, 6, 2);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player),
              RequestFields.FIELD_ELEMENT_TYPE,
              request.getElementType().toString(),
              RequestFields.FIELD_X,
              request.getX(),
              RequestFields.FIELD_Y,
              request.getY(),
              RequestFields.FIELD_CONNECTION_TYPE,
              request.getConnectionType());

      IBuildConnectionRequest deserializedRequest =
          (IBuildConnectionRequest)
              new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize a build initial structure request")
    @Test
    public void itMustDeserializeABuildInitialStructureRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IBuildStructureRequest request =
          new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, 6, 2);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player),
              RequestFields.FIELD_ELEMENT_TYPE,
              request.getElementType().toString(),
              RequestFields.FIELD_X,
              request.getX(),
              RequestFields.FIELD_Y,
              request.getY(),
              RequestFields.FIELD_STRUCTURE_TYPE,
              request.getStructureType());

      IBuildStructureRequest deserializedRequest =
          (IBuildStructureRequest)
              new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize a build structure request")
    @Test
    public void itMustDeserializeABuildStructureRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IBuildStructureRequest request =
          new BuildStructureRequest(player, StructureType.SETTLEMENT, 6, 2);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player),
              RequestFields.FIELD_ELEMENT_TYPE,
              request.getElementType().toString(),
              RequestFields.FIELD_X,
              request.getX(),
              RequestFields.FIELD_Y,
              request.getY(),
              RequestFields.FIELD_STRUCTURE_TYPE,
              request.getStructureType());

      IBuildStructureRequest deserializedRequest =
          (IBuildStructureRequest)
              new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize a start turn request")
    @Test
    public void itMustDeserializeAStartTurnRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IRequest request = new StartTurnRequest(player);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player));

      IRequest deserializedRequest =
          (IRequest) new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize an end turn request")
    @Test
    public void itMustDeserializeAnEndTurnRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IRequest request = new EndTurnRequest(player);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player));

      IRequest deserializedRequest =
          (IRequest) new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    @DisplayName("It must deserialize an upgrade structure request")
    @Test
    public void itMustDeserializeAnUpgradeStructureRequest() throws JsonProcessingException {

      IPlayer player = new Player(1, new ResourceManager());

      IUpgradeStructureRequest request =
          new UpgradeStructureRequest(player, StructureType.SETTLEMENT, 6, 2);

      String serializedRequest =
          String.format(
              "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
              RequestFields.FIELD_TYPE,
              request.getType().toString(),
              RequestFields.FIELD_PLAYER,
              getSerializedRequestPlayerSerialized(player),
              RequestFields.FIELD_ELEMENT_TYPE,
              request.getElementType().toString(),
              RequestFields.FIELD_X,
              request.getX(),
              RequestFields.FIELD_Y,
              request.getY(),
              RequestFields.FIELD_STRUCTURE_TYPE,
              request.getStructureType());

      IUpgradeStructureRequest deserializedRequest =
          (IUpgradeStructureRequest)
              new ObjectSerializationManager().deserializeRequest(serializedRequest);

      serializationAssert(request, deserializedRequest);
    }

    protected String getSerializedRequestPlayerSerialized(IPlayer player)
        throws JsonProcessingException {

      return new ObjectMapper()
          .registerModule(
              new SimpleModule()
                  .addSerializer(IResourceManager.class, new ResourceStorageSerializer())
                  .addSerializer(IPlayer.class, new PlayerSerializer()))
          .writeValueAsString(player);
    }
  }

  @DisplayName("ObjectSerializationManager.serializeRequest tests")
  @Nested
  class SerializeRequest {

    @DisplayName("It must serialize a build connection request")
    @Test
    public void itMustSerializeABuildConnectionRequest() throws JsonProcessingException {
      IBuildConnectionRequest request =
          new BuildConnectionRequest(
              new Player(1, new ResourceManager()), ConnectionType.ROAD, 6, 2);

      IBuildConnectionRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize a build initial connection request")
    @Test
    public void itMustSerializeABuildInitialConnectionRequest() throws JsonProcessingException {
      IBuildConnectionRequest request =
          new BuildInitialConnectionRequest(
              new Player(1, new ResourceManager()), ConnectionType.ROAD, 6, 2);

      IBuildConnectionRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize a build initial structure request")
    @Test
    public void itMustSerializeABuildInitialStructureRequest() throws JsonProcessingException {
      IBuildStructureRequest request =
          new BuildInitialStructureRequest(
              new Player(1, new ResourceManager()), StructureType.SETTLEMENT, 6, 2);

      IBuildStructureRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize a build structure request")
    @Test
    public void itMustSerializeABuildStructureRequest() throws JsonProcessingException {
      IBuildStructureRequest request =
          new BuildStructureRequest(
              new Player(1, new ResourceManager()), StructureType.SETTLEMENT, 6, 2);

      IBuildStructureRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize an end turn request")
    @Test
    public void itMustSerializeAnEndTurnRequest() throws JsonProcessingException {
      IEndTurnRequest request = new EndTurnRequest(new Player(5, new ResourceManager()));

      IEndTurnRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize a start turn request")
    @Test
    public void itMustSerializeAStartTurnRequest() throws JsonProcessingException {
      IStartTurnRequest request = new StartTurnRequest(new Player(5, new ResourceManager()));

      IStartTurnRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    @DisplayName("It must serialize an upgrade structure request")
    @Test
    public void itMustSerializeAnUpgradeStructureRequest() throws JsonProcessingException {
      IUpgradeStructureRequest request =
          new UpgradeStructureRequest(
              new Player(1, new ResourceManager()), StructureType.SETTLEMENT, 6, 2);

      IUpgradeStructureRequest transformedRequest = serializeAndDeserialize(request);

      serializationAssert(request, transformedRequest);
    }

    private <T extends IRequest> T serializeAndDeserialize(T request)
        throws JsonProcessingException {
      ObjectSerializationManager serializationManager = new ObjectSerializationManager();

      String serializedRequest = serializationManager.serializeRequest(request);

      @SuppressWarnings("unchecked")
      T deserializedRequest = (T) serializationManager.deserializeRequest(serializedRequest);

      return deserializedRequest;
    }
  }
}
