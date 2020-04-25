package io.github.notaphplover.catan.serialization.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import io.github.notaphplover.catan.serialization.request.builder.IRequestBuilder;

public interface IRequestPropertyParserParams {

  IRequestBuilder getBuilder();

  DeserializationContext getContext();

  JsonProcessingException getException();

  JsonParser getParser();

  void setException(JsonProcessingException exception);
}
