package wsn;

public class WSNDataGeneratorDemo implements WSNDataGenerator{
  public byte[] getData(){
    return (""+(Math.random() * 10000.0)).getBytes();
  }
}