package issuetracker.dao;

import issuetracker.entity.Issue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueDAOImpl implements IssueDAO {

	@Autowired
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
}
