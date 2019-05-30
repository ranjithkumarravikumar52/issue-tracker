package issuetracker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity //this will close the bridge between object world and relational world
@Getter
@Setter
@NoArgsConstructor //jpa entities need this
//assuming that this table is a secondary/child of user and we don't need to create a repository of this.
public class PhoneNumber extends BaseEntity{
    /**
     * The reason being this...
     * <a href="https://stackoverflow.com/questions/24353778/which-is-best-data-type-for-phone-number-in-mysql-and-what-should-java-type-mapp">Link here</a><br>
     */
    private String phoneNumber;

    //TODO create a test for the above scenario
    //in the below case we don't need to specify cascade since we are allowing the User to own this
    //if we delete the phone number we don't want to delete the user
    //however if we delete the user, we want our phone number to be deleted

    //for testing and data loading builder pattern helps a lot
    @Builder
    public PhoneNumber(int id, String phoneNumber) {
        super(id);
        this.phoneNumber = phoneNumber;
    }
}
