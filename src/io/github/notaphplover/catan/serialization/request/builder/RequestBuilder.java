package io.github.notaphplover.catan.serialization.request.builder;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.RequestType;

public class RequestBuilder implements IRequestBuilder {

  private BoardElementType boardElementType;

  private ConnectionType connectionType;

  private IPlayer player;

  private StructureType structureType;

  private RequestType type;

  private int x;

  private int y;

  @Override
  public BoardElementType getBoardElementType() {
    return boardElementType;
  }

  @Override
  public ConnectionType getConnectionType() {
    return connectionType;
  }

  @Override
  public IPlayer getPlayer() {
    return player;
  }

  @Override
  public StructureType getStructureType() {
    return structureType;
  }

  @Override
  public RequestType getType() {
    return type;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setBoardElementType(BoardElementType type) {
    boardElementType = type;
  }

  @Override
  public void setConnectionType(ConnectionType type) {
    connectionType = type;
  }

  @Override
  public void setPlayer(IPlayer player) {
    this.player = player;
  }

  @Override
  public void setStructureType(StructureType type) {
    structureType = type;
  }

  @Override
  public void setType(RequestType type) {
    this.type = type;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }
}
