package issuetracker.service;

import issuetracker.dao.ProjectDAO;
import issuetracker.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDAO projectDAO;

	@Override
	@Transactional
	public List<Project> listProjects() {
		return projectDAO.listProjects();
	}
}
