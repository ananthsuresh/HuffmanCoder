import java.io.*;
import java.util.*;

public class HuffmanCompressor{
  
  //main method that prints out the appropriate info a well as outputs the code to a new file//
  public static void main(String[] args) throws IOException {
    try{
    HuffmanNode.traversal(HuffmanNode.makeTree(HuffmanNode.createList(HuffmanNode.countFreq(args[0] + ".txt"))));
    HuffmanCompressor.huffmanCoder(args[0] + ".txt",args[1] + ".txt");
    }
    //catches exception and notifies user if input file is not found//
    catch(FileNotFoundException e){
      System.out.println("File Not Found");
    }
    
  }
  
  //method that reads input file, generates the huffman code and outputs into the appropriate file//
  public static String huffmanCoder(String inputFileName, String outputFileName) throws IOException{
    try{
      //variable that stores the original size before encoding//
      int originalSize = 0;
      //variable that stores the size after encoding//
      int newSize = 0;
      File inputFile = new File(inputFileName);
      File outputFile = new File(outputFileName);
      //creates an output file of appropriate name if file does not already exist//
      if(!outputFile.exists()) {
        outputFile.createNewFile();
      }
      FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      BufferedReader readChar = new BufferedReader(new FileReader(inputFile));
      //stores int value of char being read//
      int c;
      //writes encoding to output file//
      while((c = readChar.read())!= -1){
        if(c < 256) {
          char ch = (char) c;
          //calculates old size//
          originalSize += 8;
          bw.write(HuffmanNode.getEncoding()[(int)ch]);
        }
      }
      bw.close();
      BufferedReader nr = new BufferedReader(new FileReader(outputFile));
      int d;
      //calculates size of new file//
      while((d = nr.read())!= -1){
        newSize += 1;
      }
      //prints out savings buy subtracting new size from original size//
      System.out.println("Savings: " + (originalSize - newSize) + " bits");
      return "OK";
    } 
    //catches exception and returns appropriate value//
    catch(FileNotFoundException e) {
      return "File Not Found";
    }
  }
  
  
  
  
  
}



