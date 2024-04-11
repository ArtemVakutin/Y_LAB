package in.dto;

import model.DefaultUser;
import session.Operation;

public record Request(String requestBody, String operation, DefaultUser user) {
}