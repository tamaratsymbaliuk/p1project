package life.growthhungry.academy.p1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BytesComparator {
    public boolean compareFiles(String input, String readable) throws IOException {
        try (InputStream is1 = new FileInputStream(input);
             InputStream is2 = new FileInputStream(readable)) {
            int data1, data2;
            int position = 0;
            boolean areIdentical = true;

            while ((data1 = is1.read()) != -1 && (data2 = is2.read()) != -1) { // reads one byte at a time from each file, and returns -1 if the end is reached
                if (data1 != data2) {
                    System.out.printf("Difference at byte %d: %02x (test.txt) != %02x (readable.txt)%n", position, data1, data2);
                    areIdentical = false;
                }
                position++;
            }

            // Check if both files have reached the end
            if (is1.read() != -1 || is2.read() != -1) {
                System.out.println("Files have different lengths.");
                areIdentical = false;
            }

            return areIdentical;
        }
    }
}

