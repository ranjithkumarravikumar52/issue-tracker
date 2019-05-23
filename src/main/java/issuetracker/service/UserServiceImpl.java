package issuetracker.service;

import issuetracker.repository.UserRepository;
import issuetracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	@Override
	@Transactional
	public void addUser(User user) {
		userRepository.addUser(user);
	}

	@Override
	@Transactional
	public User getUser(int userId) {
		return userRepository.getUser(userId);
	}

	@Override
	@Transactional
	public void delete(int userId) {
		userRepository.deleteUser(userId);
	}
}
