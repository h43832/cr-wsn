package wsn;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import infinity.common.action.*;
import infinity.client.*;
import infinity.server.*;
import infinity.common.server.Connection;
import infinity.common.*;
import java.util.*;
import java.text.*;
import java.nio.*;

import java.util.regex.Pattern;
import y.ylib.ylib;
public class WSN extends javax.swing.JFrame implements GAction{
  public static String version="2.17.0011";
  public String newversion=version,versionDate="2017-04-20 13:00:00",gpw="nullgpw";
  public Weber w;
  public Net gs;
  ResourceBundle bundle2;
  Thread t,nodeThread;

  WSNUsage wsnUsage;
  WSNAbout wsnAbout;
  WSNFileThread fileThread;
  WSNDataProcessor dataThread;
  Vector displayV=new Vector();

  WSNNodesManager wsnNManager;

  public WSNActionProcessor actionThread;
  WSNSetupAp setupAp;
  DefaultStyledDocument styleDoc=new DefaultStyledDocument();
  public y.ylib.OpenURL openURL=new y.ylib.OpenURL();
  Vector waitAddV=new Vector(),waitRemoveV=new Vector(),wsnSDevices=new Vector(),lineCharts=new Vector(),eventHandlers=new Vector();
  TreeMap socketServers=new TreeMap(),serialPorts=new TreeMap();
  public TreeMap jClasses=new TreeMap();
  public Hashtable aTime=new Hashtable();
  TreeMap innerMembers=new TreeMap(),outerMembers=new TreeMap(),innerMemberItems=new TreeMap(),outerMemberItems=new TreeMap(),
          elseMemberItems=new TreeMap(),myAps=new TreeMap(),nameIdMap=new TreeMap(),dSrcRecord=new TreeMap(),dSrcRecordType=new TreeMap();
  private String pid="";
  public String sep=""+(char)0,currentItemData1[]={},currentInnerMemberId1="",currentViewId1="",
          currentItemData2[]={},currentInnerMemberId2="",currentViewId2="",statusFile="apps"+File.separator+"cr-wsn"+File.separator+"wsn_status.txt",
          currentViewId1_1="",currentViewId1_2="",currentViewId2_1="",currentViewId2_2="",logFileHead="log\\logWSN-",
          lastId1="",lastId2="",lastId1_1="",lastId1_2="",lastId2_1="",lastId2_2="",currentViewDSrc="",
          nodesFile="apps"+File.separator+"cr-wsn"+File.separator+"nodesfile1.txt",lastUpper="",statuses[]=new String[10];

  public DefaultListModel listModel1=new DefaultListModel(),listModel2=new DefaultListModel();
  boolean begin=true,lastIsData=false,beginToReceive=false,gIsFinal=false,isSleep=false,chkVersionOK=false;
  public SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"),formatter2=new SimpleDateFormat("yyyyMMddHHmmss"),
            formatter3=new SimpleDateFormat("HH:mm:ss");
  public String lastDataFid="",lastDataType="",lastDataPortN="",lastDataConnectionId="",allNodesName="????",allItemsName="????",myNodeName="??v";
  String propsFile="apps"+File.separator+"cr-wsn"+File.separator+"wsn_properties.txt",apId="1007";
  public String currentViewStatus1[]=null,currentViewStatus2[]=null,dataDir="",dataDate="";
  Color serialColor=Color.RED,socketColor=Color.BLUE,sysColor=new Color(0x4B,0x00,0x82);
  public int showIndex=7,showType=2,maxMainLogLength=100000,maxDSrcLogLength=10000;

    Properties props=new Properties();

    InputStream is = null;
    Thread pingT2=null;
    boolean up=false,needChk=false,hasNewVersion=false;
    long pingtime=60000L;

    public static final Pattern Num_PATTERN= Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
    public static boolean isNumeric(String s){
          return (s==null? false: Num_PATTERN.matcher(s).matches());
    }
  

  public static boolean isNumeric2(String str){
    return str.matches("-?\\d+(\\.\\d+)?");  
  }
 

public static boolean isNumeric3(String str){
  NumberFormat formatter = NumberFormat.getInstance();
  ParsePosition pos = new ParsePosition(0);
  formatter.parse(str, pos);
  return str.length() == pos.getIndex();
}

    public static final Pattern Hex_PATTERN= Pattern.compile("-?[0-9a-fA-F]+");
    public static boolean isHexNumber (String s) {
          return (s==null? false: Hex_PATTERN.matcher(s).matches());
    }

    public WSN() {
        initComponents();

        jList1.setPrototypeCellValue("256.256.256.256-100 yyy.yyy");
        jList2.setPrototypeCellValue("256.256.256.256-100 xxx.xxx");
        jList1.setFixedCellHeight(18);
        jList2.setFixedCellHeight(18);
        if(!new File("log").exists()) new File("log").mkdirs();
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        allNodesName=bundle2.getString("WSN.xy.msg7");
        allItemsName=bundle2.getString("WSN.xy.msg8");
        myNodeName=bundle2.getString("WSN.xy.msg9");
        for(int i=0;i<statuses.length;i++){
            statuses[i]="0";
        }
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 95000)/100000;
        int h2=(h * 95000)/100000;

        setSize(w2,h2);
        setTitle(bundle2.getString("WSN.xy.title")+version);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);

        jTextPane1.setText(bundle2.getString("WSN.xy.welcome")+"\r\n");
        beginToReceive=true;
        fileThread = new WSNFileThread(this);
        fileThread.start();
        dataThread = new WSNDataProcessor(this);
        dataThread.start();

        if(wsnNManager==null) wsnNManager=new WSNNodesManager(this,"temp");

        wsnNManager.listModel3.removeAllElements();
        String tmpName=makeListItem(allNodesName,"0");
        nameIdMap.put(tmpName,"0");
        listModel1.addElement(tmpName);
        listModel2.addElement(tmpName);
        wsnNManager.listModel3.addElement(tmpName);
        tmpName=makeListItem(myNodeName,"temp");
        nameIdMap.put(tmpName,"temp");
        listModel1.addElement(tmpName);
        listModel2.addElement(tmpName);
        wsnNManager.listModel3.addElement(tmpName);
    }
public TreeMap getSerialPorts(){
    return serialPorts;
}

  void updateTitle(){
      setTitle(bundle2.getString("WSN.xy.title")+version+"   in Group: "+w.getGNS(11)+(w.getGNS(38).length()>0? "      connect to: "+w.getGNS(8):"")+(gs!=null? "      number of client: "+gs.connectionCount():"")+(hasNewVersion? "      ("+bundle2.getString("WSN.xy.msg19")+")("+newversion+")":""));
  }

public void processData(String originalId,String stringx[]){
  try{
      String fid=stringx[1];
      String dataType=stringx[2];
      String portName=stringx[3];
      String connectionId=stringx[4];
      String dataHex=w.d642(stringx[5]),dataStr=getStringData(dataHex,-1,-1,-1);
      long time=System.currentTimeMillis();
      String shownData="",timeStr=formatter3.format(new Date(time)),
              dataSrc=(originalId.equals(w.getGNS(1))? "":fid+":")+portName+((portName.indexOf("COM")!=-1 || portName.indexOf("device")!=-1)? "":"-"+connectionId);

      byte[] b0={};
      if(beginToReceive && jList1.getSelectedValue()!=null && jComboBox1.getSelectedItem()!=null && jComboBox3.getSelectedItem()!=null && !dataType.equals("3")){
       if(getItemId((String)jList1.getSelectedValue()).equals("0") || originalId.equals(getItemId((String)jList1.getSelectedValue()))){
         if(((String)jComboBox1.getSelectedItem()).equals(allItemsName) || portName.equals((String)jComboBox1.getSelectedItem())){
           if(portName.toUpperCase().indexOf("COM")==0 || ((String)jComboBox3.getSelectedItem()).equals(allItemsName) || connectionId.equals((String)jComboBox3.getSelectedItem())){
            if(showCB.isSelected()){
            if(show16RB.isSelected()){
               shownData=dataHex;

            } else {
               shownData=dataStr;
               if(shownData.lastIndexOf("\r\n")!=-1 && shownData.lastIndexOf("\r\n")==(shownData.length()-2)) shownData=shownData.substring(0,shownData.length()-2);
               if(shownData.lastIndexOf("\n")!=-1 && shownData.lastIndexOf("\n")==(shownData.length()-1)) shownData=shownData.substring(0,shownData.length()-1);
             }
            if(!crnlCB.isSelected() && stringx[1].equals(lastDataFid) && stringx[2].equals(lastDataType) && stringx[3].equals(lastDataPortN) &&
                stringx[4].equals(lastDataConnectionId) && !begin && lastIsData){
                shownData=" "+shownData;
            } else {

                String tmp=(begin? "":"\r\n")+(showTimeCB.isSelected()? timeStr+" ":"")+(showSrcCB.isSelected()? fid+":"+portName+(portName.indexOf("COM")==-1?  "-"+connectionId+"-"+stringx[6]:"")+" ":"");
                textPaneAppend(tmp,Color.BLACK,0);

            } 

           textPaneAppend(shownData,(dataType.equals("1")? socketColor:serialColor),0);

           lastDataFid=stringx[1];
           lastDataType=stringx[2];
           lastDataPortN=stringx[3];
           lastDataConnectionId=stringx[4];
           lastIsData=true;
           if(dSrcRecord.get(dataSrc)!=null){
             DataRecord dRecord=((DataRecord) dSrcRecord.get(dataSrc));
             if(dRecord.sb.length()+dataHex.length()> maxDSrcLogLength) dRecord.clear();
             dRecord.sb.append(timeStr).append(" ").append(dRecord.hexType? dataHex:dataStr).append("\r\n");
             dSrcRecord.put(dataSrc,dRecord);
           } else {
             DataRecord dRecord=new DataRecord(show16RB.isSelected());
             dRecord.sb.append(timeStr).append(" ").append(dRecord.hexType? dataHex:dataStr).append("\r\n");
             dSrcRecord.put(dataSrc,dRecord);
           }

            }

           }
         }
       }
      } 
      Iterator it=myAps.values().iterator();
             for(;it.hasNext();){

               ((WSNApplication)it.next()).setData(time,originalId, dataSrc, dataHex);
           }

       for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setData(time,originalId, dataSrc, dataHex);
       for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setData(time,originalId, dataSrc, dataHex);
       if(saveFileCB.isSelected()) fileThread.setData(time,originalId, dataSrc+(portName.indexOf("COM")==-1?  "-"+stringx[6]:""), dataHex);
  } catch(ArrayIndexOutOfBoundsException e){
     if(w.w_debug) System.out.println("Warning: ArrayIndexOutOfBoundsException in wsn.processData():stringx[]="+w.arrayToCsvLine(stringx));

  }
}
static public String fileSeparator(String s){
  s=Weber.replace(s,"/",File.separator);
  s=Weber.replace(s,"\\",File.separator);
  return s;
}

private void readProperties(){

    propsFile=(w.getHVar("wsn_properties")==null? propsFile:w.getHVar("wsn_properties"));
    propsFile=w.fileSeparator(propsFile);

    File pFile=new File(propsFile);
    if(pFile.exists()){
    InputStream is = null;
    try {
        File f = new File(propsFile);
        is = new FileInputStream( f );
        props.load( is );

      } catch ( Exception e ) { 
          e.printStackTrace();
      }
    } else System.out.println("Warning: properties file '"+propsFile+"' not found, using default values.");

    if(props.getProperty("socket_port_open")==null)  props.setProperty("socket_port_open", "");

    if(props.getProperty("socket_port_default")==null)  props.setProperty("socket_port_default", ",5,60,hi,,1,,,,");
    if(props.getProperty("serial_port_open")==null)  props.setProperty("serial_port_open", "");

    if(props.getProperty("serial_port_default")==null)  props.setProperty("serial_port_default", ",115200,8,N,1,1,,,,");
    if(props.getProperty("chart_quantity")==null)  props.setProperty("chart_quantity", "0");
    if(props.getProperty("eventhandler_quantity")==null)  props.setProperty("eventhandler_quantity", "0");
    if(props.getProperty("socketdeviceemulator_quantity")==null)  props.setProperty("socketdeviceemulator_quantity", "0");
    if(props.getProperty("redirectorfile_name_prefix")==null)  props.setProperty("redirectorfile_name_prefix", "apps"+File.separator+"cr-wsn"+File.separator+"redirector");
    if(props.getProperty("devicefile_name_prefix")==null)  props.setProperty("devicefile_name_prefix", "apps"+File.separator+"cr-wsn"+File.separator+"device");
    if(props.getProperty("chartfile_name_prefix")==null)  props.setProperty("chartfile_name_prefix", "apps"+File.separator+"cr-wsn"+File.separator+"chart");
    if(props.getProperty("eventfile_name_prefix")==null)  props.setProperty("eventfile_name_prefix", "apps"+File.separator+"cr-wsn"+File.separator+"event");
    if(props.getProperty("savelog")==null)  props.setProperty("savelog", "N");
    if(props.getProperty("show_received")==null)  props.setProperty("show_received", "Y");
    if(props.getProperty("show_hex")==null)  props.setProperty("show_hex", "N");
    if(props.getProperty("show_linebreak")==null)  props.setProperty("show_linebreak", "N");
    if(props.getProperty("show_time")==null)  props.setProperty("show_time", "Y");
    if(props.getProperty("show_source")==null)  props.setProperty("show_source", "Y");
    if(props.getProperty("show_msg")==null)  props.setProperty("show_msg", "Y");
    if(props.getProperty("send_hex")==null)  props.setProperty("send_hex", "N");
    if(props.getProperty("send_addchecksumbysystem")==null)  props.setProperty("send_addchecksumbysystem", "N");
    if(props.getProperty("send_continuesend")==null)  props.setProperty("send_continuesend", "N");
    if(props.getProperty("send_intervaltimesecond")==null)  props.setProperty("send_intervaltimesecond", "5");
    if(props.getProperty("my_ap")==null)  props.setProperty("my_ap", "");
    if(props.getProperty("run_my_ap_only")==null)  props.setProperty("run_my_ap_only", "N");

}
   

    public int getMBCRC(byte[] buf, int len ) {
    int crc =  0xFFFF;
    int val = 0;

      for (int pos = 0; pos < len; pos++) {
        crc ^= (int)(0x00ff & buf[pos]);  

        for (int i = 8; i != 0; i--) {    
          if ((crc & 0x0001) != 0) {      
            crc >>= 1;                    
            crc ^= 0xA001;
          }
          else                            
            crc >>= 1;                    
        }
      }

    val =  (crc & 0xff) << 8;
    val =  val + ((crc >> 8) & 0xff);

    return val;  
}   

public byte[] IntTo2ByteArray( int data ) {
  byte[] result = new byte[2];
  result[0] = (byte) ((data & 0x0000FF00) >> 8);
  result[1] = (byte) ((data & 0x000000FF) >> 0);
  return result;
}

public float [] getFloat(byte[] b,int startInx,int len){
  float rtn[]=new float[len/4];
  for(int i=0;i<rtn.length;i++){
    byte b2[]={b[startInx+i*4+2],b[startInx+i*4+3],b[startInx+i*4],b[startInx+i*4+1]};
    rtn[i]=getFloat(b2);
  }
  return rtn;
}

 public float getFloat(byte[] b){
    int inx=byteArrayToInt(b);

    return Float.intBitsToFloat(inx);
  }

  public int byteArrayToInt(byte[] b) {
    return   b[3] & 0xFF |
            (b[2] & 0xFF) << 8 |
            (b[1] & 0xFF) << 16 |
            (b[0] & 0xFF) << 24;
}

  public byte[] intToByteArray(int a){
    return new byte[] {
        (byte) ((a >> 24) & 0xFF),
        (byte) ((a >> 16) & 0xFF),   
        (byte) ((a >> 8) & 0xFF),   
        (byte) (a & 0xFF)
    };
}

  public byte[] floatToByteArr(float f2){
    String hex=floatToHex(f2);
    byte b2[]={(byte)Integer.decode("0x"+hex.substring(4,6)).intValue(),(byte)Integer.decode("0x"+hex.substring(6,8)).intValue(),
    (byte)Integer.decode("0x"+hex.substring(0,2)).intValue(),(byte)Integer.decode("0x"+hex.substring(2,4)).intValue()};
    return b2;
  }

  public String floatToHex(float f2){

    int inx2=Float.floatToIntBits(f2);
    String bstr=Integer.toHexString(inx2);
    if(bstr.length()<8) bstr="00000000".substring(bstr.length())+bstr;
    else if(bstr.length()>8) bstr.substring(bstr.length()-8);
    return bstr;
  }
    private void init2(){

    lastIsData=false;

    DefaultCaret caret = (DefaultCaret)jTextPane1.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    makeDataDir();
    }
