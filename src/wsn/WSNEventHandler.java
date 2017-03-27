package wsn;

import java.awt.*;
import javax.swing.*;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import golib.*;
public class WSNEventHandler extends WSNApplication implements Runnable {
  public int programId=0;
  long longWaitTime=31536000000L;
  boolean onoff=false,isSleep=false; 
  WSN wsn;

  ResourceBundle bundle2;
  Thread myThread=null;
  Config currentConfig=new Config();
  Status currentStatus=new Status();
  String currentNameId="",currentDataSrcId="",defaultEventFile="apps"+File.separator+"cr-wsn"+File.separator+"eventhandler",eventFile="",allEventName="All events";
  TreeMap config=new TreeMap(),status=new TreeMap();
  Vector waitV=new Vector(),tmpVector=new Vector();
  DefaultListModel listModel1=new DefaultListModel();

    public WSNEventHandler(){
        initComponents();
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        labelListening.setVisible(false);

        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 95000)/100000;
        int h2=(h * 95000)/100000;

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
        if(o instanceof WSNEventHandler) programId++;
      }
    }
    else programId=wsn.eventHandlers.size();

    this.setTitle((wsn.props.getProperty("eventfile_name_prefix")!=null? wsn.props.getProperty("eventfile_name_prefix"):defaultEventFile)+(programId>0? " - "+(programId+1):""));
    if(wsn.props.getProperty("eventfile_name_prefix")!=null) eventFile=wsn.props.getProperty("eventfile_name_prefix")+"-"+(programId+1)+".txt";
      else eventFile=defaultEventFile+"-"+(programId+1)+".txt";
    eventFile=wsn.w.fileSeparator(eventFile);

    jComboBox1.removeAllItems();
    listModel1.addElement(allEventName);
    myThread=new Thread(this);
    myThread.start();
     if(eventFile!=null && eventFile.length()>0){
         readEventFile(eventFile,2);

          if(wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y")) toStartAll();
     }
}
    @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonGroup2 = new javax.swing.ButtonGroup();
    buttonGroup3 = new javax.swing.ButtonGroup();
    buttonGroup4 = new javax.swing.ButtonGroup();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel2 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    adddBtn = new javax.swing.JButton();
    removeBtn = new javax.swing.JButton();
    setBtn = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jLabel3 = new javax.swing.JLabel();
    jLabel28 = new javax.swing.JLabel();
    jTextField20 = new javax.swing.JTextField();
    startBtn = new javax.swing.JButton();
    stopBtn = new javax.swing.JButton();
    removeAllBtn = new javax.swing.JButton();
    jLabel27 = new javax.swing.JLabel();
    labelListening = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jCheckBox4 = new javax.swing.JCheckBox();
    jTextField21 = new javax.swing.JTextField();
    jLabel32 = new javax.swing.JLabel();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jLabel13 = new javax.swing.JLabel();
    jTextField9 = new javax.swing.JTextField();
    jPanel4 = new javax.swing.JPanel();
    jCheckBox16 = new javax.swing.JCheckBox();
    jComboBox2 = new javax.swing.JComboBox();
    jCheckBox18 = new javax.swing.JCheckBox();
    jTextField8 = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jCheckBox6 = new javax.swing.JCheckBox();
    jPanel5 = new javax.swing.JPanel();
    jCheckBox10 = new javax.swing.JCheckBox();
    jLabel31 = new javax.swing.JLabel();
    jTextField22 = new javax.swing.JTextField();
    jCheckBox7 = new javax.swing.JCheckBox();
    jPanel6 = new javax.swing.JPanel();
    jCheckBox13 = new javax.swing.JCheckBox();
    jLabel33 = new javax.swing.JLabel();
    jTextField23 = new javax.swing.JTextField();
    jCheckBox8 = new javax.swing.JCheckBox();
    jPanel7 = new javax.swing.JPanel();
    jCheckBox1 = new javax.swing.JCheckBox();
    jTextField3 = new javax.swing.JTextField();
    jCheckBox2 = new javax.swing.JCheckBox();
    jTextField4 = new javax.swing.JTextField();
    jPanel9 = new javax.swing.JPanel();
    jCheckBox3 = new javax.swing.JCheckBox();
    jTextField2 = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    jPanel11 = new javax.swing.JPanel();
    jCheckBox12 = new javax.swing.JCheckBox();
    jTextField5 = new javax.swing.JTextField();
    jCheckBox14 = new javax.swing.JCheckBox();
    jTextField6 = new javax.swing.JTextField();
    jCheckBox15 = new javax.swing.JCheckBox();
    jTextField7 = new javax.swing.JTextField();
    jPanel13 = new javax.swing.JPanel();
    jCheckBox17 = new javax.swing.JCheckBox();
    jCheckBox9 = new javax.swing.JCheckBox();
    jCheckBox11 = new javax.swing.JCheckBox();
    jCheckBox19 = new javax.swing.JCheckBox();
    jCheckBox5 = new javax.swing.JCheckBox();
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
    setTitle(bundle.getString("WSNEventHandler.title")); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel3.setLayout(null);

    adddBtn.setText(bundle.getString("WSNEventHandler.adddBtn.text")); 
    adddBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        adddBtnActionPerformed(evt);
      }
    });
    jPanel3.add(adddBtn);
    adddBtn.setBounds(20, 19, 130, 23);

    removeBtn.setText(bundle.getString("WSNEventHandler.removeBtn.text")); 
    removeBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeBtnActionPerformed(evt);
      }
    });
    jPanel3.add(removeBtn);
    removeBtn.setBounds(150, 20, 90, 23);

    setBtn.setText(bundle.getString("WSNEventHandler.setBtn.text")); 
    setBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        setBtnActionPerformed(evt);
      }
    });
    jPanel3.add(setBtn);
    setBtn.setBounds(240, 20, 70, 23);

    jLabel1.setText(bundle.getString("WSNEventHandler.jLabel1.text")); 
    jPanel3.add(jLabel1);
    jLabel1.setBounds(30, 70, 120, 15);
    jPanel3.add(jTextField1);
    jTextField1.setBounds(150, 70, 130, 21);

    jLabel2.setText(bundle.getString("WSNEventHandler.jLabel2.text")); 
    jPanel3.add(jLabel2);
    jLabel2.setBounds(370, 70, 100, 20);

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox1ActionPerformed(evt);
      }
    });
    jPanel3.add(jComboBox1);
    jComboBox1.setBounds(470, 90, 150, 21);

    jLabel3.setText(bundle.getString("WSNEventHandler.jLabel3.text")); 
    jPanel3.add(jLabel3);
    jLabel3.setBounds(20, 120, 90, 20);

    jLabel28.setText(bundle.getString("WSNEventHandler.jLabel28.text")); 
    jPanel3.add(jLabel28);
    jLabel28.setBounds(30, 350, 110, 20);

    jTextField20.setToolTipText(bundle.getString("WSNEventHandler.jTextField20.toolTipText")); 
    jPanel3.add(jTextField20);
    jTextField20.setBounds(470, 70, 150, 21);

    startBtn.setFont(startBtn.getFont());
    startBtn.setText(bundle.getString("WSNEventHandler.startBtn.text")); 
    startBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startBtnActionPerformed(evt);
      }
    });
    jPanel3.add(startBtn);
    startBtn.setBounds(430, 20, 90, 23);

    stopBtn.setFont(stopBtn.getFont());
    stopBtn.setText(bundle.getString("WSNEventHandler.stopBtn.text")); 
    stopBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopBtnActionPerformed(evt);
      }
    });
    jPanel3.add(stopBtn);
    stopBtn.setBounds(520, 20, 100, 23);

    removeAllBtn.setText(bundle.getString("WSNEventHandler.removeAllBtn.text")); 
    removeAllBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeAllBtnActionPerformed(evt);
      }
    });
    jPanel3.add(removeAllBtn);
    removeAllBtn.setBounds(310, 20, 120, 23);

    jLabel27.setFont(jLabel27.getFont().deriveFont(jLabel27.getFont().getSize()+4f));
    jLabel27.setText(bundle.getString("WSNEventHandler.jLabel27.text")); 
    jPanel3.add(jLabel27);
    jLabel27.setBounds(637, 70, 190, 20);

    labelListening.setBackground(new java.awt.Color(102, 153, 255));
    labelListening.setFont(labelListening.getFont().deriveFont(labelListening.getFont().getStyle() | java.awt.Font.BOLD));
    labelListening.setForeground(new java.awt.Color(255, 255, 0));
    labelListening.setText(bundle.getString("WSNEventHandler.labelListening.text")); 
    labelListening.setOpaque(true);
    jPanel3.add(labelListening);
    labelListening.setBounds(640, 120, 120, 20);

    jLabel6.setFont(jLabel6.getFont());
    jLabel6.setText(bundle.getString("WSNEventHandler.jLabel6.text")); 
    jPanel3.add(jLabel6);
    jLabel6.setBounds(640, 100, 180, 15);

    jButton1.setFont(jButton1.getFont());
    jButton1.setText(bundle.getString("WSNEventHandler.jButton1.text")); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton1);
    jButton1.setBounds(620, 20, 100, 23);

    jButton2.setFont(jButton2.getFont());
    jButton2.setText(bundle.getString("WSNEventHandler.jButton2.text")); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel3.add(jButton2);
    jButton2.setBounds(720, 20, 90, 23);

    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox4.setText(bundle.getString("WSNEventHandler.jCheckBox4.text")); 
    jCheckBox4.setActionCommand(bundle.getString("WSNEventHandler.jCheckBox4.actionCommand")); 
    jPanel1.add(jCheckBox4);

    jTextField21.setToolTipText(bundle.getString("WSNEventHandler.jTextField21.toolTipText")); 
    jTextField21.setPreferredSize(new java.awt.Dimension(100, 21));
    jPanel1.add(jTextField21);

    jLabel32.setText(bundle.getString("WSNEventHandler.jLabel32.text")); 
    jPanel1.add(jLabel32);

    buttonGroup2.add(jRadioButton3);
    jRadioButton3.setText(bundle.getString("WSNEventHandler.jRadioButton3.text")); 
    jPanel1.add(jRadioButton3);

    buttonGroup2.add(jRadioButton4);
    jRadioButton4.setText(bundle.getString("WSNEventHandler.jRadioButton4.text")); 
    jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton4ActionPerformed(evt);
      }
    });
    jPanel1.add(jRadioButton4);

    jLabel13.setText(bundle.getString("WSNEventHandler.jLabel13.text")); 
    jPanel1.add(jLabel13);

    jTextField9.setPreferredSize(new java.awt.Dimension(150, 21));
    jPanel1.add(jTextField9);

    jPanel3.add(jPanel1);
    jPanel1.setBounds(60, 380, 660, 33);

    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox16.setText(bundle.getString("WSNEventHandler.jCheckBox16.text")); 
    jPanel4.add(jCheckBox16);

    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CheckSum", "Modbus CRC", "0x0D" }));
    jPanel4.add(jComboBox2);

    jCheckBox18.setText(bundle.getString("WSNEventHandler.jCheckBox18.text")); 
    jPanel4.add(jCheckBox18);

    jTextField8.setText(bundle.getString("WSNEventHandler.jTextField8.text")); 
    jTextField8.setPreferredSize(new java.awt.Dimension(25, 25));
    jPanel4.add(jTextField8);

    jLabel5.setText(bundle.getString("WSNEventHandler.jLabel5.text")); 
    jPanel4.add(jLabel5);

    jCheckBox6.setText(bundle.getString("WSNEventHandler.jCheckBox6.text")); 
    jPanel4.add(jCheckBox6);

    jPanel3.add(jPanel4);
    jPanel4.setBounds(100, 420, 650, 40);

    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox10.setText(bundle.getString("WSNEventHandler.jCheckBox10.text")); 
    jPanel5.add(jCheckBox10);

    jLabel31.setText(bundle.getString("WSNEventHandler.jLabel31.text")); 
    jPanel5.add(jLabel31);

    jTextField22.setToolTipText(bundle.getString("WSNEventHandler.jTextField22.toolTipText")); 
    jTextField22.setPreferredSize(new java.awt.Dimension(100, 21));
    jPanel5.add(jTextField22);

    jCheckBox7.setText(bundle.getString("WSNEventHandler.jCheckBox7.text")); 
    jPanel5.add(jCheckBox7);

    jPanel3.add(jPanel5);
    jPanel5.setBounds(60, 460, 670, 40);

    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox13.setText(bundle.getString("WSNEventHandler.jCheckBox13.text")); 
    jPanel6.add(jCheckBox13);

    jLabel33.setText(bundle.getString("WSNEventHandler.jLabel33.text")); 
    jPanel6.add(jLabel33);

    jTextField23.setText(bundle.getString("WSNEventHandler.jTextField23.text")); 
    jTextField23.setToolTipText(bundle.getString("WSNEventHandler.jTextField23.toolTipText")); 
    jTextField23.setPreferredSize(new java.awt.Dimension(120, 21));
    jPanel6.add(jTextField23);

    jCheckBox8.setText(bundle.getString("WSNEventHandler.jCheckBox8.text")); 
    jPanel6.add(jCheckBox8);

    jPanel3.add(jPanel6);
    jPanel6.setBounds(60, 500, 680, 40);

    jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox1.setText(bundle.getString("WSNEventHandler.jCheckBox1.text")); 
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox1ActionPerformed(evt);
      }
    });
    jPanel7.add(jCheckBox1);

    jTextField3.setPreferredSize(new java.awt.Dimension(150, 25));
    jPanel7.add(jTextField3);

    jCheckBox2.setText(bundle.getString("WSNEventHandler.jCheckBox2.text")); 
    jPanel7.add(jCheckBox2);

    jTextField4.setPreferredSize(new java.awt.Dimension(150, 21));
    jPanel7.add(jTextField4);

    jPanel3.add(jPanel7);
    jPanel7.setBounds(30, 190, 760, 35);

    jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox3.setText(bundle.getString("WSNEventHandler.jCheckBox3.text")); 
    jPanel9.add(jCheckBox3);

    jTextField2.setText(bundle.getString("WSNEventHandler.jTextField2.text")); 
    jTextField2.setPreferredSize(new java.awt.Dimension(50, 21));
    jPanel9.add(jTextField2);

    jLabel4.setText(bundle.getString("WSNEventHandler.jLabel4.text")); 
    jPanel9.add(jLabel4);

    jPanel3.add(jPanel9);
    jPanel9.setBounds(30, 270, 410, 40);

    jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox12.setText(bundle.getString("WSNEventHandler.jCheckBox12.text")); 
    jPanel11.add(jCheckBox12);

    jTextField5.setPreferredSize(new java.awt.Dimension(100, 21));
    jPanel11.add(jTextField5);

    jCheckBox14.setText(bundle.getString("WSNEventHandler.jCheckBox14.text")); 
    jPanel11.add(jCheckBox14);

    jTextField6.setPreferredSize(new java.awt.Dimension(100, 21));
    jPanel11.add(jTextField6);

    jCheckBox15.setText(bundle.getString("WSNEventHandler.jCheckBox15.text")); 
    jPanel11.add(jCheckBox15);

    jTextField7.setPreferredSize(new java.awt.Dimension(100, 21));
    jPanel11.add(jTextField7);

    jPanel3.add(jPanel11);
    jPanel11.setBounds(30, 230, 780, 33);

    jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jCheckBox17.setText(bundle.getString("WSNEventHandler.jCheckBox17.text")); 
    jPanel13.add(jCheckBox17);

    jCheckBox9.setText(bundle.getString("WSNEventHandler.jCheckBox9.text")); 
    jPanel13.add(jCheckBox9);

    jCheckBox11.setText(bundle.getString("WSNEventHandler.jCheckBox11.text")); 
    jPanel13.add(jCheckBox11);

    jCheckBox19.setFont(jCheckBox19.getFont());
    jCheckBox19.setText(bundle.getString("WSNEventHandler.jCheckBox19.text")); 
    jPanel13.add(jCheckBox19);

    jCheckBox5.setText(bundle.getString("WSNEventHandler.jCheckBox5.text")); 
    jPanel13.add(jCheckBox5);

    jPanel3.add(jPanel13);
    jPanel13.setBounds(30, 150, 770, 40);

    jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

    jTabbedPane1.addTab(bundle.getString("WSNEventHandler.jPanel2.TabConstraints.tabTitle"), jPanel2); 

    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    jList1.setModel(listModel1);
    jList1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jList1MouseClicked(evt);
      }
    });
    jList1.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        jList1KeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        jList1KeyReleased(evt);
      }
    });
    jScrollPane1.setViewportView(jList1);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

    jMenu1.setText(bundle.getString("WSNEventHandler.jMenu1.text")); 
    jMenu1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenu1ActionPerformed(evt);
      }
    });

    jMenuItem1.setText(bundle.getString("WSNEventHandler.jMenuItem1.text")); 
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);

    jMenuItem2.setText(bundle.getString("WSNEventHandler.jMenuItem2.text")); 
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem2);

    jMenuItem3.setText(bundle.getString("WSNEventHandler.jMenuItem3.text")); 
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem3);

    jMenuBar1.add(jMenu1);

    jMenu2.setText(bundle.getString("WSNEventHandler.jMenu2.text")); 
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    pack();
  }
