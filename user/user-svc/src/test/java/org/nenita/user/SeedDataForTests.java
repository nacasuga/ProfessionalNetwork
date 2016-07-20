package org.nenita.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;
import org.nenita.user.domain.Employment;
import org.nenita.user.domain.FollowCompany;
import org.nenita.user.domain.User;
import org.nenita.user.repository.UserRepository;

public class SeedDataForTests {

	private UserRepository userRepo;
	private CompanyRepository cRepo;

	public SeedDataForTests(UserRepository userRepo, CompanyRepository cRepo) {
		this.userRepo = userRepo;
		this.cRepo = cRepo;
	}

	List<String> seedFollowCompanyAndUser() {

		List<String> coUuids = new ArrayList<String>();
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

		User user = new User("Nenita", "AC");
		user.getFollowCompanyRels().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		user.getFollowCompanyRels().add(new FollowCompany(companySaved2, user, Instant.now().toEpochMilli()));
		userRepo.save(user);

		user = new User("Mickey", "AC");
		user.getFollowCompanyRels().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		userRepo.save(user);

		user = new User("Mouse", "AC");
		user.getFollowCompanyRels().add(new FollowCompany(companySaved, user, Instant.now().toEpochMilli()));
		userRepo.save(user);
		
		coUuids.add(companySaved.getUuid());
		coUuids.add(companySaved2.getUuid());
		
		return coUuids;
	}

	void seedUser() {
		userRepo.deleteAll();
		User p = new User();
		p.setFirstname("Nenita");
		p.setLastname("AC");
		userRepo.save(p);
	}

	String seedCompany() {
		Company s = cRepo.findByName("OWN");
		if (s != null) {
			cRepo.delete(s);
		}
		Company c = new Company();
		c.setName("OWN");
		cRepo.save(c);
		return c.getUuid();
	}

	String seedEmploymentAndUser() {

		User u = userRepo.findByFirstname("Steph");
		if (u != null) {
			userRepo.delete(u);
		}
		cRepo.deleteAll();

		Company myCo = new Company();
		myCo.setName("GSW");
		Company companySaved = cRepo.save(myCo);

		User user = new User("Steph", "Curry");
		user.getEmployment().add(new Employment(user, companySaved, "MVP", 
				new Employment.EmploymentDate(01, 2012)));
		userRepo.save(user);
		
		user = new User("Draymond", "Green");
		user.getEmployment().add(new Employment(user, companySaved, "Guard", 
				new Employment.EmploymentDate(10, 2011)));
		userRepo.save(user);
		
		return myCo.getUuid();
	}
	
	void seedFriends() {

		deleteUser("Nenita");
		deleteUser("Beyonce");
		deleteUser("Sansa");
		deleteUser("Daenerys");
		deleteUser("Jon");
		deleteUser("Tyrion");

		// Nenita has 3 friends: Beyonce, Sansa, Tyrion
		
		// Daenerys has 1 friend: Jon
		// Jon has 1 friend: Sansa
		// Recommendation for Nenita: Jon through Sansa
		// Recommendation for Nenita: Daenerys through 2nd level connection
		
		// Beyonce has 1 friend: Nenita
		// Sansa has 2 friends: Nenita and Jon
		// Recommendation for Sansa: Daenerys through Jon, Tyrion and Beyonce through Nenita
		
		User nenita = new User("Nenita", "AC");
		userRepo.save(nenita);
		
		//user = userRepo.findByFirstname("Nenita");
		User beyonce = new User("Beyonce", "Knowles");
		beyonce.addFriend(nenita);
		userRepo.save(beyonce);
		
		User sansa = new User("Sansa", "Stark");
		sansa.addFriend(nenita);
		userRepo.save(sansa);
		
		User tyrion = new User("Tyrion", "Lanister");
		nenita.addFriend(tyrion);
		userRepo.save(tyrion);
		userRepo.save(nenita);
		
		User daenerys = new User("Daenerys", "Targaryen");
		userRepo.save(daenerys);
		
		User jon = new User("Jon", "Snow");
		jon.addFriend(sansa);
		jon.addFriend(daenerys);
		userRepo.save(jon);
		userRepo.save(sansa);
		userRepo.save(daenerys);
	}
	
	private void deleteUser(String firstname) {
		User u = userRepo.findByFirstname(firstname);
		if (u != null) {
			userRepo.delete(u);
		}
	}
}
