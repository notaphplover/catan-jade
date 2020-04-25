package io.github.notaphplover.catan.serialization.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ResourceStorageDeserializerTest")
public class ResourceStorageDeserializerTest {

  @Nested
  @DisplayName("ResourceStorageDeserializer deserialization")
  class Deserialization {

    @DisplayName("It must deserialize a JSON resources")
    @Test
    public void itMustDeserializeAJSONResource()
        throws JsonMappingException, JsonProcessingException {

      Map<ResourceType, Integer> resourcesMap = new TreeMap<>();

      resourcesMap.put(ResourceType.BRICK, 1);
      resourcesMap.put(ResourceType.GRAIN, 2);
      resourcesMap.put(ResourceType.LUMBER, 3);
      resourcesMap.put(ResourceType.ORE, 4);
      resourcesMap.put(ResourceType.WOOL, 5);

      String serializedResources =
          String.format(
              "{ \"%s\": %d, \"%s\": %d, \"%s\": %d, \"%s\": %d, \"%s\": %d }",
              ResourceType.BRICK.toString(),
              resourcesMap.get(ResourceType.BRICK),
              ResourceType.GRAIN.toString(),
              resourcesMap.get(ResourceType.GRAIN),
              ResourceType.LUMBER.toString(),
              resourcesMap.get(ResourceType.LUMBER),
              ResourceType.ORE.toString(),
              resourcesMap.get(ResourceType.ORE),
              ResourceType.WOOL.toString(),
              resourcesMap.get(ResourceType.WOOL));

      ObjectMapper objectMapper = new ObjectMapper();

      SimpleModule module = new SimpleModule();
      module.addDeserializer(
          IResourceStorage.class, new ResourceStorageDeserializer(IResourceStorage.class));
      objectMapper.registerModule(module);

      assertEquals(
          new ResourceStorage(resourcesMap),
          objectMapper.readValue(serializedResources, IResourceStorage.class));
    }
  }
}
