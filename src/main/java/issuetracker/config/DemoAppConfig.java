package issuetracker.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration //for getting beans created in this class
@EnableWebMvc //for web.xml migration
@EnableAspectJAutoProxy //for AOP logging
@ComponentScan("issuetracker") //scan beans across all the packages
@EnableTransactionManagement
@PropertySource({"classpath:persistence-issue-tracker-mysql.properties"}) //read props file
public class DemoAppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	private Environment environment;

	@Autowired
	private DataSource dataSource; //not used till we need jdbc authentication

	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Define a bean for ViewResolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	/**
	 * Define a bean for datasource
	 */
	@Bean
	public DataSource dataSource() {
		//create connection pool from c3p0 framework
		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		//set the jdbc driver for our datasource
		try {
			dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		// for sanity's sake, let's log url and user ... just to make sure we are reading the data
		logger.info(">>>>> jdbc.url = " + environment.getProperty("jdbc.url"));
		logger.info(">>>>> jdbc.user = " + environment.getProperty("jdbc.user"));

		//set database connection properties to our data source
		//url, user, password
		dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
		dataSource.setUser(environment.getProperty("jdbc.user"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));

		//set database connection pool properties to our data source
		//initialpoolsize, min pool size, max pool size, max idle time
		dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("connection.pool.initialPoolSize")));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("connection.pool.minPoolSize")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("connection.pool.maxPoolSize")));
		dataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("connection.pool.maxIdleTime")));

		return dataSource;

	}

	/**
	 * Create and access session factory
	 *
	 * @return
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		// set the properties
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}

	/**
	 * set hibernate properties for our hibernate session factory
	 */
	private Properties getHibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		return props;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/")
				.resourceChain(false); //for the agnostic version to work
	}

	/**
	 * To add our users for in-memory authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		User.UserBuilder users = User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
				.withUser(users.password("john").password("test123").roles("EMPLOYEE"))
				.withUser(users.password("mary").password("test123").roles("MANAGER"))
				.withUser(users.password("susan").password("test123").roles("ADMIN"));
	}
}