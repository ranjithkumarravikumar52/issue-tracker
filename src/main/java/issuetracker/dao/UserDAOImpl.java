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
}
