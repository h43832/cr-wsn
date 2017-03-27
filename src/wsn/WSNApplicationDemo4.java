package wsn;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;
import javax.swing.*;
import java.util.*;
public class WSNApplicationDemo4  extends WSNApplication implements Runnable {
  Thread myThread;
  TreeMap itemData=new TreeMap();
  WSNApplicationDemo4B a4b;
  WSNApplicationDemo4C a4c;
  WSN wsn;
  String itemFile="";
  java.util.ResourceBundle bundle2;
  public static ImageIcon omgOn=new javax.swing.ImageIcon("apps"+File.separator+"cr-wsn"+File.separator+"images"+File.separator+"20150409green_t.gif"),
          omgOff=new javax.swing.ImageIcon("apps"+File.separator+"cr-wsn"+File.separator+"images"+File.separator+"20150409gray_t.gif");
  public WSNApplicationDemo4() {
    initComponents();
      bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
      Image img = omgOn.getImage() ;  
      Image newimg = img.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH ) ;  
      omgOn = new javax.swing.ImageIcon( newimg );
      img = omgOff.getImage() ;  
      newimg = img.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH ) ;  
      omgOff = new javax.swing.ImageIcon( newimg );
      itemFile="apps"+File.separator+"cr-wsn"+File.separator+bundle2.getString("WSNApplicationDemo4.xy.msg1")+".txt";
      jTable1.getColumn("Online").setCellRenderer(new ButtonRenderer());
      jTable1.getColumn("Online").setCellEditor(new ButtonEditor(new JCheckBox(),this));
        int width=Toolkit.getDefaultToolkit().getScreenSize().width;
        int h=Toolkit.getDefaultToolkit().getScreenSize().height-20;

        int w2=(width * 95000)/100000;
        int h2=(h * 95000)/100000;

        setSize(w2,h2);

        setLocation((width-w2)/2,(h-h2)/2);

        Image iconImage=new ImageIcon(getClass().getClassLoader().getResource("crtc_logo_t.gif")).getImage();
        setIconImage(iconImage);
    }
    public void init(WSN wsn){
      this.wsn=wsn;

      myThread=new Thread(this);
      myThread.start();
      a4b=new WSNApplicationDemo4B(this,wsn);
      a4c=new WSNApplicationDemo4C(this,wsn);
      readItemFile(itemFile,2);
      String cmdStr="performmessage wsn.WSN getdatasource ";
      wsn.w.sendToAll(cmdStr);
  }

    public void onExit(int type){

  }
  public boolean runInBackground(){
    return false;
  }
