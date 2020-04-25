package io.notaphplover.catan.jade.serialization.command.builder;

import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.player.IPlayer;

public class CommandBuilder implements ICommandBuilder {

  private IPlayer destinatary;

  private CommandType type;

  @Override
  public IPlayer getDestinatary() {
    return destinatary;
  }

  @Override
  public CommandType getType() {
    return type;
  }

  @Override
  public void setDestinatary(IPlayer destinatary) {
    this.destinatary = destinatary;
  }

  @Override
  public void setType(CommandType type) {
    this.type = type;
  }

}
