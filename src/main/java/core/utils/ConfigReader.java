package core.utils;

import core.exceptions.CustomException;
import static core.utils.LogUtils.*;

public class ConfigReader {
	private static String globalParameterFileName = "config\\globalParameters.properties";
	
	public static String getPropertyValue(String propertyName) {

		String propertyValue = null;
		
		try {
			propertyValue = PropertiesReader.getPropertyValue(globalParameterFileName,propertyName);
		}
		catch (Exception e) {
			logStackTrace(e);
			throw new RuntimeException(String.format("error while getting property value for property name -> %s, from file -> %s", propertyName, globalParameterFileName));
		}
		
		return propertyValue;
	}

}
