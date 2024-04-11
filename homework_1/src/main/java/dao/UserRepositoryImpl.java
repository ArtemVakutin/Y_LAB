package dao;

import exception.DaoException;
import model.DefaultUser;

import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    private static final Set<DefaultUser> defaultUsers = new HashSet<>();

    @Override
    public Optional<DefaultUser> getUserByName(String userName) {
        return defaultUsers.stream().filter(user -> user.getLogin().equals(userName)).findFirst();
    }

    @Override
    public Optional<DefaultUser> getUserByNameAndPassword(String userName, String password) {
        return defaultUsers.stream()
                .filter(user -> user.getLogin().equals(userName) && user.getPassword().equals(userName))
                .findFirst();
    }

    @Override
    public List<DefaultUser> findAll() {
        return new ArrayList<>(defaultUsers);
    }

    @Override
    public DefaultUser save(DefaultUser user) {
        if (defaultUsers.add(user)) {
            return user;
        }
        throw new DaoException("Пользователь с такими данными уже существует");
    }

    @Override
    public void delete(DefaultUser training) {

    }
}
