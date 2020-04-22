package io.notaphplover.catan.jade.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.github.notaphplover.catan.core.request.IRequest;

public interface IObjectSerialializationManager {

  IRequest deserializeRequest(String serializedRequest)
      throws JsonMappingException, JsonProcessingException;

  String serializeRequest(IRequest request) throws JsonProcessingException;
}
