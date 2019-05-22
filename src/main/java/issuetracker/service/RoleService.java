package issuetracker.service;

import issuetracker.entity.Role;

import java.util.List;

public interface RoleService {
	List<Role> getRolesList(); //R
	Role getRole(int roleId); //helper
	void deleteRole(int roleId); //D
	void addRole(Role role); //C and U
}
