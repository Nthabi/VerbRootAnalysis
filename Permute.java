import java.util.*;

public class Permute{
  public static void main(String[] args){
    String[] in = {"is","el","an", "w"};
    ArrayList<ArrayList<String>> out  = permute(in);
    for(int i=0; i<out.size(); i++){
      String permutation = "";
      for(int j=0; j<out.get(i).size(); j++){
        permutation += out.get(i).get(j);
        //System.out.print(out.get(i).get(j) + "");
      }
      System.out.println(permutation);
      //System.out.println("\n");
    }
  }
  public static ArrayList<ArrayList<String>> permute(String[] extensions) {
    ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    permute(extensions, 0, result);
    return result;
  }

  static void permute(String[] extensions, int start, ArrayList<ArrayList<String>> result) {
    if (start >= extensions.length) {
      ArrayList<String> words = new ArrayList<String>(Arrays.asList(extensions));
      result.add(words);
    }

    for (int j = start; j <= extensions.length - 1; j++) {
      swap(extensions, start, j);
      permute(extensions, start + 1, result);
      swap(extensions, start, j);
    }
  }

  public static void swap(String[] a, int i, int j) {
    String temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}
