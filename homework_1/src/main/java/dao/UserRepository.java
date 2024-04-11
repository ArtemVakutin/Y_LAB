package dao;

import model.DefaultUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public Optional<DefaultUser> getUserByName(String userName);
    public Optional<DefaultUser> getUserByNameAndPassword(String userName, String password);
    public List<DefaultUser> findAll();
    public DefaultUser save(DefaultUser training);
    public void delete(DefaultUser training);
}