public void makeDataDir(){
    String fTime=formatter2.format(new Date());
    dataDate=fTime.substring(0,8);
    dataDir="data"+File.separator+fTime.substring(0,4)+File.separator+fTime.substring(4,6)+File.separator+fTime.substring(6,8);
    if(!(new File(dataDir)).exists())  (new File(dataDir)).mkdirs();
}
    @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    jPanel1 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jComboBox3 = new javax.swing.JComboBox();
    showCB = new javax.swing.JCheckBox();
    show16RB = new javax.swing.JRadioButton();
    showStrRB = new javax.swing.JRadioButton();
    crnlCB = new javax.swing.JCheckBox();
    showTimeCB = new javax.swing.JCheckBox();
    showSrcCB = new javax.swing.JCheckBox();
    showSysMsgCB = new javax.swing.JCheckBox();
    saveFileCB = new javax.swing.JCheckBox();
    chartBtn = new javax.swing.JButton();
    eventBtn = new javax.swing.JButton();
    myApBtn = new javax.swing.JButton();
    clearShowBtn = new javax.swing.JButton();
    jPanel5 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane(styleDoc);
    jScrollPane3 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList(listModel1);
    jPanel2 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jScrollPane4 = new javax.swing.JScrollPane();
    jList2 = new javax.swing.JList(listModel2);
    jPanel7 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jComboBox2 = new javax.swing.JComboBox();
    jComboBox4 = new javax.swing.JComboBox();
    send16RB = new javax.swing.JRadioButton();
    sendStrRB = new javax.swing.JRadioButton();
    jLabel5 = new javax.swing.JLabel();
    chkSumCB = new javax.swing.JCheckBox();
    jComboBox5 = new javax.swing.JComboBox();
    jPanel8 = new javax.swing.JPanel();
    continueSendCB = new javax.swing.JCheckBox();
    jLabel3 = new javax.swing.JLabel();
    sendIntervalTF = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    sendBtn = new javax.swing.JButton();
    stopContinueSendBtn = new javax.swing.JButton();
    clearSendBtn = new javax.swing.JButton();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem9 = new javax.swing.JMenuItem();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu4 = new javax.swing.JMenu();
    jMenuItem5 = new javax.swing.JMenuItem();
    jMenuItem4 = new javax.swing.JMenuItem();
    jMenuItem7 = new javax.swing.JMenuItem();
    jMenuItem6 = new javax.swing.JMenuItem();
    jMenuItem11 = new javax.swing.JMenuItem();
    jMenuItem8 = new javax.swing.JMenuItem();
    jMenu3 = new javax.swing.JMenu();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem10 = new javax.swing.JMenuItem();
    jMenuItem12 = new javax.swing.JMenuItem();
    jMenuItem13 = new javax.swing.JMenuItem();
    jMenuItem3 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSN.title")); 
    setName("Form"); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel1.setName("jPanel1"); 
    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel3.setBackground(new java.awt.Color(204, 255, 255));
    jPanel3.setName("jPanel3"); 
    jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setText(bundle.getString("WSN.jLabel1.text")); 
    jLabel1.setName("jLabel1"); 
    jPanel3.add(jLabel1);

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox1.setName("jComboBox1"); 
    jComboBox1.setPreferredSize(new java.awt.Dimension(80, 23));
    jComboBox1.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox1ItemStateChanged(evt);
      }
    });
    jPanel3.add(jComboBox1);

    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox3.setName("jComboBox3"); 
    jComboBox3.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox3ItemStateChanged(evt);
      }
    });
    jPanel3.add(jComboBox3);

    showCB.setBackground(new java.awt.Color(204, 255, 255));
    showCB.setText(bundle.getString("WSN.showCB.text")); 
    showCB.setName("showCB"); 
    jPanel3.add(showCB);

    show16RB.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(show16RB);
    show16RB.setText(bundle.getString("WSN.show16RB.text")); 
    show16RB.setName("show16RB"); 
    show16RB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        show16RBActionPerformed(evt);
      }
    });
    jPanel3.add(show16RB);

    showStrRB.setBackground(new java.awt.Color(204, 255, 255));
    buttonGroup1.add(showStrRB);
    showStrRB.setText(bundle.getString("WSN.showStrRB.text")); 
    showStrRB.setName("showStrRB"); 
    showStrRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        showStrRBActionPerformed(evt);
      }
    });
    jPanel3.add(showStrRB);

    crnlCB.setBackground(new java.awt.Color(204, 255, 255));
    crnlCB.setText(bundle.getString("WSN.crnlCB.text")); 
    crnlCB.setName("crnlCB"); 
    jPanel3.add(crnlCB);

    showTimeCB.setBackground(new java.awt.Color(204, 255, 255));
    showTimeCB.setText(bundle.getString("WSN.showTimeCB.text")); 
    showTimeCB.setName("showTimeCB"); 
    jPanel3.add(showTimeCB);

    showSrcCB.setBackground(new java.awt.Color(204, 255, 255));
    showSrcCB.setText(bundle.getString("WSN.showSrcCB.text")); 
    showSrcCB.setName("showSrcCB"); 
    jPanel3.add(showSrcCB);

    showSysMsgCB.setBackground(new java.awt.Color(204, 255, 255));
    showSysMsgCB.setText(bundle.getString("WSN.showSysMsgCB.text")); 
    showSysMsgCB.setName("showSysMsgCB"); 
    jPanel3.add(showSysMsgCB);

    saveFileCB.setBackground(new java.awt.Color(204, 255, 255));
    saveFileCB.setText(bundle.getString("WSN.saveFileCB.text")); 
    saveFileCB.setName("saveFileCB"); 
    jPanel3.add(saveFileCB);

    chartBtn.setText(bundle.getString("WSN.chartBtn.text")); 
    chartBtn.setName("chartBtn"); 
    chartBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        chartBtnActionPerformed(evt);
      }
    });
    jPanel3.add(chartBtn);

    eventBtn.setText(bundle.getString("WSN.eventBtn.text")); 
    eventBtn.setName("eventBtn"); 
    eventBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        eventBtnActionPerformed(evt);
      }
    });
    jPanel3.add(eventBtn);

    myApBtn.setText(bundle.getString("WSN.myApBtn.text")); 
    myApBtn.setName("myApBtn"); 
    myApBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        myApBtnActionPerformed(evt);
      }
    });
    jPanel3.add(myApBtn);

    clearShowBtn.setText(bundle.getString("WSN.clearShowBtn.text")); 
    clearShowBtn.setName("clearShowBtn"); 
    clearShowBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearShowBtnActionPerformed(evt);
      }
    });
    jPanel3.add(clearShowBtn);

    jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

    jPanel5.setName("jPanel5"); 
    jPanel5.setLayout(new java.awt.BorderLayout());

    jScrollPane2.setName("jScrollPane2"); 

    jTextPane1.setFont(jTextPane1.getFont());
    jTextPane1.setMinimumSize(new java.awt.Dimension(26, 23));
    jTextPane1.setName(""); 
    jScrollPane2.setViewportView(jTextPane1);

    jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    jScrollPane3.setName("jScrollPane3"); 

    jList1.setName("jList1"); 
    jList1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList1MouseClicked(evt);
      }
    });
    jList1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jList1KeyReleased(evt);
      }
    });
    jScrollPane3.setViewportView(jList1);

    jPanel5.add(jScrollPane3, java.awt.BorderLayout.WEST);

    jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel2.setName("jPanel2"); 
    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel6.setName("jPanel6"); 
    jPanel6.setLayout(new java.awt.BorderLayout());

    jScrollPane1.setName("jScrollPane1"); 

    jTextArea1.setColumns(20);
    jTextArea1.setFont(jTextArea1.getFont());
    jTextArea1.setRows(5);
    jTextArea1.setName("jTextArea1"); 
    jScrollPane1.setViewportView(jTextArea1);

    jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    jScrollPane4.setName("jScrollPane4"); 

    jList2.setName("jList2"); 
    jList2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList2MouseClicked(evt);
      }
    });
    jList2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        jList2KeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jList2KeyReleased(evt);
      }
    });
    jScrollPane4.setViewportView(jList2);

    jPanel6.add(jScrollPane4, java.awt.BorderLayout.WEST);

    jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

    jPanel7.setName("jPanel7"); 
    jPanel7.setLayout(new java.awt.GridLayout(2, 1));

    jPanel4.setBackground(new java.awt.Color(0, 0, 204));
    jPanel4.setName("jPanel4"); 
    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText(bundle.getString("WSN.jLabel2.text")); 
    jLabel2.setName("jLabel2"); 
    jPanel4.add(jLabel2);

    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox2.setName("jComboBox2"); 
    jComboBox2.setPreferredSize(new java.awt.Dimension(80, 23));
    jComboBox2.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox2ItemStateChanged(evt);
      }
    });
    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox2ActionPerformed(evt);
      }
    });
    jPanel4.add(jComboBox2);

    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox4.setName("jComboBox4"); 
    jComboBox4.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        jComboBox4ItemStateChanged(evt);
      }
    });
    jPanel4.add(jComboBox4);

    send16RB.setBackground(new java.awt.Color(0, 0, 204));
    buttonGroup2.add(send16RB);
    send16RB.setForeground(new java.awt.Color(255, 255, 255));
    send16RB.setText(bundle.getString("WSN.send16RB.text")); 
    send16RB.setName("send16RB"); 
    jPanel4.add(send16RB);

    sendStrRB.setBackground(new java.awt.Color(0, 0, 204));
    buttonGroup2.add(sendStrRB);
    sendStrRB.setForeground(new java.awt.Color(255, 255, 255));
    sendStrRB.setText(bundle.getString("WSN.sendStrRB.text")); 
    sendStrRB.setName("sendStrRB"); 
    jPanel4.add(sendStrRB);

    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setText(bundle.getString("WSN.jLabel5.text")); 
    jLabel5.setName("jLabel5"); 
    jPanel4.add(jLabel5);

    chkSumCB.setBackground(new java.awt.Color(0, 0, 204));
    chkSumCB.setForeground(new java.awt.Color(255, 255, 255));
    chkSumCB.setText(bundle.getString("WSN.chkSumCB.text")); 
    chkSumCB.setName("chkSumCB"); 
    jPanel4.add(chkSumCB);

    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CheckSum", "Modbus CRC", "0x0D" }));
    jComboBox5.setName("jComboBox5"); 
    jPanel4.add(jComboBox5);

    jPanel7.add(jPanel4);

    jPanel8.setBackground(new java.awt.Color(0, 0, 204));
    jPanel8.setName("jPanel8"); 
    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    continueSendCB.setBackground(new java.awt.Color(0, 0, 204));
    continueSendCB.setForeground(new java.awt.Color(255, 255, 255));
    continueSendCB.setText(bundle.getString("WSN.continueSendCB.text")); 
    continueSendCB.setName("continueSendCB"); 
    jPanel8.add(continueSendCB);

    jLabel3.setBackground(new java.awt.Color(0, 0, 204));
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText(bundle.getString("WSN.jLabel3.text")); 
    jLabel3.setName("jLabel3"); 
    jPanel8.add(jLabel3);

    sendIntervalTF.setText(bundle.getString("WSN.sendIntervalTF.text")); 
    sendIntervalTF.setToolTipText(bundle.getString("WSN.sendIntervalTF.toolTipText.text")); 
    sendIntervalTF.setMinimumSize(new java.awt.Dimension(26, 21));
    sendIntervalTF.setName("sendIntervalTF"); 
    sendIntervalTF.setPreferredSize(new java.awt.Dimension(46, 21));
    jPanel8.add(sendIntervalTF);

    jLabel4.setBackground(new java.awt.Color(0, 0, 204));
    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setText(bundle.getString("WSN.jLabel4.text")); 
    jLabel4.setName("jLabel4"); 
    jPanel8.add(jLabel4);

    sendBtn.setText(bundle.getString("WSN.sendBtn.text")); 
    sendBtn.setName("sendBtn"); 
    sendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sendBtnActionPerformed(evt);
      }
    });
    jPanel8.add(sendBtn);

    stopContinueSendBtn.setText(bundle.getString("WSN.stopContinueSendBtn.text")); 
    stopContinueSendBtn.setName("stopContinueSendBtn"); 
    stopContinueSendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopContinueSendBtnActionPerformed(evt);
      }
    });
    jPanel8.add(stopContinueSendBtn);

    clearSendBtn.setText(bundle.getString("WSN.clearSendBtn.text")); 
    clearSendBtn.setName("clearSendBtn"); 
    clearSendBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearSendBtnActionPerformed(evt);
      }
    });
    jPanel8.add(clearSendBtn);

    jPanel7.add(jPanel8);

    jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_START);

    getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

    jMenuBar1.setName("jMenuBar1"); 

    jMenu1.setText(bundle.getString("WSN.jMenu1.text")); 
    jMenu1.setName("jMenu1"); 

    jMenuItem9.setText(bundle.getString("WSN.jMenuItem9.text")); 
    jMenuItem9.setName("jMenuItem9"); 
    jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem9ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem9);

    jMenuItem1.setText(bundle.getString("WSN.jMenuItem1.text")); 
    jMenuItem1.setName("jMenuItem1"); 
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu4.setText(bundle.getString("WSN.jMenu4.text")); 
    jMenu4.setName("jMenu4"); 

    jMenuItem5.setText(bundle.getString("WSN.jMenuItem5.text")); 
    jMenuItem5.setName("jMenuItem5"); 
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem5);

    jMenuItem4.setText(bundle.getString("WSN.jMenuItem4.text")); 
    jMenuItem4.setName("jMenuItem4"); 
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem4);

    jMenuItem7.setText(bundle.getString("WSN.jMenuItem7.text")); 
    jMenuItem7.setName("jMenuItem7"); 
    jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem7ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem7);

    jMenuItem6.setText(bundle.getString("WSN.jMenuItem6.text")); 
    jMenuItem6.setName("jMenuItem6"); 
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem6ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem6);

    jMenuItem11.setFont(jMenuItem11.getFont());
    jMenuItem11.setText(bundle.getString("WSN.jMenuItem11.text")); 
    jMenuItem11.setName("jMenuItem11"); 
    jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem11ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem11);

    jMenuItem8.setText(bundle.getString("WSN.jMenuItem8.text")); 
    jMenuItem8.setName("jMenuItem8"); 
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem8ActionPerformed(evt);
      }
    });
    jMenu4.add(jMenuItem8);

    jMenuBar1.add(jMenu4);

    jMenu3.setText(bundle.getString("WSN.jMenu3.text")); 
    jMenu3.setName("jMenu3"); 

    jMenuItem2.setText(bundle.getString("WSN.jMenuItem2.text")); 
    jMenuItem2.setName("jMenuItem2"); 
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem2);

    jMenuItem10.setText(bundle.getString("WSN.jMenuItem10.text")); 
    jMenuItem10.setName("jMenuItem10"); 
    jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem10ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem10);

    jMenuItem12.setText(bundle.getString("WSN.jMenuItem12.text")); 
    jMenuItem12.setName("jMenuItem12"); 
    jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem12ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem12);

    jMenuItem13.setText(bundle.getString("WSN.jMenuItem13.text")); 
    jMenuItem13.setName("jMenuItem13"); 
    jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem13ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem13);

    jMenuItem3.setText(bundle.getString("WSN.jMenuItem3.text")); 
    jMenuItem3.setName("jMenuItem3"); 
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });
    jMenu3.add(jMenuItem3);

    jMenuBar1.add(jMenu3);

    setJMenuBar(jMenuBar1);

    pack();
  }

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
  w.ap.onExit(102);
  System.exit(0);
}

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
 if(wsnUsage==null) wsnUsage=new WSNUsage(this);
 if(!wsnUsage.isVisible()) wsnUsage.setVisible(true);
 String fn=bundle2.getString("WSN.xy.msg10");
    fn=w.fileSeparator(fn);
 wsnUsage.setPage(fn);
}

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
 if(wsnAbout==null) wsnAbout=new WSNAbout(this);
 if(!wsnAbout.isVisible()) wsnAbout.setVisible(true);
 String fn=bundle2.getString("WSN.xy.msg11");
    fn=w.fileSeparator(fn);
  wsnAbout.setPage(fn);
}

private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
  sendBtn.setEnabled(true);
}

private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
  if(wsnSDevices.size()<1) {
    if(props.getProperty("socketdeviceemulator_quantity")==null || props.getProperty("socketdeviceemulator_quantity").equals("0")) props.setProperty("socketdeviceemulator_quantity","1");
     for(int i=0;i<Integer.parseInt(props.getProperty("socketdeviceemulator_quantity"));i++){ 

       wsnSDevices.add(new WSNSocketDevice(this,pid));
     }
  } 
      Enumeration en=wsnSDevices.elements();
      for(;en.hasMoreElements();){
        Object o=en.nextElement();
        if(o!=null){
          ((WSNSocketDevice)o).setVisible(true);
          ((WSNSocketDevice)o).toFront();
        }
      }
}

private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
    String item="";
    if(jList1.getSelectedValue()!=null) item=(String)jList1.getSelectedValue();
    initWSNNManager();
    if(wsnNManager!=null) wsnNManager.setSelectedItem(item);
}

private void jList1MouseClicked(java.awt.event.MouseEvent evt) {

  if(evt.getClickCount()==2){
    String item="";
    if(jList1.getSelectedValue()!=null) item=(String)jList1.getSelectedValue();
    initWSNNManager();
    if(wsnNManager!=null) wsnNManager.setSelectedItem(item);
}
    changeListItem1();
}

private void jList1KeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  changeListItem1();
}

private void jList2MouseClicked(java.awt.event.MouseEvent evt) {
if(evt.getClickCount()==2){
    String item="";
    if(jList2.getSelectedValue()!=null) item=(String)jList2.getSelectedValue();
    initWSNNManager();
    if(wsnNManager!=null) wsnNManager.setSelectedItem(item);
}
changeListItem2();
}

private void jList2KeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  changeListItem2();
}

private void jList2KeyPressed(java.awt.event.KeyEvent evt) {

}

private void clearSendBtnActionPerformed(java.awt.event.ActionEvent evt) {
  jTextArea1.setText("");
}

private void clearShowBtnActionPerformed(java.awt.event.ActionEvent evt) {
  if(currentViewDSrc.length()>0) {
    if(dSrcRecord.get(currentViewDSrc)!=null) {
      DataRecord dRecord=(DataRecord) dSrcRecord.get(currentViewDSrc);
      dRecord.clear();
      dSrcRecord.put(currentViewDSrc, dRecord);
    }
  }
  else if(saveFileCB.isSelected()) fileThread.setData(0,"0","0", jTextPane1.getText().trim());

  clear();
}
void clear(){
   try{

   styleDoc.remove(0, jTextPane1.getDocument().getLength());
   begin=true;
 } catch(BadLocationException e){
   e.printStackTrace();
 }
 lastIsData=false;
}
private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {
		String cmd=jTextArea1.getText();
                Vector sendId=new Vector();
		if(cmd.trim().length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSN.xy.msg1")); return;}
                String interval=sendIntervalTF.getText().trim();
                if(interval.length()>0 && isNumeric(interval) && Double.parseDouble(interval)>0) {}
                 else {JOptionPane.showMessageDialog(this,bundle2.getString("WSN.xy.msg2")); return;}
                String id=getItemId((String)jList2.getSelectedValue());
                String id2=(String)jComboBox2.getSelectedItem();
                String id3=(String)jComboBox4.getSelectedItem();

                cmd=w.e642(cmd);
                if(id!=null && id.equals(allNodesName)){
                    int count=jList2.getModel().getSize();
                    for(int i=1;i<count;i++){
                        sendId.add(getItemId((String)listModel2.getElementAt(i)));
                    }
                    Enumeration en=sendId.elements();
                    for(;en.hasMoreElements();){
                       cmd="performcommand wsn.WSN cmd all all "+send16RB.isSelected()+" "+chkSumCB.isSelected()+" "+continueSendCB.isSelected()+" "+interval+" "+cmd+" "+w.e642((String)jComboBox5.getSelectedItem())+" 0 0 0 0 0"; 
                       w.sendToOne(cmd,(String)en.nextElement());
                    }
                } else if(id2!=null && id2.equals(allItemsName)){
                       cmd="performcommand wsn.WSN cmd all all "+send16RB.isSelected()+" "+chkSumCB.isSelected()+" "+continueSendCB.isSelected()+" "+interval+" "+cmd+" "+w.e642((String)jComboBox5.getSelectedItem())+" 0 0 0 0 0"; 
                       w.sendToOne(cmd,id);
                } else if(id3!=null && id3.equals(allItemsName)){
                       cmd="performcommand wsn.WSN cmd "+id2+" all "+send16RB.isSelected()+" "+chkSumCB.isSelected()+" "+continueSendCB.isSelected()+" "+interval+" "+cmd+" "+w.e642((String)jComboBox5.getSelectedItem())+" 0 0 0 0 0"; 
                       w.sendToOne(cmd,id);
                } else if(id3!=null){
                   if(id3.length()<1) id3="0";
   		   cmd="performcommand wsn.WSN cmd "+id2+" "+id3+" "+send16RB.isSelected()+" "+chkSumCB.isSelected()+" "+continueSendCB.isSelected()+" "+interval+" "+cmd+" "+w.e642((String)jComboBox5.getSelectedItem())+" 0 0 0 0 0"; 
                    w.sendToOne(cmd,id);
                } else if(id3==null) {JOptionPane.showMessageDialog(this, bundle2.getString("WSN.xy.msg3")); return;}
                stopContinueSendBtn.setEnabled(true);
}

private void stopContinueSendBtnActionPerformed(java.awt.event.ActionEvent evt) {

		String cmd="";
                String id=getItemId((String)jList2.getSelectedValue());
                Vector sendId=new Vector();
                String id2=(String)jComboBox2.getSelectedItem();
                String id3=(String)jComboBox4.getSelectedItem();
                if(id.equals(allNodesName)){
                    int count=jList2.getModel().getSize();
                    for(int i=1;i<count;i++){
                        sendId.add(getItemId((String)listModel2.getElementAt(i)));
                    }
                    Enumeration en=sendId.elements();
                    for(;en.hasMoreElements();){
                       cmd=cmd="performcommand wsn.WSN stopcontinue all all null"; 
                       w.sendToOne(cmd,(String)en.nextElement());
                    }
                } else if(id2!=null && id2.equals(allItemsName)){
                       cmd=cmd="performcommand wsn.WSN stopcontinue all all null"; 
                       w.sendToOne(cmd,id);
                } else if(id3!=null && id3.equals(allItemsName)){
                       cmd=cmd="performcommand wsn.WSN stopcontinue "+id2+" all null"; 
                       w.sendToOne(cmd,id);
                } else if(id3!=null){
   		   cmd=cmd="performcommand wsn.WSN stopcontinue "+id2+" "+id3+" null"; 
                    w.sendToOne(cmd,id);
                } else if(id3==null) {JOptionPane.showMessageDialog(this, bundle2.getString("WSN.xy.msg4")); return;}
                stopContinueSendBtn.setEnabled(false);

}

private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {
  if(evt.getStateChange()==evt.SELECTED){
      if(!((String)jComboBox2.getSelectedItem()).equals(currentViewId2_1)) lastId2_1=currentViewId2_1;
      currentViewId2_1=(String)jComboBox2.getSelectedItem();
      if(currentViewId2_1.equals(allItemsName)){
         jComboBox4.removeAllItems();
         jComboBox4.addItem(allItemsName);
         jComboBox4.setSelectedIndex(0);
      } else {
              if(currentViewId2_1.indexOf("COM")!=0){
                String contCmd="performmessage wsn.WSN getstatus2_1 "+currentViewId2_1+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                w.sendToOne(contCmd,currentViewId2);
              } else {
                  jComboBox4.removeAllItems();
                  jComboBox4.addItem(allItemsName);
                  jComboBox4.setSelectedIndex(0);
              }
      }
  }
}

private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {
 if(evt.getStateChange()==evt.SELECTED){
      lastId1_1=currentViewId1_1;
      currentViewId1_1=(String)jComboBox1.getSelectedItem();
      if(((String)jComboBox1.getSelectedItem()).equals(allItemsName)){
         jComboBox3.removeAllItems();
         jComboBox3.addItem(allItemsName);
         jComboBox3.setSelectedIndex(0);
         currentViewDSrc="";
      } else {
              if(currentViewId1_1.indexOf("COM")!=0){
                String contCmd="performmessage wsn.WSN getstatus1_1 "+currentViewId1_1+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                w.sendToOne(contCmd,currentViewId1);
                currentViewDSrc="";
              } else {
                jComboBox3.removeAllItems();
                jComboBox3.addItem(allItemsName);
                jComboBox3.setSelectedIndex(0);
                String lastDSrc=currentViewDSrc;
                currentViewDSrc=(currentViewId1.equals(w.getGNS(1))? "":getItemIp(currentViewId1)+":")+currentViewId1_1;
                if(!lastDSrc.equals(currentViewDSrc)){
                  if(lastDSrc.length()==0){
                    if(saveFileCB.isSelected()) fileThread.setData(0,"0","0", jTextPane1.getText().trim());
                  }
                  clear();
                }
                DataRecord dRecord=(DataRecord)dSrcRecord.get(currentViewDSrc);
                if(dRecord!=null) textPaneAppend(dRecord.sb.toString());
              }
      }
  }
}

private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {
  if(evt.getStateChange()==evt.SELECTED){
      lastId2_2=currentViewId2_2;
      currentViewId2_2=(String)jComboBox4.getSelectedItem();
      if(((String)jComboBox4.getSelectedItem()).equals(allItemsName)){
        sendBtn.setEnabled(true);
        stopContinueSendBtn.setEnabled(true);
      } else {
              if(currentViewId2_1.indexOf("COM")!=0){
                String contCmd="performmessage wsn.WSN getstatus2_2 "+currentViewId2_1+" "+currentViewId2_2+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                w.sendToOne(contCmd,currentViewId2);
              }
      }
  }
}

private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
   showControlChart();
}

