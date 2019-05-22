package issuetracker.dao;

import issuetracker.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Role> getRolesList() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Role> query = currentSession.createQuery("from Role", Role.class);
		return query.getResultList();
	}

	@Override
	public Role getRole(int roleId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Role role = currentSession.get(Role.class, roleId);
		return role;
	}

	@Override
	public void deleteRole(int roleId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Role role = currentSession.get(Role.class, roleId);
		currentSession.remove(role);
	}

	@Override
	public void addRole(Role role) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(role);
	}
}
