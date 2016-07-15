package org.nenita.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { UserRepositoryTest.TestConfig.class })
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;

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
	public void testCountOfFollowers() {
		seedCompanyAndUser();
		Integer countofFollowing = repo.getCountofPersonFollowingCo("MyCompany");
		assertTrue("Count of following MyCompany not 2", countofFollowing == 2);
	}
	
	@Test
	public void testUuid() {
		seedUser();
		User p = repo.getByName("Nenita");
		assertNotNull("UUID generated null", p.getUuid());
	}
	
	// Transactional annotation does not seem to be needed
	//@Transactional
	private void seedCompanyAndUser() {

		repo.deleteAll();
		cRepo.deleteAll();

		Company c = new Company();
		c.setName("MyCompany");
		Company saved = cRepo.save(c);

		User p = new User();
		p.setFirstname("Nenita");
		p.setLastname("AC");
		List<Company> companies = new ArrayList<Company>();
		companies.add(saved);
		p.setCompaniesFollowed(companies);
		repo.save(p);

		p = new User();
		p.setFirstname("Mickey");
		p.setLastname("AC");
		companies = new ArrayList<Company>();
		companies.add(saved);
		p.setCompaniesFollowed(companies);
		repo.save(p);
	}
	
	//@Transactional
	private void seedUser() {
		repo.deleteAll();
		User p = new User();
		p.setFirstname("Mickey");
		p.setLastname("AC");
		repo.save(p);
	}
}
