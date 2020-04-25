package io.notaphplover.catan.serialization.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.BuildStructureRequest;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("StructureRelatedRequest serialization test")
public class StructureRelatedRequestSerializationTest {

  @DisplayName("StructureRelatedRequest serialization test")
  @Nested
  class Serialization extends BuildElementRequestTestProvider<IStructureRelatedRequest> {

    private final StructureType TEST_STRUCTURE_TYPE = StructureType.SETTLEMENT;

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
    protected IBuildStructureRequest buildRequest() {
      return new BuildStructureRequest(
          new Player(0, new ResourceManager()), TEST_STRUCTURE_TYPE, 2, 5);
    }

    @Override
    protected JsonSerializer<IStructureRelatedRequest> buildRequestSerializer() {
      return new StructureRelatedRequestSerializer();
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
          RequestFields.FIELD_STRUCTURE_TYPE,
          TEST_STRUCTURE_TYPE);
    }

    @Override
    protected BoardElementType getSerializedRequestBoardElementType() {
      return BoardElementType.STRUCTURE;
    }

    @Override
    protected IPlayer getSerializedRequestPlayer() {
      return new Player(1, new ResourceManager());
    }

    @Override
    protected RequestType getSerializedRequestType() {
      return RequestType.BUILD_STRUCTURE;
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
    protected Class<IStructureRelatedRequest> getSerializerType() {
      return IStructureRelatedRequest.class;
    }

    @Override
    protected void itMustSerializeARequestAssertions(
        IStructureRelatedRequest expected, IStructureRelatedRequest actual) {
      super.itMustSerializeARequestAssertions(expected, actual);

      assertSame(expected.getStructureType(), actual.getStructureType());
    }
  }
}
