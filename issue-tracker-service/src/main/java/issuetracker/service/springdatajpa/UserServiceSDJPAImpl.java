package issuetracker.service.springdatajpa;

import issuetracker.entity.User;
import issuetracker.repository.UserRepository;
import issuetracker.service.UserService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserServiceSDJPAImpl implements UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = getLogger(UserServiceSDJPAImpl.class);

    public UserServiceSDJPAImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Set<User> findAll() {
        return new LinkedHashSet<>(userRepository.findAll());
    }

    @Override
    public User findById(Integer integer) {
        return userRepository.findById(integer).orElse(null);
    }

    @Override
    public User save(User object) {
        if(object.getActive() != 1){
            object.setPassword(bCryptPasswordEncoder.encode(object.getPassword())); //bcrypt password
            object.setActive(1); //for jdbc authentication
        }
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

    @Override
    public Iterable<User> saveAll(Iterable<User> entities) {
        for(User object: entities){
            if(object.getActive() != 1){
                object.setPassword(bCryptPasswordEncoder.encode(object.getPassword())); //bcrypt password
                object.setActive(1); //for jdbc authentication
            }
        }
        return userRepository.saveAll(entities);
    }

    /**
     * This method will generate a Pageable object based on controller request
     *
     * @param pageable request from the controller
     * @return returns the Page object, "page" from the repo based on our request
     */
    @Override
    public Page<User> findPaginated(Pageable pageable) {
        //this can be avoided either by caching or declaring this as static
        List<User> allUsers = new ArrayList<>(this.findAll());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        //start item is the first item number in the current page relative to total number of items
        //for example: if allUsers.size() == 20, pageSize = 7 and currentPage = 2 (0-based)
        //startItem = 15
        int startItem = currentPage * pageSize;

        //Our slice of users from the records
        List<User> pageUsers;

        if (allUsers.size() < startItem) {//no items in the current page or we exceeded our max page limit
            pageUsers = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allUsers.size()); ////Math.min(15+7, 20) will be 20
            pageUsers = allUsers.subList(startItem, toIndex); //toIndex is exclusive, however our items are 0-based, so we safe
        }

        //now that we got our requested page/slice of users from user records
        //time to return that "slice" in the form of a page object
        return new PageImpl<>(pageUsers, PageRequest.of(currentPage, pageSize), allUsers.size());
    }

    @Override
    public void saveImageFile(int userId, MultipartFile file) {
        try {
            User userById = this.findById(userId);
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b:file.getBytes()){
                byteObjects[i++] = b;
            }

            userById.setImage(byteObjects);
            userRepository.save(userById);

        } catch (IOException e) {
            //TODO exception handling please?
            log.error("Saving file to db failed: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
