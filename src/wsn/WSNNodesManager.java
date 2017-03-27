package wsn;

import java.awt.*;
import java.util.*;
import infinity.client.*;
import javax.swing.*;
import javax.swing.text.*;

import java.io.*;

public class WSNNodesManager extends javax.swing.JFrame {
   WSN wsn;

   ResourceBundle bundle2;
   WSNSocketportOptions socketOptions;
   WSNSerialportOptions serialOptions;
   String nodeConfig="",
           socketportConfig="",
           serialportConfig="";
   public String currentItemData3[]={},currentViewId3="",currentViewId3_1="",currentViewId3_2="",currentViewId3_3="",
           currentInnerMemberId3="",sep=""+(char)0,pid="",lastId3="",lastId3_1="",lastId3_2="",lastId3_3="",currentViewStatus3[]=null;
   public DefaultListModel listModel3=new DefaultListModel(),connListModel=new DefaultListModel(), skPortListModel=new DefaultListModel(), srPortListModel=new DefaultListModel();
   public WSNNodesManager(WSN wsn,String pid){
        initComponents();
        jList3.setPrototypeCellValue("256.256.256.256-100");
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        this.wsn=wsn;

        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 90000)/100000;
        int h2=(h * 90000)/100000;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        socketPortList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        connectionList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        serialPortList = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jComboBox9 = new javax.swing.JComboBox();
        jButton9 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel46 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel50 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        setTitle(bundle.getString("WSNNodesManager.title")); 
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jList3.setModel(listModel3);
        jList3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList3MouseClicked(evt);
            }
        });
        jList3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList3KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jList3);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(null);

        socketPortList.setFont(socketPortList.getFont().deriveFont(socketPortList.getFont().getSize()+6f));
        socketPortList.setModel(skPortListModel);
        socketPortList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                socketPortListMouseClicked(evt);
            }
        });
        socketPortList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                socketPortListKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(socketPortList);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(30, 40, 100, 230);

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));
        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+2f));
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        jLabel1.setText(bundle1.getString("WSNNodesManager.jLabel1.text")); 
        jLabel1.setOpaque(true);
        jPanel2.add(jLabel1);
        jLabel1.setBounds(30, 13, 100, 30);

        connectionList.setFont(connectionList.getFont().deriveFont(connectionList.getFont().getSize()+6f));
        connectionList.setModel(connListModel);
        connectionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectionListMouseClicked(evt);
            }
        });
        connectionList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                connectionListKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(connectionList);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(140, 40, 100, 230);

        jLabel2.setBackground(new java.awt.Color(255, 255, 102));
        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+2f));
        jLabel2.setText(bundle1.getString("WSNNodesManager.jLabel2.text")); 
        jLabel2.setOpaque(true);
        jPanel2.add(jLabel2);
        jLabel2.setBounds(140, 13, 100, 30);

        jPanel6.setLayout(null);

        jButton1.setFont(jButton1.getFont().deriveFont(jButton1.getFont().getSize()+2f));
        jButton1.setText(bundle1.getString("WSNNodesManager.jButton1.text")); 
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);
        jButton1.setBounds(10, 160, 220, 27);

        jLabel19.setFont(jLabel19.getFont().deriveFont(jLabel19.getFont().getSize()+6f));
        jLabel19.setText(bundle1.getString("WSNNodesManager.jLabel19.text")); 
        jPanel6.add(jLabel19);
        jLabel19.setBounds(10, 20, 90, 23);

        jLabel21.setFont(jLabel21.getFont().deriveFont(jLabel21.getFont().getSize()+6f));
        jLabel21.setText(bundle1.getString("WSNNodesManager.jLabel21.text")); 
        jPanel6.add(jLabel21);
        jLabel21.setBounds(100, 20, 70, 23);

        jLabel22.setFont(jLabel22.getFont());
        jLabel22.setText(bundle1.getString("WSNNodesManager.jLabel22.text")); 
        jPanel6.add(jLabel22);
        jLabel22.setBounds(10, 80, 180, 15);

        jButton5.setFont(jButton5.getFont().deriveFont(jButton5.getFont().getSize()+2f));
        jButton5.setText(bundle1.getString("WSNNodesManager.jButton5.text")); 
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5);
        jButton5.setBounds(10, 130, 220, 30);

        jLabel24.setFont(jLabel24.getFont().deriveFont(jLabel24.getFont().getSize()+4f));
        jLabel24.setText(bundle1.getString("WSNNodesManager.jLabel24.text")); 
        jPanel6.add(jLabel24);
        jLabel24.setBounds(10, 50, 170, 20);

        jLabel25.setFont(jLabel25.getFont().deriveFont(jLabel25.getFont().getSize()+6f));
        jLabel25.setText(bundle1.getString("WSNNodesManager.jLabel25.text")); 
        jPanel6.add(jLabel25);
        jLabel25.setBounds(190, 50, 30, 23);

        jTextField3.setFont(jTextField3.getFont().deriveFont(jTextField3.getFont().getSize()+6f));
        jTextField3.setText(bundle1.getString("WSNNodesManager.jTextField3.text")); 
        jPanel6.add(jTextField3);
        jTextField3.setBounds(190, 80, 40, 20);

        jButton8.setFont(jButton8.getFont().deriveFont(jButton8.getFont().getSize()+6f));
        jButton8.setText(bundle1.getString("WSNNodesManager.jButton8.text")); 
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton8);
        jButton8.setBounds(10, 190, 220, 33);

        jPanel2.add(jPanel6);
        jPanel6.setBounds(250, 40, 240, 230);

        jPanel7.setLayout(null);

        jButton4.setFont(jButton4.getFont().deriveFont(jButton4.getFont().getSize()+4f));
        jButton4.setText(bundle1.getString("WSNNodesManager.jButton4.text")); 
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton4);
        jButton4.setBounds(20, 90, 240, 29);

        jLabel20.setFont(jLabel20.getFont().deriveFont(jLabel20.getFont().getSize()+6f));
        jLabel20.setText(bundle1.getString("WSNNodesManager.jLabel20.text")); 
        jPanel7.add(jLabel20);
        jLabel20.setBounds(20, 10, 110, 20);

        jTextField1.setFont(jTextField1.getFont().deriveFont(jTextField1.getFont().getSize()+6f));
        jTextField1.setText(bundle1.getString("WSNNodesManager.jTextField1.text")); 
        jPanel7.add(jTextField1);
        jTextField1.setBounds(130, 10, 80, 20);

        jLabel23.setFont(jLabel23.getFont().deriveFont(jLabel23.getFont().getSize()+2f));
        jLabel23.setText(bundle1.getString("WSNNodesManager.jLabel23.text")); 
        jPanel7.add(jLabel23);
        jLabel23.setBounds(20, 30, 200, 30);

        jTextField2.setFont(jTextField2.getFont().deriveFont(jTextField2.getFont().getSize()+6f));
        jTextField2.setText(bundle1.getString("WSNNodesManager.jTextField2.text")); 
        jPanel7.add(jTextField2);
        jTextField2.setBounds(220, 30, 40, 29);

        jLabel47.setFont(new java.awt.Font("?s?????", 0, 16));
        jLabel47.setText(bundle1.getString("WSNNodesManager.jLabel47.text")); 
        jPanel7.add(jLabel47);
        jLabel47.setBounds(20, 60, 90, 20);

        jTextField14.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField14.setText(bundle1.getString("WSNNodesManager.jTextField14.text")); 
        jTextField14.setToolTipText(bundle1.getString("WSNNodesManager.jTextField14.toolTipText")); 
        jPanel7.add(jTextField14);
        jTextField14.setBounds(110, 60, 60, 20);

        jLabel48.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel48.setText(bundle1.getString("WSNNodesManager.jLabel48.text")); 
        jPanel7.add(jLabel48);
        jLabel48.setBounds(180, 60, 50, 20);

        jPanel2.add(jPanel7);
        jPanel7.setBounds(500, 130, 280, 130);

        jPanel8.setLayout(null);

        jLabel26.setFont(jLabel26.getFont().deriveFont(jLabel26.getFont().getSize()+6f));
        jLabel26.setText(bundle1.getString("WSNNodesManager.jLabel26.text")); 
        jPanel8.add(jLabel26);
        jLabel26.setBounds(10, 10, 150, 23);

        jLabel27.setFont(jLabel27.getFont().deriveFont(jLabel27.getFont().getSize()+6f));
        jLabel27.setText(bundle1.getString("WSNNodesManager.jLabel27.text")); 
        jPanel8.add(jLabel27);
        jLabel27.setBounds(170, 10, 40, 23);

        jButton6.setFont(jButton6.getFont().deriveFont(jButton6.getFont().getSize()+4f));
        jButton6.setText(bundle1.getString("WSNNodesManager.jButton6.text")); 
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton6);
        jButton6.setBounds(10, 40, 230, 29);

        jPanel2.add(jPanel8);
        jPanel8.setBounds(500, 40, 280, 80);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 0, 153));
        jPanel3.setLayout(null);

        jLabel3.setBackground(new java.awt.Color(153, 153, 255));
        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+4f));
        jLabel3.setText(bundle1.getString("WSNNodesManager.jLabel3.text")); 
        jLabel3.setOpaque(true);
        jPanel3.add(jLabel3);
        jLabel3.setBounds(30, 13, 100, 30);

        serialPortList.setFont(serialPortList.getFont().deriveFont(serialPortList.getFont().getSize()+6f));
        serialPortList.setModel(srPortListModel);
        serialPortList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                serialPortListMouseClicked(evt);
            }
        });
        serialPortList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                serialPortListKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(serialPortList);

        jPanel3.add(jScrollPane4);
        jScrollPane4.setBounds(30, 40, 100, 210);

        jPanel4.setLayout(null);

        jLabel13.setFont(jLabel13.getFont().deriveFont(jLabel13.getFont().getSize()+6f));
        jLabel13.setText(bundle1.getString("WSNNodesManager.jLabel13.text")); 
        jPanel4.add(jLabel13);
        jLabel13.setBounds(160, 110, 50, 30);

        jLabel12.setFont(jLabel12.getFont().deriveFont(jLabel12.getFont().getSize()+6f));
        jLabel12.setText(bundle1.getString("WSNNodesManager.jLabel12.text")); 
        jPanel4.add(jLabel12);
        jLabel12.setBounds(160, 60, 60, 20);

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getSize()+6f));
        jLabel11.setText(bundle1.getString("WSNNodesManager.jLabel11.text")); 
        jPanel4.add(jLabel11);
        jLabel11.setBounds(160, 30, 90, 30);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+6f));
        jLabel4.setText(bundle1.getString("WSNNodesManager.jLabel4.text")); 
        jPanel4.add(jLabel4);
        jLabel4.setBounds(160, 10, 90, 23);

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()+6f));
        jLabel10.setText(bundle1.getString("WSNNodesManager.jLabel10.text")); 
        jPanel4.add(jLabel10);
        jLabel10.setBounds(20, 10, 130, 20);

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()+6f));
        jLabel9.setText(bundle1.getString("WSNNodesManager.jLabel9.text")); 
        jPanel4.add(jLabel9);
        jLabel9.setBounds(20, 60, 130, 20);

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()+6f));
        jLabel8.setText(bundle1.getString("WSNNodesManager.jLabel8.text")); 
        jPanel4.add(jLabel8);
        jLabel8.setBounds(20, 80, 130, 30);

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+6f));
        jLabel7.setText(bundle1.getString("WSNNodesManager.jLabel7.text")); 
        jPanel4.add(jLabel7);
        jLabel7.setBounds(20, 110, 130, 30);

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+6f));
        jLabel6.setText(bundle1.getString("WSNNodesManager.jLabel6.text")); 
        jPanel4.add(jLabel6);
        jLabel6.setBounds(160, 90, 80, 15);

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+6f));
        jLabel5.setText(bundle1.getString("WSNNodesManager.jLabel5.text")); 
        jPanel4.add(jLabel5);
        jLabel5.setBounds(20, 30, 120, 30);

        jButton3.setFont(jButton3.getFont().deriveFont(jButton3.getFont().getSize()+4f));
        jButton3.setText(bundle1.getString("WSNNodesManager.jButton3.text")); 
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);
        jButton3.setBounds(10, 140, 240, 29);

        jButton7.setFont(jButton7.getFont().deriveFont(jButton7.getFont().getSize()+6f));
        jButton7.setText(bundle1.getString("WSNNodesManager.jButton7.text")); 
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(10, 170, 240, 33);

        jPanel3.add(jPanel4);
        jPanel4.setBounds(140, 40, 260, 210);

        jPanel5.setLayout(null);

        jButton2.setFont(jButton2.getFont().deriveFont(jButton2.getFont().getSize()+4f));
        jButton2.setText(bundle1.getString("WSNNodesManager.jButton2.text")); 
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);
        jButton2.setBounds(20, 170, 260, 29);

        jComboBox5.setFont(jComboBox5.getFont().deriveFont(jComboBox5.getFont().getSize()+6f));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
        jPanel5.add(jComboBox5);
        jComboBox5.setBounds(160, 130, 120, 30);

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()+6f));
        jLabel14.setText(bundle1.getString("WSNNodesManager.jLabel14.text")); 
        jPanel5.add(jLabel14);
        jLabel14.setBounds(10, 130, 140, 30);

        jComboBox3.setFont(jComboBox3.getFont().deriveFont(jComboBox3.getFont().getSize()+6f));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "ODD", "EVEN", "MARK", "SPACE" }));
        jPanel5.add(jComboBox3);
        jComboBox3.setBounds(160, 100, 120, 31);

        jLabel15.setFont(jLabel15.getFont().deriveFont(jLabel15.getFont().getSize()+6f));
        jLabel15.setText(bundle1.getString("WSNNodesManager.jLabel15.text")); 
        jPanel5.add(jLabel15);
        jLabel15.setBounds(10, 100, 140, 30);

        jComboBox2.setFont(jComboBox2.getFont().deriveFont(jComboBox2.getFont().getSize()+6f));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "75", "110", "134", "150", "200", "300", "600", "1200", "1800", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600" }));
        jComboBox2.setSelectedItem("115200");
        jPanel5.add(jComboBox2);
        jComboBox2.setBounds(160, 40, 120, 30);

        jComboBox1.setEditable(true);
        jComboBox1.setFont(jComboBox1.getFont().deriveFont(jComboBox1.getFont().getSize()+6f));
        jComboBox1.setSelectedItem("COM17");
        jPanel5.add(jComboBox1);
        jComboBox1.setBounds(160, 10, 120, 31);

        jComboBox4.setFont(jComboBox4.getFont().deriveFont(jComboBox4.getFont().getSize()+6f));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8", "9" }));
        jComboBox4.setSelectedItem("8");
        jPanel5.add(jComboBox4);
        jComboBox4.setBounds(160, 70, 120, 31);

        jLabel16.setFont(jLabel16.getFont().deriveFont(jLabel16.getFont().getSize()+6f));
        jLabel16.setText(bundle1.getString("WSNNodesManager.jLabel16.text")); 
        jPanel5.add(jLabel16);
        jLabel16.setBounds(10, 70, 130, 30);

        jLabel17.setFont(jLabel17.getFont().deriveFont(jLabel17.getFont().getSize()+6f));
        jLabel17.setText(bundle1.getString("WSNNodesManager.jLabel17.text")); 
        jPanel5.add(jLabel17);
        jLabel17.setBounds(10, 10, 140, 20);

        jLabel18.setFont(jLabel18.getFont().deriveFont(jLabel18.getFont().getSize()+6f));
        jLabel18.setText(bundle1.getString("WSNNodesManager.jLabel18.text")); 
        jPanel5.add(jLabel18);
        jLabel18.setBounds(10, 42, 140, 23);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(440, 40, 300, 210);

        jPanel1.add(jPanel3);

        jTabbedPane1.addTab(bundle1.getString("WSNNodesManager.jPanel1.TabConstraints.tabTitle"), jPanel1); 

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(null);

        jLabel28.setBackground(new java.awt.Color(204, 255, 255));
        jLabel28.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel28.setText(bundle1.getString("WSNNodesManager.jLabel28.text")); 
        jLabel28.setOpaque(true);
        jPanel9.add(jLabel28);
        jLabel28.setBounds(36, 22, 320, 30);

        jLabel29.setFont(new java.awt.Font("?s?????", 0, 16));
        jLabel29.setText(bundle1.getString("WSNNodesManager.jLabel29.text")); 
        jPanel9.add(jLabel29);
        jLabel29.setBounds(80, 60, 220, 20);

        jLabel30.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel30.setText(bundle1.getString("WSNNodesManager.jLabel30.text")); 
        jPanel9.add(jLabel30);
        jLabel30.setBounds(80, 90, 200, 23);

        jTextField4.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField4.setText(bundle1.getString("WSNNodesManager.jTextField4.text")); 
        jTextField4.setToolTipText(bundle1.getString("WSNNodesManager.jTextField4.toolTipText")); 
        jPanel9.add(jTextField4);
        jTextField4.setBounds(280, 90, 40, 29);

        jLabel31.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel31.setText(bundle1.getString("WSNNodesManager.jLabel31.text")); 
        jPanel9.add(jLabel31);
        jLabel31.setBounds(330, 90, 70, 30);

        jTextField5.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField5.setText(bundle1.getString("WSNNodesManager.jTextField5.text")); 
        jPanel9.add(jTextField5);
        jTextField5.setBounds(305, 60, 60, 29);

        jLabel32.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel32.setText(bundle1.getString("WSNNodesManager.jLabel32.text")); 
        jPanel9.add(jLabel32);
        jLabel32.setBounds(450, 60, 290, 30);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("?s?????", 0, 16));
        jRadioButton1.setText(bundle1.getString("WSNNodesManager.jRadioButton1.text")); 
        jPanel9.add(jRadioButton1);
        jRadioButton1.setBounds(460, 90, 320, 29);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("?s?????", 0, 16));
        jRadioButton2.setText(bundle1.getString("WSNNodesManager.jRadioButton2.text")); 
        jPanel9.add(jRadioButton2);
        jRadioButton2.setBounds(460, 120, 280, 29);

        jLabel33.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel33.setText(bundle1.getString("WSNNodesManager.jLabel33.text")); 
        jPanel9.add(jLabel33);
        jLabel33.setBounds(370, 200, 310, 30);

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("?s?????", 0, 16));
        jRadioButton4.setText(bundle1.getString("WSNNodesManager.jRadioButton4.text")); 
        jPanel9.add(jRadioButton4);
        jRadioButton4.setBounds(380, 230, 360, 29);

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("?s?????", 0, 16));
        jRadioButton3.setText(bundle1.getString("WSNNodesManager.jRadioButton3.text")); 
        jPanel9.add(jRadioButton3);
        jRadioButton3.setBounds(380, 260, 300, 29);

        jLabel34.setFont(jLabel34.getFont().deriveFont(jLabel34.getFont().getSize()+6f));
        jLabel34.setText(bundle1.getString("WSNNodesManager.jLabel34.text")); 
        jPanel9.add(jLabel34);
        jLabel34.setBounds(80, 200, 110, 23);

        jLabel35.setFont(jLabel35.getFont().deriveFont(jLabel35.getFont().getSize()+6f));
        jLabel35.setText(bundle1.getString("WSNNodesManager.jLabel35.text")); 
        jPanel9.add(jLabel35);
        jLabel35.setBounds(80, 230, 110, 30);

        jLabel36.setFont(jLabel36.getFont().deriveFont(jLabel36.getFont().getSize()+6f));
        jLabel36.setText(bundle1.getString("WSNNodesManager.jLabel36.text")); 
        jPanel9.add(jLabel36);
        jLabel36.setBounds(80, 260, 110, 30);

        jLabel37.setFont(jLabel37.getFont().deriveFont(jLabel37.getFont().getSize()+6f));
        jLabel37.setText(bundle1.getString("WSNNodesManager.jLabel37.text")); 
        jPanel9.add(jLabel37);
        jLabel37.setBounds(80, 290, 110, 30);

        jComboBox6.setFont(jComboBox6.getFont().deriveFont(jComboBox6.getFont().getSize()+6f));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "1.5", "2" }));
        jPanel9.add(jComboBox6);
        jComboBox6.setBounds(190, 290, 120, 30);

        jComboBox7.setFont(jComboBox7.getFont().deriveFont(jComboBox7.getFont().getSize()+6f));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "ODD", "EVEN", "MARK", "SPACE" }));
        jPanel9.add(jComboBox7);
        jComboBox7.setBounds(190, 260, 120, 31);

        jComboBox8.setFont(jComboBox8.getFont().deriveFont(jComboBox8.getFont().getSize()+6f));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8", "9" }));
        jComboBox8.setSelectedItem("8");
        jPanel9.add(jComboBox8);
        jComboBox8.setBounds(190, 230, 120, 31);

        jComboBox9.setFont(jComboBox9.getFont().deriveFont(jComboBox9.getFont().getSize()+6f));
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50", "75", "110", "134", "150", "200", "300", "600", "1200", "1800", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600" }));
        jComboBox9.setSelectedItem("115200");
        jPanel9.add(jComboBox9);
        jComboBox9.setBounds(190, 200, 120, 30);

        jButton9.setFont(new java.awt.Font("?s?????", 0, 24));
        jButton9.setText(bundle1.getString("WSNNodesManager.jButton9.text")); 
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton9);
        jButton9.setBounds(250, 370, 100, 40);

        jLabel45.setBackground(new java.awt.Color(0, 0, 153));
        jLabel45.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText(bundle1.getString("WSNNodesManager.jLabel45.text")); 
        jLabel45.setOpaque(true);
        jPanel9.add(jLabel45);
        jLabel45.setBounds(40, 170, 320, 30);

        jLabel49.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel49.setText(bundle1.getString("WSNNodesManager.jLabel49.text")); 
        jPanel9.add(jLabel49);
        jLabel49.setBounds(80, 120, 180, 23);

        jTextField15.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField15.setText(bundle1.getString("WSNNodesManager.jTextField15.text")); 
        jPanel9.add(jTextField15);
        jTextField15.setBounds(270, 120, 100, 30);

        jTabbedPane2.addTab(bundle1.getString("WSNNodesManager.jPanel9.TabConstraints.tabTitle"), jPanel9); 

        jPanel11.setLayout(null);

        jButton10.setFont(new java.awt.Font("?s?????", 0, 24));
        jButton10.setText(bundle1.getString("WSNNodesManager.jButton10.text")); 
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton10);
        jButton10.setBounds(150, 440, 100, 40);

        jLabel38.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel38.setText(bundle1.getString("WSNNodesManager.jLabel38.text")); 
        jPanel11.add(jLabel38);
        jLabel38.setBounds(50, 30, 400, 20);

        jTextField6.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField6.setText(bundle1.getString("WSNNodesManager.jTextField6.text")); 
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel11.add(jTextField6);
        jTextField6.setBounds(460, 30, 85, 29);

        jLabel39.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel39.setText(bundle1.getString("WSNNodesManager.jLabel39.text")); 
        jPanel11.add(jLabel39);
        jLabel39.setBounds(50, 60, 400, 23);

        jTextField7.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField7.setText(bundle1.getString("WSNNodesManager.jTextField7.text")); 
        jPanel11.add(jTextField7);
        jTextField7.setBounds(460, 60, 240, 29);

        jLabel40.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel40.setText(bundle1.getString("WSNNodesManager.jLabel40.text")); 
        jPanel11.add(jLabel40);
        jLabel40.setBounds(50, 90, 310, 23);

        jTextField8.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField8.setText(bundle1.getString("WSNNodesManager.jTextField8.text")); 
        jPanel11.add(jTextField8);
        jTextField8.setBounds(460, 90, 85, 29);

        jLabel41.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel41.setText(bundle1.getString("WSNNodesManager.jLabel41.text")); 
        jPanel11.add(jLabel41);
        jLabel41.setBounds(50, 120, 390, 23);

        jTextField9.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField9.setText(bundle1.getString("WSNNodesManager.jTextField9.text")); 
        jPanel11.add(jTextField9);
        jTextField9.setBounds(460, 120, 240, 29);

        jLabel42.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel42.setText(bundle1.getString("WSNNodesManager.jLabel42.text")); 
        jPanel11.add(jLabel42);
        jLabel42.setBounds(50, 150, 340, 23);

        jTextField10.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField10.setText(bundle1.getString("WSNNodesManager.jTextField10.text")); 
        jPanel11.add(jTextField10);
        jTextField10.setBounds(460, 150, 93, 29);

        jLabel43.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel43.setText(bundle1.getString("WSNNodesManager.jLabel43.text")); 
        jPanel11.add(jLabel43);
        jLabel43.setBounds(50, 180, 400, 23);

        jTextField11.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField11.setText(bundle1.getString("WSNNodesManager.jTextField11.text")); 
        jPanel11.add(jTextField11);
        jTextField11.setBounds(460, 180, 240, 29);

        jLabel44.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel44.setText(bundle1.getString("WSNNodesManager.jLabel44.text")); 
        jPanel11.add(jLabel44);
        jLabel44.setBounds(50, 250, 100, 20);

        jTextField12.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField12.setText(bundle1.getString("WSNNodesManager.jTextField12.text")); 
        jTextField12.setToolTipText(bundle1.getString("WSNNodesManager.jTextField12.toolTipText")); 
        jPanel11.add(jTextField12);
        jTextField12.setBounds(150, 250, 550, 29);

        jCheckBox1.setFont(new java.awt.Font("?s?????", 0, 18));
        jCheckBox1.setText(bundle1.getString("WSNNodesManager.jCheckBox1.text")); 
        jPanel11.add(jCheckBox1);
        jCheckBox1.setBounds(50, 340, 280, 31);

        jCheckBox2.setFont(new java.awt.Font("?s?????", 0, 18)); 
        jCheckBox2.setText(bundle1.getString("WSNNodesManager.jCheckBox2.text")); 
        jPanel11.add(jCheckBox2);
        jCheckBox2.setBounds(50, 310, 160, 31);

        jCheckBox3.setFont(new java.awt.Font("?s?????", 0, 18));
        jCheckBox3.setText(bundle1.getString("WSNNodesManager.jCheckBox3.text")); 
        jPanel11.add(jCheckBox3);
        jCheckBox3.setBounds(50, 280, 420, 31);

        jLabel46.setFont(new java.awt.Font("?s?????", 0, 18));
        jLabel46.setText(bundle1.getString("WSNNodesManager.jLabel46.text")); 
        jPanel11.add(jLabel46);
        jLabel46.setBounds(50, 210, 400, 23);

        jTextField13.setFont(new java.awt.Font("?s?????", 0, 18));
        jTextField13.setText(bundle1.getString("WSNNodesManager.jTextField13.text")); 
        jPanel11.add(jTextField13);
        jTextField13.setBounds(460, 210, 240, 29);

        jCheckBox4.setFont(new java.awt.Font("?s?????", 0, 18));
        jCheckBox4.setText(bundle1.getString("WSNNodesManager.jCheckBox4.text")); 
        jPanel11.add(jCheckBox4);
        jCheckBox4.setBounds(50, 370, 150, 31);

        buttonGroup3.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("?s?????", 0, 18));
        jRadioButton5.setSelected(true);
        jRadioButton5.setText(bundle1.getString("WSNNodesManager.jRadioButton5.text")); 
        jPanel11.add(jRadioButton5);
        jRadioButton5.setBounds(210, 370, 110, 30);

        buttonGroup3.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("?s?????", 0, 18));
        jRadioButton6.setText(bundle1.getString("WSNNodesManager.jRadioButton6.text")); 
        jPanel11.add(jRadioButton6);
        jRadioButton6.setBounds(330, 370, 140, 30);

        jCheckBox5.setFont(new java.awt.Font("?s?????", 0, 14)); 
        jCheckBox5.setText(bundle1.getString("WSNNodesManager.jCheckBox5.text")); 
        jPanel11.add(jCheckBox5);
        jCheckBox5.setBounds(210, 310, 230, 27);

        jLabel50.setFont(new java.awt.Font("?s?????", 0, 14)); 
        jLabel50.setText(bundle1.getString("WSNNodesManager.jLabel50.text")); 
        jPanel11.add(jLabel50);
        jLabel50.setBounds(440, 310, 210, 30);

        jTextField16.setFont(new java.awt.Font("?s?????", 0, 14)); 
        jTextField16.setText(bundle1.getString("WSNNodesManager.jTextField16.text")); 
        jTextField16.setToolTipText(bundle1.getString("WSNNodesManager.jTextField16.toolTipText")); 
        jPanel11.add(jTextField16);
        jTextField16.setBounds(650, 310, 70, 30);

        jTabbedPane2.addTab(bundle1.getString("WSNNodesManager.jPanel11.TabConstraints.tabTitle"), jPanel11); 

        jPanel10.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle1.getString("WSNNodesManager.jPanel10.TabConstraints.tabTitle"), jPanel10); 

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }

