package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "users")
//primary table so we have a role repo
public class Role extends BaseEntity {

    @Column
    private String name;

    //user has many-one with roles
    //roles has one-many with user
    //the direction is bi-directional
    //TODO test: if we delete a role we don't want to delete any user, if we delete a user we don't want to delete any role
    //cos of the above point we are not creating any cascading relationship
    //TODO test: direction is bi-directional since we created annotation on both sides(object navigation from both sides)
    @OneToMany(mappedBy = "role")
    private Set<User> userSet;

    public Role(String name) {
        this.name = name;
    }

    @Builder
    public Role(int id, String name, Set<User> userSet) {
        super(id);
        this.name = name;
        this.userSet = userSet;
    }
}
