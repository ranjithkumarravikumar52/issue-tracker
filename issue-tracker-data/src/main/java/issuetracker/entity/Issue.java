package issuetracker.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "issues")
@Getter
@Setter
@ToString(exclude = {"postedBy", "openedBy", "fixedBy", "closedBy"})
@NoArgsConstructor
public class Issue extends BaseEntity {


    @Column(name = "issue_description")
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
}
