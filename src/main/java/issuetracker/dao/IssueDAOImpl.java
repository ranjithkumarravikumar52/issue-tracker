package issuetracker.dao;

import issuetracker.entity.Issue;
import issuetracker.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueDAOImpl implements IssueDAO {

//	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Issue> getIssues() {
		//get hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		//execute query
		Query<Issue> from_issue = currentSession.createQuery("from Issue", Issue.class);

		//get result list
		List<Issue> resultList = from_issue.getResultList();

		//return list
		return resultList;
	}

	@Override
	public void addIssue(Issue issue) {
		Session currentSession = sessionFactory.getCurrentSession();

		User userPostedBy = null;
		User userOpenedBy = null;
		User userFixedBy = null;
		User userClosedBy = null;

		User postedBy = issue.getPostedBy();
		User openedBy = issue.getOpenedBy();
		User fixedBy = issue.getFixedBy();
		User closedBy = issue.getClosedBy();
		if(postedBy != null){
			userPostedBy = currentSession.get(User.class, postedBy.getId());
		}
		if(openedBy != null){
			userOpenedBy = currentSession.get(User.class, openedBy.getId());
		}
		if(fixedBy != null){
			userFixedBy = currentSession.get(User.class, fixedBy.getId());
		}
		if(closedBy != null){
			userClosedBy = currentSession.get(User.class, closedBy.getId());
		}


		//set the other Fk to null
		issue.setPostedBy(userPostedBy);
		issue.setOpenedBy(userOpenedBy);
		issue.setFixedBy(userFixedBy);
		issue.setClosedBy(userClosedBy);

		//add it to the user
		//  userPostedBy.addIssueToPostedByList(issue);

		//save the issue
		currentSession.saveOrUpdate(issue);
	}

	@Override
	public Issue getIssue(int issueId) {
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.get(Issue.class, issueId);
	}

	@Override
	public void deleteIssue(int issueId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Issue issue = currentSession.get(Issue.class, issueId);
		currentSession.remove(issue);
	}
}
