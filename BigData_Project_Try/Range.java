
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Range {
	

	public static void main(String[] args) throws Exception {
		
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);

		@SuppressWarnings("deprecation")
		Job j = new Job(c, "range");
		j.setJarByClass(Range.class);
		j.setMapperClass(MapRange.class);
		j.setReducerClass(ReduceRange.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(FloatWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	public static class MapRange extends Mapper<LongWritable, Text, Text, FloatWritable> {

		private final int numOfCols = 18;
		private final int  locationColumnId = 8, tipAmountColumnId = 13;
		private Text locationId = new Text(); // key 
		private static FloatWritable ta; // value     bahşiş 

		private String[] lineToList(String line, int num_of_cols) {
			
			String[] list = new String[num_of_cols];

			line = line.substring(0, line.length() - 1);
			list = line.split(",");

			return list;
		}

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] rowList = lineToList(value.toString(), numOfCols);
			String locId = rowList[locationColumnId];
			Float tip;
			
			try{ 
				tip = new Float(Float.parseFloat(rowList[tipAmountColumnId]));
			}
			catch(NumberFormatException e){
				tip = new Float(0);
			}
			
			
			ta = new FloatWritable(tip);
			
			locationId.set(locId);
			context.write(locationId,ta);
			
			
		}

	}

	public static class ReduceRange extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text locationId, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {
			
			float minVal, maxVal, range;

			maxVal = 0;
			minVal = 1000;
			for (FloatWritable value : values) {
				if (value.get() > maxVal)
					maxVal = value.get();
				if (value.get() < minVal)
					minVal = value.get();
			}

			range = maxVal - minVal;
			con.write(locationId, new FloatWritable(range));
			
			
			
		}
	}
	
	
	
	
	
	
	
	
}