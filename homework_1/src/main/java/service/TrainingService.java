package service;

import in.dto.Request;
import in.dto.Response;

public interface TrainingService {
    String createTraining(Request request);
    String updateTraining(Request request);
    String deleteTraining(Request request);
    String getTraining(Request request);
    String getTrainings(Request request);
}