public int getListItemIndex3(String s){
  int rtn=-1;
  switch(wsn.showType){
    case 1:
      int inx=s.lastIndexOf("(");
      if(inx==-1){
        for(int i=0;i<listModel3.size();i++){
          if(wsn.getItemId((String)listModel3.get(i)).equals(s)) return i;
        }
      } else {
      for(int i=0;i<listModel3.size();i++){
        if(((String)listModel3.get(i)).equals(s)) return i;
      }
      }
      break;
    case 2:
       for(int i=0;i<listModel3.size();i++){
            if(((String)listModel3.get(i)).equals(s)) return i;
       }

         for(int i=0;i<listModel3.size();i++){
         if(listModel3.get(i)!=null){
           if(wsn.nameIdMap.get((String)listModel3.get(i))!=null){
            if(s.equals((String)wsn.nameIdMap.get((String)listModel3.get(i)))) return i;
           }
         }
       }
      break;
  }
  return rtn;
}

private void formWindowClosing(java.awt.event.WindowEvent evt) {
  setVisible(false);
}
void setPid(String pid){
  this.pid=pid;
}
private void jList3MouseClicked(java.awt.event.MouseEvent evt) {
    changeListItem3();
}
public void setSelectedItem(String item){
    jList3.setSelectedValue(item,true);
    changeListItem3();
}
private void jList3KeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  changeListItem3();
}

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
    if(jLabel21.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg2")); return;
    }
    if(jTextField3.getText().trim().length()<1 || Integer.parseInt((jTextField3.getText()))<1 || Integer.parseInt((jTextField3.getText()))>100){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg3")); return;
    }
    String contCmd="performcommand wsn.WSN setmaxconnection "+jLabel21.getText().trim()+" "+jTextField3.getText()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
    if(jLabel21.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg5")); return;
    }
    int an=JOptionPane.showConfirmDialog(this, "Are you sure you want to close socket port "+jLabel21.getText().trim()+" ?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
     if(an!=JOptionPane.YES_OPTION) return;
    String contCmd="performcommand wsn.WSN closesocketport "+jLabel21.getText().trim()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg6")); return;
    } 
    if(jLabel21.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg7")); return;
    }
    if(jLabel27.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg8")); return;
    }
    int an=JOptionPane.showConfirmDialog(this, "Are you sure you want to close socket connection "+jLabel21.getText().trim()+"-"+jLabel27.getText().trim()+ " ?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
     if(an!=JOptionPane.YES_OPTION) return;
    int id=Integer.parseInt(jLabel27.getText().trim());
    if(id<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg9")); return;}
    String contCmd="performcommand wsn.WSN closesocketconnection "+jLabel21.getText().trim()+" "+jLabel27.getText().trim()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg10")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg10")); return;
      }
    }
    if(jLabel4.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg11")); return;
    }
    int an=JOptionPane.showConfirmDialog(this, "Are you sure you want to close serial port "+jLabel4.getText().trim()+ " ?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
    if(an!=JOptionPane.YES_OPTION) return;
    String contCmd="performcommand wsn.WSN closeserialport "+jLabel4.getText().trim()+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg12")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg12")); return;
      }
    }
    String comName=(String)jComboBox1.getSelectedItem();
    String bRate=(String)jComboBox2.getSelectedItem();
    String dataB=(String)jComboBox4.getSelectedItem();
    String parityB=(String)jComboBox3.getSelectedItem();
    String stopB=(String)jComboBox5.getSelectedItem();

    String contCmd="performcommand wsn.WSN openserialport "+comName+" "+bRate+" "+dataB+" "+parityB+" "+stopB+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void socketPortListMouseClicked(java.awt.event.MouseEvent evt) {
 treat3_1();
}

