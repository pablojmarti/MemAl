import java.lang.*;
import java.io.*;
import java.util.*;

public class MemoryAllocater {


  public static void main(String[] args) {
    int size = readS("Minput.txt");
    size = readS("Pinput.txt");

    MemorySlot mem[] = new MemorySlot[size];
    mem = readM(size, "Minput.txt");

    Process p[] = new Process[size];
    p = readP(size, "Pinput.txt");

    MemorySlot fin[] = new MemorySlot[size];
    for(int i = 0; i < size; i++){
      fin[i] = new MemorySlot();
    }

    int c = FF(size, p, mem, fin);
    write(size, fin,"FFOutput.txt", c);

<<<<<<< HEAD
    c = BF(size, p, mem, fin);
    write(size, fin, "BFOutput.txt", c);

=======
>>>>>>> 04cd38a53ee21e777e9e137645971b0a3f76b0e1

    c = WF(size, p, mem, fin);
    write(size, fin, "WFOutput.txt", c);

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
          temp[i].setSize(temp[i].getETime() - temp[i].getSTime());
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
  int c = 0; 

  int smol = -1;
  for(int i = 0; i < size; i++){
    for(int j = 0; j < size; j++){
      if(mem[j].getSize() >= ps[i].getSize()){
        if(smol == -1){
          smol = j;
        }
        else if (mem[j].getSize() < mem[smol].getSize()){
          smol = j;
        }
      }
    }
    if(smol != -1){
      bfMem[i].setSTime(mem[smol].getSTime());
      bfMem[i].setETime(mem[smol].getSTime() + ps[i].getSize());
      bfMem[i].setESize(ps[i].getPid());

      ps[i].setSize(0);
      ps[i].setUsed(true);

      smol = -1;
    }

    if(!ps[i].getUsed()){
      c = -ps[i].getPid();
    }
  }
    return c;
  }

  public static int WF(int size, Process ps[], MemorySlot mem[], MemorySlot wfMem[]){

    int big = -1;
    int c = 0;
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        if(mem[j].getSize() >= ps[i].getSize()){
          if(big == -1){
            big = i;
          }
          else if(mem[j].getSize() > mem[big].getSize()){
            big = i;
          }
        }
      }
      if(big != -1){
        wfMem[i].setSTime(mem[big].getSTime());
        wfMem[i].setETime(mem[big].getSTime() + ps[i].getSize());
        wfMem[i].setESize(ps[i].getPid());
        mem[big].setSTime(mem[big].getSTime() + ps[i].getSize());
        mem[big].setSize(mem[big].getSize() - ps[i].getSize());

        ps[i].setSize(0);
        ps[i].setUsed(true);
        big = -1;
      }
    }


    
    return c;
  }

  public static void write(int size, MemorySlot mem[], String outName, int c){



    try {
      FileWriter output = new FileWriter(outName);

      for(int i = 0; i < size; i++){
        output.write(String.valueOf(mem[i].getSTime() + " "));
        output.write(String.valueOf(mem[i].getETime() + " "));
        output.write(String.valueOf(mem[i].getESize() + " " + "\n"));
      }
      output.write(String.valueOf(c));
      output.close();
    }
    catch(IOException e){
      System.out.println("ERROR" + e);
    }
  }
}
