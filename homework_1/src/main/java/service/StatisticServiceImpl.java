package service;

import dao.TrainingRepository;
import dao.TrainingRepositoryImpl;
import dao.TrainingTypeRepository;
import dao.TrainingTypeRepositoryImpl;
import exception.DaoException;
import in.dto.Request;
import model.DefaultUser;
import model.Training;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class StatisticServiceImpl implements StatisticService{

    TrainingRepository trainingRepository = new TrainingRepositoryImpl();

    @Override
    public String getStatistic(Request request) {
        Set<Training> trainings = trainingRepository.findAllByUser(request.user());
        if (trainings.isEmpty()) {
            throw new DaoException("Тренировок для вывода статистики не найдено");
        }
        StringBuilder result = new StringBuilder();
        result.append("Минимальное количество калорий: ").append(minCalories(trainings)).append("\n");
        result.append("Максимальное количество калорий: ").append(maxCalories(trainings)).append("\n");
        result.append("Среднее количество калорий: ").append(averageCalories(trainings)).append("\n");
        result.append("Минимальная продолжительность тренировки: ").append(minDuration(trainings)).append("\n");
        result.append("Максимальная продолжительность тренировки: ").append(maxDuration(trainings)).append("\n");
        result.append("Средняя продолжительность тренировки: ").append(averageDuration(trainings)).append("\n");

        return result.toString();
    }

    public static int minCalories(Set<Training> trainings) {
        return trainings.stream()
                .mapToInt(Training::getTrainingCalories)
                .min()
                .orElse(0);
    }

    public static int maxCalories(Set<Training> trainings) {
        return trainings.stream()
                .mapToInt(Training::getTrainingCalories)
                .max()
                .orElse(0);
    }

    public static double averageCalories(Set<Training> trainings) {
        return trainings.stream()
                .mapToInt(Training::getTrainingCalories)
                .average()
                .orElse(0);
    }

    public static Duration minDuration(Set<Training> trainings) {
        return trainings.stream()
                .map(Training::getTrainingDuration)
                .min(Comparator.naturalOrder())
                .orElse(Duration.ZERO);
    }

    public static Duration maxDuration(Set<Training> trainings) {
        return trainings.stream()
                .map(Training::getTrainingDuration)
                .max(Comparator.naturalOrder())
                .orElse(Duration.ZERO);
    }

    public static Duration averageDuration(Set<Training> trainings) {
        return Duration.ofSeconds((long) trainings.stream()
                .mapToInt(training -> (int) training.getTrainingDuration().getSeconds())
                .average()
                .orElse(0));
    }
}
