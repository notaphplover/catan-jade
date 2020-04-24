package io.notaphplover.catan.jade.serialization.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;
import java.io.IOException;

public class StructureRelatedRequestSerializer
    extends BuildElementRequestSerializer<IStructureRelatedRequest> {

  private static final long serialVersionUID = 2310467887012961353L;

  @Override
  protected void innertSerializeContent(
      IStructureRelatedRequest value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    super.innertSerializeContent(value, gen, provider);

    gen.writeStringField(RequestFields.FIELD_STRUCTURE_TYPE, value.getStructureType().toString());
  }
}