private void chartBtnActionPerformed(java.awt.event.ActionEvent evt) {

  showControlChart();
}
private void showControlChart(){
  if(lineCharts.size()<1) {
    if(props.getProperty("chart_quantity")==null || props.getProperty("chart_quantity").equals("0")) props.setProperty("chart_quantity","1");
     for(int i=0;i<Integer.parseInt(props.getProperty("chart_quantity"));i++){ 

      WSNLineChart eh=new WSNLineChart();
      eh.init(this);
       lineCharts.add(eh);
     }
  } 
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();){
       Object o=en.nextElement();
       if(o!=null) {((WSNApplication) o).setVisible(true); ((WSNApplication) o).toFront();}
}
}
private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
    showEventHandler();
}

private void eventBtnActionPerformed(java.awt.event.ActionEvent evt) {

  showEventHandler();
}
private void showEventHandler(){
      if(eventHandlers.size()<1) {
    if(props.getProperty("eventhandler_quantity")==null || props.getProperty("eventhandler_quantity").equals("0")) props.setProperty("eventhandler_quantity","1");
     for(int i=0;i<Integer.parseInt(props.getProperty("eventhandler_quantity"));i++){ 

      WSNEventHandler eh=new WSNEventHandler();
      eh.init(this);
       eventHandlers.add(eh);
     }
  } 
    for(Enumeration en=eventHandlers.elements();en.hasMoreElements();){
       Object o=en.nextElement();
       if(o!=null) {((WSNApplication) o).setVisible(true); ((WSNApplication) o).toFront();}
    }
}
private void formWindowClosing(java.awt.event.WindowEvent evt) {
  w.ap.onExit(103);
  System.exit(0);
}

private void myApBtnActionPerformed(java.awt.event.ActionEvent evt) {
  myAp();
}
private void myAp(){
    if(myAps.size() < 1) {
            if(setupAp == null){
                setupAp = new WSNSetupAp(this, true);
                setupAp.init(this);
                setupAp.setVisible(true);
            } else {
                setupAp.setVisible(true);
            }
        } else {
            for(Iterator it = myAps.values().iterator(); it.hasNext();) {
               WSNApplication wa= (WSNApplication)it.next();

               if(!wa.isVisible()) if(!wa.runInBackground()) wa.setVisible(true);
            }
        }
}

private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {
  if(myAps.size()<1){
     int an=JOptionPane.showConfirmDialog(this, bundle2.getString("WSN.xy.msg5"),"confirm", JOptionPane.YES_NO_CANCEL_OPTION);
     if(an==JOptionPane.YES_OPTION) myAp();
     else return;
  }  else myAp();
  if(myAps.size()>0){
     int an=JOptionPane.showConfirmDialog(this, w.replace(bundle2.getString("WSN.xy.msg18"),"node_properties.txt",(new File(propsFile)).getName()),"confirm", JOptionPane.YES_NO_CANCEL_OPTION);
     if(an==JOptionPane.YES_OPTION) {
       props.setProperty("run_my_ap_only","Y");
       showCB.setSelected(false);
       setVisible(false);
       for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setVisible(false);
       for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setVisible(false);
       for(Enumeration en=wsnSDevices.elements();en.hasMoreElements();) ((WSNSocketDevice)en.nextElement()).setVisible(false);
       setProperties(); saveProperties(); 
     }
 }
}

private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {
  String file="datalog.txt";
  String str1=jTextPane1.getText().trim();
  if(str1.length()<1){JOptionPane.showMessageDialog(this, bundle2.getString("WSN.xy.msg12")); return;}
  JFileChooser chooser = new JFileChooser(file);
  chooser.setDialogTitle(bundle2.getString("WSN.xy.msg13"));
  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  if(file.length()>0){
    chooser.setSelectedFile(new File(new File(file).getName()));
  }
  int returnVal = chooser.showDialog(this,bundle2.getString("WSN.xy.msg14"));
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      file=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(file);
      boolean save=false;
      if(f2.exists()){
         int result = JOptionPane.showConfirmDialog(this, bundle2.getString("WSN.xy.msg15"), bundle2.getString("WSN.xy.msg16"), JOptionPane.YES_NO_CANCEL_OPTION);
         if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){
	try{
	  FileOutputStream out = new FileOutputStream (file);
	  byte [] b=str1.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
      }
  }
}

private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {
         String webAddr=bundle2.getString("WSN.xy.msg17");
         if(webAddr.indexOf("http")==-1){
             webAddr=webAddr.substring(5);
             webAddr=webAddr.replace('/', File.separatorChar);
             webAddr=(new File(webAddr)).getAbsolutePath();
             webAddr="file:///"+webAddr.replace(File.separatorChar,'/');
         }

         openURL.open(webAddr);
}

private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {
  myAp();
}

private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {
 if(evt.getStateChange()==evt.SELECTED){
      lastId1_2=currentViewId1_2;
      currentViewId1_2=(String)jComboBox3.getSelectedItem();
      if(((String)jComboBox3.getSelectedItem()).equals(allItemsName)){
         if(((String)jComboBox3.getSelectedItem()).indexOf("COM")!=0) currentViewDSrc="";
      } else {
                String lastDSrc=currentViewDSrc;
                currentViewDSrc=(currentViewId1.equals(w.getGNS(1))? "":getItemIp(currentViewId1)+":")+currentViewId1_1+"-"+currentViewId1_2;
                if(!lastDSrc.equals(currentViewDSrc)){
                  if(lastDSrc.length()==0){
                    if(saveFileCB.isSelected()) fileThread.setData(0,"0","0", jTextPane1.getText().trim());
                  }
                  clear();
                }
                DataRecord dRecord =(DataRecord)dSrcRecord.get(currentViewDSrc);
                if(dRecord!=null) textPaneAppend(dRecord.sb.toString());
      }
  }
}
private void setHexType(){
    if(currentViewDSrc.length()>0){
    DataRecord dRecord=(DataRecord)dSrcRecord.get(currentViewDSrc);
    if(dRecord!=null) {
      dRecord.hexType=show16RB.isSelected();
      dSrcRecord.put(currentViewDSrc, dRecord);
    }
  }
}
private void show16RBActionPerformed(java.awt.event.ActionEvent evt) {
  setHexType();
}

private void showStrRBActionPerformed(java.awt.event.ActionEvent evt) {
  setHexType();
}

  private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {
openURL.open("https://github.com/h43832/cr-wsn");
  }

  private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {
    openURL.open("http://groups.google.com/group/cr-wsn");
  }
