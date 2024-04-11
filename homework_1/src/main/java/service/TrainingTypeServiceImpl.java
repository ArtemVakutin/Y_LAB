package service;

import dao.TrainingTypeRepository;
import dao.TrainingTypeRepositoryImpl;
import exception.IllegalTextException;
import in.dto.Request;
import model.DefaultUser;

public class TrainingTypeServiceImpl implements TrainingTypeService{

    TrainingTypeRepository trainingTypeRepository = new TrainingTypeRepositoryImpl();
    private final String VALIDATE_CREATE_EXCEPTION = """
            Выполните команду "create_type <название_тренировки>". Если название тренировки включает в себя пробелы
            заключите название в кавычки ("")""";

    private final String VALIDATE_DELETE_EXCEPTION = """
            Выполните команду "delete_type <название_тренировки>". Если название тренировки включает в себя пробелы
            заключите название в кавычки ("")""";

    @Override
    public String createTrainingType(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        if (strings.length>1) {
            throw new IllegalTextException(VALIDATE_CREATE_EXCEPTION);
        }
        trainingTypeRepository.save(strings[0], request.user());
        return "Вид тренировки успешно сохранен";
    }

    @Override
    public String deleteTrainingType(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        if (strings.length>1) {
            throw new IllegalTextException(VALIDATE_DELETE_EXCEPTION);
        }
        trainingTypeRepository.deleteByTrainingTypeAndUser(strings[0], request.user());
        return "Вид тренировки удален, однако старые тренировки сохранятся";
    }
}
