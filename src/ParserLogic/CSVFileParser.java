package ParserLogic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
public class CSVFileParser {
	public List<InputPOJO> parseCSVtoBean(String fname) throws FileNotFoundException, JsonProcessingException
	{
		CSVReader reader = new CSVReader(new FileReader(fname),',','\"',1);
		ColumnPositionMappingStrategy<InputPOJO> mappingStrategy = new ColumnPositionMappingStrategy<InputPOJO>();
		mappingStrategy.setType(InputPOJO.class);
		String[] columns = new String[] {"Ticker","TradeDate","OpenPrice","HighPrice","LowPrice","ClosePrice","Volume","OI","misc"};
		mappingStrategy.setColumnMapping(columns);
		CsvToBean<InputPOJO> csv = new CsvToBean<InputPOJO>();
		return csv.parse(mappingStrategy, reader);
	}
		
}
