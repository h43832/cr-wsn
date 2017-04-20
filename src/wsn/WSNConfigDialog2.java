
package wsn;

import infinity.client.Weber;
import infinity.server.Net;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import y.ylib.ylib;
/**
 *
 * @author Administrator
 */
public class WSNConfigDialog2 extends infinity.client.CRConfig {
Net n;
Weber w;
String pid;
DefaultListModel listModel1=new DefaultListModel(),listModel2=new DefaultListModel();

String propsFile="apps"+File.separator+"cr-wsn"+File.separator+"node_properties.txt";
 Properties props=new Properties();
  TreeMap hostTM=new TreeMap(),nameTM=new TreeMap(),groupTM=new TreeMap(),pwTM=new TreeMap(),memberPWTM=new TreeMap(),myApTM=new TreeMap();
 int historyCount=15;
 String historyFile="apps"+File.separator+"cr-wsn"+File.separator+"history.txt";
 SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 public ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle");
 boolean changeHistory=false;

  public WSNConfigDialog2() {
    initComponents();
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=470;
        int h2=510;

        setSize(w2,h2);
        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
  }
public void init(Weber w,Net n,String pid){
  this.n=n;
  this.w=w;
  this.pid=pid;
  readProperties();
  updateUI_General();
  updateUI_ports();
  this.setModal(true);

  setVisible(true);
}
private void updateUI_ports(){
  String serials[]=WSNSerial.getPorts();
  jComboBox15.removeAllItems();
  for(int i=0;i<serials.length;i++) jComboBox15.addItem(serials[i]);
  String seri=w.getValueString(props.getProperty("serial_port_open"));
  if(seri.length()>0){
    serials=seri.split(",");
    for(int i=0;i<serials.length;i++) listModel1.addElement(serials[i]);
    String selected=(String)jList1.getModel().getElementAt(0);
    jList1.setSelectedIndex(0);
    boolean found=false;
    if(jComboBox15.getItemCount()>0){
      for(int i=0;i<jComboBox15.getItemCount();i++){
        if(((String)jComboBox15.getItemAt(i)).equals(selected)){
          found=true;
          jComboBox15.setSelectedIndex(i);
          break;
        }
      }
    }
    if(!found){
        jComboBox15.setSelectedItem(selected);
    }
    setSerial(selected);
  }
  String socke=w.getValueString(props.getProperty("socket_port_open"));
  String socks[];
  if(socke.length()>0){
    socks=socke.split(",");
    for(int i=0;i<socks.length;i++) listModel2.addElement(socks[i]);
    String selected=(String)jList2.getModel().getElementAt(0);
    jList2.setSelectedIndex(0);
    jTextField1.setText(selected);
    setSocket(selected);
  }
}

void setSerial(String portName){
      String bRate="9600",dBit="8",pBit="N",stopB="1",sendingType="0",sendingTo="";
      String seri=w.getValueString(props.getProperty("serial_port_"+portName));
      String serial[];
      if(seri.length()>0) {
        serial=seri.split(",");
      } else {
        seri=w.getValueString(props.getProperty("serial_port_default"));
        if(seri.length()>0){
          serial=seri.split(",");
        } else {
           serial=new String[]{portName,bRate,dBit,pBit,stopB,sendingType,sendingTo};
        }
      }
      jComboBox8.setSelectedItem(serial[1]);
        jComboBox9.setSelectedItem(serial[2]);
        if(serial[3].substring(0,1).equalsIgnoreCase("N")) jComboBox12.setSelectedItem("NONE");
          else if(serial[3].substring(0,1).equalsIgnoreCase("O")) jComboBox12.setSelectedItem("ODD");
          else if(serial[3].substring(0,1).equalsIgnoreCase("E")) jComboBox12.setSelectedItem("EVEN");
          else if(serial[3].substring(0,1).equalsIgnoreCase("M")) jComboBox12.setSelectedItem("MARK");
          else if(serial[3].substring(0,1).equalsIgnoreCase("S")) jComboBox12.setSelectedItem("SPACE");
         jComboBox14.setSelectedItem(serial[4]);
}
void setSocket(String portNumber){
      String maxConn="10",hbInterval="60",sendingType="0",sendingTo="";
      String sock=w.getValueString(props.getProperty("socket_port_"+portNumber));
      String socks[];
      if(sock.length()>0) {
        socks=sock.split(",");
      } else {
        sock=w.getValueString(props.getProperty("socket_port_default"));
        if(sock.length()>0){
          socks=sock.split(",");
        } else {
           socks=new String[]{portNumber,maxConn,hbInterval,"","",sendingType,sendingTo};
        }
      }
      jTextField2.setText(maxConn);
      jTextField3.setText(hbInterval);
}

void setSerialDefault(){
      String bRate="9600",dBit="8",pBit="N",stopB="1",sendingType="0",sendingTo="";
      String seri=w.getValueString(props.getProperty("serial_port_default"));
      String serial[];
      if(seri.length()>0) {
        serial=seri.split(",");
        } else {
           serial=new String[]{"",bRate,dBit,pBit,stopB,sendingType,sendingTo};
        }
      jComboBox7.setSelectedItem(serial[1]);
        jComboBox11.setSelectedItem(serial[2]);
        if(serial[3].substring(0,1).equalsIgnoreCase("N")) jComboBox10.setSelectedItem("NONE");
          else if(serial[3].substring(0,1).equalsIgnoreCase("O")) jComboBox10.setSelectedItem("ODD");
          else if(serial[3].substring(0,1).equalsIgnoreCase("E")) jComboBox10.setSelectedItem("EVEN");
          else if(serial[3].substring(0,1).equalsIgnoreCase("M")) jComboBox10.setSelectedItem("MARK");
          else if(serial[3].substring(0,1).equalsIgnoreCase("S")) jComboBox10.setSelectedItem("SPACE");
         jComboBox13.setSelectedItem(serial[4]);
         if(serial[5].equals("1")) jRadioButton1.setSelected(true); else jRadioButton2.setSelected(true);
}
void setSocketDefault(){
      String maxConn="10",hbInterval="60",hbData="hi",sendingType="1",sendingTo="";
      String sock=w.getValueString(props.getProperty("socket_port_default"));
      String socks[];
      if(sock.length()>0) {
        socks=sock.split(",");
       } else {
           socks=new String[]{"",maxConn,hbInterval,hbData,"",sendingType,sendingTo};
        }
      jTextField4.setText(socks[1]);
      jTextField5.setText(socks[2]);
      jTextField6.setText(socks[3]);
      if(socks[5].equals("1")) jRadioButton3.setSelected(true); else jRadioButton4.setSelected(true);
}
   public static final Pattern Num_PATTERN= Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
    public static boolean isNumeric(String s){
          return (s==null? false: Num_PATTERN.matcher(s).matches());
    }
void updateUI_General(){
  readHistory();
  jLabel1.setText(bundle2.getString("WSNConfigDialog.xy.msg1")+" "+(n!=null? n.nethost:w.inet[w.inet.length-1].getHostAddress()));
  jComboBox1.removeAllItems();
  jComboBox2.removeAllItems();
  jComboBox3.removeAllItems();
  jComboBox4.removeAllItems();
  jComboBox5.removeAllItems();
  jComboBox6.removeAllItems();
  String time=format2.format(new Date());
  hostTM.put(w.getHVar("g_host"),time);
  groupTM.put(w.getHVar("g_togroupname"),time);
  nameTM.put(w.getHVar("g_loginname"),time);
  pwTM.put(w.showPwFull(pid),time);
  memberPWTM.put(w.getSsx(6, pid),time);
  if(props!=null && w.getValueString(props.getProperty("my_ap")).length()>0) myApTM.put(props.getProperty("my_ap"),time);
  if(hostTM.size()>0){
    hostTM=chkHistory(hostTM);
    Iterator it=hostTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox1.addItem((String)it.next());
    }
    jComboBox1.setSelectedItem(w.getHVar("g_host"));
  }
  if(groupTM.size()>0){
    groupTM=chkHistory(groupTM);
    Iterator it=groupTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox2.addItem((String)it.next());
    }
    jComboBox2.setSelectedItem(w.getHVar("g_togroupname"));
  }
  if(nameTM.size()>0){
    nameTM=chkHistory(nameTM);
    Iterator it=nameTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox3.addItem((String)it.next());
    }
    jComboBox3.setSelectedItem(w.getHVar("g_loginname"));
  }
  if(pwTM.size()>0){
    pwTM=chkHistory(pwTM);
    Iterator it=pwTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox4.addItem((String)it.next());
    }
    jComboBox4.setSelectedItem(w.showPwFull(pid));
  }
  if(memberPWTM.size()>0){
    memberPWTM=chkHistory(memberPWTM);
    Iterator it=memberPWTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox5.addItem((String)it.next());
    }
    jComboBox5.setSelectedItem(w.getSsx(6, pid));
  }
  if(myApTM.size()>0){
    myApTM=chkHistory(myApTM);
    Iterator it=myApTM.keySet().iterator();
    for(;it.hasNext();){
      jComboBox6.addItem((String)it.next());
    }
    jComboBox6.setSelectedItem(props.getProperty("my_ap"));
  }
  cbConnectTo.setSelected(w.getHVar("g_connectatstart")!=null && w.getHVar("g_connectatstart").equalsIgnoreCase("true"));

  jComboBox1.setEnabled(w.getHVar("g_connectatstart")!=null && w.getHVar("g_connectatstart").equalsIgnoreCase("true"));

  if(w.schedule!=null){ 
    if(w.schedule.length<1) JOptionPane.showMessageDialog(this,"Warning: w.schedule.length = 0.");
  } else JOptionPane.showMessageDialog(this,"Warning: w.schedule is null.");

  cbNoShowConfig.setSelected(w.getHVar("run_config")==null || !w.getHVar("run_config").equalsIgnoreCase("true"));
  jCheckBox1.setSelected(props.getProperty("run_my_ap_only")!=null && props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"));
}
boolean updateVariables(){
  boolean changeV=false,chkV=true;
  if(cbConnectTo.isSelected() !=(w.getHVar("g_connectatstart")!=null && w.getHVar("g_connectatstart").equalsIgnoreCase("true"))){

    changeV=true;
  }
  if(jComboBox1.getSelectedItem()==null || !((String)jComboBox1.getSelectedItem()).trim().equals(w.getHVar("g_host").trim())){

   changeV=true; 
  }
  String host=(jComboBox1.getSelectedItem()==null? "":((String)jComboBox1.getSelectedItem()).trim());
  if(cbConnectTo.isSelected()){
    if(host.length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg2")); chkV=false;}
    else if(host.equalsIgnoreCase("localhost")){}
    else if(host.length()<7){
       JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg3")+" "+host); chkV=false;
    } else{
      boolean chkIPOK=false;
      int inx=host.indexOf(".");
      if(inx>-1){
        inx=host.indexOf(".", inx+1);
        if(inx>-1){
          inx=host.indexOf(".", inx+1);
          if(inx>-1) {
            chkIPOK=true;
            for(int i=0;i<host.length();i++){
              if(!((host.charAt(i)>='0' && host.charAt(i)<='9') || host.charAt(i)=='.')){
               chkIPOK=false; break;
              }
            }
          }
        }
      }
      if(!chkIPOK) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg4")+" "+host); chkV=false;}
    }
  }
  if(jComboBox2.getSelectedItem()==null || ((String)jComboBox2.getSelectedItem()).trim().length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg5")); chkV=false;}
  if(jComboBox2.getSelectedItem()==null || !((String)jComboBox2.getSelectedItem()).trim().equals(w.getHVar("g_togroupname").trim())){

   changeV=true; 
  }
  if(jComboBox3.getSelectedItem()==null || ((String)jComboBox3.getSelectedItem()).trim().length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg6")); chkV=false;}
  if(jComboBox3.getSelectedItem()==null || !((String)jComboBox3.getSelectedItem()).trim().equals(w.getHVar("g_loginname").trim())){

   changeV=true; 
  }
  if(jComboBox4.getSelectedItem()==null || ((String)jComboBox4.getSelectedItem()).trim().length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg7")); chkV=false;}
  if(jComboBox4.getSelectedItem()==null || !((String)jComboBox4.getSelectedItem()).trim().equals(w.showPwFull(pid))){

   changeV=true; 
  }
  if(jComboBox5.getSelectedItem()==null || ((String)jComboBox5.getSelectedItem()).trim().length()<1) {JOptionPane.showMessageDialog(this,bundle2.getString("WSNConfigDialog.xy.msg8")); chkV=false;}
  if(jComboBox5.getSelectedItem()==null || !((String)jComboBox5.getSelectedItem()).trim().equals(w.showPwFull(pid))){

   changeV=true; 
  }
  if(jComboBox6.getSelectedItem()==null || !((String)jComboBox6.getSelectedItem()).trim().equals(props.getProperty("my_ap"))){

   changeV=true; 
  }
  

  if(cbNoShowConfig.isSelected() == (w.getHVar("run_config")!=null && w.getHVar("run_config").equalsIgnoreCase("true"))){

    changeV=true;
  }
  if(!jCheckBox1.isSelected() && (props.getProperty("run_my_ap_only")!=null && props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))){

    changeV=true;
  } else if(jCheckBox1.isSelected() && (props.getProperty("run_my_ap_only")==null || !props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))){

    changeV=true;
  }
  if(changeV && chkV) {

    w.setHVar("g_connectatstart",(cbConnectTo.isSelected()? "true":"false"),pid);
    w.setHVar("g_host",(jComboBox1.getSelectedItem()==null? "": ((String)jComboBox1.getSelectedItem()).trim()), pid);
    w.setHVar("run_config",(cbNoShowConfig.isSelected()? "false":"true"),pid);
    w.setHVar("g_togroupname", (jComboBox2.getSelectedItem()==null? "":((String)jComboBox2.getSelectedItem()).trim()), pid);
    w.setGNS(11, w.getHVar("g_togroupname"), pid);
    w.setHVar("g_loginname", (jComboBox3.getSelectedItem()==null? "":((String)jComboBox3.getSelectedItem()).trim()), pid);
    w.setGNS(27, w.getHVar("g_loginname"), pid);
    props.setProperty("my_ap",(jComboBox6.getSelectedItem()==null ? "": ((String)jComboBox6.getSelectedItem()).trim()));
    props.setProperty("run_my_ap_only", (jCheckBox1.isSelected()? "Y":"N"));

    w.setCommandPw(((String)jComboBox4.getSelectedItem()).trim(),pid);
    w.setSsx(6, ((String)jComboBox5.getSelectedItem()).trim(), pid);
    w.saveSysFile("wsn_properties");
    saveProperties();
   saveHistory();
  } else if(changeHistory && chkV) saveHistory();
  return chkV;
}
private void saveProperties(){
    try{
        FileOutputStream out = new FileOutputStream(propsFile );
        props.store(out,"");
      }catch(IOException e){e.printStackTrace();}
}
void readHistory(){
    if(new File(historyFile).exists()){
      try{
      FileInputStream  in=new FileInputStream(historyFile);
      BufferedReader d= new BufferedReader(new InputStreamReader(in));
      String str1=null;
        while(true){
	  str1=d.readLine();
	  if(str1==null) {in.close(); d.close(); break; }
          if(str1.length()>0){
             String tmp[]=ylib.csvlinetoarray(str1);
             if(tmp[0].equalsIgnoreCase("host")) hostTM.put(tmp[1], tmp[2]);
             else if(tmp[0].equalsIgnoreCase("group")) groupTM.put(tmp[1], tmp[2]);
             else if(tmp[0].equalsIgnoreCase("name")) nameTM.put(tmp[1], tmp[2]);
             else if(tmp[0].equalsIgnoreCase("pw")) pwTM.put(tmp[1], tmp[2]);
             else if(tmp[0].equalsIgnoreCase("memberpw")) memberPWTM.put(tmp[1], tmp[2]);
             else if(tmp[0].equalsIgnoreCase("myap")) myApTM.put(tmp[1], tmp[2]);
          } 
        }

	in.close();
	d.close();
         } catch (IOException e) { e.printStackTrace();}
       catch (Exception e) { e.printStackTrace();}
    }

}
private void saveHistory(){
  StringBuffer sb=new StringBuffer();
  boolean first=true;
  hostTM.put(w.getHVar("g_host"), format2.format(new Date()));
  groupTM.put(w.getHVar("g_togroupname"), format2.format(new Date()));
  nameTM.put(w.getHVar("g_loginname"), format2.format(new Date()));
  pwTM.put(w.showPwFull(pid), format2.format(new Date()));
  memberPWTM.put(w.getSsx(6, pid), format2.format(new Date()));

  if(props!=null && w.getValueString(props.getProperty("my_ap")).length()>0) myApTM.put(props.getProperty("my_ap"), format2.format(new Date()));
  Iterator it=hostTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"host,"+key+","+(String)hostTM.get(key));
    first=false;
  }
  it=groupTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"group,"+key+","+(String)groupTM.get(key));
    first=false;
  }
  it=nameTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"name,"+key+","+(String)nameTM.get(key));
    first=false;
  }
  it=pwTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"pw,"+key+","+(String)pwTM.get(key));
    first=false;
  }
  it=memberPWTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"memberpw,"+key+","+(String)memberPWTM.get(key));
    first=false;
  }
  it=myApTM.keySet().iterator();
  for(;it.hasNext();){
    String key=(String)it.next();
    sb.append((first? "":"\r\n")+"myap,"+ylib.tocsv(key)+","+(String)myApTM.get(key));
    first=false;
  }
	try{
	  FileOutputStream out = new FileOutputStream (historyFile);
	  byte [] b=sb.toString().getBytes();
	  out.write(b);
	  out.close();
     }catch(IOException e){e.printStackTrace();}
}
private void saveProps(){
  try{
    propsFile=(w.getHVar("wsn_properties")==null? propsFile:w.getHVar("wsn_properties"));
    propsFile=w.fileSeparator(propsFile);
    FileOutputStream out = new FileOutputStream(propsFile );
        props.store(out,"");
        out.close();
  }catch(IOException e){
    e.printStackTrace();
  }
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

}

