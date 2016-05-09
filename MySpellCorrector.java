package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;
/**
 * Created by devonkinghorn on 5/4/16.
 */
public class MySpellCorrector implements ISpellCorrector {

  Trie dictionary;
  private static final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
//  public static class NoSimilarWordFoundException extends Exception{
//
//  }
  //todo
  public void useDictionary(InputStreamReader dictionaryFile) throws IOException {
    BufferedReader br = new BufferedReader(dictionaryFile);
    dictionary = new Trie();
    String line;
    while((line = br.readLine()) != null){
      String[] words = line.split(" ");
      for(String s : words){
        dictionary.add(s);
      }
    }
    System.out.println(dictionary.newToString());
  }

  private void deletions(String inputWord, Set added, Set notAdded){
    StringBuilder str;
    for(int i = 0; i < inputWord.length(); i++){
      str = new StringBuilder(inputWord);
      String newWord = str.deleteCharAt(i).toString();
      if(dictionary.find(newWord) != null && dictionary.find(newWord).wordCount > 0){
        added.add(newWord);
      }else{
        notAdded.add(newWord);
      }
    }
  }
  private void transposition(String inputWord, Set added, Set notAdded){
    StringBuilder str;
    char myChar;
    for(int i = 0; i < inputWord.length()-1; i++){
      str = new StringBuilder(inputWord);
      myChar = str.charAt(i);
      str.deleteCharAt(i);
      str.insert(i+1,myChar);
      String newWord = str.toString();
      if(dictionary.find(newWord) != null && dictionary.find(newWord).wordCount > 0){
        added.add(newWord);
      }else{
        notAdded.add(newWord);
      }
    }
  }
  private void alteration(String inputWord, Set added, Set notAdded){
    StringBuilder str;
    for(int i = 0; i < inputWord.length(); i++){
      for(char myChar: alphabet) {
        str = new StringBuilder(inputWord);
        str.deleteCharAt(i);
        str.insert(i, myChar);
        String newWord = str.toString();
        if (dictionary.find(newWord) != null && dictionary.find(newWord).wordCount > 0) {
          added.add(newWord);
        } else {
          notAdded.add(newWord);
        }
      }
    }
  }
  private void insertion(String inputWord, Set added, Set notAdded){
    StringBuilder str;
    for(int i = 0; i < inputWord.length()+1; i++){
      for(char myChar: alphabet) {
        str = new StringBuilder(inputWord);
        str.insert(i, myChar);
        String newWord = str.toString();
        if (dictionary.find(newWord) != null && dictionary.find(newWord).wordCount > 0) {
          added.add(newWord);
        } else {
          notAdded.add(newWord);
        }
      }

    }
  }
  //returns most common. if two are most common returns first
  Set<String> added;
  Set<String> notAdded;
  public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
    if(dictionary.find(inputWord)!= null)
      return inputWord;
    added = new TreeSet();
    notAdded = new TreeSet();
    deletions(inputWord, added, notAdded);
    transposition(inputWord, added, notAdded);
    alteration(inputWord, added, notAdded);
    insertion(inputWord, added, notAdded);
    if(added.size() == 0){
      Set trash = new TreeSet();
      for(String str : notAdded){
        deletions(str,added,trash);
        transposition(str, added, trash);
        alteration(str, added, trash);
        insertion(str, added, trash);
      }
      if(added.size() == 0)
        throw new NoSimilarWordFoundException();
    }
    return dictionary.findMostCommon(added);
  }

}
