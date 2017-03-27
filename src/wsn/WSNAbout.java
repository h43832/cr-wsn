package wsn;

import java.awt.*;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.text.html.*;
import javax.swing.event.*;
import y.ylib.*;
public class WSNAbout extends javax.swing.JFrame implements HyperlinkListener{
  java.util.ResourceBundle bundle2;
  WSN wsn;
  public WSNAbout(WSN wsn) {
       this.wsn=wsn;
        initComponents();
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=800;
        int h2=600;

        setSize(w2,h2);
        setTitle(getTitle()+" V. "+wsn.version+"  "+wsn.versionDate);

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

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSNAbout.title")); 

    jEditorPane1.setContentType(bundle.getString("WSNAbout.jEditorPane1.contentType")); 

    jScrollPane1.setViewportView(jEditorPane1);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

    pack();
  }

  private javax.swing.JEditorPane jEditorPane1;
  private javax.swing.JScrollPane jScrollPane1;

}