package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class TextAnalyzer {

	// Split Chinese web context into word list.
	public static List<String> splitText(String webcontext) throws IOException{
		
        byte[] bt = webcontext.getBytes();
        InputStream ip = new ByteArrayInputStream(bt);
        Reader reader = new InputStreamReader(ip);
        
		//StringReader reader = new StringReader(webcontext);
        IKSegmenter ik = new IKSegmenter(reader, true);
		List<String> terms = new ArrayList<String>();
		
		Lexeme lexeme = null;
		
		while((lexeme = ik.next()) != null)
		{
			terms.add(lexeme.getLexemeText());
		}
		return terms;

	}
}
