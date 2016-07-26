# All other internal library version management

This contains version management for internal library (services) except for common data domain.
Versions declared here are the default and should be overriden only on a need-to basis
inside the project that needs a different version other than the ones recommended here

To do that, see example below

```xml

	<dependecyManagement>
		<dependencies>
			<dependency>
			<groupId>org.nenita</groupId>
			<artifactId>all-internal-pom</artifactId>
			<version>${lib.pom.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	<dependencies>
	</dependencyManagement>
	<dependency>
			<groupId>org.nenita</groupId>
			<artifactId>organization-svc</artifactId>
			<version>2.0</version>
		</dependency>	
	</dependencies>			