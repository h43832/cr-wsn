package wsn;

import java.awt.*;
import javax.swing.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;
import golib.*;
public class WSNLineChart extends WSNApplication implements Runnable {
  int programId=0,maxPoints=3000;
  long longWaitTime=31536000000L;
  boolean onoff=false,isSleep=false,newValue=false,realtimeMode=true;
  WSNLineIntegraPanel iPanel;
  WSN wsn;
  ResourceBundle bundle2;
  Thread myThread=null;
  public String currentNameId="",currentDataSrcId="",defaultChartFile="apps"+File.separator+"cr-wsn"+File.separator+"linechart",chartFile="",
          allLineName="All Lines",chartConfig[]={"","1"};
  public Config currentConfig=new Config();
  public Status currentStatus=new Status();
  TreeMap datum =new TreeMap(),config=new TreeMap(),status=new TreeMap(),nameToDataSrc=new TreeMap();
  Vector waitV=new Vector();
  DefaultListModel listModel1=new DefaultListModel();

    public WSNLineChart(){
        initComponents();
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    }
public void init(WSN wsn) {

    this.wsn=wsn;
    if(wsn.w.checkOneVar(wsn.statuses[0],0)) {
      programId=-1;
      for(Iterator it=wsn.myAps.values().iterator();it.hasNext();){
        Object o=it.next();
        if(o instanceof WSNLineChart) programId++;
      }
    }
    else programId=wsn.lineCharts.size();
          if(wsn.props.getProperty("chartfile_name_prefix")!=null) chartFile=wsn.props.getProperty("chartfile_name_prefix")+"-"+(programId+1)+".txt";
      else chartFile=defaultChartFile+"-"+(programId+1)+".txt";
    chartFile=wsn.w.fileSeparator(chartFile);

    this.setTitle((wsn.props.getProperty("chartfile_name_prefix")!=null? wsn.props.getProperty("chartfile_name_prefix"):defaultChartFile)+(programId>0? " - "+(programId+1):""));

        iPanel=new WSNLineIntegraPanel(wsn, this);
        jPanel1.add(iPanel, java.awt.BorderLayout.CENTER);
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 95000)/100000;
        int h2=(h * 95000)/100000;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    jComboBox1.removeAllItems();
    listModel1.addElement(allLineName);
    nameToDataSrc.put(allLineName,"0");
    myThread=new Thread(this);
    myThread.start();
    if(wsn.w.getHVar("a_test")==null || !wsn.w.getHVar("a_test").equalsIgnoreCase("true")){

    }
    if(chartFile!=null && chartFile.length()>0){
           readChartFile(chartFile,2);

          if(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y")) toStartAll();
       }
}

    @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    buttonGroup3 = new javax.swing.ButtonGroup();
    buttonGroup4 = new javax.swing.ButtonGroup();
    buttonGroup5 = new javax.swing.ButtonGroup();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel1 = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    addBtn = new javax.swing.JButton();
    removeBtn = new javax.swing.JButton();
    setBtn = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jRadioButton1 = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jTextField4 = new javax.swing.JTextField();
    jLabel19 = new javax.swing.JLabel();
    jCheckBox6 = new javax.swing.JCheckBox();
    jLabel21 = new javax.swing.JLabel();
    jTextField14 = new javax.swing.JTextField();
    jLabel22 = new javax.swing.JLabel();
    jTextField15 = new javax.swing.JTextField();
    jLabel23 = new javax.swing.JLabel();
    jTextField16 = new javax.swing.JTextField();
    jLabel24 = new javax.swing.JLabel();
    jLabel25 = new javax.swing.JLabel();
    jLabel26 = new javax.swing.JLabel();
    jCheckBox7 = new javax.swing.JCheckBox();
    jCheckBox8 = new javax.swing.JCheckBox();
    jTextField17 = new javax.swing.JTextField();
    jLabel27 = new javax.swing.JLabel();
    jCheckBox9 = new javax.swing.JCheckBox();
    jCheckBox11 = new javax.swing.JCheckBox();
    jTextField18 = new javax.swing.JTextField();
    jLabel29 = new javax.swing.JLabel();
    jTextField19 = new javax.swing.JTextField();
    jTextField20 = new javax.swing.JTextField();