TreeMap chkHistory(TreeMap tm){
  if(tm.size()>historyCount){
    SortedSet ss=entriesSortedByValues(tm);
    Iterator it=ss.iterator();
    TreeMap tmpTM=new TreeMap();
    for(;it.hasNext();){
      Map.Entry entry=(Map.Entry) it.next();
      tmpTM.put((String)entry.getKey(), (String)entry.getValue());
      if(tmpTM.size()>=historyCount) break;
    }
    tm=(TreeMap)tmpTM.clone();
  }
  return tm;
}
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    jPanel3 = new javax.swing.JPanel();
    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    buttonGroup3 = new javax.swing.ButtonGroup();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel11 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    cbConnectTo = new javax.swing.JCheckBox();
    jComboBox1 = new javax.swing.JComboBox();
    jButton1 = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jComboBox2 = new javax.swing.JComboBox();
    jButton2 = new javax.swing.JButton();
    jPanel4 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jComboBox3 = new javax.swing.JComboBox();
    jButton3 = new javax.swing.JButton();
    jPanel6 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    jComboBox4 = new javax.swing.JComboBox();
    jButton4 = new javax.swing.JButton();
    btnGo = new javax.swing.JButton();
    btnCancel = new javax.swing.JButton();
    jPanel7 = new javax.swing.JPanel();
    jLabel6 = new javax.swing.JLabel();
    jComboBox5 = new javax.swing.JComboBox();
    jButton5 = new javax.swing.JButton();
    jPanel8 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    btnRestt = new javax.swing.JButton();
    jPanel10 = new javax.swing.JPanel();
    cbNoShowConfig = new javax.swing.JCheckBox();
    jPanel5 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    jComboBox6 = new javax.swing.JComboBox();
    jButton6 = new javax.swing.JButton();
    jPanel9 = new javax.swing.JPanel();
    jCheckBox1 = new javax.swing.JCheckBox();
    jPanel12 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();
    jButton14 = new javax.swing.JButton();
    jButton15 = new javax.swing.JButton();
    jButton16 = new javax.swing.JButton();
    jPanel14 = new javax.swing.JPanel();
    jLabel23 = new javax.swing.JLabel();
    jComboBox15 = new javax.swing.JComboBox();
    jPanel15 = new javax.swing.JPanel();
    jLabel24 = new javax.swing.JLabel();
    jComboBox8 = new javax.swing.JComboBox();
    jPanel16 = new javax.swing.JPanel();
    jLabel25 = new javax.swing.JLabel();
    jComboBox9 = new javax.swing.JComboBox();
    jPanel17 = new javax.swing.JPanel();
    jLabel26 = new javax.swing.JLabel();
    jComboBox14 = new javax.swing.JComboBox();
    jPanel30 = new javax.swing.JPanel();
    jButton13 = new javax.swing.JButton();
    jRadioButton1 = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jPanel31 = new javax.swing.JPanel();
    jButton12 = new javax.swing.JButton();
    jPanel18 = new javax.swing.JPanel();
    jLabel17 = new javax.swing.JLabel();
    jComboBox7 = new javax.swing.JComboBox();
    jPanel19 = new javax.swing.JPanel();
    jLabel18 = new javax.swing.JLabel();
    jComboBox11 = new javax.swing.JComboBox();
    jPanel20 = new javax.swing.JPanel();
    jLabel19 = new javax.swing.JLabel();
    jComboBox10 = new javax.swing.JComboBox();
    jPanel21 = new javax.swing.JPanel();
    jLabel20 = new javax.swing.JLabel();
    jComboBox13 = new javax.swing.JComboBox();
    jPanel32 = new javax.swing.JPanel();
    jLabel11 = new javax.swing.JLabel();
    jComboBox12 = new javax.swing.JComboBox();
    jPanel13 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jList2 = new javax.swing.JList();
    jButton7 = new javax.swing.JButton();
    jButton8 = new javax.swing.JButton();
    jButton9 = new javax.swing.JButton();
    jPanel22 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jPanel23 = new javax.swing.JPanel();
    jLabel8 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jPanel24 = new javax.swing.JPanel();
    jLabel9 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jLabel10 = new javax.swing.JLabel();
    jPanel28 = new javax.swing.JPanel();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jButton11 = new javax.swing.JButton();
    jPanel29 = new javax.swing.JPanel();
    jPanel25 = new javax.swing.JPanel();
    jLabel12 = new javax.swing.JLabel();
    jTextField4 = new javax.swing.JTextField();
    jPanel26 = new javax.swing.JPanel();
    jLabel13 = new javax.swing.JLabel();
    jTextField5 = new javax.swing.JTextField();
    jLabel14 = new javax.swing.JLabel();
    jPanel27 = new javax.swing.JPanel();
    jLabel15 = new javax.swing.JLabel();
    jTextField6 = new javax.swing.JTextField();
    jButton10 = new javax.swing.JButton();

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSNConfigDialog2.title")); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    cbConnectTo.setText(bundle.getString("WSNConfigDialog2.cbConnectTo.text")); 
    cbConnectTo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbConnectToActionPerformed(evt);
      }
    });
    jPanel1.add(cbConnectTo);

    jComboBox1.setEditable(true);
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox1.setToolTipText(bundle.getString("WSNConfigDialog2.jComboBox1.toolTipText")); 
    jComboBox1.setPreferredSize(new java.awt.Dimension(180, 25));
    jPanel1.add(jComboBox1);

    jButton1.setText(bundle.getString("WSNConfigDialog2.jButton1.text")); 
    jButton1.setToolTipText(bundle.getString("WSNConfigDialog2.jButton1.toolTipText")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton1);

    jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setText(bundle.getString("WSNConfigDialog2.jLabel2.text")); 
    jPanel2.add(jLabel2);

    jComboBox2.setEditable(true);
    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox2.setToolTipText(bundle.getString("WSNConfigDialog2.jComboBox2.toolTipText")); 
    jComboBox2.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel2.add(jComboBox2);

    jButton2.setText(bundle.getString("WSNConfigDialog2.jButton2.text")); 
    jButton2.setToolTipText(bundle.getString("WSNConfigDialog2.jButton2.toolTipText")); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton2);

    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel3.setText(bundle.getString("WSNConfigDialog2.jLabel3.text")); 
    jPanel4.add(jLabel3);

    jComboBox3.setEditable(true);
    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox3.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel4.add(jComboBox3);

    jButton3.setText(bundle.getString("WSNConfigDialog2.jButton3.text")); 
    jButton3.setToolTipText(bundle.getString("WSNConfigDialog2.jButton3.toolTipText")); 
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton3);

    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel5.setText(bundle.getString("WSNConfigDialog2.jLabel5.text")); 
    jPanel6.add(jLabel5);

    jComboBox4.setEditable(true);
    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox4.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel6.add(jComboBox4);

    jButton4.setText(bundle.getString("WSNConfigDialog2.jButton4.text")); 
    jButton4.setToolTipText(bundle.getString("WSNConfigDialog2.jButton4.toolTipText")); 
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });
    jPanel6.add(jButton4);

    btnGo.setText(bundle.getString("WSNConfigDialog2.btnGo.text")); 
    btnGo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnGoActionPerformed(evt);
      }
    });

    btnCancel.setText(bundle.getString("WSNConfigDialog2.btnCancel.text")); 
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });

    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel6.setText(bundle.getString("WSNConfigDialog2.jLabel6.text")); 
    jPanel7.add(jLabel6);
    jLabel6.getAccessibleContext().setAccessibleName(bundle.getString("WSNConfigDialog2.jLabel6.AccessibleContext.accessibleName")); 

    jComboBox5.setEditable(true);
    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox5.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel7.add(jComboBox5);

    jButton5.setText(bundle.getString("WSNConfigDialog2.jButton5.text")); 
    jButton5.setToolTipText(bundle.getString("WSNConfigDialog2.jButton5.toolTipText")); 
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton5ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton5);

    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setText(bundle.getString("WSNConfigDialog2.jLabel1.text")); 
    jPanel8.add(jLabel1);

    btnRestt.setText(bundle.getString("WSNConfigDialog2.btnRestt.text")); 
    btnRestt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnResttActionPerformed(evt);
      }
    });

    jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    cbNoShowConfig.setText(bundle.getString("WSNConfigDialog2.cbNoShowConfig.text")); 
    jPanel10.add(cbNoShowConfig);

    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel4.setText(bundle.getString("WSNConfigDialog2.jLabel4.text")); 
    jPanel5.add(jLabel4);

    jComboBox6.setEditable(true);
    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox6.setPreferredSize(new java.awt.Dimension(200, 21));
    jPanel5.add(jComboBox6);

    jButton6.setText(bundle.getString("WSNConfigDialog2.jButton6.text")); 
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton6ActionPerformed(evt);
      }
    });
    jPanel5.add(jButton6);

    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox1.setText(bundle.getString("WSNConfigDialog2.jCheckBox1.text")); 
    jCheckBox1.setActionCommand(bundle.getString("WSNConfigDialog2.jCheckBox1.actionCommand")); 
    jPanel9.add(jCheckBox1);

    javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
    jPanel11.setLayout(jPanel11Layout);
    jPanel11Layout.setHorizontalGroup(
      jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 429, Short.MAX_VALUE)
      .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel11Layout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel11Layout.createSequentialGroup()
              .addGap(10, 10, 10)
              .addComponent(btnRestt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGap(30, 30, 30)
              .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGap(30, 30, 30)
              .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addGap(0, 0, Short.MAX_VALUE)))
    );
    jPanel11Layout.setVerticalGroup(
      jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
      .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel11Layout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(0, 0, 0)
          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(5, 5, 5)
          .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(5, 5, 5)
          .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(5, 5, 5)
          .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(5, 5, 5)
          .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(5, 5, 5)
          .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(0, 0, 0)
          .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(10, 10, 10)
          .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(20, 20, 20)
          .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRestt)
            .addComponent(btnGo)
            .addComponent(btnCancel))
          .addGap(0, 0, Short.MAX_VALUE)))
    );

    jTabbedPane1.addTab(bundle.getString("WSNConfigDialog2.jPanel11.TabConstraints.tabTitle"), jPanel11); 

    jPanel12.setLayout(null);

    jList1.setModel(listModel1);
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
    jScrollPane1.setViewportView(jList1);

    jPanel12.add(jScrollPane1);
    jScrollPane1.setBounds(20, 10, 80, 150);

    jButton14.setText(bundle.getString("WSNConfigDialog2.jButton14.text")); 
    jPanel12.add(jButton14);
    jButton14.setBounds(120, 120, 90, 30);

    jButton15.setText(bundle.getString("WSNConfigDialog2.jButton15.text")); 
    jPanel12.add(jButton15);
    jButton15.setBounds(220, 120, 90, 30);

    jButton16.setText(bundle.getString("WSNConfigDialog2.jButton16.text")); 
    jPanel12.add(jButton16);
    jButton16.setBounds(320, 120, 90, 30);

    jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel23.setText(bundle.getString("WSNConfigDialog2.jLabel23.text")); 
    jPanel14.add(jLabel23);

    jComboBox15.setEditable(true);
    jComboBox15.setPreferredSize(new java.awt.Dimension(152, 29));
    jPanel14.add(jComboBox15);

    jPanel12.add(jPanel14);
    jPanel14.setBounds(120, 0, 280, 40);

    jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel24.setText(bundle.getString("WSNConfigDialog2.jLabel24.text")); 
    jPanel15.add(jLabel24);

    jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "75", "110", "134", "150", "200", "300", "600", "1200", "1800", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600" }));
    jPanel15.add(jComboBox8);

    jPanel12.add(jPanel15);
    jPanel15.setBounds(120, 40, 140, 30);

    jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel25.setText(bundle.getString("WSNConfigDialog2.jLabel25.text")); 
    jPanel16.add(jLabel25);

    jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8", "9" }));
    jPanel16.add(jComboBox9);

    jPanel12.add(jPanel16);
    jPanel16.setBounds(120, 80, 130, 30);

    jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel26.setText(bundle.getString("WSNConfigDialog2.jLabel26.text")); 
    jPanel17.add(jLabel26);

    jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
    jPanel17.add(jComboBox14);

    jPanel12.add(jPanel17);
    jPanel17.setBounds(260, 80, 120, 30);

    jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)), bundle.getString("WSNConfigDialog2.jPanel30.border.title"))); 
    jPanel30.setLayout(null);

    jButton13.setText(bundle.getString("WSNConfigDialog2.jButton13.text")); 
    jPanel30.add(jButton13);
    jButton13.setBounds(290, 30, 90, 23);

    buttonGroup2.add(jRadioButton1);
    jRadioButton1.setText(bundle.getString("WSNConfigDialog2.jRadioButton1.text")); 
    jPanel30.add(jRadioButton1);
    jRadioButton1.setBounds(10, 20, 220, 23);

    buttonGroup2.add(jRadioButton2);
    jRadioButton2.setSelected(true);
    jRadioButton2.setText(bundle.getString("WSNConfigDialog2.jRadioButton2.text")); 
    jPanel30.add(jRadioButton2);
    jRadioButton2.setBounds(10, 40, 123, 23);

    jPanel12.add(jPanel30);
    jPanel30.setBounds(20, 320, 400, 80);

    jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)), bundle.getString("WSNConfigDialog2.jPanel31.border.title"))); 
    jPanel31.setLayout(null);

    jButton12.setText(bundle.getString("WSNConfigDialog2.jButton12.text")); 
    jPanel31.add(jButton12);
    jButton12.setBounds(290, 70, 90, 23);

    jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel17.setText(bundle.getString("WSNConfigDialog2.jLabel17.text")); 
    jPanel18.add(jLabel17);

    jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "75", "110", "134", "150", "200", "300", "600", "1200", "1800", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600" }));
    jPanel18.add(jComboBox7);

    jPanel31.add(jPanel18);
    jPanel18.setBounds(10, 20, 150, 30);

    jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel18.setText(bundle.getString("WSNConfigDialog2.jLabel18.text")); 
    jPanel19.add(jLabel18);

    jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8", "9" }));
    jPanel19.add(jComboBox11);

    jPanel31.add(jPanel19);
    jPanel19.setBounds(10, 60, 130, 30);

    jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel19.setText(bundle.getString("WSNConfigDialog2.jLabel19.text")); 
    jPanel20.add(jLabel19);

    jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "ODD", "EVEN", "MARK", "SPACE" }));
    jPanel20.add(jComboBox10);

    jPanel31.add(jPanel20);
    jPanel20.setBounds(170, 20, 150, 30);

    jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel20.setText(bundle.getString("WSNConfigDialog2.jLabel20.text")); 
    jPanel21.add(jLabel20);

    jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
    jPanel21.add(jComboBox13);

    jPanel31.add(jPanel21);
    jPanel21.setBounds(170, 60, 120, 30);

    jPanel12.add(jPanel31);
    jPanel31.setBounds(20, 200, 400, 110);

    jPanel32.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel11.setText(bundle.getString("WSNConfigDialog2.jLabel11.text")); 
    jPanel32.add(jLabel11);

    jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "ODD", "EVEN", "MARK", "SPACE" }));
    jPanel32.add(jComboBox12);

    jPanel12.add(jPanel32);
    jPanel32.setBounds(260, 39, 150, 31);

    jTabbedPane1.addTab(bundle.getString("WSNConfigDialog2.jPanel12.TabConstraints.tabTitle"), jPanel12); 

    jPanel13.setLayout(null);

    jList2.setModel(listModel2);
    jList2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList2MouseClicked(evt);
      }
    });
    jList2.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jList2KeyReleased(evt);
      }
    });
    jScrollPane2.setViewportView(jList2);

    jPanel13.add(jScrollPane2);
    jScrollPane2.setBounds(20, 10, 80, 150);

    jButton7.setText(bundle.getString("WSNConfigDialog2.jButton7.text")); 
    jPanel13.add(jButton7);
    jButton7.setBounds(120, 130, 90, 23);

    jButton8.setText(bundle.getString("WSNConfigDialog2.jButton8.text")); 
    jPanel13.add(jButton8);
    jButton8.setBounds(220, 130, 90, 23);

    jButton9.setText(bundle.getString("WSNConfigDialog2.jButton9.text")); 
    jPanel13.add(jButton9);
    jButton9.setBounds(320, 130, 90, 23);

    jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel7.setText(bundle.getString("WSNConfigDialog2.jLabel7.text")); 
    jPanel22.add(jLabel7);

    jTextField1.setText(bundle.getString("WSNConfigDialog2.jTextField1.text")); 
    jTextField1.setPreferredSize(new java.awt.Dimension(156, 21));
    jPanel22.add(jTextField1);

    jPanel13.add(jPanel22);
    jPanel22.setBounds(120, 10, 250, 30);

    jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel8.setText(bundle.getString("WSNConfigDialog2.jLabel8.text")); 
    jPanel23.add(jLabel8);

    jTextField2.setText(bundle.getString("WSNConfigDialog2.jTextField2.text")); 
    jTextField2.setPreferredSize(new java.awt.Dimension(56, 21));
    jPanel23.add(jTextField2);

    jPanel13.add(jPanel23);
    jPanel23.setBounds(120, 50, 250, 30);

    jPanel24.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel9.setText(bundle.getString("WSNConfigDialog2.jLabel9.text")); 
    jPanel24.add(jLabel9);

    jTextField3.setText(bundle.getString("WSNConfigDialog2.jTextField3.text")); 
    jTextField3.setPreferredSize(new java.awt.Dimension(56, 21));
    jPanel24.add(jTextField3);

    jLabel10.setText(bundle.getString("WSNConfigDialog2.jLabel10.text")); 
    jPanel24.add(jLabel10);

    jPanel13.add(jPanel24);
    jPanel24.setBounds(120, 90, 250, 30);

    jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)), bundle.getString("WSNConfigDialog2.jPanel28.border.title"))); 
    jPanel28.setLayout(null);

    buttonGroup3.add(jRadioButton3);
    jRadioButton3.setText(bundle.getString("WSNConfigDialog2.jRadioButton3.text")); 
    jPanel28.add(jRadioButton3);
    jRadioButton3.setBounds(10, 20, 179, 23);

    buttonGroup3.add(jRadioButton4);
    jRadioButton4.setSelected(true);
    jRadioButton4.setText(bundle.getString("WSNConfigDialog2.jRadioButton4.text")); 
    jPanel28.add(jRadioButton4);
    jRadioButton4.setBounds(10, 50, 123, 23);

    jButton11.setText(bundle.getString("WSNConfigDialog2.jButton11.text")); 
    jPanel28.add(jButton11);
    jButton11.setBounds(290, 50, 79, 23);

    jPanel13.add(jPanel28);
    jPanel28.setBounds(20, 310, 390, 90);

    jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)), bundle.getString("WSNConfigDialog2.jPanel29.border.title"))); 
    jPanel29.setLayout(null);

    jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel12.setText(bundle.getString("WSNConfigDialog2.jLabel12.text")); 
    jPanel25.add(jLabel12);

    jTextField4.setText(bundle.getString("WSNConfigDialog2.jTextField4.text")); 
    jTextField4.setPreferredSize(new java.awt.Dimension(56, 21));
    jPanel25.add(jTextField4);

    jPanel29.add(jPanel25);
    jPanel25.setBounds(10, 20, 240, 30);

    jPanel26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel13.setText(bundle.getString("WSNConfigDialog2.jLabel13.text")); 
    jPanel26.add(jLabel13);

    jTextField5.setText(bundle.getString("WSNConfigDialog2.jTextField5.text")); 
    jTextField5.setPreferredSize(new java.awt.Dimension(56, 21));
    jPanel26.add(jTextField5);

    jLabel14.setText(bundle.getString("WSNConfigDialog2.jLabel14.text")); 
    jPanel26.add(jLabel14);

    jPanel29.add(jPanel26);
    jPanel26.setBounds(10, 50, 250, 30);

    jPanel27.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel15.setText(bundle.getString("WSNConfigDialog2.jLabel15.text")); 
    jPanel27.add(jLabel15);

    jTextField6.setText(bundle.getString("WSNConfigDialog2.jTextField6.text")); 
    jTextField6.setPreferredSize(new java.awt.Dimension(106, 21));
    jPanel27.add(jTextField6);

    jPanel29.add(jPanel27);
    jPanel27.setBounds(10, 80, 220, 30);

    jButton10.setText(bundle.getString("WSNConfigDialog2.jButton10.text")); 
    jPanel29.add(jButton10);
    jButton10.setBounds(300, 70, 70, 23);

    jPanel13.add(jPanel29);
    jPanel29.setBounds(20, 190, 390, 120);

    jTabbedPane1.addTab(bundle.getString("WSNConfigDialog2.jPanel13.TabConstraints.tabTitle"), jPanel13); 

    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    pack();
  }

  private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {

   System.exit(0);
  }

  private void btnResttActionPerformed(java.awt.event.ActionEvent evt) {
    updateUI_General();
  }

  private void btnGoActionPerformed(java.awt.event.ActionEvent evt) {
    if(updateVariables()) setVisible(false);
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
   System.exit(0);
  }

  private void cbConnectToActionPerformed(java.awt.event.ActionEvent evt) {
    if(cbConnectTo.isSelected()) {jComboBox1.setEnabled(true);  jButton1.setEnabled(true);}
    else {jComboBox1.setEnabled(false); jButton1.setEnabled(false);}
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    jComboBox1.removeAllItems();
    hostTM.clear();
    changeHistory=true;
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
     jComboBox2.removeAllItems();
    groupTM.clear();
    changeHistory=true;
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
     jComboBox3.removeAllItems();
    nameTM.clear();
    changeHistory=true;
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox4.removeAllItems();
    pwTM.clear();
    changeHistory=true;
  }

  private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
       jComboBox5.removeAllItems();
    memberPWTM.clear();
    changeHistory=true;
  }

  private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
     jComboBox6.removeAllItems();
    myApTM.clear();
    changeHistory=true;
  }

  private void jList1MouseClicked(java.awt.event.MouseEvent evt) {

  }

  private void jList2MouseClicked(java.awt.event.MouseEvent evt) {

  }

  private void jList2KeyReleased(java.awt.event.KeyEvent evt) {

  }

  private void jList1KeyReleased(java.awt.event.KeyEvent evt) {

  }
