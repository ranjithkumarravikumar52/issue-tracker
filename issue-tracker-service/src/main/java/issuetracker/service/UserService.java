package issuetracker.service;

import issuetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends CrudService<User, Integer>{
    Page<User> findPaginated(Pageable pageable); //TODO once all entities implement this method, refactor this method to crudservice if needed

    void saveImageFile(int userId, MultipartFile file);
    User findUserByEmail(String email);
}
