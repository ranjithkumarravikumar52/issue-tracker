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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "email", columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"projectList"})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

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

	@Column(name = "user_role")
	@NotNull(message = "is required")
	@NotEmpty(message = "can't be empty")
	private String userRole;

	@OneToMany(mappedBy = "postedBy", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private List<Issue> postedByIssuesList;

	public User(String userName, String password, String email, String userRole) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_has_projects",
			joinColumns = @JoinColumn(name = "users_id"),
			inverseJoinColumns = @JoinColumn(name = "projects_id")
	)
	private List<Project> projectList;


}
