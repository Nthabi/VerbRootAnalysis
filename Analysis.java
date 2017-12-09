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
     rootEndings(zuluVerbs, "rootEndings.txt");
     rootsWithoutExtensions(zuluVerbs, "zuluNoExt.txt");
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

  public static void rootEndings(ArrayList<String> verbRoots,String filename){
    //read in extensions.txt, check endings, keep count and write to file
    ArrayList<String> verbExtensions = readFile("extensions.txt");
    ArrayList<String> matchingVerbRoots = new ArrayList<String>(); //array to keep roots that match with extensions
    ArrayList<String> noExtensions = new ArrayList<String>(); //keeps roots without the extensions

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
            //String noExt = verbRoot.substring(0,verbRoot.length()-1);
            //noExtensions.add(noExt);
          }
        }
        //String finalCount = "Total roots ending in "+extension + ": " + wordCount+ "\n";
        //searchFile(filename,finalCount);
        writeToFile(filename,"\n");
      }else{
        for(String verbRoot: verbRoots){
          int rootLength = verbRoot.length();
          String verbEnding = verbRoot.substring(rootLength -2);
          if(extension.length() == verbEnding.length()){
            if(verbEnding.equals(extension)){
              wordCount++;
              writeToFile(filename,verbRoot);
              //String noExt = verbRoot.substring(0,verbRoot.length()-2);
              //noExtensions.add(noExt);
            }
          }
        }
        writeToFile(filename,"\n");
        //String finalCount = "Total roots ending in "+extension + ": " + wordCount + "\n";
        //searchFile(filename,finalCount);
      }
    }
    writeToFile("noExtRoots.txt",noExtensions);
  }


  public static void rootsWithoutExtensions(ArrayList<String> vroots, String outputFile){
    // Extract the roots without the extensions and write to file and keep count
    ArrayList<String> extensions = new ArrayList<String>();
    ArrayList<String> temp1 = readFile("extensions.txt");
    ArrayList<String> temp2 = readFile("extensionsPermutations.txt");
    extensions.addAll(temp1);
    extensions.addAll(temp2);
    int count = 0;
    for(String ext: extensions){
      int extLen = ext.length();
      for(String root : vroots){
        String ending = root.substring(root.length()-extLen);
        if(ending.equals(ext)){
          String newRoot = root.substring(0,root.length()-extLen);
          writeToFile(outputFile, newRoot);
          count++;
        }
      }
    }
  }

  public static void PercentageCalc(){
    /*nr of roots without extensions (so if with extensions then count only
bon but not the bonis bonel etc) / nr of roots that have >=1 extensions
as well] * 100.*/


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
