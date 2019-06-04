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
@PropertySource("classpath:application.properties")
@Component
public class DBCheckConfig {

	@Value("${spring.datasource.username}")
	String userName;
	@Value("${spring.datasource.password}")
	String password;
	@Value("${spring.datasource.url}")
	String jdbcUrl;
}
