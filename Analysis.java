/* Author: Nthabiseng Mashiane
  Date: 5 December 2017
  Code to do verb root analysis on zulu and xhosa words*/
import java.util.*;
import java.io.*;

public class Analysis{

  int zuluCount;
  int xhosaCount;
  public static void main(String[] args){

    //read in verb roots
    ArrayList<String> zuluVerbs = readFile("zuluRoots.txt");
    ArrayList<String> xhosaVerbs = readFile("xhosaRoots.txt");

    //get combiantions of extensions
    rootExtCombinations();

    //getVerbEndings matches
     HashMap<String,Integer> zuluStats = rootEndings(zuluVerbs, "zExtensionsSort.txt");
     HashMap<String,Integer> xhosaStats = rootEndings(xhosaVerbs, "xExtensionsSort.txt");

     int zuluCount = rootsWithoutExtensions(zuluVerbs, "zuluNoExt.txt"); //number of roots with no extension
     int xhosaCount = rootsWithoutExtensions(xhosaVerbs, "xhosaNoExt.txt");
     System.out.println(xhosaCount + " z = " + zuluCount);
     int xhosaExtCount = statsRecording(xhosaStats,"xhosaStats.txt");
     statsCalc(xhosaCount,xhosaExtCount, "xhosaStats.txt");
     int zuluExtCount = statsRecording(zuluStats,"zuluStats.txt");
     statsCalc(zuluCount,zuluExtCount,"zuluStats.txt");



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
        boolean wordExists = searchFile(outputFile,element);
        if(!wordExists){
          out.write(element + '\n');
        }
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
      boolean wordExists = searchFile(outputFile,word);
      if(!wordExists){
        out.write(word + '\n');
      }
      out.close();
    }catch(IOException e){
      System.err.println("Error while writing word to file " +e.getMessage());
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
          writeToFile(outputFile,permutation);
        }
      }
      out.close();
    }catch(IOException e){
      System.err.println("Error while writing to file: " +
             e.getMessage());
    }
  }

  public static HashMap<String,Integer> rootEndings(ArrayList<String> verbRoots,String filename){
    //read in extensions.txt, check endings, keep count and write to file
    ArrayList<String> verbExtensions = readFile("extensions.txt");
    ArrayList<String> verbExtPermutations = readFile("extPermutations.txt");
    verbExtensions.addAll(verbExtPermutations);
    HashMap<String,Integer> stats = new HashMap<String,Integer>();

    ArrayList<String> matchingVerbRoots = new ArrayList<String>(); //array to keep roots that match with extensions
    //ArrayList<String> noExtensions = new ArrayList<String>(); //keeps roots without the extensions

    for(String extension: verbExtensions){
      writeToFile(filename,extension);
      int wordCount = 0;
      int extLen = extension.length();
      if(extLen == 1){
        for(String verbRoot: verbRoots){
          String verbEnding = ""+ verbRoot.substring(verbRoot.length()-1);
          if(verbEnding.equals(extension)){
            wordCount++;
            writeToFile(filename,verbRoot);
          }
        }
        stats.put(extension,wordCount);
        //writeToFile(filename,"\n");
      }else{
        for(String verbRoot: verbRoots){
          int rootLength = verbRoot.length();
          String verbEnding = verbRoot.substring(rootLength -2);
          if(extension.length() == verbEnding.length()){
            if(verbEnding.equals(extension)){
              wordCount++;
              writeToFile(filename,verbRoot);
            }
          }
        }
        //writeToFile(filename,"\n");
        stats.put(extension,wordCount);
      }
    }
    //writeToFile("noExtRoots.txt",noExtensions);
    return stats;

  }


  public static int rootsWithoutExtensions(ArrayList<String> vroots, String outputFile){
    // Extract the roots without the extensions and write to file and keep count
    ArrayList<String> extensions = new ArrayList<String>();
    ArrayList<String> temp1 = readFile("extensions.txt");
    ArrayList<String> temp2 = readFile("extensionsPermutations.txt");
    extensions.addAll(temp1);
    extensions.addAll(temp2);
    ArrayList<String> noExtensions = new ArrayList<String>();

    for(String ext: extensions){
      int extLen = ext.length();
      for(String root : vroots){
        String ending = root.substring(root.length()-extLen);
        if(ending.equals(ext)){
          String newRoot = root.substring(0,root.length()-extLen);
          writeToFile(outputFile, newRoot);
          noExtensions.add(newRoot);
        }
      }
    }
    return noExtensions.size();
  }

  public static int statsRecording(HashMap<String,Integer> stats, String filename){
    /* Records the amount of verbroots with a specific extension*/
    Iterator it = stats.entrySet().iterator();
    int totalWords = 0;
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        String value = pair.getKey() + " = " + pair.getValue();
        writeToFile(filename,value);
        totalWords += Integer.parseInt("" + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }
    String netValue = "Total verbs with extensions = " + totalWords;
    writeToFile(filename, netValue);

    //String percent = "Percentage of extended roots which are already in the dictionary " + statCalc()
    //writeToFile(filename, percent);
    return totalWords;

  }
  public static void statsCalc(int noExt, int ext, String filename){
      String percent = "Percentage of extended roots which are already in the dictionary " + (noExt/ext)*100;
      writeToFile(filename, percent);

  }

  public static boolean searchFile(String outFile, String inputWord){
    //check if element exists in file
    ArrayList<String> words = readFile(outFile);
    if(words.contains(inputWord)){
      return true;
    }else{
      return false;
    }
  }

}