public void saveLog(String str1){
  String dDate=formatter2.format(new Date()).substring(0,8);
  if(!dDate.equals(dataDate)) makeDataDir();
  String file="log_"+dDate+".txt";
  try{
	  FileOutputStream out = new FileOutputStream (dataDir+File.separator+file,true);
	  byte [] b=str1.getBytes();
	  out.write(b);
	  out.close();
      }catch(IOException e){e.printStackTrace();}
}
private void changeListItem1(){
  String id=getItemId((String)jList1.getSelectedValue());

  if(id!=null && id.length()>0){

  if(currentViewId1.length()>1){

    lastId1=currentViewId1;
    if(currentItemData1!=null && currentItemData1.length>2) currentItemData1[2]=w.getSsx(6,pid);
    if(currentItemData1!=null && currentItemData1.length>5) currentItemData1[5]="filler";
    if(currentItemData1!=null && currentItemData1.length>5 && currentItemData1[5].length()<1) currentItemData1[5]=currentItemData1[4];
    if(currentItemData1!=null) setItemData(currentInnerMemberId1,currentViewId1,currentItemData1);
  }

  currentViewId1=id;
  currentItemData1=getItemData(id,1);
  if(id.equals("0")){
      jComboBox1.removeAllItems();
      jComboBox3.removeAllItems();
      jComboBox1.addItem(allItemsName);
      jComboBox3.addItem(allItemsName);
      jComboBox1.setSelectedIndex(0);
      jComboBox3.setSelectedIndex(0);
  } else {

  if(currentItemData1.length>0){};
  if(currentItemData1.length>4) {};
  String showName=(currentItemData1!=null && currentItemData1.length>4? currentItemData1[4]:"");
  if(currentItemData1!=null && currentItemData1.length>5) {
     if(currentItemData1[5].length()>0) showName=currentItemData1[5]; else currentItemData1[5]=currentItemData1[4];
  }

  if(currentItemData1.length>2) {};
  if(currentItemData1.length>3 && currentItemData1[3].length()>0 && !w.checkOneVar(currentItemData1[1], 0)){
    String nData[]=getNodeData(currentItemData1[3]);
    currentItemData1[1]=w.addOneVar(currentItemData1[1],0);
    if(nData.length>5 && nData[5].length()>0) {
        if(currentItemData1!=null && currentItemData1.length>5) currentItemData1[5]=nData[5];

    }
    if(nData.length>2 && nData[2].length()>0) {
        if(currentItemData1!=null && currentItemData1.length>2) currentItemData1[2]=nData[2];

    }
  }

  String contCmd="performmessage wsn.WSN getstatus1 null null null null null null null null null 0 0 0 0 ? ? ? 0";
  w.sendToOne(contCmd,id);
  }
  }
}
private void changeListItem2(){
  String id=getItemId((String)jList2.getSelectedValue());

  if(id.length()>1){

  if(currentViewId2.length()>1){

    lastId2=currentViewId2;
    if(currentItemData2!=null && currentItemData2.length>2) currentItemData2[2]=w.getSsx(6,pid);;
    if(currentItemData2!=null && currentItemData2.length>5) currentItemData2[5]="filler";
    if(currentItemData2!=null && currentItemData2.length>2 && currentItemData2[5].length()<1) currentItemData2[5]=currentItemData2[4];
    if(currentItemData2!=null) setItemData(currentInnerMemberId2,currentViewId2,currentItemData2);
  }

  currentViewId2=id;
  currentItemData2=getItemData(id,2);
  if(id.equals("0")){
      jComboBox2.removeAllItems();
      jComboBox4.removeAllItems();
      jComboBox2.addItem(allItemsName);
      jComboBox4.addItem(allItemsName);
      jComboBox2.setSelectedIndex(0);
      jComboBox4.setSelectedIndex(0);
      sendBtn.setEnabled(true);
      stopContinueSendBtn.setEnabled(true);
  } else {

  if(currentItemData2.length>0){};
  if(currentItemData2.length>4) {};
  String showName=(currentItemData2!=null && currentItemData2.length>4? currentItemData2[4]:"");
  if(currentItemData2.length>5) {
     if(currentItemData2[5].length()>0) showName=currentItemData2[5]; else currentItemData2[5]=currentItemData2[4];
  }

  if(currentItemData2.length>2) {};
  if(currentItemData2.length>3 && currentItemData2[3].length()>0 && !w.checkOneVar(currentItemData2[1], 0)){
    String nData[]=getNodeData(currentItemData2[3]);
    currentItemData2[1]=w.addOneVar(currentItemData2[1],0);
    if(nData.length>5 && nData[5].length()>0) {
        if(currentItemData2!=null && currentItemData2.length>5) currentItemData2[5]=nData[5];

    }
    if(nData.length>2 && nData[2].length()>0) {
        if(currentItemData2!=null && currentItemData2.length>2) currentItemData2[2]=nData[2];

    }
  }

  String contCmd="performmessage wsn.WSN getstatus2 null null null null null null null null null 0 0 0 0 ? ? ? 0";
  w.sendToOne(contCmd,id);
  }
  }
}
public void initWSNNManager(){
    while(waitAddV.size()>0){
        String tmp=(String)waitAddV.get(0);
        if(!wsnNManager.listModel3.contains(tmp)) wsnNManager.listModel3.addElement(tmp);
        waitAddV.remove(0);
    }
    if(wsnNManager!=null) {wsnNManager.setVisible(true); wsnNManager.toFront();}
}    
private void readStatus(){
     if(statuses!=null)  for(int i=0;i<statuses.length;i++){ statuses[i]=""; }
     if(statusFile!=null && statusFile.length()>0){
      File f=new File(statusFile);
      try{
      if(f.exists() && f.isFile()){
        FileInputStream in=new FileInputStream(statusFile);
        BufferedReader d= new BufferedReader(new InputStreamReader(in));
        while(true){
	  String str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          String tmp[]=w.csvLineToArray(str1);
          if(tmp!=null && tmp.length>3){
              if(tmp.length <= statuses.length){
                  for(int i=0;i<tmp.length;i++) statuses[i]=tmp[i];
              } else w.monitor.writeLogStr("Error: status items in file "+statusFile+" > "+statuses.length+"\r\n");
              break;
          }
        }
	in.close();
	d.close();
        if(statuses[0].length()<1) statuses[0]="0";
          } else {
            w.monitor.writeLogStr("status file "+statusFile+" not found.\r\n");
            if(statuses!=null) for(int i=0;i<statuses.length;i++) statuses[i]="";
          }
           }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){
        w.monitor.writeLogStr("IO Error in reading "+statusFile+" file.\r\n");
        e.printStackTrace();
    }
    }

    }

  public boolean perform(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String stringx[],infinity.common.server.Connection c){
        this.w=w;
        pid=w.getPid(w.de("@@","W1H1a022MO=="));
        w.removeCommandPw(w.en(w.de("@@","W1H1a022MO=="),w.de("@@","W1H1a022MO==")), 3, pid);
        nameIdMap.put(myNodeName,w.getGNS(1));
        wsnNManager.setPid(pid);
        readProperties();
        for(int i=0;i<Integer.parseInt(props.getProperty("chart_quantity"));i++){
          WSNApplication a=new WSNLineChart();
          a.init(this);
          lineCharts.add(a);
        }
        for(int i=0;i<Integer.parseInt(props.getProperty("eventhandler_quantity"));i++){
          WSNApplication a=new WSNEventHandler();
          a.init(this);
          eventHandlers.add(a);
        }
        actionThread=new WSNActionProcessor(this);
        actionThread.start();
        init2();

        if(!chkProps("run_my_ap_only")) w.startUpd("http://cloud-rain.com/web/crwsn_version.txt", apId, version, 10L);

         (new WSNBlinker(this)).start();
         if(w.getHVar("a_test")==null || !w.getHVar("a_test").equalsIgnoreCase("true")){

         }
       if(props.getProperty("socket_port_open")!=null && props.getProperty("socket_port_open").length()>0){
           String tmp[]=w.csvLineToArray(props.getProperty("socket_port_open"));
           for(int i=0;i<tmp.length;i++) {
             tmp[i]=w.replace(tmp[i]," ","");
             int from=0,to=0,inx=tmp[i].indexOf("-");
             boolean chkOK=true;
             for(int j=0;j<tmp[i].length();j++){
               if((tmp[i].charAt(j)<'0' || tmp[i].charAt(j)>'9') && tmp[i].charAt(j)!='-') {textPaneAppend("'"+tmp[i]+"' not a valid socket port number or range!"); chkOK=false; break;}
             }
             if(chkOK) if(inx==0) {textPaneAppend("'"+tmp[i]+"' not a valid socket port number or range!"); chkOK=false;}
             if(chkOK) if(inx==tmp[i].length()-1) {textPaneAppend("'"+tmp[i]+"' not a valid socket port number or range!"); chkOK=false;}
             if(chkOK){
               if(inx>0){
                 from=Integer.parseInt(tmp[i].substring(0, inx));
                 to=Integer.parseInt(tmp[i].substring(inx+1));
               }else {
                 from=Integer.parseInt(tmp[i]);
                 to=Integer.parseInt(tmp[i]);
                }
               }

             for(int port2=from;port2<=to;port2++){
              WSNSocketServer sServer=null;
              sServer=new WSNSocketServer(this,port2);
             if(sServer!=null && sServer.canListenToPort()) socketServers.put(String.valueOf(port2),sServer);
             }
           }
       }
       if(props.getProperty("serial_port_open")!=null && props.getProperty("serial_port_open").length()>0){
           String tmp[]=w.csvLineToArray(props.getProperty("serial_port_open"));
           for(int i=0;i<tmp.length;i++) {
             tmp[i]=w.replace(tmp[i]," ","");
             tmp[i]=tmp[i].toUpperCase();               
             int from=0,to=0,inx=tmp[i].indexOf("-");
             boolean chkOK=true;
             for(int j=0;j<tmp[i].length();j++){
               if((tmp[i].charAt(j)<'0' || tmp[i].charAt(j)>'9') && tmp[i].charAt(j)!='-' && tmp[i].charAt(j)!='C' && tmp[i].charAt(j)!='O' && tmp[i].charAt(j)!='M') {textPaneAppend("'"+tmp[i]+"' not a valid serial port number or range!"); chkOK=false; break;}
             }
             if(chkOK) if(inx==0) {textPaneAppend("'"+tmp[i]+"' not a valid serial port number or range!"); chkOK=false;}
             if(chkOK) if(tmp[i].indexOf("COM")!=0) {textPaneAppend("'"+tmp[i]+"' not a valid serial port number or range!"); chkOK=false;}

             if(chkOK) if(inx>0 && tmp[i].indexOf("COM",inx)==-1) {textPaneAppend("'"+tmp[i]+"' not a valid serial port number or range!"); chkOK=false;}
             if(chkOK){
               if(inx>0){
                 from=Integer.parseInt(tmp[i].substring(3, inx));
                 to=Integer.parseInt(tmp[i].substring(inx+4));
               }else {
                 from=Integer.parseInt(tmp[i].substring(3));
                 to=Integer.parseInt(tmp[i].substring(3));
                }
               }

             for(int port2=from;port2<=to;port2++){
               WSNSerial serial=new WSNSerial(this,"COM"+port2,pid);

             }
           }
       }
       

       

       

        if(nodeThread==null){
            nodeThread=new NodeThread();
            nodeThread.start();
        }
      readStatus();
      elseMemberItems.put("0","0,0,,,"+allNodesName+",,,,,,,,,,,,,,,,");
      elseMemberItems.put(w.getGNS(1),w.getGNS(1)+",0,,,"+myNodeName+",,,,,,,,,,,,,,,,");
      showCB.setSelected(true);
      show16RB.setSelected(true);
      showTimeCB.setSelected(true);
      showSrcCB.setSelected(true);
      showSysMsgCB.setSelected(true);
      sendStrRB.setSelected(true);
      jList1.setSelectedIndex(0);
      jComboBox1.removeAllItems();
      jComboBox1.addItem(allItemsName);
      jComboBox3.removeAllItems();
      jComboBox3.addItem(allItemsName);
      jComboBox1.setSelectedIndex(0);
      jComboBox3.setSelectedIndex(0);
      jList2.setSelectedIndex(1);
      changeListItem2();

       for(int i=0;i<Integer.parseInt(props.getProperty("socketdeviceemulator_quantity"));i++){
          wsnSDevices.add(new WSNSocketDevice(this,pid));
        }
       if(props.getProperty("savelog")!=null && props.getProperty("savelog").equalsIgnoreCase("Y")) saveFileCB.setSelected(true); else saveFileCB.setSelected(false); 
       if(props.getProperty("show_received")!=null && props.getProperty("show_received").equalsIgnoreCase("Y")) showCB.setSelected(true); else showCB.setSelected(false);
       if(props.getProperty("show_hex")!=null && props.getProperty("show_hex").equalsIgnoreCase("Y")) show16RB.setSelected(true); else showStrRB.setSelected(true);
       if(props.getProperty("show_linebreak")!=null && props.getProperty("show_linebreak").equalsIgnoreCase("Y")) crnlCB.setSelected(true); else crnlCB.setSelected(false);
       if(props.getProperty("show_time")!=null && props.getProperty("show_time").equalsIgnoreCase("Y")) showTimeCB.setSelected(true); else showTimeCB.setSelected(false);
       if(props.getProperty("show_source")!=null && props.getProperty("show_source").equalsIgnoreCase("Y")) showSrcCB.setSelected(true); else showSrcCB.setSelected(false);
       if(props.getProperty("show_msg")!=null && props.getProperty("show_msg").equalsIgnoreCase("Y")) showSysMsgCB.setSelected(true); else showSysMsgCB.setSelected(false);
       if(props.getProperty("send_hex")!=null && props.getProperty("send_hex").equalsIgnoreCase("Y")) send16RB.setSelected(true); else sendStrRB.setSelected(true);
       if(props.getProperty("send_addchecksumbysystem")!=null && props.getProperty("send_addchecksumbysystem").equalsIgnoreCase("Y")) chkSumCB.setSelected(true); else chkSumCB.setSelected(false);
         props.put("send_addchecksumtype",(String)jComboBox5.getSelectedItem());
       if(props.getProperty("send_addchecksumtype")!=null) jComboBox5.setSelectedItem(props.getProperty("send_addchecksumtype"));
       if(props.getProperty("send_continuesend")!=null && props.getProperty("send_continuesend").equalsIgnoreCase("Y")) continueSendCB.setSelected(true); else continueSendCB.setSelected(false);
       if(props.getProperty("send_intervaltimesecond")!=null) sendIntervalTF.setText(props.getProperty("send_intervaltimesecond"));

       loadAp();
       if(props.getProperty("run_my_ap_only")!=null && props.getProperty("run_my_ap_only").equalsIgnoreCase("Y") && myAps.size()>0){
           for(Iterator it = myAps.values().iterator(); it.hasNext();) {
             WSNApplication wa=((WSNApplication)it.next());
             if(!wa.runInBackground()) wa.setVisible(true);
           }
           showCB.setSelected(false);
           setVisible(false);
       } else this.setVisible(true);
       updateTitle();
      return true;
  }
  public boolean command(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String cmd,infinity.common.server.Connection c){

    this.w=w;
    this.gs=gs;
   StringTokenizer st=new StringTokenizer(cmd," ");
   int cnt=st.countTokens();
   String stringx[]=new String[cnt];
   for(int i=0;i<cnt;i++){
       stringx[i]=st.nextToken();
       stringx[i]=w.replaceCommon(stringx[i]);
   }

   if(stringx[0].equalsIgnoreCase("cmd")) {

     String id2=stringx[1];
     String id3=stringx[2];
     boolean isHex=Boolean.parseBoolean(stringx[3]);
     boolean needSysChksum=Boolean.parseBoolean(stringx[4]);
     boolean isContinue=Boolean.parseBoolean(stringx[5]);
     long interval=(long)(Double.parseDouble(stringx[6])* 1000.0);
     String cmd2=w.d642(stringx[7]);
     String chksumType="unknown";
     if(stringx.length>8) chksumType=w.d642(stringx[8]);
     else needSysChksum=false;

     if(id2.equalsIgnoreCase("all")){
         if(serialPorts.size()>0){
             TreeMap tmp=(TreeMap) serialPorts.clone();
             Iterator it=tmp.keySet().iterator();
             for(;it.hasNext();){
               String key=(String)it.next();
               if(serialPorts.containsKey(key)){
                WSNSerial sPort=(WSNSerial)serialPorts.get(key);

                sPort.sendCmd((isHex? "hex":"string"), cmd2, needSysChksum,chksumType,(isContinue? 2:1), interval);
               }
             }
         }
         if(socketServers.size()>0){
             TreeMap tmp=(TreeMap) socketServers.clone();
             Iterator it=tmp.keySet().iterator();
             for(;it.hasNext();){
               String key=(String)it.next();
               if(socketServers.containsKey(key)){
                WSNSocketServer sServer=(WSNSocketServer)socketServers.get(key);

                if(sServer.connectionCount()>0){
                  WSNSocketConnection cns[]=sServer.getConnections();
                  for(int i=0;i<cns.length;i++){
                    if(cns[i]!=null) cns[i].sendCmd((isHex? "hex":"string"), cmd2, needSysChksum,chksumType,(isContinue? 2:1), interval);
                  }
                }
               }
             }
         }
     }

     else if(id2.toUpperCase().indexOf("COM")==0){
         if(serialPorts.containsKey(id2)){
             WSNSerial sPort=(WSNSerial)serialPorts.get(id2);

             sPort.sendCmd((isHex? "hex":"string"), cmd2, needSysChksum,chksumType,(isContinue? 2:1), interval);
         } else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no serialport:"+id2+"\r\n");
     } else if(socketServers.containsKey(id2)){
         WSNSocketServer sServer=(WSNSocketServer) socketServers.get(id2);
         if(id3.equalsIgnoreCase("all")){

                if(sServer.connectionCount()>0){
                  WSNSocketConnection cns[]=sServer.getConnections();
                  for(int i=0;i<cns.length;i++){
                    if(cns[i]!=null) cns[i].sendCmd((isHex? "hex":"string"), cmd2, needSysChksum,chksumType,(isContinue? 2:1), interval);
                  }
                }

         } else {
           WSNSocketConnection conn=sServer.getConnection(id3);
           if(conn!=null) conn.sendCmd((isHex? "hex":"string"), cmd2, needSysChksum,chksumType,(isContinue? 2:1), interval);
           else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"socketport:"+id2+" no connection "+id3+"\r\n");
         }
     } else if(id2.indexOf("device@")!=-1){
        int count=wsnSDevices.size();
        if(count>0){
          Enumeration en=wsnSDevices.elements();
         for(;en.hasMoreElements();){
           Object o=en.nextElement();
           if(o!=null){
            WSNSocketDevice device=((WSNSocketDevice)o);
            String config[]=device.getDeviceConfig();
            if(id2.equals("device@"+config[4]+":"+config[5]) && device.connected){
              device.writer.send((isHex? getByteData(cmd2):cmd2.getBytes()), (isContinue? 2:1), interval, false, false);

            }
          }
          }
        }
     } else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no port:"+id2+"\r\n");
     return true;
   }
   if(stringx[0].equalsIgnoreCase("closeserialport")) {

     String id3=stringx[1];
     if(serialPorts.get(id3)!=null){
       closeSerialPort(id3);
     } else  w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no serialport:"+id3+" found.\r\n");
     return true;
   }
   if(stringx[0].equalsIgnoreCase("setdefaultconfig")) {

     setNodeDefaultConfig(stringx[1]);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("setotherconfig")) {

     setNodeOtherConfig(originalId,stringx[1]);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("setserialportconfig")) {

     setSerialportConfig(stringx);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("setsocketportconfig")) {
     setSocketportConfig(stringx);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("stopcontinue")) {
     String id2=stringx[1];
     String id3=stringx[2];
     if(id2.equalsIgnoreCase("all")){
         if(serialPorts.size()>0){
             TreeMap tmp=(TreeMap) serialPorts.clone();
             Iterator it=tmp.keySet().iterator();
             for(;it.hasNext();){
               String key=(String)it.next();
               if(serialPorts.containsKey(key)){
                WSNSerial sPort=(WSNSerial)serialPorts.get(key);

                sPort.stopContinueSend();
               }
             }
         }
         if(socketServers.size()>0){
             TreeMap tmp=(TreeMap) socketServers.clone();
             Iterator it=tmp.keySet().iterator();
             for(;it.hasNext();){
               String key=(String)it.next();
               if(socketServers.containsKey(key)){
                WSNSocketServer sServer=(WSNSocketServer)socketServers.get(key);

                if(sServer.connectionCount()>0){
                  WSNSocketConnection cns[]=sServer.getConnections();
                  for(int i=0;i<cns.length;i++){
                    if(cns[i]!=null) cns[i].stopContinueSend();
                  }
                }
               }
             }
         }
     }

     else if(id2.toUpperCase().indexOf("COM")==0){
         if(serialPorts.containsKey(id2)){
             WSNSerial sPort=(WSNSerial)serialPorts.get(id2);

             sPort.stopContinueSend();
         } else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no serialport:"+id2+"\r\n");
     } else if(socketServers.containsKey(id2)){
         WSNSocketServer sServer=(WSNSocketServer) socketServers.get(id2);
         if(id3.equalsIgnoreCase("all")){

                if(sServer.connectionCount()>0){
                  WSNSocketConnection cns[]=sServer.getConnections();
                  for(int i=0;i<cns.length;i++){
                    if(cns[i]!=null) cns[i].stopContinueSend();
                  }
                }

         } else {
           WSNSocketConnection conn=sServer.getConnection(id3);
           if(conn!=null) conn.stopContinueSend();
           else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"socketport:"+id2+" no connection "+id3+"\r\n");
         }
     } else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no socketport:"+id2+"\r\n");
     return true;
   }
   if(stringx[0].equalsIgnoreCase("opensocketport")) {

     String port=stringx[1];
     String maxCount=stringx[2];
     String hbeat=stringx[3];

     if(socketServers.get(port)!=null){
        String cmd2="socketport "+port+" already open, cann't be reopen.";
        cmd2="performmessage wsn.WSN wsn_msg "+w.e642(cmd2);
        w.sendToOne(cmd2,originalId);

         return false;
     }
     WSNSocketServer sServer=new WSNSocketServer(this,Integer.parseInt(port),Integer.parseInt(maxCount),Integer.parseInt(hbeat),pid);
     if(sServer!=null && sServer.canListenToPort()) socketServers.put(port, sServer);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("openserialport")) {

     String comName=stringx[1];
     String bRate=stringx[2];
     String dataB=stringx[3];
     String parityB=stringx[4];
     String stopB=stringx[5];
     if(serialPorts.get(comName)!=null){
        String cmd2="serialport "+comName+" already open, cann't be reopen.";
        cmd2="performmessage wsn.WSN wsn_msg "+w.e642(cmd2);
        w.sendToOne(cmd2,originalId);

         return false;
     }
     WSNSerial serial=new WSNSerial(this,comName,Integer.parseInt(bRate),Integer.parseInt(dataB),parityB,stopB,pid);

     return true;
   }
   if(stringx[0].equalsIgnoreCase("closesocketport")) {

     String id3=stringx[1];
     if(socketServers.get(id3)!=null){
         WSNSocketServer sServer=(WSNSocketServer)socketServers.get(id3);
         sServer.close();
         socketServers.remove(id3);
         String cmd2=w.getGNS(6)+" socket port "+id3+" closed.";

         cmd2="performmessage wsn.WSN wsn_closesocketport "+w.getGNS(6)+" "+id3+" "+w.e642(cmd2);
         w.sendToAll(cmd2);
     } else  w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no socketport:"+id3+" found.\r\n");
     return true;
   }
   if(stringx[0].equalsIgnoreCase("closesocketconnection")) {

     String id2=stringx[1];
     String id3=stringx[2];
     if(socketServers.get(id2)!=null){
         WSNSocketServer sServer=(WSNSocketServer)socketServers.get(id2);
         WSNSocketConnection con=sServer.getConnection(id3);
         if(con!=null) {
             sServer.removeConnection(con);
             String cmd2="socket connection "+id2+":"+id3+" ("+con.getIp()+") disconnected.";
             cmd2="performmessage wsn.WSN wsn_closesocketconnection "+w.getGNS(6)+" "+id2+" "+id3+" "+w.e642(cmd2);
             w.sendToAll(cmd2);
         }
         else w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no connection id :"+id2+" found.\r\n");
     } else  w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no socketport:"+id2+" found.\r\n");
     return true;
   }
   if(stringx[0].equalsIgnoreCase("setmaxconnection")) {

     String id3=stringx[1];
     int count=Integer.parseInt(stringx[2]);
     if(socketServers.get(id3)!=null){
         WSNSocketServer sServer=(WSNSocketServer)socketServers.get(id3);
         sServer.setMaxConnection(count);
     } else  w.ap.feedback(mode0,1,"nullcmdcode",originalId,"sys_mes"+sep+"no socketport:"+id3+" found.\r\n");
     return true;
   }
    return false;
  }
       /**
   * The parameter definition of this is the same as command().
   * The difference between command() and msg() is that msg() can be called without authorized code.
   */
  public boolean msg(int mode0,int modeNow,String originalId,String scheduleItemId,Weber w,Net gs,String cmd,infinity.common.server.Connection c){
   this.w=w;
   this.gs=gs;
   StringTokenizer st=new StringTokenizer(cmd," ");
   int cnt=st.countTokens();
   String stringx[]=new String[cnt];
   for(int i=0;i<cnt;i++){
       stringx[i]=st.nextToken();
       stringx[i]=w.replaceCommon(stringx[i]);
   }
   if(stringx[0].equalsIgnoreCase("fromtop_remove_outer")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: fromtop_remove_outer: "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              outerMembers.remove(keys[i]);
              outerMemberItems.remove(keys[i]);

              String tmpName=getListItem(keys[i]);
              if(tmpName!=null){
              nameIdMap.remove(tmpName);

              listModel1.removeElement(tmpName);
              listModel2.removeElement(tmpName);
              if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
              }
          }
      }      
      return true;
   }

   if(stringx[0].equalsIgnoreCase("fromdown_add_inner") || stringx[0].equalsIgnoreCase("fromdown_add_inner2") || stringx[0].equalsIgnoreCase("fromdown_add_inner3")) {
      if(!originalId.equals(w.getGNS(1))){
        TreeMap tm,tmItems;
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+":");
        if(!innerMembers.containsKey(originalId)){
            tm=new TreeMap();
            tmItems=new TreeMap();
            Connection cn=gs.getConnection(originalId,pid);
            tm.put(originalId,w.arrayToCsvLine(cn.getCGNS()));
            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.arrayToCsvLine(cn.getCGNS()));
            tmItems.put(originalId,originalId+",0,,,"+cn.getNName()+",,,,,,,,,,,,,,,,");
            innerMembers.put(originalId,tm);
            innerMemberItems.put(originalId,tmItems);
            String tmpName=getListItem(cn.getCGNS(),showIndex);
            nameIdMap.put(tmpName,cn.getCGNS()[0]);
            if(!listModel1.contains(tmpName)) listModel1.addElement(tmpName);
            if(!listModel2.contains(tmpName)) listModel2.addElement(tmpName);
            if(wsnNManager!=null && !wsnNManager.listModel3.contains(tmpName)) wsnNManager.listModel3.addElement(tmpName);
            else waitAddV.addElement(tmpName);
        } else {tm=(TreeMap)innerMembers.get(originalId); tmItems=(TreeMap)innerMemberItems.get(originalId);}
          String data[]=w.csvLineToArray(stringx[1]);
          for(int i=0;i<data.length;i++){
              String item[]=w.csvLineToArray(w.de("@@",data[i]));
              if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" "+w.de("@@",data[i]));
              tm.put(item[0],w.de("@@",data[i]));
              tmItems.put(item[0],item[i]+",0,,,"+item[showIndex]+",,,,,,,,,,,,,,,,");
              String tmpName=getListItem(item,showIndex);
              nameIdMap.put(tmpName, item[0]);
              if(!listModel1.contains(getListItem(item,showIndex))) listModel1.addElement(tmpName);
              if(!listModel2.contains(getListItem(item,showIndex))) listModel2.addElement(tmpName);
              if(wsnNManager!=null && !wsnNManager.listModel3.contains(tmpName)) wsnNManager.listModel3.addElement(tmpName);
              else waitAddV.addElement(tmpName);
          }
          innerMembers.put(originalId,tm);
          innerMemberItems.put(originalId,tmItems);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
      }      
     String cmd2="performmessage wsn.WSN fromdown_add_inner3 "+stringx[1];
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to upper: fromdown_add_inner3 : "+stringx[1]+"\r\n");
      w.sendToUpper(cmd2);
     cmd2="performmessage wsn.WSN fromupper_add_outer "+stringx[1];
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_add_outer : "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromupper_add_outer") || stringx[0].equalsIgnoreCase("fromupper_add_outer2") 
           || stringx[0].equalsIgnoreCase("fromtop_add_outer") || stringx[0].equalsIgnoreCase("fromupper_add_outer4")
            || stringx[0].equalsIgnoreCase("fromupper_add_outer3") || stringx[0].equalsIgnoreCase("fromupper_add_outer5") 
           || stringx[0].equalsIgnoreCase("fromupper_add_outer6") || stringx[0].equalsIgnoreCase("fromupper_add_outer7")
           || stringx[0].equalsIgnoreCase("fromupper_add_outer8")) {
      if(!originalId.equals(w.getGNS(1))){
          String data[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+":");
          for(int i=0;i<data.length;i++){

              String item[]=w.csvLineToArray(w.de("@@",data[i]));
              if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" "+w.de("@@",data[i]));

              if(!outerMembers.containsKey(item[0])){
                outerMembers.put(item[0],w.de("@@",data[i]));
                outerMemberItems.put(item[0],item[0]+",0,,,"+item[showIndex]+",,,,,,,,,,,,,,,,");
                String tmpName=getListItem(item,showIndex);
                nameIdMap.put(tmpName,item[0]);
                if(!listModel1.contains(getListItem(item,showIndex))) listModel1.addElement(tmpName);
                if(!listModel2.contains(getListItem(item,showIndex))) listModel2.addElement(tmpName);

                if(wsnNManager!=null && !wsnNManager.listModel3.contains(tmpName)) wsnNManager.listModel3.addElement(tmpName);
                else waitAddV.addElement(tmpName);
              }
          }
         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
      }      
     String cmd2="performmessage wsn.WSN fromupper_add_outer2 "+stringx[1];
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_add_outer2: "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptOne(cmd2,w.getGNS(1));
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromtop_remove_outer2") || stringx[0].equalsIgnoreCase("fromtop_remove_outer3")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              outerMembers.remove(keys[i]);

              outerMemberItems.remove(keys[i]);

              String tmpName=getListItem(keys[i]);
              if(tmpName!=null){
              nameIdMap.remove(tmpName);

              listModel1.removeElement(tmpName);
              listModel2.removeElement(tmpName);

              if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
              }
          }
      }  
      String cmd2="performmessage wsn.WSN fromtop_remove_outer3 "+stringx[1];
      if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromtop_remove_outer3: "+stringx[1]+"\r\n");
      w.sendToSubLayerExceptOne(cmd2,w.getGNS(1));   
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromdown_remove_inner") || stringx[0].equalsIgnoreCase("fromdown_remove_inner2")) {
      if(!originalId.equals(w.getGNS(1))){
        TreeMap tm,tmItems;
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]);
        if(!innerMembers.containsKey(originalId)){
            tm=new TreeMap();
            tmItems=new TreeMap();
            Connection cn=gs.getConnection(originalId,pid);
            tm.put(originalId,w.arrayToCsvLine(cn.getCGNS()));
            tmItems.put(originalId,originalId+",0,,,"+cn.getNName()+",,,,,,,,,,,,,,,,");
            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(" (add original "+originalId+")");
            innerMembers.put(originalId,tm);
            innerMemberItems.put(originalId, tmItems);
            String tmpName=getListItem(cn.getCGNS(),showIndex);
            nameIdMap.put(tmpName, cn.getCGNS()[0]);
            if(!listModel1.contains(tmpName)) listModel1.addElement(tmpName);
            if(!listModel2.contains(tmpName)) listModel2.addElement(tmpName);
            if(wsnNManager!=null && !wsnNManager.listModel3.contains(tmpName)) wsnNManager.listModel3.addElement(tmpName);
            else waitAddV.addElement(tmpName);
        } else {tm=(TreeMap)innerMembers.get(originalId); tmItems=(TreeMap) innerMemberItems.get(originalId);}
          String keys[]=w.csvLineToArray(stringx[1]);
          for(int i=0;i<keys.length;i++){
              tm.remove(keys[i]);
              tmItems.remove(keys[i]);
              String tmpName=getListItem(keys[i]);
              if(tmpName!=null){
              nameIdMap.remove(tmpName);

              listModel1.removeElement(tmpName);
              listModel2.removeElement(tmpName);
              if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
              }
          }
         if(tm.size()==0){innerMembers.remove(originalId); innerMemberItems.remove(originalId);}
         else { innerMembers.put(originalId,tm);  innerMemberItems.put(originalId,tmItems);}
        if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr("\r\n");
       }      
       String cmd2="performmessage wsn.WSN fromdown_remove_inner2 "+stringx[1];
       if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to upper: fromdown_remove_inner2: "+stringx[1]+"\r\n");
      w.sendToUpper(cmd2);
     cmd2="performmessage wsn.WSN fromupper_remove_outer "+stringx[1];
       if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_remove_outer: "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("fromupper_remove_outer") || stringx[0].equalsIgnoreCase("fromupper_remove_outer2") || stringx[0].equalsIgnoreCase("fromupper_remove_outer3")) {
      if(!originalId.equals(w.getGNS(1))){
          String keys[]=w.csvLineToArray(stringx[1]);
         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": in: "+stringx[0]+": "+stringx[1]+"\r\n");
          for(int i=0;i<keys.length;i++){
              outerMembers.remove(keys[i]);
              outerMemberItems.remove(keys[i]);
              String tmpName=getListItem(keys[i]);
              if(tmpName!=null){
              nameIdMap.remove(tmpName);

              listModel1.removeElement(tmpName);
              listModel2.removeElement(tmpName);
              if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
              }
          }
       }
     String cmd2="performmessage wsn.WSN fromupper_remove_outer2 "+stringx[1];
     if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": out to sub: fromupper_remove_outer2: "+stringx[1]+"\r\n");
     w.sendToSubLayerExceptTwo(cmd2,w.getGNS(1),originalId);
     return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus")) {
       String cmdStr="OK";
       if(!originalId.equals(w.getGNS(1))){
        stringx[2]=w.d642(stringx[2]);
        if(version.compareTo(stringx[1])>0) cmdStr="performmessage wsn.WSN wsn_status "+w.e642("Warning: Your version "+stringx[1]+" is not the latest version "+version+".");
        else if(versionDate.compareTo(stringx[2])>0) cmdStr="performmessage wsn.WSN wsn_status "+w.e642("Warning: Your version date "+stringx[2]+" is not the latest version date "+versionDate+".");
        else if(!version.equals(stringx[1]) || !versionDate.equals(stringx[2])) cmdStr="performmessage wsn.WSN wsn_status "+w.e642("Warning: Your version "+stringx[1]+" "+stringx[2]+" is not the same as mainnode's version "+version+" "+versionDate+".");
        else cmdStr="performmessage wsn.WSN wsn_status "+w.e642("Version OK.");
       } else cmdStr="performmessage wsn.WSN wsn_status "+w.e642("Good day to you.");
        w.sendToOne(cmdStr,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getdatasource")) {
      String contCmd="performmessage wsn.WSN datasource "+getDataSrc(originalId)+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("datasource")) {

      String dSrcs[]=w.csvLineToArray(stringx[1]);

      for(int i=0;i<dSrcs.length;i++) dSrcs[i]=getDataSrcId(originalId,dSrcs[i]);
      for(Iterator it=myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(originalId,dSrcs, 3);
      for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(originalId,dSrcs, 3);
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(originalId,dSrcs, 3);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus1")) {
      String contCmd="performmessage wsn.WSN status1 "+getStatus2(w.getComeCmdCode(originalId))+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus2")) {
      String contCmd="performmessage wsn.WSN status2 "+getStatus2(w.getComeCmdCode(originalId))+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus3")) {
      String contCmd="performmessage wsn.WSN status3 "+getStatus2(w.getComeCmdCode(originalId))+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getnodeconfig")) {
      String contCmd="performmessage wsn.WSN nodeconfig "+getNodeConfig()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus1_1")) {
      String contCmd="performmessage wsn.WSN status1_1 "+getStatus1_1(stringx)+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus2_1")) {
      String contCmd="performmessage wsn.WSN status2_1 "+getStatus1_1(stringx)+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus2_2")) {
      if(socketServers.get(stringx[1])!=null){
         WSNSocketServer sServer=(WSNSocketServer) socketServers.get(stringx[1]);
         if(sServer.getConnection(stringx[2])!=null){
           WSNSocketConnection conn=sServer.getConnection(stringx[2]);
           String contCmd="performmessage wsn.WSN status2_2 "+stringx[1]+" "+stringx[2]+" "+conn.getStatus()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
           w.sendToOne(contCmd,originalId);
         }
       }  
      return true;
   }
   if(stringx[0].equalsIgnoreCase("getstatus3_1")) {
     if(isNumeric(stringx[1])){
      String contCmd="performmessage wsn.WSN status3_1 "+getStatus1_1(stringx)+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
     } else return false;
   }
   if(stringx[0].equalsIgnoreCase("getstatus3_3")) {
     if(stringx[1].toUpperCase().indexOf("COM")==0){
      String contCmd="performmessage wsn.WSN status3_3 "+getStatus3_3(stringx)+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      w.sendToOne(contCmd,originalId);
      return true;
     } else return false;
   }
   if(stringx[0].equalsIgnoreCase("status1")) {
      if(jComboBox1.getItemCount()>0 && jComboBox1.getSelectedItem()!=null) lastId1_1=(String)jComboBox1.getSelectedItem();
      else lastId1_1="";
      String target="";
      currentViewStatus1=w.csvLineToArray(stringx[1]);
      jComboBox1.removeAllItems();
      int count=Integer.parseInt(currentViewStatus1[2]);
      int count2=Integer.parseInt(currentViewStatus1[4]);
      if(count>0 || count2>0){
          jComboBox1.addItem(allItemsName);
          if(count>0){
            String tmp[]=w.csvLineToArray(currentViewStatus1[3]);
            for(int i=0;i<tmp.length;i++) jComboBox1.addItem(tmp[i]);
          }
          if(count2>0){
            String tmp[]=w.csvLineToArray(currentViewStatus1[5]);
            for(int i=0;i<tmp.length;i++) jComboBox1.addItem(tmp[i]);
          }
          if(lastId1.equals(currentViewId1) && lastId1_1.length()>0){
            boolean exists = false;
            for (int index = 0; index < jComboBox1.getItemCount() && !exists; index++) {
              if (lastId1_1.equals((String)jComboBox1.getItemAt(index))) { 
                exists = true;
              }
            }
            if(exists) {
              jComboBox1.setSelectedItem(lastId1_1);
              currentViewId1_1=lastId1_1;
              if(!lastId1_1.equals(allItemsName) && lastId1_1.toUpperCase().indexOf("COM")!=0){
                String contCmd="performmessage wsn.WSN getstatus1_1 "+currentViewId1_1+" null null null null null null null null null 0 0 0 0 ? ? ? 0";
                w.sendToOne(contCmd,currentViewId1);
              }
            }
            else {currentViewId1_1=(String)jComboBox1.getItemAt(0);}
           } else {currentViewId1_1=(String)jComboBox1.getItemAt(0);}
          } else {currentViewId1_1=""; currentViewId1_2=""; jComboBox3.removeAllItems();}
      return true;
   }
   if(stringx[0].equalsIgnoreCase("status2")) {
      if(jComboBox2.getItemCount()>0 && jComboBox2.getSelectedItem()!=null) lastId2_1=(String)jComboBox2.getSelectedItem();
      else lastId2_1="";
      currentViewStatus2=w.csvLineToArray(stringx[1]);
      jComboBox2.removeAllItems();
      int count=Integer.parseInt(currentViewStatus2[2]);
      int count2=Integer.parseInt(currentViewStatus2[4]);
      if(count>0 || count2>0){
          jComboBox2.addItem(allItemsName);
          if(count>0){
            String tmp[]=w.csvLineToArray(currentViewStatus2[3]);
            for(int i=0;i<tmp.length;i++) jComboBox2.addItem(tmp[i]);
          }
          if(count2>0){
            String tmp[]=w.csvLineToArray(currentViewStatus2[5]);
            for(int i=0;i<tmp.length;i++) jComboBox2.addItem(tmp[i]);
          }
          if(lastId2.equals(currentViewId2) && lastId2_1.length()>0){
            boolean exists = false;
            for (int index = 0; index < jComboBox2.getItemCount() && !exists; index++) {
              if (lastId2_1.equals((String)jComboBox2.getItemAt(index))) { 
                exists = true;
              }
            }
            if(exists) {
              jComboBox2.setSelectedItem(lastId2_1);
              currentViewId2_1=lastId2_1;
              if(!lastId2_1.equals(allItemsName) && lastId2_1.indexOf("COM")!=0){
                String contCmd="performmessage wsn.WSN getstatus2_1 "+currentViewId2_1+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                w.sendToOne(contCmd,currentViewId2);
              }
            }
            else {currentViewId2_1=(String)jComboBox2.getItemAt(0);}
           } else {currentViewId2_1=(String)jComboBox2.getItemAt(0);}
      } else {currentViewId2_1=""; currentViewId2_2="";  jComboBox4.removeAllItems();;}
      return true;
   }
   if(stringx[0].equalsIgnoreCase("status1_1")) {
      if(jComboBox3.getItemCount()>0 && jComboBox3.getSelectedItem()!=null) lastId1_2=(String)jComboBox3.getSelectedItem();
      else lastId1_2="";
      String tmp[]=w.csvLineToArray(stringx[1]);
      jComboBox3.removeAllItems();
      int maxCount=Integer.parseInt(tmp[1]);
      int count=Integer.parseInt(tmp[2]);
      if(count>0){
          jComboBox3.addItem(allItemsName);
            String tmp2[]=w.csvLineToArray(tmp[3]);
            for(int i=0;i<tmp2.length;i++) jComboBox3.addItem(tmp2[i]);
          if(lastId1.equals(currentViewId1) && lastId1_1.equals(currentViewId1_1) && lastId1_2.length()>0){
            boolean exists = false;
            for (int index = 0; index < jComboBox3.getItemCount() && !exists; index++) {
              if (lastId1_2.equals((String)jComboBox3.getItemAt(index))) { 
                exists = true;
              }
            }
            if(exists) {
              jComboBox3.setSelectedItem(lastId1_2);
              currentViewId1_2=lastId1_2;
            } else {currentViewId1_2=(String)jComboBox3.getItemAt(0);}
           } else {currentViewId1_2=(String)jComboBox3.getItemAt(0);}
      } else currentViewId1_2="";
      return true;
   }
   if(stringx[0].equalsIgnoreCase("status2_1")) {
      String tmp[]=w.csvLineToArray(stringx[1]);
      if(jComboBox2.getSelectedItem()!=null && ((String)jComboBox2.getSelectedItem()).equals(tmp[0])){
      if(tmp[0].toUpperCase().indexOf("COM")!=-1){

          if(w.checkOneVar(tmp[1],0)) stopContinueSendBtn.setEnabled(true);
          else if(!tmp[0].equals(allItemsName)) stopContinueSendBtn.setEnabled(false);
          sendBtn.setEnabled(true);

      } else {
      if(jComboBox4.getItemCount()>0 && jComboBox4.getSelectedItem()!=null) lastId2_2=(String)jComboBox4.getSelectedItem();
      else lastId2_2="";
      jComboBox4.removeAllItems();
      int maxCount=Integer.parseInt(tmp[1]);
      int count=Integer.parseInt(tmp[2]);
      if(count>0){
          jComboBox4.addItem(allItemsName);
            String tmp2[]=w.csvLineToArray(tmp[3]);
            for(int i=0;i<tmp2.length;i++) jComboBox4.addItem(tmp2[i]);
          if(lastId2.equals(currentViewId2) && lastId2_1.equals(currentViewId2_1) && lastId2_2.length()>0){
            boolean exists = false;
            for (int index = 0; index < jComboBox4.getItemCount() && !exists; index++) {
              if (lastId2_2.equals((String)jComboBox4.getItemAt(index))) { 
                exists = true;
              }
            }
            if(exists) {
              jComboBox4.setSelectedItem(lastId2_2);
              currentViewId2_2=lastId2_2;
            } else {currentViewId2_2=(String)jComboBox4.getItemAt(0);}
           } else {currentViewId2_2=(String)jComboBox4.getItemAt(0);}
      } else currentViewId2_2="";
      }
      }
      return true;
   }
   if(stringx[0].equalsIgnoreCase("status2_2")) {
      if(jComboBox2.getSelectedItem()!=null && ((String)jComboBox2.getSelectedItem()).equals(stringx[1]) &&
         jComboBox4.getSelectedItem()!=null && ((String)jComboBox4.getSelectedItem()).equals(stringx[2])){
          if(w.checkOneVar(stringx[3],0)) stopContinueSendBtn.setEnabled(true);
          else  if(! ((String)jComboBox4.getSelectedItem()).equals(allItemsName)) stopContinueSendBtn.setEnabled(false);
          if(w.checkOneVar(stringx[3],1)) sendBtn.setEnabled(true);
          else sendBtn.setEnabled(false);
      }
      return true;
   }
   if(stringx[0].equalsIgnoreCase("status3")) {
      wsnNManager.setStatus3(originalId,stringx);
   }
   if(stringx[0].equalsIgnoreCase("status3_1")) {
      wsnNManager.setStatus3_1(originalId,stringx);
   }
   if(stringx[0].equalsIgnoreCase("status3_3")) {
      wsnNManager.setStatus3_3(originalId,stringx);
   }
   if(stringx[0].equalsIgnoreCase("nodeconfig")) {
      wsnNManager.setNodeConfig(originalId,stringx);
   }
   

   if(stringx[0].equalsIgnoreCase("wsn_data")) {

      dataThread.setData(originalId,stringx);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_status")) {

          if(lastIsData) textPaneAppend("\r\n");
          if(!w.d642(stringx[1]).equalsIgnoreCase("OK")) textPaneAppend(formatter.format(new Date())+" "+w.d642(stringx[1])+"\r\n");
          chkVersionOK=true;
          lastIsData=false;

       return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_msg")) {
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          String temp2=w.d642(stringx[1]);
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);

          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_openserial")) {
       String temp2=w.d642(stringx[3]);
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);

          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }
      String dSrc=getDataSrcId(originalId,stringx[1],stringx[2],"");
      String dSrcs[]=new String[]{dSrc};
      for(Iterator it=myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(originalId,dSrcs, 1);
      for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(originalId,dSrcs, 1);
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(originalId,dSrcs, 1);
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 1);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_closeserial")) {
      String temp2=w.d642(stringx[3]);
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);

          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      String dSrc=getDataSrcId(originalId,stringx[1],stringx[2],"");
      String dSrcs[]=new String[]{dSrc};
      for(Iterator it=myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(originalId,dSrcs, 2);
      for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(originalId,dSrcs, 2);
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(originalId,dSrcs, 2);
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 2);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_opensocketport")) {
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          String temp2=w.d642(stringx[3]);
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);
          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      String dSrcs[]=w.csvLineToArray(stringx[1]+":"+stringx[2]+"-0");
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 5);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_closesocketport")) {
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          String temp2=w.d642(stringx[3]);
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);
          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      String dSrcs[]=w.csvLineToArray(stringx[1]+":"+stringx[2]+"-0");
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 6);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_opensocketconnection")) {
      String temp2=w.d642(stringx[4]);
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);

          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      String dSrc=getDataSrcId(originalId,stringx[1],stringx[2],stringx[3]);
      String dSrcs[]=new String[]{dSrc};
      for(Iterator it=myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(originalId,dSrcs, 1);
      for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(originalId,dSrcs, 1);
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(originalId,dSrcs, 1);
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 1);
      return true;
   }
   if(stringx[0].equalsIgnoreCase("wsn_closesocketconnection")) {
      String temp2=w.d642(stringx[4]);
      if(beginToReceive && showSysMsgCB.isSelected()){
          if(lastIsData) textPaneAppend("\r\n");
          if(temp2.lastIndexOf("\r\n")!=-1 && temp2.lastIndexOf("\r\n")==(temp2.length()-2)) temp2=temp2.substring(0,temp2.length()-2);
          if(temp2.lastIndexOf("\n")!=-1 && temp2.lastIndexOf("\n")==(temp2.length()-1)) temp2=temp2.substring(0,temp2.length()-1);

          if(temp2.length()>0) textPaneAppend(formatter.format(new Date())+" "+temp2+"\r\n");
          lastIsData=false;
      }      
      String dSrc=getDataSrcId(originalId,stringx[1],stringx[2],stringx[3]);
      String dSrcs[]=new String[]{dSrc};
      for(Iterator it=myAps.values().iterator();it.hasNext();) ((WSNApplication)it.next()).setStatus(originalId,dSrcs, 2);
      for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNEventHandler)en.nextElement()).setStatus(originalId,dSrcs, 2);
      for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNLineChart)en.nextElement()).setStatus(originalId,dSrcs, 2);
      if(wsnNManager!=null) wsnNManager.setStatus(originalId, dSrcs, 2);
      return true;
   }
   

    return false;
  }

  public void sortList(JList jList,DefaultListModel listModel){
    String selected="";
    if(jList.getSelectedValue()!=null) selected=(String)jList.getSelectedValue();
    String[] strings = new String[listModel.getSize()];
    for(int i=0;i<strings.length;i++){
        strings[i]=listModel.getElementAt(i).toString();
    }
    Arrays.sort(strings);

    listModel.removeAllElements();
    for(int i=0;i<strings.length;i++) listModel.addElement(strings[i]);
    if(selected.length()>0) jList.setSelectedValue(selected, true);
  }

  public void textPaneAppend(String temp2){
       textPaneAppend(temp2,null,0);

  }

  public void textPaneAppend(String temp2,Color col,int fontSize){

     displayV.add(new DisplayData(temp2,col,fontSize));
      Runnable  runnable = new Runnable() {
        public void run(){
         if(jTextPane1.getText().length()>maxMainLogLength) {
            if(saveFileCB.isSelected()) fileThread.setData(0,"0","0",  jTextPane1.getText().trim());

             try{

               styleDoc.remove(0, styleDoc.getLength());
               begin=true;
             } catch(BadLocationException e){
                e.printStackTrace();
              }
            lastIsData=false;
         }
         DisplayData dData=(DisplayData)displayV.get(0);
            try   {   
                SimpleAttributeSet   attrSet   =   new   SimpleAttributeSet();
                if(dData.fontColor!=null) StyleConstants.setForeground(attrSet, dData.fontColor);   
                if(dData.fontSize>0) StyleConstants.setFontSize(attrSet,   dData.fontSize);  
                styleDoc.insertString(styleDoc.getLength(), dData.data,  attrSet);
                begin=false;
            }   catch   (BadLocationException   e)   {   
               System.out.println("BadLocationException:   "   +   e);   
                }   

              displayV.remove(0);
              needChk=true;
            }
        };
        SwingUtilities.invokeLater(runnable);
  }
  

  /**
   * The parameter definition of this method is the same as commandObj().
   * The difference between commandObj() and msgObj() is that msgObj() can be called without authorized code.
   */
  public boolean msgObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){return false;}
  public boolean msgObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){return false;}
  public boolean commandObj(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread sot){
          return true;
      }
  public boolean commandObj2(int mode0,int modeNow,String originalId,Weber w,Net gs,String stringx[],Obj obj,infinity.common.server.ServerObjectThread2 sot){
          return true;
      }
  

  public void closeSerialPort(String id3){
         WSNSerial serial=(WSNSerial)serialPorts.get(id3);
         serial.closePort();
         serialPorts.remove(id3);
         String cmd2="performmessage wsn.WSN wsn_closeserial "+w.getGNS(6)+" "+id3+" "+w.e642(w.getGNS(6)+":"+id3+" "+bundle2.getString("WSN.xy.msg6"));
         w.sendToAll(cmd2);
   }

    private String getStatus1(String cmdCode){
        String da[]=new String [8];
        da[0]=statuses[0];
        da[1]=""+w.ap.chkAu(11, cmdCode);
        int count=socketServers.size();
        int inx=0;
        da[2]=""+count;
        if(count>0){
          TreeMap tmp=(TreeMap)socketServers.clone();
          String tmp2[]=new String[count];
          Iterator it=tmp.keySet().iterator();
          for(;it.hasNext();){
              tmp2[inx]=(String)it.next();
              inx++;
          }
          da[3]=w.arrayToCsvLine(tmp2);
        } else da[3]="filler";
        count=serialPorts.size();
        inx=0;
        da[4]=""+count;
        if(count>0){
          TreeMap tmp=(TreeMap)serialPorts.clone();
          String tmp2[]=new String[count];
          Iterator it=tmp.keySet().iterator();
          for(;it.hasNext();){
              tmp2[inx]=(String)it.next();
              inx++;
          }
          da[5]=w.arrayToCsvLine(tmp2);
        } else da[5]="filler";
        String serials[]=WSNSerial.getPorts();
        da[6]=""+serials.length;
        if(serials.length>0){
           da[7]=w.arrayToCsvLine(serials);
        } else da[7]="filler";
        return w.arrayToCsvLine(da);
    }

    private String getStatus2(String cmdCode){
        return getStatus1(cmdCode);
    }

    private String getStatus3(String cmdCode){
        return getStatus1(cmdCode);
    }
    public TreeMap getMyAps(){
      return myAps;
    }

    private String getNodeConfig(){
        String da[]=new String[50];
        for(int i=0;i<da.length;i++) da[i]="";
       if(props.getProperty("socket_port_default")!=null){
         String conf[]=w.csvLineToArray(props.getProperty("socket_port_default"));
         if(conf.length>1 && conf[1].length()>1 && isNumeric(conf[1])) da[0]=conf[1]; else da[0]="10";
         if(conf.length>2 && conf[2].length()>0 && isNumeric(conf[2])) da[1]=conf[2]; else da[1]="60";
         if(conf.length>3 && conf[3].length()>0) da[2]=conf[3]; else da[2]="hi";
         if(conf.length>5 && conf[5].length()>0 && isNumeric(conf[5])) da[4]=conf[5]; else da[4]="1";
         if(conf.length>6 && conf[6].length()>0) da[5]=conf[6]; else da[5]="";
       } else {da[0]="10"; da[1]="60"; da[2]="hi"; da[4]="1"; da[5]="";}
       if(props.getProperty("serial_port_default")!=null){
         String conf[]=w.csvLineToArray(props.getProperty("serial_port_default"));
         if(conf.length>1 && conf[1].length()>0 && isNumeric(conf[1])) da[10]=conf[1]; else da[10]="115200";
         if(conf.length>2 && conf[2].length()>0 && isNumeric(conf[2])) da[11]=conf[2]; else da[11]="8";
         if(conf.length>3 && conf[3].length()>0) da[12]=conf[3]; else da[12]="N";
         if(conf.length>4 && conf[4].length()>0 && isNumeric(conf[4])) da[13]=conf[4]; else da[13]="1";
         if(conf.length>5 && conf[5].length()>0 && isNumeric(conf[5])) da[14]=conf[5]; else da[14]="1";
         if(conf.length>6 && conf[6].length()>0) da[15]=conf[6]; else da[15]="";
       } else {da[10]="115200"; da[11]="8"; da[12]="N"; da[13]="1";  da[14]="1"; da[15]="";}
       if(props.getProperty("socketdeviceemulator_quantity")!=null && isNumeric(props.getProperty("socketdeviceemulator_quantity"))) da[20]=props.getProperty("socketdeviceemulator_quantity"); else da[20]="0";
       if(props.getProperty("devicefile_name_prefix")!=null) da[21]=props.getProperty("devicefile_name_prefix"); else da[21]="";
       if(props.getProperty("chart_quantity")!=null && isNumeric(props.getProperty("chart_quantity"))) da[22]=props.getProperty("chart_quantity"); else da[22]="0";
       if(props.getProperty("chartfile_name_prefix")!=null) da[23]=props.getProperty("chartfile_name_prefix"); else da[23]="";
       if(props.getProperty("eventhandler_quantity")!=null && isNumeric(props.getProperty("eventhandler_quantity"))) da[24]=props.getProperty("eventhandler_quantity"); else da[24]="0";
       if(props.getProperty("eventfile_name_prefix")!=null) da[25]=props.getProperty("eventfile_name_prefix"); else da[25]="";
       if(props.getProperty("redirectorfile_name_prefix")!=null) da[26]=props.getProperty("redirectorfile_name_prefix"); else da[26]="";
       if(props.getProperty("my_ap")!=null) da[27]=props.getProperty("my_ap"); else da[27]="";
       if(props.getProperty("show_received")!=null) da[28]=props.getProperty("show_received"); else da[28]="N";
       if(props.getProperty("savelog")!=null) da[29]=props.getProperty("savelog"); else da[29]="N";
       if(props.getProperty("run_my_ap_only")!=null) da[30]=props.getProperty("run_my_ap_only"); else da[30]="";
       return w.arrayToCsvLine(da);
    }
    

    

    private String getStatus1_1(String [] stringx){
      String rtn="0";
      int joinLimit=0,cnsConnectCount=0;
      if(socketServers.containsKey(stringx[1])){
        WSNSocketServer skServer=(WSNSocketServer) socketServers.get(stringx[1]);
        WSNSocketConnection cns[]=skServer.getConnections();

        joinLimit=skServer.joinLimit;
        Vector v=new Vector();
        for(int i=0;i<cns.length;i++){
            if(cns[i]!=null) v.addElement(cns[i].getId());
        }
        if(v.size()>0){
         cnsConnectCount=v.size();
         String ids[]=new String[v.size()]; 
         Enumeration en=v.elements();
         int inx=0;
         for(;en.hasMoreElements();){
             ids[inx]=(String)en.nextElement();
             inx++;
         }
         rtn=w.arrayToCsvLine(ids);
        }
        rtn=stringx[1]+","+joinLimit+","+cnsConnectCount+","+(v.size()>0? w.toCsv(rtn):"filler")+","+skServer.pingTime+","+skServer.sendingType+","+skServer.sendingTo+",,,"+skServer.heartbeatData+"";
      } else if(serialPorts.containsKey(stringx[1])){
          WSNSerial serial=(WSNSerial) serialPorts.get(stringx[1]);
          rtn=stringx[1]+","+serial.getStatus()+","+serial.sendingType+","+serial.sendingTo;
      } else {
          rtn=stringx[1]+",1,0,filler,60,1,,,,Hi";
      }
      return rtn;
    }

    private String getStatus3_3(String [] stringx){
      String rtn="0";
      String bRate="0",dBit="8",pBit="N",stopB="1",sendingType="0",sendingTo="";
      if(serialPorts.containsKey(stringx[1])){
        WSNSerial serial=(WSNSerial) serialPorts.get(stringx[1]);
        bRate=""+serial.getBaudrate();
        dBit=""+serial.getDataBits();
        pBit=serial.getParityBit();
        stopB=""+serial.getStopBit();
        sendingTo=serial.sendingTo;
        sendingType=""+serial.sendingType;
      }
      rtn=stringx[1]+","+bRate+","+dBit+","+pBit+","+stopB+","+sendingType+","+sendingTo+",,,,";
      return rtn;
    }
    public String getHtmlOutput(){
        return "";
    }
  public String getActionName(){
      return "Prime Action";
  }
  public String getMenuTitle(){
      return "";
  }
  public String getHtmlTitle(){
    return "no title";
  }
  public String getVersion(){
      return version;
  }
  public String getVersionDate(){
      return versionDate;
  }
  public String getStyle(){
      return "";
  }
  public String getJScript(){
     return "";     
  }
  public String getHtmlResult(){
      return "";
  }
