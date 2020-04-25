package io.github.notaphplover.catan.serialization.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;
import java.io.IOException;

public abstract class BuildElementRequestSerializer<R extends IBuildElementRequest>
    extends BaseRequestSerializer<R> {

  private static final long serialVersionUID = 974630176460977772L;

  @Override
  protected void innertSerializeContent(R value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    super.innertSerializeContent(value, gen, provider);

    gen.writeStringField(RequestFields.FIELD_ELEMENT_TYPE, value.getElementType().toString());

    gen.writeNumberField(RequestFields.FIELD_X, value.getX());

    gen.writeNumberField(RequestFields.FIELD_Y, value.getY());
  }
}
