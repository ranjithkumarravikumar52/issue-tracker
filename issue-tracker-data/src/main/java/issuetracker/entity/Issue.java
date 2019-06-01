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
//primary table so we have a issue repo
public class Issue extends BaseEntity {

    @Column(unique = true)
    @NotNull
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

    public Issue(String issueDescription, User postedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
    }

    @Builder //adding postedBy cos this field is not null
    public Issue(@NotNull String issueDescription, @NotNull User postedBy, User openedBy, User fixedBy, User closedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
        this.openedBy = openedBy;
        this.fixedBy = fixedBy;
        this.closedBy = closedBy;
    }
}
