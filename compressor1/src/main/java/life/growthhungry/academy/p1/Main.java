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

            ByteArrayOutputStream codedText = new ByteArrayOutputStream();

            // using Short as it's enough for our program
            Map<String, Short> wordToCode= new HashMap<>();
            Map<Short, String> codeToWord= new HashMap<>();
            short codeCounter = 0; // or Short.MIN_VALUE
            String line = reader.readLine();
            while (line != null) {
              String[] words = line.split("(?<=\\s)|(?=\\s)");
              for (String w: words) {
                  // using Short object to use null
                 Short existingCode = wordToCode.get(w);
                 if (existingCode == null) {
                     wordToCode.put(w, codeCounter);
                     codeToWord.put(codeCounter, w);


                     // explanation: short has 2 bites : 01011001(high) 01111100(low)
                     // divide short into 2 bytes; we shift short to 8 bites
                     // the low bite is now lost, and high is now low
                     byte high = (byte) (codeCounter >>> 8); // this operation does >> 00000000 01011001 => 01011001
                     // everything bigger than 1 bite is cut off
                     byte low = (byte) codeCounter; // this operation does >> 01111100
                     codedText.write(high);
                     codedText.write(low);


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

            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(outputFile));
            CompressionInfoHolder holder = new CompressionInfoHolder(codeToWord, codedText.toByteArray());

            System.out.println("Number of words " + codeCounter);


        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find your file: " + inputFile);
        } catch (IOException e) {
            System.err.println("Sorry, error occurred during reading: " + inputFile + "  error: " + e.getMessage());
        }
    }
}
