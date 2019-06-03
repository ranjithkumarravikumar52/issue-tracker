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
@ToString(doNotUseGetters = true, exclude = {"users"}) //for displaying and logging
//primary table so we have a project repo
public class Project extends BaseEntity {

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
    @ManyToMany
    @JoinTable(name = "project_user", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    //creates a table called project_user with columns project_id and user_id
    //and when the inverse table is also annotated by mappedBy then we get one table
    private Set<User> users = new HashSet<>();

    public Project(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    //avoid using other entities inside the builder
    @Builder
    public Project(int id, String projectDescription) {
        super(id);
        this.projectDescription = projectDescription;
    }
}
