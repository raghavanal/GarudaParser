package ParserLogic;

public class InputPOJO {
		private String Ticker;
		private String TradeDate;
		private String  OpenPrice;
		private  String HighPrice;
		private String LowPrice;
		private String ClosePrice;
		private String Volume;
		private String OI;
		private String  misc;
		public String getTicker() {
			return Ticker;
		}
		public void setTicker(String ticker) {
			Ticker = ticker;
		}
		public String getTradeDate() {
			return TradeDate;
		}
		public void setTradeDate(String tradeDate) {
			TradeDate = tradeDate;
		}
		public String getOpenPrice() {
			return OpenPrice;
		}
		public void setOpenPrice(String openPrice) {
			if (openPrice == "-")
				OpenPrice = "0";
			else		
			OpenPrice = openPrice;
		}
		public String getHighPrice() {
			return HighPrice;
		}
		public void setHighPrice(String highPrice) {
			if (highPrice == "-")
				HighPrice = "0";
			else
			HighPrice = highPrice;
		}
		public String getLowPrice() {
			return LowPrice;
		}
		public void setLowPrice(String lowPrice) {
			if(lowPrice == "-")
				LowPrice = "0";
			else
			LowPrice = lowPrice;
		}
		public String getClosePrice() {
			return ClosePrice;
		}
		public void setClosePrice(String closePrice) {
			if (closePrice == "-")
				ClosePrice = "0";
			else
			ClosePrice = closePrice;
		}
		public String getVolume() {
			return Volume;
		}
		public void setVolume(String volume) {
			Volume = volume;
		}
		public String getOI() {
			return OI;
		}
		public void setOI(String oI) {
			OI = oI;
		}
		public String getMisc() {
			return misc;
		}
		public void setMisc(String misc) {
			this.misc = misc;
		}
		
	
		
}
