import java.util.*;
import java.io.*;

public class Analysis{
  public static void main(String[] args){
  
    ArrayList<String> zuluVerbs = readFile("zuluRoots.txt");
    ArrayList<String> xhosaVerbs = readFile("xhosaRoots.txt");



  }

  public static ArrayList<String> readFile(String filename){
    Scanner scan = new Scanner(System.in);
    File inputFile = new File(filename);
    ArrayList<String> verbArray = new ArrayList<String>();
    try{
      scan = new Scanner(inputFile);
      while(scan.hasNextLine()){
        verbArray.add(scan.nextLine());
      }
    }catch(IOException e){
      System.out.println("IO Error");
    }

    return verbArray;

  }

  public static void rootExtCombinations(){
    /*Takes root extensions and creates possible combinations of longer extensions*/

  }
}
