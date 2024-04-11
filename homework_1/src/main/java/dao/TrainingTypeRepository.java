package dao;

import model.DefaultUser;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TrainingTypeRepository {
    Optional<String> getTrainingTypeByShortNameAndUser(String trainingTypeShortName, DefaultUser user);
    public Map<DefaultUser, Set<String>> findAll();
    public Set<String> findAllByUser(DefaultUser user);
    public String save(String trainingType, DefaultUser user);
    public void deleteByTrainingTypeAndUser(String trainingType, DefaultUser user);
}
