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

public class Mean_Operation {

	public static void main(String[] args) throws Exception {
		
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);

		@SuppressWarnings("deprecation")
		Job j = new Job(c, "summation");
		j.setJarByClass(Summation.class);
		j.setMapperClass(MapSummation.class);
		j.setReducerClass(ReduceSummation.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	public static class MapSummation extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final int numOfCols = 18; // kolon sayısı   // locationColumnID = 8 , passengersColumnId = 9
		private Text locationId = new Text(); // key 
		private final int locationColumId = 8;
		private final int passengerCountColumnId = 3;
		private final static IntWritable pc; // value     passeng count 

		private String[] lineToList(String line, int num_of_cols) {
			String[] list = new String[num_of_cols];

			line = line.substring(0, line.length() - 1);
			list = line.split(",");

			return list;
		}
		
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			
            String[] rowList = lineToList(value.toString(), numOfCols);
			String line = value.toString();
			String locId = rowList[locationColumnID];
			Integer passengCount = new Integer(Integer.parseInt(rowList[passengerCountColumnId])); 
			pc = new IntWritable(passengCount);
			
			locationId.set(locId);
			context.write(locationId,pc);
			
		
        }		

	}
					
	public static class ReduceSummation extends Reducer<Text, IntWritable, Text, IntWritable> {
							
		public void reduce(Text locationId, Iterable<IntWritable> values, Context con) throws IOException, InterruptedException {
			int sum = 0;

			for (IntWritable value : values) {
				sum += value.get();
			}
			
			con.write(yearAge, new IntWritable(mean));
		}
	}
}