import java.util.*;
import java.io.*;

public class Analysis{
  int withExtensionCount = 0;
  int withoutExtensionCount = 0;
  public static void main(String[] args){

    //read in verb roots
    ArrayList<String> zuluVerbs = readFile("zuluRoots.txt");
    ArrayList<String> xhosaVerbs = readFile("xhosaRoots.txt");

    //get combiantions of extensions
    rootExtCombinations();

    //getVerbEndings matches
    //  rootEndings(zuluVerbs);
    //rootEndings(xhosaVerbs);


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

  public static void writeToFile(String outputFile, String word){
    try{
      FileWriter fstream = new FileWriter(outputFile,true);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write(word + '\n');
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
        searchFile(outputFile,permutation);
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
    HashMap<String,ArrayList<String>> organizedMatchingRoots = new HashMap<String,ArrayList<String>>();
    int wordCount = 0;
    ArrayList<String> rootsOnly = new ArrayList<String>(); //stores the roots without the extensions

    for(String verbRoot: verbRoots){
      if(verbRoot.length() > 2){
        for(int i=0; i<verbExtensions.size(); i++){
          String extension = verbExtensions.get(i);
          String verbEnding = verbRoot.substring(verbRoot.length()-2);
          if(extension.length() == 2){
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              //System.out.println(extension + " " + verbRoot);
              String noExtension = verbRoot.substring(0,verbRoot.length()-2);
              rootsOnly.add(noExtension.substring(0,verbRoot.length()-2));

            }
          }
          //
          //String verbEnding = verbRoot.substring(verbRoot.length()-2);
          if(extension.length() == 1){
            verbEnding = verbRoot.substring(verbRoot.length()-1);
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              //System.out.println(extension + " " + verbRoot);
              rootsOnly.add(verbEnding.substring(0,verbRoot.length()-1));
            }
          }
          else{
            if(verbEnding.equals(extension)){
              wordCount++;
              matchingVerbRoots.add(verbRoot);
              //System.out.println(extension + " " + verbRoot);
              //rootsOnly.add(verbEnding);
            }
          }
        }


      }
    }

  }

  public static void checkDuplicates(String filename){
    /*check if string exists in file before */
  }

  public static void rootsWithoutExtensions(ArrayList<String> vroots){
    // Extract the roots without the extensions and write to file and keep count
    ArrayList<String> extensions = new ArrayList<String>();
    ArrayList<String> temp1 = readFile("extensions.txt");
    ArrayList<String> temp2 = readFile("extensionsPermutations.txt");
    int count = 0;
    for(String root : vroots){

    }

  }

  public static void PercentageCalc(){
    /*nr of roots without extensions (so if with extensions then count only
bon but not the bonis bonel etc) / nr of roots that have >=1 extensions
as well] * 100.*/


  }

  public static void searchFile(String outFile, String inputWord){
    //check if element exists in file
    ArrayList<String> words = readFile(outFile);
    if(words.contains(inputWord)){
      //do nothing
    }else{
      //write to file
      writeToFile(outFile,inputWord);
    }

  }
}
