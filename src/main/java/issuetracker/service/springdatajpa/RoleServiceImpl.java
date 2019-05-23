package issuetracker.service.springdatajpa;

import issuetracker.repository.RoleRepository;
import issuetracker.entity.Role;
import issuetracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Transactional
	@Override
	public List<Role> getRolesList() {
		return roleRepository.getRolesList();
	}

	@Override
	@Transactional
	public Role getRole(int roleId) {
		return roleRepository.getRole(roleId);
	}

	@Override
	@Transactional
	public void deleteRole(int roleId) {
		roleRepository.deleteRole(roleId);
	}

	@Override
	@Transactional
	public void addRole(Role role) {
		roleRepository.addRole(role);
	}
}