private void serialPortListMouseClicked(java.awt.event.MouseEvent evt) {
  treat3_3();
}

private void serialPortListKeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  treat3_3();
}

private void socketPortListKeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  treat3_1();
}

private void connectionListKeyReleased(java.awt.event.KeyEvent evt) {
if(evt.getKeyCode()==38 || evt.getKeyCode()==40 )  treat3_2();
}

private void connectionListMouseClicked(java.awt.event.MouseEvent evt) {
  treat3_2();
}

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
    String port=jTextField1.getText().trim();
    String maxCount=jTextField2.getText().trim();
    String hbeat=jTextField14.getText().trim();
      if(maxCount.length()>0 && wsn.isNumeric(maxCount)){
         if(Integer.parseInt(maxCount) > WSNSocketServer.connectionLimit) {jTextField2.setText(""+WSNSocketServer.connectionLimit); maxCount=jTextField2.getText();}
      } else {jTextField2.setText("1"); maxCount=jTextField2.getText();}
      if(hbeat.length()>0 && wsn.isNumeric(hbeat)){
      } else {jTextField14.setText("0"); hbeat=jTextField14.getText();}
    if(port.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg14")); return;}
    if(maxCount.length()<1) {JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg15")); return;}
    if(hbeat.length()<1) hbeat="0";

     String contCmd="performcommand wsn.WSN opensocketport "+port+" "+maxCount+" "+hbeat+" null null null null null null null 0 0 0 0 ? ? ? 0";
     wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
    if(jLabel21.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg5")); return;
    }
    if(socketOptions==null) socketOptions=new WSNSocketportOptions(this,true);
    else socketOptions.init();
}

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg6")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg6")); return;
      }
    }
    if(jLabel4.getText().trim().length()<1){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg11")); return;
    }
  if(serialOptions==null) serialOptions=new WSNSerialportOptions(this,true);
  else serialOptions.init();
}

