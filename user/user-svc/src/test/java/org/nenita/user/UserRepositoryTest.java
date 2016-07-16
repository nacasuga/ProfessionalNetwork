package org.nenita.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;
import org.nenita.user.domain.FollowCompany;
import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { UserRepositoryTest.TestConfig.class })
//@SqlGroup({
//    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:neo4j-script.sql")})
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CompanyRepository cRepo;

	@Configuration
	@Import({ CustomNeo4jConfig.class })
	static class TestConfig {

	}

	/*
	@After
	public void tearDown() {
		if (Components.driver() instanceof EmbeddedDriver) {
			((EmbeddedDriver) Components.driver()).close();
		}
	}*/

	@Test
	public void testUuid() {
		seedUser();
		User p = userRepo.findByFirstname("Nenita");
		assertNotNull("UUID generated null", p.getUuid());
	}

	@Test
	public void testCountOfFollowers() {
		seedCompanyAndUser();
		Integer countofFollowing = userRepo.findCountofUserFollowingCo("MyCompany");
		assertTrue("Count of following MyCompany not 3", countofFollowing == 3);
		
		countofFollowing = userRepo.findCountofUserFollowingCo("Piere Herme");
		assertTrue("Count of following Pierre Herme not 1", countofFollowing == 1);
	}

	@Test
	public void testPaginate() {
		seedCompanyAndUser();
		List<User> users = userRepo.findUsersFollowingCo("MyCompany", 0, 1);
		assertEquals("First user following MyCompany in page 1 not Mouse", "Mouse", users.get(0).getFirstname());
		assertNotNull("Followed on date/ts is null", users.get(0).getFollowRel().get(0).getFollowedOn());

		users = userRepo.findUsersFollowingCo("MyCompany", 1, 1);
		assertEquals("First user following MyCompany in page 2 not Mickey", "Mickey", users.get(0).getFirstname());

		users = userRepo.findUsersFollowingCo("MyCompany", 2, 1);
		assertEquals("First user following MyCompany in page 3 not Nenita", "Nenita", users.get(0).getFirstname());
		assertTrue("User is not following 2 companies", users.get(0).getFollowRel().size() == 2);
	}

	// Transactional annotation does not seem to be needed
	// @Transactional
	private void seedCompanyAndUser() {

		User u = userRepo.findByFirstname("Nenita");
		if (u != null) {
		    userRepo.delete(u);
		}
		u = userRepo.findByFirstname("Mickey");
		if (u != null) {
		    userRepo.delete(u);
		}
		u = userRepo.findByFirstname("Mouse");
		if (u != null) {
		    userRepo.delete(u);
		}
		cRepo.deleteAll();

		Company myCo = new Company();
		myCo.setName("MyCompany");
		Company companySaved = cRepo.save(myCo);

		myCo = new Company();
		myCo.setName("Piere Herme");
		Company companySaved2 = cRepo.save(myCo);

		User user = new User();
		user.setFirstname("Nenita");
		user.setLastname("AC");

		user.getFollowRel().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		user.getFollowRel().add(new FollowCompany(companySaved2, user, Instant.now().toEpochMilli()));
		userRepo.save(user);

		/*List<Company> companies = new ArrayList<Company>();
		companies.add(saved);
		p.setCompaniesFollowed(companies);*/

		user = new User();
		user.setFirstname("Mickey");
		user.setLastname("AC");
		user.getFollowRel().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		userRepo.save(user);

		user = new User();
		user.setFirstname("Mouse");
		user.setLastname("AC");
		user.getFollowRel().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		userRepo.save(user);
	}

	// @Transactional
	private void seedUser() {
		userRepo.deleteAll();
		User p = new User();
		p.setFirstname("Nenita");
		p.setLastname("AC");
		userRepo.save(p);
	}

	private String seedCompany() {
		cRepo.deleteAll();
		Company c = new Company();
		c.setName("OWN");
		cRepo.save(c);
		return c.getUuid();
	}
}
