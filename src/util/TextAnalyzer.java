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
		
		List<String> terms = new ArrayList<String>();
		
		// too long, cut it into half and process each of them
		if(webcontext.length() > 3000)
		{
			List<String> webcontexts = new ArrayList<String>();
			for(int i=0;i<=webcontext.length()/3000;++i){
				int temp = i*3000+3000;
				int end = (temp > webcontext.length() ? webcontext.length(): temp);
				webcontexts.add(webcontext.substring(i*3000, end));
				terms = getTerms(webcontexts.get(i), terms);
			}
			//System.out.println(terms.toString());
		}
		else
			terms = getTerms(webcontext, terms);

		return terms;

	}
	
	
	private static List<String> getTerms(String context, List<String> terms) throws IOException{
		
		StringReader reader = new StringReader(context);
        IKSegmenter ik = new IKSegmenter(reader, true);
		Lexeme lexeme = null;
		while((lexeme = ik.next()) != null)
		{
			terms.add(lexeme.getLexemeText());
		}
		return terms;
	}
}
