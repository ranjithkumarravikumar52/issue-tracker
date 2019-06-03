package issuetracker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
//primary table so we have a role repo
public class Role extends BaseEntity {

    @Column
    private String name;

    /**
     * user has many-one with roles
     * roles has one-many with user
     * the direction is bi-directional
     * if we delete a role we don't want to delete any user, if we delete a user we don't want to delete any role
     * cos of the above point we are not creating any cascading relationship
     * direction is bi-directional since we created annotation on both sides(object navigation from both sides)
     */
    @OneToMany(mappedBy = "role")
    private Set<User> userSet = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    @Builder
    public Role(int id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        String roleName = "Role{" + this.name + "}";
        if (userSet == null || userSet.isEmpty()) {
            return roleName;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(roleName);
        userSet.forEach(user -> {
            stringBuilder.append("User{");
            stringBuilder.append(user.getUserName());
            stringBuilder.append("}");
        });
        return stringBuilder.toString();
    }
}
