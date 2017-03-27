
package wsn;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import y.ylib.*;
/**
 *
 * @author Administrator
 */
public class WSNDeliverer  extends WSNApplication implements Runnable {

  public int programId=0;
  long longWaitTime=31536000000L,sendInterval=0L;
  boolean onoff=false,isSleep=false,run_in_background=false; 
  WSN wsn;

  Thread myThread=null;
  Vector waitV=new Vector();
  String defaultRedirectorFile="apps"+File.separator+"cr-wsn"+File.separator+"redirector",redirectorFile="";
  public Properties props=new Properties();
  String dataSource1="6001-1", dataSource2="COM131";
  boolean src1Connected=false,src2Connected=false;

  public WSNDeliverer() {
    initComponents();
                int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=800;
        int h2=550;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jPanel4 = new javax.swing.JPanel();
    jLabel6 = new javax.swing.JLabel();
    jPanel7 = new javax.swing.JPanel();
    jLabel12 = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jLabel9 = new javax.swing.JLabel();
    jPanel6 = new javax.swing.JPanel();
    jLabel10 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jLabel11 = new javax.swing.JLabel();
    jTextField4 = new javax.swing.JTextField();
    jPanel9 = new javax.swing.JPanel();
    jLabel17 = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("cr-WSN  Deliverer 1.09.11");
    setBackground(new java.awt.Color(204, 255, 255));
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(null);

    jPanel1.setBackground(new java.awt.Color(0, 0, 204));
    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Sensor ??", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("MingLiU", 1, 18), new java.awt.Color(255, 255, 255))); 
    jPanel1.setForeground(new java.awt.Color(255, 255, 255));
    jPanel1.setLayout(null);

    jPanel3.setOpaque(false);
    jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("G1101 connect to cr-WSN at IP: ");
    jPanel3.add(jLabel2);

    jTextField1.setFont(new java.awt.Font("Arial", 1, 18)); 
    jTextField1.setText("123.234.121.232");
    jTextField1.setPreferredSize(new java.awt.Dimension(200, 28));
    jPanel3.add(jTextField1);

    jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("Port: ");
    jPanel3.add(jLabel3);

    jTextField2.setFont(new java.awt.Font("Arial", 1, 18)); 
    jTextField2.setText("7301");
    jTextField2.setPreferredSize(new java.awt.Dimension(80, 28));
    jPanel3.add(jTextField2);

    jPanel1.add(jPanel3);
    jPanel3.setBounds(20, 30, 680, 40);

    jPanel4.setOpaque(false);
    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel6.setForeground(new java.awt.Color(255, 255, 255));
    jLabel6.setText("Current Status:");
    jPanel4.add(jLabel6);

    jPanel1.add(jPanel4);
    jPanel4.setBounds(20, 110, 690, 30);

    jPanel7.setOpaque(false);
    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel12.setForeground(new java.awt.Color(255, 255, 255));
    jLabel12.setText("Last time data from sensor: ");
    jPanel7.add(jLabel12);

    jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel13.setForeground(new java.awt.Color(255, 255, 255));
    jPanel7.add(jLabel13);

    jPanel1.add(jPanel7);
    jPanel7.setBounds(20, 70, 690, 30);

    getContentPane().add(jPanel1);
    jPanel1.setBounds(20, 40, 720, 160);

    jPanel2.setBackground(new java.awt.Color(153, 255, 153));
    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "User Application ??", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("MingLiU", 1, 18), new java.awt.Color(0, 0, 0))); 
    jPanel2.setLayout(null);

    jPanel5.setOpaque(false);
    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel9.setText("Current Status:");
    jPanel5.add(jLabel9);

    jPanel2.add(jPanel5);
    jPanel5.setBounds(20, 110, 680, 30);

    jPanel6.setOpaque(false);
    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel10.setText("User Aplplication at IP: ");
    jPanel6.add(jLabel10);

    jTextField3.setFont(new java.awt.Font("Arial", 1, 18)); 
    jTextField3.setText("127.0.0.1");
    jTextField3.setPreferredSize(new java.awt.Dimension(200, 28));
    jPanel6.add(jTextField3);

    jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel11.setText("Port: ");
    jPanel6.add(jLabel11);

    jTextField4.setFont(new java.awt.Font("Arial", 1, 18)); 
    jTextField4.setText("6001");
    jTextField4.setPreferredSize(new java.awt.Dimension(80, 28));
    jPanel6.add(jTextField4);

    jPanel2.add(jPanel6);
    jPanel6.setBounds(20, 30, 670, 40);

    jPanel9.setOpaque(false);
    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel17.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel17.setText("Last time data from user application: ");
    jPanel9.add(jLabel17);

    jLabel18.setFont(new java.awt.Font("Arial", 1, 18)); 
    jPanel9.add(jLabel18);

    jPanel2.add(jPanel9);
    jPanel9.setBounds(20, 70, 680, 30);

    getContentPane().add(jPanel2);
    jPanel2.setBounds(20, 220, 720, 150);

    jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); 
    jLabel1.setText("2016/08/05 14:40:00");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(540, 10, 190, 20);

    jButton1.setFont(new java.awt.Font("Arial", 1, 18)); 
    jButton1.setText("Save");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(250, 400, 110, 40);

    jButton2.setFont(new java.awt.Font("Arial", 1, 18)); 
    jButton2.setText("Reset");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton2);
    jButton2.setBounds(410, 400, 130, 40);

    pack();
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
  setVisible(false);
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
   String ip1=jTextField1.getText().trim();
   String port1=jTextField2.getText().trim();
   String ip2=jTextField3.getText().trim();
   String port2=jTextField4.getText().trim();
  String src1=jTextField1.getText().trim();
  String src2=jTextField2.getText().trim();
  if(ip1.length()<1 || ip2.length()<1 || port1.length()<1 || port2.length()<1) {
    JOptionPane.showMessageDialog(this, "Data source can not be empty.");
    return;
  } else if(!true) {
    JOptionPane.showMessageDialog(this, "Two data sources can not be the same.");
    return;
  }
  src1=ip1+":"+port1;
  src2="device@"+ip2+":"+port2;
  setDataSource(src1,src2);
  props.setProperty("auto_start_if_run_my_ap_only","Y");
  showStatus();
  setDevice(ip2,port2);
  }
