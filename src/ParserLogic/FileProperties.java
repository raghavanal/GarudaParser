package ParserLogic;
import java.io.InputStream;
import java.util.Properties;

public class FileProperties {
			public Properties getProperties()
		{
			Properties prop = new Properties();
			String propfileName = "FileProps.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propfileName);
			try
			{
				prop.load(inputStream);
			}		
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return prop;		
		}

	}
