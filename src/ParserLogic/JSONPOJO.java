package ParserLogic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONPOJO {
		private String Ticker;
		private String TradeDate;
		private double  OpenPrice;
		private  double HighPrice;
		private double LowPrice;
		private double ClosePrice;
		private int Volume;
		private int OI;
		private int  misc;
		public String getTicker() {
			return Ticker;
		}
		public void setTicker(String ticker) {
			Ticker = ticker;
		}
		public String getTradeDate() {			
			return TradeDate;
		}	
		public void setTradeDate(String tradeDate) throws ParseException {
			//DateFormat sf = new SimpleDateFormat("yyyyMMdd");
			//TradeDate = sf.parse(tradeDate).toString();
			TradeDate = tradeDate;
		}
		public double getOpenPrice() {
			return OpenPrice;
		}
		public void setOpenPrice(double openPrice) {
			OpenPrice = openPrice;
		}
		public double getHighPrice() {
			return HighPrice;
		}
		public void setHighPrice(double highPrice) {
			HighPrice = highPrice;
		}
		public double getLowPrice() {
			return LowPrice;
		}
		public void setLowPrice(double lowPrice) {
			LowPrice = lowPrice;
		}
		public double getClosePrice() {
			return ClosePrice;
		}
		public void setClosePrice(double closePrice) {
			ClosePrice = closePrice;
		}
		public int getVolume() {
			return Volume;
		}
		public void setVolume(int volume) {
			Volume = volume;
		}
		public int getOI() {
			return OI;
		}
		public void setOI(int oI) {
			OI = oI;
		}
		public int getMisc() {
			return misc;	
		}
		public void setMisc(int misc) {
			this.misc = misc;
		}
		
}
