package org.nenita.organization.domain;

import org.nenita.domain.UUIDable;
import org.neo4j.ogm.annotation.GraphId;

/**
 * Base class representing any type of organization. One concrete implementation
 * is a company. This should implement the marker class UUIDable in order to
 * generate its unique UUID upon persistence. Neo4j ID is not good enough as an
 * identifier to be exposed to API level
 * 
 * @author nenita
 *
 */
public abstract class Organization implements UUIDable {

	/*
	 * Must be of type Long and NEVER EVER SET MANUALLY by the application. See
	 * http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#
	 * __graphid_neo4j_id_field
	 */
	@GraphId
	Long id;

	private String uuid;
	private String name;
	private String website;
	private String summary;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}
}