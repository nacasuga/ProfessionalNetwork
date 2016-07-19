package org.nenita.user.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Represents the friend relationship between two users.
 * 
 * Use this RelationshipEntity modeling technique if you have a rich
 * association, that is, a relationship that has attributes
 * 
 * @author nenita
 *
 */
@RelationshipEntity(type = "FRIENDS")
public class FriendRelationship {

	@GraphId
	private Long nodeId;

	@StartNode
	private User user;

	@EndNode
	private User friend;

	private Long friendsSince;

	
	public FriendRelationship() {

	}

	public FriendRelationship(User user, User friend, Long friendsSince) {
		this.user = user;
		this.friend = friend;
		this.friendsSince = friendsSince;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public Long getFriendsSince() {
		return friendsSince;
	}

	public void setFriendsSince(Long friendsSince) {
		this.friendsSince = friendsSince;
	}
}
