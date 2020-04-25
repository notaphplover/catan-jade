package io.github.notaphplover.catan.serialization.request;

import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.request.IUpgradeStructureRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.request.trade.ITradeAgreementRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeConfirmationRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeDiscardRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeRequest;

public class RequestTypeMap {

  public static Class<? extends IRequest> getClassOfType(RequestType type) {
    switch (type) {
      case BUILD_CONNECTION:
      case BUILD_INITIAL_CONNECTION:
        return IBuildConnectionRequest.class;
      case BUILD_INITIAL_STRUCTURE:
      case BUILD_STRUCTURE:
        return IBuildStructureRequest.class;
      case END_TURN:
        return IEndTurnRequest.class;
      case START_TURN:
        return IStartTurnRequest.class;
      case TRADE:
        return ITradeRequest.class;
      case TRADE_AGREEMENT:
        return ITradeAgreementRequest.class;
      case TRADE_CONFIRMATION:
        return ITradeConfirmationRequest.class;
      case TRADE_DISCARD:
        return ITradeDiscardRequest.class;
      case UPGRADE_STRUCTURE:
        return IUpgradeStructureRequest.class;
      default:
        return IRequest.class;
    }
  }
}
