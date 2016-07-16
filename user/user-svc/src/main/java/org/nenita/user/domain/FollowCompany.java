package org.nenita.user.domain;

import org.nenita.organization.domain.Company;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Use this modeling technique if you have a rich association, that is, a relationship
 * that has attributes
 * 
 * @author nenita
 *
 */
@RelationshipEntity(type = "FOLLOWS")
public class FollowCompany {

	@GraphId
	private Long nodeId;

	@StartNode
	private User user;

	@EndNode
	private Company company;
	
	private Long followedOn;

	public FollowCompany() {
		
	}
	
	public FollowCompany(Company company, User user, Long followedOn) {
		this.user = user;
		this.company = company;
		this.followedOn = followedOn;
	}
	
	public Long getFollowedOn() {
		return followedOn;
	}

	public void setFollowedOn(Long followedOn) {
		this.followedOn = followedOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	
}
