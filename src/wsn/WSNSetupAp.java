package wsn;

import java.awt.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import javax.swing.ImageIcon;
public class WSNSetupAp extends javax.swing.JDialog {
  WSN wsn;
  ResourceBundle bundle2;
    public WSNSetupAp(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=550;
        int h2=260;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }
public void init(WSN wsn){
    this.wsn=wsn;
}

    @SuppressWarnings("unchecked")

    private void initComponents() {

        setBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        setTitle(bundle.getString("WSNSetupAp.title")); 
        getContentPane().setLayout(null);

        setBtn.setFont(setBtn.getFont().deriveFont(setBtn.getFont().getSize()+6f));
        setBtn.setText(bundle.getString("WSNSetupAp.setBtn.text")); 
        setBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setBtnActionPerformed(evt);
            }
        });
        getContentPane().add(setBtn);
        setBtn.setBounds(370, 30, 100, 31);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+6f));
        jLabel1.setText(bundle.getString("WSNSetupAp.jLabel1.text")); 
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 30, 100, 30);

        jTextField1.setFont(jTextField1.getFont().deriveFont(jTextField1.getFont().getSize()+6f));
        jTextField1.setToolTipText(bundle.getString("WSNSetupAp.jTextField1.toolTipText")); 
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(130, 30, 220, 29);

        jLabel2.setText(bundle.getString("WSNSetupAp.jLabel2.text")); 
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 140, 440, 15);

        jLabel3.setText(bundle.getString("WSNSetupAp.jLabel3.text")); 
        getContentPane().add(jLabel3);
        jLabel3.setBounds(50, 120, 440, 15);

        jLabel4.setText(bundle.getString("WSNSetupAp.jLabel4.text")); 
        getContentPane().add(jLabel4);
        jLabel4.setBounds(50, 100, 370, 15);

        pack();
    }

private void setBtnActionPerformed(java.awt.event.ActionEvent evt) {
  setAp();
}

private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
  setAp();
}
void setAp(){
     String name=jTextField1.getText().trim();
   

   if(name.length()>0){
         this.setVisible(false);
         wsn.props.setProperty("my_ap",name);
         wsn.loadAp();

         for(Iterator it = wsn.myAps.values().iterator(); it.hasNext();) {
               WSNApplication wa= (WSNApplication)it.next();
               if(!wa.isVisible()) if(!wa.runInBackground()) wa.setVisible(true);
         }
   } else JOptionPane.showMessageDialog(this, bundle2.getString("WSNSetupAp.xy.msg1"));
}

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton setBtn;

}