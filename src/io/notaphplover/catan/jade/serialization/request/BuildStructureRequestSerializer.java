package io.notaphplover.catan.jade.serialization.request;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.github.notaphplover.catan.core.request.IBuildStructureRequest;

public class BuildStructureRequestSerializer extends BuildElementRequestSerializer<IBuildStructureRequest> {

  private static final long serialVersionUID = 843134170593090698L;

  @Override
  protected void innertSerializeContent(IBuildStructureRequest value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    super.innertSerializeContent(value, gen, provider);

    gen.writeStringField(RequestFields.FIELD_STRUCTURE_TYPE, value.getStructureType().toString());
  }
}