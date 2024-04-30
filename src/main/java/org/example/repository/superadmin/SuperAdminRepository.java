package org.example.repository.superadmin;

import org.example.model.roles.SuperAdmin;

public interface SuperAdminRepository {

    public void createSuperAdmin(String username, String password);

    public SuperAdmin readSuperAdmin(Long id);

    public void updateSuperAdmin(Long id, String username, String password);

    public void deleteSuperAdmin(Long id);

    public SuperAdmin fetchSuperAdminByName(String username);
}
