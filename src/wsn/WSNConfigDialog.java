
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
import javax.swing.JOptionPane;
import y.ylib.ylib;
/**
 *
 * @author Administrator
 */
public class WSNConfigDialog extends infinity.client.CRConfig {
Net n;
Weber w;
String pid;

String propsFile="apps"+File.separator+"cr-wsn"+File.separator+"node_properties.txt";
 Properties props=new Properties();
  TreeMap hostTM=new TreeMap(),nameTM=new TreeMap(),groupTM=new TreeMap(),pwTM=new TreeMap(),memberPWTM=new TreeMap(),myApTM=new TreeMap();
 int historyCount=15;
 String historyFile="apps"+File.separator+"cr-wsn"+File.separator+"history.txt";
 SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 public ResourceBundle bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle");
 boolean changeHistory=false;

  public WSNConfigDialog() {
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
  updateUI();
  this.setModal(true);

  setVisible(true);
}
   public static final Pattern Num_PATTERN= Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
    public static boolean isNumeric(String s){
          return (s==null? false: Num_PATTERN.matcher(s).matches());
    }
void updateUI(){
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
    setTitle(bundle.getString("WSNConfigDialog.title")); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(null);

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    cbConnectTo.setText(bundle.getString("WSNConfigDialog.cbConnectTo.text")); 
    cbConnectTo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cbConnectToActionPerformed(evt);
      }
    });
    jPanel1.add(cbConnectTo);

    jComboBox1.setEditable(true);
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox1.setToolTipText(bundle.getString("WSNConfigDialog.jComboBox1.toolTipText")); 
    jComboBox1.setPreferredSize(new java.awt.Dimension(180, 25));
    jPanel1.add(jComboBox1);

    jButton1.setText(bundle.getString("WSNConfigDialog.jButton1.text")); 
    jButton1.setToolTipText(bundle.getString("WSNConfigDialog.jButton1.toolTipText")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel1.add(jButton1);

    getContentPane().add(jPanel1);
    jPanel1.setBounds(50, 40, 370, 35);

    jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel2.setText(bundle.getString("WSNConfigDialog.jLabel2.text")); 
    jPanel2.add(jLabel2);

    jComboBox2.setEditable(true);
    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox2.setToolTipText(bundle.getString("WSNConfigDialog.jComboBox2.toolTipText")); 
    jComboBox2.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel2.add(jComboBox2);

    jButton2.setText(bundle.getString("WSNConfigDialog.jButton2.text")); 
    jButton2.setToolTipText(bundle.getString("WSNConfigDialog.jButton2.toolTipText")); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton2);

    getContentPane().add(jPanel2);
    jPanel2.setBounds(50, 80, 360, 35);

    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel3.setText(bundle.getString("WSNConfigDialog.jLabel3.text")); 
    jPanel4.add(jLabel3);

    jComboBox3.setEditable(true);
    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox3.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel4.add(jComboBox3);

    jButton3.setText(bundle.getString("WSNConfigDialog.jButton3.text")); 
    jButton3.setToolTipText(bundle.getString("WSNConfigDialog.jButton3.toolTipText")); 
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton3);

    getContentPane().add(jPanel4);
    jPanel4.setBounds(50, 120, 350, 35);

    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel5.setText(bundle.getString("WSNConfigDialog.jLabel5.text")); 
    jPanel6.add(jLabel5);

    jComboBox4.setEditable(true);
    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox4.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel6.add(jComboBox4);

    jButton4.setText(bundle.getString("WSNConfigDialog.jButton4.text")); 
    jButton4.setToolTipText(bundle.getString("WSNConfigDialog.jButton4.toolTipText")); 
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });
    jPanel6.add(jButton4);

    getContentPane().add(jPanel6);
    jPanel6.setBounds(50, 160, 360, 35);

    btnGo.setText(bundle.getString("WSNConfigDialog.btnGo.text")); 
    btnGo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnGoActionPerformed(evt);
      }
    });
    getContentPane().add(btnGo);
    btnGo.setBounds(180, 380, 80, 23);

    btnCancel.setText(bundle.getString("WSNConfigDialog.btnCancel.text")); 
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });
    getContentPane().add(btnCancel);
    btnCancel.setBounds(290, 380, 100, 23);

    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel6.setText(bundle.getString("WSNConfigDialog.jLabel6.text")); 
    jPanel7.add(jLabel6);
    jLabel6.getAccessibleContext().setAccessibleName(bundle.getString("WSNConfigDialog.jLabel6.AccessibleContext.accessibleName")); 

    jComboBox5.setEditable(true);
    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox5.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel7.add(jComboBox5);

    jButton5.setText(bundle.getString("WSNConfigDialog.jButton5.text")); 
    jButton5.setToolTipText(bundle.getString("WSNConfigDialog.jButton5.toolTipText")); 
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton5ActionPerformed(evt);
      }
    });
    jPanel7.add(jButton5);

    getContentPane().add(jPanel7);
    jPanel7.setBounds(50, 200, 370, 35);

    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setText(bundle.getString("WSNConfigDialog.jLabel1.text")); 
    jPanel8.add(jLabel1);

    getContentPane().add(jPanel8);
    jPanel8.setBounds(50, 10, 280, 30);

    btnRestt.setText(bundle.getString("WSNConfigDialog.btnRestt.text")); 
    btnRestt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnResttActionPerformed(evt);
      }
    });
    getContentPane().add(btnRestt);
    btnRestt.setBounds(60, 380, 90, 23);

    jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    cbNoShowConfig.setText(bundle.getString("WSNConfigDialog.cbNoShowConfig.text")); 
    jPanel10.add(cbNoShowConfig);

    getContentPane().add(jPanel10);
    jPanel10.setBounds(50, 320, 360, 40);

    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel4.setText(bundle.getString("WSNConfigDialog.jLabel4.text")); 
    jPanel5.add(jLabel4);

    jComboBox6.setEditable(true);
    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox6.setPreferredSize(new java.awt.Dimension(200, 21));
    jPanel5.add(jComboBox6);

    jButton6.setText(bundle.getString("WSNConfigDialog.jButton6.text")); 
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton6ActionPerformed(evt);
      }
    });
    jPanel5.add(jButton6);

    getContentPane().add(jPanel5);
    jPanel5.setBounds(50, 240, 330, 40);

    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox1.setText(bundle.getString("WSNConfigDialog.jCheckBox1.text")); 
    jCheckBox1.setActionCommand(bundle.getString("WSNConfigDialog.jCheckBox1.actionCommand")); 
    jPanel9.add(jCheckBox1);

    getContentPane().add(jPanel9);
    jPanel9.setBounds(50, 280, 320, 30);

    pack();
  }

  private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {

   System.exit(0);
  }

  private void btnResttActionPerformed(java.awt.event.ActionEvent evt) {
    updateUI();
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
  private javax.swing.JCheckBox cbConnectTo;
  private javax.swing.JCheckBox cbNoShowConfig;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JComboBox jComboBox3;
  private javax.swing.JComboBox jComboBox4;
  private javax.swing.JComboBox jComboBox5;
  private javax.swing.JComboBox jComboBox6;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
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

}