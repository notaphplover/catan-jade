package io.github.notaphplover.catan.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.request.IRequest;

public interface IObjectSerialializationManager {

  ICommand deserializeCommand(String serializedCommand)
      throws JsonMappingException, JsonProcessingException;

  IRequest deserializeRequest(String serializedRequest)
      throws JsonMappingException, JsonProcessingException;

  String serializeCommand(ICommand command) throws JsonProcessingException;

  String serializeRequest(IRequest request) throws JsonProcessingException;
}
