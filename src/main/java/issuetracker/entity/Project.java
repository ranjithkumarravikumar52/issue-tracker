package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"userList"})
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "project_description")
	private String projectDescription;

	@ManyToMany
	@JoinTable(
			name = "users_has_projects",
			joinColumns = @JoinColumn(name = "projects_id"),
			inverseJoinColumns = @JoinColumn(name = "users_id")
	)
	private List<User> userList;

	public Project(String projectDescription) {
		this.projectDescription = projectDescription;
	}

}
