package issuetracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Extending WebSecurityConfigurerAdapter class will override default spring-boot security behavior. This means
 * implementation of the whole authentication journey is upto us.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder, DataSource dataSource) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/login").permitAll() //any user can user access the login page
                //url restriction based on authorities
                .antMatchers("/sanityCheck/**", "/roles/**", "/users/list").hasAuthority("admin").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/") //take me to home page
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //logout mapping here
                .logoutSuccessUrl("/login").and().exceptionHandling() //after logout take me to login page
                .accessDeniedPage("/access-denied");
    }

    /**
     * Permitting static files
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
    }
}
