
package wsn;

 public class WSNDataRecord{
   public StringBuffer sb=new StringBuffer();
   public boolean hexType;
   public WSNDataRecord(boolean hexType){
     this.hexType=hexType;
   }
   public void setHexType(boolean type){
     this.hexType=type;
   }
   public void clear(){
     sb.delete(0,sb.length());
   }
 }