private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
  String conf[]=wsn.w.csvLineToArray(nodeConfig);
      conf[0]=jTextField5.getText();
      if(conf[0].length()>0 && wsn.isNumeric(conf[0])){
         if(Integer.parseInt(conf[0]) > WSNSocketServer.connectionLimit) {jTextField5.setText(""+WSNSocketServer.connectionLimit); conf[0]=jTextField5.getText();}
      } else {jTextField5.setText("1"); conf[0]=jTextField5.getText();}
      conf[1]=jTextField4.getText();
      if(conf[1].length()>0 && wsn.isNumeric(conf[1])){
      } else {jTextField4.setText("0"); conf[1]=jTextField4.getText();}
      if(jRadioButton2.isSelected()) conf[4]="1"; else conf[4]="0";
      conf[8]=jTextField15.getText();
      conf[8]=wsn.w.replace(conf[8],",",".");
      if(conf[8].length()==0) {conf[8]="."; jTextField15.setText(conf[8]);}
      conf[10]=(String)jComboBox9.getSelectedItem();
      conf[11]=(String)jComboBox8.getSelectedItem();
      conf[12]=(String)jComboBox7.getSelectedItem();
      conf[13]=(String)jComboBox6.getSelectedItem();
      jComboBox2.setSelectedItem(conf[10]);
      jComboBox4.setSelectedItem(conf[11]);
      if(conf[12].substring(0,1).equalsIgnoreCase("N")) jComboBox3.setSelectedItem("NONE");
      else if(conf[12].substring(0,1).equalsIgnoreCase("O")) jComboBox3.setSelectedItem("ODD");
      else if(conf[12].substring(0,1).equalsIgnoreCase("E")) jComboBox3.setSelectedItem("EVEN");
      else if(conf[12].substring(0,1).equalsIgnoreCase("M")) jComboBox3.setSelectedItem("MARK");
      else if(conf[12].substring(0,1).equalsIgnoreCase("S")) jComboBox3.setSelectedItem("SPACE");
      jComboBox5.setSelectedItem(conf[13]);
      if(jRadioButton3.isSelected()) conf[14]="1"; else conf[14]="0";
      nodeConfig=wsn.w.arrayToCsvLine(conf);
      String contCmd="performcommand wsn.WSN setdefaultconfig "+nodeConfig+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
    if(jList3.getSelectedValue()==null){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
    } else {
      String item=(String)jList3.getSelectedValue();
      if(wsn.getItemId(item).equals("0")){
        JOptionPane.showMessageDialog(this, bundle2.getString("WSNNodesManager.xy.msg13")); return;
      }
    }
  String conf[]=wsn.w.csvLineToArray(nodeConfig);
      conf[20]=jTextField6.getText();
      conf[21]=jTextField7.getText();
      conf[22]=jTextField8.getText();
      conf[23]=jTextField9.getText();
      conf[24]=jTextField10.getText();
      conf[25]=jTextField11.getText();
      conf[26]=jTextField13.getText();
      conf[27]=jTextField12.getText();
      if(jCheckBox3.isSelected()) conf[28]="Y"; else conf[28]="N";
      if(jCheckBox2.isSelected()) conf[29]="Y"; else conf[29]="N";
      if(jCheckBox1.isSelected()) {
        if(conf[27].length()<1) {JOptionPane.showMessageDialog(this, "Warning: Cannot set \"Run_My_Ap_Only\" when no My_AP running."); return;}
         if(wsn.getItemId((String)jList3.getSelectedValue()).equals(wsn.w.getGNS(1))){
          int an=JOptionPane.showConfirmDialog(this, wsn.w.replace("Notice: In the \"Run_My_AP_Only\" mode, the WSN platform will disappear and keep only the \"My AP\", even you restart the program.\r\n\tThe only way to disable \"Run_My_Ap_Only\" is to set run_my_ap_only=N in the node_properties.txt file.\r\n\r\n\t\tAre you sure you want enable the \"Run_My_Ap_Only\" mode ?","node_properties.txt",(new File(wsn.propsFile)).getName()),"confirm", JOptionPane.YES_NO_CANCEL_OPTION);
          if(an!=JOptionPane.YES_OPTION)  return;
        }
        conf[30]="Y"; 
      }else conf[30]="N";
      if(jCheckBox4.isSelected()) {
        if(jRadioButton5.isSelected()) conf[31]="1";
        else conf[31]="2";
          int an=JOptionPane.showConfirmDialog(this, "Are you sure you want to "+(conf[31].equals("1")? "Restart":"Shutdown") +" "+(String)jList3.getSelectedValue()+" CR-WSN program ?","confirm", JOptionPane.YES_NO_CANCEL_OPTION);
          if(an!=JOptionPane.YES_OPTION)  return;
      } else conf[31]="0";
      if(jCheckBox5.isSelected()) conf[32]="Y"; else conf[32]="N";
      conf[33]=jTextField16.getText().trim();
      nodeConfig=wsn.w.arrayToCsvLine(conf);
      String contCmd="performcommand wsn.WSN setotherconfig "+nodeConfig+" null null null null null null null null 0 0 0 0 ? ? ? 0";
      wsn.w.sendToOne(contCmd,wsn.getItemId((String)jList3.getSelectedValue()));
}

