package io.github.notaphplover.catan.serialization.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import io.github.notaphplover.catan.serialization.request.builder.IRequestBuilder;

public class RequestPropertyParserParams implements IRequestPropertyParserParams {

  private IRequestBuilder builder;

  private DeserializationContext context;

  private JsonProcessingException exception;

  private JsonParser parser;

  public RequestPropertyParserParams(
      IRequestBuilder builder, DeserializationContext context, JsonParser parser) {
    this.builder = builder;
    this.context = context;
    this.parser = parser;
  }

  @Override
  public IRequestBuilder getBuilder() {
    return builder;
  }

  @Override
  public DeserializationContext getContext() {
    return context;
  }

  @Override
  public JsonParser getParser() {
    return parser;
  }

  @Override
  public JsonProcessingException getException() {
    return exception;
  }

  @Override
  public void setException(JsonProcessingException exception) {
    this.exception = exception;
  }
}
