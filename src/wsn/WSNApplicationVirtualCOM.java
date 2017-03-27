package wsn;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
public class WSNApplicationVirtualCOM extends WSNApplication implements Runnable{
  public int programId=0;
  long longWaitTime=31536000000L;
  boolean onoff=false,isSleep=false; 
  WSN wsn;
  WSNAp_VCOM_Setup setup;

  Thread myThread=null;
  Vector waitV=new Vector();

  String defaultRedirectorFile="apps"+File.separator+"cr-wsn"+File.separator+"vcom",redirectorFile="";
  Properties props=new Properties();
  String programCom="COM132",redirectorCom="COM131",connectionPort="6001-1";
  boolean socketConnected=false,comcomConnected=false;
  /** Creates new form WSNApplicationVirtualCOM */
  public WSNApplicationVirtualCOM() {
    initComponents();
            int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 60000)/100000;
        int h2=(h * 35000)/100000;

        setSize(w2,h2);

        this.setVisible(true);
        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
  }
  public void init(WSN wsn) {

    this.wsn=wsn;
    if(wsn.w.checkOneVar(wsn.statuses[0],0))  {
      programId=-1;
      for(Iterator it=wsn.myAps.values().iterator();it.hasNext();){
        Object o=it.next();
        if(o instanceof WSNApplicationVirtualCOM) programId++;
      }
    }
    if(programId>0) this.setTitle(this.getTitle()+" - "+(programId+1));
    if(wsn.props.getProperty("vcomfile_name_prefix")!=null) redirectorFile=wsn.props.getProperty("vcomfile_name_prefix")+"-"+(programId+1)+".txt";
      else redirectorFile=defaultRedirectorFile+"-"+(programId+1)+".txt";
    redirectorFile=wsn.w.fileSeparator(redirectorFile);
    readProperties();
    String cmdStr="performmessage wsn.WSN getdatasource ";
    wsn.w.sendToAll(cmdStr);
    myThread=new Thread(this);
    myThread.start();
   if(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y")) startCB.setSelected(true);
  }

public void onExit(int type){}
  public boolean runInBackground(){
    return false;
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
          if(dataClass.dataSrc.equals(connectionPort)){
            if(showCB.isSelected()){
            if(jTextArea1.toString().length()>10000) jTextArea1.setText("");
            String str1=getDisplay(dataClass.data);
            jTextArea1.append("\r\n"+str1);
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            }
            if(comcomConnected)  redirect(dataClass.data,redirectorCom);
            

          } else if(dataClass.dataSrc.equals(redirectorCom)){
            if(showCB.isSelected()){
            if(jTextArea2.toString().length()>10000) jTextArea2.setText("");
            String str1=getDisplay(dataClass.data);
            jTextArea2.append("\r\n"+str1);
            jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
            }
            if(socketConnected) redirect(dataClass.data,connectionPort);
            

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
String getDisplay(String data){
  byte b0[]={};
            String temp2=data;
            if(show16RB.isSelected()){

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
    showFlow();
      showStatus();
    } else System.out.println("Warning: properties file '"+redirectorFile+"' not found, using default values.");

}
void showFlow(){
      if(props.getProperty("port_connect_to_device")!=null && props.getProperty("port_connect_to_device").length()>0) connectionPort=props.getProperty("port_connect_to_device");
      if(props.getProperty("serialport_connec_to_crwsn_redirector")!=null && props.getProperty("serialport_connec_to_crwsn_redirector").length()>0) redirectorCom=props.getProperty("serialport_connec_to_crwsn_redirector");
      if(props.getProperty("serialport_connect_to_program")!=null && props.getProperty("serialport_connect_to_program").length()>0) programCom=props.getProperty("serialport_connect_to_program");
      jLabel2.setText("Device Data  ->  Socket Port "+connectionPort+"  ->  "+redirectorCom+"  ->  "+programCom+"  ->  Program_A");
      jLabel3.setText("Device  <-  Socket Port "+connectionPort+"  <-  "+redirectorCom+"  <-  "+programCom+"  <-  Program_A Data");
}
void redirect(String cmd,String target){
              String id[]=wsn.getIdFromDataSrc(target);
		if(cmd.trim().length()>0){
                 String id2=wsn.getId2(target);
                 String id3=wsn.getId3(target);
                 if(id.length>0 && id[0].length()>0){
                 if(wsn.getId1(target).equals("0")){
                 cmd="performcommand wsn.WSN cmd all all true false false 0 "+wsn.w.e642(cmd); 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id2.equals("0")){
                 cmd="performcommand wsn.WSN cmd all all true false false 0 "+wsn.w.e642(cmd); 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && (id3.equals("0") || (id3.length()==0))){
                 cmd="performcommand wsn.WSN cmd "+id2+" all true false false 0 "+wsn.w.e642(cmd); 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && id3.length()>0){
                 cmd="performcommand wsn.WSN cmd "+id2+" "+id3+" true false false 0 "+wsn.w.e642(cmd); 

                    for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } 
            }
            }

}
void showStatus(){
  jLabel4.setText("Status: "+connectionPort+" "+(socketConnected? "connected":"disconnected")+", "+redirectorCom+" "+(comcomConnected? "connected":"disconnected"));
}
public void setBlink(boolean onoff){
     this.onoff=onoff;
     if(onoff){
         if(wsn!=null) jLabel1.setText(wsn.formatter.format(new Date()));
     } else{

     }
  }
public void setData(long time,String nodeId, String dataSrc,String data){

  if(startCB.isSelected()){
    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) myThread.interrupt();
  }
}
public void setStatus(String nodeId,String dataSrc[],int statusCode){
  for(int i=0;i<dataSrc.length;i++){
    if(dataSrc[i].equals(connectionPort)){
      if(statusCode==1 || statusCode==3) socketConnected=true;
      else if(statusCode==2) socketConnected=false;
    } else if(dataSrc[i].equals(redirectorCom)){
      if(statusCode==1 || statusCode==3) comcomConnected=true;
      else if(statusCode==2) comcomConnected=false;
    }
  }
  showStatus();
}
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jPanel10 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jPanel2 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea2 = new javax.swing.JTextArea();
    jPanel5 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    jPanel7 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jPanel8 = new javax.swing.JPanel();
    startCB = new javax.swing.JCheckBox();
    showCB = new javax.swing.JCheckBox();
    show16RB = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jPanel9 = new javax.swing.JPanel();
    jButton2 = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("CR-WSN + Com0com         Virtual COM Port Monitor ");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel10.setLayout(new java.awt.GridLayout(2, 1));

    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel3.setBackground(new java.awt.Color(0, 0, 204));

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("Device Data  ->  Socket Port 6001-1  ->  COM131  ->  COM132  ->  Program_A");
    jPanel3.add(jLabel2);

    jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

    jTextArea1.setColumns(20);
    jTextArea1.setForeground(new java.awt.Color(0, 0, 204));
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    jPanel10.add(jPanel1);

    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel4.setBackground(new java.awt.Color(153, 255, 153));

    jLabel3.setForeground(new java.awt.Color(0, 0, 204));
    jLabel3.setText("Device  <-  Socket Port 6001-1  <-  COM131  <-  COM132  <-  Program_A Data");
    jPanel4.add(jLabel3);

    jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

    jTextArea2.setColumns(20);
    jTextArea2.setForeground(new java.awt.Color(0, 102, 0));
    jTextArea2.setRows(5);
    jScrollPane2.setViewportView(jTextArea2);

    jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    jPanel10.add(jPanel2);

    getContentPane().add(jPanel10, java.awt.BorderLayout.CENTER);

    jPanel5.setLayout(new java.awt.GridLayout(2, 2));

    jPanel6.setBackground(new java.awt.Color(204, 255, 255));
    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel4.setForeground(new java.awt.Color(255, 0, 255));
    jLabel4.setText("Status: 6006-1 disconnected, COM131 disconnected");
    jPanel6.add(jLabel4);

    jPanel5.add(jPanel6);

    jPanel7.setBackground(new java.awt.Color(204, 255, 255));
    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jLabel1.setForeground(new java.awt.Color(255, 51, 255));
    jLabel1.setText("2015-05-07 09:25:00");
    jPanel7.add(jLabel1);

    jPanel5.add(jPanel7);

    jPanel8.setBackground(new java.awt.Color(204, 255, 255));
    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    startCB.setBackground(new java.awt.Color(204, 255, 255));
    startCB.setForeground(new java.awt.Color(255, 51, 255));
    startCB.setText("Start");
    jPanel8.add(startCB);

    showCB.setBackground(new java.awt.Color(204, 255, 255));
    showCB.setForeground(new java.awt.Color(255, 0, 255));
    showCB.setSelected(true);
    showCB.setText("Show");
    jPanel8.add(showCB);

    show16RB.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(show16RB);
    show16RB.setForeground(new java.awt.Color(255, 51, 255));
    show16RB.setText("Hex");
    jPanel8.add(show16RB);

    jRadioButton2.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(jRadioButton2);
    jRadioButton2.setForeground(new java.awt.Color(255, 51, 255));
    jRadioButton2.setSelected(true);
    jRadioButton2.setText("Word          ");
    jPanel8.add(jRadioButton2);

    jPanel5.add(jPanel8);

    jPanel9.setBackground(new java.awt.Color(204, 255, 255));
    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton2.setText("Set up");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel9.add(jButton2);

    jButton1.setText("Clear");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel9.add(jButton1);

    jPanel5.add(jPanel9);

    getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  jTextArea1.setText("");
  jTextArea2.setText("");
}
public void setDataSource(String src1,String src2){
  connectionPort=src1; redirectorCom=src2;
  props.setProperty("port_connect_to_device",connectionPort);
  props.setProperty("serialport_connec_to_crwsn_redirector",redirectorCom);
  String cmdStr="performmessage wsn.WSN getdatasource ";
  wsn.w.sendToAll(cmdStr);
  saveProperties();
  showFlow();
  showStatus();
}
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  if(setup==null) setup=new WSNAp_VCOM_Setup(this,true);
  else setup.setVisible(true);
  showStatus();
}

private void formWindowClosing(java.awt.event.WindowEvent evt) {
  setVisible(false);
}

  /**
   * @param args the command line arguments
   */

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  public javax.swing.JLabel jLabel2;
  public javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel10;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JTextArea jTextArea2;
  private javax.swing.JRadioButton show16RB;
  private javax.swing.JCheckBox showCB;
  private javax.swing.JCheckBox startCB;

 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }
}