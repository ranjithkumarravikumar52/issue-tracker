package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true, exclude = {"projects"}) //for displaying and logging
//primary table so we have a user repo
public class User extends BaseEntity {

    @Column
    @NotNull(message = "is required")
    @Size(min = 3, max = 10, message = "min is 3, max is 10")
    private String userName;

    @Column
    @NotNull(message = "is required")
    @NotEmpty(message = "can't be empty")
    @Size(min = 3, max = 254, message = "min is 3, max is 254")
    private String password;

    @Email(message = "Invalid Email")
    @Size(max = 254, message = "It is too big")
    @Column
    @NotNull(message = "Please, set here the user email")
    private String email;

    @Column
    @NotNull(message = "is required")
    @Size(min = 2, max = 50, message = "min is 3, max is 50")
    private String firstName;

    @Column
    @NotNull(message = "is required")
    @Size(min = 2, max = 50, message = "min is 3, max is 50")
    private String lastName;

    @Lob
    @Column //not needed, for explicit hint on what default annotation applies here
    private Byte[] image; //Byte wrapper is preferred to byte primitive type by hibernate team. cos primitive can't be null

    /**
     * user has many-one with roles
     * roles has one-many with user
     * the direction is bi-directional
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    //TODO test: check if the relationship is valid
    //User can work on multiple projects
    //A project can have multiple users
    //Bi-directional
    //No cascading delete
    //fetch type lazy
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER) //mappedBy here will join the default two tables into one table which we want
    private Set<Project> projects = new HashSet<>();

    /**
     * The foreign key is present on User side of the relationship - this means User table becomes the owning side
     *  So the owning side of the relationship will always have CascadeType.ALL
     */
    @OneToOne(cascade = CascadeType.ALL)
    private PhoneNumber phoneNumber;

    public User(String userName, String password, String email, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //avoid using other entities inside the builder
    @Builder
    public User(int id, String userName, String password, String email, String firstName, String lastName) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
