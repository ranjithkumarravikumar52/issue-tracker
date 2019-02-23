package issuetracker.dao;

import issuetracker.entity.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Project> listProjects() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Project> from_project = currentSession.createQuery("from Project", Project.class);
		List<Project> resultList = from_project.getResultList();
		return resultList;
	}

	@Override
	public void addProject(Project project) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(project);
	}
}
