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
@ToString
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

	@Column(name = "first_name")
	@NotNull(message = "is required")
	@Size(min = 3, max = 10, message = "min is 3, max is 10")
	private String firstName;

	@Column(name = "last_name")
	@NotNull(message = "is required")
	@Size(min = 3, max = 10, message = "min is 3, max is 10")
	private String lastName;

	@ManyToMany
	@JoinTable(name = "users_has_role", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roleList;

	public User(@NotNull(message = "is required") @Size(min = 3, max = 10, message = "min is 3, max is 10") String userName, @NotNull(message = "is required") @NotEmpty(message = "can't be empty") @Size(min = 3, max = 254, message = "min is 3, max is 10") String password, @Email(message = "Invalid Email") @Size(max = 254, message = "It is too big") @NotNull(message = "Please, set here the user email") String email, @NotNull(message = "is required") @Size(min = 3, max = 10, message = "min is 3, max is 10") String firstName, @NotNull(message = "is required") @Size(min = 3, max = 10, message = "min is 3, max is 10") String lastName) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void addRole(){
		if(this.roleList == null){
			roleList = new ArrayList<>();
		}
		//TODO implement enums
		roleList.add(new Role("developer"));
	}
}
