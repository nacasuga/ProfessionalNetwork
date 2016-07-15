package org.nenita.graphdb;

/**
 * Marker class to determine that a neo4j entity needs to generate a UUID key
 * 
 * @author Nenita Casuga
 *
 */
public interface UUIDable {

	void setUuid(String uuid);
	
	String getUuid();
	
}
