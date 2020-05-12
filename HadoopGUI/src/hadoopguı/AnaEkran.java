/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hadoopguı;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Ali_Asaf
 */
public class AnaEkran extends javax.swing.JFrame {
    String file_tam_path;
    private static final String hadoop_input_path="/gui_kodlari/input";
    private static final String hadoop_output_path="/gui_kodlari/outputs";
    private static final String yer_kodlari_path="/home/hadoop/yer_kodlari.csv";
    private int file_numarasi;
    private String last_output_path;
    /**
     * Creates new form AnaEkran
     */
    public AnaEkran() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        jButtonSonuc.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Jpanel_AnaEkran = new javax.swing.JPanel();
        btnDosyaSec = new javax.swing.JButton();
        jComboBox_fonksiyonlar = new javax.swing.JComboBox();
        jlabel_FonksiyonSec = new javax.swing.JLabel();
        jlabel_secilen_dosya_adi = new javax.swing.JLabel();
        jButtonBaslat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jButtonSonuc = new javax.swing.JButton();
        jButtonSistemBaslat = new javax.swing.JButton();
        jButtonSistemDurdur = new javax.swing.JButton();
        jLabelTanitim = new javax.swing.JLabel();
        jButtonYardim = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Jpanel_AnaEkran.setOpaque(false);

