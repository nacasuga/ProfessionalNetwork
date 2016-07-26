# ProfessionalNetwork

Sample project/POC for a professional networking using GraphDB (Neo4j), Java and Spring Boot. This project
also uses Maven for dependency management, employing inheritance and composition to manage complications of dependency library versioning

### Graph of a User-User relationship [e.g. Friends]
![Alt text](/user/user_friends_graph.jpg?raw=true "User-User Relationship")

### Graph of a User-Status relationship
![Alt text](/user/status_update_graph.jpg?raw=true "User-Status Relationship")

### Graph of a User-Company relationship
![Alt text](/organization/organization_graph.jpg?raw=true "User-Company Relationship")

# To run locally
Assumes JDK 1.8, maven are already installed

### 1. Compile and generate lib for local testing
Run mvn clean install in the following order:
- common-pom
- external-lib-pom
- common-data-lib-pom/common-data-domain
- internal-lib-pom
- common-server-pom
- organization-svc
- user-svc

### 2. Set up neo4j
Either:
- Download neo4j and start it locally then change user/user-server/src/main/resources/ogm.properties
Change URI to provide your own username/passwd

- Use embedded neo4j, change user/user-server/src/main/resources/ogm.properties to this
```
driver=org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver
URI=file:///var/tmp/neo4j.db
```

### 3. Seed data:

If you downloaded your own neo4j, change ogm.properties of user/user-svc/src/main/resources/ogm.properties
to match the URI provided in step #2 then run mvn clean test from inside user-svc
OR run neo4j-seed-data.cql which can be found under the root dir of the project sh neo4j-shell -c <  neo4j-seed-data.cql

If you are using embedded neo4j, change change ogm.properties of user/user-svc/src/main/resources/ogm.properties to match the entries provided in step #2 then run mvn clean test from inside user-svc

Then change user/user-server/pom.xml to add the following

```xml
<dependencyManagement>
    <dependencies>
    	<dependency>
				<groupId>org.nenita</groupId>
				<artifactId>neo4j-lib-pom</artifactId>
				<version>1.0.0-SNAPSHOT</version>
				<type>pom</type>
				<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>

Inside dependencies:
<dependency>
	<groupId>org.neo4j</groupId>
	<artifactId>neo4j-ogm-embedded-driver</artifactId>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>org.neo4j</groupId>
	<artifactId>neo4j-ogm-core</artifactId>
	<scope>runtime</scope>
</dependency>
```
		    
### 4. Run Spring Boot servers

Pick one for testing

: user-server
mvn spring-boot:run

: organization-server
mvn spring-boot:run

#### 5. Run a sample API

User sample API
First, get the UUID of the user
```
http://localhost:8080/api/user?firstname=Sansa&lastname=Stark
```

Then use that UUID to get the list of recommended friends,
E.g. 
```
http://localhost:8080/api/user/9d47b1fe-4b11-4b6d-a0ef-abd1deb3dcaa/recommend-friends
```

Output of recommendations for Sansa based on 2nd-level friendship (see diagram above):

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
