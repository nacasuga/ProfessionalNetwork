# External library version management

This contains version management for external library dependencies such as Spring, etc.
Versions declared here are the default and should ideally not be overriden or changed
except during a planned upgrade event. If it poses version conflict with other dependencies,
exclusions should be made instead of version change.

This is divided into sub-modules, namely:

##spring-lib-pom
All external library dependencies related to Spring framework

##neo4j-lib-pom
All external library dependencies related to Neo4j graph database

##junit-test-lib-pom
All external library dependencies related to the JUnit testing framework

##util-lib-pom
All external library dependencies related to utilities such as Google's Guava