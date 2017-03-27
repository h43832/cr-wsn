package wsn;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import java.text.*;
import java.util.regex.*;
import y.ylib.ylib;
public class WSNSocketDevice extends javax.swing.JFrame implements Runnable{
  public int programId=0;
  private int arrayCount=20;
  Thread reader,writerThread;
  public Writer writer;
  public WSN wsn;
  WSNSocketDataHandler dataHandler;
  ResourceBundle bundle2;
  public String defaultDeviceFile="apps"+File.separator+"cr-wsn"+File.separator+"device",deviceFile="",deviceConfig[]=null;
  boolean autoSendOK=false;
  public String pid;

  public String status1="init";
  public Socket so; 

  long reconnectcycletime=10000L;

  public InputStream in;
  public OutputStream out;
  public boolean isSleep=false,lastIsData=false;
  public boolean connected= false;

    public WSNSocketDevice(WSN  wsn, String pid) {
      this.wsn=wsn;
      this.pid=pid;

      programId=wsn.wsnSDevices.size();
      if(wsn.props.getProperty("devicefile_name_prefix")!=null) deviceFile=wsn.props.getProperty("devicefile_name_prefix")+"-"+(programId+1)+".txt";
      else deviceFile=defaultDeviceFile+"-"+(programId+1)+".txt";
      deviceFile=wsn.w.fileSeparator(deviceFile);

      init();
    }
    public void init(){
        initComponents();

        this.setTitle((wsn.props.getProperty("devicefile_name_prefix")!=null? wsn.props.getProperty("devicefile_name_prefix"):defaultDeviceFile)+(programId>0? " - "+(programId+1):""));
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 80000)/100000;
        int h2=(h * 80000)/100000;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        show16RB.setSelected(true);
        send16RB.setSelected(true);
        readDeviceFile(2);
    }

    @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    jPanel1 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    showCB = new javax.swing.JCheckBox();
    show16RB = new javax.swing.JRadioButton();
    showStrRB = new javax.swing.JRadioButton();
    crnlCB = new javax.swing.JCheckBox();
    showTimeCB = new javax.swing.JCheckBox();
    clearShowBtn = new javax.swing.JButton();
    jPanel7 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    ipTF = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    portTF = new javax.swing.JTextField();
    connectBtn = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    jPanel4 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane();
    jPanel2 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jPanel8 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    send16RB = new javax.swing.JRadioButton();
    sendStrRB = new javax.swing.JRadioButton();
    jLabel7 = new javax.swing.JLabel();
    checkSumCB = new javax.swing.JCheckBox();
    jComboBox1 = new javax.swing.JComboBox();
    jPanel9 = new javax.swing.JPanel();
    javaDataClassCB = new javax.swing.JCheckBox();
    javaDataClassTF = new javax.swing.JTextField();
    javaHandlerClassCB = new javax.swing.JCheckBox();
    javaHandlerClassTF = new javax.swing.JTextField();
    jPanel10 = new javax.swing.JPanel();
    continueSendCB = new javax.swing.JCheckBox();
    jLabel3 = new javax.swing.JLabel();
    sendIntervalTF = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    randomCB = new javax.swing.JCheckBox();
    sendBtn = new javax.swing.JButton();
    stopContinueSendBtn = new javax.swing.JButton();
    clearSendBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("null");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel3.setBackground(new java.awt.Color(204, 255, 255));
    jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    jLabel1.setText(bundle.getString("WSNSocketDevice.jLabel1.text")); 
    jPanel3.add(jLabel1);

    showCB.setBackground(new java.awt.Color(204, 255, 255));
    showCB.setSelected(true);
    showCB.setText(bundle.getString("WSNSocketDevice.showCB.text")); 
    jPanel3.add(showCB);

    show16RB.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(show16RB);
    show16RB.setText(bundle.getString("WSNSocketDevice.show16RB.text")); 
    jPanel3.add(show16RB);

    showStrRB.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(showStrRB);
    showStrRB.setText(bundle.getString("WSNSocketDevice.showStrRB.text")); 
    jPanel3.add(showStrRB);

    crnlCB.setBackground(new java.awt.Color(204, 255, 255));
    crnlCB.setText(bundle.getString("WSNSocketDevice.crnlCB.text")); 
    jPanel3.add(crnlCB);

    showTimeCB.setBackground(new java.awt.Color(204, 255, 255));
    showTimeCB.setText(bundle.getString("WSNSocketDevice.showTimeCB.text")); 
    jPanel3.add(showTimeCB);

    clearShowBtn.setText(bundle.getString("WSNSocketDevice.clearShowBtn.text")); 
    clearShowBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearShowBtnActionPerformed(evt);
      }
    });
    jPanel3.add(clearShowBtn);

    jPanel7.setBackground(new java.awt.Color(255, 0, 204));

    jLabel5.setText(bundle.getString("WSNSocketDevice.jLabel5.text")); 
    jPanel7.add(jLabel5);

    ipTF.setText(bundle.getString("WSNSocketDevice.ipTF.text")); 
    ipTF.setPreferredSize(new java.awt.Dimension(90, 25));
    jPanel7.add(ipTF);

    jLabel6.setText(bundle.getString("WSNSocketDevice.jLabel6.text")); 
    jPanel7.add(jLabel6);

    portTF.setText(bundle.getString("WSNSocketDevice.portTF.text")); 
    portTF.setPreferredSize(new java.awt.Dimension(50, 25));
    jPanel7.add(portTF);

    connectBtn.setText(bundle.getString("WSNSocketDevice.connectBtn.text")); 
    connectBtn.setPreferredSize(new java.awt.Dimension(100, 23));
    connectBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        connectBtnActionPerformed(evt);
      }
    });
    jPanel7.add(connectBtn);

    jPanel3.add(jPanel7);

    jButton1.setText(bundle.getString("WSNSocketDevice.jButton1.text")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton1);

    jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

    jPanel4.setLayout(new java.awt.BorderLayout());

    jTextPane1.setFont(jTextPane1.getFont());
    jScrollPane1.setViewportView(jTextPane1);

    jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel6.setLayout(new java.awt.BorderLayout());

    jTextArea1.setColumns(20);
    jTextArea1.setFont(jTextArea1.getFont());
    jTextArea1.setRows(5);
    jScrollPane2.setViewportView(jTextArea1);

    jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

    jPanel8.setLayout(new java.awt.GridLayout(3, 1));

    jPanel5.setBackground(new java.awt.Color(0, 0, 204));
    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText(bundle.getString("WSNSocketDevice.jLabel2.text")); 
    jPanel5.add(jLabel2);

    send16RB.setBackground(new java.awt.Color(0, 0, 204));
    buttonGroup2.add(send16RB);
    send16RB.setForeground(new java.awt.Color(255, 255, 255));
    send16RB.setText(bundle.getString("WSNSocketDevice.send16RB.text")); 
    jPanel5.add(send16RB);

    sendStrRB.setBackground(new java.awt.Color(0, 0, 204));
    buttonGroup2.add(sendStrRB);
    sendStrRB.setForeground(new java.awt.Color(255, 255, 255));
    sendStrRB.setText(bundle.getString("WSNSocketDevice.sendStrRB.text")); 
    jPanel5.add(sendStrRB);

    jLabel7.setForeground(new java.awt.Color(255, 255, 255));
    jLabel7.setText(bundle.getString("WSNSocketDevice.jLabel7.text")); 
    jPanel5.add(jLabel7);

    checkSumCB.setBackground(new java.awt.Color(0, 0, 204));
    checkSumCB.setForeground(new java.awt.Color(255, 255, 255));
    checkSumCB.setText(bundle.getString("WSNSocketDevice.checkSumCB.text")); 
    jPanel5.add(checkSumCB);

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CheckSum", "Modbus CRC", "0x0D" }));
    jPanel5.add(jComboBox1);

    jPanel8.add(jPanel5);

    jPanel9.setBackground(new java.awt.Color(0, 0, 204));

    javaDataClassCB.setBackground(new java.awt.Color(0, 0, 204));
    javaDataClassCB.setForeground(new java.awt.Color(255, 255, 255));
    javaDataClassCB.setText(bundle.getString("WSNSocketDevice.javaDataClassCB.text_1")); 
    javaDataClassCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        javaDataClassCBActionPerformed(evt);
      }
    });
    jPanel9.add(javaDataClassCB);

    javaDataClassTF.setText(bundle.getString("WSNSocketDevice.javaDataClassTF.text_1")); 
    javaDataClassTF.setToolTipText(bundle.getString("WSNSocketDevice.javaDataClassTF.toolTipText")); 
    javaDataClassTF.setPreferredSize(new java.awt.Dimension(200, 21));
    jPanel9.add(javaDataClassTF);

    javaHandlerClassCB.setForeground(new java.awt.Color(255, 255, 255));
    javaHandlerClassCB.setText(bundle.getString("WSNSocketDevice.javaHandlerClassCB.text_1")); 
    javaHandlerClassCB.setOpaque(false);
    jPanel9.add(javaHandlerClassCB);

    javaHandlerClassTF.setText(bundle.getString("WSNSocketDevice.javaHandlerClassTF.text_1")); 
    javaHandlerClassTF.setToolTipText(bundle.getString("WSNSocketDevice.javaHandlerClassTF.toolTipText")); 
    javaHandlerClassTF.setPreferredSize(new java.awt.Dimension(200, 21));
    jPanel9.add(javaHandlerClassTF);

    jPanel8.add(jPanel9);

    jPanel10.setBackground(new java.awt.Color(0, 0, 204));
    jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    continueSendCB.setBackground(new java.awt.Color(0, 0, 204));
    continueSendCB.setForeground(new java.awt.Color(255, 255, 255));
    continueSendCB.setText(bundle.getString("WSNSocketDevice.continueSendCB.text")); 
    jPanel10.add(continueSendCB);

    jLabel3.setBackground(new java.awt.Color(0, 0, 204));
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText(bundle.getString("WSNSocketDevice.jLabel3.text")); 
    jPanel10.add(jLabel3);

    sendIntervalTF.setText(bundle.getString("WSNSocketDevice.sendIntervalTF.text")); 
    sendIntervalTF.setToolTipText(bundle.getString("WSNSocketDevice.sendIntervalTF.toolTipText")); 
    sendIntervalTF.setMinimumSize(new java.awt.Dimension(26, 21));
    sendIntervalTF.setPreferredSize(new java.awt.Dimension(42, 21));
    sendIntervalTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sendIntervalTFActionPerformed(evt);
      }
    });
    jPanel10.add(sendIntervalTF);

    jLabel4.setBackground(new java.awt.Color(0, 0, 204));
    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setText(bundle.getString("WSNSocketDevice.jLabel4.text")); 
    jPanel10.add(jLabel4);

    randomCB.setBackground(new java.awt.Color(0, 0, 204));
    randomCB.setForeground(new java.awt.Color(255, 255, 255));
    randomCB.setText(bundle.getString("WSNSocketDevice.randomCB.text")); 
    randomCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        randomCBActionPerformed(evt);
      }
    });
    jPanel10.add(randomCB);

    sendBtn.setText(bundle.getString("WSNSocketDevice.sendBtn.text")); 
    sendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sendBtnActionPerformed(evt);
      }
    });
    jPanel10.add(sendBtn);

    stopContinueSendBtn.setText(bundle.getString("WSNSocketDevice.stopContinueSendBtn.text")); 
    stopContinueSendBtn.setEnabled(false);
    stopContinueSendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopContinueSendBtnActionPerformed(evt);
      }
    });
    jPanel10.add(stopContinueSendBtn);

    clearSendBtn.setText(bundle.getString("WSNSocketDevice.clearSendBtn.text")); 
    clearSendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearSendBtnActionPerformed(evt);
      }
    });
    jPanel10.add(clearSendBtn);

    jPanel8.add(jPanel10);

    jPanel2.add(jPanel8, java.awt.BorderLayout.NORTH);

    getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

    pack();
  }

  public void onExit(int type){
    saveDeviceFile(2);
}
 public String getDeviceFileName(){
   return deviceFile;
 }

