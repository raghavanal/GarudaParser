package ParserLogic;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ParserLogic.InputPOJO;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;

public class EndofDayImplementation {
	
	private static String fname = "C:\\Data\\Java Project\\bhavcopy\\2016-05-30-NSE-EQ.txt";
	public static void main(String args[]) throws ParseException
	{
		CSVFileParser parser = new CSVFileParser();
		try {
			List<InputPOJO> stocks = parser.parseCSVtoBean(fname);
			DateFormat formatter = new SimpleDateFormat("yyyymmdd");
			double openprice;
			double closeprice;
			double highprice;
			double lowprice;
			int volume;
			int oi;
			int misc;
			CassandraLoader cload = new CassandraLoader();
			Session session = cload.connect("localhost");
			PreparedStatement st;
			for(InputPOJO stk : stocks)
			{
				Date tradeDate = (Date)formatter.parse(stk.getTradeDate());
				openprice = ConvertStringToDouble(stk.getOpenPrice());
				closeprice = ConvertStringToDouble(stk.getClosePrice());
				highprice = ConvertStringToDouble(stk.getHighPrice());
				lowprice = ConvertStringToDouble(stk.getLowPrice());
				volume = Integer.parseInt(stk.getVolume());
				oi = Integer.parseInt(stk.getOI());
				if (stk.getMisc() == null)
					misc = 0;
				else
				misc = Integer.parseInt(stk.getMisc());
				st = session.prepare("INSERT INTO Stocks(\"Ticker\",\"TradeDate\",openprice,closeprice,highprice,lowprice,volume,oi,misc) VALUES(?,?,?,?,?,?,?,?,?)");
				BoundStatement bs = new BoundStatement(st);
				session.execute(bs.bind(stk.getTicker(),tradeDate,openprice,closeprice,highprice,lowprice,volume,oi,misc));
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Double ConvertStringToDouble(String str)
	{
		Double out;
		if (str == "-")
			out = null;
		else
			out = Double.parseDouble(str);
		return out;
	}
	

}
