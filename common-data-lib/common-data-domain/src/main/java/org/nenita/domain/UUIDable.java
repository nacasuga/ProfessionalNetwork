package org.nenita.domain;

/**
 * Marker class to determine that a neo4j entity needs to generate a UUID key.
 * When a class implements this marker, our custom neo4j's interceptor will
 * generate a unique UUID before persistence if the entity has no UUID yet.
 * Neo4j ID is not good enough as an identifier to be exposed to API level,
 * hence the need to generate a UUID
 * 
 * @author nenita
 *
 */
public interface UUIDable {

	void setUuid(String uuid);

	String getUuid();

}
