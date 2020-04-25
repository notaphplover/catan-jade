package io.github.notaphplover.catan.serialization.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.io.IOException;

public class ResourceStorageSerializer extends StdSerializer<IResourceStorage> {

  private static final long serialVersionUID = 1506222424790300208L;

  public ResourceStorageSerializer() {
    super((Class<IResourceStorage>) null);
  }

  @Override
  public void serialize(IResourceStorage value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();

    for (ResourceType type : ResourceType.values()) {
      gen.writeNumberField(type.toString(), value.getResource(type));
    }

    gen.writeEndObject();
  }
}
