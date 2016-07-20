package org.nenita.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;
import org.nenita.user.domain.Employment;
import org.nenita.user.domain.FollowCompany;
import org.nenita.user.domain.FriendRelationship;
import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Lists;

import javax.annotation.PostConstruct;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { UserRepositoryTest.TestConfig.class })
//@SqlGroup({
//    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:neo4j-script.sql")})
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CompanyRepository cRepo;

	private SeedDataForTests seedData;
	
	@Configuration
	@Import({ CustomNeo4jConfig.class })
	static class TestConfig {

	}

	@PostConstruct
	public void construct() {
		seedData = new SeedDataForTests(userRepo, cRepo);
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
		seedData.seedUser();
		User p = userRepo.findByFirstname("Nenita");
		assertNotNull("UUID generated null", p.getUuid());
	}

	@Test
	public void testCountOfFollowers() {
		String coUuid1 = seedData.seedFollowCompanyAndUser().get(0);
		Integer countofFollowing = userRepo.findCountofUserFollowingCo(coUuid1);
		assertTrue("Count of following MyCompany not 3", countofFollowing == 3);
		
		coUuid1 = seedData.seedFollowCompanyAndUser().get(1);
		countofFollowing = userRepo.findCountofUserFollowingCo(coUuid1);
		assertTrue("Count of following Pierre Herme not 1", countofFollowing == 1);
	}

	@Test
	public void testFollow() {
		
		User u = userRepo.findByFirstname("Scarface");
		if (u != null) {
		    userRepo.delete(u);
		}
		User user = new User();
		user.setFirstname("Scarface");
		user.setLastname("Pacino");

		userRepo.save(user);
		
		String uuid = seedData.seedCompany();
		int countofFollowing = userRepo.findCountofUserFollowingCo(uuid);
		assertTrue("Count of following OWN not 0", countofFollowing == 0);
		
		Company comp = cRepo.findByUuid(uuid);
		user = userRepo.findByFirstname("Scarface");
		user.getFollowCompanyRels().add(new FollowCompany(comp, user, Instant.now().toEpochMilli()));
		userRepo.save(user);
		
		countofFollowing = userRepo.findCountofUserFollowingCo(uuid);
		assertTrue("Count of following OWN not 1", countofFollowing == 1);
	}
	
	@Test
	public void testPaginate() {
		String coUuid = seedData.seedFollowCompanyAndUser().get(0);
		List<User> users = userRepo.findUsersFollowingCo(coUuid, 0, 1);
		assertEquals("First user following MyCompany in page 1 not Mouse", "Mouse", users.get(0).getFirstname());
		assertNotNull("Followed on date/ts is null", users.get(0).getFollowCompanyRels().get(0).getFollowedOn());

		users = userRepo.findUsersFollowingCo(coUuid, 1, 1);
		assertEquals("First user following MyCompany in page 2 not Mickey", "Mickey", users.get(0).getFirstname());

		users = userRepo.findUsersFollowingCo(coUuid, 2, 1);
		assertEquals("First user following MyCompany in page 3 not Nenita", "Nenita", users.get(0).getFirstname());
		assertTrue("User is not following 2 companies", users.get(0).getFollowCompanyRels().size() == 2);
	}

	@Test
	public void testEmployment() {
		String cUuid = seedData.seedEmploymentAndUser();
		List<User> employees = userRepo.findAllEmployeesOfCo(cUuid);
		assertTrue("Count of employees for GSW not 2", employees.size() == 2);
		Employment emp1 = employees.get(0).getEmployment().get(0);
		assertEquals("Employee name not Draymond", "Draymond", emp1.getUser().getFirstname());
		assertEquals("Employee title not Guard", "Guard", emp1.getJobTitle());
		assertNull("Employment to date not null", emp1.getEmployedTo());
		assertTrue("Current employer not true", emp1.getCurrentEmployer());
		
		Employment emp2 = employees.get(1).getEmployment().get(0);
		assertEquals("Employee name not Steph", "Steph", emp2.getUser().getFirstname());
		assertEquals("Employee title not MVP", "MVP", emp2.getJobTitle());
		assertNull("Employment to date not null", emp2.getEmployedTo());
		assertTrue("Current employer not true", emp2.getCurrentEmployer());
	}
	
	@Test
	public void testRecommendation() {
		seedData.seedFriends();
		// Find recommendations for Sansa
		// Answer: Beyonce and Daenerys because of Sansa's connection to Nenita and Jon
		User user = userRepo.findByFirstname("Sansa");
		// Sansa - Nenita - Beyonce
		// Sansa - Jon - Daenerys
		List<Map<String,List<User>>> resIterator = userRepo.findCommonConnection(user.getUuid());
		assertTrue("Iterator size not 2", resIterator.size() == 2);
		int loopCnt = 0;
		for (Map<String, List<User>> items: resIterator){
			assertTrue("Set of keys not 1", items.keySet().size() == 1);
			
			for (String mapKey: items.keySet()) {
				loopCnt++;
				List<User> users = items.get(mapKey);
				assertTrue("Path size not 3", users.size() == 3);
				
				// First row = the user itself
				// Second row = the common friend
				// Third row = the recommendation based on the common friend
				assertEquals("First user in path not Sansa", "Sansa", users.get(0).getFirstname());
				if (loopCnt == 1) {
					assertEquals("Second user in path not Nenita", "Nenita", users.get(1).getFirstname());
					assertEquals("Third user in path not Beyonce", "Beyonce", users.get(2).getFirstname());
				} else {
					assertEquals("Second user in path not Jon", "Jon", users.get(1).getFirstname());
					assertEquals("Third user in path not Daenerys", "Daenerys", users.get(2).getFirstname());
				}
			}
		}
	}
	
	@Test
	public void testFriends() {
		
		seedData.seedFriends();
		User user = userRepo.findByFirstname("Nenita");
		List<FriendRelationship> friendRels = user.getFriendRels();
		assertTrue("Nenita's friend size not 2", friendRels.size() == 2);
		assertEquals("Nenita's first friend not Beyonce", "Beyonce", 
				friendRels.get(0).getFriend().getFirstname());
		assertEquals("Nenita's second friend not Sansa", "Sansa", 
				friendRels.get(1).getFriend().getFirstname());
		
		user = userRepo.findByFirstname("Beyonce");
		friendRels = user.getFriendRels();
		assertTrue("Beyonce's friend size not 1", friendRels.size() == 1);
		assertEquals("Beyonce's first friend not Nenita", "Nenita", 
				friendRels.get(0).getFriend().getFirstname());
		
		
		user = userRepo.findByFirstname("Sansa");
		friendRels = user.getFriendRels();
		assertTrue("Sansa's friend size not 2", friendRels.size() == 2);
		assertEquals("Sansa's first friend not Nenita", "Nenita", 
				friendRels.get(0).getFriend().getFirstname());
		assertEquals("Beyonce's second friend not Daenerys", "Daenerys", 
				friendRels.get(1).getFriend().getFirstname());
		
	}
}