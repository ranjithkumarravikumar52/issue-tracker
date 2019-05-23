package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "email", columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "roleList")
public class User extends BaseEntity {

    @Column(name = "user_name")
    @NotNull(message = "is required")
    @Size(min = 3, max = 10, message = "min is 3, max is 10")
    private String userName;

    @Column(name = "password")
    @NotNull(message = "is required")
    @NotEmpty(message = "can't be empty")
    @Size(min = 3, max = 254, message = "min is 3, max is 10")
    private String password;

    @Email(message = "Invalid Email")
    @Size(max = 254, message = "It is too big")
    @Column(name = "email")
    @NotNull(message = "Please, set here the user email")
    private String email;

    @Column(name = "first_name")
    @NotNull(message = "is required")
    @Size(min = 3, max = 10, message = "min is 3, max is 10")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "is required")
    @Size(min = 3, max = 10, message = "min is 3, max is 10")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "users_has_role", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;

    public User(String userName, String password, String email, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
