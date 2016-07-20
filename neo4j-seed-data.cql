#In Mac, go to cd /Applications/Neo4j\ Community\ Edition.app/Contents/Resources/app/bin
#sh neo4j-shell -c <  path-to-cypher-query-file.sql
#Has issue right now, ./neo4j-shared.sh: No such file or directory
#Current only solution is install from homebrew

#run in command line neo4j-shell -c <  path-to-cypher-query-file.cql

#Cleanup
MATCH (me:User) OPTIONAL MATCH (me)-[r]-() DELETE me,r;
MATCH (me:Company) OPTIONAL MATCH (me)-[r]-() DELETE me,r;

CREATE (c:Company {uuid: "f3056e86-f903-497e-9c3f-8a11e9e75781", 
name:"Pierre Herme", description:"Delicious macarons. World's finest chocolatier"});
CREATE INDEX ON :Company(uuid);

CREATE (u:User {uuid: "2e1349fb-a302-4b80-8d48-d41a6d5c5141", 
firstname:"Nenita", lastname:"AC"});
CREATE (u:User {uuid: "d3dff572-bb68-44fb-8796-765efd7e886f", 
firstname:"Beyonce", lastname:"Knowles"});
CREATE (u:User {uuid: "68de55b0-e1c2-43a0-9fcb-e5d443f6c8ee", 
firstname:"Sansa", lastname:"Stark"});
CREATE (u:User {uuid: "4891fbd0-ab12-499d-965f-5f707b1a9ee4", 
firstname:"Tyrion", lastname:"Lanister"});
CREATE (u:User {uuid: "80163b88-1577-435d-8f30-6a49ba10aa91", 
firstname:"Jon", lastname:"Snow"});
CREATE (u:User {uuid: "1fe0b977-e748-4a4e-a1e6-22c93fc910ca", 
firstname:"Daenerys", lastname:"Targaryen"});
CREATE INDEX ON :User(uuid);
CREATE INDEX ON :User(firstname);

MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Beyonce"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Beyonce"
CREATE UNIQUE (friend)-[:FRIENDS {friendsSince:[1469040743]}]->(me);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Sansa"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Sansa"
CREATE UNIQUE (friend)-[:FRIENDS {friendsSince:[1469040743]}]->(me);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Tyrion"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Tyrion"
CREATE UNIQUE (friend)-[:FRIENDS {friendsSince:[1469040743]}]->(me);

MATCH (me:User), (friend:User) WHERE me.firstname = "Sansa" AND friend.firstname = "Jon"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Sansa" AND friend.firstname = "Jon"
CREATE UNIQUE (friend)-[:FRIENDS {friendsSince:[1469040743]}]->(me);

MATCH (me:User), (friend:User) WHERE me.firstname = "Jon" AND friend.firstname = "Daenerys"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Jon" AND friend.firstname = "Daenerys"
CREATE UNIQUE (friend)-[:FRIENDS {friendsSince:[1469040743]}]->(me);
