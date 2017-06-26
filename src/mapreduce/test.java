package mapreduce;

import java.io.IOException;
import java.util.List;

import util.TextAnalyzer;
import util.TextExtractor;

public class test {
	
	public static void main(String[] args) throws IOException{
		String s = "IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始，IKAnalyzer已经推出 了3个大版本。最初，它是以开源项目Luence为应用主体的，结合词典分词和文法分析算法的中文分词组件。新版本的IKAnalyzer3.0则发展为 面向Java的公用分词组件，独立于Lucene项目，同时提供了对Lucene的默认优化实现。";
		s = TextExtractor.extract(s);
		/*List<String> token = TextAnalyzer.splitText(s);
		for(String t : token)
			System.out.println(t);*/
	}
}
