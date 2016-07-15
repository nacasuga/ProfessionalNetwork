package org.nenita.user.domain;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import org.nenita.graphdb.UUIDable;
import org.nenita.organization.domain.Company;

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
	
	//http://blog.armbruster-it.de/2013/08/assigning-uuids-to-neo4j-nodes-and-relationships/
	// Neo4j does not allow UUID data type
	private String uuid;

	private String firstname;
	private String lastname;

	@Relationship(type="FOLLOWS", direction=Relationship.OUTGOING)
    private List<Company> companiesFollowed;
	
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

	@Relationship(type = "FOLLOWS", direction = "OUTGOING")
	public List<Company> getCompaniesFollowed() {
		return companiesFollowed;
	}

	public void setCompaniesFollowed(List<Company> companiesFollowed) {
		this.companiesFollowed = companiesFollowed;
	}
	
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
