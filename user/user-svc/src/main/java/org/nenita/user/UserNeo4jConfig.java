package org.nenita.user;

import org.nenita.graphdb.CustomNeo4jDomainPackage;

/**
 * Configuration declaring the location of user domains
 * 
 * @author nenita
 *
 */
@CustomNeo4jDomainPackage(packages = "org.nenita.user.domain")
public class UserNeo4jConfig {

}