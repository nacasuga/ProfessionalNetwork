package org.nenita.organization;

import org.nenita.graphdb.CustomNeo4jConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { CustomNeo4jConfig.class })
public class OrganizationWebConfig {

}
