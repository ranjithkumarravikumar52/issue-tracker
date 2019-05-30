package issuetracker.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(exclude = {"postedBy", "openedBy", "fixedBy", "closedBy"})
@NoArgsConstructor
//primary table so we have a issue repo
public class Issue extends BaseEntity {

    @Column
    private String issueDescription;

    @ManyToOne
    private User postedBy;

    @ManyToOne
    private User openedBy;

    @ManyToOne
    private User fixedBy;

    @ManyToOne
    private User closedBy;

    /**
     * Issue issue = new Issue(issue_description, posted_by)
     * posted_by.addIssueToPostedByList(issue)
     * session.save(issue)
     */
    public Issue(String issueDescription) {
        this.issueDescription = issueDescription;
    }


    //adding postedBy cos this field is not null
    @Builder
    public Issue(int id, String issueDescription, User postedBy) {
        super(id);
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
    }

    public Issue(String issueDescription, User postedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
    }

    public Issue(String issueDescription, User postedBy, User openedBy, User fixedBy, User closedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
        this.openedBy = openedBy;
        this.fixedBy = fixedBy;
        this.closedBy = closedBy;
    }
}
