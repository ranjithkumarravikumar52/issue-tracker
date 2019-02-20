package issuetracker.service;

import issuetracker.dao.UserDAO;
import issuetracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
}
