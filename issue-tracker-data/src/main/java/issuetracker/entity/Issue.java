package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(exclude = {"openedBy", "closedBy"})
@NoArgsConstructor
//primary table so we have a issue repo
public class Issue extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String issueDescription;

    @ManyToOne
    @NotNull
    private User openedBy;

    @ManyToOne
    private User closedBy;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private IssueStatus issueStatus;

    @Builder //adding postedBy cos this field is not null
    public Issue(@NotNull String issueDescription, @NotNull User openedBy, User closedBy, IssueStatus issueStatus) {
        this.issueDescription = issueDescription;
        this.openedBy = openedBy;
        this.closedBy = closedBy;
        this.issueStatus = issueStatus;
    }
}
