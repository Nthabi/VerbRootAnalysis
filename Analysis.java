import java.util.*;
import java.io.*;

public class Analysis{
  public static void main(String[] args){
    Scanner k = new Scanner(System.in);
    File zulu = new File("zuluRoots.txt");
    File xhosa = new File("xhosaRoots.txt");

    ArrayList<String> zuluVerbs = new ArrayList<String>();
    ArrayList<String> xhosaVerbs = new ArrayList<String>();
    try{
      k = new Scanner(zulu);
      while(k.hasNextLine()){
        zuluVerbs.add(k.nextLine());
      }
      k = new Scanner(xhosa);
      while(k.hasNextLine()){
        xhosaVerbs.add(k.nextLine());
      }
    }catch(IOException e){
      System.out.println("IO Error");
    }



  }
}
