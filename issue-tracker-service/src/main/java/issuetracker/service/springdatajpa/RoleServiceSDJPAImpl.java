package issuetracker.service.springdatajpa;

import issuetracker.entity.Role;
import issuetracker.repository.RoleRepository;
import issuetracker.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RoleServiceSDJPAImpl implements RoleService {

	private final RoleRepository roleRepository;

    public RoleServiceSDJPAImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findAll() {
        Set<Role> roles = new LinkedHashSet<>();
        roleRepository.findAll().forEach(roles:: add);
        return roles;
    }

    @Override
    public Role findById(Integer integer) {
        return roleRepository.findById(integer).orElse(null);
    }

    @Override
    public Role save(Role object) {
        return roleRepository.save(object);
    }

    @Override
    public void delete(Role object) {
        roleRepository.delete(object);
    }

    @Override
    public void deleteById(Integer integer) {
        roleRepository.deleteById(integer);
    }

    @Override
    public Iterable<Role> saveAll(Iterable<Role> entities) {
        return roleRepository.saveAll(entities);
    }
}