public void readDeviceFile(int type){
    if(deviceFile!=null && deviceFile.length()>0){
       File f=new File(deviceFile);
       if(f.exists() && f.isFile()){
         try{
           FileInputStream in=new FileInputStream(deviceFile);
           BufferedReader d= new BufferedReader(new InputStreamReader(in));
           while(true){
	     String str1=d.readLine();
	     if(str1==null) {in.close(); d.close(); break; }

             if(str1.length()>0){
               deviceConfig=wsn.w.csvLineToArray(str1);

               break;
             }
           }
	   in.close();
           deviceConfig[13]=ylib.replace(deviceConfig[13], "xxrxxn", "\r\n");
           deviceConfig[13]=ylib.replace(deviceConfig[13], "yyn", "\n");
           deviceConfig[13]=ylib.replace(deviceConfig[13], "yyr", "\r");
	   d.close();
         setUI(type);
        }catch(FileNotFoundException e){
               e.printStackTrace();
      }
    catch(IOException e){

      if(type==1) JOptionPane.showMessageDialog(this," Error in reading "+deviceFile+" file.");

        e.printStackTrace();
    }
    } else {
           if(type==1) JOptionPane.showMessageDialog(this,"Warning: device file "+deviceFile+" not exist.");

       }
}
  if(deviceConfig==null){
      deviceConfig=new String[]{"1","0","0","1","127.0.0.1","6001","0","0","0","1","3","1","0","","0","","","","",""};
  }
  if(deviceConfig.length<arrayCount){
    String tmp[]=new String[arrayCount];
    for(int i=0;i<deviceConfig.length;i++) tmp[i]=deviceConfig[i];
    for(int i=deviceConfig.length;i<arrayCount;i++) tmp[i]="";
    deviceConfig=tmp;
  }
}

