package issuetracker.service.springdatajpa;

import issuetracker.entity.User;
import issuetracker.repository.UserRepository;
import issuetracker.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceSDJPAImpl(userRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        Integer id = 1;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "User profile image content".getBytes());

        User johnDoe = User.builder()
                .id(id)
                .userName("johnDoe")
                .password("pass123")
                .email("johnDoe@gmail.com")
                .firstName("john")
                .lastName("doe")
                .build();

        Optional<User> userOptional = Optional.of(johnDoe);

        when(userRepository.findById(anyInt())).thenReturn(userOptional);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        //when
        userService.saveImageFile(id, multipartFile);

        //then
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        User savedUser = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedUser.getImage().length);
    }
}