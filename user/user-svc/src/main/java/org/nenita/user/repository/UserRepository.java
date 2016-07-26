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
	
	@Query("MATCH (user:User) WHERE user.firstname={0} AND user.lastname={1} RETURN user")
	User findByName(String firstname, String lastname);

	/**
	 * Find friends of a user
	 * 
	 * @param userUuid
	 *            UUID of the user whose friends we're trying to find
	 * @return
	 */
	@Query("MATCH (user:User)-[:FRIENDS]->(friend:User) WHERE user.uuid={0} RETURN friend")
	List<User> findFriends(String userUuid);

	/**
	 * Find 2nd-level FRIENDS path of a user (friends of friends)
	 * 
	 * @param userUuid
	 *            of the user we're trying to recommend friends to
	 * @return
	 */
	@Query("MATCH path=(user:User)-[:FRIENDS*2]->(friend:User) WHERE user.uuid={0} "
			+ "AND friend.uuid <> user.uuid RETURN nodes(path);")
	List<Map<String, List<User>>> findCommonConnection(String userUuid);

	/**
	 * Find status updates from user and user's friends. For now the result is
	 * always user's own first + friends order by date DESC
	 * 
	 * @param userUuid
	 *            The user ID whose status update is being retrieved
	 * @return For now the result is always user's own first + friends order by
	 *         date DESC
	 */
	@Query("MATCH (me:User)-[c:CREATED]->(s:Status) WHERE me.uuid = {0} "
			+ "RETURN c.date AS date, s.content AS content, me.firstname + ' ' + me.lastname AS name UNION "
			+ "MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status) WHERE me.uuid={0} "
			+ "RETURN c.date AS date, s.content AS content, friend.firstname + ' ' + friend.lastname AS name "
			+ "ORDER by date DESC;")
	List<Map<String, Object>> findStatusUpdates(String userUuid);

	@Query("MATCH (u:User)-[c:COMMENTED]->(s:Status) where ID(s) = {0} RETURN u.firstname + ' ' + u.lastname "
			+ "AS commentor, c.comment AS comment ORDER BY c.date ASC;")
	List<Map<String, Object>> findStatusUpdateComments(Long statusId);
	
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
