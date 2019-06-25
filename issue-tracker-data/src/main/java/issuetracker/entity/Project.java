package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a primary entity, so we do have project repo.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true, exclude = {"users", "issues"})
public class Project extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String title;

    @Column(name = "project_description", unique = true)
    @NotNull
    private String projectDescription;


    /**
     * User can work on multiple projects
     * A project can have multiple users
     * Bi-directional
     * No cascading delete <br>
     * JoinTable meaning:
     * creates a table called project_user with columns project_id and user_id
     * and when the inverse table is also annotated by mappedBy then we get one table
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    /**
     * A project can have multiple issues. An issue can have only one project assigned
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Issue> issues = new HashSet<>();

    @Builder
    public Project(int id, String projectDescription, String title) {
        super(id);
        this.projectDescription = projectDescription;
        this.title = title;
    }

    /**
     * Helper method for establishing bi-directional relationship between user and project
     */
    public void addUser(User user){
        this.users.add(user);
        user.getProjects().add(this);
    }
}
