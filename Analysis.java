import java.util.*;
import java.io.*;

public class Analysis{
  public static void main(String[] args){

    //read in verb roots
    ArrayList<String> zuluVerbs = readFile("zuluRoots.txt");
    ArrayList<String> xhosaVerbs = readFile("xhosaRoots.txt");

    //get combiantions of extensions
    rootExtCombinations();

    //getVerbEndings matches
    rootEndings(zuluVerbs);
    rootEndings(xhosaVerbs);


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

  public static void writeToFile(String outputFile, ArrayList<String> values){
    try{
      FileWriter fstream = new FileWriter(outputFile,true);
      BufferedWriter out = new BufferedWriter(fstream);
      for(String element: values){
        out.write(element + '\n');
      }
      out.close();
    }catch(IOException e){
      System.err.println(e.getMessage());
    }


  }


  public static void rootExtCombinations(){
    /*Takes root extensions and creates possible combinations of longer extensions*/
    Permute p = new Permute();

    ArrayList<String> extensionsInput = readFile("extensions.txt");
    String[] extensionsArray = extensionsInput.toArray(new String[extensionsInput.size()]);
    ArrayList<ArrayList<String>> extPermutations = new ArrayList<ArrayList<String>>();
    extPermutations = p.permute(extensionsArray);

    //write extensions to file
    try{
      String outputFile = "extPermutations.txt";
      FileWriter fstream = new FileWriter(outputFile,true);
  	  BufferedWriter out = new BufferedWriter(fstream);

      for(int i=0; i<extPermutations.size(); i++){
        String permutation = "";
        for(int j=0; j<extPermutations.get(i).size(); j++){
          permutation += extPermutations.get(i).get(j);
        }
        out.write(permutation + "\n");
      }
      out.close();
    }catch(IOException e){
      System.err.println("Error while writing to file: " +
             e.getMessage());
    }

  }

  public static void rootEndings(ArrayList<String> verbRoots){
    //read in extensions.txt, check endings, keep count and write to file
    ArrayList<String> verbExtensions = readFile("extensions.txt");
    ArrayList<String> matchingVerbRoots = new ArrayList<String>(); //array to keep matching roots
    int wordCount = 0;

    for(String verbRoot: verbRoots){
      if(verbRoot.length() > 2){
        for(int i=0; i<verbExtensions.size(); i++){
          String extension = verbExtensions.get(i);
          String verbEnding = verbRoot.substring(verbRoot.length()-2);
          if(extension.length() == 2){
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              System.out.println(extension + " " + verbRoot);
            }
          }
          //
          //String verbEnding = verbRoot.substring(verbRoot.length()-2);
          if(extension.length() == 1){
            verbEnding = verbRoot.substring(verbRoot.length()-1);
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              System.out.println(extension + " " + verbRoot);
            }
          }
          else{
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              System.out.println(extension + " " + verbRoot);
            }
          }
        }

      }
    }
  }

  
}
