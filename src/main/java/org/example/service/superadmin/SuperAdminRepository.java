package org.example.service.superadmin;

import org.example.model.SuperAdmin;

public interface SuperAdminRepository {

    public void createSuperAdmin(String username, String password);

    public SuperAdmin readSuperAdmin(Long id);

    public void updateSuperAdmin(Long id, String username, String password);

    public void deleteSuperAdmin(Long id);
}
