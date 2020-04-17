package io.notaphplover.catan.jade.serialization.resource;

import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;

public class ResourceStorageDeserializer extends BaseResourceStorageDeserializer<ResourceStorage> {

  private static final long serialVersionUID = -5835512299306787421L;

  public ResourceStorageDeserializer(Class<? extends ResourceStorage> vc) {
    super(vc);
  }

  @Override
  protected ResourceStorage createResourceStorage(Map<ResourceType, Integer> resources) {
    return new ResourceStorage(resources);
  }
}
