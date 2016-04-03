import java.io.*;
import java.util.*;

public class HuffmanNode{ 
  
  //stores character in node if leaf node or null of internal node//
  private Character inChar;
  //number of times character is present in input file//
  private int frequency = 0;
  //reference of the right child of current node//
  private HuffmanNode right;
  //reference of the left child of current node//
  private HuffmanNode left;
  //Stringbuilder object that builds the code for character as it traverses through huffman tree//
  private static StringBuilder codeBuilder = new StringBuilder();
  //array of Strings that stores the huffman code for each character//
  private static String[] encoding = new String[256];
  
  //constructor that creates a huffman node//
  public HuffmanNode(Character inChar, int frequency, HuffmanNode right, HuffmanNode left){
    this.inChar = inChar;
    this.frequency = frequency;
    this.right = right;
    this.left = left;
  }
  
  
  public static String[] getEncoding(){
    return encoding;
  }
  
  public int getFreq(){
    return frequency;
  }
  
  public Character getInChar(){
    return inChar;
  }
  
  public HuffmanNode getRight(){
    return right;
  }
  
  public HuffmanNode getLeft(){
    return left;
  }
  
  //method that calculates the frequency of each character and stores it into an array which it returns//
  public static int[] countFreq(String inputFileName) throws IOException{
    //array that stores frequency values//
    int[] charFreqs = new int[256];
    File file = new File(inputFileName);
    BufferedReader readChar = new BufferedReader(new FileReader(file));
    //integer value of character being read//
    int c;
    //reads file till no more characters are present//
    while((c = readChar.read())!= -1){
      if(c < 256){
      charFreqs[(int)c]++;
      }
    }
    readChar.close();
    return charFreqs;
  }
  
  //creates and returns an unordered linked list of HuffmanNodes base on the frequency array parameter//
  public static LinkedList<HuffmanNode> createList(int[] charFreqs){
    //list which stores the nu=odes created//
    LinkedList<HuffmanNode> freqList = new LinkedList<HuffmanNode>();
    //traverses through input arraty, creating node for each character and adding them into the list//
    for(int i = 0; i < charFreqs.length; i++){
      if(charFreqs[i] != 0){
        freqList.add(new HuffmanNode((char)i, charFreqs[i], null, null));
      }
    }
    return freqList;
  }
  
  //returns the huffman node in the input linked list which has the lowest frequency//
  public static HuffmanNode lowestFreq(LinkedList<HuffmanNode> freqList){
    //initialises the variable storing the lowest node to the first node in the list//
    HuffmanNode lowestNode = freqList.peek();
    //traverses through the list, comparing nodes to the lowest node and replacing the lowest node as required//
    for(HuffmanNode h : freqList){
      if(h.getFreq() < lowestNode.getFreq()){
        lowestNode = h;
      }
    }
    return lowestNode;
  }
  
  //creates the huffman tree based on the input linked list and returns the root//
  public static HuffmanNode makeTree(LinkedList<HuffmanNode> freqList){
    //initialises variable storing the reference to the root of the Huffman Tree//
    HuffmanNode root = null;
    //traverses through the list till there are no nodes left//
    while(freqList.size() > 1){
      //stores the node with the lowest frequency//
      HuffmanNode lowest = HuffmanNode.lowestFreq(freqList);
      //removes it from list//
      freqList.remove(lowest);
      //stores the node with the second lowest frequency//
      HuffmanNode seclowest = HuffmanNode.lowestFreq(freqList);
      //creates a parent node that has the combined frequency of the lowest and second lowest//
      root = new HuffmanNode(null, lowest.getFreq() + seclowest.getFreq(), seclowest, lowest);
      //adds this new node to the list//
      freqList.add(root);
      //removes second lowest frequency node//
      freqList.remove(seclowest);
    }
    return root;
  }
  
  //traverses through the huffman tree to each node, generating the huffman code//
  public static void traversal(HuffmanNode root){
    //base case where current node is a leaf node//
    if(root.getLeft() == null && root.getRight() == null){
      //assigns the value of the code generated from the traversal to the appropriate index in the array that stores code for all characters//
      encoding[(int)root.getInChar()] = codeBuilder.toString();
      //prints out the character, frequency, and huffman code//
      System.out.println(root.getInChar() + "\t" + root.getFreq() + "\t" + codeBuilder.toString());
      
    }
    //case when there is a left child//
    if(root.getLeft() != null){
      codeBuilder.append('0');
      //recursive call to roots left sub tree//
      traversal(root.getLeft());
      //deletes the last character added to the code builder//
      codeBuilder.deleteCharAt(codeBuilder.length() -1);
    }
    
    if(root.getRight() != null){
      codeBuilder.append('1');
      traversal(root.getRight());
      codeBuilder.deleteCharAt(codeBuilder.length() -1);
    }
  }
  
  
}


