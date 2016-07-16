package org.nenita.organization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.organization.domain.Company;
import org.nenita.organization.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
	public void testSaveAndUuidGen() {
		String uuid = seedCompany("MyCompany");
		Company c = cRepo.findByName("MyCompany");
		assertEquals("UUID generated incorrect", uuid, c.getUuid());
	}
	
	@Test
	public void testFindByUuid() {
		String uuid = seedCompany("GoT");
		Company c = cRepo.findByUuid(uuid);
		assertEquals("Company name lookup by UUID incorrect", "GoT", c.getName());
	}

	// Transactional annotation does not seem to be needed
	//@Transactional
	private String seedCompany(String companyName) {
		cRepo.deleteAll();
		Company c = new Company();
		c.setName(companyName);
		cRepo.save(c);
		return c.getUuid();
	}
}