static <K,V extends java.lang.Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
        new java.util.Comparator<Map.Entry<K,V>>() {
            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {

                int res = e2.getValue().compareTo(e1.getValue());
                if (e1.getKey().equals(e2.getKey())) {
                    return res; 
                } else {
                    return res != 0 ? res : 1; 
                }
            }
        }
    );
    sortedEntries.addAll(map.entrySet());
    return sortedEntries;
}

  private javax.swing.JButton btnCancel;
  private javax.swing.JButton btnGo;
  private javax.swing.JButton btnRestt;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  private javax.swing.ButtonGroup buttonGroup3;
  private javax.swing.JCheckBox cbConnectTo;
  private javax.swing.JCheckBox cbNoShowConfig;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton10;
  private javax.swing.JButton jButton11;
  private javax.swing.JButton jButton12;
  private javax.swing.JButton jButton13;
  private javax.swing.JButton jButton14;
  private javax.swing.JButton jButton15;
  private javax.swing.JButton jButton16;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JButton jButton7;
  private javax.swing.JButton jButton8;
  private javax.swing.JButton jButton9;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox10;
  private javax.swing.JComboBox jComboBox11;
  private javax.swing.JComboBox jComboBox12;
  private javax.swing.JComboBox jComboBox13;
  private javax.swing.JComboBox jComboBox14;
  private javax.swing.JComboBox jComboBox15;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JComboBox jComboBox3;
  private javax.swing.JComboBox jComboBox4;
  private javax.swing.JComboBox jComboBox5;
  private javax.swing.JComboBox jComboBox6;
  private javax.swing.JComboBox jComboBox7;
  private javax.swing.JComboBox jComboBox8;
  private javax.swing.JComboBox jComboBox9;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
  private javax.swing.JLabel jLabel19;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel20;
  private javax.swing.JLabel jLabel23;
  private javax.swing.JLabel jLabel24;
  private javax.swing.JLabel jLabel25;
  private javax.swing.JLabel jLabel26;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JList jList1;
  private javax.swing.JList jList2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel10;
  private javax.swing.JPanel jPanel11;
  private javax.swing.JPanel jPanel12;
  private javax.swing.JPanel jPanel13;
  private javax.swing.JPanel jPanel14;
  private javax.swing.JPanel jPanel15;
  private javax.swing.JPanel jPanel16;
  private javax.swing.JPanel jPanel17;
  private javax.swing.JPanel jPanel18;
  private javax.swing.JPanel jPanel19;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel20;
  private javax.swing.JPanel jPanel21;
  private javax.swing.JPanel jPanel22;
  private javax.swing.JPanel jPanel23;
  private javax.swing.JPanel jPanel24;
  private javax.swing.JPanel jPanel25;
  private javax.swing.JPanel jPanel26;
  private javax.swing.JPanel jPanel27;
  private javax.swing.JPanel jPanel28;
  private javax.swing.JPanel jPanel29;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel30;
  private javax.swing.JPanel jPanel31;
  private javax.swing.JPanel jPanel32;
  private javax.swing.JPanel jPanel4;
  public javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  public javax.swing.JPanel jPanel9;
  private javax.swing.JRadioButton jRadioButton1;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField4;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField6;

}