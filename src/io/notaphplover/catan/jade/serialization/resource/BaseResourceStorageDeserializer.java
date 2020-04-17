package io.notaphplover.catan.jade.serialization.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class BaseResourceStorageDeserializer<R extends IResourceStorage>
    extends StdDeserializer<R> {

  private static final long serialVersionUID = -3548401312218917887L;

  public BaseResourceStorageDeserializer(Class<? extends R> vc) {
    super(vc);
  }

  @Override
  public R deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    Map<ResourceType, Integer> resources = new TreeMap<>();

    JsonNode node = p.getCodec().readTree(p);

    node.fields()
        .forEachRemaining(
            (Entry<String, JsonNode> entry) -> {
              resources.put(ResourceType.valueOf(entry.getKey()), entry.getValue().asInt());
            });

    return this.createResourceStorage(resources);
  }

  protected abstract R createResourceStorage(Map<ResourceType, Integer> resources);
}
