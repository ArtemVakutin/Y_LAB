package dao;

import model.Audit;

import java.util.List;

public interface AuditRepository {

    void save(Audit audit);
    List<Audit> findAll();

}
