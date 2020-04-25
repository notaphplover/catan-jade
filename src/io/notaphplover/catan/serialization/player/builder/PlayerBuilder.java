package io.notaphplover.catan.serialization.player.builder;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import java.util.Collection;

public class PlayerBuilder implements IPlayerBuilder {

  private int id;

  private Collection<IRequest> missingRequests;

  private IResourceManager resourceManager;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public IResourceManager getResourceManager() {
    return resourceManager;
  }

  @Override
  public Collection<IRequest> getMissing() {
    return missingRequests;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setMissing(Collection<IRequest> missing) {
    missingRequests = missing;
  }

  @Override
  public void setResourceManager(IResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }
}
