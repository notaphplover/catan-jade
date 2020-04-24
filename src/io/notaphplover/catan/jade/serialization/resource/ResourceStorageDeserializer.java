package io.notaphplover.catan.jade.serialization.resource;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;

public class ResourceStorageDeserializer extends BaseResourceStorageDeserializer<IResourceStorage> {

  private static final long serialVersionUID = -5835512299306787421L;

  public ResourceStorageDeserializer(Class<? extends IResourceStorage> vc) {
    super(vc);
  }

  @Override
  protected IResourceStorage createResourceStorage(Map<ResourceType, Integer> resources) {
    return new ResourceStorage(resources);
  }
}
