package mapreduce;

import java.io.IOException;
import java.util.List;

import util.TextAnalyzer;
import util.TextExtractor;

public class test {
	
	public static void main(String[] args) throws IOException{
		String s = "IKAnalyzer��һ����Դ�ģ�����java���Կ����������������ķִʹ��߰�����2006��12���Ƴ�1.0�濪ʼ��IKAnalyzer�Ѿ��Ƴ� ��3����汾������������Կ�Դ��ĿLuenceΪӦ������ģ���ϴʵ�ִʺ��ķ������㷨�����ķִ�������°汾��IKAnalyzer3.0��չΪ ����Java�Ĺ��÷ִ������������Lucene��Ŀ��ͬʱ�ṩ�˶�Lucene��Ĭ���Ż�ʵ�֡�";
		s = TextExtractor.extract(s);
		/*List<String> token = TextAnalyzer.splitText(s);
		for(String t : token)
			System.out.println(t);*/
	}
}