public String getHelp(){
    return "";
}

public byte[] getByteData(String data){
  return getByteData(data,-1,-1);
}

public byte[] getByteData(String data,int inx1,int inx2){
  byte b0[]={};
  int cnt=0;
            StringTokenizer st=new StringTokenizer(data, " ");
            int intx[]=new int[data.length()];
              if(data!=null && data.length()>0){
              for(cnt=0;st.hasMoreTokens();cnt++){
                String tmp=st.nextToken();
                if(isHexNumber(tmp)) intx[cnt]=Integer.decode("0x"+tmp).intValue();  
                else return b0;
              }
              if(inx1!=-1){
                if(inx1>=intx.length) return b0;
                if(inx2==-1 || inx2>intx.length) inx2=intx.length;
                int cnt2=inx2-inx1;
                b0=new byte[cnt2];
                int cnt3=0;
                 for(int i=inx1;i<inx2;i++){
                  b0[cnt3]=(byte)intx[i];
                  cnt3++;
                 }
              } else {
                 b0=new byte[cnt];
                 for(int i=0;i<cnt;i++){
                  b0[i]=(byte)intx[i];
                 }
              }
            }
     return b0;
}

public long getByteValue(byte b0[],int inx1, int inx2){
  byte b2[];
      if(inx1!=-1){
                if(inx1>=b0.length) return 0;
                if(inx2==-1 || inx2>b0.length) inx2=b0.length;
                int cnt2=inx2-inx1;
                b2=new byte[cnt2];
                int cnt3=0;
                 for(int i=inx1;i<inx2;i++){
                  b2[cnt3]=b0[i];
                  cnt3++;
                 }
              } else {
                 b2=b0;
                 }
    return ByteBuffer.wrap(b2).getLong();
}