private void setDevice(String ip2,String port2){
  WSNSocketDevice device=null;
  Enumeration en=wsn.wsnSDevices.elements();
      for(;en.hasMoreElements();){
        Object o=en.nextElement();
        if(o!=null){
          device=((WSNSocketDevice)o);

          if(device.getHost()!=null && !device.getHost().equals("210.65.1.29")) {
            boolean needConnect=false;
            if(device.connected){
              device.disconnect();
              needConnect=true;
            }
            device.setHost(ip2);
            if(port2.length()>0 && wsn.isNumeric(port2)) device.setPort(Integer.parseInt(port2));

            device.saveDeviceFile(2);
            if(needConnect) device.connect();
            break;  
          }
        }
      }
}
  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
   showFlow();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {

        

    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(WSNDeliverer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(WSNDeliverer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(WSNDeliverer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(WSNDeliverer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new WSNDeliverer().setVisible(true);
      }
    });
  }
  public void init(WSN wsn) {

    this.wsn=wsn;
    if(wsn.w.checkOneVar(wsn.statuses[0],0)) {
      programId=-1;
      for(Iterator it=wsn.myAps.values().iterator();it.hasNext();){
        Object o=it.next();
        if(o instanceof WSNDeliverer) programId++;
      }
    }

    if(programId>0) this.setTitle(this.getTitle()+" - "+(programId+1));
    if(wsn.props.getProperty("redirectorfile_name_prefix")!=null) redirectorFile=wsn.props.getProperty("redirectorfile_name_prefix")+"-"+(programId+1)+".txt";
      else redirectorFile=defaultRedirectorFile+"-"+(programId+1)+".txt";
    redirectorFile=wsn.w.fileSeparator(redirectorFile);
    readProperties();
    showFlow();
    String cmdStr="performmessage wsn.WSN getdatasource ";
    wsn.w.sendToAll(cmdStr);
    myThread=new Thread(this);
    myThread.start();
     props.setProperty("start", "Y");
  }

public void onExit(int type){
  saveProperties();
}
public boolean runInBackground(){
  return run_in_background;
}
public void setDataSource(String src1,String src2){
  dataSource1=src1; dataSource2=src2;
  props.setProperty("datasource-1",dataSource1);
  props.setProperty("datasource-2",dataSource2);
  String cmdStr="performmessage wsn.WSN getdatasource ";
  wsn.w.sendToAll(cmdStr);
  saveProperties();
  showFlow();
  showStatus();
}
private void saveProperties(){

    try{
        FileOutputStream out = new FileOutputStream(redirectorFile );
        props.store(out,"");
      }catch(IOException e){e.printStackTrace();}
}
public void run(){
      while(true){
        while(waitV.size()>0){
          DataClass dataClass=(DataClass)waitV.get(0);

          if((dataSource1.indexOf("-")==-1? wsn.getPort(dataClass.dataSrc):dataClass.dataSrc).equals(dataSource1)){
            if(wsn!=null) jLabel13.setText(wsn.formatter.format(new Date()));
            if(src2Connected) redirect(dataClass.data,dataSource2);
            

          } else if((dataSource2.indexOf("-")==-1? wsn.getPort(dataClass.dataSrc):dataClass.dataSrc).equals(dataSource2)){
            if(wsn!=null) jLabel18.setText(wsn.formatter.format(new Date()));
            if(src1Connected)  redirect(dataClass.data,dataSource1);
            

          }
          waitV.remove(0);
          try{
            Thread.sleep(sendInterval);
          } catch(InterruptedException e){}
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
String getDisplay(String data){
  byte b0[]={};
            String temp2=data;
            if(!true){

            } else {
               int cnt2=0;
               StringTokenizer st2=new StringTokenizer(data, " ");
               int intx[]=new int[data.length()];
               if(data!=null && data.length()>0){
                 for(cnt2=0;st2.hasMoreTokens();cnt2++){
                  intx[cnt2]=Integer.decode("0x"+st2.nextToken()).intValue();  
                }
                b0=new byte[cnt2];
                for(int i=0;i<cnt2;i++){
                  b0[i]=(byte)intx[i];
                }
               }
               temp2=new String(b0);
               if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
               if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);
             }
  return temp2;
}
private void readProperties(){

    File pFile=new File(redirectorFile);
    if(pFile.exists()){
    InputStream is = null;
    try {
        File f = new File(redirectorFile);
        is = new FileInputStream( f );
        props.load( is );

      } catch ( Exception e ) { 
          e.printStackTrace();
      }
      sendInterval=wsn.getPropsLong(props,"send_interval");
      run_in_background=wsn.chkProps(props,"run_in_background");
      showFlow();
      showStatus();
    } else System.out.println("Warning: properties file '"+redirectorFile+"' not found, using default values.");

}
void showFlow(){

      if(props.getProperty("datasource-1")!=null && props.getProperty("datasource-1").length()>0) dataSource1=props.getProperty("datasource-1");
      if(props.getProperty("datasource-2")!=null && props.getProperty("datasource-2").length()>0) dataSource2=props.getProperty("datasource-2");

      String ip=dataSource1,port=dataSource1;
      int inx=dataSource1.indexOf(":");
      if(inx>-1){
      jTextField1.setText(dataSource1.substring(0, inx));
      jTextField2.setText(dataSource1.substring(inx+1));
      }
      inx=dataSource2.indexOf("@");
      int inx2=dataSource2.indexOf(":");
      if(inx>-1 && inx2>-1){
      jTextField3.setText(dataSource2.substring(inx+1, inx2));
      jTextField4.setText(dataSource2.substring(inx2+1));
      }
}
void redirect(String cmd,String target){
              String id[]=wsn.getIdFromDataSrc(target);
		if(cmd.trim().length()>0){
                 String id2=wsn.getId2(target);
                 String id3=wsn.getId3(target);

                 if(id.length>0 && id[0].length()>0){
                 if(wsn.getId1(target).equals("0")){

                } else if(id2.equals("0")){

                } else if(id3!=null && (id3.equals("0") || (id3.length()==0))){
                    cmd="performcommand wsn.WSN cmd "+id2+" all true false false 0 "+wsn.w.e642(cmd)+" 0"; 
                    for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && id3.length()>0){
                    cmd="performcommand wsn.WSN cmd "+id2+" "+id3+" true false false 0 "+wsn.w.e642(cmd)+" 0"; 
                    for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } 
            }
            }

}
void showStatus(){
  jLabel6.setText("Current Status: "+(src1Connected? "connected":"disconnected"));
  jLabel9.setText("Current Status: "+(src2Connected? "connected":"disconnected"));
}
public void setBlink(boolean onoff){
     this.onoff=onoff;
     if(onoff){
         if(wsn!=null) jLabel1.setText(wsn.formatter.format(new Date()));
     } else{

     }
  }
 boolean chkProps(String key){
   return props.getProperty(key)!=null && props.getProperty(key).length()>0 && (props.getProperty(key).equalsIgnoreCase("y") ||props.getProperty(key).equalsIgnoreCase("yes"));  
 }
 public String getPropsString(String key){
   return (props.getProperty(key)!=null? props.getProperty(key).trim():"");
 }
public void setData(long time,String nodeId, String dataSrc,String data){

    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) myThread.interrupt();

}

public void setStatus(String nodeId,String dataSrc[],int statusCode){

  for(int i=0;i<dataSrc.length;i++){

    if((dataSource1.indexOf("-")==-1? wsn.getPort(dataSrc[i]):dataSrc[i]).equals(dataSource1)){
      if(statusCode==1 || statusCode==3) src1Connected=true;
      else if(statusCode==2) src1Connected=false;
    } else if((dataSource2.indexOf("-")==-1? wsn.getPort(dataSrc[i]):dataSrc[i]).equals(dataSource2)){
      if(statusCode==1 || statusCode==3) src2Connected=true;
      else if(statusCode==2) src2Connected=false;
    }
  }

  showStatus();
}
 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField4;

}