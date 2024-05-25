package life.growthhungry.academy.p1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputFile = "data/shakespeare.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String line = reader.readLine();
            while (line != null) {

              String[] words = line.split("(?<=\\s)|(?=\\s)");
              for (String w: words) {
                  System.out.print(w);
              }
              System.out.println();
              line = reader.readLine();
            }


        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find your file: " + inputFile);
        } catch (IOException e) {
            System.err.println("Sorry, error occurred during reading: " + inputFile + "  error: " + e.getMessage());
        }
    }
}
