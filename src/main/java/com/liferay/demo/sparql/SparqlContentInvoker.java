package com.liferay.demo.sparql;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import java.io.IOException;

@Component(
		immediate = true,
		service = SparqlContentInvoker.class
)
public class SparqlContentInvoker {

	String ENDPOINT = "http://sparql-playground.sib.swiss/sparql";
	String BREAK = "<br/>";

	public String getBasicSparql() throws PortalException, IOException {

		StringBuilder r = new StringBuilder();
		r.append("start..");
		r.append(BREAK);
		r.append("using ");
		r.append(ENDPOINT);
		r.append(BREAK);

		try ( RDFConnection conn = RDFConnectionFactory.connect(ENDPOINT) ) {
					Txn.executeRead(conn, ()-> {
						// Process results by row:
						String query = "SELECT DISTINCT ?s { ?s ?p ?o }";
						r.append("Using query ");
						r.append(query);
						r.append(BREAK);
						conn.querySelect(query, (qs)->{
							Resource subject = qs.getResource("s") ;
							r.append("subject: " + subject);
							r.append(BREAK);
						});
						query = "SELECT * { ?s ?p ?o }";
						r.append("Using query ");
						r.append(query);
						r.append(BREAK);
						ResultSet rs = conn.query(query).execSelect() ;
						while (rs.hasNext()) {
							QuerySolution qs = rs.next();
							r.append(qs.toString());
							r.append(BREAK);
						}
						r.append("ask: ");
						r.append(conn.query("ASK{}").execAsk());
					});
					return r.toString();
		} catch (Exception e) {
			System.out.println("Exception found...");
			System.out.println(e.getMessage());
			r.append("exceptions found");
			r.append(BREAK);
			return r.toString();
		}
	}

	public String getSparql(String qry) throws PortalException, IOException {

		final String query = qry;

		StringBuilder r = new StringBuilder();
		r.append("start..");
		r.append(BREAK);
		r.append("using ");
		r.append(ENDPOINT);
		r.append(BREAK);

		try ( RDFConnection conn = RDFConnectionFactory.connect(ENDPOINT) ) {
			Txn.executeRead(conn, ()-> {
				// Process results by row:

				r.append("Using query ");
				r.append(query);
				r.append(BREAK);
				conn.querySelect(query, (qs)->{
					Resource subject = qs.getResource("s") ;
					r.append("subject: " + subject);
					r.append(BREAK);
				});

				r.append("Using query ");
				r.append(query);
				r.append(BREAK);
				ResultSet rs = conn.query(query).execSelect() ;
				while (rs.hasNext()) {
					QuerySolution qs = rs.next();
					r.append(qs.toString());
					r.append(BREAK);
				}
				r.append("ask: ");
				r.append(conn.query("ASK{}").execAsk());
			});
			return r.toString();
		} catch (Exception e) {
			System.out.println("Exception found...");
			System.out.println(e.getMessage());
			r.append("exceptions found");
			r.append(BREAK);
			return r.toString();
		}
	}

	//@Reference
	//private UserLocalService userLocalService;
}