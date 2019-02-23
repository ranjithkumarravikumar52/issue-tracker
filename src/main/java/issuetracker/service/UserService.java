package issuetracker.service;

import issuetracker.entity.User;

import java.util.List;

public interface UserService {
	List<User> getUsers();

	void addUser(User user);

	User getUser(int userId);

	void delete(int userId);
}
