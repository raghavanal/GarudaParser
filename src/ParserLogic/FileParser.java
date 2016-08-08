package ParserLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
public class FileParser {
	private final static String filename = "C:\\Data\\Java Project\\bhavcopy\\2016-05-31-NSE-EQ.txt";
	private Cluster cluster;
	private static Session session;
	public static void main(String args[]) throws FileNotFoundException, JsonProcessingException, ParseException
	{
		FileParser parseFile = new FileParser();
		parseFile.connect("localhost");
		List<InputPOJO> stocks = parseFile.parseCSVtoBean(filename);
		PreparedStatement st = null;
		DateFormat formatter = new SimpleDateFormat("yyyymmdd");
		Date TradeDate;
		double openprice;
		double closeprice;
		double highprice;
		double lowprice;
		int volume;
		int oi;
		int misc;
		for(InputPOJO stk:stocks)
		{
			TradeDate = (Date)formatter.parse(stk.getTradeDate());
			try {
			if(stk.getOpenPrice() == "-") 
				openprice = 0;
			else
				openprice = Double.parseDouble(stk.getOpenPrice()); 
			if(stk.getClosePrice() == "-")
				closeprice =Double.parseDouble(null);
			else
				closeprice = Double.parseDouble(stk.getClosePrice());
			if (stk.getHighPrice() == "-")
				highprice = Double.parseDouble(null);
			else
				highprice = Double.parseDouble(stk.getHighPrice());
			if (stk.getLowPrice() == "-")
				lowprice = Double.parseDouble(null);
			else
				lowprice = Double.parseDouble(stk.getLowPrice());
			volume = Integer.parseInt(stk.getVolume());
			oi = Integer.parseInt(stk.getOI());
			if (stk.getMisc() == null)
				misc = 0;
			else
			misc = Integer.parseInt(stk.getMisc());
			st  = session.prepare("INSERT INTO Stocks(\"Ticker\",\"TradeDate\",openprice,closeprice,highprice,lowprice,volume,oi,misc) VALUES(?,?,?,?,?,?,?,?,?)");
			BoundStatement bs = new BoundStatement(st);
			session.execute(bs.bind(stk.getTicker(),TradeDate,openprice,closeprice,highprice,lowprice,volume,oi,misc));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		parseFile.close();
		
	}
	
	public Session getSession()
	{
		return FileParser.session;
	}
	
	public void connect(String node) {
		   cluster = Cluster.builder().addContactPoint("localhost").build();
		   Metadata metadata = cluster.getMetadata();
		         metadata.getClusterName();
		   for ( Host host : metadata.getAllHosts() ) {
		      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
		         host.getDatacenter(), host.getAddress(), host.getRack());
		   }
		   session = cluster.connect("\"EODStocks\"");
		}
	
	public void close() {
		   cluster.close();
		}
	
	private List<InputPOJO> parseCSVtoBean(String fname) throws FileNotFoundException, JsonProcessingException
	{
		CSVReader reader = new CSVReader(new FileReader(fname),',','\"',1);
		ColumnPositionMappingStrategy<InputPOJO> mappingStrategy = new ColumnPositionMappingStrategy<InputPOJO>();
		mappingStrategy.setType(InputPOJO.class);
		String[] columns = new String[] {"Ticker","TradeDate","OpenPrice","HighPrice","LowPrice","ClosePrice","Volume","OI","misc"};
		mappingStrategy.setColumnMapping(columns);
		CsvToBean<InputPOJO> csv = new CsvToBean<InputPOJO>();
		List<InputPOJO> stocklist = csv.parse(mappingStrategy, reader);
		return stocklist;
	}
		
}
