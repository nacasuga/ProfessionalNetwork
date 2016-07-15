package org.nenita.user.repository;

import org.nenita.user.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User> {

	@Query("MATCH (user:User)-[:FOLLOWS]->(c:Company) WHERE c.name={0} RETURN COUNT(user)")
	Integer getCountofPersonFollowingCo(String companyName);
	
	@Query("MATCH (user:User) WHERE user.firstname={0} RETURN user")
	User getByName(String firstname);
}