    jComboBox3 = new javax.swing.JComboBox();
    jCheckBox14 = new javax.swing.JCheckBox();
    jLabel34 = new javax.swing.JLabel();
    jTextField24 = new javax.swing.JTextField();
    removeAllBtn = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();
    jRadioButton9 = new javax.swing.JRadioButton();
    jLabel37 = new javax.swing.JLabel();
    jTextField27 = new javax.swing.JTextField();
    jCheckBox21 = new javax.swing.JCheckBox();
    jTextField28 = new javax.swing.JTextField();
    jLabel38 = new javax.swing.JLabel();
    jTextField29 = new javax.swing.JTextField();
    jRadioButton10 = new javax.swing.JRadioButton();
    jRadioButton11 = new javax.swing.JRadioButton();
    jLabel39 = new javax.swing.JLabel();
    jTextField30 = new javax.swing.JTextField();
    jLabel40 = new javax.swing.JLabel();
    jTextField31 = new javax.swing.JTextField();
    jRadioButton12 = new javax.swing.JRadioButton();
    jCheckBox22 = new javax.swing.JCheckBox();
    jTextField32 = new javax.swing.JTextField();
    jLabel41 = new javax.swing.JLabel();
    jTextField33 = new javax.swing.JTextField();
    jPanel4 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jLabel36 = new javax.swing.JLabel();
    jTextField26 = new javax.swing.JTextField();
    jButton5 = new javax.swing.JButton();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jTextField5 = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    jTextField6 = new javax.swing.JTextField();
    jCheckBox12 = new javax.swing.JCheckBox();
    jCheckBox20 = new javax.swing.JCheckBox();
    jLabel14 = new javax.swing.JLabel();
    jRadioButton5 = new javax.swing.JRadioButton();
    jTextField10 = new javax.swing.JTextField();
    jLabel15 = new javax.swing.JLabel();
    jRadioButton6 = new javax.swing.JRadioButton();
    jTextField11 = new javax.swing.JTextField();
    jLabel16 = new javax.swing.JLabel();
    jLabel17 = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();
    jLabel20 = new javax.swing.JLabel();
    jRadioButton7 = new javax.swing.JRadioButton();
    jRadioButton8 = new javax.swing.JRadioButton();
    jTextField12 = new javax.swing.JTextField();
    jTextField13 = new javax.swing.JTextField();
    jLabel10 = new javax.swing.JLabel();
    jButton6 = new javax.swing.JButton();
    jButton7 = new javax.swing.JButton();
    jButton8 = new javax.swing.JButton();
    jButton9 = new javax.swing.JButton();
    jButton10 = new javax.swing.JButton();
    jLabel47 = new javax.swing.JLabel();
    jPanel15 = new javax.swing.JPanel();
    jCheckBox2 = new javax.swing.JCheckBox();
    jLabel11 = new javax.swing.JLabel();
    jTextField7 = new javax.swing.JTextField();
    jCheckBox3 = new javax.swing.JCheckBox();
    jLabel12 = new javax.swing.JLabel();
    jTextField8 = new javax.swing.JTextField();
    jPanel16 = new javax.swing.JPanel();
    jCheckBox30 = new javax.swing.JCheckBox();
    jLabel48 = new javax.swing.JLabel();
    jTextField38 = new javax.swing.JTextField();
    jCheckBox31 = new javax.swing.JCheckBox();
    jLabel49 = new javax.swing.JLabel();
    jTextField39 = new javax.swing.JTextField();
    jPanel17 = new javax.swing.JPanel();
    jCheckBox19 = new javax.swing.JCheckBox();
    jCheckBox1 = new javax.swing.JCheckBox();
    jPanel18 = new javax.swing.JPanel();
    jCheckBox5 = new javax.swing.JCheckBox();
    jLabel35 = new javax.swing.JLabel();
    jTextField25 = new javax.swing.JTextField();
    jPanel19 = new javax.swing.JPanel();
    jLabel30 = new javax.swing.JLabel();
    Color[] colors={Color.white,Color.red,Color.blue,Color.green,Color.BLACK,Color.CYAN,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    jComboBox2 = new javax.swing.JComboBox();
    jPanel10 = new javax.swing.JPanel();
    jLabel28 = new javax.swing.JLabel();
    jPanel6 = new javax.swing.JPanel();
    jCheckBox4 = new javax.swing.JCheckBox();
    jTextField21 = new javax.swing.JTextField();
    jLabel32 = new javax.swing.JLabel();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jLabel13 = new javax.swing.JLabel();
    jTextField9 = new javax.swing.JTextField();
    jCheckBox17 = new javax.swing.JCheckBox();
    jPanel9 = new javax.swing.JPanel();
    jCheckBox18 = new javax.swing.JCheckBox();
    jComboBox4 = new javax.swing.JComboBox();
    jPanel7 = new javax.swing.JPanel();
    jCheckBox10 = new javax.swing.JCheckBox();
    jLabel31 = new javax.swing.JLabel();
    jTextField22 = new javax.swing.JTextField();
    jCheckBox15 = new javax.swing.JCheckBox();
    jPanel8 = new javax.swing.JPanel();
    jCheckBox13 = new javax.swing.JCheckBox();
    jLabel33 = new javax.swing.JLabel();
    jTextField23 = new javax.swing.JTextField();
    jCheckBox16 = new javax.swing.JCheckBox();
    jPanel11 = new javax.swing.JPanel();
    jCheckBox23 = new javax.swing.JCheckBox();
    jLabel42 = new javax.swing.JLabel();
    jTextField34 = new javax.swing.JTextField();
    jCheckBox24 = new javax.swing.JCheckBox();
    jLabel43 = new javax.swing.JLabel();
    jPanel12 = new javax.swing.JPanel();
    jCheckBox25 = new javax.swing.JCheckBox();
    jComboBox5 = new javax.swing.JComboBox();
    jPanel13 = new javax.swing.JPanel();
    jCheckBox26 = new javax.swing.JCheckBox();
    jLabel44 = new javax.swing.JLabel();
    jTextField35 = new javax.swing.JTextField();
    jCheckBox27 = new javax.swing.JCheckBox();
    jPanel14 = new javax.swing.JPanel();
    jCheckBox28 = new javax.swing.JCheckBox();
    jTextField36 = new javax.swing.JTextField();
    jLabel45 = new javax.swing.JLabel();
    jRadioButton13 = new javax.swing.JRadioButton();
    jRadioButton14 = new javax.swing.JRadioButton();
    jLabel46 = new javax.swing.JLabel();
    jTextField37 = new javax.swing.JTextField();
    jCheckBox29 = new javax.swing.JCheckBox();
    jButton13 = new javax.swing.JButton();
    jButton12 = new javax.swing.JButton();
    jButton15 = new javax.swing.JButton();
    jButton14 = new javax.swing.JButton();
    jButton11 = new javax.swing.JButton();
    jButton16 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem3 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSNLineChart.title")); 
    setName("Form"); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jTabbedPane1.setName("jTabbedPane1"); 

    jPanel1.setName("jPanel1"); 
    jPanel1.setLayout(new java.awt.BorderLayout());
    jTabbedPane1.addTab(bundle.getString("WSNLineChart.jPanel1.TabConstraints.tabTitle"), jPanel1); 

    jPanel2.setName("jPanel2"); 
    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel3.setName("jPanel3"); 
    jPanel3.setLayout(null);

    addBtn.setText(bundle.getString("WSNLineChart.addBtn.text")); 
    addBtn.setName("addBtn"); 
    addBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addBtnActionPerformed(evt);
      }
    });
    jPanel3.add(addBtn);
    addBtn.setBounds(20, 19, 130, 30);

    removeBtn.setText(bundle.getString("WSNLineChart.removeBtn.text")); 
    removeBtn.setName("removeBtn"); 
    removeBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeBtnActionPerformed(evt);
      }
    });
    jPanel3.add(removeBtn);
    removeBtn.setBounds(160, 20, 90, 30);

    setBtn.setText(bundle.getString("WSNLineChart.setBtn.text")); 
    setBtn.setName("setBtn"); 
    setBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        setBtnActionPerformed(evt);
      }
    });
    jPanel3.add(setBtn);
    setBtn.setBounds(260, 20, 70, 30);

    jLabel1.setText(bundle.getString("WSNLineChart.jLabel1.text")); 
    jLabel1.setName("jLabel1"); 
    jPanel3.add(jLabel1);
    jLabel1.setBounds(30, 60, 70, 15);

    jTextField1.setName("jTextField1"); 
    jPanel3.add(jTextField1);
    jTextField1.setBounds(110, 60, 130, 21);

    jLabel2.setText(bundle.getString("WSNLineChart.jLabel2.text")); 
    jLabel2.setName("jLabel2"); 
    jPanel3.add(jLabel2);
    jLabel2.setBounds(550, 60, 80, 20);

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox1.setName("jComboBox1"); 
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox1ActionPerformed(evt);
      }
    });
    jPanel3.add(jComboBox1);
    jComboBox1.setBounds(630, 80, 150, 21);

    buttonGroup1.add(jRadioButton1);
    jRadioButton1.setText(bundle.getString("WSNLineChart.jRadioButton1.text")); 
    jRadioButton1.setName("jRadioButton1"); 
    jPanel3.add(jRadioButton1);
    jRadioButton1.setBounds(200, 100, 90, 23);

    buttonGroup1.add(jRadioButton2);
    jRadioButton2.setText(bundle.getString("WSNLineChart.jRadioButton2.text")); 
    jRadioButton2.setName("jRadioButton2"); 
    jPanel3.add(jRadioButton2);
    jRadioButton2.setBounds(200, 140, 80, 23);

    jLabel3.setText(bundle.getString("WSNLineChart.jLabel3.text")); 
    jLabel3.setName("jLabel3"); 
    jPanel3.add(jLabel3);
    jLabel3.setBounds(30, 100, 70, 20);

    jLabel4.setText(bundle.getString("WSNLineChart.jLabel4.text")); 
    jLabel4.setName("jLabel4"); 
    jPanel3.add(jLabel4);
    jLabel4.setBounds(290, 100, 80, 20);

    jTextField2.setToolTipText(bundle.getString("WSNLineChart.jTextField2.toolTipText")); 
    jTextField2.setName("jTextField2"); 
    jPanel3.add(jTextField2);
    jTextField2.setBounds(370, 100, 50, 21);

    jLabel5.setText(bundle.getString("WSNLineChart.jLabel5.text")); 
    jLabel5.setName("jLabel5"); 
    jPanel3.add(jLabel5);
    jLabel5.setBounds(430, 100, 30, 15);

    jTextField3.setToolTipText(bundle.getString("WSNLineChart.jTextField3.toolTipText")); 
    jTextField3.setName("jTextField3"); 
    jPanel3.add(jTextField3);
    jTextField3.setBounds(470, 100, 50, 21);

    jLabel6.setText(bundle.getString("WSNLineChart.jLabel6.text")); 
    jLabel6.setName("jLabel6"); 
    jPanel3.add(jLabel6);
    jLabel6.setBounds(430, 170, 110, 20);

    jTextField4.setToolTipText(bundle.getString("WSNLineChart.jTextField4.toolTipText")); 
    jTextField4.setName("jTextField4"); 
    jPanel3.add(jTextField4);
    jTextField4.setBounds(540, 170, 40, 21);

    jLabel19.setText(bundle.getString("WSNLineChart.jLabel19.text")); 
    jLabel19.setName("jLabel19"); 
    jPanel3.add(jLabel19);
    jLabel19.setBounds(270, 60, 80, 20);

    jCheckBox6.setText(bundle.getString("WSNLineChart.jCheckBox6.text")); 
    jCheckBox6.setName("jCheckBox6"); 
    jPanel3.add(jCheckBox6);
    jCheckBox6.setBounds(120, 300, 160, 30);

    jLabel21.setText(bundle.getString("WSNLineChart.jLabel21.text")); 
    jLabel21.setName("jLabel21"); 
    jPanel3.add(jLabel21);
    jLabel21.setBounds(280, 300, 80, 20);

    jTextField14.setName("jTextField14"); 
    jPanel3.add(jTextField14);
    jTextField14.setBounds(370, 300, 40, 30);

    jLabel22.setText(bundle.getString("WSNLineChart.jLabel22.text")); 
    jLabel22.setName("jLabel22"); 
    jPanel3.add(jLabel22);
    jLabel22.setBounds(420, 300, 40, 20);

    jTextField15.setName("jTextField15"); 
    jPanel3.add(jTextField15);
    jTextField15.setBounds(460, 300, 40, 30);

    jLabel23.setText(bundle.getString("WSNLineChart.jLabel23.text")); 
    jLabel23.setName("jLabel23"); 
    jPanel3.add(jLabel23);
    jLabel23.setBounds(510, 300, 30, 20);

    jTextField16.setName("jTextField16"); 
    jPanel3.add(jTextField16);
    jTextField16.setBounds(550, 300, 40, 30);

    jLabel24.setText(bundle.getString("WSNLineChart.jLabel24.text")); 
    jLabel24.setName("jLabel24"); 
    jPanel3.add(jLabel24);
    jLabel24.setBounds(430, 290, 20, 15);

    jLabel25.setText(bundle.getString("WSNLineChart.jLabel25.text")); 
    jLabel25.setName("jLabel25"); 
    jPanel3.add(jLabel25);
    jLabel25.setBounds(620, 300, 140, 20);

    jLabel26.setText(bundle.getString("WSNLineChart.jLabel26.text")); 
    jLabel26.setName("jLabel26"); 
    jPanel3.add(jLabel26);
    jLabel26.setBounds(120, 100, 80, 20);

    jCheckBox7.setText(bundle.getString("WSNLineChart.jCheckBox7.text")); 
    jCheckBox7.setName("jCheckBox7"); 
    jPanel3.add(jCheckBox7);
    jCheckBox7.setBounds(120, 380, 130, 23);

    jCheckBox8.setText(bundle.getString("WSNLineChart.jCheckBox8.text")); 
    jCheckBox8.setName("jCheckBox8"); 
    jPanel3.add(jCheckBox8);
    jCheckBox8.setBounds(120, 330, 130, 23);

    jTextField17.setText(bundle.getString("WSNLineChart.jTextField17.text")); 
    jTextField17.setName("jTextField17"); 
    jPanel3.add(jTextField17);
    jTextField17.setBounds(250, 330, 30, 21);

    jLabel27.setText(bundle.getString("WSNLineChart.jLabel27.text")); 
    jLabel27.setName("jLabel27"); 
    jPanel3.add(jLabel27);
    jLabel27.setBounds(290, 330, 110, 20);

    jCheckBox9.setText(bundle.getString("WSNLineChart.jCheckBox9.text")); 
    jCheckBox9.setName("jCheckBox9"); 
    jPanel3.add(jCheckBox9);
    jCheckBox9.setBounds(120, 350, 120, 30);

    jCheckBox11.setText(bundle.getString("WSNLineChart.jCheckBox11.text")); 
    jCheckBox11.setName("jCheckBox11"); 
    jPanel3.add(jCheckBox11);
    jCheckBox11.setBounds(600, 170, 130, 23);

    jTextField18.setToolTipText(bundle.getString("WSNLineChart.jTextField18.toolTipText")); 
    jTextField18.setName("jTextField18"); 
    jPanel3.add(jTextField18);
    jTextField18.setBounds(730, 170, 30, 21);

    jLabel29.setText(bundle.getString("WSNLineChart.jLabel29.text")); 
    jLabel29.setName("jLabel29"); 
    jPanel3.add(jLabel29);
    jLabel29.setBounds(770, 170, 40, 20);

    jTextField19.setToolTipText(bundle.getString("WSNLineChart.jTextField19.toolTipText")); 
    jTextField19.setName("jTextField19"); 
    jPanel3.add(jTextField19);
    jTextField19.setBounds(810, 170, 30, 21);

    jTextField20.setToolTipText(bundle.getString("WSNLineChart.jTextField20.toolTipText")); 
    jTextField20.setName("jTextField20"); 
    jPanel3.add(jTextField20);
    jTextField20.setBounds(630, 60, 150, 21);

    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(colors));
    jComboBox3.setName("jComboBox3"); 
    jComboBox3.setRenderer(new MyCellRenderer());
    jPanel3.add(jComboBox3);
    jComboBox3.setBounds(360, 60, 130, 21);

    jCheckBox14.setFont(jCheckBox14.getFont());
    jCheckBox14.setText(bundle.getString("WSNLineChart.jCheckBox14.text")); 
    jCheckBox14.setName("jCheckBox14"); 
    jCheckBox14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox14ActionPerformed(evt);
      }
    });
    jPanel3.add(jCheckBox14);
    jCheckBox14.setBounds(120, 270, 180, 23);

    jLabel34.setText(bundle.getString("WSNLineChart.jLabel34.text")); 
    jLabel34.setName("jLabel34"); 
    jPanel3.add(jLabel34);
    jLabel34.setBounds(310, 270, 80, 20);

    jTextField24.setFont(jTextField24.getFont());
    jTextField24.setText(bundle.getString("WSNLineChart.jTextField24.text_1")); 
    jTextField24.setToolTipText(bundle.getString("WSNLineChart.jTextField24.text_2")); 
    jTextField24.setName("jTextField24"); 
    jTextField24.setPreferredSize(new java.awt.Dimension(127, 25));
    jPanel3.add(jTextField24);
    jTextField24.setBounds(390, 260, 150, 30);

    removeAllBtn.setText(bundle.getString("WSNLineChart.removeAllBtn.text")); 
    removeAllBtn.setName("removeAllBtn"); 
    removeAllBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeAllBtnActionPerformed(evt);
      }
    });
    jPanel3.add(removeAllBtn);
    removeAllBtn.setBounds(340, 20, 120, 30);

    jButton1.setFont(jButton1.getFont());
    jButton1.setText(bundle.getString("WSNLineChart.jButton1.text")); 
    jButton1.setName("jButton1"); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton1);
    jButton1.setBounds(470, 20, 90, 30);

    jButton2.setFont(jButton2.getFont());
    jButton2.setText(bundle.getString("WSNLineChart.jButton2.text")); 
    jButton2.setName("jButton2"); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton2);
    jButton2.setBounds(570, 20, 90, 30);

    jButton3.setFont(jButton3.getFont());
    jButton3.setText(bundle.getString("WSNLineChart.jButton3.text")); 
    jButton3.setName("jButton3"); 
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton3ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton3);
    jButton3.setBounds(670, 20, 100, 30);

    jButton4.setFont(jButton4.getFont());
    jButton4.setText(bundle.getString("WSNLineChart.jButton4.text")); 
    jButton4.setName("jButton4"); 
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton4);
    jButton4.setBounds(780, 20, 100, 30);

    buttonGroup5.add(jRadioButton9);
    jRadioButton9.setText(bundle.getString("WSNLineChart.jRadioButton9.text")); 
    jRadioButton9.setToolTipText(bundle.getString("WSNLineChart.jRadioButton9.toolTipText")); 
    jRadioButton9.setName("jRadioButton9"); 
    jPanel3.add(jRadioButton9);
    jRadioButton9.setBounds(290, 200, 130, 23);

    jLabel37.setText(bundle.getString("WSNLineChart.jLabel37.text")); 
    jLabel37.setName("jLabel37"); 
    jPanel3.add(jLabel37);
    jLabel37.setBounds(430, 200, 110, 20);

    jTextField27.setToolTipText(bundle.getString("WSNLineChart.jTextField27.toolTipText")); 
    jTextField27.setName("jTextField27"); 
    jPanel3.add(jTextField27);
    jTextField27.setBounds(540, 200, 40, 21);

    jCheckBox21.setText(bundle.getString("WSNLineChart.jCheckBox21.text")); 
    jCheckBox21.setName("jCheckBox21"); 
    jPanel3.add(jCheckBox21);
    jCheckBox21.setBounds(600, 200, 130, 23);

    jTextField28.setToolTipText(bundle.getString("WSNLineChart.jTextField28.toolTipText")); 
    jTextField28.setName("jTextField28"); 
    jPanel3.add(jTextField28);
    jTextField28.setBounds(730, 200, 30, 21);

    jLabel38.setText(bundle.getString("WSNLineChart.jLabel38.text")); 
    jLabel38.setName("jLabel38"); 
    jPanel3.add(jLabel38);
    jLabel38.setBounds(770, 200, 40, 20);

    jTextField29.setToolTipText(bundle.getString("WSNLineChart.jTextField29.toolTipText")); 
    jTextField29.setName("jTextField29"); 
    jPanel3.add(jTextField29);
    jTextField29.setBounds(810, 200, 30, 21);

    buttonGroup5.add(jRadioButton10);
    jRadioButton10.setText(bundle.getString("WSNLineChart.jRadioButton10.text")); 
    jRadioButton10.setToolTipText(bundle.getString("WSNLineChart.jRadioButton10.toolTipText")); 
    jRadioButton10.setName("jRadioButton10"); 
    jPanel3.add(jRadioButton10);
    jRadioButton10.setBounds(290, 170, 140, 23);

    buttonGroup5.add(jRadioButton11);
    jRadioButton11.setText(bundle.getString("WSNLineChart.jRadioButton11.text")); 
    jRadioButton11.setName("jRadioButton11"); 
    jPanel3.add(jRadioButton11);
    jRadioButton11.setBounds(290, 230, 180, 23);

    jLabel39.setText(bundle.getString("WSNLineChart.jLabel39.text")); 
    jLabel39.setName("jLabel39"); 
    jPanel3.add(jLabel39);
    jLabel39.setBounds(480, 230, 130, 20);

    jTextField30.setText(bundle.getString("WSNLineChart.jTextField30.text")); 
    jTextField30.setName("jTextField30"); 
    jTextField30.setPreferredSize(new java.awt.Dimension(6, 25));
    jPanel3.add(jTextField30);
    jTextField30.setBounds(610, 230, 50, 25);

    jLabel40.setText(bundle.getString("WSNLineChart.jLabel40.text")); 
    jLabel40.setName("jLabel40"); 
    jPanel3.add(jLabel40);
    jLabel40.setBounds(680, 230, 120, 20);

    jTextField31.setText(bundle.getString("WSNLineChart.jTextField31.text")); 
    jTextField31.setToolTipText(bundle.getString("WSNLineChart.jTextField31.toolTipText")); 
    jTextField31.setName("jTextField31"); 
    jTextField31.setPreferredSize(new java.awt.Dimension(65, 25));
    jPanel3.add(jTextField31);
    jTextField31.setBounds(800, 230, 40, 25);

    buttonGroup5.add(jRadioButton12);
    jRadioButton12.setSelected(true);
    jRadioButton12.setText(bundle.getString("WSNLineChart.jRadioButton12.text")); 
    jRadioButton12.setName("jRadioButton12"); 
    jPanel3.add(jRadioButton12);
    jRadioButton12.setBounds(290, 140, 150, 23);

    jCheckBox22.setText(bundle.getString("WSNLineChart.jCheckBox22.text")); 
    jCheckBox22.setName("jCheckBox22"); 
    jPanel3.add(jCheckBox22);
    jCheckBox22.setBounds(440, 140, 130, 23);

    jTextField32.setText(bundle.getString("WSNLineChart.jTextField32.text")); 
    jTextField32.setToolTipText(bundle.getString("WSNLineChart.jTextField32.toolTipText")); 
    jTextField32.setName("jTextField32"); 
    jTextField32.setPreferredSize(new java.awt.Dimension(25, 25));
    jPanel3.add(jTextField32);
    jTextField32.setBounds(590, 140, 30, 25);

    jLabel41.setText(bundle.getString("WSNLineChart.jLabel41.text")); 
    jLabel41.setName("jLabel41"); 
    jPanel3.add(jLabel41);
    jLabel41.setBounds(630, 140, 40, 20);

    jTextField33.setText(bundle.getString("WSNLineChart.jTextField33.text")); 
    jTextField33.setToolTipText(bundle.getString("WSNLineChart.jTextField33.toolTipText")); 
    jTextField33.setName("jTextField33"); 
    jTextField33.setPreferredSize(new java.awt.Dimension(25, 25));
    jPanel3.add(jTextField33);
    jTextField33.setBounds(680, 140, 30, 25);

    jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

    jTabbedPane1.addTab(bundle.getString("WSNLineChart.jPanel2.TabConstraints.tabTitle"), jPanel2); 

    jPanel4.setName("jPanel4"); 
    jPanel4.setLayout(null);

    jPanel5.setName("jPanel5"); 
    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel36.setText(bundle.getString("WSNLineChart.jLabel36.text")); 
    jLabel36.setName("jLabel36"); 
    jPanel5.add(jLabel36);

    jTextField26.setText(bundle.getString("WSNLineChart.jTextField26.text")); 
    jTextField26.setName("jTextField26"); 
    jTextField26.setPreferredSize(new java.awt.Dimension(200, 25));
    jPanel5.add(jTextField26);

    jPanel4.add(jPanel5);
    jPanel5.setBounds(10, 60, 320, 40);

    jButton5.setText(bundle.getString("WSNLineChart.jButton5.text")); 
    jButton5.setName("jButton5"); 
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton5ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton5);
    jButton5.setBounds(350, 20, 100, 30);

    jLabel7.setText(bundle.getString("WSNLineChart.jLabel7.text")); 
    jLabel7.setName("jLabel7"); 
    jPanel4.add(jLabel7);
    jLabel7.setBounds(20, 170, 50, 15);

    jLabel8.setText(bundle.getString("WSNLineChart.jLabel8.text")); 
    jLabel8.setName("jLabel8"); 
    jPanel4.add(jLabel8);
    jLabel8.setBounds(90, 170, 110, 15);

    jTextField5.setText(bundle.getString("WSNLineChart.jTextField5.text")); 
    jTextField5.setName("jTextField5"); 
    jPanel4.add(jTextField5);
    jTextField5.setBounds(200, 170, 70, 21);

    jLabel9.setText(bundle.getString("WSNLineChart.jLabel9.text")); 
    jLabel9.setName("jLabel9"); 
    jPanel4.add(jLabel9);
    jLabel9.setBounds(280, 170, 150, 20);

    jTextField6.setText(bundle.getString("WSNLineChart.jTextField6.text")); 
    jTextField6.setName("jTextField6"); 
    jPanel4.add(jTextField6);
    jTextField6.setBounds(440, 170, 70, 21);

    jCheckBox12.setText(bundle.getString("WSNLineChart.jCheckBox12.text")); 
    jCheckBox12.setName("jCheckBox12"); 
    jPanel4.add(jCheckBox12);
    jCheckBox12.setBounds(510, 170, 260, 23);

    jCheckBox20.setText(bundle.getString("WSNLineChart.jCheckBox20.text")); 
    jCheckBox20.setName("jCheckBox20"); 
    jPanel4.add(jCheckBox20);
    jCheckBox20.setBounds(260, 190, 170, 23);

    jLabel14.setText(bundle.getString("WSNLineChart.jLabel14.text")); 
    jLabel14.setName("jLabel14"); 
    jPanel4.add(jLabel14);
    jLabel14.setBounds(90, 220, 120, 15);

    buttonGroup3.add(jRadioButton5);
    jRadioButton5.setText(bundle.getString("WSNLineChart.jRadioButton5.text")); 
    jRadioButton5.setName("jRadioButton5"); 
    jPanel4.add(jRadioButton5);
    jRadioButton5.setBounds(210, 220, 120, 23);

    jTextField10.setText(bundle.getString("WSNLineChart.jTextField10.text")); 
    jTextField10.setName("jTextField10"); 
    jPanel4.add(jTextField10);
    jTextField10.setBounds(350, 220, 50, 21);

    jLabel15.setText(bundle.getString("WSNLineChart.jLabel15.text")); 
    jLabel15.setName("jLabel15"); 
    jPanel4.add(jLabel15);
    jLabel15.setBounds(420, 220, 60, 20);

    buttonGroup3.add(jRadioButton6);
    jRadioButton6.setText(bundle.getString("WSNLineChart.jRadioButton6.text")); 
    jRadioButton6.setName("jRadioButton6"); 
    jPanel4.add(jRadioButton6);
    jRadioButton6.setBounds(210, 250, 110, 23);

    jTextField11.setText(bundle.getString("WSNLineChart.jTextField11.text")); 
    jTextField11.setName("jTextField11"); 
    jPanel4.add(jTextField11);
    jTextField11.setBounds(350, 250, 40, 21);

    jLabel16.setText(bundle.getString("WSNLineChart.jLabel16.text")); 
    jLabel16.setName("jLabel16"); 
    jPanel4.add(jLabel16);
    jLabel16.setBounds(400, 250, 40, 15);

    jLabel17.setText(bundle.getString("WSNLineChart.jLabel17.text")); 
    jLabel17.setName("jLabel17"); 
    jPanel4.add(jLabel17);
    jLabel17.setBounds(480, 250, 30, 15);

    jLabel18.setText(bundle.getString("WSNLineChart.jLabel18.text")); 
    jLabel18.setName("jLabel18"); 
    jPanel4.add(jLabel18);
    jLabel18.setBounds(550, 250, 40, 20);

    jLabel20.setText(bundle.getString("WSNLineChart.jLabel20.text")); 
    jLabel20.setName("jLabel20"); 
    jPanel4.add(jLabel20);
    jLabel20.setBounds(90, 280, 200, 20);

    buttonGroup4.add(jRadioButton7);
    jRadioButton7.setText(bundle.getString("WSNLineChart.jRadioButton7.text")); 
    jRadioButton7.setName("jRadioButton7"); 
    jPanel4.add(jRadioButton7);
    jRadioButton7.setBounds(290, 280, 120, 23);

    buttonGroup4.add(jRadioButton8);
    jRadioButton8.setText(bundle.getString("WSNLineChart.jRadioButton8.text")); 
    jRadioButton8.setName("jRadioButton8"); 
    jPanel4.add(jRadioButton8);
    jRadioButton8.setBounds(420, 280, 100, 23);

    jTextField12.setText(bundle.getString("WSNLineChart.jTextField12.text")); 
    jTextField12.setName("jTextField12"); 
    jPanel4.add(jTextField12);
    jTextField12.setBounds(440, 250, 30, 21);

    jTextField13.setText(bundle.getString("WSNLineChart.jTextField13.text")); 
    jTextField13.setName("jTextField13"); 
    jPanel4.add(jTextField13);
    jTextField13.setBounds(510, 250, 30, 21);

    jLabel10.setText(bundle.getString("WSNLineChart.jLabel10.text")); 
    jLabel10.setName("jLabel10"); 
    jPanel4.add(jLabel10);
    jLabel10.setBounds(20, 320, 70, 30);

    jButton6.setFont(jButton6.getFont());
    jButton6.setText(bundle.getString("WSNLineChart.jButton6.text")); 
    jButton6.setName("jButton6"); 
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton6ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton6);
    jButton6.setBounds(780, 20, 100, 30);

    jButton7.setFont(jButton7.getFont());
    jButton7.setText(bundle.getString("WSNLineChart.jButton7.text")); 
    jButton7.setName("jButton7"); 
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton7ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton7);
    jButton7.setBounds(470, 20, 90, 30);

    jButton8.setFont(jButton8.getFont());
    jButton8.setText(bundle.getString("WSNLineChart.jButton8.text")); 
    jButton8.setName("jButton8"); 
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton8ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton8);
    jButton8.setBounds(570, 20, 90, 30);

    jButton9.setFont(jButton9.getFont());
    jButton9.setText(bundle.getString("WSNLineChart.jButton9.text")); 
    jButton9.setName("jButton9"); 
    jButton9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton9ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton9);
    jButton9.setBounds(670, 20, 100, 30);

    jButton10.setText(bundle.getString("WSNLineChart.jButton10.text")); 
    jButton10.setName("jButton10"); 
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton10ActionPerformed(evt);
      }
    });
    jPanel4.add(jButton10);
    jButton10.setBounds(220, 20, 110, 30);

    jLabel47.setText(bundle.getString("WSNLineChart.jLabel47.text")); 
    jLabel47.setName("jLabel47"); 
    jPanel4.add(jLabel47);
    jLabel47.setBounds(20, 410, 80, 30);

    jPanel15.setName("jPanel15"); 
    jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox2.setText(bundle.getString("WSNLineChart.jCheckBox2.text")); 
    jCheckBox2.setName("jCheckBox2"); 
    jPanel15.add(jCheckBox2);

    jLabel11.setText(bundle.getString("WSNLineChart.jLabel11.text")); 
    jLabel11.setName("jLabel11"); 
    jPanel15.add(jLabel11);

    jTextField7.setName("jTextField7"); 
    jTextField7.setPreferredSize(new java.awt.Dimension(50, 25));
    jPanel15.add(jTextField7);

    jCheckBox3.setText(bundle.getString("WSNLineChart.jCheckBox3.text")); 
    jCheckBox3.setName("jCheckBox3"); 
    jPanel15.add(jCheckBox3);

    jLabel12.setText(bundle.getString("WSNLineChart.jLabel12.text")); 
    jLabel12.setName("jLabel12"); 
    jPanel15.add(jLabel12);

    jTextField8.setName("jTextField8"); 
    jTextField8.setPreferredSize(new java.awt.Dimension(50, 25));
    jPanel15.add(jTextField8);

    jPanel4.add(jPanel15);
    jPanel15.setBounds(100, 320, 430, 40);

    jPanel16.setName("jPanel16"); 
    jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox30.setText(bundle.getString("WSNLineChart.jCheckBox30.text")); 
    jCheckBox30.setName("jCheckBox30"); 
    jPanel16.add(jCheckBox30);

    jLabel48.setText(bundle.getString("WSNLineChart.jLabel48.text")); 
    jLabel48.setName("jLabel48"); 
    jPanel16.add(jLabel48);

    jTextField38.setName("jTextField38"); 
    jTextField38.setPreferredSize(new java.awt.Dimension(50, 25));
    jPanel16.add(jTextField38);

    jCheckBox31.setText(bundle.getString("WSNLineChart.jCheckBox31.text")); 
    jCheckBox31.setName("jCheckBox31"); 
    jPanel16.add(jCheckBox31);

    jLabel49.setText(bundle.getString("WSNLineChart.jLabel49.text")); 
    jLabel49.setName("jLabel49"); 
    jPanel16.add(jLabel49);

    jTextField39.setName("jTextField39"); 
    jTextField39.setPreferredSize(new java.awt.Dimension(50, 25));
    jPanel16.add(jTextField39);

    jPanel4.add(jPanel16);
    jPanel16.setBounds(100, 360, 430, 40);

    jPanel17.setName("jPanel17"); 
    jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox19.setText(bundle.getString("WSNLineChart.jCheckBox19.text")); 
    jCheckBox19.setName("jCheckBox19"); 
    jCheckBox19.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox19ActionPerformed(evt);
      }
    });
    jPanel17.add(jCheckBox19);

    jCheckBox1.setText(bundle.getString("WSNLineChart.jCheckBox1.text")); 
    jCheckBox1.setName("jCheckBox1"); 
    jPanel17.add(jCheckBox1);

    jPanel4.add(jPanel17);
    jPanel17.setBounds(100, 410, 550, 40);

    jPanel18.setName("jPanel18"); 
    jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox5.setText(bundle.getString("WSNLineChart.jCheckBox5.text")); 
    jCheckBox5.setName("jCheckBox5"); 
    jPanel18.add(jCheckBox5);

    jLabel35.setText(bundle.getString("WSNLineChart.jLabel35.text")); 
    jLabel35.setName("jLabel35"); 
    jPanel18.add(jLabel35);

    jTextField25.setName("jTextField25"); 
    jTextField25.setPreferredSize(new java.awt.Dimension(100, 25));
    jPanel18.add(jTextField25);

    jPanel4.add(jPanel18);
    jPanel18.setBounds(100, 450, 320, 40);

    jPanel19.setName("jPanel19"); 
    jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel30.setText(bundle.getString("WSNLineChart.jLabel30.text")); 
    jLabel30.setName("jLabel30"); 
    jPanel19.add(jLabel30);

    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(colors));
    jComboBox2.setName("jComboBox2"); 
    jComboBox2.setRenderer(new MyCellRenderer());
    jPanel19.add(jComboBox2);

    jPanel4.add(jPanel19);
    jPanel19.setBounds(330, 60, 280, 40);

    jTabbedPane1.addTab(bundle.getString("WSNLineChart.jPanel4.TabConstraints.tabTitle"), jPanel4); 

    jPanel10.setName("jPanel10"); 
    jPanel10.setLayout(null);

    jLabel28.setText(bundle.getString("WSNLineChart.jLabel28.text")); 
    jLabel28.setName("jLabel28"); 
    jPanel10.add(jLabel28);
    jLabel28.setBounds(40, 50, 200, 20);

    jPanel6.setName("jPanel6"); 
    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox4.setText(bundle.getString("WSNLineChart.jCheckBox4.text")); 
    jCheckBox4.setName("jCheckBox4"); 
    jPanel6.add(jCheckBox4);

    jTextField21.setToolTipText(bundle.getString("WSNLineChart.jTextField21.toolTipText")); 
    jTextField21.setName("jTextField21"); 
    jTextField21.setPreferredSize(new java.awt.Dimension(100, 25));
    jPanel6.add(jTextField21);

    jLabel32.setText(bundle.getString("WSNLineChart.jLabel32.text")); 
    jLabel32.setName("jLabel32"); 
    jPanel6.add(jLabel32);

    buttonGroup2.add(jRadioButton3);
    jRadioButton3.setText(bundle.getString("WSNLineChart.jRadioButton3.text")); 
    jRadioButton3.setName("jRadioButton3"); 
    jPanel6.add(jRadioButton3);

    buttonGroup2.add(jRadioButton4);
    jRadioButton4.setText(bundle.getString("WSNLineChart.jRadioButton4.text")); 
    jRadioButton4.setName("jRadioButton4"); 
    jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton4ActionPerformed(evt);
      }
    });
    jPanel6.add(jRadioButton4);

    jLabel13.setText(bundle.getString("WSNLineChart.jLabel13.text")); 
    jLabel13.setName("jLabel13"); 
    jPanel6.add(jLabel13);

    jTextField9.setName("jTextField9"); 
    jTextField9.setPreferredSize(new java.awt.Dimension(100, 25));
    jPanel6.add(jTextField9);

    jCheckBox17.setText(bundle.getString("WSNLineChart.jCheckBox17.text")); 
    jCheckBox17.setName("jCheckBox17"); 
    jPanel6.add(jCheckBox17);

    jPanel10.add(jPanel6);
    jPanel6.setBounds(50, 70, 790, 35);

    jPanel9.setName("jPanel9"); 
    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox18.setText(bundle.getString("WSNLineChart.jCheckBox18.text")); 
    jCheckBox18.setName("jCheckBox18"); 
    jPanel9.add(jCheckBox18);

    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CheckSum", "Modbus CRC", "0x0D" }));
    jComboBox4.setName("jComboBox4"); 
    jPanel9.add(jComboBox4);

    jPanel10.add(jPanel9);
    jPanel9.setBounds(100, 110, 700, 33);

    jPanel7.setName("jPanel7"); 
    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox10.setText(bundle.getString("WSNLineChart.jCheckBox10.text")); 
    jCheckBox10.setName("jCheckBox10"); 
    jPanel7.add(jCheckBox10);

    jLabel31.setText(bundle.getString("WSNLineChart.jLabel31.text")); 
    jLabel31.setName("jLabel31"); 
    jPanel7.add(jLabel31);

    jTextField22.setToolTipText(bundle.getString("WSNLineChart.jTextField22.toolTipText")); 
    jTextField22.setName("jTextField22"); 
    jTextField22.setPreferredSize(new java.awt.Dimension(120, 25));
    jPanel7.add(jTextField22);

    jCheckBox15.setText(bundle.getString("WSNLineChart.jCheckBox15.text")); 
    jCheckBox15.setName("jCheckBox15"); 
    jPanel7.add(jCheckBox15);

    jPanel10.add(jPanel7);
    jPanel7.setBounds(50, 150, 730, 35);

    jPanel8.setName("jPanel8"); 
    jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox13.setText(bundle.getString("WSNLineChart.jCheckBox13.text")); 
    jCheckBox13.setName("jCheckBox13"); 
    jPanel8.add(jCheckBox13);

    jLabel33.setText(bundle.getString("WSNLineChart.jLabel33.text")); 
    jLabel33.setName("jLabel33"); 
    jPanel8.add(jLabel33);

    jTextField23.setText(bundle.getString("WSNLineChart.jTextField23.text")); 
    jTextField23.setToolTipText(bundle.getString("WSNLineChart.jTextField23.toolTipText")); 
    jTextField23.setName("jTextField23"); 
    jTextField23.setPreferredSize(new java.awt.Dimension(120, 25));
    jPanel8.add(jTextField23);

    jCheckBox16.setText(bundle.getString("WSNLineChart.jCheckBox16.text")); 
    jCheckBox16.setName("jCheckBox16"); 
    jPanel8.add(jCheckBox16);

    jPanel10.add(jPanel8);
    jPanel8.setBounds(50, 190, 770, 35);

    jPanel11.setName("jPanel11"); 
    jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox23.setText(bundle.getString("WSNLineChart.jCheckBox23.text")); 
    jCheckBox23.setName("jCheckBox23"); 
    jPanel11.add(jCheckBox23);

    jLabel42.setText(bundle.getString("WSNLineChart.jLabel42.text")); 
    jLabel42.setName("jLabel42"); 
    jPanel11.add(jLabel42);

    jTextField34.setToolTipText(bundle.getString("WSNLineChart.jTextField34.toolTipText")); 
    jTextField34.setName("jTextField34"); 
    jTextField34.setPreferredSize(new java.awt.Dimension(120, 25));
    jPanel11.add(jTextField34);

    jCheckBox24.setText(bundle.getString("WSNLineChart.jCheckBox24.text")); 
    jCheckBox24.setName("jCheckBox24"); 
    jPanel11.add(jCheckBox24);

    jPanel10.add(jPanel11);
    jPanel11.setBounds(50, 360, 730, 35);

    jLabel43.setText(bundle.getString("WSNLineChart.jLabel43.text")); 
    jLabel43.setName("jLabel43"); 
    jPanel10.add(jLabel43);
    jLabel43.setBounds(40, 260, 200, 20);

    jPanel12.setName("jPanel12"); 
    jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox25.setText(bundle.getString("WSNLineChart.jCheckBox25.text")); 
    jCheckBox25.setName("jCheckBox25"); 
    jPanel12.add(jCheckBox25);

    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CheckSum", "Modbus CRC", "0x0D" }));
    jComboBox5.setName("jComboBox5"); 
    jPanel12.add(jComboBox5);

    jPanel10.add(jPanel12);
    jPanel12.setBounds(100, 320, 700, 33);

    jPanel13.setName("jPanel13"); 
    jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox26.setText(bundle.getString("WSNLineChart.jCheckBox26.text")); 
    jCheckBox26.setName("jCheckBox26"); 
    jPanel13.add(jCheckBox26);

    jLabel44.setText(bundle.getString("WSNLineChart.jLabel44.text")); 
    jLabel44.setName("jLabel44"); 
    jPanel13.add(jLabel44);

    jTextField35.setText(bundle.getString("WSNLineChart.jTextField35.text")); 
    jTextField35.setToolTipText(bundle.getString("WSNLineChart.jTextField35.toolTipText")); 
    jTextField35.setName("jTextField35"); 
    jTextField35.setPreferredSize(new java.awt.Dimension(120, 25));
    jPanel13.add(jTextField35);

    jCheckBox27.setText(bundle.getString("WSNLineChart.jCheckBox27.text")); 
    jCheckBox27.setName("jCheckBox27"); 
    jPanel13.add(jCheckBox27);

    jPanel10.add(jPanel13);
    jPanel13.setBounds(50, 400, 770, 35);

    jPanel14.setName("jPanel14"); 
    jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox28.setText(bundle.getString("WSNLineChart.jCheckBox28.text")); 
    jCheckBox28.setName("jCheckBox28"); 
    jPanel14.add(jCheckBox28);

    jTextField36.setToolTipText(bundle.getString("WSNLineChart.jTextField36.toolTipText")); 
    jTextField36.setName("jTextField36"); 
    jTextField36.setPreferredSize(new java.awt.Dimension(100, 25));
    jPanel14.add(jTextField36);

    jLabel45.setText(bundle.getString("WSNLineChart.jLabel45.text")); 
    jLabel45.setName("jLabel45"); 
    jPanel14.add(jLabel45);

    buttonGroup2.add(jRadioButton13);
    jRadioButton13.setText(bundle.getString("WSNLineChart.jRadioButton13.text")); 
    jRadioButton13.setName("jRadioButton13"); 
    jPanel14.add(jRadioButton13);

    buttonGroup2.add(jRadioButton14);
    jRadioButton14.setText(bundle.getString("WSNLineChart.jRadioButton14.text")); 
    jRadioButton14.setName("jRadioButton14"); 
    jRadioButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton14ActionPerformed(evt);
      }
    });
    jPanel14.add(jRadioButton14);

    jLabel46.setText(bundle.getString("WSNLineChart.jLabel46.text")); 
    jLabel46.setName("jLabel46"); 
    jPanel14.add(jLabel46);

    jTextField37.setName("jTextField37"); 
    jTextField37.setPreferredSize(new java.awt.Dimension(100, 25));
    jPanel14.add(jTextField37);

    jCheckBox29.setText(bundle.getString("WSNLineChart.jCheckBox29.text")); 
    jCheckBox29.setName("jCheckBox29"); 
    jPanel14.add(jCheckBox29);

    jPanel10.add(jPanel14);
    jPanel14.setBounds(50, 280, 790, 35);

    jButton13.setFont(jButton13.getFont());
    jButton13.setText(bundle.getString("WSNLineChart.jButton13.text")); 
    jButton13.setName("jButton13"); 
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton13ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton13);
    jButton13.setBounds(390, 10, 90, 30);

    jButton12.setText(bundle.getString("WSNLineChart.jButton12.text")); 
    jButton12.setName("jButton12"); 
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton12ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton12);
    jButton12.setBounds(270, 10, 100, 30);

    jButton15.setFont(jButton15.getFont());
    jButton15.setText(bundle.getString("WSNLineChart.jButton15.text")); 
    jButton15.setName("jButton15"); 
    jButton15.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton15ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton15);
    jButton15.setBounds(590, 10, 100, 30);

    jButton14.setFont(jButton14.getFont());
    jButton14.setText(bundle.getString("WSNLineChart.jButton14.text")); 
    jButton14.setName("jButton14"); 
    jButton14.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton14ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton14);
    jButton14.setBounds(490, 10, 90, 30);

    jButton11.setText(bundle.getString("WSNLineChart.jButton11.text")); 
    jButton11.setName("jButton11"); 
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton11ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton11);
    jButton11.setBounds(140, 10, 110, 30);

    jButton16.setFont(jButton16.getFont());
    jButton16.setText(bundle.getString("WSNLineChart.jButton16.text")); 
    jButton16.setName("jButton16"); 
    jButton16.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton16ActionPerformed(evt);
      }
    });
    jPanel10.add(jButton16);
    jButton16.setBounds(700, 10, 100, 30);

    jTabbedPane1.addTab(bundle.getString("WSNLineChart.jPanel10.TabConstraints.tabTitle"), jPanel10); 

    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    jScrollPane1.setName("jScrollPane1"); 

    jList1.setModel(listModel1);
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
    jScrollPane1.setViewportView(jList1);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

    jMenuBar1.setName("jMenuBar1"); 

    jMenu1.setText(bundle.getString("WSNLineChart.jMenu1.text")); 
    jMenu1.setName("jMenu1"); 
    jMenu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenu1ActionPerformed(evt);
      }
    });

    jMenuItem1.setText(bundle.getString("WSNLineChart.jMenuItem1.text")); 
    jMenuItem1.setName("jMenuItem1"); 
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuItem2.setText(bundle.getString("WSNLineChart.jMenuItem2.text")); 
    jMenuItem2.setName("jMenuItem2"); 
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem2);

    jMenuItem3.setText(bundle.getString("WSNLineChart.jMenuItem3.text")); 
    jMenuItem3.setName("jMenuItem3"); 
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem3);

    jMenuBar1.add(jMenu1);

    jMenu2.setText(bundle.getString("WSNLineChart.jMenu2.text")); 
    jMenu2.setName("jMenu2"); 
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    pack();
  }
