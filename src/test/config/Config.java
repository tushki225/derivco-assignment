package test.config;

import test.utils.Constants;
import java.io.FileReader;
import java.util.Properties;

public class Config {
	public static String baseURI;
	
	public static void readPropertiesFile (){ 
		try {
			Properties prop;
			try (FileReader reader = new FileReader(Constants.PROPERTIES_FILE_PATH)) {
				prop = new Properties();
				prop.load(reader);
			}
			baseURI= prop.getProperty(Constants.BASE_URI_IDENTIFIER); 
  
		} catch (Exception e) { 
			e.getMessage();
	  	}
	}
	
	public static String getJsonSchemaPath (){ 		
		return Constants.PROPERTIES_FILE_PATH;	  
	}

}