private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {

}

public void treat3_1(){
  if(socketPortList.getSelectedValue()!=null){
    String id=(String)socketPortList.getSelectedValue();  
    if(!id.equals(currentViewId3_1)){
       lastId3_1=currentViewId3_1;
      currentViewId3_1=id;
    }
    String contCmd="performmessage wsn.WSN getstatus3_1 "+currentViewId3_1+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,currentViewId3);
  }
}
public void treat3_2(){
    if(connectionList.getSelectedValue()!=null)      jLabel27.setText((String)connectionList.getSelectedValue());
}
public void treat3_3(){
    String id=(String)serialPortList.getSelectedValue();  
    if(id!=null){
    if(!id.equals(currentViewId3_3)){
       lastId3_3=currentViewId3_3;
      currentViewId3_3=id;
    }
    String contCmd="performmessage wsn.WSN getstatus3_3 "+currentViewId3_3+" null null null null null null null null 0 0 0 0 ? ? ? 0";
    wsn.w.sendToOne(contCmd,currentViewId3);
    } 
}
public void changeListItem3(){
  String id=wsn.getItemId((String)jList3.getSelectedValue());

  if(id.length()>1){

  if(currentViewId3.length()>0){

    String lastId=currentViewId3;
    if(currentItemData3!=null && currentItemData3.length>2) currentItemData3[2]=wsn.w.getSsx(6,pid);
    if(currentItemData3!=null && currentItemData3.length>5) currentItemData3[5]="filler";
    if(currentItemData3!=null && currentItemData3.length>5 && currentItemData3[5].length()<1) currentItemData3[5]=currentItemData3[4];
    setItemData();
  }

  currentViewId3=id;
  currentItemData3=wsn.getItemData(id,3);
  if(id.equals("0")){
      skPortListModel.removeAllElements();
      connListModel.removeAllElements();
      srPortListModel.removeAllElements();
      jButton1.setEnabled(false);
      jButton2.setEnabled(false);
      jButton3.setEnabled(false);
      jButton4.setEnabled(false);
      jButton5.setEnabled(false);
      jButton6.setEnabled(false);
      jButton7.setEnabled(false);
      jButton8.setEnabled(false);
      jButton9.setEnabled(false);
      jButton10.setEnabled(false);
  } else {

      jButton1.setEnabled(true);
      jButton2.setEnabled(true);
      jButton3.setEnabled(true);
      jButton4.setEnabled(true);
      jButton5.setEnabled(true);
      jButton6.setEnabled(true);
      jButton7.setEnabled(true);
      jButton8.setEnabled(true);
      jButton9.setEnabled(true);
      jButton10.setEnabled(true);
  if(currentItemData3.length>0){};
  if(currentItemData3.length>4){};
  String showName=(currentItemData3!=null && currentItemData3.length>4? currentItemData3[4]:"");
  if(currentItemData3.length>5) {
     if(currentItemData3[5].length()>0) showName=currentItemData3[5]; else currentItemData3[5]=currentItemData3[4];
  }

  if(currentItemData3.length>2) {};
  if(currentItemData3.length>3 && currentItemData3[3].length()>0 && !wsn.w.checkOneVar(currentItemData3[1], 0)){
    String nData[]=wsn.getNodeData(currentItemData3[3]);
    currentItemData3[1]=wsn.w.addOneVar(currentItemData3[1],0);
    if(nData.length>5 && nData[5].length()>0) {
        if(currentItemData3!=null && currentItemData3.length>5) currentItemData3[5]=nData[5];

    }
    if(nData.length>2 && nData[2].length()>0) {
        if(currentItemData3!=null && currentItemData3.length>2) currentItemData3[2]=nData[2];

    }
  }

  String contCmd="performmessage wsn.WSN getstatus3 null null null null null null null null null 0 0 0 0 ? ? ? 0";
  wsn.w.sendToOne(contCmd,id);
  }
  }
  else {
      skPortListModel.removeAllElements();
      connListModel.removeAllElements();
      srPortListModel.removeAllElements();
  }
}

