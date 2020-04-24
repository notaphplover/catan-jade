package io.notaphplover.catan.jade.serialization.resource;

import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;

public class ResourceManagerDeserializer extends BaseResourceStorageDeserializer<IResourceManager> {

  private static final long serialVersionUID = 9008225905224378562L;

  public ResourceManagerDeserializer(Class<? extends IResourceManager> vc) {
    super(vc);
  }

  @Override
  protected IResourceManager createResourceStorage(Map<ResourceType, Integer> resources) {
    return new ResourceManager(resources);
  }
}