public void run(){

}
public String getSelectedId(){
  String rtn=null;
  int row=jTable1.getSelectedRow();
  if(row!=-1){
    rtn=(String)jTable1.getValueAt(row, 0);
  }
  return rtn;
}
  public void setBlink(boolean onoff){
     if(onoff){
         if(wsn!=null) jLabel2.setText("Ap Node "+wsn.w.getGNS(6)+"           "+wsn.formatter.format(new Date()));
         if(a4b!=null) a4b.jLabel1.setText(wsn.formatter.format(new Date()));
         if(a4c!=null) a4c.jLabel1.setText(wsn.formatter.format(new Date()));
         Iterator it=itemData.values().iterator();
         int online=0;
         for(;it.hasNext();){
           String da[]=wsn.w.csvLineToArray((String)it.next());
           if(wsn.w.checkOneVar(da[4], 0)) online++;
         }
         jLabel1.setText("Total connection count:  "+itemData.size()+", online: "+online+", offline: "+(itemData.size()-online));
     }
  }
  public void setData(long time,String nodeId, String dataSrc,String data){
    String ip="",conn="";
    if(a4b!=null) a4b.setData(time,dataSrc,data);
    TreeMap itemData2=(TreeMap)itemData.clone();
    if(dataSrc.indexOf(":")!=-1) ip=dataSrc.substring(0,dataSrc.indexOf(":"));
    else ip=wsn.w.getGNS(6);
    Iterator it=itemData2.keySet().iterator();
    int inx=0;
    for(;it.hasNext();){
      String arr[]=wsn.w.csvLineToArray((String)itemData2.get((String)it.next()));
      if(ip.equals(arr[1])){
           conn=(dataSrc.indexOf(":")>-1? dataSrc.substring(dataSrc.indexOf(":")+1):dataSrc);
           if(conn.equals(arr[2])){
               String time2=wsn.formatter.format(new Date());

               jTable1.getModel().setValueAt(time2, inx,5);

               jTable1.getModel().setValueAt("Y", inx, 4);

               arr[4]=wsn.w.addOneVar(arr[4], 0);
               arr[5]=time2;
               itemData.put(arr[0], wsn.w.arrayToCsvLine(arr));
           }
      }
      inx++;
    }
  }

  

  public void setStatus(String nodeId,String dataSrc[],int statusCode){
    String ip="",conn="";
    TreeMap itemData2=(TreeMap)itemData.clone();
    if(dataSrc[0].indexOf(":")!=-1) ip=dataSrc[0].substring(0,dataSrc[0].indexOf(":"));
    else ip=wsn.w.getGNS(6);
    Iterator it=itemData2.keySet().iterator();
    int inx=0;
    if(!dataSrc[0].equals("0")){
    for(;it.hasNext();){
      String data[]=wsn.w.csvLineToArray((String)itemData2.get((String)it.next()));
      if(ip.equals(data[1])){
         for(int i=0;i<dataSrc.length;i++){
           conn=(dataSrc[i].indexOf(":")>-1? dataSrc[i].substring(dataSrc[i].indexOf(":")+1):dataSrc[i]);
           if(conn.equals(data[2])){
             if(statusCode==1 || statusCode==3){
               jTable1.getModel().setValueAt(nodeId, inx, 2);

               jTable1.getModel().setValueAt("Y", inx, 4);

               data[4]=wsn.w.addOneVar(data[4], 0);
               data[6]=nodeId;
               itemData.put(data[0], wsn.w.arrayToCsvLine(data));
             }else if(statusCode==2 || statusCode==4){
               jTable1.getModel().setValueAt("", inx, 2);

               jTable1.getModel().setValueAt("N", inx, 4);

               data[4]=wsn.w.removeOneVar(data[4], 0);
               data[6]="";
               itemData.put(data[0], wsn.w.arrayToCsvLine(data));
             }
             break;
           }
         }
      }
      inx++;
    }
    }
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jPanel5 = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jPanel4 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    setTitle(bundle.getString("WSNApplicationDemo4.title")); 
    setName("Form"); 

    jPanel1.setName("jPanel1"); 
    jPanel1.setLayout(new java.awt.GridLayout(3, 1));

    jPanel3.setName("jPanel3"); 
    jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jLabel1.setFont(jLabel1.getFont());

    jLabel1.setText(bundle.getString("WSNApplicationDemo4.jLabel1.text")); 
    jLabel1.setName("jLabel1"); 
    jPanel3.add(jLabel1);

    jPanel1.add(jPanel3);

    jPanel5.setName("jPanel5"); 
    jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jButton1.setFont(jButton1.getFont());
    jButton1.setText(bundle.getString("WSNApplicationDemo4.jButton1.text")); 
    jButton1.setName("jButton1"); 
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jPanel5.add(jButton1);

    jButton2.setFont(jButton2.getFont());
    jButton2.setText(bundle.getString("WSNApplicationDemo4.jButton2.text")); 
    jButton2.setName("jButton2"); 
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });
    jPanel5.add(jButton2);

    jPanel1.add(jPanel5);

    jPanel4.setName("jPanel4"); 
    jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jLabel2.setFont(jLabel2.getFont());
    jLabel2.setText(bundle.getString("WSNApplicationDemo4.jLabel2.text")); 
    jLabel2.setName("jLabel2"); 
    jPanel4.add(jLabel2);

    jPanel1.add(jPanel4);

    getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

    jPanel2.setName("jPanel2"); 
    jPanel2.setLayout(new java.awt.BorderLayout());

    jScrollPane1.setName("jScrollPane1"); 

    jTable1.setFont(jTable1.getFont());
    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null}
      },
      new String [] {
        "No", "Node IP", "Node ID", "Connection ID", "Online", "Last data time", "Position"
      }
    ));
    jTable1.setName("jTable1"); 
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jTable1MouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(jTable1);

    jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

    pack();
  }

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

}

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
  a4b.setSelectedId(getSelectedId());
  if(!a4b.isVisible()) a4b.setVisible(true);
  a4b.toFront();
}

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
  a4c.setSelectedId(getSelectedId());
  if(!a4c.isVisible()) {a4c.setVisible(true); a4c.renewFileList();}
  a4c.toFront();
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
      java.util.logging.Logger.getLogger(WSNApplicationDemo4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(WSNApplicationDemo4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(WSNApplicationDemo4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(WSNApplicationDemo4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new WSNApplicationDemo4().setVisible(true);
      }
    });
  }

