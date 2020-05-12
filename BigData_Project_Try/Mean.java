
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
		j.setOutputValueClass(FloatWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	
	// Belirli bir lokasyona giderken ödenen ortalama ücreti bulma
	public static class MapMean extends Mapper<LongWritable, Text, Text, FloatWritable> { 

		private final int kolon_sayisi = 18; 		// Veriset içerisindeki toplam kolon sayısı
		private final int lokasyon_kolon_id = 8;	// Datasette key,value ikilisine denk gelen kolon numaraları
		private final int ucret_kolon_id = 10; 
		private Text lokasyon_id = new Text(); 		// key 	 - lokasyon id
		private static FloatWritable fa; 		// value - ücret  (fare amount)   

		// .csv dosyası içerisindeki bir satırı alır ve kolon sırasına göre diziye atıp döndürür.
		private String[] satirListele(String satir, int kolonSayisi) {

			String[] list = new String[kolonSayisi];

			satir = satir.substring(0, satir.length() - 1);
			list = satir.split(",");
			return list;
		}


		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			// Veri setindeki bir satırı alır.
			String[] satirListesi = satirListele(value.toString(), kolon_sayisi);

			// Verisetindeki lokasyon id bilgisi değişkene atanır.
			String locId = satirListesi[lokasyon_kolon_id]; 


			Float ucret; // Ücretin tutulacağı değişken.

			try{ 
				// Verisetinden string olarak okunan ücret float değerine cast edilir.
				ucret = new Float(Float.parseFloat(satirListesi[ucret_kolon_id])); 
			}
			catch(NumberFormatException e){
				ucret = 0.0;
			}
			// Elde edilen sonuç FloatWritable şekline çevrilir.
			fa = new FloatWritable(ucret);

			// Key değeri olarak lokasyon id bilgisi atanır.
			lokasyon_id.set(locId);

			// Elde edilen key - value pairleri context üzerine yazılır.
			context.write(lokasyon_id,fa); 	
		}

	}




	public static class ReduceMean extends Reducer<Text, FloatWritable, Text, FloatWritable> {

		// Key değeri olarak lokasyon_id kullanılır.
		public void reduce(Text lokasyon_id, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {
			float sum = 0, mean = 0;
			int count = 0;
			for (FloatWritable value : values) {
				sum += value.get(); // Toplamın tutulduğu değişken.
				count++;  	    // Değer sayısının tutulduğu değişken.
			}
			mean = sum / count; 	    // Ortalamanın hesaplanması.

			// Reduce işleminden sonra değerler context üzerine yazılır. 
			con.write(lokasyon_id, new FloatWritable(mean)); 
		}
	}
}
