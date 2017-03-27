package wsn;

public class WSNLineOptionPanel extends javax.swing.JPanel {
   WSN wsn;
   WSNLineChart lineChart;
   WSNLineIntegraPanel iPanel;
    public WSNLineOptionPanel(WSN wsn, WSNLineChart lineChart,WSNLineIntegraPanel iPanel) {
        this.wsn=wsn;
        this.lineChart=lineChart;
        this.iPanel=iPanel;
        initComponents();
        if(wsn.w.getHVar("a_test")==null || !wsn.w.getHVar("a_test").equalsIgnoreCase("true")){
           jCheckBox1.setVisible(false);    
           jCheckBox9.setVisible(false);    

        }
    }

    @SuppressWarnings("unchecked")

  private void initComponents() {

    jCheckBox10 = new javax.swing.JCheckBox();
    jCheckBox9 = new javax.swing.JCheckBox();
    jCheckBox8 = new javax.swing.JCheckBox();
    jCheckBox7 = new javax.swing.JCheckBox();
    jLabel27 = new javax.swing.JLabel();
    jCheckBox1 = new javax.swing.JCheckBox();
    jCheckBox2 = new javax.swing.JCheckBox();

    setBackground(new java.awt.Color(204, 255, 255));
    setLayout(null);

    jCheckBox10.setFont(jCheckBox10.getFont().deriveFont(jCheckBox10.getFont().getSize()+6f));
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    jCheckBox10.setText(bundle.getString("WSNLineOptionPanel.jCheckBox10.text")); 
    jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox10ActionPerformed(evt);
      }
    });
    add(jCheckBox10);
    jCheckBox10.setBounds(400, 30, 170, 31);

    jCheckBox9.setFont(jCheckBox9.getFont().deriveFont(jCheckBox9.getFont().getSize()+6f));
    jCheckBox9.setText(bundle.getString("WSNLineOptionPanel.jCheckBox9.text")); 
    jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox9ActionPerformed(evt);
      }
    });
    add(jCheckBox9);
    jCheckBox9.setBounds(480, 80, 140, 30);

    jCheckBox8.setFont(jCheckBox8.getFont().deriveFont(jCheckBox8.getFont().getSize()+6f));
    jCheckBox8.setSelected(true);
    jCheckBox8.setText(bundle.getString("WSNLineOptionPanel.jCheckBox8.text")); 
    jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox8ActionPerformed(evt);
      }
    });
    add(jCheckBox8);
    jCheckBox8.setBounds(40, 30, 190, 31);

    jCheckBox7.setFont(jCheckBox7.getFont().deriveFont(jCheckBox7.getFont().getSize()+6f));
    jCheckBox7.setSelected(true);
    jCheckBox7.setText(bundle.getString("WSNLineOptionPanel.jCheckBox7.text")); 
    jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox7ActionPerformed(evt);
      }
    });
    add(jCheckBox7);
    jCheckBox7.setBounds(240, 30, 150, 31);

    jLabel27.setFont(jLabel27.getFont().deriveFont(jLabel27.getFont().getSize()+6f));
    jLabel27.setText(bundle.getString("WSNLineOptionPanel.jLabel27.text")); 
    add(jLabel27);
    jLabel27.setBounds(40, 80, 250, 30);

    jCheckBox1.setFont(jCheckBox1.getFont().deriveFont(jCheckBox1.getFont().getSize()+6f));
    jCheckBox1.setText(bundle.getString("WSNLineOptionPanel.jCheckBox1.text")); 
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox1ActionPerformed(evt);
      }
    });
    add(jCheckBox1);
    jCheckBox1.setBounds(580, 30, 120, 31);

    jCheckBox2.setFont(jCheckBox2.getFont().deriveFont(jCheckBox2.getFont().getSize()+6f));
    jCheckBox2.setText(bundle.getString("WSNLineOptionPanel.jCheckBox2.text")); 
    jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBox2ActionPerformed(evt);
      }
    });
    add(jCheckBox2);
    jCheckBox2.setBounds(320, 80, 150, 30);
  }

private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {

  if(lineChart.currentConfig!=null){
    if(jCheckBox10.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 3);
    else lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 3);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}

private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {
  if(lineChart.currentConfig!=null){
    if(jCheckBox9.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 6);
    else lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 6);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}

private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {
  if(lineChart.currentConfig!=null){
         if(jCheckBox8.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 11);
    else lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 11);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}

private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {
  if(lineChart.currentConfig!=null){
    if(jCheckBox7.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 12);
    else lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 12);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}

private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
  if(lineChart.currentConfig!=null){
    if(jCheckBox1.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 4);
    else lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 4);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}

private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {
  if(lineChart.currentConfig!=null){
         if(jCheckBox2.isSelected()) lineChart.currentConfig.longValue[0]=wsn.w.addOneVar(lineChart.currentConfig.longValue[0], 18);
    else lineChart.currentConfig.longValue[0]=wsn.w.removeOneVar(lineChart.currentConfig.longValue[0], 18);
    lineChart.config.put(lineChart.currentDataSrcId,lineChart.currentConfig);

    iPanel.tPanel.toRepaint();
  }
}
public void setProperties(long pros){
  if(wsn.w.checkOneVar(pros, 12)) jCheckBox7.setSelected(true); else  jCheckBox7.setSelected(false); 
  if(wsn.w.checkOneVar(pros, 11)) jCheckBox8.setSelected(true); else  jCheckBox8.setSelected(false); 
}

  public javax.swing.JCheckBox jCheckBox1;
  public javax.swing.JCheckBox jCheckBox10;
  public javax.swing.JCheckBox jCheckBox2;
  public javax.swing.JCheckBox jCheckBox7;
  public javax.swing.JCheckBox jCheckBox8;
  public javax.swing.JCheckBox jCheckBox9;
  public javax.swing.JLabel jLabel27;

}