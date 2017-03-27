package wsn;

import java.io.*;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.text.html.*;
import javax.swing.event.*;
import y.ylib.*;
public class WSNUsage extends javax.swing.JFrame implements HyperlinkListener{
  java.util.ResourceBundle bundle2;
  WSN wsn;
    public WSNUsage(WSN wsn) {
        initComponents();
        this.wsn=wsn;
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 90000)/100000;
        int h2=(h * 90000)/100000;

        setSize(w2,h2);
        setTitle(bundle2.getString("WSNUsage.xy.msg1"));

        this.setVisible(true);
        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
        jEditorPane1.setEditable(false);
        jEditorPane1.addHyperlinkListener(this);
    }
public void hyperlinkUpdate(HyperlinkEvent e){
    if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)  { 
        

        String webAddr=e.getURL().toString();
         if(webAddr.indexOf("http")==-1){
             webAddr=webAddr.substring(5);
             webAddr=webAddr.replace('/', File.separatorChar);
             webAddr=(new File(webAddr)).getAbsolutePath();
             webAddr="file:///"+webAddr.replace(File.separatorChar,'/');
         }

         wsn.openURL.open(webAddr);

    }  
}
public void setPage(String page){
   try{
          jEditorPane1.setPage(page);
   }catch(IOException e){e.printStackTrace();}
}

    @SuppressWarnings("unchecked")

  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    jEditorPane1 = new javax.swing.JEditorPane();

    jScrollPane1.setViewportView(jEditorPane1);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

    pack();
  }

  private javax.swing.JEditorPane jEditorPane1;
  private javax.swing.JScrollPane jScrollPane1;

}