public void setStatus(String nodeId,String dataSrc[],int statusCode){
  if(nodeId.equals(currentViewId3)){
    for(int i=0;i<dataSrc.length;i++){
      String id1=wsn.getId1(dataSrc[i]);
      String id2=wsn.getId2(dataSrc[i]);
      String id3=wsn.getId3(dataSrc[i]);
      switch(statusCode){
        case 1:
          if(id2.toUpperCase().indexOf("COM")==0){
            if(!srPortListModel.contains(id2)){
              srPortListModel.addElement(id2);
              wsn.sortList(serialPortList,srPortListModel);
            }
          } else {
              if(!skPortListModel.contains(id2)) {
                skPortListModel.addElement(id2);
                wsn.sortList(socketPortList,skPortListModel);
                String selected2="";
                if(socketPortList.getSelectedValue()!=null) selected2=(String)socketPortList.getSelectedValue();
                if(selected2.equals(id2)){
                  if(!connListModel.contains(id3)){
                    connListModel.addElement(id3);
                    wsn.sortList(connectionList,connListModel);
                  }
                }
              } else {
                String selected="";
                if(socketPortList.getSelectedValue()!=null) selected=(String)socketPortList.getSelectedValue();
                if(selected.equals(id2)){
                  if(!connListModel.contains(id3)){
                    connListModel.addElement(id3);
                    wsn.sortList(connectionList,connListModel);
                  }
                }        
              }
          }
          break;
        case 2:
          if(id2.toUpperCase().indexOf("COM")==0){
            if(srPortListModel.contains(id2)) srPortListModel.removeElement(id2);
          } else {
            if(skPortListModel.contains(id2)) {
              String selected=(String)socketPortList.getSelectedValue();
              skPortListModel.removeElement(id2);
              if(selected!=null && selected.equals(id2)) connectionList.removeAll();
            }
          }
          break;
        case 5:
          if(!skPortListModel.contains(id2)) {
                skPortListModel.addElement(id2);
                wsn.sortList(socketPortList,skPortListModel);
          }
          break;
        case 6:
          if(skPortListModel.contains(id2)) {
            String selected=(String)socketPortList.getSelectedValue();
            skPortListModel.removeElement(id2);
            if(id2.equals(selected)){
              connectionList.removeAll();
            }
          }
          break;
      }
    }
  }
}
public void setStatus3(String id,String stringx[]){
      if(socketPortList.getModel().getSize()>0 && socketPortList.getSelectedValue()!=null) lastId3_1=(String)socketPortList.getSelectedValue();
      else lastId3_1="";
      if(serialPortList.getModel().getSize()>0 && serialPortList.getSelectedValue()!=null) lastId3_3=(String)serialPortList.getSelectedValue();
      else lastId3_3="";
      currentViewStatus3=wsn.w.csvLineToArray(stringx[1]);
      skPortListModel.removeAllElements();
      connListModel.removeAllElements();
      srPortListModel.removeAllElements();
      int count=Integer.parseInt(currentViewStatus3[2]);
      int count2=Integer.parseInt(currentViewStatus3[4]);
          if(count>0){
            String tmp[]=wsn.w.csvLineToArray(currentViewStatus3[3]);
            String target="";
            for(int i=0;i<tmp.length;i++) skPortListModel.addElement(tmp[i]);
            if(lastId3.equals(currentViewId3) && lastId3_1.length()>0){
             int index = socketPortList.getNextMatch(lastId3_1,0,Position.Bias.Forward);
             if (index != -1) {
                 socketPortList.setSelectedValue(lastId3_1,true);
                 currentViewId3_1=lastId3_1;
             }
              else {socketPortList.setSelectedIndex(0); target=(String)socketPortList.getSelectedValue(); currentViewId3_1=target;}
            } else {socketPortList.setSelectedIndex(0); target=(String)socketPortList.getSelectedValue(); currentViewId3_1=target;}
              String contCmd="performmessage wsn.WSN getstatus3_1 "+currentViewId3_1+" null null null null null null null null null 0 0 0 0 ? ? ? 0";
            wsn.w.sendToOne(contCmd,currentViewId3);
          }
          if(count2>0){
            String tmp[]=wsn.w.csvLineToArray(currentViewStatus3[5]);
            String target="";
            for(int i=0;i<tmp.length;i++) srPortListModel.addElement(tmp[i]);
            if(lastId3.equals(currentViewId3) && lastId3_3.length()>0){
              int index = serialPortList.getNextMatch(lastId3_3,0,Position.Bias.Forward);
              if (index != -1) {
                  serialPortList.setSelectedValue(lastId3_3,true);
                  currentViewId3_3=lastId3_3;
              }
              else {serialPortList.setSelectedIndex(0); target=(String)serialPortList.getSelectedValue(); currentViewId3_3=target;}
            } else {serialPortList.setSelectedIndex(0); target=(String)serialPortList.getSelectedValue(); currentViewId3_3=target;}
              String contCmd="performmessage wsn.WSN getstatus3_3 "+currentViewId3_3+" null null null null null null null null null 0 0 0 0 ? ? ? 0";
              wsn.w.sendToOne(contCmd,currentViewId3);
          }
          int count3=Integer.parseInt(currentViewStatus3[6]);
          String serials []=wsn.w.csvLineToArray(currentViewStatus3[7]);
          jComboBox1.removeAllItems();
          for(int i=0;i<count3;i++) jComboBox1.addItem(serials[i]);

  String contCmd="performmessage wsn.WSN getnodeconfig null null null null null null null null null 0 0 0 0 ? ? ? 0";
  wsn.w.sendToOne(contCmd,id);
}
public void setNodeConfig(String id,String stringx[]){
  String id2=wsn.getItemId((String)jList3.getSelectedValue());
  if(id2.equals(id)){
      nodeConfig=stringx[1];
      String conf[]=wsn.w.csvLineToArray(nodeConfig);
      jTextField2.setText(conf[0]);
      jTextField5.setText(conf[0]);
      jTextField4.setText(conf[1]);
      jTextField14.setText(conf[1]);
      if(conf[4].equals("1")) jRadioButton2.setSelected(true); else jRadioButton1.setSelected(true);
      jComboBox2.setSelectedItem(conf[10]);
      jComboBox4.setSelectedItem(conf[11]);
      if(conf[12].substring(0,1).equalsIgnoreCase("N")) jComboBox3.setSelectedItem("NONE");
      else if(conf[12].substring(0,1).equalsIgnoreCase("O")) jComboBox3.setSelectedItem("ODD");
      else if(conf[12].substring(0,1).equalsIgnoreCase("E")) jComboBox3.setSelectedItem("EVEN");
      else if(conf[12].substring(0,1).equalsIgnoreCase("M")) jComboBox3.setSelectedItem("MARK");
      else if(conf[12].substring(0,1).equalsIgnoreCase("S")) jComboBox3.setSelectedItem("SPACE");
      jComboBox5.setSelectedItem(conf[13]);
      jComboBox9.setSelectedItem(conf[10]);
      jComboBox8.setSelectedItem(conf[11]);
      if(conf[12].substring(0,1).equalsIgnoreCase("N")) jComboBox7.setSelectedItem("NONE");
      else if(conf[12].substring(0,1).equalsIgnoreCase("O")) jComboBox7.setSelectedItem("ODD");
      else if(conf[12].substring(0,1).equalsIgnoreCase("E")) jComboBox7.setSelectedItem("EVEN");
      else if(conf[12].substring(0,1).equalsIgnoreCase("M")) jComboBox7.setSelectedItem("MARK");
      else if(conf[12].substring(0,1).equalsIgnoreCase("S")) jComboBox7.setSelectedItem("SPACE");
      jComboBox6.setSelectedItem(conf[13]);
      if(conf[14].equals("1")) jRadioButton3.setSelected(true); else jRadioButton4.setSelected(true);
      jTextField6.setText(conf[20]);
      jTextField7.setText(conf[21]);
      jTextField8.setText(conf[22]);
      jTextField9.setText(conf[23]);
      jTextField10.setText(conf[24]);
      jTextField11.setText(conf[25]);
      jTextField13.setText(conf[26]);
      jTextField12.setText(conf[27]);
      if(conf[28].equalsIgnoreCase("Y")) jCheckBox3.setSelected(true); else jCheckBox3.setSelected(false);
      if(conf[29].equalsIgnoreCase("Y")) jCheckBox2.setSelected(true); else jCheckBox2.setSelected(false);
      if(conf[30].equalsIgnoreCase("Y")) jCheckBox1.setSelected(true); else jCheckBox1.setSelected(false);
      jCheckBox4.setSelected(false);
      if(conf[32].equalsIgnoreCase("Y")) jCheckBox5.setSelected(true); else jCheckBox5.setSelected(false);
      jTextField16.setText(conf[33]);
  }
}

