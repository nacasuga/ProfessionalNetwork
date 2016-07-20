# ProfessionalNetwork

Sample project/POC for a professional networking using GraphDB (Neo4j), Java and Spring Boot. This project
also uses Maven for dependency management, employing inheritance to manage complications of dependency
library versioning

### Graph of a User-User relationship
![Alt text](/user/user_friends_graph.jpg?raw=true "User-User Relationship")

### Graph of a User-Company relationship
![Alt text](/organization/organization_graph.jpg?raw=true "User-Company Relationship")

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

### 2. Run Spring Boot servers

Pick one for testing

: user-server
mvn spring-boot:run

: organization-server
mvn spring-boot:run

#### 3. Seed data by running:

CQL can be found under the root dir of the project
sh neo4j-shell -c <  neo4j-seed-data.cql

#### 4. Run a sample API

User sample API
http://localhost:8080/api/user/83da2ad1-35fc-479f-9801-40cf804224d1/recommend-friends

Output:

```javascript
[
  {
    "recommendedUser": {
      "name": "Beyonce Knowles",
      "id": "31dfd0e3-4599-4d43-a7f5-b5abd81ffbdd"
    },
    "connectedThru": {
      "name": "Nenita AC",
      "id": "5d3256a7-eab7-4233-a75f-c196390552b8"
    }
  },
  {
    "recommendedUser": {
      "name": "Tyrion Lanister",
      "id": "e0ccd6bb-2473-4ee6-8477-2b624baa7063"
    },
    "connectedThru": {
      "name": "Nenita AC",
      "id": "5d3256a7-eab7-4233-a75f-c196390552b8"
    }
  },
  {
    "recommendedUser": {
      "name": "Daenerys Targaryen",
      "id": "feb07412-ab4d-4db2-922b-6ec94e102b10"
    },
    "connectedThru": {
      "name": "Jon Snow",
      "id": "cc02951d-6359-412b-a3f7-6a2de94a03a4"
    }
  }
]
