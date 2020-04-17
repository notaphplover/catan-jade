package io.notaphplover.catan.jade.serialization.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@DisplayName("ResourceStorageSerializerTest")
public class ResourceStorageSerializerTest {

  @Nested
  @DisplayName("ResourceStorageTest serialization")
  class Serialization {

    @DisplayName("It must serialize its resources")
    @Test
    public void itMustSerializeItsResources() throws JsonProcessingException {

      Map<ResourceType, Integer> resourcesMap = new TreeMap<>();
      resourcesMap.put(ResourceType.WOOL, 1);
      resourcesMap.put(ResourceType.LUMBER, 2);

      ResourceStorage storage = new ResourceStorage(resourcesMap);

      ObjectMapper objectMapper = new ObjectMapper();

      SimpleModule module = new SimpleModule();
      module.addSerializer(IResourceStorage.class, new ResourceStorageSerializer());
      module.addDeserializer(
          IResourceStorage.class, new ResourceStorageDeserializer(ResourceStorage.class));
      objectMapper.registerModule(module);

      String serializedJson = objectMapper.writeValueAsString(storage);

      assertEquals(storage, objectMapper.readValue(serializedJson, IResourceStorage.class));
    }
  }
}
