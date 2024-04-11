package service;

import in.dto.Request;
import in.dto.Response;

public interface RegistrationService {
    String registerUser(Request request);
    String getAllUsers(Request request);
}
