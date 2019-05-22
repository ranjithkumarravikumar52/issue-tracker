package issuetracker.service;

import issuetracker.dao.RoleDAO;
import issuetracker.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Transactional
	@Override
	public List<Role> getRolesList() {
		return roleDAO.getRolesList();
	}

	@Override
	@Transactional
	public Role getRole(int roleId) {
		return roleDAO.getRole(roleId);
	}

	@Override
	@Transactional
	public void deleteRole(int roleId) {
		roleDAO.deleteRole(roleId);
	}

	@Override
	@Transactional
	public void addRole(Role role) {
		roleDAO.addRole(role);
	}
}
