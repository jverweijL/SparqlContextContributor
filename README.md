# SparqlContextContributor


How to use?
- drop the jar in the deploy folder
- create a fragment
- in the html you can call the SparqlContextContributor with `${sparql.getBasicSparql()}` or a bit more dynamic with `${sparql.getSparql("SELECT DISTINCT * WHERE { ?s ?p ?o} order by desc(?p) LIMIT 4")}`
- make sure your users have a primary address configured in their profile