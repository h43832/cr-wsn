package wsn;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import y.ylib.ylib;
public class WSNRedirector extends WSNApplication implements Runnable{
  public int programId=0;
  long longWaitTime=31536000000L,sendInterval=0L;
  boolean onoff=false,isSleep=false,run_in_background=false; 
  WSN wsn;

  WSNRedirectorSetup setup;
  Thread myThread=null;
  Vector waitV=new Vector();
  String defaultRedirectorFile="apps"+File.separator+"cr-wsn"+File.separator+"redirector",redirectorFile="";
  public Properties props=new Properties();
  String dataSource1="6001-1", dataSource2="COM131";
  boolean src1Connected=false,src2Connected=false;
  /** Creates new form WSNApplicationVirtualCOM */
  public WSNRedirector() {
    initComponents();
            int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 60000)/100000;
        int h2=(h * 35000)/100000;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
  }
  public void init(WSN wsn) {

    this.wsn=wsn;
    if(wsn.w.checkOneVar(wsn.statuses[0],0)) {
      programId=-1;
      for(Iterator it=wsn.myAps.values().iterator();it.hasNext();){
        Object o=it.next();
        if(o instanceof WSNRedirector) programId++;
      }
    }
    if(programId>0) this.setTitle(this.getTitle()+" - "+(programId+1));
    if(wsn.props.getProperty("redirectorfile_name_prefix")!=null) redirectorFile=wsn.props.getProperty("redirectorfile_name_prefix")+"-"+(programId+1)+".txt";
      else redirectorFile=defaultRedirectorFile+"-"+(programId+1)+".txt";
    redirectorFile=wsn.w.fileSeparator(redirectorFile);
    readProperties();
    String cmdStr="performmessage wsn.WSN getdatasource ";
    wsn.w.sendToAll(cmdStr);
    myThread=new Thread(this);
    myThread.start();
   if(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y") && chkProps("auto_start_if_run_my_ap_only")) {
     startCB.setSelected(true);
     props.setProperty("start", "Y");
   } else if(chkProps("start")) startCB.setSelected(true);
     else startCB.setSelected(false);
    if(chkProps("show_msg")) showCB.setSelected(true); else showCB.setSelected(false);
    if(chkProps("show_hex")) show16RB.setSelected(true); else show16RB.setSelected(false);
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
    if(startCB.isSelected()) props.setProperty("start", "Y"); else  props.setProperty("start", "N");
    if(showCB.isSelected()) props.setProperty("show_msg", "Y"); else  props.setProperty("show_msg", "N");
    if(show16RB.isSelected()) props.setProperty("show_hex", "Y"); else  props.setProperty("show_hex", "N");
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
            if(showCB.isSelected()){
            if(jTextArea1.toString().length()>10000) jTextArea1.setText("");
            String str1=getDisplay(dataClass.data);
            jTextArea1.append("\r\n"+str1);
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            }
            if(wsn.chkProps("no_heartbeat1")){
              if(wsn.getPropsString("heartbeat1").length()>0){

              }
            }
            if(src2Connected) redirect(dataClass.data,dataSource2);
            

          } else if((dataSource2.indexOf("-")==-1? wsn.getPort(dataClass.dataSrc):dataClass.dataSrc).equals(dataSource2)){
            if(showCB.isSelected()){
            if(jTextArea2.toString().length()>10000) jTextArea2.setText("");
            String str1=getDisplay(dataClass.data);
            jTextArea2.append("\r\n"+str1);
            jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
            }
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
      sendInterval=wsn.getPropsLong(props,"send_interval");
      run_in_background=wsn.chkProps(props,"run_in_background");
      showFlow();
      showStatus();
    } else System.out.println("Warning: properties file '"+redirectorFile+"' not found, using default values.");

}
void showFlow(){
      if(props.getProperty("datasource-1")!=null && props.getProperty("datasource-1").length()>0) dataSource1=props.getProperty("datasource-1");
      if(props.getProperty("datasource-2")!=null && props.getProperty("datasource-2").length()>0) dataSource2=props.getProperty("datasource-2");

      jLabel2.setText("Data -> "+dataSource1+"  ->  "+dataSource2);
      jLabel3.setText(dataSource1+"  <-  "+dataSource2+"  <-  Data");

}
void redirect(String cmd,String target){
              String id[]=wsn.getIdFromDataSrc(target);
		if(cmd.trim().length()>0){
                 String id2=wsn.getId2(target);
                 String id3=wsn.getId3(target);
                 System.out.println("id[0]="+id[0]+",id2="+id2+",id3="+id3);
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
  jLabel4.setText("Status: "+dataSource1+" "+(src1Connected? "connected":"disconnected")+", "+dataSource2+" "+(src2Connected? "connected":"disconnected"));
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

  if(startCB.isSelected()){

    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) myThread.interrupt();
  }
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
    jPanel8 = new javax.swing.JPanel();
    startCB = new javax.swing.JCheckBox();
    showCB = new javax.swing.JCheckBox();
    show16RB = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jPanel9 = new javax.swing.JPanel();
    jPanel11 = new javax.swing.JPanel();
    jButton2 = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    jPanel12 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("CR-WSN  Redirector");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel10.setLayout(new java.awt.GridLayout(2, 1));

    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel3.setBackground(new java.awt.Color(0, 0, 204));

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("Data  ->  6001-1  ->  COM131");
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
    jLabel3.setText("6001-1  <-  COM131  <-  Data");
    jPanel4.add(jLabel3);

    jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

    jTextArea2.setColumns(20);
    jTextArea2.setForeground(new java.awt.Color(0, 102, 0));
    jTextArea2.setRows(5);
    jScrollPane2.setViewportView(jTextArea2);

    jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    jPanel10.add(jPanel2);

    getContentPane().add(jPanel10, java.awt.BorderLayout.CENTER);

    jPanel5.setLayout(new java.awt.GridLayout(2, 1));

    jPanel6.setBackground(new java.awt.Color(204, 255, 255));
    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel4.setForeground(new java.awt.Color(255, 0, 255));
    jLabel4.setText("Status: 6006-1 disconnected, COM131 disconnected");
    jPanel6.add(jLabel4);

    jPanel5.add(jPanel6);

    jPanel7.setBackground(new java.awt.Color(204, 255, 255));
    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

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

    jPanel7.add(jPanel8);

    jPanel9.setBackground(new java.awt.Color(204, 255, 255));
    jPanel9.setLayout(new java.awt.GridLayout(1, 2));

    jPanel11.setOpaque(false);
    jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton2.setText("Set up");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel11.add(jButton2);

    jButton1.setText("Clear");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel11.add(jButton1);

    jPanel9.add(jPanel11);

    jPanel12.setOpaque(false);
    jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jLabel1.setForeground(new java.awt.Color(255, 51, 255));
    jLabel1.setText("2015-05-07 09:25:00");
    jPanel12.add(jLabel1);

    jPanel9.add(jPanel12);

    jPanel7.add(jPanel9);

    jPanel5.add(jPanel7);

    getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  jTextArea1.setText("");
  jTextArea2.setText("");
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  if(setup==null) setup=new WSNRedirectorSetup(this,true);
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
  private javax.swing.JPanel jPanel11;
  private javax.swing.JPanel jPanel12;
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