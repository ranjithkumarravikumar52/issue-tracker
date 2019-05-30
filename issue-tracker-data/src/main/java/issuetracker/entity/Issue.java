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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "opened_by")
    private User openedBy;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "fixed_by")
    private User fixedBy;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "closed_by")
    private User closedBy;

    /**
     * Issue issue = new Issue(issue_description, posted_by)
     * posted_by.addIssueToPostedByList(issue)
     * session.save(issue)
     */
    public Issue(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    //avoid using other entities inside the builder
    @Builder
    public Issue(int id, String issueDescription) {
        super(id);
        this.issueDescription = issueDescription;
    }

    public Issue(String issueDescription, User postedBy, User openedBy, User fixedBy, User closedBy) {
        this.issueDescription = issueDescription;
        this.postedBy = postedBy;
        this.openedBy = openedBy;
        this.fixedBy = fixedBy;
        this.closedBy = closedBy;
    }
}
