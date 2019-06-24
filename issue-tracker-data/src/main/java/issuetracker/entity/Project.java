package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true, exclude = {"users", "issues"}) //for displaying and logging
//primary table so we have a project repo
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
     * No cascading delete
     * fetch type lazy
     */
    @ManyToMany(fetch = FetchType.EAGER) //needed this for bi-directional relationship
    @JoinTable(name = "project_user", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    //creates a table called project_user with columns project_id and user_id
    //and when the inverse table is also annotated by mappedBy then we get one table
    private Set<User> users = new HashSet<>();

    /**
     * A project can have multiple issues. An issue can have only one project assigned
     */
    @OneToMany(mappedBy = "project")
    private Set<Issue> issues = new HashSet<>();


    //avoid using other entities inside the builder
    @Builder
    public Project(int id, String projectDescription, String title) {
        super(id);
        this.projectDescription = projectDescription;
        this.title = title;
    }
}
