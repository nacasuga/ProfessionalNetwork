package org.nenita.organization;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.nenita.graphdb.CustomNeo4jConfig;

@Configuration
@Import(value = { CustomNeo4jConfig.class })
public class OrganizationWebConfig {

}
