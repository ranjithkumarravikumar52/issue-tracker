package issuetracker.dao;

import issuetracker.entity.Role;

import java.util.List;

public interface RoleDAO {
	List<Role> getRolesList(); //R
	Role getRole(int roleId); //helper
	void deleteRole(int roleId); //D
	void addRole(Role role); //C and U
}
