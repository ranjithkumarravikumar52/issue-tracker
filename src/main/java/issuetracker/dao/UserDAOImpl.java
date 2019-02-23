package issuetracker.dao;

import issuetracker.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<User> getUsers() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create a query
		Query<User> theQuery = currentSession.createQuery("from User", User.class);

		// execute query and get result list
		List<User> users = theQuery.getResultList();

		// return the results
		return users;
	}


	@Override
	public void addUser(User user) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// save
		currentSession.saveOrUpdate(user);
	}

	@Override
	public User getUser(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = currentSession.get(User.class, userId);
		return user;
	}

	@Override
	public void deleteUser(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = currentSession.get(User.class, userId);
		currentSession.remove(user);
	}
}