public void run(){
    while(true){
        while(waitV.size()>0){

            DataClass dataClass=(DataClass)waitV.get(0);
            if(config.get(dataClass.dataSrc)!=null && status.get(dataClass.dataSrc)!=null){
              Config cfg=(Config)config.get(dataClass.dataSrc);
              Status sta=(Status)status.get(dataClass.dataSrc);
              if(wsn.w.checkOneVar(sta.longValue[1],0)){
              sta.stat[15]=dataClass.data;
              status.put(dataClass.dataSrc, sta);
              if(getValue(dataClass.time,dataClass.data,cfg,sta)){
                if(realtimeMode) iPanel.setTMData(datum,config,status,currentDataSrcId);
                else  newValue=true;
                if(wsn.w.checkOneVar(sta.longValue[1],0)){
                if((wsn.w.checkOneVar(cfg.longValue[0],16) && sta.doubleValue[2] > Double.parseDouble(cfg.conf[20])) ||
                 (wsn.w.checkOneVar(cfg.longValue[0],17) && sta.doubleValue[2] < Double.parseDouble(cfg.conf[21]))){ 
                    sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],2);
                    sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],5);
                    if((wsn.w.checkOneVar(cfg.conf[1],56) && (!wsn.w.checkOneVar(cfg.conf[1],49) || sta.longValue[3]<1)) ||
                        (wsn.w.checkOneVar(cfg.conf[1],54) && (!wsn.w.checkOneVar(cfg.conf[1],50) || sta.longValue[4]<1)) ||
                        (wsn.w.checkOneVar(cfg.conf[1],52) && (!wsn.w.checkOneVar(cfg.conf[1],48) || sta.longValue[2]<1))){
                    wsn.actionThread.setAction(cfg, sta, 1);
                    if(wsn.w.checkOneVar(cfg.conf[1],52)){
                      sta.longValue[2]++;
                      status.put(cfg.conf[4], sta);
                    }
                    if(wsn.w.checkOneVar(cfg.conf[1],54)){
                      sta.longValue[4]++;
                      status.put(cfg.conf[4], sta);
                    }
                    if(wsn.w.checkOneVar(cfg.conf[1],56)){
                      sta.longValue[3]++;
                      status.put(cfg.conf[4], sta);
                    }
                    }
                   }
                    status.put(dataClass.dataSrc, sta);
                }
                else {

                }
              }
              }
            } else {

            }

           if(datum.get(dataClass.dataSrc)!=null && ((TreeMap)datum.get(dataClass.dataSrc)).size()>maxPoints){
               TreeMap tm=(TreeMap)datum.get(dataClass.dataSrc);
               tm.clear();
               datum.put(dataClass.dataSrc,tm);
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

boolean getValue(long time,String da,Config confClass,Status stat){
    double value=0;
    int cnt=0;

    if(wsn.w.checkOneVar(confClass.longValue[0],51)){
                String cla=confClass.conf[29];
                if(((WSNDataTranslator)wsn.jClasses.get(cla))==null){
                    wsn.loadClass(cla,1);

                }
                if(((WSNDataTranslator)wsn.jClasses.get(cla))!=null){
                  value= ((WSNDataTranslator)wsn.jClasses.get(cla)).getValue(da);
                }
    } else {
      if(wsn.w.checkOneVar(confClass.longValue[0],59)){
            StringTokenizer st=new StringTokenizer(da, " ");
            int intx[]=new int[da.length()];
            if(da!=null && da.length()>0){
              for(cnt=0;st.hasMoreTokens();cnt++){
                String tmp=st.nextToken();
                if(wsn.isHexNumber(tmp)) intx[cnt]=Integer.decode("0x"+tmp).intValue();  
                else return false;
              }
              byte[] b0=new byte[cnt];
              for(int i=0;i<cnt;i++){
                b0[i]=(byte)intx[i];
              }
              int from=Integer.parseInt(confClass.conf[5]);
              int to=Integer.parseInt(confClass.conf[6]);
              if(b0.length>to){
                int a=1;
                for(int i=to;i>=from;i--){
                    value=value+(double)(b0[i]*a);
                    a=a * 256;
                }
              } else return false;
            } else return false; 
    } else {
        int cnt2=0;
        byte[] b0={};
        StringTokenizer st2=new StringTokenizer(da, " ");
               int intx[]=new int[da.length()];
               if(da!=null && da.length()>0){
                 for(cnt2=0;st2.hasMoreTokens();cnt2++){
                  intx[cnt2]=Integer.decode("0x"+st2.nextToken()).intValue();  
                }
                b0=new byte[cnt2];
                for(int i=0;i<cnt2;i++){
                  b0[i]=(byte)intx[i];
                }
               }
        da=new String(b0);
        int fieldNo=Integer.parseInt(confClass.conf[7]);
        boolean found=false;
        StringTokenizer st=new StringTokenizer(da, " ");
        while(st.hasMoreElements()){
            cnt++;
            if(fieldNo==cnt) {
                String da2=st.nextToken();
                if(wsn.w.checkOneVar(confClass.longValue[0],53)) {
                    if(confClass.conf[9].length()>0) da2=da2.substring(Integer.parseInt(confClass.conf[8]),Integer.parseInt(confClass.conf[9]));
                    else da2=da2.substring(Integer.parseInt(confClass.conf[8]));
                }
                found=true;
                if(wsn.isNumeric(da2)) value=Double.parseDouble(da2);
                else return false;
                break;
            } else st.nextToken();
        }
        if(!found) return false;
    }
    }
    TreeMap dataTm;
    Object o=datum.get(confClass.conf[4]);
    if(o!=null) dataTm=(TreeMap)o;
      else dataTm=new TreeMap();
    dataTm.put(time, value);
    datum.put(confClass.conf[4],dataTm);
    stat.doubleValue[2]=value;
    status.put(confClass.conf[4],stat);
    return true;
}
public void setData(long time,String nodeId,String dataSrc,String data){

    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) myThread.interrupt();
}