public String getStringData(String data,int fieldNo,int subStrInx1,int subStrInx2){
        int cnt=0,cnt2=0;
        byte[] b0={};
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
        data=new String(b0);

        if(fieldNo>0){
        StringTokenizer st=new StringTokenizer(data, " ");
        while(st.hasMoreElements()){
            cnt++;
            if(fieldNo==cnt) {
                data=st.nextToken();
                break;
            } else st.nextToken();
        }
        }
        if(subStrInx1!=-1){

          if(subStrInx2!=-1 && subStrInx1 < subStrInx2) data=data.substring(subStrInx1,subStrInx2);
            else data=data.substring(subStrInx1);
        }
    return data;
}
public String getStatus(){
    return "";
}

public void onExit(int type){
  setVisible(false);
  saveNodes();
  for(Enumeration en=lineCharts.elements();en.hasMoreElements();) {
   WSNApplication ap=((WSNApplication)en.nextElement());
   ap.onExit(type);
   ap.setVisible(false);
  }
  for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) {
   WSNApplication ap=((WSNApplication)en.nextElement());
   ap.onExit(type);
   ap.setVisible(false);
  }
  for(Enumeration en=wsnSDevices.elements();en.hasMoreElements();) {
   WSNSocketDevice device=((WSNSocketDevice)en.nextElement());
   device.onExit(type);
   device.setVisible(false);
  }
  for(Iterator it=myAps.values().iterator();it.hasNext();) {
           WSNApplication ap= ((WSNApplication)it.next());
           ap.onExit(type);
           ap.setVisible(false);
         }
  if(wsnNManager!=null) wsnNManager.setVisible(false);
  if(saveFileCB.isSelected()) saveLog(jTextPane1.getText().trim());
  saveStatus();

  if(!props.getProperty("run_my_ap_only").equalsIgnoreCase("Y")) {setProperties(); saveProperties();}

  if(serialPorts.size()>0){
    for(Iterator it=serialPorts.keySet().iterator();it.hasNext();){
       String id3=(String)it.next();
         WSNSerial serial=(WSNSerial)serialPorts.get(id3);
         serial.closePort();
         String cmd2="performmessage wsn.WSN wsn_closeserial "+w.getGNS(6)+" "+id3+" "+w.e642(w.getGNS(6)+":"+id3+" "+bundle2.getString("WSN.xy.msg6"));
         w.sendToAll(cmd2);
    }
    serialPorts.clear();
  }
  if(socketServers.size()>0){
    for(Iterator it=socketServers.keySet().iterator();it.hasNext();){
      String id3=(String)it.next();
      WSNSocketServer svr=(WSNSocketServer) socketServers.get(id3);
      WSNSocketConnection con[]=svr.getConnections();
      for(int i=0;i<con.length;i++){
        if(con[i]!=null) {
         String cmd2="socket connection "+id3+":"+con[i].getId()+" ("+con[i].getIp()+") disconnected.";
         cmd2="performmessage wsn.WSN wsn_closesocketconnection "+w.getGNS(6)+" "+id3+" "+con[i].getId()+" "+w.e642(cmd2);
         w.sendToAll(cmd2);
          con[i].disconnect(); 
          con[i]=null;
        }
      }
       String cmd2=w.getGNS(6)+" socket port "+id3+" closed.";
       cmd2="performmessage wsn.WSN wsn_closesocketport "+w.getGNS(6)+" "+id3+" "+w.e642(cmd2);
      w.sendToAll(cmd2);
    }
    socketServers.clear();
  }
  }
public Properties getProperties(){
    return props;
}

  void setProperties(){
  if(saveFileCB.isSelected()) props.put("savelog","Y"); else props.put("savelog","N");
  if(showCB.isSelected()) props.put("show_received","Y"); else props.put("show_received","N");
  if(show16RB.isSelected()) props.put("show_hex","Y"); else props.put("show_hex","N");
  if(crnlCB.isSelected()) props.put("show_linebreak","Y"); else props.put("show_linebreak","N");
  if(showTimeCB.isSelected()) props.put("show_time","Y"); else props.put("show_time","N");
  if(showSrcCB.isSelected()) props.put("show_source","Y"); else props.put("show_source","N");
  if(showSysMsgCB.isSelected()) props.put("show_msg","Y"); else props.put("show_msg","N");
  if(send16RB.isSelected()) props.put("send_hex","Y"); else props.put("send_hex","N");
  if(chkSumCB.isSelected()) props.put("send_addchecksumbysystem","Y"); else props.put("send_addchecksumbysystem","N");
  props.put("send_addchecksumtype",(String)jComboBox5.getSelectedItem());
  if(continueSendCB.isSelected()) props.put("send_continuesend","Y"); else props.put("send_continuesend","N");

    Vector delV=new Vector();
    for(Iterator it=props.keySet().iterator();it.hasNext();){
      String key=(String)it.next();
      if(key.indexOf("socket_port_")==0 && key.indexOf("socket_port_default")==-1) delV.add(key);
    }
    for(Enumeration en=delV.elements();en.hasMoreElements();) props.remove((String)en.nextElement());
  if(socketServers.size()>0){
    String name="";
    boolean first=true;
    for(Iterator it=socketServers.values().iterator();it.hasNext();){
      WSNSocketServer sv=(WSNSocketServer)it.next();
      String ps[]=sv.getProps();
      props.put(ps[0],ps[1]);
      name=name+(first? "":",")+ps[0].substring(12);
      first=false;
    }
    props.put("socket_port_open",name);

  } else  props.put("socket_port_open","");

    delV=new Vector();
    for(Iterator it=props.keySet().iterator();it.hasNext();){
      String key=(String)it.next();
      if(key.indexOf("serial_port_")==0 && key.indexOf("serial_port_default")==-1) delV.add(key);
    }
    for(Enumeration en=delV.elements();en.hasMoreElements();) props.remove((String)en.nextElement());
  if(serialPorts.size()>0){
    String name="";
    boolean first=true;
    for(Iterator it=serialPorts.values().iterator();it.hasNext();){
      WSNSerial sv=(WSNSerial)it.next();
      String ps[]=sv.getProps();
      props.put(ps[0],ps[1]);
      name=name+(first? "":",")+ps[0].substring(12);
      first=false;
    }
    props.put("serial_port_open",name);

   } else  props.put("serial_port_open","");
   props.put("send_intervaltimesecond",sendIntervalTF.getText().trim());
  }
    private void saveStatus(){

	try{
		          FileOutputStream out = new FileOutputStream (statusFile);
		          String str1=w.arrayToCsvLine(statuses);
		          byte [] b=str1.getBytes();
		          out.write(b);
		          out.close();
      }catch(IOException e){e.printStackTrace();}

    }

 private void saveNodes(){
        TreeMap tm=new TreeMap();

    File f=new File(nodesFile);
    FileInputStream in;
    BufferedReader d;
    if(f.exists()) {
    try{
        in=new FileInputStream(nodesFile);
        d= new BufferedReader(new InputStreamReader(in));
        while(true){
          String str1=d.readLine();
          if(str1==null) {in.close(); d.close(); break; }
          if(str1.length()>1) {

            try{
              str1=w.de("@@",str1);
              String da[]=w.csvLineToArray(str1);
              if(da.length>3) tm.put(da[3],str1);
            } catch (ArrayIndexOutOfBoundsException e){
                 System.out.println("ArrayIndexOutOfBoundsException in wsn.saveNodes(), msg="+e.getMessage()+"\r\nstr1="+str1);
               }
          }
        }
        in.close();
        d.close();
    } catch (IOException e) {
        e.printStackTrace();
      }
    }

        TreeMap tm2=(TreeMap) outerMembers.clone();
        for(Iterator it=tm2.values().iterator();it.hasNext();){
            String da2[]=w.csvLineToArray((String)it.next());
            if(tm.containsKey(da2[3])){
                String da3[]=w.csvLineToArray((String)tm.get(da2[3]));
                da3[2]=da2[2];
                da3[4]=da2[4];
                da3[5]=da2[5];
                tm.put(da2[3],w.arrayToCsvLine(da3));
            } else {
                String da4[]=new String [10];
                for(int i=0;i<da4.length;i++){
                    da4[i]="";
                }
                da4[0]="0";
                da4[1]=da2[1];
                da4[2]=da2[2];
                da4[3]=da2[3];
                da4[4]=da2[4];
                da4[5]=da2[5];
                da4[9]=""+System.currentTimeMillis();
                tm.put(da4[3],w.arrayToCsvLine(da4));
            }
        }

        TreeMap tm3=(TreeMap) innerMembers.clone();
        for(Iterator it2=tm3.values().iterator();it2.hasNext();){
        tm2=(TreeMap)((TreeMap) it2.next()).clone();
        for(Iterator it=tm2.values().iterator();it.hasNext();){
            String da2[]=w.csvLineToArray((String)it.next());
            if(tm.containsKey(da2[3])){
                String da3[]=w.csvLineToArray((String)tm.get(da2[3]));
                da3[2]=da2[2];
                da3[4]=da2[4];
                da3[5]=da2[5];
                tm.put(da2[3],w.arrayToCsvLine(da3));
            } else {
                String da4[]=new String [10];
                for(int i=0;i<da4.length;i++){
                    da4[i]="";
                }
                da4[0]="0";
                da4[1]=da2[1];
                da4[2]=da2[2];
                da4[3]=da2[3];
                da4[4]=da2[4];
                da4[5]=da2[5];
                da4[9]=""+System.currentTimeMillis();
                tm.put(da4[3],w.arrayToCsvLine(da4));
            }
        }
        }

        StringBuffer sb=new StringBuffer();
        boolean first=true;
        for(Iterator it=tm.values().iterator();it.hasNext();){
            sb.append((first? "":"\r\n")+w.en("@@",(String)it.next()));
        }
        try{
		          FileOutputStream out = new FileOutputStream (nodesFile);
		          byte [] b=sb.toString().getBytes();
		          out.write(b);
		          out.close();
      }catch(IOException e){e.printStackTrace();}
    }