        btnDosyaSec.setLabel("Dosya Seç");
        btnDosyaSec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDosyaSecMouseClicked(evt);
            }
        });
        btnDosyaSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDosyaSecActionPerformed(evt);
            }
        });

        jComboBox_fonksiyonlar.setMaximumRowCount(5);
        jComboBox_fonksiyonlar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mean", "Summation", "Standart Dev.", "Min-Max-Range", "Median" }));

        jlabel_FonksiyonSec.setText("Fonksiyon Seç :");

        jlabel_secilen_dosya_adi.setText("Dosya Seçilmedi");

        jButtonBaslat.setText("Başlat");
        jButtonBaslat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBaslatMouseClicked(evt);
            }
        });

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPane1.setViewportView(jTextAreaLog);

        jButtonSonuc.setText("Sonuçlar");
        jButtonSonuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSonucMouseClicked(evt);
            }
        });

        jButtonSistemBaslat.setText("HDFS+");
        jButtonSistemBaslat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSistemBaslatMouseClicked(evt);
            }
        });
        jButtonSistemBaslat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSistemBaslatActionPerformed(evt);
            }
        });

        jButtonSistemDurdur.setText("HDFS -");
        jButtonSistemDurdur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSistemDurdurMouseClicked(evt);
            }
        });

        jLabelTanitim.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelTanitim.setText("Hadoop GUI");

        jButtonYardim.setBackground(new java.awt.Color(120, 253, 127));
        jButtonYardim.setText("Yardım");
        jButtonYardim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonYardimMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Jpanel_AnaEkranLayout = new javax.swing.GroupLayout(Jpanel_AnaEkran);
        Jpanel_AnaEkran.setLayout(Jpanel_AnaEkranLayout);
        Jpanel_AnaEkranLayout.setHorizontalGroup(
            Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpanel_AnaEkranLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpanel_AnaEkranLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDosyaSec)
                                .addGap(54, 54, 54))
                            .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                                .addComponent(jlabel_FonksiyonSec)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                                .addComponent(jButtonSonuc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSistemBaslat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                                .addComponent(jButtonSistemDurdur)
                                .addGap(28, 28, 28)
                                .addComponent(jButtonBaslat))
                            .addComponent(jComboBox_fonksiyonlar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlabel_secilen_dosya_adi, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabelTanitim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonYardim)))
                .addContainerGap())
        );
        Jpanel_AnaEkranLayout.setVerticalGroup(
            Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel_AnaEkranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTanitim)
                    .addComponent(jButtonYardim, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDosyaSec)
                    .addComponent(jlabel_secilen_dosya_adi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_fonksiyonlar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabel_FonksiyonSec))
                .addGap(21, 21, 21)
                .addGroup(Jpanel_AnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBaslat)
                    .addComponent(jButtonSonuc)
                    .addComponent(jButtonSistemBaslat)
                    .addComponent(jButtonSistemDurdur))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Jpanel_AnaEkran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Jpanel_AnaEkran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDosyaSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDosyaSecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDosyaSecActionPerformed

    private void btnDosyaSecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDosyaSecMouseClicked
 
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        if(file!=null)
            jlabel_secilen_dosya_adi.setText(file);
        
        
        file_tam_path = dialog.getDirectory()+dialog.getFile();

    }//GEN-LAST:event_btnDosyaSecMouseClicked
    // Dosya seç ile seçilen dosyayı hdfs sistemine yukler.
    private void sisteme_yukle(){
            jTextAreaLog.append("Dosya sisteme yukleniyor...\n");
    try{
            String []command = {"/home/hadoop/sh_dosyalari/sisteme_yukle.sh",file_tam_path,hadoop_input_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
            }
            jTextAreaLog.append("Dosya sisteme yuklendi.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    }
    
    private void daemon_baslat(){
        
    try{
            
            String []command = {"/home/hadoop/sh_dosyalari/daemon_baslat.sh"};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Daemonlar Baslatildi.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void daemon_durdur(){
        
    try{
            
            String []command = {"/home/hadoop/sh_dosyalari/daemon_durdur.sh"};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Daemonlar Durduruldu.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void mean_fonksiyonu_calistir(){
        
    try{
            
            Random rand = new Random();
            file_numarasi = rand.nextInt(2500);
            last_output_path = hadoop_output_path+"/output"+String.valueOf(file_numarasi);
            String []command = {"/home/hadoop/sh_dosyalari/func_mean_baslat.sh",hadoop_input_path,last_output_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Mean fonksiyonu çalıştırıldı.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void median_fonksiyonu_calistir(){
        
    try{
            
            Random rand = new Random();
            file_numarasi = rand.nextInt(2500);
            last_output_path = hadoop_output_path+"/output"+String.valueOf(file_numarasi);
            String []command = {"/home/hadoop/sh_dosyalari/func_median_baslat.sh",hadoop_input_path,last_output_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Median fonksiyonu çalıştırıldı.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void range_fonksiyonu_calistir(){
        
    try{
            
            Random rand = new Random();
            file_numarasi = rand.nextInt(2500);
            last_output_path = hadoop_output_path+"/output"+String.valueOf(file_numarasi);
            String []command = {"/home/hadoop/sh_dosyalari/func_minmaxrange_baslat.sh",hadoop_input_path,last_output_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Range fonksiyonu çalıştırıldı.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void standart_sapma_fonksiyonu_calistir(){
        
    try{
            
            Random rand = new Random();
            file_numarasi = rand.nextInt(2500);
            last_output_path = hadoop_output_path+"/output"+String.valueOf(file_numarasi);
            String []command = {"/home/hadoop/sh_dosyalari/func_standartdeviation_baslat.sh",hadoop_input_path,last_output_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Standart Dev. fonksiyonu çalıştırıldı.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void summation_fonksiyonu_calistir(){
        
    try{
            
            Random rand = new Random();
            file_numarasi = rand.nextInt(2500);
            last_output_path = hadoop_output_path+"/output"+String.valueOf(file_numarasi);
            String []command = {"/home/hadoop/sh_dosyalari/func_summation_baslat.sh",hadoop_input_path,last_output_path};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
                
            }
            jTextAreaLog.append("Summation fonksiyonu çalıştırıldı.\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    
    private ArrayList<String> sonuclari_getir(){
        ArrayList<String> sonuclar = new ArrayList<String>();
        
    try{
           
            String sonuc_file=last_output_path+"/part-r-00000"; 
            String []command = {"/home/hadoop/sh_dosyalari/sonuclari_getir.sh",sonuc_file};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                sonuclar.add(line);
            }
            jTextAreaLog.append("Sonuclar Getiriliyor...\n");
            
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
        return sonuclar;
    }
    
    private void input_path_temizle(){
        
    try{ 
            String []command = {"/home/hadoop/sh_dosyalari/input_path_sil.sh"};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                System.out.println(line);
            }
   
        }catch(Exception e){
            e.printStackTrace();
            jTextAreaLog.append("Hata Oluştu! \n");
        } 
    
    }
    
    private void jButtonBaslatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBaslatMouseClicked
        // TODO add your handling code here:
        String func = jComboBox_fonksiyonlar.getSelectedItem().toString();
        // Eğer file seçilmemişse hata versin seçilmişse sisteme yuklesin
        // ve çalıştırsın.
        if(file_tam_path==null)
            JOptionPane.showMessageDialog(null, "Dosya Seçilmedi");
        else{
            // input pathde yalnızca seçilen dosya olmasını sağlar.
            input_path_temizle();
            
            // Bu kısımda gerekli hadoop kodlarını çalıştıracağız.
            // Seçilen dosyayı sisteme yükler.
            sisteme_yukle();
            
            switch(func){
                case "Mean":
                    mean_fonksiyonu_calistir();
                    System.out.println("Mean fonksiyonu");
                    break;
                case "Standart Dev.":
                    standart_sapma_fonksiyonu_calistir();
                    System.out.println("Standart Sapma fonksiyonu");
                    break;
                case "Summation":
                    summation_fonksiyonu_calistir();
                    System.out.println("Summation Fonksiyonu");
                    break;
                case "Min-Max-Range":
                    range_fonksiyonu_calistir();
                    System.out.println("Range fonksiyonu");
                   break;
                case "Median":
                    median_fonksiyonu_calistir();
                    System.out.println("Median fonksiyonu");
                    break;
            }
            jButtonSonuc.setEnabled(true);
        }     
    }//GEN-LAST:event_jButtonBaslatMouseClicked

    private void jButtonSonucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSonucMouseClicked
        
    ArrayList<String> sonuclar = sonuclari_getir();
    ArrayList<String> yerler = csv_oku();
    sonuclar.remove(sonuclar.size()-1);
    
    String func = jComboBox_fonksiyonlar.getSelectedItem().toString();
    String ek;
    if(func.compareTo("Summation")==0)
        ek = " kisi";
    else
        ek = " $";
    
    for (String sonuc:sonuclar){
        String []buff = sonuc.split("\t");
        
        jTextAreaLog.append(yerler.get(Integer.valueOf(buff[0]))+"\t"+buff[1]+ek+"\n");
    }
    jButtonSonuc.setEnabled(false);
    }//GEN-LAST:event_jButtonSonucMouseClicked

    public ArrayList<String> csv_oku(){
        ArrayList<String> mekanlar = new ArrayList<String>();
        String row;
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(yer_kodlari_path));
			try {
				while ((row = csvReader.readLine()) != null) {
				    String[] data = row.split(",");
				    mekanlar.add(data[2]);
                                    
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
        return mekanlar;
    }
    
    private void jButtonSistemBaslatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSistemBaslatMouseClicked
        jTextAreaLog.append("Sistem Başlatılıyor...\n");
        daemon_baslat();
    
    }//GEN-LAST:event_jButtonSistemBaslatMouseClicked

    private void jButtonSistemDurdurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSistemDurdurMouseClicked
        jTextAreaLog.append("Sistem Durduruluyor...\n");
        daemon_durdur();    
        
    }//GEN-LAST:event_jButtonSistemDurdurMouseClicked

    private void jButtonSistemBaslatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSistemBaslatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSistemBaslatActionPerformed

    private void jButtonYardimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonYardimMouseClicked
    YardimEkrani ekran = new YardimEkrani();
    ekran.setVisible(true);
    
    }//GEN-LAST:event_jButtonYardimMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnaEkran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnaEkran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnaEkran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnaEkran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnaEkran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jpanel_AnaEkran;
    private javax.swing.JButton btnDosyaSec;
    private javax.swing.JButton jButtonBaslat;
    private javax.swing.JButton jButtonSistemBaslat;
    private javax.swing.JButton jButtonSistemDurdur;
    private javax.swing.JButton jButtonSonuc;
    private javax.swing.JButton jButtonYardim;
    private javax.swing.JComboBox jComboBox_fonksiyonlar;
    private javax.swing.JLabel jLabelTanitim;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JLabel jlabel_FonksiyonSec;
    private javax.swing.JLabel jlabel_secilen_dosya_adi;
    // End of variables declaration//GEN-END:variables
}
