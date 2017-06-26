package mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WebCount extends Configured implements Tool{

	private static final String INPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/webinput";
	private static final String OUTPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/tmp/webcount";

	public static class WebCountMapper extends Mapper<LongWritable, Text, LongWritable, LongWritable>{
		
		private final LongWritable ONE = new LongWritable(1);
		
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			context.write(ONE, ONE);
		}
	}
	
	
	public static class WebCountCombiner extends Reducer<LongWritable, LongWritable, LongWritable, LongWritable>{
		
		@Override
		public void reduce(LongWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException{
			long sum = 0L;
			for(LongWritable v : values){
				sum += v.get();
			}
			context.write(key, new LongWritable(sum));
		}
	}
	
	public static class WebCountReducer extends Reducer<LongWritable, LongWritable, NullWritable, LongWritable>{
		@Override
		public void reduce(LongWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException{
			long sum = 0L;
			for(LongWritable v : values){
				sum += v.get();
			}
			context.write(null, new LongWritable(sum));
		}
	}
	
	public int run(String[] arg0) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf, "Web count");
		job.setJarByClass(WebCount.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setMapperClass(WebCountMapper.class);
		job.setCombinerClass(WebCountCombiner.class);
		job.setReducerClass(WebCountReducer.class);
		job.setNumReduceTasks(1);

		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(LongWritable.class);

		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(),
				new WebCount(), args);
		System.exit(res);
	}

}
