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
@ToString(callSuper = true)
public class User extends BaseEntity {

    @Column
    @NotNull(message = "is required")
    @Size(max = 20, message = "max is 20")
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
    @Size(max = 50, message = "max is 50")
    private String firstName;

    @Column
    @NotNull(message = "is required")
    @Size(max = 50, message = "max is 50")
    private String lastName;

    /**
     * Byte wrapper is preferred to byte primitive type by hibernate team. cos primitive can't be null
     */
    @Lob
    @Column
    private Byte[] image;

    /**
     * For jdbc authentication
     */
    @Column(name = "active")
    private int active;

    /**
     * user has many-one with roles
     * roles has one-many with user
     * the direction is bi-directional
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    /**
     * User can work on multiple projects
     * A project can have multiple users
     * Bi-directional
     * No cascading delete
     * fetch type lazy
     * <p>
     * mappedBy here will join the default two tables into one table which we want
     */
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();

    /**
     * The foreign key is present on User side of the relationship - this means User table becomes the owning side
     * So the owning side of the relationship will always have CascadeType.ALL
     */
    @OneToOne(cascade = CascadeType.ALL)
    private PhoneNumber phoneNumber;

    @Builder
    public User(int id, String userName, String password, String email, String firstName, String lastName,
                Byte[] image) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    /**
     * Helper method to establish bi-directional relationship
     * @param role assign a role to current user
     */
    public void setRole(Role role) {
        this.role = role;
        role.getUserSet().add(this);
    }

    /**
     * helper method for bi-directional relationship
     */
    public void addProject(Project project){
        this.projects.add(project);
        project.getUsers().add(this);
    }
}