public void run(){
    while(true){
        while(waitV.size()>0){

          DataClass dataClass=(DataClass)waitV.get(0);

          chkConditions(2,dataClass.data,allEventName);
          TreeMap tmConf=(TreeMap) config.clone();
          Iterator it=tmConf.values().iterator();
          for(;it.hasNext();){
              Config cf=(Config)it.next();
              if(dataClass.dataSrc.equals(cf.conf[4])) chkConditions(2,dataClass.data,cf.conf[0]);
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

boolean chkConditions(int type,String da,String nameId){

    Config cf=(Config)config.get(nameId);
        if(cf!=null && cf.conf[0].equals(nameId)){
          Status sta=(Status)status.get(cf.conf[0]);
          sta.stat[15]=da;
          if(wsn.w.checkOneVar(sta.longValue[1],0)){
            boolean found=false;
            switch(type){
                case 1:
                    if(wsn.w.checkOneVar(cf.conf[1],11)) found=true;
                    break;
                case 2:
                    if(wsn.w.checkOneVar(cf.conf[1],4)) found=true;
                    else if(wsn.w.checkOneVar(cf.conf[1],5) && da.equals(cf.conf[2])) found=true;
                    else if(wsn.w.checkOneVar(cf.conf[1],6) && !da.equals(cf.conf[3])) found=true;
                    else if(wsn.w.checkOneVar(cf.conf[1],13) && wsn.isNumeric(da) && Double.parseDouble(da)>Double.parseDouble(cf.conf[10])) found=true;
                    else if(wsn.w.checkOneVar(cf.conf[1],14) && wsn.isNumeric(da) && Double.parseDouble(da)==Double.parseDouble(cf.conf[11])) found=true;
                    else if(wsn.w.checkOneVar(cf.conf[1],15) && wsn.isNumeric(da) && Double.parseDouble(da)<Double.parseDouble(cf.conf[12])) found=true;
                    break;
                case 3:
                    if(wsn.w.checkOneVar(cf.conf[1],12)) found=true;
                    break;
                case 4:
                    if(wsn.w.checkOneVar(cf.conf[1],16)) found=true;
                    break;
                case 5:
                    if(wsn.w.checkOneVar(cf.conf[1],17)) found=true;
                    break;
                case 6:
                    if(wsn.w.checkOneVar(cf.conf[1],7) && wsn.w.checkOneVar(sta.longValue[1],2) && (System.currentTimeMillis()-sta.longValue[7])>Long.parseLong(cf.conf[9])) {
                      found=true;
                      wsn.w.addOneVar(sta.longValue[1],2);
                      status.put(cf.conf[0], sta);
                    }
                    break;
            }
            if(found) {
                if((wsn.w.checkOneVar(cf.conf[1],56) && (!wsn.w.checkOneVar(cf.conf[1],49) || sta.longValue[3]<1)) ||
                        (wsn.w.checkOneVar(cf.conf[1],54) && (!wsn.w.checkOneVar(cf.conf[1],50) || sta.longValue[4]<1)) ||
                        (wsn.w.checkOneVar(cf.conf[1],52) && (!wsn.w.checkOneVar(cf.conf[1],48) || sta.longValue[2]<1))){
                wsn.actionThread.setAction(cf, sta, 2);
                if(wsn.w.checkOneVar(cf.conf[1],52)){
                      sta.longValue[2]++;
                      status.put(cf.conf[0], sta);
                }
                if(wsn.w.checkOneVar(cf.conf[1],54)){
                      sta.longValue[4]++;
                      status.put(cf.conf[0], sta);
                }
                if(wsn.w.checkOneVar(cf.conf[1],56)){
                      sta.longValue[3]++;
                      status.put(cf.conf[0], sta);
                }
                }
              sta.longValue[6]++;
              status.put(cf.conf[0], sta);
              getCount();
            }
           if(type!=6) {sta.longValue[7]=System.currentTimeMillis(); status.put(cf.conf[0], sta);}
            }
          }

    return true;
}

public void setData(long time,String nodeId, String dataSrc,String data){

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

    if(statusCode==1) statusCode=1;
    else if(statusCode==2) statusCode=3;
    for(int i=0;i<dataSrc.length;i++){
          TreeMap tmConf=(TreeMap) config.clone();
          Iterator it=tmConf.values().iterator();
          for(;it.hasNext();){
              Config cf=(Config)it.next();
              if(dataSrc[i].equals(cf.conf[4])) chkConditions(statusCode,"",cf.conf[0]);
          }
    }
}

public void toStartAll(){
  jList1.setSelectedIndex(0);
  changeItem();
          Iterator it=status.keySet().iterator();
          for(;it.hasNext();){
               String key=(String)it.next();
               Status sta=(Status)status.get(key);
               Config cf=(Config)config.get(key);
               if(!wsn.w.checkOneVar(sta.longValue[1],0)){
               sta.longValue[0]=System.currentTimeMillis();
               sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],0);
               sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
               sta.longValue[2]=0;
               sta.longValue[3]=0;
               sta.longValue[4]=0;
               sta.longValue[6]=0;
               status.put(key, sta);
               chkConditions(4,"",cf.conf[0]);
               }
          }  
}
public void toStopAll(){
          Iterator it=status.keySet().iterator();
          for(;it.hasNext();){
               String key=(String)it.next();
               Status sta=(Status)status.get(key);
               Config cf=(Config)config.get(key);
               if(wsn.w.checkOneVar(sta.longValue[1],0)){
               chkConditions(5,"",cf.conf[0]);
               sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
               sta.longValue[5]=System.currentTimeMillis();
               status.put(key, sta);
               }
          }
}

public void toStart(String nameid){
    status.put(currentDataSrcId,currentStatus);
   if(nameid.equals("0")) nameid=allEventName;
    if(status.get(nameid)!=null){
         Status sta=((Status)status.get(nameid));
         Config cf=(Config)config.get(nameid);
         if(!wsn.w.checkOneVar(sta.longValue[1], 0)){
           sta.longValue[0]=System.currentTimeMillis();
           sta.longValue[1]=wsn.w.addOneVar(sta.longValue[1],0);
           sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],2);
           sta.longValue[2]=0;
           sta.longValue[3]=0;
           sta.longValue[4]=0;
           sta.longValue[6]=0;
           status.put(nameid, sta);
           chkConditions(4,"",cf.conf[0]);
        }
    }
}

