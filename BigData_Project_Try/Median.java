
import java.io.IOException;

import java.util.Collections;
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

public class Median {
	

	public static void main(String[] args) throws Exception {
		
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);

		@SuppressWarnings("deprecation")
		Job j = new Job(c, "median");
		j.setJarByClass(Median.class);
		j.setMapperClass(MapMedian.class);
		j.setReducerClass(ReduceMedian.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(FloatWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	// Seyahat edilen lokasyonlara göre bahşişlerin ortancasını bulma. 
	public static class MapMedian extends Mapper<LongWritable, Text, Text, FloatWritable> {

		private final int kolon_sayisi = 18; 			// Veriset içerisindeki toplam kolon sayısı.
		private final int lokasyon_kolon_id = 8;
		private final int bahsis_miktari_kolon_id = 13; // key,value ikilisine denk gelen kolon numaraları
		private Text lokasyon_id = new Text(); 			// key - lokasyon id
		private static FloatWritable ta; 				// value - bahşiş miktarı  (tip amount)

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

			// Verisetinden lokasyon id bilgisinin değişkene atanması. 
			String locId = satirListesi[lokasyon_kolon_id]; 

			Float bahsis; 	// Bahşiş miktarını tutan değişken 

			try{
				// Verisetinde String olarak tutulan bahsis değeri Float değerine cast edilir. 		
				bahsis = new Float(Float.parseFloat(satirListesi[bahsis_miktari_kolon_id]));
			}
			catch(NumberFormatException e){
				bahsis = new Float(0);
			}
			// Bahşiş miktarı FloatWritable olarak dönüştürülür.
			ta = new FloatWritable(bahsis);

			// Key değeri olarak lokasyon id atanır.	
			lokasyon_id.set(locId);

			// Elde edilen key - value değerleri context üzerine yazılır.
			context.write(lokasyon_id,ta); 
		}
	}




	public static class ReduceMedian extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text lokasyon_id, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {

			float median; 		// median değerinin tutulduğu değişken.
			int ortanca_deger; 	// Ortanca değeri tutan indis.
			List<Float> valueList = new ArrayList<Float>();

			for (FloatWritable value : values) {
				valueList.add(value.get());
			}

			Collections.sort(valueList); // Bahşiş değerleri sıralanır.
			int size = valueList.size(); // Listenin büyüklüğü alınır.

			if (size % 2 == 0) { 		 // Listenin eleman sayısı çift ise :
				ortanca_deger = size / 2;

				// Ortadaki 2 elemanın ortalaması alınır ve medyan bulunur
				median = (valueList.get(ortanca_deger - 1) + valueList.get(ortanca_deger)) / 2; 

			} else { 					 // Listenin eleman sayısı tek ise :

				ortanca_deger = (size + 1) / 2; 	 // Ortadaki elemanı gösteren indis.
				median = valueList.get(ortanca_deger - 1);  // Ortadaki eleman medyan olarak bulunur.
			}

			// Elde edilen değerler context üzerine yazılır.
			con.write(lokasyon_id, new FloatWritable(median)); // key - value ikilisinin yazılması						
		}
	}

	
	
	
	
	
}
