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

		User u = userRepo.findByFirstname("Nenita");
		if (u != null) {
			userRepo.delete(u);
		}
		u = userRepo.findByFirstname("Beyonce");
		if (u != null) {
			userRepo.delete(u);
		}
		u = userRepo.findByFirstname("Peter");
		if (u != null) {
			userRepo.delete(u);
		}

		// Nenita has 2 friends: Beyonce and Peter
		// Beyonce has 2 friends: Nenita and Daenerys
		// Peter has 1 friend: Nenita
		User user = new User("Nenita", "AC");
		userRepo.save(user);
		
		user = userRepo.findByFirstname("Nenita");
		User user2 = new User("Beyonce", "Knowles");
		user2.addFriend(user);
		userRepo.save(user2);
		
		User user3 = new User("Peter", "Sting");
		user3.addFriend(user);
		userRepo.save(user3);
		userRepo.save(user);
		
		User user4 = new User("Daenerys", "Targaryen");
		user2.addFriend(user4);
		userRepo.save(user4);
	}
}
