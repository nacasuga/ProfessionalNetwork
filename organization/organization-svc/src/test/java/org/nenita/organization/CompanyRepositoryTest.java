package org.nenita.organization;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import org.nenita.graphdb.CustomNeo4jConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { CompanyRepositoryTest.TestConfig.class })
public class CompanyRepositoryTest {

	@Autowired
	private CompanyRepository cRepo;

	@Configuration
	@Import({ CustomNeo4jConfig.class })
	static class TestConfig {

	}

	@Test
	public void testSave() {
		seedCompany();
		Company c = cRepo.findByName("MyCompany");
		assertNotNull("UUID generated null", c.getUuid());
	}

	// Transactional annotation does not seem to be needed
	//@Transactional
	private void seedCompany() {

		cRepo.deleteAll();
		Company c = new Company();
		c.setName("MyCompany");
		cRepo.save(c);
	}
}
