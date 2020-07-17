package web.dao;

import web.model.Role;

public interface RoleDao {

    void addRole(Role role);

    Role getRole(String name);

    boolean hasRole(String name);
}
