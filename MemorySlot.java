import java.util.*;

public class MemorySlot {

  int sTime;
  int eTime;
  int size;
  int eSize;
  boolean used;

  public MemorySlot(){
    this.sTime = 0;
    this.eTime = 0;
    this.size = 0;
    this.eSize = 0;
    this.used = false;
  }

  public void setSTime(int item){
    this.sTime = item;
  }

  public void setETime(int item){
    this.eTime = item;
  }

  public void setSize(){
    this.size = this.eTime - this.sTime;
  }

  public void setESize(int item){
    this.eSize = item;
  }

  public void setUsed(boolean item){
    this.used = item;
  }

  public int getSTime(){
    return sTime;
  }

  public int getETime(){
    return eTime;
  }

  public int getSize(){
    return size;
  }

  public int getESize(){
    return eSize;
  }

  public boolean getUsed(){
    return used;
  }
}


