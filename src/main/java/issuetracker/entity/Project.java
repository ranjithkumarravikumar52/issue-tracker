package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "project_description")
	private String projectDescription;

	public Project(String projectDescription) {
		this.projectDescription = projectDescription;
	}
}
