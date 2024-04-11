package dao;

import exception.DaoException;
import model.DefaultUser;

import java.util.*;

public class TrainingTypeRepositoryImpl implements TrainingTypeRepository{

    private static final String noTrainingTypes = "У пользователя нет добавленных видов  тренировок. Добавьте тренировки";
    private static final String noTrainingType = "Вид тренировки не найден";
    private static final String trainingTypeAlreadyExists = "Вид тренировки уже существует";

    private static final Map<DefaultUser, Set<String>> trainingTypes = new HashMap<>();
    @Override
    public Optional<String> getTrainingTypeByShortNameAndUser(String trainingTypeShortName, DefaultUser user) {
        Set<String> userTrainingTypes = trainingTypes.entrySet()
                .stream()
                .filter(defaultUserSetEntry -> defaultUserSetEntry.getKey().equals(user))
                .map(Map.Entry::getValue).findFirst().orElseThrow(() -> new DaoException(noTrainingTypes));

        Optional<String> trainingType = userTrainingTypes.stream()
                .filter(s -> s.contains(trainingTypeShortName))
                .findFirst();

        return trainingType;
    }

    @Override
    public Map<DefaultUser, Set<String>> findAll() {
        return Collections.unmodifiableMap(trainingTypes);
    }

    @Override
    public Set<String> findAllByUser(DefaultUser user) {
        return Collections.unmodifiableSet(trainingTypes.entrySet()
                .stream()
                .filter(defaultUserSetEntry -> defaultUserSetEntry.getKey().equals(user))
                .map(Map.Entry::getValue).findFirst().orElseThrow(() -> new DaoException(noTrainingTypes)));
    }

    @Override
    public String save(String trainingType, DefaultUser user) {
        if (!trainingTypes.containsKey(user)) {
            Set<String> strings = new HashSet<>(List.of(trainingType));
            trainingTypes.put(user, strings);
            return trainingType;
        }
        if (trainingTypes.get(user).add(trainingType)) {
            return trainingType;
        }
        throw new DaoException(trainingTypeAlreadyExists);
    }

    @Override
    public void deleteByTrainingTypeAndUser(String trainingType, DefaultUser user) {
        if (!trainingTypes.containsKey(user)) {
            throw new DaoException(noTrainingType);
        }
        if (trainingTypes.get(user).remove(trainingType)) {
            return;
        }
        throw new DaoException(noTrainingType);
    }
}
