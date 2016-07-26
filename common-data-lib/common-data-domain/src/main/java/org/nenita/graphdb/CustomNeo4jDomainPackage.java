package org.nenita.graphdb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define the packages to be scanned as Neo4j domain entity
 * <br>
 * Sample usage:
 * 
 * <pre>
 * 	&#64;CustomNeo4jDomainPackage(packages = "org.nenita.user.domain")
 * 	public class UserNeo4jConfig {}
 * </pre>
 * 
 * @author nenita
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomNeo4jDomainPackage {

	String[] packages() default {};
}
