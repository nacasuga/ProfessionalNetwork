package org.nenita.organization.repository;

import org.nenita.organization.domain.Company;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Neo4j queries related to the Company domain
 * 
 * @author nenita
 *
 */
public interface CompanyRepository extends GraphRepository<Company> {

	@Query("MATCH (c:Company) WHERE c.name={0} RETURN c")
	Company findByName(String name);
	
	@Query("MATCH (c:Company) WHERE c.uuid={0} RETURN c")
	Company findByUuid(String uuid);
}
