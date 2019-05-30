package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
//primary table so we have a project repo
public class Project extends BaseEntity {

	@Column(name = "project_description")
	private String projectDescription;

    //TODO test: check if the relationship is valid
    //User can work on multiple projects
    //A project can have multiple users
    //Bi-directional
    //No cascading delete
    //fetch type lazy
    @ManyToMany
    @JoinTable(name = "project_user", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    //creates a table called project_user with columns project_id and user_id
    //and when the inverse table is also annotated by mappedBy then we get one table
	private Set<User> users;

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
