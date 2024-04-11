package service;

import dao.UserRepository;
import dao.UserRepositoryImpl;
import exception.DaoException;
import exception.IllegalTextException;
import in.dto.Request;
import model.DefaultUser;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {

    private final String VALIDATION_PROBLEM = """
            команда должна быть вида "reg <login> <password>".
            Логин и пароль должны быть не менее 3 знаков и состоять
            из букв русского или английского алфавита, а также цифр""";
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public String registerUser(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        DefaultUser user = createUser(strings);
        userRepository.save(user);
        return String.format("Пользователь \"%s\" зарегистрирован успешно. Войдите в приложение под своими данными.",
                user.getLogin());
    }

    private DefaultUser createUser(String[] strings) {

        if (strings.length != 2) {
            throw new IllegalTextException(VALIDATION_PROBLEM);
        }
        DefaultUser user = new DefaultUser(strings[0], strings[1]);
        if (user.getLogin().length() < 3 ||
                user.getPassword().length() < 3 ||
                !user.getLogin().matches("[a-zA-Zа-яА-Я0-9]+") ||
                !user.getPassword().matches("[a-zA-Zа-яА-Я0-9]+")) {
            throw new IllegalTextException(VALIDATION_PROBLEM);
        }
        return user;
    }

    @Override
    public String getAllUsers(Request request) {
        List<DefaultUser> all = userRepository.findAll();
        if (all.isEmpty()) {
            throw new DaoException("Пользователей не найдено");
        }
        return all.toString();
    }


}