public void setStatus3_1(String id,String stringx[]){
    socketportConfig=stringx[1];
      if(connectionList.getModel().getSize()>0 && connectionList.getSelectedValue()!=null) lastId3_2=(String)connectionList.getSelectedValue();
      else lastId3_2="";
      String tmp[]=wsn.w.csvLineToArray(stringx[1]);
      connListModel.removeAllElements();
        int maxCount=Integer.parseInt(tmp[1]);
        int count=Integer.parseInt(tmp[2]);
        jLabel21.setText(tmp[0]);
        jTextField3.setText(tmp[1]);
        jLabel25.setText(tmp[2]);
          if(count>0){
            String tmp2[]=wsn.w.csvLineToArray(tmp[3]);
            for(int i=0;i<tmp2.length;i++) connListModel.addElement(tmp2[i]);
            if(lastId3.equals(currentViewId3) && lastId3_1.equals(currentViewId3_1) && lastId3_2.length()>0){
             int index = connectionList.getNextMatch(lastId3_2,0,Position.Bias.Forward);
             if (index != -1) {
                 connectionList.setSelectedValue(lastId3_2,true);
                 currentViewId3_2=lastId3_2;
             }
              else {connectionList.setSelectedIndex(0); currentViewId3_2=(String)connectionList.getSelectedValue();}
            } else {connectionList.setSelectedIndex(0); currentViewId3_2=(String)connectionList.getSelectedValue();}
          } else currentViewId3_2="";
          jLabel27.setText(currentViewId3_2);

}

public void setStatus3_3(String id,String stringx[]){
    serialportConfig=stringx[1];
    String tmp[]=wsn.w.csvLineToArray(stringx[1]);
    jLabel4.setText(tmp[0]);
    jLabel11.setText(tmp[1]);
    jLabel12.setText(tmp[2]);
    jLabel6.setText(tmp[3]);
    jLabel13.setText(tmp[4]);

}
private void setItemData(){
  boolean found=false;
  if(currentInnerMemberId3.length()>0){
      Object o=wsn.innerMemberItems.get(currentInnerMemberId3);
      if(o!=null){
        TreeMap tm=(TreeMap)o;
        if(tm.containsKey(currentViewId3)){
          tm.put(currentViewId3,wsn.w.arrayToCsvLine(currentItemData3));
          wsn.innerMemberItems.put(currentViewId3,tm);
        }
      }
    } else {
        Object o=wsn.outerMemberItems.get(currentViewId3);
        if(o!=null){
          wsn.outerMemberItems.put(currentViewId3,wsn.w.arrayToCsvLine(currentItemData3));
        }
  }
}

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JList connectionList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JComboBox jComboBox1;
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
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
    public javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JList serialPortList;
    private javax.swing.JList socketPortList;

}