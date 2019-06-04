package issuetracker.sanitycheck;

import issuetracker.config.DBCheckConfig;

public interface SanityCheckService {
    String sanityDBCheck(DBCheckConfig dbCheckConfig);
}
