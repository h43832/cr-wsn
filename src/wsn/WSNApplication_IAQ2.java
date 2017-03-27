package wsn;

import java.util.*;
import java.awt.*;
import javax.swing.text.*;
import javax.swing.*;
import java.io.*;
public class WSNApplication_IAQ2 extends WSNApplication implements Runnable{
  WSN wsn;
  Vector waitV=new Vector();
  boolean isSleep=false;
  long longWaitTime=31536000000L;
  Thread myThread;

  int rsp_len=0,time2=10,time2Now=0;
  boolean onoff2[]=new boolean[4];

  String configFile="apps"+File.separator+"cr-wsn"+File.separator+"ap_iaq_config.txt";
  String config[]={"COM9","2","4","5","0","","","","",};
  public WSNApplication_IAQ2() {
        initComponents();
        jLabel28.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("apps"+File.separator+"cr-wsn"+File.separator+"images"+File.separator+"Beyondlogo_20140115B.jpg")));
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=640;
        int h2=500;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }

  public void init(WSN wsn){
      this.wsn=wsn;
      readConfig();
      if(config[4].equals("1")) jCheckBox1.setSelected(true);
      else jCheckBox1.setSelected(false);
      for(int i=0;i<onoff2.length;i++) onoff2[i]=false;
      jLabel3.setText(config[1]);
      jLabel4.setText(config[2]);
      jLabel5.setText(config[3]);

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
  String lbID_1="XXX",lbID_2="XXX",lbID_3="XXX";
   int unsignedByte(byte b){
        return  (int) b & 0xff;
      }
  public void run(){
    while(true){
        while(waitV.size()>0){

          int cnt2=0, iValue=0,inx=0;
          byte[] b0={};
          double RH,Temp,Co2;

          DataClass dataClass=(DataClass)waitV.get(0);
          b0=wsn.getByteData(dataClass.data, -1, -1);
          String xValue="";
          boolean found=false;

          if(b0.length>7){
            for(int i=0;i<b0.length;i++){
              if((b0[i] & 0xF0) == 0xA0) {found=true; inx=i+1; break;}
            }

            if(found && inx<b0.length-7){
                if(b0[inx+4]==0x41 || b0[inx+4]==0x42 || b0[inx+4]==0x50){
                if(Byte.toString(b0[inx+2]).equals(config[1]) || Byte.toString(b0[inx+2]).equals(config[2]) || Byte.toString(b0[inx+2]).equals(config[3])) {
                xValue = "";
                int raw= unsignedByte(b0[inx+5]) * 256 + unsignedByte(b0[inx+6]);
                Co2 = raw;
                RH = ((double)Math.round (((double)raw) / 10.0))/10.0;
                Temp =((double)Math.round((((double)raw) *10.0/ 16.0) - 2731.5))/10.0;

                switch(b0[inx+4]){
                    case 0x41:
                        if(((int)b0[inx+2])==Integer.parseInt(config[1])) {jLabel23.setText(""+RH); onoff2[1]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[2])) {jLabel24.setText(""+RH); onoff2[2]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[3])) {jLabel25.setText(""+RH); onoff2[3]=true;}
                        xValue = " RH : " + RH;
                        break;
                    case 0x42:
                        if(((int)b0[inx+2])==Integer.parseInt(config[1])) {jLabel20.setText(""+Temp); onoff2[1]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[2])) {jLabel21.setText(""+Temp); onoff2[2]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[3])) {jLabel22.setText(""+Temp); onoff2[3]=true;}
                        xValue = " Temp : " + Temp;
                        break;
                    case 0x50:
                        if(((int)b0[inx+2])==Integer.parseInt(config[1])) {jLabel10.setText(""+Co2); onoff2[1]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[2])) {jLabel26.setText(""+Co2); onoff2[2]=true;}
                        if(((int)b0[inx+2])==Integer.parseInt(config[3])) {jLabel27.setText(""+Co2); onoff2[3]=true;}
                        xValue = " Co2 : " + Co2;
                        break;
                 }
                if(xValue.length()>0) {
                  jTextArea1.append("["+wsn.formatter3.format(new Date())+"] ID = "+b0[inx+2]+" "+xValue+"\r\n");
                }
                }
              }
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

      if(onoff){
          time2Now++;
          jLabel1.setText(wsn.formatter.format(new Date()));
          if(jTextArea1.toString().length()>2000) jTextArea1.setText("");
          if(time2Now>=time2){
            if(jCheckBox1.isSelected()){
              for(int i=1;i<4;i++){
                if(onoff2[i]){
              String fname=config[i]+"_"+wsn.formatter2.format(new Date()).subSequence(0,8)+".csv";
              String data="";
              switch(i){
                case 1:
                  data=wsn.formatter3.format(new Date())+","+jLabel23.getText()+","+jLabel20.getText()+","+jLabel10.getText()+"\r\n";
                  break;
                case 2:
                  data=wsn.formatter3.format(new Date())+","+jLabel24.getText()+","+jLabel21.getText()+","+jLabel26.getText()+"\r\n";
                  break;
                case 3:
                  data=wsn.formatter3.format(new Date())+","+jLabel25.getText()+","+jLabel22.getText()+","+jLabel27.getText()+"\r\n";
                  break;
              }
               try{
                 FileOutputStream out;
                     out = new FileOutputStream (wsn.dataDir+File.separator+fname,true);
	             out.write(data.getBytes());
      	             out.close();
               } catch (IOException e){
                   e.printStackTrace();
               }
             }
            }
          }
          time2Now=0;
      }
      }
  }
  public void setData(long time,String nodeid,String dataSrc,String data){
    if(!dataSrc.equals(this.config[0])) System.out.println("dataSrc="+dataSrc+",config[0]="+config[0]);
    if(jButton1.getText().equals("Stop") && dataSrc.equals(this.config[0])){

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
    jButton2 = new javax.swing.JButton();
    jCheckBox1 = new javax.swing.JCheckBox();
    jLabel28 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("CR-WSN  IAQ  System");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(null);

    jLabel1.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel1.setForeground(new java.awt.Color(0, 102, 0));
    jLabel1.setText("2015-02-24 09:56:00");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(460, 10, 150, 17);

    jLabel2.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel2.setText("Module ID");
    getContentPane().add(jLabel2);
    jLabel2.setBounds(20, 40, 110, 30);

    jLabel3.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel3.setForeground(new java.awt.Color(255, 102, 0));
    jLabel3.setText("2");
    getContentPane().add(jLabel3);
    jLabel3.setBounds(180, 40, 40, 30);

    jLabel4.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel4.setForeground(new java.awt.Color(0, 153, 51));
    jLabel4.setText("4");
    getContentPane().add(jLabel4);
    jLabel4.setBounds(310, 40, 40, 30);

    jLabel5.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel5.setForeground(new java.awt.Color(0, 204, 204));
    jLabel5.setText("5");
    getContentPane().add(jLabel5);
    jLabel5.setBounds(470, 40, 40, 30);

    jTextArea1.setColumns(20);
    jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 14));
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    getContentPane().add(jScrollPane1);
    jScrollPane1.setBounds(10, 171, 600, 90);

    jLabel6.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel6.setForeground(new java.awt.Color(51, 51, 255));
    jLabel6.setText("CO");
    getContentPane().add(jLabel6);
    jLabel6.setBounds(23, 75, 50, 20);

    jLabel7.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel7.setForeground(new java.awt.Color(51, 51, 255));
    jLabel7.setText("2");
    getContentPane().add(jLabel7);
    jLabel7.setBounds(60, 80, 20, 20);

    jLabel8.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel8.setForeground(new java.awt.Color(51, 51, 255));
    jLabel8.setText("Temperature");
    getContentPane().add(jLabel8);
    jLabel8.setBounds(10, 100, 160, 29);

    jLabel9.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel9.setForeground(new java.awt.Color(51, 51, 255));
    jLabel9.setText("Humidity");
    getContentPane().add(jLabel9);
    jLabel9.setBounds(10, 130, 120, 29);

    jLabel10.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel10.setForeground(new java.awt.Color(255, 102, 0));
    jLabel10.setText("XXX");
    getContentPane().add(jLabel10);
    jLabel10.setBounds(170, 70, 80, 30);

    jLabel11.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel11.setText("ppm");
    getContentPane().add(jLabel11);
    jLabel11.setBounds(250, 70, 30, 20);

    jLabel12.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel12.setText("ppm");
    getContentPane().add(jLabel12);
    jLabel12.setBounds(390, 70, 40, 20);

    jLabel13.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel13.setText("ppm");
    getContentPane().add(jLabel13);
    jLabel13.setBounds(550, 70, 30, 17);

    jLabel14.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel14.setText("?XC");
    getContentPane().add(jLabel14);
    jLabel14.setBounds(250, 100, 20, 20);

    jLabel15.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel15.setText("?XC");
    getContentPane().add(jLabel15);
    jLabel15.setBounds(390, 100, 20, 20);

    jLabel16.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel16.setText("?XC");
    getContentPane().add(jLabel16);
    jLabel16.setBounds(550, 100, 20, 20);

    jLabel17.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel17.setText("%RH");
    getContentPane().add(jLabel17);
    jLabel17.setBounds(250, 130, 40, 20);

    jLabel18.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel18.setText("%RH");
    getContentPane().add(jLabel18);
    jLabel18.setBounds(390, 130, 40, 20);

    jLabel19.setFont(new java.awt.Font("Arial", 0, 14));
    jLabel19.setText("%RH");
    getContentPane().add(jLabel19);
    jLabel19.setBounds(550, 130, 40, 20);

    jLabel20.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel20.setForeground(new java.awt.Color(255, 102, 0));
    jLabel20.setText("XX.XX");
    getContentPane().add(jLabel20);
    jLabel20.setBounds(170, 100, 80, 29);

    jLabel21.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel21.setForeground(new java.awt.Color(0, 153, 51));
    jLabel21.setText("XX.XX");
    getContentPane().add(jLabel21);
    jLabel21.setBounds(300, 100, 80, 29);

    jLabel22.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel22.setForeground(new java.awt.Color(0, 204, 204));
    jLabel22.setText("XX.XX");
    getContentPane().add(jLabel22);
    jLabel22.setBounds(450, 100, 90, 29);

    jLabel23.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel23.setForeground(new java.awt.Color(255, 102, 0));
    jLabel23.setText("XX.XX");
    getContentPane().add(jLabel23);
    jLabel23.setBounds(170, 130, 80, 29);

    jLabel24.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel24.setForeground(new java.awt.Color(0, 153, 51));
    jLabel24.setText("XX.XX");
    getContentPane().add(jLabel24);
    jLabel24.setBounds(300, 130, 80, 29);

    jLabel25.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel25.setForeground(new java.awt.Color(0, 204, 204));
    jLabel25.setText("XX.XX");
    getContentPane().add(jLabel25);
    jLabel25.setBounds(450, 130, 80, 29);

    jButton1.setFont(new java.awt.Font("?s?????", 0, 14));
    jButton1.setText("Start");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(400, 270, 90, 30);

    jLabel26.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel26.setForeground(new java.awt.Color(0, 153, 51));
    jLabel26.setText("XXX");
    getContentPane().add(jLabel26);
    jLabel26.setBounds(300, 70, 80, 30);

    jLabel27.setFont(new java.awt.Font("Arial", 0, 24));
    jLabel27.setForeground(new java.awt.Color(0, 204, 204));
    jLabel27.setText("XXX");
    getContentPane().add(jLabel27);
    jLabel27.setBounds(450, 70, 70, 30);

    jButton2.setFont(new java.awt.Font("?s?????", 0, 14));
    jButton2.setText("Clear");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton2);
    jButton2.setBounds(510, 270, 100, 30);

    jCheckBox1.setFont(new java.awt.Font("?s?????", 0, 14));
    jCheckBox1.setText("Save log,    Log Sec: ");
    getContentPane().add(jCheckBox1);
    jCheckBox1.setBounds(20, 270, 160, 30);

    jLabel28.setText("jLabel28");
    getContentPane().add(jLabel28);
    jLabel28.setBounds(10, 360, 460, 93);

    jComboBox1.setFont(new java.awt.Font("?s?????", 0, 14));
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "20", "30", "45", "60", "120", "180", "240" }));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox1ActionPerformed(evt);
      }
    });
    getContentPane().add(jComboBox1);
    jComboBox1.setBounds(190, 270, 60, 30);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
 if(jButton1.getText().equals("Start")){
     jButton1.setText("Stop");
 } else {
     jButton1.setText("Start");
 }
}
private void readConfig(){

 if(configFile!=null && configFile.length()>0){
      File f=new File(configFile);
      try{
      if(f.exists() && f.isFile()){
        FileInputStream in=new FileInputStream(configFile);
        BufferedReader d= new BufferedReader(new InputStreamReader(in));
        while(true){
	  String str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          config=wsn.w.csvLineToArray(str1);
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

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
 jTextArea1.setText("");
}

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
   String sel=(String)jComboBox1.getSelectedItem();
   time2=Integer.parseInt(sel);
}

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JComboBox jComboBox1;
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
  private javax.swing.JLabel jLabel28;
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