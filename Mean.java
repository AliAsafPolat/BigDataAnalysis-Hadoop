
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Mean {

	public static void main(String[] args) throws Exception {
		
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);

		@SuppressWarnings("deprecation")
		Job j = new Job(c, "mean");
		j.setJarByClass(Mean.class);
		j.setMapperClass(MapMean.class);
		j.setReducerClass(ReduceMean.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	public static class MapMean extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final int numOfCols = 18;
		private final int  locationColumnId = 8, fareAmountColumnId = 10;
		private Text locationId = new Text(); // key 
		private static IntWritable fa; // value     hesap 

		private String[] lineToList(String line, int num_of_cols) {
			
			String[] list = new String[num_of_cols];

			line = line.substring(0, line.length() - 1);
			list = line.split(",");

			return list;
		}

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] rowList = lineToList(value.toString(), numOfCols);
			String year = rowList[yearIdx];
			String locId = rowList[locationColumnId];
			Integer fare;
			
			try{
				fare = new Integer(Integer.parseInt(rowList[fareAmountColumnId]));
			}
			catch(NumberFormatException e){
				fare = 0;
			}
			
			
			fa = new IntWritable(fare);
			
			locationId.set(locId);
			context.write(locationId,fa);
			
			
		}

	}

	public static class ReduceMean extends Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text locationId, Iterable<IntWritable> values, Context con) throws IOException, InterruptedException {
			int sum = 0, count = 0, mean = 0;

			for (IntWritable value : values) {
				sum += value.get();
				count++;
			}
			mean = sum / count;
			con.write(locationId, new IntWritable(mean));
		}
	}
}