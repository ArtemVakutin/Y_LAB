package model;

import session.Operation;
import session.OperationStatus;

import java.time.LocalDateTime;

public record Audit (DefaultUser user, String operation, OperationStatus operationStatus, LocalDateTime dateTime){
}
