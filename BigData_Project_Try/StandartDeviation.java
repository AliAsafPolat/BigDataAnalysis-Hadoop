
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

	// Farklı lokasyonlara giden yolcuların bıraktığı bahşiş miktarlarının standart sapmasını bulma
	public static class MapStandartDeviation extends Mapper<LongWritable, Text, Text, FloatWritable> {
		private final int kolon_sayisi = 18; 			// veriset içerisindeki toplam kolon sayısı
		private final int lokasyon_kolon_id = 8;
		private final int bahsis_miktari_kolon_id = 13; // key,value ikilisine denk gelen kolon numaraları
		private Text lokasyon_id = new Text(); 			// key - lokasyon 
		private static FloatWritable ta; 				// value - bahşiş (tip amount)

		// .csv dosyası içerisindeki bir satırı alır ve kolon sırasına göre diziye atıp döndürür.
		private String[] satirListele(String satir, int kolonSayisi) {

			String[] list = new String[kolonSayisi];
			satir = satir.substring(0, satir.length() - 1);
			list = satir.split(",");
			return list;
		}

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] satirListesi = satirListele(value.toString(), kolon_sayisi);

			// Verisetinden lokasyon id bilgisinin değişkene atanması.
			String locId = satirListesi[lokasyon_kolon_id]; 

			Float bahsis; 		// Bahşiş miktarının tutulacağı değişken.

			try{
				// Verisetinde String olarak verilen bahşiş değerleri Float olarak dönüştürülür.
				bahsis = new Float(Float.parseFloat(satirListesi[bahsis_miktari_kolon_id]));
			}
			catch(NumberFormatException e){
				bahsis = new Float(0);
			}

			// Bahşiş değeri FloatWritable olarak dönüştürüldü.
			ta = new FloatWritable(bahsis);		

			// key değeri olarak lokasyon id atanır.
			lokasyon_id.set(locId);

			// Elde edilen değerler context üzerine yazılır.
			context.write(lokasyon_id,ta); 		
		}
	}



	public static class ReduceStandartDeviation extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text lokasyon_id, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {
			List<Float> valueList = new ArrayList<>();
			int count = 0; 				// Toplam eleman sayısını tutan değişken.
			float sum = 0, mean = 0; 	// Toplam ve ortalama değerlerini tutan değişken.
			float sumOfSquares = 0; 	// Kareler toplamı değerini tutan değişken.
			float stdDev = 0; 			// Standart sapma değerini tutan değişken.

			for (FloatWritable value : values) {
				sum += value.get(); 		// Toplamlar bulunur.
				count++; 					// Eleman sayısı arttırılır.
				valueList.add(value.get()); // Listeye eklenir.
			}

			mean = sum / count; 			// Ortalama hesaplanır.

			for (float value : valueList) {
				// Kareler toplamı bulunur.
				sumOfSquares += (value - mean) * (value - mean);  
			}
			if(count == 1) // Eğer 1 eleman varsa standart sapmanın doğru hesaplanması için count 1 arttılır.
				count++;

			// Standart sapma değeri bulunur.
			stdDev = (float) Math.sqrt(sumOfSquares / (count - 1));

			// Elde edilen değerler context üzerine yazılır.
			con.write(lokasyon_id, new FloatWritable(stdDev)); 
		}
	}
	
	
	
	
	
	
}
