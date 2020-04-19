package project.common;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class MyByte {
	private static Logger logger = Logger.getLogger(MyByte.class);
	private final static int BYTE_LENGTH = 8;
	
	public static byte stringToByte(String s) {
		String copy = checkString(s);
		return (byte) stringToInt(copy);
		
	}

	private static int stringToInt(String s) {
		if(s.length()!=8) {
			logger.error("your String not convertible to byte");
			return 0;
		}
		int result = 0;
		int division = 128;
		for(int i=0; i< BYTE_LENGTH; i++) {
			result = result + division * Character.getNumericValue(s.charAt(i));
			division = division/2;
		}
		return result;
	}
	
	private static String checkString(String s) {
		String result = s;
		for(int i=0; i< BYTE_LENGTH - s.length(); i++) {
			result = "0" + s;
		}
		return result;
	}
	
	public static List<String> stringsFromString(String s) {
		List<String> result = new ArrayList<String>();
		String copy = s;
		while(copy.length()>=8) {
			String bit = copy.substring(0, 8);
			result.add(bit);
			copy = copy.substring(8,copy.length());
		}
		if(copy.length()>0) {
			result.add(checkString(copy));
		}
		return result;
	}
	
	public static byte[] binaryAsStringTobyte(String s) {
		List<String> strings = stringsFromString(s);
		byte[] result = new byte[strings.size()];
		for(int i=0; i< strings.size(); i++) {
			result[i] = stringToByte(strings.get(i));
		}
		return result;
	}
}
