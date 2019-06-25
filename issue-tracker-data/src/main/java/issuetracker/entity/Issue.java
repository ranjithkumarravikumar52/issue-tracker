package issuetracker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is considered as a primary table so we have a issue repo
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true, exclude = {"openedBy", "closedBy"})
@NoArgsConstructor
public class Issue extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String title;

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

    @ManyToOne
    @NotNull
    private Project project;

    @Builder
    public Issue(@NotNull String title, @NotNull String issueDescription, @NotNull User openedBy, User closedBy,
                 IssueStatus issueStatus, @NotNull Project project) {
        this.title = title;
        this.issueDescription = issueDescription;
        this.openedBy = openedBy;
        this.closedBy = closedBy;
        this.issueStatus = issueStatus;
        this.project = project;
        project.getIssues().add(this);
    }

    /**
     * Helper method for bi-directional relationship between issue and project
     */
    public void setProject(Project project){
        this.project = project;
        project.getIssues().add(this);
    }
}
