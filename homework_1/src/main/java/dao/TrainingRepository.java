package dao;

import model.DefaultUser;
import model.Training;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TrainingRepository {
    Optional<Training> getTrainingByTrainingDateAndUser(LocalDate trainingDate, DefaultUser user);
    public Set<Training> findAll();
    public Set<Training> findAllByUser(DefaultUser user);
    public Training save(Training training);
    public void deleteByTrainingDateAndUser(LocalDate trainingDate, DefaultUser user);
}
