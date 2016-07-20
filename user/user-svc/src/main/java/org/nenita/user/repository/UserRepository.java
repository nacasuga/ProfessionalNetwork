package org.nenita.user.repository;

import java.util.List;
import java.util.Map;

import org.nenita.user.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

// Need to add indexes where needed, especially on recommendations
public interface UserRepository extends GraphRepository<User> {

	@Query("MATCH (user:User) WHERE user.firstname={0} RETURN user")
	User findByFirstname(String firstname);
	
	/**
	 * Find friends of a user
	 * 
	 * @param userUuid
	 * @return
	 */
	@Query("MATCH (user:User)-[:FRIENDS]->(friend:User) WHERE user.uuid={0} RETURN friend")
	List<User> findFriends(String userUuid);
	
	/**
	 * Find 2nd-level FRIENDS path of a user (friends of friends)
	 * 
	 * @param userUuid
	 * @return
	 */
	@Query("MATCH path=(user:User)-[:FRIENDS*2]->(friend:User) WHERE user.uuid={0} "
			+ "AND friend.firstname <> user.firstname RETURN nodes(path);")
	List<Map<String,List<User>>> findCommonConnection(String userUuid);
	
	@Query("MATCH (user:User)-[:FOLLOWS]->(co:Company) WHERE co.uuid={0} RETURN COUNT(user)")
	Integer findCountofUserFollowingCo(String companyUuid);
	
	/**
	 * Find all employees of a company
	 * 
	 * @param companyUuid
	 * @return
	 */
	@Query("MATCH (user:User)-[:EMPLOYED_AT]->(co:Company) WHERE co.uuid={0} RETURN user")
	List<User> findAllEmployeesOfCo(String companyUuid);
	
	/**
	 * Find users following a company, paginated
	 * 
	 * @param companyUuid
	 * @param skipResultsSize
	 * @param limitResultsSize
	 * @return
	 */
	// Skip a number of results
	@Query("MATCH (user:User)-[:FOLLOWS]->(co:Company) WHERE co.uuid={0} RETURN user SKIP {1} LIMIT {2}")
	List<User> findUsersFollowingCo(String companyUuid, Integer skipResultsSize, Integer limitResultsSize);
	
	
}
