package service;

import in.dto.Request;

public interface AuthenticationService {
    String authenticate(Request request);

    String logout();

    String authByUser(Request request);
}
