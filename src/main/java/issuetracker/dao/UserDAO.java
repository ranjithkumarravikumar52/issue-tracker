package issuetracker.dao;

import issuetracker.entity.User;

import java.util.List;

public interface UserDAO {
	List<User> getUsers();

	void addUser(User user);

	User getUser(int userId);
}
