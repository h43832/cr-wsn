package wsn;

import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.ImageIcon;
public class WSNApplicationDemo5 extends WSNApplication {
  WSN wsn;
  String status="stop";
  public WSNApplicationDemo5() {
    initComponents();
  }
    public void init(WSN wsn){
      this.wsn=wsn;
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;
        int w2=(width * 50000)/100000;
        int h2=(h * 47000)/100000;
        setSize(w2,h2);
        if(wsn.props.getProperty("demo_ap5_no")!=null) {
          int mod2=Integer.parseInt(wsn.props.getProperty("demo_ap5_no"));
          switch(mod2){
              case 1:
                setLocation(0,0);

                break;
              case 2:
                setLocation(w2,0);

                break;
              case 3:
                setLocation(0,h2);

                break;
              case 4:
                setLocation(w2,h2);

                break;
          }
          this.setTitle(this.getTitle()+" - "+mod2);
        } else{
        setLocation((width-w2)/2,(h-h2)/2);
        }

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
  }
    public void setBlink(boolean onoff){
      if(onoff){
          jLabel1.setText(wsn.formatter.format(new Date()));
      }
    }
    public void setData(long time,String nodeId,String dataSrc, String data){
          data=wsn.getStringData(data, 1, -1, -1);
          if(wsn.isNumeric(data) && status.equals("start") && wsn.props.getProperty("demo_ap5_datasrc").equals(dataSrc)){
           double da=Double.parseDouble(data);
           da=Math.round(da*100.0)/100.0;
           jLabel3.setText(""+da);
          } else if(data.equals("start") || data.equals("stop")) status=data;
          else if(data.equals("exit")) System.exit(0);
    }
    public void setStatus(String nodeId,String dataSrc[], int statusCode){

    }
  @SuppressWarnings("unchecked")

  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jPanel4 = new javax.swing.JPanel();
    jTextField1 = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSNApplicationDemo5.title")); 
    setName("Form"); 
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jPanel1.setName("jPanel1"); 
    jPanel1.setLayout(null);

    jLabel1.setFont(jLabel1.getFont());

    jLabel1.setText(bundle.getString("WSNApplicationDemo5.jLabel1.text")); 
    jLabel1.setName("jLabel1"); 
    jPanel1.add(jLabel1);
    jLabel1.setBounds(20, 10, 160, 20);

    jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+24f));
    jLabel2.setForeground(new java.awt.Color(0, 102, 102));
    jLabel2.setText(bundle.getString("WSNApplicationDemo5.jLabel2.text")); 
    jLabel2.setName("jLabel2"); 
    jPanel1.add(jLabel2);
    jLabel2.setBounds(20, 80, 200, 80);

    jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+24f));
    jLabel3.setForeground(new java.awt.Color(0, 0, 255));
    jLabel3.setText(bundle.getString("WSNApplicationDemo5.jLabel3.text")); 
    jLabel3.setName("jLabel3"); 
    jPanel1.add(jLabel3);
    jLabel3.setBounds(230, 80, 160, 80);

    jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+12f));
    jLabel4.setForeground(new java.awt.Color(255, 0, 51));
    jLabel4.setText(bundle.getString("WSNApplicationDemo5.jLabel4.text")); 
    jLabel4.setName("jLabel4"); 
    jPanel1.add(jLabel4);
    jLabel4.setBounds(390, 90, 70, 70);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel3.setName("jPanel3"); 
    jPanel3.setLayout(new java.awt.BorderLayout());

    jPanel2.setName("jPanel2"); 
    jPanel2.setOpaque(false);

    jButton1.setFont(jButton1.getFont().deriveFont(jButton1.getFont().getSize()+24f));
    jButton1.setText(bundle.getString("WSNApplicationDemo5.jButton1.text")); 
    jButton1.setName("jButton1"); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton1);

    jButton2.setFont(jButton2.getFont().deriveFont(jButton2.getFont().getSize()+24f));
    jButton2.setText(bundle.getString("WSNApplicationDemo5.jButton2.text")); 
    jButton2.setName("jButton2"); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel2.add(jButton2);

    jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

    jPanel4.setName("jPanel4"); 
    jPanel4.setLayout(new java.awt.BorderLayout());

    jTextField1.setFont(jTextField1.getFont());
    jTextField1.setText(bundle.getString("WSNApplicationDemo5.jTextField1.text")); 
    jTextField1.setName("jTextField1"); 
    jPanel4.add(jTextField1, java.awt.BorderLayout.CENTER);

    jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);

    getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  status="start";
  byte[] b0=status.getBytes();
  String cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 1 1 1 "+wsn.w.e642(wsn.byteToStr(b0,b0.length));
  wsn.w.sendToAllExceptMyself(cmd2);
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  status="stop";
  byte[] b0=status.getBytes();
  String cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 1 1 1 "+wsn.w.e642(wsn.byteToStr(b0,b0.length));
  wsn.w.sendToAllExceptMyself(cmd2);
}

private void formWindowClosing(java.awt.event.WindowEvent evt) {
  status="exit";
  byte[] b0=status.getBytes();
  String cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 1 1 1 "+wsn.w.e642(wsn.byteToStr(b0,b0.length));
  wsn.w.sendToAllExceptMyself(cmd2);
  System.exit(0);
}

public void onExit(int type){

}
  public boolean runInBackground(){
    return false;
  }

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JTextField jTextField1;

}