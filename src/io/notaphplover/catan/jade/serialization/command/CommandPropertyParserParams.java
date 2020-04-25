package io.notaphplover.catan.jade.serialization.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import io.notaphplover.catan.jade.serialization.command.builder.ICommandBuilder;

public class CommandPropertyParserParams implements ICommandPropertyParserParams {

  private ICommandBuilder builder;

  private DeserializationContext context;

  private JsonProcessingException exception;

  private JsonParser parser;

  public CommandPropertyParserParams(ICommandBuilder builder, DeserializationContext context, JsonParser parser) {

    this.builder = builder;
    this.context = context;
    this.parser = parser;
  }

  @Override
  public ICommandBuilder getBuilder() {
    return builder;
  }

  @Override
  public DeserializationContext getContext() {
    return context;
  }

  @Override
  public JsonProcessingException getException() {
    return exception;
  }

  @Override
  public JsonParser getParser() {
    return parser;
  }

  @Override
  public void setException(JsonProcessingException exception) {
    this.exception = exception;
  }

}