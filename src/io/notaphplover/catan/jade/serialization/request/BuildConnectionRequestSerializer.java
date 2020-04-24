package io.notaphplover.catan.jade.serialization.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import java.io.IOException;

public class BuildConnectionRequestSerializer
    extends BuildElementRequestSerializer<IBuildConnectionRequest> {

  private static final long serialVersionUID = -6738928717718118532L;

  @Override
  protected void innertSerializeContent(
      IBuildConnectionRequest value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    super.innertSerializeContent(value, gen, provider);

    gen.writeStringField(RequestFields.FIELD_CONNECTION_TYPE, value.getConnectionType().toString());
  }
}