private void saveProperties(){
    try{
        FileOutputStream out = new FileOutputStream(propsFile );
        props.store(out,"");
      }catch(IOException e){e.printStackTrace();}
}
public void loadAp(){
    statuses[0]=w.addOneVar(statuses[0], 0);
     if(props.getProperty("my_ap")!=null && props.getProperty("my_ap").length()>0){
           String aps[]=w.csvLineToArray(props.getProperty("my_ap"));
           for(int i=0;i<aps.length;i++){
               loadClass(aps[i],3);
           }
       }
    statuses[0]=w.removeOneVar(statuses[0], 0);
}

  public boolean loadClass(String className,int type)  {
    String classDir="apps/cr-wsn/lib/classes";
    if(className.length()>6 && className.indexOf(".class")==className.length()-6) className=className.substring(0,className.indexOf(".class"));
    File f3=new File(w.replace(classDir,"/",File.separator));
    boolean found=false;
    try{
     w.serverURLs = new URL[]{new URL("file:"+classDir)};
     w.cl = new URLClassLoader(w.serverURLs);
     File classFile=new File(classDir+File.separator+className+".class");
     long lastFileTime=0;
     if(aTime.get(className)!=null){
        lastFileTime=Long.parseLong((String)aTime.get(className));
      }
    if(f3.exists()){
     if(classFile.exists()){
      long fileTime=classFile.lastModified();
      if(aTime.get(className)==null || fileTime > lastFileTime){
         if(type == 3) myAps.put(className, w.cl.loadClass(className).newInstance());
         else jClasses.put(className,w.cl.loadClass(className).newInstance());
         aTime.put(className,String.valueOf(fileTime));
         try{
           if((type==1 && !((WSNDataTranslator)jClasses.get(className) instanceof WSNDataTranslator)) || 
                   (type==2 && !((WSNAction)jClasses.get(className) instanceof WSNAction)) || 
                   (type == 3 && !((WSNApplication)myAps.get(className) instanceof WSNApplication)) || 
                   (type == 4 && !((WSNDataGenerator) jClasses.get(className) instanceof WSNDataGenerator)) || 
                   (type == 5 && !((WSNSocketDataHandler) jClasses.get(className) instanceof WSNSocketDataHandler))){
             if(type == 3) myAps.remove(className);
              else jClasses.remove(className);
             System.out.println("Error1: class file "+classFile.getAbsolutePath()+" not a "+(type==1? "WSNDataTranslator":(type==2? "WSNAction":(type==3? "WSNApplication":(type==4? "WSNDataGenerator":"UnknownType"))))+" class."); 
           } else {
               found=true;
              if(type == 3) {
                 WSNApplication tmp = (WSNApplication)myAps.get(className);
                 tmp.init(this);
                 myAps.put(className, tmp);
              }
           }
         } catch(ClassCastException e){
             if(type == 3) myAps.remove(className);
             else jClasses.remove(className);
             System.out.println("Error2: class file "+classFile.getAbsolutePath()+" can not be cast to a "+(type==1? "WSNDataTranslator":(type==2? "WSNAction":(type==3? "WSNApplication":(type==4? "WSNDataGenerator":"UnknownType"))))+" class."); 
         }
       } else found=true;
     }
     else {
      } 
    }  
    if(!found){
      if(type==3 && myAps.get(className)==null){
             myAps.put(className,w.cl.loadClass(className).newInstance());
           try{
             aTime.put(className,String.valueOf(System.currentTimeMillis()));
              if(!((WSNApplication)myAps.get(className) instanceof WSNApplication)){
                myAps.remove(className);
                System.out.println("Error3: class file "+className+" in jar file not a WSNApplication class."); return false;
             } else {
                 WSNApplication tmp = (WSNApplication)myAps.get(className);
                 tmp.init(this);
                 myAps.put(className, tmp);
              }
            }catch(ClassCastException e){
               myAps.remove(className);

               System.out.println("Error4: class file "+className+" in jar file can not be cast to a WSNApplication class."); return false;
            }
         } else if(type!=3 && jClasses.get(className)==null){
             jClasses.put(className,w.cl.loadClass(className).newInstance());
           try{
             aTime.put(className,String.valueOf(System.currentTimeMillis()));
              if((type==1 && !((WSNDataTranslator)jClasses.get(className) instanceof WSNDataTranslator)) || (type==2 && !((WSNAction)jClasses.get(className) instanceof WSNAction)) || (type==4 && !((WSNDataGenerator)jClasses.get(className) instanceof WSNDataGenerator)) || (type==5 && !((WSNSocketDataHandler)jClasses.get(className) instanceof WSNSocketDataHandler))){
                jClasses.remove(className);
                System.out.println("Error5: class file "+className+" in jar file not a "+(type==1? "WSNDataTranslator":(type==2? "WSNAction":(type==3? "WSNApplication":(type==4? "WSNDataGenerator":(type==5? "WSNSocketDataHandler":"UnknownType")))))+" class."); return false;
             }
            }catch(ClassCastException e){
               jClasses.remove(className);
               System.out.println("Error6: class file "+className+" in jar file can not be cast to a "+(type==1? "WSNDataTranslator":(type==2? "WSNAction":(type==3? "WSNApplication":(type==4? "WSNDataGenerator":(type==5? "WSNSocketDataHandler":"UnknownType")))))+" class."); return false;
            }
         }
    }
      }  catch (ClassNotFoundException e) {
    	    if(type==3 && myAps.get(className)!=null) myAps.remove(className);
            else if(jClasses.get(className)!=null) jClasses.remove(className);
            if(w.w_monitor) System.out.println("Error8: "+className+".class not found in class directory or jar files.");

            return false;
      }  catch (Exception e) {
    	    if(type==3 && myAps.get(className)!=null) myAps.remove(className);
            else if(jClasses.get(className)!=null) jClasses.remove(className);
            e.printStackTrace();
            return false;
        }
    return true;
  }

  

public void gridChanged(Weber w,Net gs,int from){
  this.w=w; this.gs=gs;
  if(w.wnc.status1.equals("login"))  gIsFinal=true;

      String items[]=new String[0];
      int maxN=200,inx=0,inx2=0;
      boolean first=true;
      if(w.getGNS(1).equals(w.getGNS(44))){
          if(outerMembers.size()>0){
            TreeMap tmOut=(TreeMap) outerMembers.clone();
            inx=0; inx2=0; first=true;
            Iterator it=tmOut.keySet().iterator();
            for(;it.hasNext();){
                if(first){
                    if((tmOut.size()-inx)>maxN) items=new String[maxN];
                    else items=new String[tmOut.size()-inx];
                    first=false;
                    inx2=0;
                }
                String key=(String)it.next();
                items[inx2]=key;
                String tmp[]=w.csvLineToArray((String)tmOut.get(key));
                String tmpName=getListItem(tmp[0]);
                nameIdMap.remove(tmpName);
                listModel1.removeElement(tmpName);
                listModel2.removeElement(tmpName);
                if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
                outerMembers.remove(key);
                inx++;
                inx2++;
                if(inx==tmOut.size() || inx2==maxN){
                   String cmd="performmessage wsn.WSN fromtop_remove_outer "+w.arrayToCsvLine(items);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to all: fromtop_remove_outer: "+w.arrayToCsvLine(items)+"\r\n");
                   w.sendToAllExceptMyself(cmd);
                   inx2=0;
                   first=true;
                }
            }
            tmOut=null;
          }
      }

      Vector willBeRemoved=new Vector(),willBeAdded=new Vector();
      TreeMap tmIn=(TreeMap)innerMembers.clone();
      if(gs!=null){
        Connection [] cns=gs.getConnections(pid);
        if(cns!=null){

          for(int i=0;i<cns.length;i++){
            if(cns[i]!=null && !cns[i].getId().equals("null") && !cns[i].getId().equals(w.getGNS(1))){

              if(!tmIn.containsKey(cns[i].getId())){
                  String tmpName=makeListItem(cns[i].getCGNS(showIndex),cns[i].getId());
                  nameIdMap.put(tmpName, cns[i].getId());
                  if(!listModel1.contains(makeListItem(cns[i].getCGNS(showIndex),cns[i].getId()))) listModel1.addElement(tmpName);
                  if(!listModel2.contains(makeListItem(cns[i].getCGNS(showIndex),cns[i].getId()))) listModel2.addElement(tmpName);
                  if(wsnNManager!=null && !wsnNManager.listModel3.contains(tmpName)) wsnNManager.listModel3.addElement(tmpName);
                  else waitAddV.addElement(tmpName);

                  TreeMap tm=(TreeMap) (tmIn.get(cns[i].getId())==null? new TreeMap():tmIn.get(cns[i].getId()));
                  TreeMap tmItems=(TreeMap) (innerMemberItems.get(cns[i].getId())==null? new TreeMap():innerMemberItems.get(cns[i].getId()));
                  tm.put(cns[i].getId(), w.arrayToCsvLine(cns[i].getCGNS()));
                  tmItems.put(cns[i].getId(), cns[i].getId()+",0,,,"+cns[i].getCGNS(showIndex)+",,,,,,,,,,,,,,,,");
                  innerMembers.put(cns[i].getId(),tm);
                  innerMemberItems.put(cns[i].getId(),tmItems);
                  willBeAdded.add(w.arrayToCsvLine(cns[i].getCGNS()));
              } else {
                  tmIn.remove(cns[i].getId());
              }
          }
        }
        }
      }

      if(willBeAdded.size()>0){
           if(w.getGNS(1).equals(w.getGNS(44))){
              for(int i=0;i<willBeAdded.size();i++){
                   String cmd="performmessage wsn.WSN fromtop_add_outer "+w.en("@@",(String)willBeAdded.get(i));
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromtop_add_outer: "+(String)willBeAdded.get(i)+"\r\n");
                   w.sendToSubLayerExceptTwo(cmd,w.getGNS(1),w.csvLineToArray((String)willBeAdded.get(i))[0]);
              }
           } else {
               items=new String[willBeAdded.size()];
               for(int i=0;i<willBeAdded.size();i++) items[i]=w.en("@@",(String)willBeAdded.get(i));
                   String cmd="performmessage wsn.WSN fromdown_add_inner "+w.arrayToCsvLine(items);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_add_inner: "+w.arrayToCsvLine(items)+"\r\n");
                   w.sendToUpper(cmd);
           }

              TreeMap tmOut=(TreeMap) outerMembers.clone();
              for(int i=0;i<willBeAdded.size();i++){
                  String cmd="performmessage wsn.WSN fromupper_add_outer4 "+w.en("@@",w.arrayToCsvLine(w.getCGNS()));
                  if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer4: "+w.arrayToCsvLine(w.getCGNS())+"\r\n");
                  w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                  inx=0; inx2=0; first=true;
                  if(tmOut!=null && tmOut.size()>0){
                  Iterator it=tmOut.values().iterator();
                  for(;it.hasNext();){
                    if(first){
                      if((tmOut.size()-inx)>maxN) items=new String[maxN];
                      else items=new String[tmOut.size()-inx];
                      first=false;
                      inx2=0;
                  }
                  items[inx2]=w.en("@@",(String)it.next());
                  inx++;
                  inx2++;
                  if(inx==tmOut.size() || inx2==maxN){
                    cmd="performmessage wsn.WSN fromupper_add_outer3 "+w.arrayToCsvLine(items);
                    if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromupper_add_outer3: "+w.arrayToCsvLine(items)+"\r\n");

                    w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                    inx2=0;
                    first=true;
                  }
                }
             }
            tmOut=null;
          }

            TreeMap tmIn2=(TreeMap)innerMembers.clone();
            Vector willBeAdded2=new Vector();
            for(int i=0;i<willBeAdded.size();i++){
                Iterator it0=tmIn2.keySet().iterator();
                inx2=0; first=true;
                for(;it0.hasNext();){
                  String key=(String)it0.next();
                  if(!tmIn.containsKey(key) && !key.equals(w.csvLineToArray((String)willBeAdded.get(i))[0])){
                    TreeMap tm2=(TreeMap)tmIn2.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp2=(String)tm2.get((String)it2.next());
                       willBeAdded2.add(tmp2);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded2.get(k));
                         String cmd="performmessage wsn.WSN fromupper_add_outer5 "+w.arrayToCsvLine(items);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer5: "+w.arrayToCsvLine(items)+"\r\n");
                         w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                         inx2=0;
                         first=true;
                         willBeAdded2.clear();
                       }
                    }
                  }
                }
                if(willBeAdded2.size()>0){
                         items=new String[willBeAdded2.size()];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded2.get(k));
                         String cmd="performmessage wsn.WSN fromupper_add_outer6 "+w.arrayToCsvLine(items);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+w.csvLineToArray((String)willBeAdded.get(i))[0]+": fromupper_add_outer6: "+w.arrayToCsvLine(items)+"\r\n");
                         w.sendToOneExceptMyself(cmd,w.csvLineToArray((String)willBeAdded.get(i))[0]);
                         willBeAdded2.clear();
                }
            }
            tmIn2.clear();

            TreeMap tmIn3=(TreeMap)innerMembers.clone();
            Vector willBeAdded3=new Vector(),nameV=new Vector();
            for(int i=0;i<willBeAdded.size();i++){
                nameV.add(w.csvLineToArray((String)willBeAdded.get(i))[0]);
            }
            for(Iterator it5=tmIn3.keySet().iterator();it5.hasNext();){
              String id5=(String)it5.next();
              if(!nameV.contains(id5)){
                Iterator it0=tmIn3.keySet().iterator();
                inx2=0; first=true;
                for(;it0.hasNext();){
                  String key=(String)it0.next();
                  if(!tmIn.containsKey(key) && !key.equals(id5)){
                    TreeMap tm2=(TreeMap)tmIn3.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp2=(String)tm2.get((String)it2.next());
                       willBeAdded3.add(tmp2);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded3.get(k));
                         String cmd="performmessage wsn.WSN fromupper_add_outer7 "+w.arrayToCsvLine(items);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+id5+": fromupper_add_outer7: "+w.arrayToCsvLine(items)+"\r\n");
                         w.sendToOneExceptMyself(cmd,id5);
                         inx2=0;
                         first=true;
                         willBeAdded3.clear();
                       }
                    }
                  }
                }
                if(willBeAdded3.size()>0){
                         items=new String[willBeAdded3.size()];
                         for(int k=0;k<inx2;k++) items[k]=w.en("@@",(String)willBeAdded3.get(k));
                         String cmd="performmessage wsn.WSN fromupper_add_outer8 "+w.arrayToCsvLine(items);
                         if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to "+id5+": fromupper_add_outer8: "+w.arrayToCsvLine(items)+"\r\n");
                         w.sendToOneExceptMyself(cmd,id5);
                         willBeAdded3.clear();
                }
              }
            }
            tmIn3.clear();

       willBeAdded.clear();
      }

              if(tmIn.size()>0){
                  Iterator it0=tmIn.keySet().iterator();
                  inx2=0; first=true;
                  for(;it0.hasNext();){
                    String key=(String)it0.next();
                    TreeMap tm2=(TreeMap)tmIn.get(key);
                    Iterator it2=tm2.keySet().iterator();
                    for(;it2.hasNext();){
                       if(first){
                           first=false;
                           inx2=0;
                       }
                       String tmp[]=w.csvLineToArray((String)tm2.get((String)it2.next()));
                       String tmpName=getListItem(tmp,showIndex);
                       nameIdMap.remove(tmpName);
                       listModel1.removeElement(tmpName);
                       listModel2.removeElement(tmpName);
                       if(wsnNManager!=null) wsnNManager.listModel3.removeElement(tmpName);
                       willBeRemoved.add(tmp[0]);
                       inx2++;
                       if((!it0.hasNext() && !it2.hasNext()) || inx2==maxN){
                         items=new String[inx2];
                         for(int i=0;i<inx2;i++) items[i]=(String)willBeRemoved.get(i);
                         if(willBeRemoved.size()>0 && w.getGNS(1).equals(w.getGNS(44))){
                           String cmd="performmessage wsn.WSN fromtop_remove_outer2 "+w.arrayToCsvLine(items);
                          if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromtop_remove_outer2: "+w.arrayToCsvLine(items)+"\r\n");
                           w.sendToSubLayerExceptOne(cmd,w.getGNS(1));
                         } else {
                            String cmd="performmessage wsn.WSN fromdown_remove_inner "+w.arrayToCsvLine(items);
                            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_remove_inner: "+w.arrayToCsvLine(items)+"\r\n");
                            w.sendToUpper(cmd);
                            cmd="performmessage wsn.WSN fromupper_remove_outer3 "+w.arrayToCsvLine(items);
                            if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to sub: fromupper_remove_outer3: "+w.arrayToCsvLine(items)+"\r\n");
                            w.sendToSubLayerExceptOne(cmd,w.getGNS(1));
                         }
                         inx2=0;
                         first=true;
                         willBeRemoved.clear();
                       }
                    }
                    innerMembers.remove(key);
                    innerMemberItems.remove(key);
                  }
              }
              tmIn=null;

      if(w.getGNS(38)!=null && w.getGNS(38).length()>0 && !w.getGNS(38).equals(w.getGNS(1)) && !w.getGNS(38).equals(lastUpper)){
        if(innerMembers.size()>0){
          willBeAdded.clear();
          items=new String[0];
          inx2=0; first=true;
          TreeMap tm3=(TreeMap)innerMembers.clone();
          Iterator it3=tm3.keySet().iterator();
          for(;it3.hasNext();){
              TreeMap tm4=(TreeMap) tm3.get((String)it3.next());
              Iterator it4=tm4.values().iterator();
              for(;it4.hasNext();){
                if(first){
                    willBeAdded.clear();
                    first=false;
                    inx2=0;
                }
                  String data=(String)it4.next();
                  inx2++;
                  willBeAdded.add(data);
                  if((!it3.hasNext() && !it4.hasNext()) || inx2==maxN){
                   items=new String[willBeAdded.size()];
                   for(int i=0;i<items.length;i++) items[i]=w.en("@@",(String)willBeAdded.get(i));
                   String cmd="performmessage wsn.WSN fromdown_add_inner2 "+w.arrayToCsvLine(items);
                   if(w.getHVar("a_monitor")!=null && w.getHVar("a_monitor").equalsIgnoreCase("true")) w.monitor.writeLogStr(w.format4.format(new Date())+": gchanged: to upper: fromdown_add_inner2: "+w.arrayToCsvLine(items)+"\r\n");
                   w.sendToUpper(cmd);
                   inx2=0;
                   first=true;
                   willBeAdded.clear();
                }
              }
          }
        }
      }

      lastUpper=w.getGNS(38);
      updateTitle();
}

public String[] getIdFromDataSrc(String dataSrcId){
    String ip=w.getGNS(6);
    String rtn[]={};
    if(dataSrcId.indexOf("device@")!=-1) {return new String[]{w.getGNS(1)};}
    else if(dataSrcId.indexOf(":")!=-1) ip=dataSrcId.substring(0,dataSrcId.indexOf(":")) ;
    else if(dataSrcId.equals("0")) ip="0";
    else {
        return new String[]{w.getGNS(1)};
    }
    return getIdFromIP(ip);
}

public String[] getIdFromIP(String ip){
    String rtn[]={};
    Vector v=new Vector();
    if(ip.equals("0")){
       int count=listModel1.size();
                    for(int i=1;i<count;i++){
                        v.add(getItemId((String)listModel1.getElementAt(i)));
                    }
    }
    else {
      for(int i=0;i<listModel1.size();i++){
        String item=(String)listModel1.elementAt(i);
        if(item.indexOf(ip)!=-1) v.add(getItemId(item));
      }
     }
    if(v.size()>0){
      rtn=new String[v.size()];
      for(int i=0;i<v.size();i++) rtn[i]=(String)v.get(i);

    }
    return rtn;
}

public String[] getIdFromFixId(String fixId){
    String rtn[]={};
    return rtn;
}
public boolean chkProps(String key){
   return chkProps(props,key);
 }
  public boolean chkProps(Properties props,String key){
   return props.getProperty(key)!=null && props.getProperty(key).length()>0 && (props.getProperty(key).equalsIgnoreCase("y") || props.getProperty(key).equalsIgnoreCase("yes") || props.getProperty(key).equalsIgnoreCase("true"));  
 }
 int getPropsInt(String key){
   return getPropsInt(props,key);
 }
 public int getPropsInt(Properties props,String key){
   return (props.getProperty(key)!=null && isNumeric(props.getProperty(key))? ((int)Double.parseDouble(props.getProperty(key).trim())):0);
 }
 long getPropsLong(String key){
   return getPropsLong(props,key);
 }
 public  long getPropsLong(Properties props,String key){
   return (props.getProperty(key)!=null && isNumeric(props.getProperty(key))? ((long)Double.parseDouble(props.getProperty(key).trim())):0);
 }
 double getPropsDouble(String key){
   return getPropsDouble(props,key);
 }
 public double getPropsDouble(Properties props,String key){
   return (props.getProperty(key)!=null && isNumeric(props.getProperty(key))? Double.parseDouble(props.getProperty(key).trim()):0.0);
 }
 public String getPropsString(String key){
   return getPropsString(props,key);
 }
 public String getPropsString(Properties props,String key){
   return (props.getProperty(key)!=null? props.getProperty(key).trim():"");
 }

    private String getDataSrc(String originalId){

        Vector v=new Vector();
        int count=socketServers.size();
        if(count>0){
          TreeMap tmp=(TreeMap)socketServers.clone();
          Iterator it=tmp.keySet().iterator();
          for(;it.hasNext();){
              String port=(String) it.next();
              WSNSocketServer sServer=(WSNSocketServer)tmp.get(port);
              int count2=sServer.connectionCount();
              if(count2>0){
                  WSNSocketConnection cns[]=sServer.getConnections();

                  for(int i=0;i<cns.length;i++) if(cns[i]!=null) v.addElement((originalId.equals(w.getGNS(1))? port+"-"+cns[i].getId():w.getGNS(6)+":"+port+"-"+cns[i].getId() ));
              }
          }
        }
        count=serialPorts.size();
        if(count>0){
          TreeMap tmp=(TreeMap)serialPorts.clone();
          Iterator it=tmp.keySet().iterator();
          for(;it.hasNext();){
              String serialPort=(String)it.next();
              v.addElement((originalId.equals(w.getGNS(1))? serialPort:w.getGNS(6)+":"+serialPort ));
          }
        }
        count=wsnSDevices.size();

        if(count>0){
          Enumeration en=wsnSDevices.elements();
         for(;en.hasMoreElements();){
           Object o=en.nextElement();
           if(o!=null){
            WSNSocketDevice device=((WSNSocketDevice)o);

            if(device.connected){

              v.addElement((originalId.equals(w.getGNS(1))? "device@"+device.getHost()+":"+device.getPort():w.getGNS(6)+":"+"device@"+device.getHost()+":"+device.getPort() ));
            }
          }
          }
        }
        String dataSrc[]={"0"};
        if(v.size()>0) {
            dataSrc=new String[v.size()];
            for(int i=0;i<v.size();i++) dataSrc[i]=(String)v.get(i);

        } 

        return w.arrayToCsvLine(dataSrc);
    }