public void setStatus(String nodeId,String dataSrc[],int statusCode){
  for(int i=0;i<dataSrc.length;i++){
    if(statusCode==1 || statusCode==3) {
      int count=jComboBox1.getItemCount();
      boolean found=false;
      for(int j=0;j<count;j++){
        if(((String)jComboBox1.getItemAt(j)).equals(dataSrc[i])) {found=true; break;}
      }
      if(!found) jComboBox1.addItem(dataSrc[i]);
    } else if(statusCode==2){
      int count=jComboBox1.getItemCount();
      boolean found=false;
      for(int j=0;j<count;j++){
        if(((String)jComboBox1.getItemAt(j)).equals(dataSrc[i])) {found=true; break;}
      }
      if(found) jComboBox1.removeItem(dataSrc[i]);
    }
  }
}

private void toStartAll(){
  jList1.setSelectedIndex(0);
  changeItem();

    Iterator it=config.keySet().iterator();
    for(;it.hasNext();){
        String key=(String)it.next();
        Status sta=(Status)status.get(key);
        if(!wsn.w.checkOneVar(sta.longValue[1],0)){
        TreeMap tm=(TreeMap)datum.get(key);
        if(tm!=null){
          tm.clear();
          datum.put(key, tm);
        }
        sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],0);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],1);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],3);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],4);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
        sta.longValue[2]=0;
        sta.longValue[3]=0;
        sta.longValue[4]=0;
        sta.longValue[0]=System.currentTimeMillis();
        status.put(key, sta);
        }
    }
    jTabbedPane1.setSelectedIndex(0);
}
private void toStopAll(){
    Iterator it=status.keySet().iterator();
    for(;it.hasNext();){
        String key=(String)it.next();
        Status sta=(Status)status.get(key);
        if(wsn.w.checkOneVar(sta.longValue[1],0)){
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
        sta.longValue[5]=System.currentTimeMillis();
        status.put(key, sta);
        }
    }
}

