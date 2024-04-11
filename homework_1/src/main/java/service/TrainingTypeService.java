package service;

import in.dto.Request;
import in.dto.Response;

public interface TrainingTypeService {
    String createTrainingType(Request request);
    String deleteTrainingType(Request trainingType);
}
