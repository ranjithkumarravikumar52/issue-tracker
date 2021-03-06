package issuetracker.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Primary table so we have a role repo
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"userSet"})
public class Role extends BaseEntity {

    @Column(unique = true)
    private String name;

    /**
     * user has many-one with roles
     * roles has one-many with user
     * the direction is bi-directional
     * if we delete a role we don't want to delete any user, if we delete a user we don't want to delete any role
     * cos of the above point we are not creating any cascading relationship
     * direction is bi-directional since we created annotation on both sides(object navigation from both sides)
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> userSet = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    @Builder
    public Role(int id, String name) {
        super(id);
        this.name = name;
    }

}
