package org.nenita.organization.repository;

import org.nenita.organization.domain.Company;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * 
 * @author Nenita Casuga
 *
 */
public interface CompanyRepository extends GraphRepository<Company> {

	@Query("MATCH (c:Company) WHERE c.name={0} RETURN c")
	Company findByName(String name);
}
