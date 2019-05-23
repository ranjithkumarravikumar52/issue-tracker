package issuetracker.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class) //run tests using Junit
@ContextConfiguration(classes = {DemoAppConfig.class}) //load our spring context
@WebAppConfiguration //load web application context
public class HomeControllerTest {
	/**
	 * WebApplicationContext (wac) provides the web application configuration. It loads all the application beans and controllers into the context.
	 * We’ll now be able to wire the web application context right into the test:
	 */
	@Autowired
	private WebApplicationContext wac;

	// MockMvc provides support for Spring MVC testing. It encapsulates all web application beans and make them available for testing.
	private MockMvc mockMvc;
	@Before
	public void setup() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesHomeController() {
		ServletContext servletContext = wac.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("homeController"));
	}
}
