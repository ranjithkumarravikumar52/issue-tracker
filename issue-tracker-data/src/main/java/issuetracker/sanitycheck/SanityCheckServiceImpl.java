package issuetracker.sanitycheck;

import issuetracker.config.DBCheckConfig;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class SanityCheckServiceImpl implements SanityCheckService {
    @Override
    public String sanityDBCheck(DBCheckConfig dbCheckConfig) {
        Connection myConnection;
        StringBuilder stringBuilder = new StringBuilder();

        //connection to database
        try {
            stringBuilder
                    .append("Connection to database: ")
                    .append(dbCheckConfig.getJdbcUrl())
                    .append(System.lineSeparator());

            myConnection = DriverManager
                    .getConnection(
                            dbCheckConfig.getJdbcUrl(),
                            dbCheckConfig.getUserName(),
                            dbCheckConfig.getPassword()
                    );

            if(myConnection != null){
                stringBuilder.append("Connection successful").append(System.lineSeparator());
            }else {
                stringBuilder.append("Connection failed").append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
