package service;

import model.Audit;

public interface AuditService {
    public void addAudit(Audit audit);
    public String findAllAudit();


}