public void readItemFile(String itemFile,int type){
    if(itemFile!=null && itemFile.length()>0){
       File f=new File(itemFile);
       if(f.exists() && f.isFile()){
         StringBuilder sb=new StringBuilder();
         try{
           FileInputStream in=new FileInputStream(itemFile);
           BufferedReader d= new BufferedReader(new InputStreamReader(in));
           while(true){
	     String str1=d.readLine();
	     if(str1==null) {in.close(); d.close(); break; }

             if(str1.length()>0){
               String arr[]=wsn.w.csvLineToArray(str1);
               str1=wsn.w.replace(str1,"localhost",wsn.w.getGNS(6));
               itemData.put(arr[0], str1);
             }
           }
	   in.close();
	   d.close();
           if(itemData.size()>0){
             int inx=0;
             Iterator it=itemData.keySet().iterator();
             for(;it.hasNext();){
               String key=(String)it.next();
               String data[]=wsn.w.csvLineToArray((String)itemData.get(key));
               jTable1.getModel().setValueAt(data[0], inx, 0);
               jTable1.getModel().setValueAt(data[1], inx, 1);
               jTable1.getModel().setValueAt(data[2], inx, 3);

               jTable1.getModel().setValueAt("N", inx, 4);

               jTable1.getModel().setValueAt(data[3], inx, 6);
               a4b.listModel1.addElement(data[0]+":"+data[1]+":"+data[2]);

               inx++;
             }
           }
         }catch(FileNotFoundException e){
               e.printStackTrace();
           }
    catch(IOException e){

       if(type==1) JOptionPane.showMessageDialog(this,"Error in reading "+itemFile+" file.");

        e.printStackTrace();
    }
    }else {
           if(type==1) JOptionPane.showMessageDialog(this,"Warning: event file "+itemFile+" not exist.");

      }
}
}

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable jTable1;

}
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        ImageIcon omg;
        if(value!=null && ((String)value).equals("Y")) omg= WSNApplicationDemo4.omgOn;
             else omg=WSNApplicationDemo4.omgOff;
             setIcon(omg);
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    WSNApplicationDemo4 a4;
    public ButtonEditor(JCheckBox checkBox,WSNApplicationDemo4 a4) {
        super(checkBox);
        this.a4=a4;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                fireEditingStopped();
                ButtonEditor.this.a4.a4b.setSelectedId(ButtonEditor.this.a4.getSelectedId());
                if(!ButtonEditor.this.a4.a4b.isVisible()) ButtonEditor.this.a4.a4b.setVisible(true);
                ButtonEditor.this.a4.a4b.toFront();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {

        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}