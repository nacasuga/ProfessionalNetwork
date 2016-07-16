CREATE (c:Company {uuid: "f3056e86-f903-497e-9c3f-8a11e9e75781", 
name:"Pierre Herme", description:"Delicious macarons. World's finest chocolatier"});
CREATE INDEX ON :Company(uuid);

CREATE (u:User {uuid: "2e1349fb-a302-4b80-8d48-d41a6d5c5141", 
firstname:"Nenita", lastname:"Casuga"});
CREATE (u:User {uuid: "d3dff572-bb68-44fb-8796-765efd7e886f", 
firstname:"Punky", lastname:"Brewster"});
CREATE (u:User {uuid: "80163b88-1577-435d-8f30-6a49ba10aa91", 
firstname:"Jon", lastname:"Snow"});
CREATE (u:User {uuid: "1fe0b977-e748-4a4e-a1e6-22c93fc910ca", 
firstname:"Daenerys", lastname:"Targaryen"});
CREATE INDEX ON :Person(uuid);