package org.nenita.user.repository;

import java.util.List;

import org.nenita.user.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User> {

	@Query("MATCH (user:User)-[:FOLLOWS]->(c:Company) WHERE c.name={0} RETURN COUNT(user)")
	Integer findCountofUserFollowingCo(String companyName);
	
	@Query("MATCH (user:User)-[f:EMPLOYED_AT]->(c:Company) WHERE c.uuid={0} RETURN user")
	List<User> findAllEmployees(String companyUuid);
	
	// Skip a number of results
	@Query("MATCH (user:User)-[f:FOLLOWS]->(c:Company) WHERE c.name={0} RETURN user SKIP {1} LIMIT {2}")
	List<User> findUsersFollowingCo(String companyName, Integer skipResultsSize, Integer limitResultsSize);
	
	@Query("MATCH (user:User) WHERE user.firstname={0} RETURN user")
	User findByFirstname(String firstname);
}
