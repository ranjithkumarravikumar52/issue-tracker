package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "userList")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;

	/**
	 * Bi-directional relationship with user
	 */
	@ManyToMany(mappedBy = "roleList")
	private List<User> userList;

	public Role(String name) {
		this.name = name;
	}
}
