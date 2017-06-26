package util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtractor {

	public static String extract(String webcontext) throws UnsupportedEncodingException{
		String re = "([\u4e00-\u9fa5]+)"; // Chinese words unicode
		StringBuilder sb = new StringBuilder();
		Matcher matcher = Pattern.compile(re).matcher(webcontext);
		while(matcher.find()){
			//sb.append(new String(matcher.group().getBytes("GBK"),"UTF-8")); // very important!
			sb.append(matcher.group());
		}
		
		return sb.toString();
	}
}
