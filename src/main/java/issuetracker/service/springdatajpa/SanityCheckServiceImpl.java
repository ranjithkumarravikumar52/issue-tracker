package issuetracker.service.springdatajpa;

import issuetracker.config.DBCheckConfig;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class SanityCheckServiceImpl implements SanityCheckService {
	@Override
	public String sanityDBCheck(DBCheckConfig dbCheckConfig) {
		Connection myConnection = null;
		StringBuilder stringBuilder = new StringBuilder();

		//connection to database
		try{
			stringBuilder.append("Connection to database: ").append(dbCheckConfig.getJdbcUrl()).append(System.lineSeparator());
			Class.forName(dbCheckConfig.getDriver()); //jdbcUrl, user, pass
			myConnection = DriverManager.getConnection(dbCheckConfig.getJdbcUrl(), dbCheckConfig.getUserName(), dbCheckConfig.getPassword());
			stringBuilder.append("Connection successful").append(System.lineSeparator());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			stringBuilder.append(e.getMessage());
		}finally {
			if(myConnection != null){
				try {
					myConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					stringBuilder.append(e.getMessage()).append(System.lineSeparator());
				}
			}
			return stringBuilder.toString();
		}
	}
}
