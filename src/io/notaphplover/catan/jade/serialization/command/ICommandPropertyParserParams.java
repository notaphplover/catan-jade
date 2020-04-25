package io.notaphplover.catan.jade.serialization.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import io.notaphplover.catan.jade.serialization.command.builder.ICommandBuilder;

public interface ICommandPropertyParserParams {

  ICommandBuilder getBuilder();

  DeserializationContext getContext();

  JsonProcessingException getException();

  JsonParser getParser();

  void setException(JsonProcessingException exception);
}