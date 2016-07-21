package org.nenita.user.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Status update, will show up in newsfeed
 * 
 * @author nenita
 *
 */
@NodeEntity(label = "Status")
public class Status {

	@GraphId
	Long id;

	private String content;

	public Status() {
		
	}
	
	public Status(String content) {
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
