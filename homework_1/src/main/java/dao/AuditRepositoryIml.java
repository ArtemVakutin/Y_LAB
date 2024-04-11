package dao;

import model.Audit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditRepositoryIml implements AuditRepository{
    private static final List<Audit> auditList = new ArrayList<>();

    @Override
    public void save(Audit audit) {
        auditList.add(audit);
    }

    @Override
    public List<Audit> findAll() {
        return Collections.unmodifiableList(auditList);
    }
}
