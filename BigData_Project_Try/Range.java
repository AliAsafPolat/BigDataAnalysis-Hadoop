
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

	// Belirli lokasyonlarda bırakılan bahşiş aralığını bulma.
	public static class MapRange extends Mapper<LongWritable, Text, Text, FloatWritable> {
		private final int kolon_sayisi = 18; 			// Veriset içerisindeki toplam kolon sayısı.
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

			// Verisetinden lokasyon id bilgisinin değişkene atanması
			String locId = satirListesi[lokasyon_kolon_id]; 

			Float bahsis; // bahşiş miktarının tutulacağı değişken

			try{
				// Verisetinde String olarak tutulan bahşiş değeri Float olarak dönüştürüldü.
				bahsis = new Float(Float.parseFloat(satirListesi[bahsis_miktari_kolon_id]));
			}
			catch(NumberFormatException e){
				bahsis = new Float(0);
			}
			// Elde edilen bahşiş değeri FloatWritable olarak dönüştürüldü.		
			ta = new FloatWritable(bahsis);		

			// key değeri olarak lokasyon id atandı.
			lokasyon_id.set(locId);

			// Elde edilen key - value değerleri context üzerine yazıldı.
			context.write(lokasyon_id,ta); 
		}

	}


	public static class ReduceRange extends Reducer<Text, FloatWritable, Text, FloatWritable> {
		public void reduce(Text lokasyon_id, Iterable<FloatWritable> values, Context con) throws IOException, InterruptedException {		
			float minVal, maxVal, range; // max,min, range değişkenleri
			maxVal = 0;
			minVal = 1000;
			for (FloatWritable value : values) {
				if (value.get() > maxVal)
					maxVal = value.get(); // max değer bulunur
				if (value.get() < minVal)
					minVal = value.get(); // min değer bulunur
			}
			range = maxVal - minVal; // max ve min arasındaki fark yani range bulunur
			con.write(lokasyon_id, new FloatWritable(range)); // key - value ikilisinin yazılması	
		}
	}

	
	
	
	
	
	
	
	
}
