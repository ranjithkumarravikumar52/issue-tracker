package issuetracker.service;

import issuetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends CrudService<User, Integer>{
    Page<User> findPaginated(Pageable pageable); //TODO once all entities implement this method, refactor this method to crudservice if needed
}
