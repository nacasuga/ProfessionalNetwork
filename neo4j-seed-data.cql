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
MATCH (me:User), (friend:User) WHERE me.firstname = "Beyonce" AND friend.firstname = "Nenita"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Sansa"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Sansa" AND friend.firstname = "Nenita"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Nenita" AND friend.firstname = "Tyrion"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Tyrion" AND friend.firstname = "Nenita"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);

MATCH (me:User), (friend:User) WHERE me.firstname = "Sansa" AND friend.firstname = "Jon"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Jon" AND friend.firstname = "Sansa"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);

MATCH (me:User), (friend:User) WHERE me.firstname = "Jon" AND friend.firstname = "Daenerys"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);
MATCH (me:User), (friend:User) WHERE me.firstname = "Daenerys" AND friend.firstname = "Jon"
CREATE UNIQUE (me)-[:FRIENDS {friendsSince:[1469040743]}]->(friend);

#####################################################
# Create status update
# Nenita's update
CREATE (s:Status {userUuid: "2e1349fb-a302-4b80-8d48-d41a6d5c5141", date: 1469040743, 
   content: "Success is not final, failure is not fatal: it is the courage to continue that counts"});
CREATE INDEX ON :Status(userUuid);
# Create relationship
MATCH (me:User), (st:Status) WHERE me.firstname = "Nenita" AND st.userUuid = me.uuid
CREATE (me)-[:CREATED]->(st);

# Jon's update
CREATE (s:Status {userUuid: "80163b88-1577-435d-8f30-6a49ba10aa91", date: 1469041290, 
   content: "Winter is coming! Father promised."});
MATCH (me:User), (s:Status) WHERE me.firstname = "Jon" AND s.userUuid = me.uuid
CREATE (me)-[:CREATED]->(statUpdate);

# Sansa's update
CREATE (s:Status {userUuid: "68de55b0-e1c2-43a0-9fcb-e5d443f6c8ee", date: 1469052000, 
   content: "The battle of the bastards. Winterfell is ours again."});
MATCH (me:User), (statUpdate:Status) WHERE me.firstname = "Sansa" AND statUpdate.userUuid = me.uuid
CREATE (me)-[:CREATED]->(statUpdate);

## Newsfeed for nenita: her own status and her friends' status
https://neo4j.com/blog/cypher-union-query-using-collect-clause/
# This one is ordered properly
MATCH (me:User)-[:CREATED]->(ms:Status) WHERE ms.userUuid = "2e1349fb-a302-4b80-8d48-d41a6d5c5141"
WITH COLLECT({date:ms.date, content:ms.content, firstname:me.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)<-[:CREATED]-(s:Status)
WHERE me.uuid="28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH rows + COLLECT({date:s.date, content:s.content, firstname:friend.firstname}) as allRows
UNWIND allRows as row
RETURN row.firstname as firstname, row.date as date, row.content as content 
ORDER by date DESC;

#Unordered together: This works even when friends do not have update!
MATCH (m1:User)-[c1:CREATED]->(s1:Status) 
WHERE m1.uuid = "278d1adc-2d9f-462f-ada5-1d2e74da87fe"
return c1.date as date, s1.content as content,m1.firstname as firstname
UNION
MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status)
WHERE me.uuid="278d1adc-2d9f-462f-ada5-1d2e74da87fe"
return c.date as date, s.content as content, friend.firstname as firstname
ORDER by date DESC;

