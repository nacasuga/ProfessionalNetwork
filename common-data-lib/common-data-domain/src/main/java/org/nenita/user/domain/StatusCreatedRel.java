package org.nenita.user.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Represents the status created relationship between a user and the status
 * nodes
 * 
 * @author nenita
 *
 */
@RelationshipEntity(type = "CREATED")
public class StatusCreatedRel {

	@GraphId
	private Long nodeId;

	@StartNode
	private User user;

	@EndNode
	private Status status;

	/*
	 * Epoch date when the status was created
	 * This is how you convert it to displayed format
	 * LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
	 */
	private Long date;

	/*
	 * UUID of the user who created the status
	 * For some reason, query doesn't work against user.uuid
	 */
	private String userUuid;

	public StatusCreatedRel() {

	}

	public StatusCreatedRel(User user, Status status, Long date) {
		this.user = user;
		this.userUuid = user.getUuid();
		this.status = status;
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getUserUuid() {
		return userUuid;
	}
}
