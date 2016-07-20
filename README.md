# ProfessionalNetwork

Sample project/POC for a professional networking using GraphDB (Neo4j), Java and Spring Boot. This project
also uses Maven for dependency management, employing inheritance to manage complications of dependency
library versioning

### Graph of a User-User relationship
![Alt text](/user/user_friends_graph.jpg?raw=true "User-User Relationship")

### Graph of a User-Company relationship
![Alt text](/user/organization_graph.jpg?raw=true "User-Company Relationship")

# To run locally
### 1. Compile and generate lib for local testing
Run mvn clean install in the following order:
- common-pom
- external-lib-pom
- common-data-lib-pom/common-data-domain
- internal-lib-pom
- common-server-pom
- organization-svc
- user-svc

### 2. Run Spring Boot server
: organization-server
mvn spring-boot:run

: user-server
mvn spring-boot:run
