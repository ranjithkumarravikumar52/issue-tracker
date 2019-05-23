package issuetracker.service.springdatajpa;

import issuetracker.entity.User;
import issuetracker.repository.UserRepository;
import issuetracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceSDJPAImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceSDJPAImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        /*
        Iterable<User> allUsers = userRepository.findAll();
        for (User allUser : allUsers) {
            users.add(allUser);
        }
        */
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(Integer integer) {
        Optional<User> userOptional = userRepository.findById(integer);
        /*if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;*/
        return userOptional.orElse(null);

    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
    }
}