public void toStop(String nameid){
   if(nameid.equals("0")) nameid=allEventName;
    if(status.get(nameid)!=null){
        Status sta=(Status)status.get(nameid);
        Config cf=(Config) config.get(nameid);
        if(wsn.w.checkOneVar(sta.longValue[1], 0)){
           chkConditions(5,"",cf.conf[0]);
           sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
           sta.longValue[5]=System.currentTimeMillis();
           status.put(nameid, sta);
        }
    }
}
private void formWindowClosing(java.awt.event.WindowEvent evt) {
  if(wsn==null) System.exit(0);
  else setVisible(false);
}
public void getDataSrcList(){
    String cmdStr="performmessage wsn.WSN getdatasource ";
    wsn.w.sendToAll(cmdStr);
}
void getCount(){
    long cnt=currentStatus.longValue[2];
    if(currentStatus.longValue[3]>cnt) cnt=currentStatus.longValue[3];
    if(currentStatus.longValue[4]> cnt) cnt=currentStatus.longValue[4];
    jLabel6.setText("event : "+currentStatus.longValue[6]+" , action : "+cnt);
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
private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void adddBtnActionPerformed(java.awt.event.ActionEvent evt) {
 reset();
}
private void reset(){
 jTextField1.setText("");
 jTextField20.setText("");
 jComboBox1.removeAllItems();
 getDataSrcList();
 jTextField2.setText("1000");
 jTextField3.setText("");
 jTextField4.setText("");
 jCheckBox1.setSelected(false);
 jCheckBox2.setSelected(false);
 jCheckBox3.setSelected(false);
 jCheckBox4.setSelected(false);
 jCheckBox5.setSelected(false);
 jCheckBox6.setSelected(false);
 jCheckBox7.setSelected(false);
 jCheckBox8.setSelected(false);
 jCheckBox10.setSelected(false);
 jCheckBox9.setSelected(false);
 jCheckBox11.setSelected(false);
 jCheckBox12.setSelected(false);
 jCheckBox14.setSelected(false);
 jCheckBox15.setSelected(false);
 jCheckBox19.setSelected(false);
 jTextField5.setText("");
 jTextField6.setText("");
 jTextField7.setText("");
 jTextField8.setText("10");
 jRadioButton4.setSelected(true);
 jTextField9.setText("");
 jTextField21.setText("");
 jTextField22.setText("");
 jCheckBox13.setSelected(false);
 jCheckBox16.setSelected(false);
 jTextField23.setText("");
 jCheckBox17.setSelected(false);
 jCheckBox18.setSelected(false);
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
    if(jList1.getSelectedValue()==null) return;
    currentNameId=(String)jList1.getSelectedValue();

    if(config.get(currentNameId)!=null){
    currentConfig=(Config)config.get(currentNameId);
    currentStatus=(Status)status.get(currentNameId);
    getDataSrcList();
    setUI();
    }
    

}
void setUI(){
    currentDataSrcId=currentConfig.conf[4];
    jTextField1.setText(currentNameId);
    jTextField20.setText(currentDataSrcId);
    jTextField2.setText(currentConfig.conf[9]);
    jTextField3.setText(currentConfig.conf[2]);
    jTextField4.setText(currentConfig.conf[3]);
    jTextField21.setText(currentConfig.conf[22]);
    jTextField9.setText(currentConfig.conf[23]);
    jTextField22.setText(currentConfig.conf[24]);
    jTextField23.setText(currentConfig.conf[28]);
    jTextField5.setText(currentConfig.conf[10]);
    jTextField6.setText(currentConfig.conf[11]);
    jTextField7.setText(currentConfig.conf[12]);
    jTextField8.setText(currentConfig.conf[31]);
    currentConfig.longValue[0]=Long.parseLong(currentConfig.conf[1]);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],5)) jCheckBox1.setSelected(true); else  jCheckBox1.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],6)) jCheckBox2.setSelected(true); else  jCheckBox2.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],7)) jCheckBox3.setSelected(true); else  jCheckBox3.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],54)) jCheckBox4.setSelected(true); else  jCheckBox4.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],55)) jRadioButton3.setSelected(true); else  jRadioButton4.setSelected(true);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],56)) jCheckBox10.setSelected(true); else  jCheckBox10.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],4)) jCheckBox5.setSelected(true); else  jCheckBox5.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],52)) jCheckBox13.setSelected(true); else  jCheckBox13.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],50)) jCheckBox6.setSelected(true); else  jCheckBox6.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],49)) jCheckBox7.setSelected(true); else  jCheckBox7.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],48)) jCheckBox8.setSelected(true); else  jCheckBox8.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],11)) jCheckBox9.setSelected(true); else  jCheckBox9.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],12)) jCheckBox11.setSelected(true); else  jCheckBox11.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],13)) jCheckBox12.setSelected(true); else  jCheckBox12.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],14)) jCheckBox14.setSelected(true); else  jCheckBox14.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],15)) jCheckBox15.setSelected(true); else  jCheckBox15.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],46)) jCheckBox16.setSelected(true); else  jCheckBox16.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],16)) jCheckBox17.setSelected(true); else  jCheckBox17.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],17)) jCheckBox19.setSelected(true); else  jCheckBox19.setSelected(false);
    if(wsn.w.checkOneVar(currentConfig.longValue[0],45)) jCheckBox18.setSelected(true); else  jCheckBox18.setSelected(false);
    jComboBox2.setSelectedItem(currentConfig.conf[165]);
 }
