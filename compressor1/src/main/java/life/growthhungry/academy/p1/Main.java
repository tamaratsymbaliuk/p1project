package life.growthhungry.academy.p1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        String inputFile = "data/shakespeare.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find your file: " + inputFile);
        }
    }
}
