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

public class Summation {

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

	// Belirli bir lokasyona seyahat eden yolcu sayısını bulma.
	public static class MapSummation extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final int kolon_sayisi = 18; 		// Veriset içerisindeki toplam kolon sayısı.
		private Text lokasyon_id = new Text(); 		// key - lokasyon id
		private static IntWritable pc; 				// value - yolcu sayısı (passenger count)

		private final int lokasyon_kolon_id = 8; 	// key'e (lokasyon id) denk gelen kolon numarası
		private final int yolcu_sayisi_kolon_id = 3;// value'ya(yolcu sayısı) denk gelen kolon numarası


		// .csv dosyası içerisindeki bir satırı alır ve kolon sırasına göre diziye atıp döndürür.
		private String[] satirListele(String satir, int kolonSayisi) {

			String[] list = new String[kolonSayisi];
			satir = satir.substring(0, satir.length() - 1);
			list = satir.split(",");
			return list;
		}


		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// Veri setindeki bir satırı alır.		
			String[] satirListesi = satirListele(value.toString(), kolon_sayisi);
			String satir = value.toString();

			// Verisetinden lokasyon id bilgisinin değişkene atanması.
			String locId = satirListesi[lokasyon_kolon_id]; 

			Integer yolcuSayisi; 	// Yolcu sayısını tutan değişken

			try{
				// Verisetinde string olarak tutulan yolcu sayısı integer değerine cast edilir.
				yolcuSayisi = new Integer(Integer.parseInt(satirListesi[yolcu_sayisi_kolon_id])); 
			}
			catch(NumberFormatException e){
				yolcuSayisi = 0;
			}

			// Yolcu sayisi IntWritable olarak dönüştürülür.
			pc = new IntWritable(yolcuSayisi);

			// key değeri olarak lokasyon id atanır.
			lokasyon_id.set(locId);

			// key - value değerleri alınır ve context üzerine yazılır.
			context.write(lokasyon_id,pc); 
		}		
	}


	public static class ReduceSummation extends Reducer<Text, IntWritable, Text, IntWritable> {							

		public void reduce(Text lokasyon_id, Iterable<IntWritable> values, Context con) throws IOException, InterruptedException {

			int sum = 0; 	// Toplamın tutulacağı değişken

			for (IntWritable value : values) {
				sum += value.get();		// Toplam hesaplanır.
			}		

			// Hesaplanan toplam değer key - value şeklinde context üzerine yazılır.
			con.write(lokasyon_id, new IntWritable(sum));

		}
	}
}
