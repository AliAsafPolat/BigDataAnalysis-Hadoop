
import java.io.IOException;

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

public class StandartDeviation {
	

	public static void main(String[] args) throws Exception {
		
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);

		@SuppressWarnings("deprecation")
		Job j = new Job(c, "standart deviation");
		j.setJarByClass(StandartDeviation.class);
		j.setMapperClass(MapStandartDeviation.class);
		j.setReducerClass(ReduceStandartDeviation.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(FloatWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	public static class MapStandartDeviation extends Mapper<LongWritable, Text, Text, FloatWritable> {

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
				tip = 0.0;
			}
			
			
			ta = new FloatWritable(tip);
			
			locationId.set(locId);
			context.write(locationId,ta);
			
			
		}

	}

	public static class ReduceStandartDeviation extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text locationId, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {
			List<Float> valueList = new ArrayList<>();
			int count = 0;
			float sum = 0, mean = 0;
			float sumOfSquares = 0;
			float stdDev = 0;

			for (FloatWritable value : values) {
				sum += value.get();
				count++;
				valueList.add(value.get());
			}
			mean = sum / count;
			for (float value : valueList) {
				sumOfSquares += (value - mean) * (value - mean);
			}
			if(count == 1)
				count++;
			
			stdDev = (float) Math.sqrt(sumOfSquares / (count - 1));
			con.write(locationId, new FloatWritable(stdDev));
		}
	}
	
	
	
	
	
	
	
	
}