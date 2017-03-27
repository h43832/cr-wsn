package wsn;

import java.util.*;
import java.awt.*;
import javax.swing.text.*;
import java.io.*;
import javax.swing.ImageIcon;
public class WSNApplicationDemo2 extends WSNApplication implements Runnable{
  WSN wsn;
  Vector waitV=new Vector();
  boolean isSleep=false;
  long longWaitTime=31536000000L;
  Thread myThread;
  String dataSrcFile="apps"+File.separator+"cr-wsn"+File.separator+"demo_ap2_datasrc.txt";
  String dataSrc[]={"6001-1","6001-2","6001-3","6002-1","6002-2","6002-3","6003-1","6003-2","6003-3",};
  public WSNApplicationDemo2() {
        initComponents();
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=520;
        int h2=390;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }

  public void init(WSN wsn){
      this.wsn=wsn;
      readDataSrc();

      myThread=new Thread(this);
      myThread.start();

    DefaultCaret caret = (DefaultCaret)jTextArea1.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    if(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))   jButton1.setText("Stop");
  }

  public void onExit(int type){

  }
  public boolean runInBackground(){
    return false;
  }
  public void run(){
    while(true){
        while(waitV.size()>0){

          int cnt2=0;
          byte[] b0={};

          DataClass dataClass=(DataClass)waitV.get(0);
          String data=wsn.getStringData(dataClass.data, 1, -1, -1);
          jTextArea1.append(wsn.formatter.format(new Date())+" "+dataClass.dataSrc+": "+data+"\r\n");
          if(wsn.isNumeric(data)){
           double da=Double.parseDouble(data);
           da=Math.round(da*100.0)/100.0;

           if(dataClass.dataSrc.equals(dataSrc[0])){
             jLabel20.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[1])){
             jLabel21.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[2])){
             jLabel22.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[3])){
             jLabel23.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[4])){
             jLabel24.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[5])){
             jLabel25.setText(""+da);
           } else if(dataClass.dataSrc.equals(dataSrc[6])){
             jLabel10.setText(""+(int)da);
           } else if(dataClass.dataSrc.equals(dataSrc[7])){
             jLabel26.setText(""+(int)da);
           }else if(dataClass.dataSrc.equals(dataSrc[8])){
             jLabel27.setText(""+(int)da);
           }
          }
            waitV.remove(0);
        }
        try{
            isSleep=true;
            Thread.sleep(longWaitTime);
            isSleep=false;
        }catch(InterruptedException e){
            isSleep=false;
        }
    }
  }
  public void setBlink(boolean onoff){

      jLabel1.setText(wsn.formatter.format(new Date()));
      if(onoff){
          jLabel1.setText(wsn.formatter.format(new Date()));
      }
  }
  public void setData(long time,String nodeid,String dataSrc,String data){
    if(jButton1.getText().equals("Stop")){

    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) myThread.interrupt();
    }
  }

  public void setStatus(String nodeId,String dataSrc[],int statusCode){}
    @SuppressWarnings("unchecked")

  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    jLabel14 = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    jLabel16 = new javax.swing.JLabel();
    jLabel17 = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();
    jLabel19 = new javax.swing.JLabel();
    jLabel20 = new javax.swing.JLabel();
    jLabel21 = new javax.swing.JLabel();
    jLabel22 = new javax.swing.JLabel();
    jLabel23 = new javax.swing.JLabel();
    jLabel24 = new javax.swing.JLabel();
    jLabel25 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jLabel26 = new javax.swing.JLabel();
    jLabel27 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("CR-WSN Environment System");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(null);

    jLabel1.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel1.setText("2015-02-24 09:56:00");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(270, 10, 140, 15);

    jLabel2.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel2.setText("Module ID");
    getContentPane().add(jLabel2);
    jLabel2.setBounds(20, 50, 70, 15);

    jLabel3.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel3.setForeground(new java.awt.Color(255, 102, 0));
    jLabel3.setText("1");
    getContentPane().add(jLabel3);
    jLabel3.setBounds(160, 40, 30, 30);

    jLabel4.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel4.setForeground(new java.awt.Color(0, 153, 51));
    jLabel4.setText("2");
    getContentPane().add(jLabel4);
    jLabel4.setBounds(260, 40, 40, 30);

    jLabel5.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel5.setForeground(new java.awt.Color(0, 204, 204));
    jLabel5.setText("3");
    getContentPane().add(jLabel5);
    jLabel5.setBounds(360, 40, 40, 30);

    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    getContentPane().add(jScrollPane1);
    jScrollPane1.setBounds(10, 171, 390, 90);

    jLabel6.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel6.setForeground(new java.awt.Color(51, 51, 255));
    jLabel6.setText("CO");
    getContentPane().add(jLabel6);
    jLabel6.setBounds(23, 75, 30, 20);

    jLabel7.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel7.setForeground(new java.awt.Color(51, 51, 255));
    jLabel7.setText("2");
    getContentPane().add(jLabel7);
    jLabel7.setBounds(50, 80, 10, 15);

    jLabel8.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel8.setForeground(new java.awt.Color(51, 51, 255));
    jLabel8.setText("Temperature");
    getContentPane().add(jLabel8);
    jLabel8.setBounds(10, 100, 130, 22);

    jLabel9.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel9.setForeground(new java.awt.Color(51, 51, 255));
    jLabel9.setText("Humidity");
    getContentPane().add(jLabel9);
    jLabel9.setBounds(10, 130, 100, 22);

    jLabel10.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel10.setForeground(new java.awt.Color(255, 102, 0));
    jLabel10.setText("XXX");
    getContentPane().add(jLabel10);
    jLabel10.setBounds(150, 70, 50, 30);

    jLabel11.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel11.setText("ppm");
    getContentPane().add(jLabel11);
    jLabel11.setBounds(200, 80, 30, 15);

    jLabel12.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel12.setText("ppm");
    getContentPane().add(jLabel12);
    jLabel12.setBounds(290, 80, 40, 15);

    jLabel13.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel13.setText("ppm");
    getContentPane().add(jLabel13);
    jLabel13.setBounds(400, 80, 30, 15);

    jLabel14.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel14.setText("?XC");
    getContentPane().add(jLabel14);
    jLabel14.setBounds(200, 100, 20, 20);

    jLabel15.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel15.setText("?XC");
    getContentPane().add(jLabel15);
    jLabel15.setBounds(290, 110, 20, 10);

    jLabel16.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel16.setText("?XC");
    getContentPane().add(jLabel16);
    jLabel16.setBounds(400, 110, 20, 10);

    jLabel17.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel17.setText("%RH");
    getContentPane().add(jLabel17);
    jLabel17.setBounds(200, 130, 40, 30);

    jLabel18.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel18.setText("%RH");
    getContentPane().add(jLabel18);
    jLabel18.setBounds(290, 130, 40, 30);

    jLabel19.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel19.setText("%RH");
    getContentPane().add(jLabel19);
    jLabel19.setBounds(400, 130, 40, 30);

    jLabel20.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel20.setForeground(new java.awt.Color(255, 102, 0));
    jLabel20.setText("XX.XX");
    getContentPane().add(jLabel20);
    jLabel20.setBounds(140, 100, 60, 22);

    jLabel21.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel21.setForeground(new java.awt.Color(0, 153, 51));
    jLabel21.setText("XX.XX");
    getContentPane().add(jLabel21);
    jLabel21.setBounds(240, 100, 50, 22);

    jLabel22.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel22.setForeground(new java.awt.Color(0, 204, 204));
    jLabel22.setText("XX.XX");
    getContentPane().add(jLabel22);
    jLabel22.setBounds(340, 100, 60, 22);

    jLabel23.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel23.setForeground(new java.awt.Color(255, 102, 0));
    jLabel23.setText("XX.XX");
    getContentPane().add(jLabel23);
    jLabel23.setBounds(140, 130, 50, 20);

    jLabel24.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel24.setForeground(new java.awt.Color(0, 153, 51));
    jLabel24.setText("XX.XX");
    getContentPane().add(jLabel24);
    jLabel24.setBounds(240, 130, 50, 22);

    jLabel25.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel25.setForeground(new java.awt.Color(0, 204, 204));
    jLabel25.setText("XX.XX");
    getContentPane().add(jLabel25);
    jLabel25.setBounds(340, 130, 60, 22);

    jButton1.setText("Start");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(303, 270, 80, 23);

    jLabel26.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel26.setForeground(new java.awt.Color(0, 153, 51));
    jLabel26.setText("XXX");
    getContentPane().add(jLabel26);
    jLabel26.setBounds(240, 70, 50, 30);

    jLabel27.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel27.setForeground(new java.awt.Color(0, 204, 204));
    jLabel27.setText("XXX");
    getContentPane().add(jLabel27);
    jLabel27.setBounds(340, 70, 50, 30);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
 if(jButton1.getText().equals("Start")){
     jButton1.setText("Stop");
 } else {
     jButton1.setText("Start");
 }
}
private void readDataSrc(){

 if(dataSrcFile!=null && dataSrcFile.length()>0){
      File f=new File(dataSrcFile);
      try{
      if(f.exists() && f.isFile()){
        FileInputStream in=new FileInputStream(dataSrcFile);
        BufferedReader d= new BufferedReader(new InputStreamReader(in));
        while(true){
	  String str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          dataSrc=wsn.w.csvLineToArray(str1);
          break;
        }
	in.close();
	d.close();
      }
           }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){
        e.printStackTrace();
    }
    }
}
private void formWindowClosing(java.awt.event.WindowEvent evt) {
  setVisible(false);
}

  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
  private javax.swing.JLabel jLabel19;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel20;
  private javax.swing.JLabel jLabel21;
  private javax.swing.JLabel jLabel22;
  private javax.swing.JLabel jLabel23;
  private javax.swing.JLabel jLabel24;
  private javax.swing.JLabel jLabel25;
  private javax.swing.JLabel jLabel26;
  private javax.swing.JLabel jLabel27;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea jTextArea1;

 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }
}