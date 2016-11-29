import java.lang.*;
import java.io.*;
import java.util.*;

public class MemoryAllocater {

  public static void main(String[] args) {
    String fileName = "Minput.txt";

    int size;

    try {
      Reader inp = new FileReader(fileName);
      StreamTokenizer tstream = new StreamTokenizer(inp);
      tstream.parseNumbers();

      int token = tstream.nextToken();

      size = (int)tstream.nval;

      MemorySlot[] mem = new MemorySlot[size];
      Process[] ps = new Process[size];
      for(int i = 0; i < size; i++){
        mem[i] = new MemorySlot();
        ps[i] = new Process();
      }

      token = tstream.nextToken();

      for(int i = 0; i < size; i++){
        if(token == size){
          break;
        }
        else{
          mem[i].setSTime((int)tstream.nval);
          token = tstream.nextToken();
          mem[i].setETime((int)tstream.nval);
          token = tstream.nextToken();
          mem[i].setSize();
          mem[i].setESize(mem[i].getSize());

        }
      }

      token = tstream.nextToken();

      for(int i = 0; i < size; i++){
        if(token == StreamTokenizer.TT_EOF){
          break;
        }
        else{
          ps[i].setPid((int)tstream.nval);
          token = tstream.nextToken();
          ps[i].setSize((int)tstream.nval);
          token = tstream.nextToken();

        }
      }


      System.out.println("End of Reading"); 

      // Test Loop
      System.out.println("total SIZE " + size);
      for(int i = 0; i < size; i++){
        System.out.print("start: " + mem[i].getSTime());
        System.out.print(" end: " + mem[i].getETime());
        System.out.print(" size: " + mem[i].getSize() + "\n");

      }
      for(int i = 0; i < size; i++){
        System.out.print("PID: " + ps[i].getPid());
        System.out.print(" size: " + ps[i].getSize() + "\n");
      }



      MemorySlot[] ffMem = new MemorySlot[size];
      MemorySlot[] bfMem = new MemorySlot[size];
      MemorySlot[] wfMem = new MemorySlot[size];

      for(int i = 0; i < size; i++){
        ffMem[i] = new MemorySlot();
        bfMem[i] = new MemorySlot();
        wfMem[i] = new MemorySlot();
      }

      int c = FF(size, ps, mem, ffMem);
      write(size, ffMem, "FFOutput.txt", c);
      c = BF(size, ps, mem, bfMem);
      write(size, bfMem, "BFOutput.txt", c);

      for(int i = 0; i < size; i++){
        System.out.print("start: " + ffMem[i].getSTime() + " end: " + ffMem[i].getETime() + " ID: " + ffMem[i].getESize()  + "\n");
        System.out.println(c);
      }

      for(int i = 0; i < size; i++){
        // System.out.print("start: " + bfMem[i].getSTime() + " end: " + bfMem[i].getETime() + " ID: " + bfMem[i].getESize() + c +"\n");
      }
    }

      catch(IOException e){
        System.out.println(e);
      }


  }

  public static int FF(int size, Process ps[], MemorySlot mem[], MemorySlot ffMem[]){
    MemorySlot[] temp = new MemorySlot[size];
    temp = mem;
    int c = 0;

    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){ 
        if(ps[i].getSize() <= mem[j].getSize() && !mem[j].getUsed()){
          ffMem[i].setSTime(mem[j].getSTime());
          ffMem[i].setETime(mem[j].getETime() - (mem[j].getSize() - ps[i].getSize()));
          ffMem[i].setESize(ps[i].getPid());
          mem[j].setUsed(true);
        }
      }
    }
    return c;
  }

  public static int BF(int size, Process ps[], MemorySlot mem[], MemorySlot bfMem[]){
    MemorySlot[] temp = new MemorySlot[size];
    temp = mem;
    int c = 0;

    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        if(temp[j].getESize() >= ps[i].getSize()){
          bfMem[j].setSTime(mem[j].getSTime());
          bfMem[j].setETime(mem[j].getETime() - (mem[j].getSize() - ps[i].getSize()));
          bfMem[j].setESize(ps[i].getPid()); 
        }
        else if(temp[i].getESize() >= ps[j].getSize()){
          bfMem[i].setSTime(mem[i].getSTime());
          bfMem[i].setETime(mem[i].getETime() - (mem[i].getSize() - ps[j].getSize()));
          bfMem[i].setESize(ps[j].getPid()); 
        }
        else if(temp[i].getESize() >= ps[i].getSize()){
          bfMem[i].setSTime(mem[i].getSTime());
          bfMem[i].setETime(mem[i].getETime() - (mem[i].getSize() - ps[i].getSize())); 
          temp[i].setESize(mem[j].getSize() - ps[i].getSize());
          bfMem[i].setESize(ps[i].getPid());
        }
      } 
    }


    return c;
  }

  public void WF(int size, Process ps[], MemorySlot mem[], MemorySlot wfMem[]){
  }

  public static void write(int size, MemorySlot mem[], String outName, int c){



    try {
      FileWriter output = new FileWriter(outName);

      for(int i = 0; i < size; i++){
        output.write(String.valueOf(mem[i].getSTime() + " "));
        output.write(String.valueOf(mem[i].getETime() + " "));
        output.write(String.valueOf(mem[i].getSize() + " " + "\n"));
      }
      output.write(String.valueOf(c));
      output.close();
    }
    catch(IOException e){
      System.out.println("ERROR" + e);
    }
  }
}
