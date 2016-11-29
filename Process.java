import java.util.*;

public class Process {

  int pid;
  int size;
  boolean used;

  public Process(){
    this.pid = 0;
    this.size = 0;
    this.used = false;
  }

  public void setPid(int item){
    this.pid = item;
  }

  public void setSize(int item){
    this.size = item;
  }

  public void setUsed(boolean item){
    this.used = item;
  }

  public int getPid(){
    return pid;
  }

  public int getSize(){
    return size;
  }

  public boolean getUsed(){
    return used;
  }
}