public void run(){

    while(true){

        try{
          so= new Socket(getHost(), getPort());
	  in= so.getInputStream();
          out= so.getOutputStream();
	  connected= true;
          textPaneAppend("Connect to "+getHost()+":"+portTF.getText().trim()+" successfully.\r\n");
          status1="login";
          writer=new Writer(out);
          writerThread=new Thread(writer);
          writerThread.start();
          if(!autoSendOK && wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y")) {
            sendAction(2);
            showCB.setSelected(false);
          }
          informStatus(1);
      } catch(Exception e) {
          connected=false;

          if(reconnectcycletime==0) System.out.println(getHost()+":"+getPort()+" "+bundle2.getString("WSNSocketDevice.xy.msg1"));
          informStatus(2);
      	}
      try{
       byte [] readB=new byte[1024];
       int count=0;
       while(connected) {

         

         while((count=in.read(readB))>-1){

           if(count>0){
           byte a[]=new byte[count];
           for(int i=0;i<count;i++) a[i]=readB[i];

           if(javaHandlerClassCB.isSelected()) {
               if(dataHandler==null) {
                 String cla=javaHandlerClassTF.getText().trim();
                 if(cla.length()>0){
                if(((WSNSocketDataHandler)wsn.jClasses.get(cla))==null){
                    wsn.loadClass(cla,5);

                }
                if(((WSNSocketDataHandler)wsn.jClasses.get(cla))!=null){
                 dataHandler= (WSNSocketDataHandler)wsn.jClasses.get(cla);
                }
                 }
               }
               if(dataHandler!=null) dataHandler.setData((byte [])a.clone(),this);
           }
            if(showCB.isSelected()){
            String temp2="";
            if(show16RB.isSelected()){
               temp2=byteToStr(a);
            } else {
            temp2=new String(a);
            }
            if(crnlCB.isSelected() || !lastIsData){
                String tmp="";
                if(showTimeCB.isSelected()) tmp="\r\n"+wsn.formatter3.format(new Date())+" ";
                else temp2="\r\n"+temp2;
                if(tmp.length()>0) textPaneAppend(jTextPane1,tmp,Color.BLACK,0);
                textPaneAppend(jTextPane1,temp2,Color.RED,0); 
            } else {
                  temp2=" "+temp2;
                  textPaneAppend(jTextPane1,temp2,Color.RED,0); 
            }

             lastIsData=true;

            }

                 String str=byteToStr(a);

                 String cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 3 device@"+getHost()+":"+getPort()+" 1 "+wsn.w.e642(str);
                 switch(0){
                   case 0:
                     String cmdCode=wsn.w.getSsx(6, pid);
                     cmdCode=(cmdCode!=null && cmdCode.length()>0? cmdCode:"%empty%");
                     wsn.w.command(cmd2,7,7,cmdCode,"0",null,wsn.w.getGNS(1),null);
                     break;
                   case 1:

                     break;
                   case 2:

                     break;
                   case 3:

                     break;
                   case 4:

                     break;
                   case 5:

                     break;
                   case 6:

                     break;
                   case 7:

                     break;
                   case 8:

                     break;
                 }

         }
       }

       connected=false;
       informStatus(2);
      }
    } catch(java.net.SocketException e) {

        System.out.print("|");

       } catch(IOException e) {

        e.printStackTrace();
       }
           connected=false;
            try {
	      if(so!=null) so.close();
             } catch(Exception e2) {
      	        e2.printStackTrace();
               }  

            status1="logout";
            informStatus(2);
            if(lastIsData && !crnlCB.isSelected()) textPaneAppend("\r\n");
            if(reconnectcycletime>0 && connectBtn.getText().equalsIgnoreCase(bundle2.getString("WSNSocketDevice.xy.msg9"))) System.out.print(".");
            else {textPaneAppend(getHost()+":"+portTF.getText().trim()+" disconnected.\r\n"); connectBtn.setText(bundle2.getString("WSNSocketDevice.connectBtn.text"));}
            lastIsData=false;            
            try{

              isSleep=true;
              if(reconnectcycletime>0 && connectBtn.getText().equalsIgnoreCase(bundle2.getString("WSNSocketDevice.xy.msg9"))){
                Thread.sleep(reconnectcycletime);
              }  else Thread.sleep(1000L * 60L * 60L * 24L * 365L);
              isSleep=false;
             }catch(InterruptedException e2){

                 isSleep=false; 
             }
           }
  }
