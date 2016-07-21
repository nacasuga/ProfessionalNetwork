package org.nenita.user.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Entity representing commenting on status node relationship
 * (User)-[:COMMENTED]-(Status)
 * 
 * @author nenita
 *
 */
@RelationshipEntity(type = "COMMENTED")
public class StatusCommentedRel {

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

	private String comment;
	
	public StatusCommentedRel() {

	}

	public StatusCommentedRel(User user, Status status, String comment, Long date) {
		this.user = user;
		this.status = status;
		this.comment = comment;
		this.date = date;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
