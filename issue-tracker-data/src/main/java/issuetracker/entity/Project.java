package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"userSet"})
public class Project extends BaseEntity {

	@Column(name = "project_description")
	private String projectDescription;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_has_projects",
			joinColumns = @JoinColumn(name = "projects_id"),
			inverseJoinColumns = @JoinColumn(name = "users_id")
	)
	private List<User> userList;

	public Project(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	@Builder
    public Project(int id, String projectDescription, List<User> userList) {
        super(id);
        this.projectDescription = projectDescription;
        this.userList = userList;
    }
}
