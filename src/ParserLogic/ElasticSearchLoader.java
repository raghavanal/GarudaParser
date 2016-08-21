package ParserLogic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticSearchLoader
{
	private Properties p;
	private FileProperties fp;
	private static  String folder;
	private static String arcfolder;
	public ElasticSearchLoader()
	{
		fp = new FileProperties();
		p = fp.getProperties();
		folder = p.getProperty("inputfolder");//new File(p.getProperty("inputfolder"));
		arcfolder = p.getProperty("archivefolder");
		//arcfolder = new File(p.getProperty("archivefolder"));
	}
	public static Double ConvertStringToDouble(String str)
	{
		double out = 0;
		if (str == "-")
			out =  0;
		else
			
			out = Double.parseDouble(str);
		return out;
	}
	
	public static void moveFile(String inpfilename,String optfilename)
	{
		
		File archFile = new File(inpfilename);
		Boolean result = archFile.renameTo(new File(optfilename));
		//System.out.println("Movement to Archive Folder Done?" + result);
	}
	public static void main(String args[]) throws IOException, ParseException
	{
		
		FileIterator fil = new FileIterator();
		ElasticSearchLoader load = new ElasticSearchLoader();
		File inpfolder = new File(load.folder);
		File arcfolder = new File(load.arcfolder);
		ArrayList<String> afil = fil.listFilesForFolder(inpfolder);
		CSVFileParser csv = new CSVFileParser();
		Client client = TransportClient.builder().build()
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
		ObjectMapper map = new ObjectMapper();
		IndexResponse response;
		double openprice;
		double closeprice;
		double highprice;
		double lowprice;
		for (String filename : afil)
		{
			JSONPOJO poj = new JSONPOJO();
			List<InputPOJO> inputfile = csv.parseCSVtoBean(folder+"\\"+ filename);
				for(InputPOJO inp: inputfile)
				{
					poj.setTicker(inp.getTicker());
					poj.setTradeDate(inp.getTradeDate());
					 openprice = ConvertStringToDouble(inp.getOpenPrice());
					 closeprice = ConvertStringToDouble(inp.getClosePrice());
					 highprice = ConvertStringToDouble(inp.getHighPrice());
					 lowprice = ConvertStringToDouble(inp.getLowPrice());
					poj.setOpenPrice(openprice);
					poj.setClosePrice(closeprice);
					poj.setHighPrice(highprice);
					poj.setLowPrice(lowprice);
					if(inp.getOI() == "-")
						poj.setOI(0);
					else
					poj.setOI(Integer.parseInt(inp.getOI()));
					try{
					if (inp.getVolume() == "-")
						poj.setVolume(0);
					else
						poj.setVolume(Integer.parseInt(inp.getVolume()));
					}
					catch(NumberFormatException e){
						moveFile(inpfolder+"\\"+ filename,arcfolder+"\\"+ filename);
					}
					if(inp.getMisc() != null)
					poj.setMisc(Integer.parseInt(inp.getMisc()));
					
					byte[] json = map.writeValueAsBytes(poj);
					//String json = map.writeValueAsString(poj);
					//System.out.println(json.toString());
					response = client.prepareIndex("eodstocksearch","stocklist").setSource(json).get();
				//	System.out.println(response.isCreated());
				}
			moveFile(inpfolder+"\\"+ filename+".txt",arcfolder+"\\"+ filename+".txt");
			System.out.println("Completed Loading" + filename + "..");
	
		}
	}
	
	
}