private void setBtnActionPerformed(java.awt.event.ActionEvent evt) {
  long onevar=0;

  String name=jTextField1.getText().trim();
  if(name.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg1")); return;}
  String dataSrc=jTextField20.getText().trim().toUpperCase();
  if(dataSrc.length()<3)  {JOptionPane.showMessageDialog(this,"\""+dataSrc+"\" "+bundle2.getString("WSNEventHandler.xy.msg2")); return;}

    if(jCheckBox1.isSelected()) onevar=wsn.w.addOneVar(onevar,5);
    if(jCheckBox2.isSelected()) onevar=wsn.w.addOneVar(onevar,6);
    if(jCheckBox3.isSelected()) onevar=wsn.w.addOneVar(onevar,7);
    if(jCheckBox4.isSelected()) onevar=wsn.w.addOneVar(onevar,54);
    if(jRadioButton3.isSelected()) onevar=wsn.w.addOneVar(onevar,55);
    if(jCheckBox10.isSelected()) onevar=wsn.w.addOneVar(onevar,56);
    if(jCheckBox5.isSelected()) onevar=wsn.w.addOneVar(onevar,4);
    if(jCheckBox6.isSelected()) onevar=wsn.w.addOneVar(onevar,50);
    if(jCheckBox7.isSelected()) onevar=wsn.w.addOneVar(onevar,49);
    if(jCheckBox8.isSelected()) onevar=wsn.w.addOneVar(onevar,48);
    if(jCheckBox9.isSelected()) onevar=wsn.w.addOneVar(onevar,11);
    if(jCheckBox11.isSelected()) onevar=wsn.w.addOneVar(onevar,12);
    if(jCheckBox12.isSelected()) onevar=wsn.w.addOneVar(onevar,13);
    if(jCheckBox14.isSelected()) onevar=wsn.w.addOneVar(onevar,14);
    if(jCheckBox15.isSelected()) onevar=wsn.w.addOneVar(onevar,15);
    if(jCheckBox16.isSelected()) onevar=wsn.w.addOneVar(onevar,46);
    if(jCheckBox17.isSelected()) onevar=wsn.w.addOneVar(onevar,16);
    if(jCheckBox18.isSelected()) onevar=wsn.w.addOneVar(onevar,45);
    if(jCheckBox19.isSelected()) onevar=wsn.w.addOneVar(onevar,17);
  String chksumType=(String)jComboBox2.getSelectedItem();
  String equals=jTextField3.getText().trim(),notEquals=jTextField4.getText().trim(),controlTo=jTextField21.getText().trim().toUpperCase(),
          controlValue=jTextField9.getText().trim(),disconnectTo=jTextField22.getText().trim().toUpperCase(),javaActionClass=jTextField23.getText().trim(),
          idleTime=jTextField2.getText().trim(),largerThan=jTextField5.getText().trim(),equalTo=jTextField6.getText().trim(),
          lessThan=jTextField7.getText().trim(),interval=jTextField8.getText().trim();
  if(javaActionClass.length()>6 && javaActionClass.indexOf(".class")==javaActionClass.length()-6) javaActionClass=javaActionClass.substring(0,javaActionClass.indexOf(".class"));
  if(jCheckBox18.isSelected()) {
        if(interval.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg3")); return;}
        if(!wsn.isNumeric(interval)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg4")); return;}
    }
  if(jCheckBox13.isSelected()) {
        onevar=wsn.w.addOneVar(onevar,52);
        if(javaActionClass.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg5")); return;}
        if(!wsn.loadClass(javaActionClass,2)) {JOptionPane.showMessageDialog(this, "java action class "+javaActionClass+" not exist or not implements WSNAction interface."); return;}
    }
  String eventConf[]={name,String.valueOf(onevar),equals,notEquals,dataSrc,
      "","","","",idleTime,
      largerThan,equalTo,lessThan,"","",
      "","","","","",
      "","",controlTo,controlValue,disconnectTo,
      "","","",javaActionClass,"",
      "",interval,"","","",
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
  Config newConfig=new Config(new long[]{Long.parseLong(eventConf[1])},new int[]{},new double[]{},null,eventConf);
  Status newStatus=new Status();
  if(jCheckBox12.isSelected()) {
        if(largerThan.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg6")); return;}
        if(!wsn.isNumeric(largerThan)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg7")); return;}
   }
  if(jCheckBox14.isSelected()) {
        if(equalTo.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg8")); return;}
        if(!wsn.isNumeric(equalTo)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg9")); return;}
   }
  if(jCheckBox15.isSelected()) {
        if(lessThan.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg10")); return;}
        if(!wsn.isNumeric(lessThan)) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg11")); return;}
   }
  if(jCheckBox4.isSelected()){
     if(controlTo.length()<4)  {JOptionPane.showMessageDialog(this,"\""+controlTo+"\" "+ bundle2.getString("WSNEventHandler.xy.msg16")); return;}
     if(controlValue.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNEventHandler.xy.msg17")); return;}
    }
  if(jCheckBox10.isSelected()){
     if(disconnectTo.length()<4)  {JOptionPane.showMessageDialog(this,"\""+disconnectTo+"\" "+ bundle2.getString("WSNEventHandler.xy.msg18")); return;}
  }  if(config.containsKey(name)){
      Config oldConfig=(Config) config.get(name);
      Status oldStatus=(Status) status.get(name);
      int an=JOptionPane.showConfirmDialog(this, "replace old data (name id="+name+")?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
      if(an==JOptionPane.YES_OPTION){

          config.put(name, newConfig);
          if(currentNameId.equals(newConfig.conf[0])) currentConfig=newConfig;
      } else return;
  } else {
      config.put(name, newConfig); 
      if(config.get(allEventName)==null) {
          String conf2[]=new String[newConfig.conf.length];
          for(int i=0;i<conf2.length;i++) conf2[i]=newConfig.conf[i];
          Config newConfigAll=new Config(new long[]{Long.parseLong(eventConf[1])},new int[]{},new double[]{},null,conf2);
          newConfigAll.conf[0]=allEventName;
          newConfigAll.conf[4]="0";
          config.put(allEventName,newConfigAll);

      } 
      status.put(name,newStatus);
      if(status.get(allEventName)==null)status.put(allEventName, newStatus); 
      listModel1.addElement(name);

  }
  if(currentNameId==null || currentNameId.length()<1) {
      currentNameId=name;
      currentDataSrcId=dataSrc;
      currentConfig=(Config)config.get(currentNameId);
      currentStatus=(Status)status.get(currentNameId);
  }
  jList1.setSelectedValue(currentNameId, true);
  JOptionPane.showMessageDialog(this, "\""+name+"\" "+bundle2.getString("WSNEventHandler.xy.msg12"));

}
public void setBlink(boolean onoff){
     this.onoff=onoff;
     if(onoff){
         if(wsn!=null) jLabel27.setText(wsn.formatter.format(new Date()));
          TreeMap tmConf=(TreeMap) config.clone();
          Iterator it=tmConf.values().iterator();
          for(;it.hasNext();){
              Config cf=(Config)it.next();
              chkConditions(6,"",cf.conf[0]);
          }
     } else{

     }
     if(currentStatus!=null && wsn.w.checkOneVar(currentStatus.longValue[1],0)) {labelListening.setVisible(onoff);}
     else {labelListening.setVisible(false);}
  }
private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {
  if(jList1.getSelectedValue()!=null){
      String n=(String)jList1.getSelectedValue();
      listModel1.removeElement(n);
      boolean found=false;
      currentNameId="";
      currentDataSrcId="";
      reset();
  }
}

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
  formWindowClosing(null);
}

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
  JFileChooser chooser = new JFileChooser(eventFile);
  chooser.setDialogTitle(bundle2.getString("WSNEventHandler.xy.msg13"));
  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  if(eventFile.length()>0){
    chooser.setSelectedFile(new File(new File(eventFile).getName()));
  }
  int returnVal = chooser.showDialog(this,bundle2.getString("WSNEventHandler.xy.msg14"));
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      String tempEventFile=chooser.getSelectedFile().getAbsolutePath();

     readEventFile(tempEventFile,1);
  }
}

private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
  JFileChooser chooser = new JFileChooser(eventFile);
  chooser.setDialogTitle(bundle2.getString("WSNEventHandler.xy.msg19"));
  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
  if(eventFile.length()>0){
    chooser.setSelectedFile(new File(new File(eventFile).getName()));
  }
  int returnVal = chooser.showDialog(this,bundle2.getString("WSNEventHandler.xy.msg20"));
  if(returnVal == JFileChooser.APPROVE_OPTION) {
      String tempEventFile=chooser.getSelectedFile().getAbsolutePath();
      File f2=new File(tempEventFile);
      boolean save=false;
      if(f2.exists()){
         int result = JOptionPane.showConfirmDialog(this, bundle2.getString("WSNEventHandler.xy.msg21"), bundle2.getString("WSNEventHandler.xy.msg22"), JOptionPane.YES_NO_CANCEL_OPTION);
         if (result == JOptionPane.YES_OPTION) save=true; else save=false;
      } else save=true;
      if(save){
        saveEventFile(tempEventFile,1);
      }
  }
}

private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {

}

private void removeAllBtnActionPerformed(java.awt.event.ActionEvent evt) {

      listModel1.removeAllElements();
      listModel1.addElement(allEventName);
      status.clear();
      config.clear();
      currentNameId="";
      currentDataSrcId="";
      reset();
}

private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList1.getSelectedValue()==null) return;
    currentNameId=(String)jList1.getSelectedValue();
    toStart(currentNameId);
}

private void jList1KeyPressed(java.awt.event.KeyEvent evt) {

}

private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList1.getSelectedValue()==null) return;
    currentNameId=(String)jList1.getSelectedValue();
    toStop(currentNameId);
}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  toStartAll();
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
 toStopAll();
}

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
  if(jComboBox1.getSelectedItem()!=null) jTextField20.setText((String)jComboBox1.getSelectedItem());
}

public void onExit(int type){
   Iterator it=status.keySet().iterator();
           for(;it.hasNext();){
             String key=(String)it.next();
             Status sta=(Status)status.get(key);
             sta.longValue[1]=wsn.w.removeOneVar(sta.longValue[1],0);
             sta.longValue[2]=0L;
             sta.longValue[3]=0L;
             sta.longValue[4]=0L;
             sta.longValue[6]=0L;
             status.put(key, sta);
           }
    saveEventFile(eventFile,2);
}
  public boolean runInBackground(){
    return false;
  }

public void readEventFile(String eventFile2,int type){
    if(eventFile2!=null && eventFile2.length()>0){
       File f=new File(eventFile2);
       if(f.exists() && f.isFile()){
         StringBuilder sb=new StringBuilder();

         listModel1.removeAllElements();
         listModel1.addElement(allEventName);
         config.clear();
         status.clear();
         currentNameId="";
         currentDataSrcId="";
         currentConfig=null;
         try{
           FileInputStream in=new FileInputStream(eventFile2);
           BufferedReader d= new BufferedReader(new InputStreamReader(in));
           while(true){
	     String str1=d.readLine();
	     if(str1==null) {in.close(); d.close(); break; }

             if(str1.length()>0){
               int inx=str1.indexOf("config_");
               int inx2=str1.indexOf("status_");
               int inx3=str1.indexOf("=");
               int inx4=str1.indexOf("current");
               if(inx3>-1){
                 if(inx==0 && str1.length()>7){
                   String nameId=str1.substring(7,inx3);
                   String conf[]=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());

                     if(conf.length<200){
                     String conf2[]=new String[200];
                     for(int i=0;i<conf.length;i++) conf2[i]=conf[i];
                     for(int i=conf.length;i<200;i++) conf2[i]="";
                     conf=conf2;
                   }
                   config.put(conf[0], new Config(conf));
                   if(!conf[0].equals(allEventName)) listModel1.addElement(conf[0]);
                 } else if(inx2==0 && str1.length()>7){
                   String nameId=str1.substring(7,inx3);
                   String [] stat=wsn.w.csvLineToArray(str1.substring(inx3+1).trim());
                   if(stat.length>24) status.put(nameId, new Status(new long[]{Long.parseLong(stat[0]),Long.parseLong(stat[1]),Long.parseLong(stat[2]),Long.parseLong(stat[3]),Long.parseLong(stat[4]),
                     Long.parseLong(stat[20]),Long.parseLong(stat[21]),Long.parseLong(stat[22]),Long.parseLong(stat[23]),Long.parseLong(stat[24])},
                           new int[]{Integer.parseInt(stat[5]),Integer.parseInt(stat[6]),Integer.parseInt(stat[7]),Integer.parseInt(stat[8]),Integer.parseInt(stat[9])},
                           new double[]{Double.parseDouble(stat[10]),Double.parseDouble(stat[11]),Double.parseDouble(stat[12]),Double.parseDouble(stat[13]),Double.parseDouble(stat[14])},
                           stat)); 
                   else status.put(nameId,new Status());
                 } else if(inx4==0 && str1.length()>7){
                     String tmp[]=wsn.w.csvLineToArray(str1.substring(8).trim());
                     currentNameId=tmp[0];
                     currentDataSrcId=tmp[1];
                 }
                 if(currentNameId!=null && config.get(currentNameId)!=null){

                     currentConfig=(Config)config.get(currentNameId);
                     currentStatus=(Status)status.get(currentNameId);
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
             sta.longValue[2]=0L;
             sta.longValue[3]=0L;
             sta.longValue[4]=0L;
             sta.longValue[6]=0L;
             status.put(key, sta);
           }
           }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){

       if(type==1) JOptionPane.showMessageDialog(this,"Error in reading "+eventFile2+" file.");

        e.printStackTrace();
    }
    }else {
           if(type==1) JOptionPane.showMessageDialog(this,"Warning: event file "+eventFile2+" not exist.");

      }
}
}

 public void saveEventFile(String eventFile2,int type){
       StringBuffer sb=new StringBuffer("current="+wsn.w.toCsv(currentNameId)+","+currentDataSrcId);
        for(Iterator it=config.values().iterator();it.hasNext();){
            Config cfg=(Config)it.next();
            sb.append("\r\nconfig_"+cfg.conf[0]+"="+wsn.w.arrayToCsvLine(cfg.conf));
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
		          FileOutputStream out = new FileOutputStream (eventFile2);
		          byte [] b=sb.toString().getBytes();
		          out.write(b);
		          out.close();
      }catch(IOException e){e.printStackTrace();}

      if(type==1) JOptionPane.showMessageDialog(this,"\""+eventFile2+"\" "+bundle2.getString("WSNEventHandler.xy.msg15"));
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

  private javax.swing.JButton adddBtn;
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.ButtonGroup buttonGroup2;
  private javax.swing.ButtonGroup buttonGroup3;
  private javax.swing.ButtonGroup buttonGroup4;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
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
  private javax.swing.JCheckBox jCheckBox3;
  private javax.swing.JCheckBox jCheckBox4;
  private javax.swing.JCheckBox jCheckBox5;
  private javax.swing.JCheckBox jCheckBox6;
  private javax.swing.JCheckBox jCheckBox7;
  private javax.swing.JCheckBox jCheckBox8;
  private javax.swing.JCheckBox jCheckBox9;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel2;
  public javax.swing.JLabel jLabel27;
  private javax.swing.JLabel jLabel28;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel31;
  private javax.swing.JLabel jLabel32;
  private javax.swing.JLabel jLabel33;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JList jList1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel11;
  private javax.swing.JPanel jPanel13;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField20;
  private javax.swing.JTextField jTextField21;
  private javax.swing.JTextField jTextField22;
  private javax.swing.JTextField jTextField23;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField4;
  private javax.swing.JTextField jTextField5;
  private javax.swing.JTextField jTextField6;
  private javax.swing.JTextField jTextField7;
  private javax.swing.JTextField jTextField8;
  private javax.swing.JTextField jTextField9;
  private javax.swing.JLabel labelListening;
  private javax.swing.JButton removeAllBtn;
  private javax.swing.JButton removeBtn;
  private javax.swing.JButton setBtn;
  private javax.swing.JButton startBtn;
  private javax.swing.JButton stopBtn;

 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }
}