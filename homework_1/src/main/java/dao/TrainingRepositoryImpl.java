package dao;

import exception.DaoException;
import model.DefaultUser;
import model.Training;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TrainingRepositoryImpl implements TrainingRepository{
    private static final Set<Training> trainings = new TreeSet<>();

    @Override
    public Optional<Training> getTrainingByTrainingDateAndUser(LocalDate trainingDate, DefaultUser user) {
        return trainings.stream().filter(training -> training.getTrainingDate().equals(trainingDate)
                && training.getUser().equals(user)).findFirst();
    }

    @Override
    public Set<Training> findAll() {
        return Collections.unmodifiableSet(trainings);
    }

    @Override
    public Set<Training> findAllByUser(DefaultUser user) {
        Set<Training> collect = trainings.stream().filter(training -> training.getUser().equals(user)).collect(Collectors.toSet());
        return new TreeSet<>(collect);
    }

    @Override
    public Training save(Training training) {
        if (trainings.contains(training) ) {
            throw new DaoException("Тренировка за этот день уже существует");
        }
        trainings.add(training);
        return training;
    }

    @Override
    public void deleteByTrainingDateAndUser(LocalDate trainingDate, DefaultUser user) {
        Training first = trainings.stream().filter(tr -> tr.getTrainingDate().equals(trainingDate)
                && tr.getUser().equals(user)).findFirst().orElseThrow(()->new DaoException("Тренировки не существует"));
        trainings.remove(first);
    }
}
