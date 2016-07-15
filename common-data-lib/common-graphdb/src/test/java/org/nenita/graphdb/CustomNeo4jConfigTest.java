package org.nenita.graphdb;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.nenita.graphdb.CustomNeo4jConfig;
import org.nenita.graphdb.domain.UuidableDomain;

/**
 * 
 * @author Nenita Casuga
 *
 */
public class CustomNeo4jConfigTest {

	private CustomNeo4jConfig config = new CustomNeo4jConfig();
	
	@Test
	public void testAnnotationOfPackage() throws Exception {
		config.prepareSession();
		List<String> str = config.getPackages();
		
		assertTrue("Package list not 2", str.size()==2);
		assertTrue("Package list does not contain org.nenita.graphdb.domain",
				str.contains("org.nenita.graphdb.domain"));
		assertTrue("Package list does not contain org.nenita.foo.domain",
				str.contains("org.nenita.foo.domain"));
		assertTrue("Domain class is not assignable from UUIDable", 
				config.isClassUuidable(UuidableDomain.class.getName()));
	}
}
