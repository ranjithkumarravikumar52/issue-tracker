package issuetracker.service;

import issuetracker.config.DBCheckConfig;

public interface SanityCheckService {
	public String sanityDBCheck(DBCheckConfig dbCheckConfig);
}
