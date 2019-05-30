package issuetracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity //this will close the bridge between object world and relational world
@Getter
@Setter
class PhoneNumber extends BaseEntity{
    /**
     * The reason being this...
     * <a href="https://stackoverflow.com/questions/24353778/which-is-best-data-type-for-phone-number-in-mysql-and-what-should-java-type-mapp">Link here</a><br>
     */
    private String phoneNumber;

    //in the below case we don't need to specify cascade since we are allowing the User to own this
    //if we delete the phone number we don't want to delete the user
    //however if we delete the user, we want our phone number to be deleted
    //TODO create a test for the above scenario
    @OneToOne
    private User user;
}