public void toStart(String dataSrcId){

    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],2);
    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],3);
    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],4);
    status.put(currentDataSrcId,currentStatus);
    if(datum.get(dataSrcId)!=null){
        TreeMap tm=(TreeMap)datum.get(dataSrcId);
        tm.clear();
        datum.put(dataSrcId, tm);
    }
    if(status.get(dataSrcId)!=null){
      Status sta=(Status)status.get(dataSrcId);
      if(!wsn.w.checkOneVar(sta.longValue[1], 0)){

        sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],0);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
        sta.longValue[0]=System.currentTimeMillis();
        status.put(dataSrcId, sta);
    }
    }

    status.put(currentDataSrcId, currentStatus);
    jTabbedPane1.setSelectedIndex(0);
}

public void toStop(String dataSrcId){

    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],2);
    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],3);
    currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],4);
    status.put(currentDataSrcId,currentStatus);
    if(status.get(dataSrcId)!=null){
        Status sta=(Status)status.get(dataSrcId);
        if(wsn.w.checkOneVar(sta.longValue[1],0)){
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
        sta.longValue[5]=System.currentTimeMillis();
        status.put(dataSrcId, sta);
        }
    }

    status.put(currentDataSrcId,currentStatus);
}
private void formWindowClosing(java.awt.event.WindowEvent evt) {
  if(wsn==null) System.exit(0);
  else setVisible(false);
}
public void getDataSrcList(){
    String cmdStr="performmessage wsn.WSN getdatasource ";
    wsn.w.sendToAll(cmdStr);
}
public void setDataSrc(String data){
    String da[]=wsn.w.csvLineToArray(data);
    if(!da[0].equals("0")){
        String src[]=wsn.w.csvLineToArray(da[1]);
        for(int i=0;i<src.length;i++){
            boolean found=false;
            for(int j=0;j<jComboBox1.getItemCount();j++){
               if(((String)jComboBox1.getItemAt(j)).equals(src[i])) {found=true; break;}
            }
            if(!found) jComboBox1.addItem(src[i]);
        }
    }
}
private void resetLine(){
 jTextField1.setText("");
 jTextField20.setText("");
 jComboBox1.removeAllItems();
 getDataSrcList();
 jComboBox3.setSelectedItem(Color.yellow);
 jRadioButton2.setSelected(true);
 jTextField2.setText("1");
 jTextField3.setText("1");
 jTextField4.setText("1");
 jTextField18.setText("0");
 jTextField19.setText("0");
 jCheckBox6.setSelected(false);
 jCheckBox7.setSelected(false);
 jCheckBox8.setSelected(false);
 jCheckBox9.setSelected(false);
 jTextField14.setText("0.0");
 jTextField15.setText("1.0");
 jTextField16.setText("0.0");
 jTextField17.setText("1");

}
private void resetChart(){
 jComboBox2.setSelectedItem(Color.black);
 jTextField26.setText("Control Chart");
 jTextField5.setText("1000.0");
 jTextField6.setText("0.0");
 jCheckBox12.setSelected(false);
 jCheckBox1.setSelected(false);
 jRadioButton6.setSelected(true);
 jTextField10.setText("400");
 jTextField11.setText("0");
 jTextField12.setText("10");
 jTextField13.setText("0");
 jRadioButton7.setSelected(true);
 jCheckBox2.setSelected(false);
 jCheckBox3.setSelected(false);
 jCheckBox5.setSelected(false);
 jTextField7.setText("100.0");
 jTextField8.setText("0.0");
 jCheckBox4.setSelected(false);
 jCheckBox10.setSelected(false);
 jRadioButton4.setSelected(true);
 jTextField9.setText("");
 jTextField21.setText("");
 jTextField22.setText("");
 jCheckBox13.setSelected(false);
 jTextField23.setText("");
 jCheckBox14.setSelected(false);
 jTextField24.setText("");
 jTextField25.setText("0.0");

 jCheckBox17.setSelected(false);
 jCheckBox15.setSelected(false);
 jCheckBox16.setSelected(false);
 jCheckBox18.setSelected(false);
 jCheckBox19.setSelected(false);
 jCheckBox20.setSelected(true);
}
private void jList1MouseClicked(java.awt.event.MouseEvent evt) {
  changeItem();
}

private void jList1KeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  {
  changeItem();
}
}
private void changeItem(){
    jComboBox1.removeAllItems();
    newValue=false;
    if(jList1.getSelectedValue()==null) return;
    currentNameId=(String)jList1.getSelectedValue();
    currentDataSrcId=(String)nameToDataSrc.get(currentNameId);
    if(currentDataSrcId!=null){
      if(config.get(currentDataSrcId)!=null){
        currentConfig=(Config)config.get(currentDataSrcId);
        currentStatus=(Status)status.get(currentDataSrcId);
        if(currentConfig==null) System.out.println("Warning: configuration for "+currentNameId+" not found.");
        if(currentStatus==null) System.out.println("Warning: status for "+currentNameId+" not found.");
        else {}

        if(currentDataSrcId.equals("0")){
           removeBtn.setEnabled(false);
           setBtn.setEnabled(false);
           getSummary();
        }
        else{
          getDataSrcList();
          removeBtn.setEnabled(true);
          setBtn.setEnabled(true);
          if(chartConfig[1].equals("1")) realtimeMode=true;
          else realtimeMode=false;
        }
        setUI();
        iPanel.setProperties(currentConfig.longValue[0]);
        iPanel.setTMData(datum,config,status,currentDataSrcId);
        newValue=false;
      }
    }
    

}

