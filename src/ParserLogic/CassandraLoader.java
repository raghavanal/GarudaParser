package ParserLogic;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraLoader {
	private Cluster cluster;
	private static Session session;
	
	public Session getSession()
	{
		return CassandraLoader.session;
	}
	
	public Session connect(String node) {
		   cluster = Cluster.builder().addContactPoint("localhost").build();
		   Metadata metadata = cluster.getMetadata();
		         metadata.getClusterName();
		   for ( Host host : metadata.getAllHosts() ) {
		      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		         host.getDatacenter(), host.getAddress(), host.getRack());
		   }
		   session = cluster.connect("\"EODStocks\"");
		   return session;
		}
	
	public void close() {
		   cluster.close();
		}
}
