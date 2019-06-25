package issuetracker.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Entity - this will close the bridge between object world and relational world
 * NoArgsConstructor - jpa entities need this
 * doNotUseGetters - have been assigned true for display and logging. (Avoiding stack over flow error)
 * <br>
 *     Also this class is not a primary entity. Hence we don't have repository class for this.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(doNotUseGetters = true)
public class PhoneNumber extends BaseEntity {
    /**
     * The reason being this...
     * <a href="https://stackoverflow.com/questions/24353778/which-is-best-data-type-for-phone-number-in-mysql-and-what-should-java-type-mapp">Link here</a><br>
     */
    @Column(unique = true)
    private String phoneNumber;

    /**
     * for testing and data loading builder pattern helps a lot
     */
    @Builder
    public PhoneNumber(int id, String phoneNumber) {
        super(id);
        this.phoneNumber = phoneNumber;
    }
}
