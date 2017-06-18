package wsn;

import java.io.*;
import java.awt.*;
import javax.swing.ImageIcon;
public class WSNSerialportOptions extends javax.swing.JDialog {

  WSNNodesManager mgr;
  public WSNSerialportOptions(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    this.mgr=(WSNNodesManager)parent;
            int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=600;
        int h2=400;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        init();
  }
  public void init(){
   String id=mgr.wsn.getItemId((String)mgr.jList3.getSelectedValue()); 
   String itemname=mgr.wsn.getItemName((String)mgr.jList3.getSelectedValue());
   String port=mgr.jLabel4.getText().trim();
   setTitle("Options for Serial port "+itemname+":"+port);
      String conf[]=mgr.wsn.w.csvLineToArray(mgr.serialportConfig);
   if(conf.length > 5 && mgr.wsn.isNumeric(conf[5])){
     switch(Integer.parseInt(conf[5])){
       case 0:
         jRadioButton1.setSelected(true);
         break;
       case 1:
         jRadioButton2.setSelected(true);
         break;
       case 2:
         jRadioButton3.setSelected(true);
         if(conf.length > 6) jTextField3.setText(conf[6]); else jTextField3.setText("");
         break;
       case 3:
         jRadioButton4.setSelected(true);
         if(conf.length > 6) jTextField4.setText(conf[6]); else jTextField4.setText("");
         break;
       case 5:
         jRadioButton5.setSelected(true);
         if(conf.length > 6) jTextField1.setText(conf[6]); else jTextField1.setText("");
         break;
       case 7:
         jRadioButton6.setSelected(true);
         break;
     }
   } else jRadioButton2.setSelected(true);

   this.setVisible(true);
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jLabel1 = new javax.swing.JLabel();
    jRadioButton1 = new javax.swing.JRadioButton();
    jRadioButton2 = new javax.swing.JRadioButton();
    jRadioButton3 = new javax.swing.JRadioButton();
    jRadioButton4 = new javax.swing.JRadioButton();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jTextField3 = new javax.swing.JTextField();
    jTextField4 = new javax.swing.JTextField();
    jRadioButton5 = new javax.swing.JRadioButton();
    jRadioButton6 = new javax.swing.JRadioButton();
    jTextField1 = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(null);

    jLabel1.setFont(new java.awt.Font("?s?????", 0, 18));
    jLabel1.setText("Options for data sending:");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(35, 24, 180, 30);

    buttonGroup1.add(jRadioButton1);
    jRadioButton1.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton1.setText("Not to sending out to other node.");
    getContentPane().add(jRadioButton1);
    jRadioButton1.setBounds(40, 60, 290, 31);

    buttonGroup1.add(jRadioButton2);
    jRadioButton2.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton2.setSelected(true);
    jRadioButton2.setText("Sending to all nodes.");
    getContentPane().add(jRadioButton2);
    jRadioButton2.setBounds(40, 90, 200, 31);

    buttonGroup1.add(jRadioButton3);
    jRadioButton3.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton3.setText("Sending to node IP:");
    getContentPane().add(jRadioButton3);
    jRadioButton3.setBounds(40, 180, 190, 31);

    buttonGroup1.add(jRadioButton4);
    jRadioButton4.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton4.setText("Sending to node ID:");
    getContentPane().add(jRadioButton4);
    jRadioButton4.setBounds(40, 210, 180, 31);

    jButton1.setFont(new java.awt.Font("?s?????", 0, 24));
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(120, 300, 100, 40);

    jButton2.setFont(new java.awt.Font("?s?????", 0, 24));
    jButton2.setText("Cancel");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton2);
    jButton2.setBounds(260, 300, 130, 40);

    jTextField3.setFont(new java.awt.Font("?s?????", 0, 18));
    getContentPane().add(jTextField3);
    jTextField3.setBounds(250, 180, 210, 29);

    jTextField4.setFont(new java.awt.Font("?s?????", 0, 18));
    getContentPane().add(jTextField4);
    jTextField4.setBounds(250, 210, 210, 29);

    buttonGroup1.add(jRadioButton5);
    jRadioButton5.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton5.setText("Sending to node group:");
    getContentPane().add(jRadioButton5);
    jRadioButton5.setBounds(40, 120, 210, 31);

    buttonGroup1.add(jRadioButton6);
    jRadioButton6.setFont(new java.awt.Font("?s?????", 0, 18));
    jRadioButton6.setText("Sending to main node.");
    getContentPane().add(jRadioButton6);
    jRadioButton6.setBounds(40, 150, 210, 31);

    jTextField1.setFont(new java.awt.Font("?s?????", 0, 18));
    getContentPane().add(jTextField1);
    jTextField1.setBounds(260, 120, 170, 29);

    pack();
  }

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  setVisible(false);
}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
   String conf[]=mgr.wsn.w.csvLineToArray(mgr.serialportConfig);
   if(conf.length<10) {conf=new String[10]; for(int i=0;i<conf.length;i++) conf[i]="";}
   if(jRadioButton1.isSelected()) conf[5]="0";
   else if(jRadioButton2.isSelected()) conf[5]="1";
   else if(jRadioButton3.isSelected()) {conf[5]="2"; conf[6]=jTextField3.getText().trim();}
   else if(jRadioButton4.isSelected()) {conf[5]="3"; conf[6]=jTextField4.getText().trim();}
   else if(jRadioButton5.isSelected()) {conf[5]="5"; conf[6]=jTextField1.getText().trim();}
   else if(jRadioButton6.isSelected()) conf[5]="7";
   String contCmd="performcommand wsn.WSN setserialportconfig "+mgr.jLabel4.getText().trim()+" "+mgr.wsn.w.arrayToCsvLine(conf) +" null null null null null null null null 0 0 0 0 ? ? ? 0";
   mgr.wsn.w.sendToOne(contCmd,mgr.wsn.getItemId((String)mgr.jList3.getSelectedValue()));
   setVisible(false);
}

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {

        

    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(WSNSocketportOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(WSNSocketportOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(WSNSocketportOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(WSNSocketportOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        WSNSocketportOptions dialog = new WSNSocketportOptions(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

          @Override
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JRadioButton jRadioButton1;
  private javax.swing.JRadioButton jRadioButton2;
  private javax.swing.JRadioButton jRadioButton3;
  private javax.swing.JRadioButton jRadioButton4;
  private javax.swing.JRadioButton jRadioButton5;
  private javax.swing.JRadioButton jRadioButton6;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField3;
  private javax.swing.JTextField jTextField4;

}