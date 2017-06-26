package mapreduce;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import util.TextExtractor;
import util.TextAnalyzer;
import util.TextTransformer;

public class WordCountInWeb extends Configured implements Tool{
	private static final String INPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/webinput";
	private static final String OUTPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/tmp/wordcountinweb";
	
	// (key, webtext) - (key@word, 1)
	public static class WordCountInWebMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private static final IntWritable ONE = new IntWritable(1);
		private static Text text = new Text();
		private String result;
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			String line=new String(value.getBytes(),0,value.getLength(),"GBK");
			String webcontext = TextExtractor.extract(value.toString());
			System.out.println(webcontext);
			List<String> token = TextAnalyzer.splitText(webcontext);
			for(String word : token){
				text.set(word + "@" + key);
				context.write(text, ONE);
			}
		}
	}
	
	// (key@word ,1) - (key@word, x)
	public static class WordCountInWebReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
			int total = 0;
			for(IntWritable v : values){
				total += v.get();
			}
			context.write(key, new IntWritable(total));
		}
	}

	public int run(String[] arg0) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf, "Word count in web");
		job.setJarByClass(WordCountInWeb.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(WordCountInWebMapper.class);
		job.setCombinerClass(WordCountInWebReducer.class);
		job.setReducerClass(WordCountInWebReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new WordCountInWeb(), args);
		System.exit(res);
	}
	
}
