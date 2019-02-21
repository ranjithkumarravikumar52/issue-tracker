package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "user_role")
	private String userRole;

	@OneToMany(mappedBy = "postedBy", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private List<Issue> postedByIssuesList;

	public User(String userName, String password, String email, String userRole) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	/**
	 * Convenience method for bidirectional relationship
	 */
	public void addIssueToPostedByList(Issue issue){
		if(postedByIssuesList == null){
			postedByIssuesList = new ArrayList<>();
		}
		postedByIssuesList.add(issue);
		issue.setOpenedBy(this);
	}
}
