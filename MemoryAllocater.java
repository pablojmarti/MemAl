import java.lang.*;
import java.io.*;
import java.util.*;

public class MemoryAllocater {


  public static void main(String[] args) {
    String fileName = "Minput.txt";
    int size = readS("Minput.txt");
    fileName = "Pinput.txt";
    size = readS("Pinput.txt");

    MemorySlot mem[] = new MemorySlot[size];
    mem = readM(size, fileName);

    Process p[] = new Process[size];
    p = readP(size, fileName);
 
  }

  // read file from Minput
  public static int readS(String fileName){

    int size = 0;

    try {
      Reader inp = new FileReader(fileName);
      StreamTokenizer tstream = new StreamTokenizer(inp);
      tstream.parseNumbers();

      int token = tstream.nextToken();

      size = (int)tstream.nval;

    }
    catch(IOException e){
      System.out.println(e);
    }
    return size;
  }

  public static MemorySlot[] readM(int size, String fileName){

    MemorySlot[] temp = new MemorySlot[size];
    for(int i = 0; i < size; i++){
      temp[i] = new MemorySlot();
    }

    try {
      Reader inp = new FileReader(fileName);
      StreamTokenizer tstream = new StreamTokenizer(inp);
      tstream.parseNumbers();

      int token = tstream.nextToken();

      for(int i = 0; i < size; i++){
        if(token == StreamTokenizer.TT_EOF)
          break;
        else{
          token = tstream.nextToken();
          temp[i].setSTime((int)tstream.nval);
          token = tstream.nextToken();
          temp[i].setETime((int)tstream.nval);
          temp[i].setSize();
          temp[i].setUsed(false);
        }
      }
    }
    catch(IOException e){
      System.out.println(e);
    }
    return temp;
  } 

  public static Process[] readP(int size, String fileName){

    Process[] temp = new Process[size];
    for(int i = 0; i < size; i++){
      temp[i] = new Process();
    }

    try {
      Reader inp = new FileReader(fileName);
      StreamTokenizer tstream = new StreamTokenizer(inp);
      tstream.parseNumbers();

      int token = tstream.nextToken();

      for(int i = 0; i < size; i++){
        if(token == StreamTokenizer.TT_EOF)
          break;
        else{
          token = tstream.nextToken();
          temp[i].setPid((int)tstream.nval);
          token = tstream.nextToken();
          temp[i].setSize((int)tstream.nval);
          temp[i].setUsed(false);
        }
      }
    }
    catch(IOException e){
      System.out.println(e);
    }
    return temp;
  } 


  // First Fit Method
  public static int FF(int size, Process ps[], MemorySlot mem[], MemorySlot ffMem[]){
    int c = 0;    // create the varible that will return the negative

    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        if(ps[i].getSize() <= mem[j].getSize()){ // if the size of the process is less than the size of the memory slot
          if( !mem[j].getUsed()){     // and if the memory slot isn't used
            if (!ps[i].getUsed()){    // and if the process slot hasn't been used

              // Add the process to the memory slot and calculate the correct end time
              ffMem[j].setSTime(mem[j].getSTime());
              ffMem[j].setETime(mem[j].getETime() - (mem[j].getSize() - ps[i].getSize()));
              ffMem[j].setESize(ps[i].getPid());
              ffMem[j].setUsed(true);   // the memory slot is used
              ps[i].setUsed(true);      // the process is used
              mem[j].setUsed(true);     // the memory slot is used
            }
          } 
        }
      }

      // if the process hasn't been used add it to c and make it a negative
      if (!ps[i].getUsed())
        c = -ps[i].getPid();
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
