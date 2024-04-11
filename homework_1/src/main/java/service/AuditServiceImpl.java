package service;

import dao.AuditRepository;
import dao.AuditRepositoryIml;
import model.Audit;

import java.util.stream.Collectors;

public class AuditServiceImpl implements AuditService{
    AuditRepository auditRepository = new AuditRepositoryIml();
    @Override
    public void addAudit(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public String findAllAudit() {
        return auditRepository.findAll()
                .stream()
                .map(Record::toString)
                .collect(Collectors.joining("\n"));
    }
}
