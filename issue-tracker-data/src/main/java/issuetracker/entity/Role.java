package issuetracker.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "userList")
public class Role extends BaseEntity {

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

    @Builder
    public Role(int id, String name, List<User> userList) {
        super(id);
        this.name = name;
        this.userList = userList;
    }
}