void informStatus(int statusCode){
      String dSrc="device@"+getHost()+":"+getPort();
      String dSrcs[]=new String[]{dSrc};
      for(Iterator it=wsn.myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(wsn.w.getGNS(1),dSrcs, statusCode);
      for(Enumeration en=wsn.eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(wsn.w.getGNS(1),dSrcs, statusCode);
      for(Enumeration en=wsn.lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(wsn.w.getGNS(1),dSrcs, statusCode);
      if(wsn.wsnNManager!=null) wsn.wsnNManager.setStatus(wsn.w.getGNS(1), dSrcs, statusCode);
}
public String getHost(){
  return ipTF.getText().trim();
}
public int getPort(){
  if(wsn.isNumeric(portTF.getText().trim())){
  return Integer.parseInt(portTF.getText().trim());
  } else return 6001;
}
public void setHost(String h){
  ipTF.setText(h.trim());
}
public void setPort(int p){
  portTF.setText(String.valueOf(p));
}
public String [] getDeviceConfig(){
    String newConfig[]=deviceConfig;
    if(showCB.isSelected()) newConfig[0]="1"; else newConfig[0]="0";
    if(show16RB.isSelected()) newConfig[1]="1"; else newConfig[1]="0";
    if(crnlCB.isSelected()) newConfig[2]="1"; else newConfig[2]="0";
    if(showTimeCB.isSelected()) newConfig[3]="1"; else newConfig[3]="0";
    newConfig[4]=ipTF.getText().trim();
    newConfig[5]=portTF.getText();

    if(send16RB.isSelected()) newConfig[7]="1"; else newConfig[7]="0";
    if(checkSumCB.isSelected()) newConfig[8]="1"; else newConfig[8]="0";
    if(continueSendCB.isSelected()) newConfig[9]="1"; else newConfig[9]="0";
    newConfig[10]=sendIntervalTF.getText();
    if(randomCB.isSelected()) newConfig[11]="1"; else newConfig[11]="0";

    newConfig[13]=jTextArea1.getText();
    newConfig[13]=ylib.replace(newConfig[13], "\r\n", "xxrxxn");
    newConfig[13]=ylib.replace(newConfig[13], "\n", "yyn");
    newConfig[13]=ylib.replace(newConfig[13], "\r", "yyr");
    if(javaDataClassCB.isSelected()) newConfig[14]="1"; else newConfig[14]="0";
    newConfig[15]=javaDataClassTF.getText();
    if(javaHandlerClassCB.isSelected()) newConfig[16]="1"; else newConfig[16]="0";
    newConfig[17]=javaHandlerClassTF.getText();
    newConfig[19]=(String)jComboBox1.getSelectedItem();
    return newConfig;
}

public void saveDeviceFile(int type){
        deviceConfig=getDeviceConfig();
        try{
		          FileOutputStream out = new FileOutputStream (deviceFile);
		          byte [] b=wsn.w.arrayToCsvLine(deviceConfig).getBytes();
		          out.write(b);
		          out.close();
      }catch(IOException e){e.printStackTrace();}
      if(type==1) JOptionPane.showMessageDialog(this,"device file "+deviceFile+" "+bundle2.getString("WSNSocketDevice.xy.msg10"));

    }

void setUI(int type){
    if(deviceConfig.length>0) {
        if(deviceConfig[0].equals("1")) showCB.setSelected(true); else showCB.setSelected(false);
    }
    if(deviceConfig.length>1) {
        if(deviceConfig[1].equals("1")) show16RB.setSelected(true); else showStrRB.setSelected(true);
    }
    if(deviceConfig.length>2) {
        if(deviceConfig[2].equals("1")) crnlCB.setSelected(true); else crnlCB.setSelected(false);
    }
    if(deviceConfig.length>3) {
        if(deviceConfig[3].equals("1")) showTimeCB.setSelected(true); else showTimeCB.setSelected(false);
    }
    if(deviceConfig.length>4) {
        ipTF.setText(deviceConfig[4]);

    }
    if(deviceConfig.length>5 && wsn.isNumeric(deviceConfig[5])) {
        portTF.setText(deviceConfig[5]);

    }
    if(deviceConfig.length>7) {
        if(deviceConfig[7].equals("1")) send16RB.setSelected(true); else sendStrRB.setSelected(true);
    }
    if(deviceConfig.length>8) {
        if(deviceConfig[8].equals("1")) checkSumCB.setSelected(true); else checkSumCB.setSelected(false);
    }
    if(deviceConfig.length>9) {
        if(deviceConfig[9].equals("1")) continueSendCB.setSelected(true); else continueSendCB.setSelected(false);
    }
    if(deviceConfig.length>10) {
        sendIntervalTF.setText(deviceConfig[10]);
    }
    if(deviceConfig.length>11) {
        if(deviceConfig[11].equals("1")) randomCB.setSelected(true); else randomCB.setSelected(false);
    }
    if(deviceConfig.length>12) {

    }
    if(deviceConfig.length>13) {
        jTextArea1.setText(deviceConfig[13]);
    }
    if(deviceConfig.length>14) {
       if(deviceConfig[14].equals("1")) javaDataClassCB.setSelected(true);
       else javaDataClassCB.setSelected(false);
    }
    if(deviceConfig.length>15) {
        javaDataClassTF.setText(deviceConfig[15]);
    }
    if(deviceConfig.length>16) {
       if(deviceConfig[16].equals("1")) javaHandlerClassCB.setSelected(true);
       else javaHandlerClassCB.setSelected(false);
    }
    if(deviceConfig.length>17) {
        javaHandlerClassTF.setText(deviceConfig[17]);
    }
    if(deviceConfig.length>19) {
        jComboBox1.setSelectedItem(deviceConfig[19]);
    }

    if(type==2 && deviceConfig.length>6) {
      if(deviceConfig[6].equals("1") && wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))  connectAction();
    }
}
  public void textPaneAppend(String temp2){
      textPaneAppend(jTextPane1,temp2);
  }
  public void textPaneAppend(JTextPane tp,String temp2){
       textPaneAppend(tp,temp2,null,0);
  }

  public void textPaneAppend(JTextPane tp,String temp2,Color col,int fontSize){
            Document   doc   = jTextPane1.getDocument();   
           if(jTextPane1.getText().length()>wsn.maxMainLogLength) {
             try{

               doc.remove(0, doc.getLength());
             } catch(BadLocationException e){
                e.printStackTrace();
              }
            lastIsData=false;
         }
            try   {   
                SimpleAttributeSet   attrSet   =   new   SimpleAttributeSet();
                if(col!=null) StyleConstants.setForeground(attrSet,   col);   
                if(fontSize>0) StyleConstants.setFontSize(attrSet,   fontSize);  
                doc.insertString(doc.getLength(), temp2,  attrSet);   
            }   catch   (BadLocationException   e)   {   
               System.out.println("BadLocationException:   "   +   e);   
                }   
  }

String getStatus(){
    return status1;
}

  String byteToStr(byte b[],int n){
      if(b.length<n) n=b.length;
      byte a[]=new byte[n];
      for(int i=0;i<n;i++) a[i]=b[i];
      return byteToStr(a);
  }

 public String byteToStr(byte b[]){
    StringBuilder sb=new StringBuilder();
    boolean first=true;
    for (int i=0;i<b.length;i++) {
        if(first) first=false;
        else sb.append(" ");
	    int j=new Byte(b[i]).intValue();
	    String h=Integer.toHexString(j).toUpperCase();
	    switch(h.length()){
	      case 1:
	        h="0"+h;
	        break;
	      case 2:

	        break;
	      default:
	        h=h.substring(h.length()-2,h.length());
	        break;
	    }
	    sb.append(h);
	  }
    return sb.toString();
  }

  public boolean isSleep(){
      return isSleep;
  }
private void sendIntervalTFActionPerformed(java.awt.event.ActionEvent evt) {

}

private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {
  connectAction();          
}
void connectAction(){
  if(connectBtn.getText().equalsIgnoreCase(bundle2.getString("WSNSocketDevice.connectBtn.text"))){
    connect();
  } else {
     disconnect();
  }
}
public void connect(){

      if(getHost().length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNSocketDevice.xy.msg2")); return;}
      if(portTF.getText().trim().length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNSocketDevice.xy.msg3")); return;}

      if(getPort()==0) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNSocketDevice.xy.msg4")); return;}
      connectBtn.setText(bundle2.getString("WSNSocketDevice.xy.msg9"));
      if(reader==null) {
          reader=new Thread(this); 
          reader.start();

      } else {

          if(isSleep()) reader.interrupt();
      }
      if(lastIsData && !crnlCB.isSelected()) textPaneAppend("\r\n");
      textPaneAppend("Connecting to "+getHost()+":"+portTF.getText().trim()+".\r\n");
      lastIsData=false;

}
public void disconnect(){

    connectBtn.setText(bundle2.getString("WSNSocketDevice.connectBtn.text"));  

    connected=false;
    try{
      if(so!=null) so.close(); 
      if(lastIsData && !crnlCB.isSelected()) textPaneAppend("\r\n");

     lastIsData=false;
    } catch(IOException e){
        e.printStackTrace();
    }
    so=null;
    if(writer!=null) writer.stopContinueSend();
    informStatus(2);
}
private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {
 sendAction(1);
}

void sendAction(int callType){
  if(javaDataClassCB.isSelected()){
    byte b[]=getJavaClassData();
    jTextArea1.setText(send16RB.isSelected()? wsn.byteToStr(b):new String(b)); 
  } 
  String cmd=jTextArea1.getText().trim();

  if(cmd.trim().length()<1) {
      if(callType==1) JOptionPane.showMessageDialog(this,bundle2.getString("WSNSocketDevice.xy.msg5")); 
      else System.out.println(bundle2.getString("WSNSocketDevice.xy.msg5"));
      return;
  }
  if(connected && writer!=null && connectBtn.getText().equalsIgnoreCase(bundle2.getString("WSNSocketDevice.xy.msg9")) && status1.equalsIgnoreCase("login")){
      byte [] b=null;
      int sendType=1;
      long interval=10L * 1000L;
      if(continueSendCB.isSelected()) {
          sendType=2;
          String tmp=sendIntervalTF.getText().trim();
          if(tmp.length()>0 && wsn.isNumeric(tmp) && Double.parseDouble(tmp)>0) interval=(long)(Double.parseDouble(tmp)*1000.0);
          else {
            if(callType==1) JOptionPane.showMessageDialog(this,bundle2.getString("WSNSocketDevice.xy.msg6")); 
            else System.out.println(bundle2.getString("WSNSocketDevice.xy.msg6"));
              return;
          }
          if(randomCB.isSelected() && !wsn.isNumeric(cmd)) {
            if(callType==1)  JOptionPane.showMessageDialog(this,bundle2.getString("WSNSocketDevice.xy.msg7")); 
            else System.out.println(bundle2.getString("WSNSocketDevice.xy.msg7"));
              return;
          }
          if(interval<10L) interval=10L;
          stopContinueSendBtn.setEnabled(true);
      }

       byte b0[]=null;
       if(send16RB.isSelected()){
            int cnt=0;
            StringTokenizer st=new StringTokenizer(cmd, " ");
            int intx[]=new int[cmd.length()];
            if(cmd!=null && cmd.length()>0){
              for(cnt=0;st.hasMoreTokens();cnt++){
                intx[cnt]=Integer.decode("0x"+st.nextToken()).intValue();  
              }
              b0=new byte[cnt];
              for(int i=0;i<cnt;i++){
                b0[i]=(byte)intx[i];
              }
            }
          } else {
           b0=cmd.getBytes();
          }
          b0=wsn.addChksum(b0,checkSumCB.isSelected(),(String)jComboBox1.getSelectedItem());

      writer.send(b0,sendType,interval,randomCB.isSelected(),javaDataClassCB.isSelected());

      autoSendOK=true;
  } else {
      if(callType==1) JOptionPane.showMessageDialog(this,bundle2.getString("WSNSocketDevice.xy.msg8") +"\r\n(connected="+connected+",writer==null? "+(writer==null)+",connectBtn="+connectBtn.getText()+",status1="+status1);

      else if(!(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))) System.out.println(bundle2.getString("WSNSocketDevice.xy.msg8") +"\r\n(connected="+connected+",writer==null? "+(writer==null)+",connectBtn="+connectBtn.getText()+",status1="+status1);
  }
}
private void clearShowBtnActionPerformed(java.awt.event.ActionEvent evt) {
  jTextPane1.setText("");
  lastIsData=false;
}
public byte[] getJavaClassData(){
                  String cla=javaDataClassTF.getText();
                if(((WSNDataGenerator)wsn.jClasses.get(cla))==null){
                    wsn.loadClass(cla,4);

                }
                if(((WSNDataGenerator)wsn.jClasses.get(cla))!=null){
                 return ((WSNDataGenerator)wsn.jClasses.get(cla)).getData();
                }
                return new byte[0];
}
private void clearSendBtnActionPerformed(java.awt.event.ActionEvent evt) {
  jTextArea1.setText("");
}

private void formWindowClosing(java.awt.event.WindowEvent evt) {

    setVisible(false);
}

private void stopContinueSendBtnActionPerformed(java.awt.event.ActionEvent evt) {
  writer.stopContinueSend();
  stopContinueSendBtn.setEnabled(false);
}

private void randomCBActionPerformed(java.awt.event.ActionEvent evt) {
  if(randomCB.isSelected()) javaDataClassCB.setSelected(false);
}

private void javaDataClassCBActionPerformed(java.awt.event.ActionEvent evt) {
  if(javaDataClassCB.isSelected()) randomCB.setSelected(false);
}

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
      new WSNSocketDeviceSetup(this,true).setVisible(true);
    }
 public class Writer implements Runnable{

        byte [] toSend=null;
        int type=1;
        long interval=10000L;
        boolean isSleep=false,random=false,byGenerator=false;
        public Writer(OutputStream out){

        }
        public void run(){
         while(true){
          try{
            isSleep=true;
            if(type==1) Thread.sleep(1000L * 60L * 60L * 24L * 365L);
            else Thread.sleep(interval);
            isSleep=false;
          } catch(InterruptedException e){
              isSleep=false;
          }
          if(toSend!=null) {
              if(byGenerator){
                String cmd=(send16RB.isSelected()? wsn.byteToStr(getJavaClassData()):new String(getJavaClassData()));
               jTextArea1.setText(cmd);

               if(send16RB.isSelected()){
                 int cnt=0;
                 StringTokenizer st=new StringTokenizer(cmd, " ");
                 int intx[]=new int[cmd.length()];
                 if(cmd!=null && cmd.length()>0){
                   for(cnt=0;st.hasMoreTokens();cnt++){
                     intx[cnt]=Integer.decode("0x"+st.nextToken()).intValue();  
                   }
                   toSend=new byte[cnt];
                   for(int i=0;i<cnt;i++){
                     toSend[i]=(byte)intx[i];
                   }
                 }
               } else {
                   toSend=cmd.getBytes();
               }
               toSend=wsn.addChksum(toSend,checkSumCB.isSelected(),(String)jComboBox1.getSelectedItem());
               put(toSend);
              } else if(random){
               String cmd=new String(toSend);
               Double v=Double.parseDouble(cmd);
               v=Math.random() * v;
               v=((double)Math.round(v*1000.0))/1000.0;
               byte[] toSend2=String.valueOf(v).getBytes();
               toSend2=wsn.addChksum(toSend2,checkSumCB.isSelected(),(String)jComboBox1.getSelectedItem());
                put(toSend2);
              } else put(toSend);
          }
          if(type==1) toSend=null;
         }
        }
    public boolean isSleep(){
        return isSleep;
    }

    public void send(byte[] b,int type,long interval,boolean random,boolean byGenerator){
        switch(type){
            case 1:
                put(b);
                break;
            case 2:
               this.type=type;
               this.toSend=b;
               this.interval=interval;
               this.random=random;
               this.byGenerator=byGenerator;
               if(isSleep()) writerThread.interrupt();
               break;
        }

    }
    public void stopContinueSend(){
        toSend=null;
        type=1;
        if(isSleep()) writerThread.interrupt();
    }
    public synchronized boolean put(byte b[]) {
    if(!connected)  return false;
    try {

	out.write(b);
      } catch(IOException e)  {
         connectBtn.setText(bundle2.getString("WSNSocketDevice.connectBtn.text"));
         connected=false;
         status1="logout";
         informStatus(2);

      }
    return true;
  }
    }

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  public javax.swing.JCheckBox checkSumCB;
  private javax.swing.JButton clearSendBtn;
  private javax.swing.JButton clearShowBtn;
  public javax.swing.JButton connectBtn;
  public javax.swing.JCheckBox continueSendCB;
  public javax.swing.JCheckBox crnlCB;
  private javax.swing.JTextField ipTF;
  private javax.swing.JButton jButton1;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
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
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTextArea jTextArea1;
  public javax.swing.JTextPane jTextPane1;
  private javax.swing.JCheckBox javaDataClassCB;
  private javax.swing.JTextField javaDataClassTF;
  private javax.swing.JCheckBox javaHandlerClassCB;
  private javax.swing.JTextField javaHandlerClassTF;
  private javax.swing.JTextField portTF;
  public javax.swing.JCheckBox randomCB;
  public javax.swing.JRadioButton send16RB;
  private javax.swing.JButton sendBtn;
  public javax.swing.JTextField sendIntervalTF;
  public javax.swing.JRadioButton sendStrRB;
  public javax.swing.JRadioButton show16RB;
  public javax.swing.JCheckBox showCB;
  public javax.swing.JRadioButton showStrRB;
  public javax.swing.JCheckBox showTimeCB;
  private javax.swing.JButton stopContinueSendBtn;

}