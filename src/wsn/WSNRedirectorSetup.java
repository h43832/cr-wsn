package wsn;

import javax.swing.JOptionPane;
import java.io.*;
import java.awt.*;
import javax.swing.ImageIcon;
public class WSNRedirectorSetup extends javax.swing.JDialog {

  WSNRedirector redirector;
  public WSNRedirectorSetup(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    this.redirector=(WSNRedirector)parent;
       int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=410;
        int h2=300;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        jTextField1.setText(redirector.dataSource1);
        jTextField2.setText(redirector.dataSource2);
        if(redirector.chkProps("auto_start_if_run_my_ap_only")) jCheckBox1.setSelected(true); else jCheckBox1.setSelected(false);
        this.setVisible(true);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jTextField2 = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jCheckBox1 = new javax.swing.JCheckBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Redirector Setup");
    getContentPane().setLayout(null);

    jLabel1.setText("Data Source 1");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(50, 60, 90, 15);

    jLabel2.setText("Data Source 2");
    getContentPane().add(jLabel2);
    jLabel2.setBounds(250, 60, 90, 15);

    jTextField1.setToolTipText("192.168.1.200:6001-1 or 192.168.1.200:COM17 or 6001-1 or COM17");
    getContentPane().add(jTextField1);
    jTextField1.setBounds(30, 90, 130, 21);

    jTextField2.setToolTipText("192.168.1.200:6001-1 or 192.168.1.200:COM17 or 6001-1 or COM17");
    getContentPane().add(jTextField2);
    jTextField2.setBounds(240, 90, 130, 21);

    jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); 
    jLabel3.setText("------>");
    getContentPane().add(jLabel3);
    jLabel3.setBounds(180, 90, 50, 15);

    jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); 
    jLabel4.setText("<------");
    getContentPane().add(jLabel4);
    jLabel4.setBounds(180, 100, 50, 15);

    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton1);
    jButton1.setBounds(80, 180, 80, 23);

    jButton2.setText("Cancel");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    getContentPane().add(jButton2);
    jButton2.setBounds(190, 180, 80, 23);

    jCheckBox1.setText("Auto start when \"Run_My_Ap_Only\"");
    getContentPane().add(jCheckBox1);
    jCheckBox1.setBounds(30, 130, 300, 23);

    pack();
  }

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  String src1=jTextField1.getText().trim();
  String src2=jTextField2.getText().trim();
  if(src1.length()<1 || src2.length()<1) {
    JOptionPane.showMessageDialog(this, "Data source can not be empty.");
    return;
  } else if(src1.equals(src2)) {
    JOptionPane.showMessageDialog(this, "Two data sources can not be the same.");
    return;
  }
  redirector.setDataSource(src1,src2);
  redirector.props.setProperty("auto_start_if_run_my_ap_only", (jCheckBox1.isSelected()? "Y":"N"));
  setVisible(false);
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
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
      java.util.logging.Logger.getLogger(WSNRedirectorSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(WSNRedirectorSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(WSNRedirectorSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(WSNRedirectorSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        WSNRedirectorSetup dialog = new WSNRedirectorSetup(new javax.swing.JFrame(), true);
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

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;

}