package org.nenita.organization.domain;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Concrete implementation of the Organization superclass which represents a type
 * Company 
 * 
 * @author nenita
 *
 */
@NodeEntity(label = "Company")
public class Company extends Organization {

}
