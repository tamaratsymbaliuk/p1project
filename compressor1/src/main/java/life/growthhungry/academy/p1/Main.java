package life.growthhungry.academy.p1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws CompressionException {
        String inputFile = "data/shakespeare.txt";
        String outputFile = inputFile + ".sc";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(outputFile));

            // using Short as it's enough for our program
            Map<String, Short> wordToCode= new HashMap<>();
            Map<Short, String> codeToWord= new HashMap<>();
            short codeCounter = 0;
            String line = reader.readLine();
            while (line != null) {
              String[] words = line.split("(?<=\\s)|(?=\\s)");
              for (String w: words) {
                  // using Short object to use null
                 Short existingCode = wordToCode.get(w);
                 if (existingCode == null) {
                     wordToCode.put(w, codeCounter);
                     codeToWord.put(codeCounter, w);
                     codeCounter++;
                     if (codeCounter == Short.MAX_VALUE) {
                         throw new CompressionException("Too many words in this file");
                     }
                 }
                  System.out.print(w);
              }
              System.out.println();
              line = reader.readLine();
            }
            System.out.println("Number of words " + codeCounter);


        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find your file: " + inputFile);
        } catch (IOException e) {
            System.err.println("Sorry, error occurred during reading: " + inputFile + "  error: " + e.getMessage());
        }
    }
}
