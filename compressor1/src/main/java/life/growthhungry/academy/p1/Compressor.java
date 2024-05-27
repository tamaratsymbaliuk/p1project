package life.growthhungry.academy.p1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Compressor {
    public static void main(String[] args) throws CompressionException {
        String inputFile = "data/shakespeare.txt";
        String compressedFile = inputFile + ".sc";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // Reads the input file.

            ByteArrayOutputStream codedText = new ByteArrayOutputStream(); // Stores the compressed data in memory.

            // using Short as it's enough for our program
            Map<String, Short> wordToCode= new HashMap<>(); // Maps words to unique short codes.
            Map<Short, String> codeToWord= new HashMap<>(); // Maps short codes back to words.
            short newLineCode = 0;
            wordToCode.put(System.lineSeparator(), newLineCode); // Maps newline to 0.
            codeToWord.put(newLineCode, System.lineSeparator()); // Maps 0 to newline.

            short codeCounter = (short) (newLineCode + 1); // or Short.MIN_VALUE
            int totalNumberOfWords = 0;
            String line = reader.readLine();
            while (line != null) {

              String[] words = line.split("(?<=\\s)|(?=\\s)");
              totalNumberOfWords += words.length;
              for (String w: words) {
                  // using Short object to use null
                 Short existingCode = wordToCode.get(w);  // Checks if the word already has a code.
                 if (existingCode == null) { // If not, assigns a new code.
                     wordToCode.put(w, codeCounter);
                     codeToWord.put(codeCounter, w);
                     existingCode = codeCounter;


                     codeCounter++;
                     if (codeCounter == Short.MAX_VALUE) {
                         throw new CompressionException("Too many words in this file");
                     }
                 }

                  // explanation: short has 2 bites : 01011001(high) 01111100(low)
                  // divide short into 2 bytes; we shift short to 8 bites
                  // the low bite is now lost, and high is now low
                  byte high = (byte) (existingCode >>> 8); // this operation does >> 00000000 01011001 => 01011001
                  // everything bigger than 1 bite is cut off
                  byte low = existingCode.byteValue(); // this operation does >> 01111100
                  codedText.write(high); // Writes the high byte to the output.
                  codedText.write(low); // Writes the low byte to the output.

                  // System.out.print(w);
              }
              // System.out.println();
              codedText.write(0);// Writes a delimiter (two zero bytes) between lines.
              codedText.write(0);
              totalNumberOfWords++; // Increments the word count for the delimiter


              line = reader.readLine(); // Reads the next line.
            }

            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(compressedFile)); // Writes the compressed data to a file
            CompressionInfoHolder holder = new CompressionInfoHolder(codeToWord, codedText.toByteArray()); // Creates a holder for the data
            writer.writeObject(holder); // Serializes the holder to the file
            writer.flush(); // write to the file and not wait, Ensures all data is written.
            writer.close();
            System.out.println("Number of unique words " + codeCounter);
            System.out.println("Number of total words " + totalNumberOfWords);


        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find your file: " + inputFile);
        } catch (IOException e) {
            System.err.println("Sorry, error occurred during reading: " + inputFile + "  error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