void getSummary(){
  if(currentConfig.conf[4].equals("0")){
          double upLimit=-1000000.0,downLimit=1000000.0,currentUp=-1000000.0,currentDown=1000000.0;
          int maxPointCount=0,maxStopInx=0;
          long timeInSec=0,hr=0,min=0,sec=0;
          String xUnit="";
          boolean first=true,alarm=false,realtimeAlarm=false,hasXUnit=false;
          Iterator it=config.keySet().iterator();
          for(;it.hasNext();){
            String key=(String)it.next();
            if(!key.equals("0")){
            Config conf2=(Config)config.get(key);
            Status sta2=(Status)status.get(key);
            TreeMap tm=(TreeMap)datum.get(key);
            if(first){
              upLimit=conf2.doubleValue[1];
              downLimit=conf2.doubleValue[2];
              currentUp=sta2.doubleValue[0];
              currentDown=sta2.doubleValue[1];
              maxPointCount=conf2.intValue[0];
              if(tm.size()>0) maxStopInx=tm.size()-1;
              timeInSec=conf2.longValue[1]*60*60+conf2.longValue[2]*60+conf2.longValue[3];
              hr=conf2.longValue[1];
              min=conf2.longValue[2];
              sec=conf2.longValue[3];
              if(conf2.conf[27].length()>0) {xUnit=conf2.conf[27]; hasXUnit=true;}
              first=false;
            } else {
            if(conf2.doubleValue[1]>upLimit) upLimit=conf2.doubleValue[1];
            if(conf2.doubleValue[2]<upLimit) downLimit=conf2.doubleValue[2];
            if(conf2.intValue[0]>maxPointCount) maxPointCount=conf2.intValue[0];
            long t=conf2.longValue[1]*60*60+conf2.longValue[2]*60+conf2.longValue[3];
            if(t>timeInSec) {timeInSec=t; hr=conf2.longValue[1]; min=conf2.longValue[2]; sec=conf2.longValue[3];}
            if(sta2.doubleValue[0]>currentUp) currentUp=sta2.doubleValue[0];
            if(sta2.doubleValue[1]<currentUp) currentDown=sta2.doubleValue[1];
            if(tm.size()>maxStopInx) maxStopInx=tm.size()-1;
            }
            if(wsn.w.checkOneVar(sta2.longValue[1],0)){
                if(wsn.w.checkOneVar(sta2.longValue[1],2)) alarm=true;
                if(wsn.w.checkOneVar(sta2.longValue[1],5)) realtimeAlarm=true;
              }
            if(conf2.conf[27].length()>0)  {xUnit=conf2.conf[27]; hasXUnit=true;}
            }
          }
          if(!first){
            currentConfig.doubleValue[1]=upLimit;
            currentConfig.doubleValue[2]=downLimit;
            currentConfig.intValue[0]=maxPointCount;
            currentConfig.longValue[1]=hr; 
            currentConfig.longValue[2]=min; 
            currentConfig.longValue[3]=sec;
            currentStatus.doubleValue[0]=currentUp;
            currentStatus.doubleValue[1]=currentDown;
            currentConfig.longValue[0]=wsn.w.removeOneVar(currentConfig.longValue[0],4);
            currentConfig.longValue[0]=wsn.w.removeOneVar(currentConfig.longValue[0],18);
            currentConfig.longValue[0]=wsn.w.addOneVar(currentConfig.longValue[0],10);
            currentConfig.conf[27]=xUnit;
            if(alarm) currentStatus.longValue[0]=wsn.w.addOneVar(currentStatus.longValue[0], 2);
            else currentStatus.longValue[0]=wsn.w.removeOneVar(currentStatus.longValue[0], 2);
            if(realtimeAlarm) currentStatus.longValue[0]=wsn.w.addOneVar(currentStatus.longValue[0], 5);
            else currentStatus.longValue[0]=wsn.w.removeOneVar(currentStatus.longValue[0], 5);
            if(hasXUnit) currentConfig.longValue[0]=wsn.w.addOneVar(currentConfig.longValue[0], 20);
            else currentConfig.longValue[0]=wsn.w.removeOneVar(currentConfig.longValue[0], 20);
            currentConfig.longValue[0]=wsn.w.addOneVar(currentConfig.longValue[0], 3);
            currentStatus.intValue[0]=maxStopInx;
            config.put(currentDataSrcId,currentConfig);
            status.put(currentDataSrcId,currentStatus);
            realtimeMode=false;
          }
  }
}
void setUI(){
    currentDataSrcId=currentConfig.conf[4];
    jTextField1.setText(currentNameId);
    jComboBox3.setSelectedItem(new Color(Integer.parseInt(currentConfig.conf[2])));
    jComboBox2.setSelectedItem(new Color(Integer.parseInt(currentConfig.conf[3])));
    jTextField20.setText(currentDataSrcId);
    jTextField2.setText(currentConfig.conf[5]);
    jTextField3.setText(currentConfig.conf[6]);
    jTextField4.setText(currentConfig.conf[7]);
    jTextField18.setText(currentConfig.conf[8]);
    jTextField19.setText(currentConfig.conf[9]);
    jTextField14.setText(currentConfig.conf[10]);
    jTextField15.setText(currentConfig.conf[11]);
    jTextField16.setText(currentConfig.conf[12]);
    jTextField17.setText(currentConfig.conf[13]);
    jTextField5.setText(currentConfig.conf[14]);
    jTextField6.setText(currentConfig.conf[15]);
    jTextField10.setText(currentConfig.conf[16]);
    jTextField11.setText(currentConfig.conf[17]);
    jTextField12.setText(currentConfig.conf[18]);
    jTextField13.setText(currentConfig.conf[19]);
    jTextField7.setText(currentConfig.conf[20]);
    jTextField8.setText(currentConfig.conf[21]);
    jTextField21.setText(currentConfig.conf[22]);
    jTextField9.setText(currentConfig.conf[23]);
    jTextField22.setText(currentConfig.conf[24]);
    jTextField23.setText(currentConfig.conf[28]);
    jTextField24.setText(currentConfig.conf[29]);
    jTextField25.setText(currentConfig.conf[30]);

    if(wsn.w.checkOneVar(currentConfig.longValue[0],59)) jRadioButton1.setSelected(true); else  jRadioButton2.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],53)) jCheckBox11.setSelected(true); else  jCheckBox11.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],58)) jCheckBox6.setSelected(true); else  jCheckBox6.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],57)) jCheckBox8.setSelected(true); else  jCheckBox8.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],13)) jCheckBox9.setSelected(true); else  jCheckBox9.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],1)) jCheckBox7.setSelected(false); else  jCheckBox7.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],14)) jCheckBox12.setSelected(true); else  jCheckBox12.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],15)) jCheckBox1.setSelected(true); else  jCheckBox1.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],18)) jRadioButton6.setSelected(true); else  jRadioButton5.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],12)) jRadioButton8.setSelected(true); else  jRadioButton7.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],16)) jCheckBox2.setSelected(true); else  jCheckBox2.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],17)) jCheckBox3.setSelected(true); else  jCheckBox3.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],11)) jCheckBox19.setSelected(true); else  jCheckBox19.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],54)) jCheckBox4.setSelected(true); else  jCheckBox4.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],55)) jRadioButton3.setSelected(true); else  jRadioButton4.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],56)) jCheckBox10.setSelected(true); else  jCheckBox10.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],52)) jCheckBox13.setSelected(true); else  jCheckBox13.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],51)) jCheckBox14.setSelected(true); else  jCheckBox14.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],50)) jCheckBox17.setSelected(true); else  jCheckBox17.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],49)) jCheckBox15.setSelected(true); else  jCheckBox15.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],48)) jCheckBox16.setSelected(true); else  jCheckBox16.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],46)) jCheckBox18.setSelected(true); else  jCheckBox18.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],19)) jCheckBox5.setSelected(true); else  jCheckBox5.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],3)) jCheckBox20.setSelected(true); else  jCheckBox20.setSelected(false);
    iPanel.oPanel.jCheckBox10.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],3));
    iPanel.oPanel.jCheckBox1.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],4));
    iPanel.oPanel.jCheckBox9.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],6));
    iPanel.oPanel.jCheckBox8.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],11));
    iPanel.oPanel.jCheckBox7.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],12));
    iPanel.oPanel.jCheckBox2.setSelected(wsn.w.checkOneVar(currentConfig.longValue[0],18));
    jComboBox4.setSelectedItem(currentConfig.conf[165]);

}public void setBlink(boolean onoff){
     this.onoff=onoff;
     if(!realtimeMode){
       if(wsn.w.checkOneVar(currentStatus.longValue[1],0)) getSummary();
       if(newValue) {iPanel.setTMData(datum,config,status,currentDataSrcId); newValue=false;}
     }
     if(iPanel!=null) iPanel.setBlink(onoff);
     if(onoff){
        {

       }
     } else {
         currentStatus.longValue[1]=wsn.w.removeOneVar(currentStatus.longValue[1],5);
         status.put(currentDataSrcId, currentStatus);
     }
  }
private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
  formWindowClosing(null);
}

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
  JFileChooser chooser = new JFileChooser(chartFile);
  chooser.setDialogTitle(bundle2.getString("WSNLineChart.xy.msg1"));
  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  if(chartFile.length()>0){
    chooser.setSelectedFile(new File(new File(chartFile).getName()));
  }
  int returnVal = chooser.showDialog(this,bundle2.getString("WSNLineChart.xy.msg2"));
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      String tempChartFile=chooser.getSelectedFile().getAbsolutePath();

     readChartFile(tempChartFile,1);
  }
}

private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
  JFileChooser chooser = new JFileChooser(chartFile);
  chooser.setDialogTitle(bundle2.getString("WSNLineChart.xy.msg45"));
  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  if(chartFile.length()>0){
    chooser.setSelectedFile(new File(new File(chartFile).getName()));
  }
  int returnVal = chooser.showDialog(this,bundle2.getString("WSNLineChart.xy.msg46"));
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      String tempChartFile=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(tempChartFile);
      boolean save=false;
      if(f2.exists()){
         int result = JOptionPane.showConfirmDialog(this, bundle2.getString("WSNLineChart.xy.msg47"), bundle2.getString("WSNLineChart.xy.msg48"), JOptionPane.YES_NO_CANCEL_OPTION);
         if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){
        saveChartFile(tempChartFile,1);
      }
  }
}

private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void setBtnActionPerformed(java.awt.event.ActionEvent evt) {
setupChart();
}

private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {
  if(jList1.getSelectedValue()!=null){
      String n=(String)jList1.getSelectedValue();
      config.remove((String)nameToDataSrc.get(n));
      listModel1.removeElement(n);
      status.remove((String)nameToDataSrc.get(n));
      boolean found=false;
      for(int i=0;i<listModel1.getSize();i++){
          String tmpName=(String)listModel1.get(i);
          if(nameToDataSrc.get(tmpName)!=null && ((String)nameToDataSrc.get(tmpName)).equals(currentDataSrcId)) {found=true; break;} 
      }
      if(!found) {datum.remove(currentDataSrcId);}
      currentNameId="";
      currentDataSrcId="";
      resetLine();
  }
}

private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {
 resetLine();
}

private void removeAllBtnActionPerformed(java.awt.event.ActionEvent evt) {

      listModel1.removeAllElements();
      listModel1.addElement(allLineName);
      status.clear();
      nameToDataSrc.clear();
      config.clear();
      datum.clear();
      currentNameId="";
      currentDataSrcId="";
      nameToDataSrc.put(allLineName,"0");
      resetLine();
}

private void jCheckBox14ActionPerformed(java.awt.event.ActionEvent evt) {
  if(jCheckBox14.isSelected()){
      jRadioButton1.setEnabled(false);
      jRadioButton2.setEnabled(false);
  } else {
      jRadioButton1.setEnabled(true);
      jRadioButton2.setEnabled(true);
  }
}

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
  toStartAll();
}

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
 toStopAll();
}

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
  if(jComboBox1.getSelectedItem()!=null) jTextField20.setText((String)jComboBox1.getSelectedItem());
}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  iPanel.btnPanel.toStart();
  jTabbedPane1.setSelectedIndex(0);
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  iPanel.btnPanel.toStop();
}

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
      setupChart();
    }

