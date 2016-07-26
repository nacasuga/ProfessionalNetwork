package org.nenita.user.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.nenita.domain.UUIDable;
import org.nenita.organization.domain.Company;

/**
 * Represents a person who uses the networking site. This should implement the
 * marker class UUIDable in order to generate its unique UUID upon persistence.
 * Neo4j ID is not good enough as an identifier to be exposed to API level
 * 
 * @author nenita
 *
 */
@NodeEntity(label = "User")
public class User implements UUIDable {

	/*
	 * https://github.com/graphaware/neo4j-uuid
	 * 
	 * Must be of type Long and NEVER EVER SET MANUALLY by the application. See
	 * http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#
	 * __graphid_neo4j_id_field
	 */
	@GraphId
	Long id;

	// http://blog.armbruster-it.de/2013/08/assigning-uuids-to-neo4j-nodes-and-relationships/
	// Neo4j does not allow UUID data type
	private String uuid;

	private String firstname;
	private String lastname;

	// Use this for non-rich relationship, that is, the association has no
	// additional attributes
	// @Relationship(type="FOLLOWS", direction=Relationship.OUTGOING)
	// private List<Company> followedCompanies = new ArrayList<Company>();
	@Relationship(type = "FOLLOWS")
	private List<FollowCompany> followCompanyRels = new ArrayList<FollowCompany>();

	@Relationship(type = "EMPLOYED_AT")
	private List<Employment> employment = new ArrayList<Employment>();

	@Relationship(type = "FRIENDS")
	private List<FriendRelationship> friendRels = new ArrayList<FriendRelationship>();

	@Relationship(type = "CREATED")
	private List<StatusCreatedRel> statusCreatedRels = new ArrayList<StatusCreatedRel>();

	@Relationship(type = "COMMENTED")
	private List<StatusCommentedRel> statusCommentedRels = new ArrayList<StatusCommentedRel>();

	public User() {

	}

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<FollowCompany> getFollowCompanyRels() {
		return followCompanyRels;
	}

	public void setFollowCompanyRels(List<FollowCompany> followRel) {
		this.followCompanyRels = followRel;
	}

	/*
	@Relationship(type = "FOLLOWS", direction = "OUTGOING")
	public List<Company> getCompaniesFollowed() {
		return companiesFollowed;
	}*/

	public List<Employment> getEmployment() {
		return employment;
	}

	public void setEmployment(List<Employment> employment) {
		this.employment = employment;
	}

	public List<FriendRelationship> getFriendRels() {
		return friendRels;
	}

	public List<StatusCreatedRel> getStatusCreatedRels() {
		return statusCreatedRels;
	}

	public void setStatusCreatedRels(List<StatusCreatedRel> statusCreatedRels) {
		this.statusCreatedRels = statusCreatedRels;
	}

	public List<StatusCommentedRel> getStatusCommentedRels() {
		return statusCommentedRels;
	}

	public void setStatusCommentedRels(List<StatusCommentedRel> statusCommentedRels) {
		this.statusCommentedRels = statusCommentedRels;
	}

	public void addStatus(Status status) {
		statusCreatedRels.add(new StatusCreatedRel(this, status, Instant.now().toEpochMilli()));
	}

	public void addCommentOnStatus(Status status, String comment) {
		statusCommentedRels.add(new StatusCommentedRel(this, status, comment, Instant.now().toEpochMilli()));
	}

	public void addFriend(User friend) {
		Long time = Instant.now().toEpochMilli();
		friendRels.add(new FriendRelationship(this, friend, time));
		friend.getFriendRels().add(new FriendRelationship(friend, this, time));
	}

	public void setFriendRels(List<FriendRelationship> friendRels) {
		this.friendRels = friendRels;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
