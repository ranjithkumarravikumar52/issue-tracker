package issuetracker.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@PropertySource("classpath:persistence-issue-tracker-mysql.properties")
@Component
public class DBCheckConfig {

	@Value("${jdbc.user}")
	String userName;
	@Value("${jdbc.password}")
	String password;
	@Value("${jdbc.url}")
	String jdbcUrl;
	@Value("${jdbc.driver}")
	String driver;
}