public String getDataSrcId(String originald,String id1,String id2,String id3){
    if(id2.indexOf("device@")==-1) id2=id2.toUpperCase();
    id2=id2.trim();
    return (originald.equals(w.getGNS(1))? "":id1+":")+id2+((id2.indexOf("COM")==0 || id2.indexOf("device@")==0)? "":"-"+id3);
}

public String getDataSrcId(String originald,String sDrc){

  int inx=sDrc.indexOf("device@");
  return (originald.equals(w.getGNS(1))? (inx>-1? sDrc.substring(inx):sDrc.substring(sDrc.indexOf(":")+1)):sDrc);
}

public String getPort(String dataSrc){
  if(dataSrc.indexOf("-")>-1) dataSrc=dataSrc.substring(0,dataSrc.indexOf("-"));
  return dataSrc;
}

public String getId1(String dataSrcId){
    if(dataSrcId.equals("0")) return "0";
    return (dataSrcId.indexOf(":")>0? dataSrcId.substring(0,dataSrcId.indexOf(":")):w.getGNS(6));
}

public String getId2(String dataSrcId){
    if(dataSrcId.indexOf("device@")!=-1) return dataSrcId;
    dataSrcId=dataSrcId.toUpperCase().trim();
    if(dataSrcId.indexOf(":")>0) dataSrcId=dataSrcId.substring(dataSrcId.indexOf(":")+1);
    if(dataSrcId.indexOf("-")>0) dataSrcId=dataSrcId.substring(0,dataSrcId.indexOf("-"));
    return dataSrcId;
}

public String getId3(String dataSrcId){
    if(dataSrcId.indexOf("-")>0) dataSrcId=dataSrcId.substring(dataSrcId.indexOf("-")+1);
    else dataSrcId="";
    return dataSrcId;
}

public String[] getItemData(String id,int listN){
    String rtn[]={};
    TreeMap tm=(TreeMap)outerMemberItems.clone();
    if(tm.get(id)!=null){
        rtn=w.csvLineToArray((String)tm.get(id));
        switch(listN){
            case 1:
             currentInnerMemberId1="";
             break;
            case 2:
             currentInnerMemberId2="";
             break;
            case 3:
             wsnNManager.currentInnerMemberId3="";
             break;
        }
    } else if(elseMemberItems.get(id)!=null){
        rtn=w.csvLineToArray((String)elseMemberItems.get(id));
        switch(listN){
            case 1:
             currentInnerMemberId1="";
             break;
            case 2:
             currentInnerMemberId2="";
             break;
            case 3:
             wsnNManager.currentInnerMemberId3="";
             break;
        }
    } else  {
        tm=(TreeMap)innerMemberItems.clone();
        for(Iterator it=tm.keySet().iterator();it.hasNext();){
            String innerId=(String)it.next();
            TreeMap tm2=(TreeMap)tm.get(innerId);
            for(Iterator it2=tm2.keySet().iterator();it2.hasNext();){
                String key=(String)it2.next();
                if(id.equals(key)){
                    rtn=w.csvLineToArray((String)tm2.get(id));
                    switch(listN){
                     case 1:
                      currentInnerMemberId1=innerId;
                      break;
                     case 2:
                      currentInnerMemberId2=innerId;
                      break;
                     case 3:
                      wsnNManager.currentInnerMemberId3=innerId;
                      break;
                    }
                    break;
                }
            }
        }
    }
    return rtn;
}

public String getItemId(String s){
 String rtn="";
 switch(showType){
   case 1:
     if(s!=null && s.length()>0){
        int inx=s.lastIndexOf("("), inx2=s.lastIndexOf(")");
       rtn=s.substring(inx+1,inx2);
     }
     break;
   case 2:
    rtn=(String)nameIdMap.get(s);
    break;
 }
 return rtn;
}

public String getItemIp(String s){
 String rtn=getItemName(s);
 if(rtn.equals(allNodesName)) rtn="";
 else if(rtn.equals(myNodeName)) rtn=w.getGNS(6);
 else if(rtn.indexOf("-")>-1) rtn=rtn.substring(0, rtn.indexOf("-"));
 return rtn;
}

public String getItemName(String s){
  String rtn=s;
  switch(showType){
    case 1:
      int inx=s.lastIndexOf("(");
        if(inx==-1){
          s=getListItem(s);
          inx=s.lastIndexOf("(");
          rtn=s.substring(0,inx).trim();
        } else rtn=s.substring(0,inx).trim();
      break;
    case 2:
      if(nameIdMap.containsKey(s)) rtn=s;
      else {
        for(Iterator it=nameIdMap.keySet().iterator();it.hasNext();){
          String key=(String)it.next();
          if(((String)nameIdMap.get(key)).equals(s)) {rtn=key; break;}
        }
      }
      break;
  }
  return rtn;
}

public String getListItem(String s[],int inx){

    return makeListItem(s[inx],s[0]);
}

public String makeListItem(String name,String id){
  String rtn=name+" ("+id+")";
  if(showType==2){
    rtn=name;
    int count=0;
    Vector sNo=new Vector();
    for(Iterator it=nameIdMap.keySet().iterator();it.hasNext();){
      String key=(String)it.next();
      String id2=(String)nameIdMap.get(key);
      if(id.equals(id2)) {rtn=key; count=0; break;}
      if(key.equals(name)) count++;
      else {
        int inx=key.indexOf("-");
        if(inx>-1 && key.substring(0,inx).equals(name)) {
          count++;
          sNo.add(key.substring(inx+1));
        }
      }
    }
    if(sNo.size()>0){
      if(sNo.size()==count) rtn=rtn;
      else {
        for(int i=0;i<sNo.size();i++){
          i++;
          if(!sNo.contains(String.valueOf(i))) {rtn=rtn+"-"+i; break;}
        }
      }
    } else if(count>0) rtn=rtn+"-"+(count+1);
  }

    return rtn;
}
public String getListItem(String id){
    for(int i=0;i<listModel1.size();i++){
       if(getItemId((String)listModel1.get(i)).equals(id)) return (String)listModel1.get(i);
    }
    return null;
}

public void setBlink(boolean onoff){
  if(needChk){jTextPane1.setCaretPosition(styleDoc.getLength()); needChk=false;}
}

public void setItemData(String currentInnerMemberId,String currentViewId,String [] currentItemData){
  boolean found=false;
  if(currentInnerMemberId.length()>0){
      Object o=innerMemberItems.get(currentInnerMemberId);
      if(o!=null){
        TreeMap tm=(TreeMap)o;
        if(tm.containsKey(currentViewId)){
          tm.put(currentViewId,w.arrayToCsvLine(currentItemData));
          innerMemberItems.put(currentViewId,tm);
        }
      }
    } else {
        Object o=outerMemberItems.get(currentViewId);
        if(o!=null){
          outerMemberItems.put(currentViewId,w.arrayToCsvLine(currentItemData));
        }
  }
}

private void setNodeDefaultConfig(String config){
  String conf[]=w.csvLineToArray(config);
  props.put("socket_port_default",","+conf[0]+","+conf[1]+","+conf[2]+","+conf[3]+","+conf[4]+","+conf[5]+","+conf[6]+","+conf[7]+","+conf[8]);
  props.put("serial_port_default",","+conf[10]+","+conf[11]+","+conf[12]+","+conf[13]+","+conf[14]+","+conf[15]+","+conf[16]+","+conf[17]+","+conf[18]);
}

private void setNodeOtherConfig(String originalId,String config){
  String conf[]=w.csvLineToArray(config);
  if(!props.getProperty("socketdeviceemulator_quantity").equalsIgnoreCase(conf[20]) || !props.getProperty("devicefile_name_prefix").equalsIgnoreCase(conf[21])){
    if(wsnSDevices.size()>0){
      Enumeration en=wsnSDevices.elements();
      for(;en.hasMoreElements();){
        Object o=en.nextElement();
        if(o!=null){
          ((WSNSocketDevice)o).setVisible(false);
        }
      }
      wsnSDevices.clear();
    }
    props.put("socketdeviceemulator_quantity",conf[20]);
    props.put("devicefile_name_prefix",conf[21]);
     for(int i=0;i<Integer.parseInt(props.getProperty("socketdeviceemulator_quantity"));i++){ 
       wsnSDevices.add(new WSNSocketDevice(this,pid));
     }
  }
  if(!props.getProperty("chart_quantity").equalsIgnoreCase(conf[22]) || !props.getProperty("chartfile_name_prefix").equalsIgnoreCase(conf[23])){
    if(lineCharts.size()>0){
      Enumeration en=lineCharts.elements();
      for(;en.hasMoreElements();){
        Object o=en.nextElement();
        if(o!=null){
          ((WSNApplication)o).setVisible(false);
        }
      }
      lineCharts.clear();
    }
    props.put("chart_quantity",conf[22]);
    props.put("chartfile_name_prefix",conf[23]);
            for(int i=0;i<Integer.parseInt(props.getProperty("chart_quantity"));i++){
          WSNApplication a=new WSNLineChart();
          a.init(this);
          lineCharts.add(a);
        }
  }
  if(!props.getProperty("eventhandler_quantity").equalsIgnoreCase(conf[24]) || !props.getProperty("eventfile_name_prefix").equalsIgnoreCase(conf[25])){
    if(eventHandlers.size()>0){
      Enumeration en=eventHandlers.elements();
      for(;en.hasMoreElements();){
        Object o=en.nextElement();
        if(o!=null){
          ((WSNApplication)o).setVisible(false);
        }
      }
      eventHandlers.clear();
    }
    props.put("eventhandler_quantity",conf[24]);
    props.put("eventfile_name_prefix",conf[25]);
        for(int i=0;i<Integer.parseInt(props.getProperty("eventhandler_quantity"));i++){
          WSNApplication a=new WSNEventHandler();
          a.init(this);
          eventHandlers.add(a);
        }
  }
  if(!props.getProperty("redirectorfile_name_prefix").equalsIgnoreCase(conf[26])){
    props.put("redirectorfile_name_prefix",conf[26]);
  }
  if(!props.getProperty("show_received").equalsIgnoreCase(conf[28])){
    props.put("show_received",conf[28]);
    if(props.getProperty("show_received").equalsIgnoreCase("Y")) showCB.setSelected(true); else showCB.setSelected(false);
  }
  if(!props.getProperty("savelog").equalsIgnoreCase(conf[29])){
    props.put("savelog",conf[29]);
    if(props.getProperty("savelog").equalsIgnoreCase("Y")) saveFileCB.setSelected(true); else saveFileCB.setSelected(false);
  }

  if(!props.getProperty("my_ap").equalsIgnoreCase(conf[27])){
    if(myAps.size()>0){
      for(Iterator it = myAps.values().iterator(); it.hasNext();) {
               WSNApplication wa= (WSNApplication)it.next();
               wa.onExit(110);
               wa.setVisible(false);
      }
      myAps.clear();
    }
      props.put("my_ap",conf[27]);
      if(conf[27].length()>0){
         loadAp();

         for(Iterator it = myAps.values().iterator(); it.hasNext();) {
               WSNApplication wa= (WSNApplication)it.next();
               if(!wa.isVisible()) wa.setVisible(true);
         }
      }
  }

  if(!props.getProperty("run_my_ap_only").equalsIgnoreCase(conf[30])){
    props.put("run_my_ap_only",conf[30]);
    if(conf[30].equalsIgnoreCase("Y") && myAps.size()>0){
        myAp();

       showCB.setSelected(false);
       setVisible(false);
       wsnNManager.setVisible(false);
       for(Enumeration en=lineCharts.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setVisible(false);
       for(Enumeration en=eventHandlers.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setVisible(false);
       for(Enumeration en=wsnSDevices.elements();en.hasMoreElements();) ((WSNSocketDevice)en.nextElement()).setVisible(false);
    } else if(!this.isVisible()) this.setVisible(true);
  }
    if(conf[31].equals("1")){
      

       w.ap.onExit(107);
       String xcommand2="java -classpath "+System.getProperty("java.class.path")+" Infinity "+w.replace(w.getSx(3),","," ");

                           if(gs!=null){
                             for(Enumeration k= gs.groups.keys(); k.hasMoreElements();){
                               String g2= (String)k.nextElement();
                               GroupInfo gi=(GroupInfo) gs.groups.get(g2); 
                               if(gi.recordContent==true){
                                  gs.nap.saveRecord(g2,gi.starttime,gi.record.toString());
                               }
                             }
                             gs.stop();
                           }

                           w.setHVar("temp", xcommand2, pid);
                           new Thread(){
                              public void run(){
              Runtime rt = Runtime.getRuntime();
              String os = System.getProperty("os.name").toLowerCase();
              try{
                if(os.indexOf( "win" ) >= 0){
                  String cmd[] = {"cmd.exe","/C","start "+w.getHVar("temp")};
                   Process proc = rt.exec(cmd);
                }else if(os.indexOf( "mac" ) >= 0){
                   rt.exec( "open" + w.getHVar("temp"));
                } else if(os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0){

                  Process proc = rt.exec(w.getHVar("temp")+" &");
                }
              } catch(IOException e){e.printStackTrace();}

                           }
                           }.start();
                           try{
                             Thread.sleep(2*1000L);
                           } catch(InterruptedException e){}
                           w.ap.onExit(108);
                           System.exit(0);
    }else if(conf[31].equals("2")){
      w.ap.onExit(109);
      System.exit(0);
    }
}

  public void informVersion(String apId,int status,String version){
    if(apId.equals(this.apId)){
    if(status==1 || status==3) {hasNewVersion=true; newversion=version;}
    

    updateTitle();
    } else {
            Iterator it=myAps.values().iterator();
             for(;it.hasNext();){

               ((WSNApplication)it.next()).informVersion(apId, status, version);
           }
    }
  }

public void setSerialportConfig(String stringx[]){
  Object o=serialPorts.get(stringx[1]);
  if(o!=null){
     WSNSerial serial=(WSNSerial)o;
     serial.setConfig(stringx[2]);

  }
}

public void setSocketportConfig(String stringx[]){

  Object o=socketServers.get(stringx[1]);
  if(o!=null){
     WSNSocketServer sServer=(WSNSocketServer)o;
     sServer.setConfig(stringx[2]);
  }
}

public String[] getNodeData(String fid){
    String rtn[]={};
    File f=new File(nodesFile);
    FileInputStream in;
    BufferedReader d;

    if(f.exists()) {
    try{
        in=new FileInputStream(nodesFile);
        d= new BufferedReader(new InputStreamReader(in));
        while(true){
          String str1=d.readLine();
          if(str1==null) {in.close(); d.close(); break; }
          if(str1.length()>1) {

              String da[]=w.csvLineToArray(w.de("@@",str1));
              if(da.length>3 && da[3].equals(fid)){
                  rtn=da;
                  break;
              }
          }
        }
        in.close();
        d.close();
    } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return rtn;
}
  public void log(String msg){
    w.log(msg,logFileHead,true);
  }

public boolean isAlive(){
    return true;
}
public boolean isNull(){
    return false;
}
public boolean isSleep(){
    return isSleep;
}
public void killThread(){

}
 byte [] addChksum(byte in[],boolean needCS,String csType){
  int cnt=in.length;
  byte [] out=new byte[cnt];
                   if(needCS) {
                     if(csType.equals("CheckSum") || csType.equals("0x0D")) out=new byte[cnt+1];
                     else if(csType.equals("Modbus CRC")) out=new byte[cnt+2];
                     for(int i=0;i<cnt;i++){
                         out[i]=in[i];	
                       }
                     if(csType.equals("CheckSum")) {
                       out[cnt]=0;
                       for(int i=0;i<cnt;i++){
                         out[cnt]^=out[i];	
                       }
                     }  else if(csType.equals("Modbus CRC")) {
                          byte ad[]=IntTo2ByteArray(getMBCRC(in, cnt));
                         out[cnt]=ad[0];	
                         out[cnt+1]=ad[1];	
                     } else if(csType.equals("0x0D")) {
                         out[cnt]=0x0D;	
                     }
                   }
                   else out=in;
  return out;
}

  public String byteToStr(byte b[],int n){
      if(b.length<n) n=b.length;
      byte a[]=new byte[n];
      for(int i=0;i<n;i++) a[i]=b[i];
      return byteToStr(a);
  }

  static public String byteToStr(byte b[]){
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

  static public byte[] strToByte(String command){
    int cnt=0;
    byte [] b0={};
            StringTokenizer st=new StringTokenizer(command, " ");
            int intx[]=new int[command.length()];
            if(command!=null && command.length()>0){
              for(cnt=0;st.hasMoreTokens();cnt++){
                intx[cnt]=Integer.decode("0x"+st.nextToken()).intValue();  
              }
              b0=new byte[cnt];
                   for(int i=0;i<cnt;i++){
                     b0[i]=(byte)intx[i];
                   }
            }
            return b0;
  }
private class NodeThread extends Thread{
    int count=0;
    public void run(){
      while(true){
        try{

          if(!chkVersionOK){
            String cmd="performmessage wsn.WSN getstatus "+version+" "+w.e642(versionDate);
            if(gIsFinal) w.sendToOne(cmd,w.getGNS(44));

          }
          isSleep=true;
          count++;
          if(count>1) gIsFinal=true;

          if(!chkVersionOK) nodeThread.sleep(3000L);
          else nodeThread.sleep(1000L * 60L * 60L * 24L * 365);
          isSleep=false;

        }catch(InterruptedException e){

            isSleep=false;
        }
    }
  }
}
   public class DisplayData{
   String data;
   int fontSize;
   Color fontColor;
   public DisplayData(String data,Color fontColor,int fontSize){
     this.data=data;
     this.fontColor=fontColor;
     this.fontSize=fontSize;
   }
 }
 public class DataRecord{
   public StringBuffer sb=new StringBuffer();
   boolean hexType;
   public DataRecord(boolean hexType){
     this.hexType=hexType;
   }
   public void setHexType(boolean type){
     this.hexType=type;
   }
   public void clear(){
     sb.delete(0,sb.length());
   }
 }

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  private javax.swing.JButton chartBtn;
  private javax.swing.JCheckBox chkSumCB;
  private javax.swing.JButton clearSendBtn;
  private javax.swing.JButton clearShowBtn;
  private javax.swing.JCheckBox continueSendCB;
  private javax.swing.JCheckBox crnlCB;
  private javax.swing.JButton eventBtn;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JComboBox jComboBox3;
  private javax.swing.JComboBox jComboBox4;
  private javax.swing.JComboBox jComboBox5;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JList jList1;
  private javax.swing.JList jList2;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem10;
  private javax.swing.JMenuItem jMenuItem11;
  private javax.swing.JMenuItem jMenuItem12;
  private javax.swing.JMenuItem jMenuItem13;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
  private javax.swing.JMenuItem jMenuItem7;
  private javax.swing.JMenuItem jMenuItem8;
  private javax.swing.JMenuItem jMenuItem9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane4;
  private javax.swing.JTextArea jTextArea1;
  public javax.swing.JTextPane jTextPane1;
  private javax.swing.JButton myApBtn;
  private javax.swing.JCheckBox saveFileCB;
  private javax.swing.JRadioButton send16RB;
  private javax.swing.JButton sendBtn;
  private javax.swing.JTextField sendIntervalTF;
  private javax.swing.JRadioButton sendStrRB;
  public javax.swing.JRadioButton show16RB;
  public javax.swing.JCheckBox showCB;
  private javax.swing.JCheckBox showSrcCB;
  public javax.swing.JRadioButton showStrRB;
  private javax.swing.JCheckBox showSysMsgCB;
  private javax.swing.JCheckBox showTimeCB;
  private javax.swing.JButton stopContinueSendBtn;

}