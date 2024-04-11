package service;

import dao.TrainingRepository;
import dao.TrainingRepositoryImpl;
import dao.TrainingTypeRepository;
import dao.TrainingTypeRepositoryImpl;
import exception.DaoException;
import exception.IllegalTextException;
import in.dto.Request;
import model.DefaultUser;
import model.Training;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainingServiceImpl implements TrainingService {

    TrainingTypeRepository trainingTypeRepository = new TrainingTypeRepositoryImpl();
    TrainingRepository trainingRepository = new TrainingRepositoryImpl();

    String VALIDATE_TRAINING_EXCEPTION = """
            Вы ошиблись во вводимых данных. Команда для добавления тренировки
             "createtr <дд.мм.гг> <вид_тренировки> <мм> <количество_калорий> <иныеданные=данные>"
             При этом <дд.мм.гг> - это дата тренировки (учтите, что в один день может быть лишь одна тренировка
             чч:мм - это время тренировки, например "120" (в минутах), вид_тренировки - вид тренировки (в том числе,
             первые три буквы существующего вида тренировки)
             количество калорий - целое число, иныеданные=данные - это иные параметры тренировки (например дистанция). 
             В данном случае количество километров вводится как "дистанция=100км". Обязателен знак "равно" при наличии значения.
             Значения может не быть, тогда знак "равно" не нужен. Иных данных может быть любое количество.
             Пример createtr 10.01.24 бег 120 1000 дистанция=200 устал=сильно "место тренировки=площадка"
             Если дата - сегодня, то ставьте вместо даты точку (.)            
             """;

    @Override
    public String createTraining(Request request) {

        Training training = mapRequestToTraining(request);
        trainingRepository.save(training);

        return "Тренировка сохранена";
    }

    private Training mapRequestToTraining(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            LocalDate date = strings[0].equals(".") ? LocalDate.now() : LocalDate.parse(strings[0], formatter);
            String trainingType = getTrainingTypeOrSaveNew(strings[1], request.user());

            Duration duration = Duration.ofMinutes(Long.parseLong(strings[2]));
            int calories = Integer.valueOf(strings[3]);
            Map<String, String> othersMap = new HashMap<>();

            if (strings.length > 4) {
                String[] others = Arrays.copyOfRange(strings, 4, strings.length);
                for (String part : others) {
                    String[] keyValue = part.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        othersMap.put(key, value);
                    } else {
                        throw new Exception();
                    }
                }
            }
            return new Training(date, request.user(), trainingType, duration, calories, othersMap);
        } catch (Exception e) {
            throw new IllegalTextException(VALIDATE_TRAINING_EXCEPTION);
        }
    }

    private String getTrainingTypeOrSaveNew(String trainingType, DefaultUser user) {
        String type;
        try {
            type = trainingTypeRepository
                    .getTrainingTypeByShortNameAndUser(trainingType, user).orElseThrow();
        } catch (Exception e) {
            type = trainingTypeRepository.save(trainingType, user);
        }
        return type;
    }

    @Override
    public String updateTraining(Request request) {
        Training training = mapRequestToTraining(request);
        Set<Training> allTrainings = trainingRepository.findAllByUser(request.user());
        if (!allTrainings.contains(training)) {
            throw new  DaoException("Тренировка для изменения за эту дату не найдена");
        }
        trainingRepository.save(training);
        return "Тренировка сохранена";
    }

    @Override
    public String deleteTraining(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            date = LocalDate.parse(strings[0], formatter);;
        } catch (Exception e) {
            throw new IllegalTextException ("Запрос должен быть вида deletetr <дд.мм.гг> ");
        }
        trainingRepository.deleteByTrainingDateAndUser(date, request.user());
        return "Тренировка удалена";
    }

    @Override
    public String getTraining(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            date = LocalDate.parse(strings[0], formatter);
        } catch (Exception e) {
            throw new IllegalTextException ("Запрос должен быть вида gettr <дд.мм.гг> ");
        }
        return trainingRepository.getTrainingByTrainingDateAndUser(date, request.user())
                .orElseThrow(()->new DaoException("Тренировки не обнаружено")).toString();
    }

    @Override
    public String getTrainings(Request request) {
        String collect = trainingRepository.findAllByUser(request.user()).stream().map(Training::toString)
                .collect(Collectors.joining("\n"));
        return collect;
    }
}
