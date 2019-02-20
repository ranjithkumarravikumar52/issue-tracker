package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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

	public User(String userName, String password, String email, String userRole) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}
}
