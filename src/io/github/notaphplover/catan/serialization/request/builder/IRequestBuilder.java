package io.github.notaphplover.catan.serialization.request.builder;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.RequestType;

public interface IRequestBuilder {

  BoardElementType getBoardElementType();

  ConnectionType getConnectionType();

  IPlayer getPlayer();

  StructureType getStructureType();

  RequestType getType();

  int getX();

  int getY();

  void setBoardElementType(BoardElementType type);

  void setConnectionType(ConnectionType type);

  void setPlayer(IPlayer player);

  void setStructureType(StructureType type);

  void setType(RequestType type);

  void setX(int x);

  void setY(int y);
}
