import java.util.*;

public class Process {

  int pid;
  int size;

  public Process(){
    this.pid = 0;
    this.size = 0;
  }

  public void setPid(int item){
    this.pid = item;
  }

  public void setSize(int item){
    this.size = item;
  }

  public int getPid(){
    return pid;
  }

  public int getSize(){
    return size;
  }
}


