package org.nenita.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.organization.repository.CompanyRepository;
import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;
import org.nenita.user.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { UserSvcTest.TestConfig.class })
public class UserSvcTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CompanyRepository cRepo;

	@Autowired
	private UserSvc userSvc;
	
	private SeedDataForTests seedData;
	
	@Configuration
	@Import({ CustomNeo4jConfig.class, UserSvcConfig.class })
	static class TestConfig {

	}

	@PostConstruct
	public void construct() {
		seedData = new SeedDataForTests(userRepo, cRepo);
	}
	
	@Test
	public void testRec() {
		seedData.seedFriends();
		// Find recommendations for Sansa
		// Answer: Beyonce and Daenerys because of Sansa's connection to Nenita and Jon
		User user = userRepo.findByFirstname("Sansa");
		List<User> recommendedFriends = userSvc.findRecommendedFriends(user.getUuid());
		
		assertTrue("Recommended size not 2", recommendedFriends.size() == 2);
		assertEquals("First recommended friend not Beyonce", "Beyonce", 
				recommendedFriends.get(0).getFirstname());
		assertEquals("First recommended friend not Daenerys", "Daenerys", 
				recommendedFriends.get(1).getFirstname());
		
		user = userRepo.findByFirstname("Nenita");
		recommendedFriends = userSvc.findRecommendedFriends(user.getUuid());
		
		// Find recommendations for Nenita
		// Answer: Jon because of Nenita's connection to Sansa
		assertTrue("Recommended size not 1", recommendedFriends.size() == 1);
		assertEquals("First recommended friend not Jon", "Jon", 
				recommendedFriends.get(0).getFirstname());
		}
}
