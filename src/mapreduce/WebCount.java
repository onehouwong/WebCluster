package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WebCount {

	private static final String INPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/webinput";
	private static final String OUTPUT_PATH = "hdfs://119.29.90.226:9000/user/hadoop/tmp/webcount ";
	
	public static class WebCountMapper extends Mapper<LongWritable, Text, LongWritable, LongWritable>{
		
		@Override
		public void map(LongWritable key, Text value, Context context){
			
		}
	}
}
