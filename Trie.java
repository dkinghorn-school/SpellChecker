package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.util.Set;
import java.util.*;
/**
 * Created by devonkinghorn on 5/4/16.
 */
public class Trie implements ITrie {
  public Node rootNode;
  int totalWords;
  int totalNodes;
  Set words;
  public Trie(){
    rootNode = new Node("");
    totalWords = 0;
    totalNodes = 1;
  }

  public void add(String word){
    String testWord = word.toLowerCase();
    Node currentNode = rootNode;
    if(word == "")
      return;
    int position;
    for(int i = 0; i < testWord.length(); i++){
      position = testWord.charAt(i) - 'a';
      System.out.println(position);
      if(currentNode.children[position] == null){
        currentNode.children[position] = new Node(currentNode.word + testWord.charAt(i));
        totalNodes++;
      }
      currentNode = currentNode.children[position];
    }
    if(currentNode.getValue() == 0){
      totalWords++;
    }
    currentNode.wordCount++;
  }
  //todo
  public  Node find(String word){
    String testWord = word.toLowerCase();
    Node currentNode = rootNode;
    int position;
    for(int i = 0; i < testWord.length(); i++){
      position = testWord.charAt(i) - 'a';
      System.out.println(position);
      if(currentNode.children[position] == null){
        return null;
      }
      currentNode = currentNode.children[position];
    }
    return currentNode;
  }
  public int getWordCount(){
    return totalWords;
  }
  public int getNodeCount(){
    return totalNodes;
  }

  private void traverseDown(StringBuilder sb, Node node){
    if(node.wordCount > 0 ){
      sb.append(node.word).append("\n");//append(" ").append(node.word).append(" ").append(node.wordCount);
    }
    for(int i = 0; i < 26; i++){
      if(node.children[i] != null){
        traverseDown(sb, node.children[i]);
      }
    }
  }
  private void newTraverseDown(StringBuilder sb, Node node){
    if(node.wordCount > 0 ){
      sb.append(" ").append(node.word).append(" ").append(node.wordCount);
    }
    for(int i = 0; i < 26; i++){
      if(node.children[i] != null){
        newTraverseDown(sb, node.children[i]);
      }
    }
  }
  @Override
  public String toString(){
    StringBuilder toReturn = new StringBuilder();
    traverseDown(toReturn, rootNode);
    return toReturn.toString();
  }
  public String newToString(){
    StringBuilder toReturn = new StringBuilder();
    newTraverseDown(toReturn, rootNode);
    return toReturn.toString();
  }

  @Override
  public int hashCode(){
    return totalWords*100000 + totalNodes;
  }

  @Override
  public boolean equals(Object o){
    if(o == null)
      return false;
    if(o.getClass() != this.getClass())
      return false;
    Trie temp = (Trie)o;
    return (temp.newToString().equals(this.newToString()));
  }

  public String findMostCommon(Set<String> input){
    Node mostCommon = rootNode;//input.iterator().next();
//    Iterator<Node> iter = input.iterator();
    Node temp;
//    while(iter.hasNext()){
//      temp = find(iter.next().toString());
//    }
    for(String str: input){
      temp = find(str);
      if(temp.wordCount > mostCommon.wordCount){
        mostCommon = temp;
      }
    }
//    for(Node node: input){
////      if(node.wordCount>mostCommon.wordCount){
//        mostCommon = node;
////      }
//    }
    return mostCommon.word;
  }
  public class Node implements ITrie.INode{
    public Node[] children;
    int wordCount;
    public String word;
    Node(String word){
      children = new Node[26];
      this.word = word;
      wordCount = 0;
    }
    public int getValue(){
      return wordCount;
    }
  }


}
