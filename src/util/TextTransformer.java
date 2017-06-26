package util;

import java.io.UnsupportedEncodingException;

import org.apache.hadoop.io.Text;

public class TextTransformer {
	public static Text transformTextToUTF8(Text text, String encoding) {
		String value = null;
		try {
		value = new String(text.getBytes(), 0, text.getLength(), encoding);
		} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		}
		return new Text(value);
		}
}