# Ordered together: This works only if both user and at least one friend has a status update
MATCH (m1:User)-[c1:CREATED]->(s1:Status) 
WHERE m1.uuid = "278d1adc-2d9f-462f-ada5-1d2e74da87fe"
WITH COLLECT({date:c1.date, content:s1.content, firstname:m1.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status)
WHERE me.uuid="278d1adc-2d9f-462f-ada5-1d2e74da87fe"
WITH rows + COLLECT({date:c.date, content:s.content, firstname:friend.firstname}) as allRows
UNWIND allRows as row
RETURN row.firstname as firstname, row.date as date, row.content as content 
ORDER by date DESC;

# Now comment: Still TODO
CREATE (s:Status {date: 1469040755, content: "Omg. So true"});


######### OLD ########

#But this doesn't work with friend does not have update
MATCH (me:User)-[:CREATED]->(ms:Status) WHERE ms.userUuid = "28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH COLLECT({date:ms.date, content:ms.content, firstname:me.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)
OPTIONAL MATCH (friend)<-[c:CREATED]-(s:Status)
WHERE me.uuid="28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH rows as allRows
//WITH (CASE WHEN (c <> null) 
 //     THEN rows + COLLECT({date:s.date, content:s.content, firstname:friend.firstname}) 
 //     ELSE rows END) as allRows
UNWIND allRows as row
RETURN row.firstname as firstname, row.date as date, row.content as content 
ORDER by date DESC;

MATCH (m1:User)-[c1:CREATED]->(s1:Status) 
WHERE c1.userUuid = "28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH COLLECT({date:c1.date, content:s1.content, firstname:m1.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status)
WHERE me.uuid="28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH rows + COLLECT({date:c.date, content:s.content, firstname:friend.firstname}) as allRows
UNWIND allRows as row
RETURN row.firstname as firstname, row.date as date, row.content as content 
ORDER by date DESC;

MATCH (m1:User)-[c1:CREATED]->(s1:Status) 
WHERE c1.userUuid = "28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH COLLECT({cUuid:c1.userUuid, fUuid:'', date:c1.date, content:s1.content, firstname:m1.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status)
WHERE c.userUuid=friend.uuid
WITH rows + COLLECT({cUuid:c.userUuid, fUuid:friend.uuid,date:c.date, content:s.content, firstname:friend.firstname}) as allRows
UNWIND allRows as row
RETURN row
ORDER by row.date DESC;

MATCH (m1:User)-[c1:CREATED]->(s1:Status) 
WHERE c1.userUuid = "28299b0c-3fd5-45d9-89ea-64975c77b9d3"
WITH COLLECT({date:c1.date, content:s1.content, firstname:m1.firstname}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)-[c:CREATED]->(s:Status)
WHERE c.userUuid=friend.uuid
WITH rows + COLLECT({date:c.date, content:s.content, firstname:friend.firstname}) as allRows
UNWIND allRows as row
RETURN row
ORDER by row.date DESC;

#Ordered
MATCH (mine:Status) WHERE mine.userUuid = "2e1349fb-a302-4b80-8d48-d41a6d5c5141"
WITH COLLECT({date:mine.date, content:mine.content, userUuid:mine.userUuid}) as rows
MATCH (me:User)-[:FRIENDS]->(friend:User)-[:CREATED]->(s:Status)
WHERE me.uuid="2e1349fb-a302-4b80-8d48-d41a6d5c5141"
WITH rows + COLLECT({date:s.date, content:s.content, userUuid:s.userUuid}) as allRows
UNWIND allRows as row
RETURN row.userUuid as userUuid, row.date as date, row.content as content 
ORDER by date DESC; 

# Newsfeed for nenita's friends and nenita's own newsfeed
MATCH (mine:Status) WHERE mine.userUuid = "2e1349fb-a302-4b80-8d48-d41a6d5c5141"
RETURN mine as statUpd
UNION
MATCH (me:User)-[:FRIENDS]->(friend:User)-[:CREATED]->(s:Status)
WHERE me.uuid="2e1349fb-a302-4b80-8d48-d41a6d5c5141"
RETURN s as statUpd
ORDER BY s.date DESC;

# Newsfeed for Sansa: sansa's friends (Nenita + Jon's updates), not including Sansa's own
MATCH (me:User)-[:FRIENDS]-(friend:User)
WHERE me.uuid="68de55b0-e1c2-43a0-9fcb-e5d443f6c8ee"
WITH DISTINCT friend
MATCH (friend:User)-[:CREATED]->(s:Status)
RETURN DISTINCT s
ORDER BY s.date DESC;

# This will also work too but it's a cartesian join and would have perf impact
MATCH (user:User)-[:FRIENDS]->(friend:User), (st:Status) WHERE 
user.uuid="68de55b0-e1c2-43a0-9fcb-e5d443f6c8ee" 
AND st.userUuid=friend.uuid RETURN st;

MATCH (user:User)-[:FRIENDS]->(friend:User), (st:Status) WHERE 
user.uuid="68de55b0-e1c2-43a0-9fcb-e5d443f6c8ee" 
AND st.userUuid=friend.uuid RETURN st;