package issuetracker.dao;

import issuetracker.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
	@Override
	public List<User> getUsers() {
		return null;
	}
}
