package wsn;

import java.util.*;
import java.awt.*;
import javax.swing.text.*;
public class WSNApplicationDemo extends WSNApplication implements Runnable{
  WSN wsn;
  Vector waitV=new Vector();
  boolean isSleep=false;
  long longWaitTime=31536000000L;
  Thread myThread;
  public WSNApplicationDemo() {
        initComponents();
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;
        int w2=520;
        int h2=390;
        setSize(w2,h2);
        setLocation((width-w2)/2,(h-h2)/2);
    }

  public void init(WSN wsn){
      this.wsn=wsn;
      myThread=new Thread(this);
      myThread.start();
  }

  public void onExit(int type){

  }
  public boolean runInBackground(){
    return false;
  }
  public void run(){
    while(true){
        while(waitV.size()>0){
          String data=(String)waitV.get(0);
          data=wsn.getStringData(data, 1, -1, -1);
          

          if(wsn.isNumeric(data)){
           double da=Double.parseDouble(data);
           da=Math.round(da*100.0)/100.0;
           jLabel20.setText(""+da);
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
  public void setBlink(boolean onoff){
      if(onoff){
          jLabel1.setText(wsn.formatter.format(new Date()));
      }
  }

  public void setStatus(String nodeId,String dataSrc[],int statusCode){}
  public void setData(long time,String nodeId,String dataSrc,String data){
    waitV.addElement(data);
    if(isSleep) myThread.interrupt();
  }
    @SuppressWarnings("unchecked")

  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel14 = new javax.swing.JLabel();
    jLabel20 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("CR-WSN Temperature Monitoring System");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(null);

    jLabel1.setFont(new java.awt.Font("Arial", 0, 12));
    jLabel1.setText("2015-02-24 09:56:00");
    getContentPane().add(jLabel1);
    jLabel1.setBounds(20, 10, 140, 15);

    jLabel8.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel8.setForeground(new java.awt.Color(51, 51, 255));
    jLabel8.setText("Temperature");
    getContentPane().add(jLabel8);
    jLabel8.setBounds(20, 70, 150, 22);

    jLabel14.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel14.setText("?XC");
    getContentPane().add(jLabel14);
    jLabel14.setBounds(270, 60, 30, 40);

    jLabel20.setFont(new java.awt.Font("Arial", 0, 18));
    jLabel20.setForeground(new java.awt.Color(255, 102, 0));
    jLabel20.setText("13.35");
    getContentPane().add(jLabel20);
    jLabel20.setBounds(180, 70, 80, 22);

    pack();
  }

private void formWindowClosing(java.awt.event.WindowEvent evt) {
  setVisible(false);
}

  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel20;
  private javax.swing.JLabel jLabel8;

}