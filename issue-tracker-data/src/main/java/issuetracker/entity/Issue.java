package issuetracker.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(exclude = {"postedBy", "openedBy", "fixedBy", "closedBy"})
@NoArgsConstructor
@AllArgsConstructor
//primary table so we have a issue repo
public class Issue extends BaseEntity {

    @Column
    private String issueDescription;

    @ManyToOne
    @NotNull
    private User postedBy;

    @ManyToOne
    private User openedBy;

    @ManyToOne
    private User fixedBy;

    @ManyToOne
    private User closedBy;

    @Builder //adding postedBy cos this field is not null
    public Issue(int id, String issueDescription, User postedBy) {
        super(id);
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
    }

    public Issue(String issueDescription, User postedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
    }
}
