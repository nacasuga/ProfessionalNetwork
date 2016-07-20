# ProfessionalNetwork

Sample project/POC for a professional networking using GraphDB (Neo4j), Java and Spring Boot

Also using Maven for dependency management, employing inheritance to manage different libraries

![Alt text](/user/user_friends_graph.jpg?raw=true "Optional Title")

#Compile and generate lib for local testing
Run mvn clean install in the following order:
- common-pom
- external-lib-pom
- common-data-lib-pom/common-data-domain
- internal-lib-pom
- common-server-pom
- organization-svc
- user-svc

#Run Spring Boot server
: organization-server
mvn spring-boot:run

: user-server
mvn spring-boot:run
