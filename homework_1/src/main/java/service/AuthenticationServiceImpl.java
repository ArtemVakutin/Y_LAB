package service;

import dao.UserRepository;
import dao.UserRepositoryImpl;
import exception.DaoException;
import exception.IllegalTextException;
import in.dto.Request;
import model.DefaultUser;
import session.Session;
import session.SessionManager;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final static UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public String authenticate(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        if (strings.length != 2) {
            throw new IllegalTextException("Для аутентификации введите команду вида auth <login> <password>");
        }
        DefaultUser user = userRepository.getUserByNameAndPassword(strings[0], strings[1])
                .orElseThrow(() -> new DaoException("Пользователь с такими данными не найден"));
        SessionManager.setSession(new Session(user));

        return String.format("%s, аутентифицированы успешно. Введите команду \"help\" для вызова всех команд",
                user.getLogin());
    }

    @Override
    public String logout() {
        SessionManager.setSession(new Session());
        return """
                Вы вышли из своего аккаунта. Авторизируйтесь заново или зарегистрируйтесь.
                Для получения справки введите команду Help""";
    }

    @Override
    public String authByUser(Request request) {
        String[] strings = RequestBodyParser.parseRequest(request.requestBody());
        if (strings.length != 1) {
            throw new IllegalTextException("Для аутентификации введите команду authbyuser <userLogin>");
        }
        DefaultUser user = userRepository.getUserByName(strings[0])
                .orElseThrow(() -> new DaoException("Пользователь с такими данными не найден"));
        SessionManager.setSession(new Session(user));

        return String.format("""
                        Вы зашли под пользователем "%s" успешно. Введите команду "help
                        " для вызова всех команд пользователя. Если хотите выйти из режима пользователя - введите команду
                        logout и далее заново аутентифицируйтесь под данными администратора""",
                user.getLogin());
    }
}