void setupChart(){
           if(jTextField26.getText().trim().length()>0){
           chartConfig[0]=jTextField26.getText().trim();
           setTitle(chartConfig[0]);
       }
         long onevar=1024;
  onevar=wsn.w.addOneVar(onevar,4);
  Color[] confColor={};
  String name=jTextField1.getText().trim();
  if(name.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg3")); return;}
  if(jTextField26.getText().trim().length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg50")); return;}
  Color lineColor=(Color)jComboBox3.getSelectedItem(),bgColor=(Color)jComboBox2.getSelectedItem();
  String lineColor2=String.valueOf(lineColor.getRGB());
  String bgColor2=String.valueOf(bgColor.getRGB());
  if(lineColor.equals(bgColor)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg4")); return;}
  String dataSrc=jTextField20.getText().trim().toUpperCase();
  if(dataSrc.length()<3)  {JOptionPane.showMessageDialog(this,"\""+ dataSrc+"\" "+bundle2.getString("WSNLineChart.xy.msg5")); return;}
  String fromByte=jTextField2.getText().trim(),toByte=jTextField3.getText().trim();
  String fieldNo=jTextField4.getText().trim(),subStr1=jTextField18.getText().trim(),subStr2=jTextField19.getText().trim();
  if(jRadioButton1.isSelected()) {
      onevar=wsn.w.addOneVar(onevar,59);
      if(!wsn.isNumeric(fromByte) || !wsn.isNumeric(toByte)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg6")); return;}
      if(fromByte.indexOf(".")>-1 || toByte.indexOf(".")>-1){JOptionPane.showMessageDialog(this, "\"byte from "+fromByte+" to "+toByte+"\", "+bundle2.getString("WSNLineChart.xy.msg7")); return;}
      if(Integer.parseInt(fromByte)>Integer.parseInt(toByte)){JOptionPane.showMessageDialog(this, "\"byte from "+fromByte+" to "+toByte+"\", "+bundle2.getString("WSNLineChart.xy.msg8")); return;}
      if(Integer.parseInt(fromByte)==0){JOptionPane.showMessageDialog(this, "\"byte from "+fromByte+" to "+toByte+"\", \r\n"+bundle2.getString("WSNLineChart.xy.msg9")); return;}
  } else {
      if(!wsn.isNumeric(fieldNo)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg10")); return;}
      if(jCheckBox11.isSelected()){
        onevar=wsn.w.addOneVar(onevar,53);
        if(!wsn.isNumeric(subStr1) || (subStr2.length()>0 && !wsn.isNumeric(subStr2))) {JOptionPane.showMessageDialog(this, "\"substring from "+subStr1+" to "+subStr2+"\", "+bundle2.getString("WSNLineChart.xy.msg11")); return;}
        if(subStr1.indexOf(".")>-1 || subStr2.indexOf(".")>-1){JOptionPane.showMessageDialog(this, "\"substring from "+subStr1+" to "+subStr2+"\", "+bundle2.getString("WSNLineChart.xy.msg12")); return;}
        if(subStr2.length()>0 && Integer.parseInt(subStr1)>Integer.parseInt(subStr2)){JOptionPane.showMessageDialog(this, "\"substring from "+subStr1+" to "+subStr2+"\", "+bundle2.getString("WSNLineChart.xy.msg13")); return;}
      }
  }
  String x2Value=jTextField14.getText().trim(),xValue=jTextField15.getText().trim(),constantValue=jTextField16.getText().trim();
  if(jCheckBox6.isSelected()){
      onevar=wsn.w.addOneVar(onevar,58);
      if(x2Value.length()<1 && xValue.length()<1 && constantValue.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg14")); return;}
      if(x2Value.length()>0 && !wsn.isNumeric(x2Value))  {JOptionPane.showMessageDialog(this, "\""+x2Value+"\" "+bundle2.getString("WSNLineChart.xy.msg15")); return;}
      if(xValue.length()>0 && !wsn.isNumeric(xValue))  {JOptionPane.showMessageDialog(this, "\""+xValue+"\" "+bundle2.getString("WSNLineChart.xy.msg16")); return;}
      if(constantValue.length()>0 && !wsn.isNumeric(constantValue))  {JOptionPane.showMessageDialog(this, "\""+x2Value+"\" "+bundle2.getString("WSNLineChart.xy.msg17")); return;}
      if(x2Value.length()<1) x2Value="0";
      if(xValue.length()<1) xValue="0";
      if(constantValue.length()<1) constantValue="0";
  }
  String averageN=jTextField17.getText().trim(),javaDataClass=jTextField24.getText().trim();
  if(javaDataClass.length()>6 && javaDataClass.indexOf(".class")==javaDataClass.length()-6) javaDataClass=javaDataClass.substring(0,javaDataClass.indexOf(".class"));
  if(jCheckBox14.isSelected()){
      onevar=wsn.w.addOneVar(onevar,51);
      if(javaDataClass.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg18")); return;}
      if(!wsn.loadClass(javaDataClass,1)) {JOptionPane.showMessageDialog(this, "java data class "+javaDataClass+" not exist or not implements WSNData interface."); return;}
  }
  if(jCheckBox8.isSelected()){
      onevar=wsn.w.addOneVar(onevar,57);
      if(!wsn.isNumeric(averageN)) {JOptionPane.showMessageDialog(this,"\""+averageN+"\" "+ bundle2.getString("WSNLineChart.xy.msg19")); return;}
      if(Integer.parseInt(averageN)<2) {JOptionPane.showMessageDialog(this,"\""+averageN+"\" "+ bundle2.getString("WSNLineChart.xy.msg20")); return;}
  }
  if(jCheckBox9.isSelected()) onevar=wsn.w.addOneVar(onevar,13);
  if(!jCheckBox7.isSelected()) onevar=wsn.w.addOneVar(onevar,1);
  String upLimit=jTextField5.getText().trim(),downLimit=jTextField6.getText().trim();
  if(upLimit.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg21")); return;}
  if(downLimit.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg22")); return;}
  if(!wsn.isNumeric(upLimit)) {JOptionPane.showMessageDialog(this, "\""+upLimit+"\" "+bundle2.getString("WSNLineChart.xy.msg23")); return;}
  if(!wsn.isNumeric(downLimit)) {JOptionPane.showMessageDialog(this, "\""+downLimit+"\" "+ bundle2.getString("WSNLineChart.xy.msg24")); return;}
  if(Double.parseDouble(downLimit)>Double.parseDouble(upLimit)){JOptionPane.showMessageDialog(this,"\""+ upLimit+" < "+downLimit+"\", "+bundle2.getString("WSNLineChart.xy.msg25")); return;}

  if(jCheckBox12.isSelected()) onevar=wsn.w.addOneVar(onevar,14);
  if(jCheckBox1.isSelected()) onevar=wsn.w.addOneVar(onevar,15);
  String pointCount=jTextField10.getText().trim(),timeHour=jTextField11.getText().trim(),timeMinute=jTextField12.getText().trim(),
          timeSecond=jTextField13.getText().trim(),javaActionClass=jTextField23.getText().trim();
  if(javaActionClass.length()>6 && javaActionClass.indexOf(".class")==javaActionClass.length()-6) javaActionClass=javaActionClass.substring(0,javaActionClass.indexOf(".class"));
  if(jRadioButton6.isSelected()){
      onevar=wsn.w.addOneVar(onevar,18);
      if(timeHour.length()<1 && timeMinute.length()<1 && timeSecond.length()<1)  {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg26")); return;}
      if(timeHour.length()<1) timeHour="0";
      if(timeMinute.length()<1) timeMinute="0";
      if(timeSecond.length()<1) timeSecond="0";
      if(timeHour.equals("0")  && timeMinute.equals("0")  && timeSecond.equals("0"))  {JOptionPane.showMessageDialog(this, "\""+timeHour+":"+timeMinute+":"+timeSecond+"\" "+bundle2.getString("WSNLineChart.xy.msg27")); return;}
      if(!wsn.isNumeric(timeHour) || !wsn.isNumeric(timeMinute) || !wsn.isNumeric(timeSecond))  {JOptionPane.showMessageDialog(this,"\""+timeHour+":"+timeMinute+":"+timeSecond+"\" "+ bundle2.getString("WSNLineChart.xy.msg28")); return;}
      if(timeHour.indexOf(".")>-1 || timeMinute.indexOf(".")>-1  || timeSecond.indexOf(".")>-1 || Integer.parseInt(timeHour)<0 || Integer.parseInt(timeMinute)<0 || Integer.parseInt(timeSecond)<0)  {JOptionPane.showMessageDialog(this,"\""+timeHour+":"+timeMinute+":"+timeSecond+"\" "+ bundle2.getString("WSNLineChart.xy.msg29")); return;}
  } else {
      if(pointCount.length()<1)  {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg30")); return;}
      if(!wsn.isNumeric(pointCount))  {JOptionPane.showMessageDialog(this,"\""+pointCount+"\" "+ bundle2.getString("WSNLineChart.xy.msg31")); return;}
      if(pointCount.indexOf(".")>-1)  {JOptionPane.showMessageDialog(this,"\""+pointCount+"\" "+ bundle2.getString("WSNLineChart.xy.msg32")); return;}
      if(pointCount.equals("0"))  {JOptionPane.showMessageDialog(this,"\""+pointCount+"\" "+ bundle2.getString("WSNLineChart.xy.msg33")); return;}
  }
  String controlUp=jTextField7.getText().trim(),controlDown=jTextField8.getText().trim(),controlTo=jTextField21.getText().trim().toUpperCase(),
        controlValue=jTextField9.getText().trim(),disconnectTo=jTextField22.getText().trim().toUpperCase(),targetValue=jTextField25.getText().trim();
  if(jRadioButton8.isSelected()) onevar=wsn.w.addOneVar(onevar,12);
  if(jCheckBox2.isSelected()) onevar=wsn.w.addOneVar(onevar,16);
  if(jCheckBox3.isSelected()) onevar=wsn.w.addOneVar(onevar,17);
  if(jCheckBox20.isSelected()) onevar=wsn.w.addOneVar(onevar,3);
  if(jCheckBox5.isSelected()) {
      onevar=wsn.w.addOneVar(onevar,19);
      if(targetValue.length()<1 || !wsn.isNumeric(targetValue))  {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg34")); return;}
  }
    if(jCheckBox19.isSelected()) {
        onevar=wsn.w.addOneVar(onevar,11);
        if(!wsn.isNumeric(controlUp) || !wsn.isNumeric(controlDown))  {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg35")); return;}
  }
  if(jCheckBox4.isSelected()) onevar=wsn.w.addOneVar(onevar,54);
  if(jRadioButton3.isSelected()) onevar=wsn.w.addOneVar(onevar,55);
  if(jCheckBox10.isSelected()) onevar=wsn.w.addOneVar(onevar,56);
  if(jCheckBox17.isSelected()) onevar=wsn.w.addOneVar(onevar,50);
  if(jCheckBox15.isSelected()) onevar=wsn.w.addOneVar(onevar,49);
  if(jCheckBox16.isSelected()) onevar=wsn.w.addOneVar(onevar,48);
  if(jCheckBox18.isSelected()) onevar=wsn.w.addOneVar(onevar,46);
  if(jCheckBox2.isSelected() && !wsn.isNumeric(controlUp))  {JOptionPane.showMessageDialog(this,"\""+controlUp+"\" "+ bundle2.getString("WSNLineChart.xy.msg36")); return;}
  if(jCheckBox3.isSelected() && !wsn.isNumeric(controlDown))  {JOptionPane.showMessageDialog(this,"\""+controlDown+"\" "+bundle2.getString("WSNLineChart.xy.msg37")); return;}
  if(jCheckBox2.isSelected() && !wsn.isNumeric(controlUp) && jCheckBox3.isSelected() && !wsn.isNumeric(controlDown) && Double.parseDouble(controlDown)>Double.parseDouble(controlUp)){
      JOptionPane.showMessageDialog(this,"\""+ controlUp+" < "+controlDown+"\", "+bundle2.getString("WSNLineChart.xy.msg38"));
      return;
  }
  if(jCheckBox4.isSelected()){
     if(controlTo.length()<4)  {JOptionPane.showMessageDialog(this,"\""+controlTo+"\" "+ bundle2.getString("WSNLineChart.xy.msg39")); return;}
     if(controlValue.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg40")); return;}
  }
  if(jCheckBox10.isSelected()){
     if(disconnectTo.length()<4)  {JOptionPane.showMessageDialog(this, "\""+disconnectTo+"\" "+bundle2.getString("WSNLineChart.xy.msg41")); return;}
  }
  if(jCheckBox13.isSelected()) {
      onevar=wsn.w.addOneVar(onevar,52);
      if(javaActionClass.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNLineChart.xy.msg42")); return;}
      if(!wsn.loadClass(javaActionClass,2)) {JOptionPane.showMessageDialog(this, "java action class "+javaActionClass+" not exist or not implements WSNAction interface."); return;}
  }
  if(fromByte.length()<1) fromByte="1";
  if(toByte.length()<1) toByte="1";
  if(fieldNo.length()<1) fieldNo="1";
  if(subStr1.length()<1) subStr1="0";
  if(subStr2.length()<1) subStr2="0";
  if(x2Value.length()<1) x2Value="0.0";
  if(xValue.length()<1) xValue="1.0";
  if(constantValue.length()<1) constantValue="0.0";
  if(averageN.length()<1) averageN="1";
  if(upLimit.length()<1) upLimit="1.0";
  if(downLimit.length()<1) downLimit="0.0";
  if(timeHour.length()<1) timeHour="0";
  if(timeMinute.length()<1) timeMinute="0";
  if(timeSecond.length()<1) timeSecond="0";
  if(controlUp.length()<1) controlUp="1.0";
  if(controlDown.length()<1) controlDown="0.0";
  if(targetValue.length()<1) targetValue="0.0";
  String chksumType=(String)jComboBox4.getSelectedItem();
  String interval="10";
  String conf[]={name,String.valueOf(onevar),lineColor2,bgColor2,dataSrc,
      fromByte,toByte,fieldNo,subStr1,subStr2,
      x2Value,xValue,constantValue,averageN,upLimit,
      downLimit,pointCount,timeHour,timeMinute,timeSecond,
    controlUp,controlDown,controlTo,controlValue,disconnectTo,
    "Y","10","",javaActionClass,javaDataClass,
    targetValue,interval,"","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  chksumType,"","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","","",
  "","","","",""};
    confColor=new Color[]{bgColor,lineColor,Color.gray,Color.white,Color.red,Color.blue,Color.red,Color.magenta,Color.orange,Color.white};
    Config newConfig=new Config(new long[]{Long.parseLong(conf[1]),Long.parseLong(conf[17]),Long.parseLong(conf[18]),Long.parseLong(conf[19]),0},new int[]{Integer.parseInt(conf[16]),Integer.parseInt(conf[26]),Integer.parseInt(conf[13]),0,0,0},new double[]{Double.parseDouble(conf[30]),Double.parseDouble(conf[14]),Double.parseDouble(conf[15]),Double.parseDouble(conf[20]),Double.parseDouble(conf[21])},confColor,conf);
    Status newStatus=new Status();
  if(config.containsKey(dataSrc)){
      Config oldConfig=(Config) config.get(dataSrc);
      Status oldStatus=(Status) status.get(dataSrc);
      int an=JOptionPane.showConfirmDialog(this, "replace old data (name="+oldConfig.conf[0]+",id="+dataSrc+")?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
      if(an==JOptionPane.YES_OPTION){

          if(!oldConfig.conf[0].equals(newConfig.conf[0])){
              listModel1.removeElement(oldConfig.conf[0]);
              listModel1.addElement(newConfig.conf[0]);
              nameToDataSrc.remove(oldConfig.conf[0]);
              nameToDataSrc.put(name,dataSrc);
              if(!currentNameId.equals(oldConfig.conf[0])) currentNameId=newConfig.conf[0];
          }
          config.put(dataSrc, newConfig);
          if(currentDataSrcId.equals(newConfig.conf[4])){
              currentConfig=newConfig;
          }
      } else return;
  } else if(listModel1.contains(name)){
      String oldDataSrc=(String)nameToDataSrc.get(name);
      Config oldConfig=(Config) config.get(oldDataSrc);
      Status oldStatus=(Status) status.get(oldDataSrc);
      int an=JOptionPane.showConfirmDialog(this, "replace old data (name="+name+",id="+oldDataSrc+")?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
      if(an==JOptionPane.YES_OPTION){

          config.remove(oldDataSrc);
          status.remove(oldDataSrc);
          datum.remove(oldDataSrc);
          config.put(dataSrc, newConfig);
          status.put(dataSrc,newStatus);
          datum.put(dataSrc, new TreeMap());
          nameToDataSrc.put(name,dataSrc);
          if(currentDataSrcId.equals(oldConfig.conf[4])) currentDataSrcId=newConfig.conf[4];
          if(currentNameId.equals(oldConfig.conf[0])){
              currentConfig=newConfig;
          }
      } else return;
  }else {
      datum.put(dataSrc,new TreeMap()); 
      config.put(dataSrc,newConfig); 
      if(config.get("0")==null) {
          String conf2[]=new String[conf.length];
          for(int i=0;i<conf2.length;i++) conf2[i]=conf[i];
          Config newConfigAll=new Config(new long[]{Long.parseLong(conf[1]),0,5,0},new int[]{Integer.parseInt(conf[16]),Integer.parseInt(conf[26]),Integer.parseInt(conf[13]),0,0,0},new double[]{Double.parseDouble(conf[30]),Double.parseDouble(conf[14]),Double.parseDouble(conf[15]),Double.parseDouble(conf[20]),Double.parseDouble(conf[21])},confColor,conf2);
          newConfigAll.conf[0]=allLineName;
          newConfigAll.conf[4]="0";
          config.put("0",newConfigAll);
      } 
      status.put(dataSrc,newStatus);
      if(status.get("0")==null) {
          Status newStatusAll=new Status();
          status.put("0", newStatusAll);
      } 
      if(!listModel1.contains(allLineName)){
          listModel1.addElement(allLineName);
          nameToDataSrc.put(allLineName, "0");
      }
      listModel1.addElement(name);
      nameToDataSrc.put(name,dataSrc);

  }
  if(currentNameId==null || currentNameId.length()<1) {
      currentNameId=name;
      currentDataSrcId=dataSrc;
      currentConfig=(Config)config.get(currentDataSrcId);
      currentStatus=(Status)status.get(currentDataSrcId);
  }
  jList1.setSelectedValue(currentNameId, true);
  JOptionPane.showMessageDialog(this, "\""+chartConfig[0]+"\" "+bundle2.getString("WSNLineChart.xy.msg43"));
}
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
       toStopAll();
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
       iPanel.btnPanel.toStart();
       jTabbedPane1.setSelectedIndex(0);
    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
      iPanel.btnPanel.toStop();
    }

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
  toStartAll();
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
      resetChart();
    }

  private void jRadioButton14ActionPerformed(java.awt.event.ActionEvent evt) {

  }

  private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
      resetChart();
  }

  private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {
      setupChart();
  }

  private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
       iPanel.btnPanel.toStart();
       jTabbedPane1.setSelectedIndex(0);
  }

  private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
      iPanel.btnPanel.toStop();
  }

  private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
  toStartAll();
  }

  private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
       toStopAll();
  }

  private void jCheckBox19ActionPerformed(java.awt.event.ActionEvent evt) {

  }

public void onExit(int type){
    Iterator it=status.keySet().iterator();
    for(;it.hasNext();){
        String key=(String)it.next();
        Status sta=(Status)status.get(key);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
        sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
        status.put(key, sta);
    }
    saveChartFile(chartFile,2);
}
  public boolean runInBackground(){
    return false;
  }

public void readChartFile(String chartFile2,int type){
    if(chartFile2!=null && chartFile2.length()>0){
       File f=new File(chartFile2);
       if(f.exists() && f.isFile()){
         StringBuilder sb=new StringBuilder();

         listModel1.removeAllElements();
         listModel1.addElement(allLineName);
         datum.clear();
         config.clear();
         status.clear();
         currentNameId="";
         currentDataSrcId="";
         currentConfig=null;
         nameToDataSrc.clear();
         nameToDataSrc.put(allLineName,"0");
         try{
           FileInputStream in=new FileInputStream(chartFile2);
           BufferedReader d= new BufferedReader(new InputStreamReader(in));
           while(true){
	     String str1=d.readLine();
	     if(str1==null) {in.close(); d.close(); break; }

             if(str1.length()>0){
               int inx=str1.indexOf("config_");
               int inx2=str1.indexOf("status_");
               int inx3=str1.indexOf("=");
               int inx4=str1.indexOf("current");
               int inx5=str1.indexOf("chart");
               if(inx3>-1){
                 if(inx==0 && str1.length()>7){
                   String dataSrc=str1.substring(7,inx3);
                   String conf[]=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());

                   if(!dataSrc.equals("0")) datum.put(dataSrc,new TreeMap());

                   if(conf.length<200){
                     String conf2[]=new String[200];
                     for(int i=0;i<conf.length;i++) conf2[i]=conf[i];
                     for(int i=conf.length;i<200;i++) conf2[i]="";
                     conf=conf2;
                   }
                   config.put(dataSrc, new Config(conf));
                   if(!conf[4].equals("0")) listModel1.addElement(conf[0]);
                   nameToDataSrc.put(conf[0],conf[4]);
                 } else if(inx2==0 && str1.length()>7){
                   String dataSrc=str1.substring(7,inx3);
                   String [] stat=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());
                   if(stat.length>24) status.put(dataSrc, new Status(new long[]{Long.parseLong(stat[0]),Long.parseLong(stat[1]),Long.parseLong(stat[2]),Long.parseLong(stat[3]),Long.parseLong(stat[4]),
                     Long.parseLong(stat[20]),Long.parseLong(stat[21]),Long.parseLong(stat[22]),Long.parseLong(stat[23]),Long.parseLong(stat[24])},
                           new int[]{Integer.parseInt(stat[5]),Integer.parseInt(stat[6]),Integer.parseInt(stat[7]),Integer.parseInt(stat[8]),Integer.parseInt(stat[9])},
                           new double[]{Double.parseDouble(stat[10]),Double.parseDouble(stat[11]),Double.parseDouble(stat[12]),Double.parseDouble(stat[13]),Double.parseDouble(stat[14])},
                           stat)); 
                   else status.put(dataSrc,new Status());
                 } else if(inx4==0 && str1.length()>7){
                     String tmp[]=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());
                     currentNameId=tmp[0];
                     currentDataSrcId=tmp[1];
                 } else if(inx5==0 && str1.length()>6){
                   chartConfig=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());
                   if(chartConfig.length>0 && chartConfig[0].length()>0) {setTitle(chartConfig[0]); jTextField26.setText(chartConfig[0]); }
                 }
                 if(currentNameId!=null && nameToDataSrc.get(currentNameId)!=null && config.get((String)nameToDataSrc.get(currentNameId))!=null){
                   currentConfig=(Config)config.get((String)nameToDataSrc.get(currentNameId));
                   currentStatus=(Status)status.get((String)nameToDataSrc.get(currentNameId));
                   if(currentConfig==null) currentConfig=new Config();
                   if(currentStatus==null) currentStatus=new Status();
                   setUI();
                 }
               } else{

               }
             }
           }
	   in.close();
	   d.close();

           Vector toRemove=new Vector();
           Iterator it=status.keySet().iterator();
           for(;it.hasNext();){
               String key=(String)it.next();
               if(config.get(key)==null) toRemove.add(key); 
           }
           if(toRemove.size()>0){
               Enumeration en=toRemove.elements();
               for(;en.hasMoreElements();){
                   status.remove((String)en.nextElement());
               }
           }

           it=status.keySet().iterator();
           for(;it.hasNext();){
             String key=(String)it.next();
             Status sta=(Status)status.get(key);
             sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
             sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
             sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],5);
             status.put(key, sta);
           }
           }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){

      if(type==1) JOptionPane.showMessageDialog(this," Error in reading "+chartFile2+" file.");

        e.printStackTrace();
    }
    } else {
           if(type==1) JOptionPane.showMessageDialog(this,"Warning: chart file "+chartFile2+" not exist.");

       }
}
}

public void saveChartFile(String chartFile2,int type){
       StringBuffer sb=new StringBuffer("chart="+wsn.w.arrayToCsvLine(chartConfig)+"\r\ncurrent="+wsn.w.toCsv(currentNameId)+","+currentDataSrcId);
        for(Iterator it=config.values().iterator();it.hasNext();){
            Config cfg=(Config)it.next();
            sb.append("\r\nconfig_"+cfg.conf[4]+"="+wsn.w.arrayToCsvLine(cfg.conf));
        }
        for(Iterator it=status.keySet().iterator();it.hasNext();){
            String key=(String)it.next();
            Status sts=(Status)status.get(key);
            sts.stat=new String[]{""+sts.longValue[0],""+sts.longValue[1],""+sts.longValue[2],""+sts.longValue[3],""+sts.longValue[4],
             ""+sts.intValue[0],""+sts.intValue[1],""+sts.intValue[2],""+sts.intValue[3],""+sts.intValue[4],
             ""+sts.doubleValue[0],""+sts.doubleValue[1],""+sts.doubleValue[2],""+sts.doubleValue[3],""+sts.doubleValue[4],
             sts.stat[15],sts.stat[16],sts.stat[17],sts.stat[18],sts.stat[19],
            ""+sts.longValue[5],""+sts.longValue[6],""+sts.longValue[7],""+sts.longValue[8],""+sts.longValue[9]};
            sb.append("\r\nstatus_"+key+"="+wsn.w.arrayToCsvLine(sts.stat));
        }
        try{
		          FileOutputStream out = new FileOutputStream (chartFile2);
		          byte [] b=sb.toString().getBytes();
		          out.write(b);
		          out.close();
      }catch(IOException e){e.printStackTrace();}

      if(type==1) JOptionPane.showMessageDialog(this,"chart file "+chartFile2+" "+bundle2.getString("WSNLineChart.xy.msg44"));
    }
 class MyCellRenderer extends JButton implements ListCellRenderer {  
     public MyCellRenderer() {  
         setOpaque(true); 

     }
     boolean b=false;
    @Override
    public void setBackground(Color bg) {

         if(!b)
         {
             return;
         }

        super.setBackground(bg);
    }
     public Component getListCellRendererComponent(  
         JList list,  
         Object value,  
         int index,  

         boolean isSelected,  
         boolean cellHasFocus)  
     {  

         b=true;
         setText(" ");           
         setBackground((Color)value);        
         b=false;
         return this;  
     }  
}

  private javax.swing.JButton addBtn;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  private javax.swing.ButtonGroup buttonGroup3;
  private javax.swing.ButtonGroup buttonGroup4;
  private javax.swing.ButtonGroup buttonGroup5;
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
  private javax.swing.JCheckBox jCheckBox10;
  private javax.swing.JCheckBox jCheckBox11;
  private javax.swing.JCheckBox jCheckBox12;
  private javax.swing.JCheckBox jCheckBox13;
  private javax.swing.JCheckBox jCheckBox14;
  private javax.swing.JCheckBox jCheckBox15;
  private javax.swing.JCheckBox jCheckBox16;
  private javax.swing.JCheckBox jCheckBox17;
  private javax.swing.JCheckBox jCheckBox18;
  private javax.swing.JCheckBox jCheckBox19;
  private javax.swing.JCheckBox jCheckBox2;
  private javax.swing.JCheckBox jCheckBox20;
  private javax.swing.JCheckBox jCheckBox21;
  private javax.swing.JCheckBox jCheckBox22;
  private javax.swing.JCheckBox jCheckBox23;
  private javax.swing.JCheckBox jCheckBox24;
  private javax.swing.JCheckBox jCheckBox25;
  private javax.swing.JCheckBox jCheckBox26;
  private javax.swing.JCheckBox jCheckBox27;
  private javax.swing.JCheckBox jCheckBox28;
  private javax.swing.JCheckBox jCheckBox29;
  private javax.swing.JCheckBox jCheckBox3;
  private javax.swing.JCheckBox jCheckBox30;
  private javax.swing.JCheckBox jCheckBox31;
  private javax.swing.JCheckBox jCheckBox4;
  private javax.swing.JCheckBox jCheckBox5;
  private javax.swing.JCheckBox jCheckBox6;
  private javax.swing.JCheckBox jCheckBox7;
  private javax.swing.JCheckBox jCheckBox8;
  private javax.swing.JCheckBox jCheckBox9;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JComboBox jComboBox3;
  private javax.swing.JComboBox jComboBox4;
  private javax.swing.JComboBox jComboBox5;
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
  private javax.swing.JLabel jLabel29;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel30;
  private javax.swing.JLabel jLabel31;
  private javax.swing.JLabel jLabel32;
  private javax.swing.JLabel jLabel33;
  private javax.swing.JLabel jLabel34;
  private javax.swing.JLabel jLabel35;
  private javax.swing.JLabel jLabel36;
  private javax.swing.JLabel jLabel37;
  private javax.swing.JLabel jLabel38;
  private javax.swing.JLabel jLabel39;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel40;
  private javax.swing.JLabel jLabel41;
  private javax.swing.JLabel jLabel42;
  private javax.swing.JLabel jLabel43;
  private javax.swing.JLabel jLabel44;
  private javax.swing.JLabel jLabel45;
  private javax.swing.JLabel jLabel46;
  private javax.swing.JLabel jLabel47;
  private javax.swing.JLabel jLabel48;
  private javax.swing.JLabel jLabel49;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JList jList1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
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
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JRadioButton jRadioButton1;
  private javax.swing.JRadioButton jRadioButton10;
  private javax.swing.JRadioButton jRadioButton11;
  private javax.swing.JRadioButton jRadioButton12;
  private javax.swing.JRadioButton jRadioButton13;
  private javax.swing.JRadioButton jRadioButton14;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;
  private javax.swing.JRadioButton jRadioButton5;
  private javax.swing.JRadioButton jRadioButton6;
  private javax.swing.JRadioButton jRadioButton7;
  private javax.swing.JRadioButton jRadioButton8;
  private javax.swing.JRadioButton jRadioButton9;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField10;
  private javax.swing.JTextField jTextField11;
  private javax.swing.JTextField jTextField12;
  private javax.swing.JTextField jTextField13;
  private javax.swing.JTextField jTextField14;
  private javax.swing.JTextField jTextField15;
  private javax.swing.JTextField jTextField16;
  private javax.swing.JTextField jTextField17;
  private javax.swing.JTextField jTextField18;
  private javax.swing.JTextField jTextField19;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField20;
  private javax.swing.JTextField jTextField21;
  private javax.swing.JTextField jTextField22;
  private javax.swing.JTextField jTextField23;
  private javax.swing.JTextField jTextField24;
  private javax.swing.JTextField jTextField25;
  private javax.swing.JTextField jTextField26;
  private javax.swing.JTextField jTextField27;
  private javax.swing.JTextField jTextField28;
  private javax.swing.JTextField jTextField29;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField30;
  private javax.swing.JTextField jTextField31;
  private javax.swing.JTextField jTextField32;
  private javax.swing.JTextField jTextField33;
  private javax.swing.JTextField jTextField34;
  private javax.swing.JTextField jTextField35;
  private javax.swing.JTextField jTextField36;
  private javax.swing.JTextField jTextField37;
  private javax.swing.JTextField jTextField38;
  private javax.swing.JTextField jTextField39;
  private javax.swing.JTextField jTextField4;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField6;
  private javax.swing.JTextField jTextField7;
  private javax.swing.JTextField jTextField8;
  private javax.swing.JTextField jTextField9;
  private javax.swing.JButton removeAllBtn;
  private javax.swing.JButton removeBtn;
  private javax.swing.JButton setBtn;

 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }
}