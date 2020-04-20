package io.notaphplover.catan.jade.serialization.player.builder;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import java.util.Collection;

public interface IPlayerBuilder {

  int getId();

  IResourceManager getResourceManager();

  Collection<IRequest> getMissing();

  void setId(int id);

  void setMissing(Collection<IRequest> missing);

  void setResourceManager(IResourceManager resourceManager);
}
