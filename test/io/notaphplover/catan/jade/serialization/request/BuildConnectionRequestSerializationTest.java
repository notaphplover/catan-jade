package io.notaphplover.catan.jade.serialization.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.BuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("BuildConnectionRequest serialization test")
public class BuildConnectionRequestSerializationTest {

  @DisplayName("BuildConnectionRequest serialization test")
  @Nested
  class Serialization extends BuildElementRequestTestProvider<IBuildConnectionRequest> {

    private final ConnectionType TEST_CONNECTION_TYPE = ConnectionType.ROAD;

    @Override
    @Test
    public void itMustDeserialializeARequest() throws JsonProcessingException {
      super.itMustDeserialializeARequest();
    }

    @Override
    @Test
    public void itMustSerializeARequest() throws JsonProcessingException {
      super.itMustSerializeARequest();
    }

    @Override
    protected IBuildConnectionRequest buildRequest() {
      return new BuildConnectionRequest(
          new Player(0, new ResourceManager()), TEST_CONNECTION_TYPE, 2, 5);
    }

    @Override
    protected JsonSerializer<IBuildConnectionRequest> buildRequestSerializer() {
      return new BuildConnectionRequestSerializer();
    }

    @Override
    protected String buildSerializedRequest() throws JsonProcessingException {
      return String.format(
          "{ \"%s\": \"%s\", \"%s\": %s, \"%s\": \"%s\", \"%s\": %d, \"%s\": %d, \"%s\": \"%s\" }",
          RequestFields.FIELD_TYPE,
          getSerializedRequestType(),
          RequestFields.FIELD_PLAYER,
          getSerializedRequestPlayerSerialized(),
          RequestFields.FIELD_ELEMENT_TYPE,
          getSerializedRequestBoardElementType().toString(),
          RequestFields.FIELD_X,
          getSerializedRequestX(),
          RequestFields.FIELD_Y,
          getSerializedRequestY(),
          RequestFields.FIELD_CONNECTION_TYPE,
          TEST_CONNECTION_TYPE);
    }

    @Override
    protected BoardElementType getSerializedRequestBoardElementType() {
      return BoardElementType.CONNECTION;
    }

    @Override
    protected IPlayer getSerializedRequestPlayer() {
      return new Player(1, new ResourceManager());
    }

    @Override
    protected RequestType getSerializedRequestType() {
      return RequestType.BUILD_CONNECTION;
    }

    @Override
    protected int getSerializedRequestX() {
      return 1;
    }

    @Override
    protected int getSerializedRequestY() {
      return 2;
    }

    @Override
    protected Class<IBuildConnectionRequest> getSerializerType() {
      return IBuildConnectionRequest.class;
    }

    @Override
    protected void itMustSerializeARequestAssertions(
        IBuildConnectionRequest expected, IBuildConnectionRequest actual) {
      super.itMustSerializeARequestAssertions(expected, actual);

      assertSame(expected.getConnectionType(), actual.getConnectionType());
    }
  }
}
