package life.growthhungry.academy.p1;

import java.io.*;

public class Decompressor {
    public static void main(String[] args) throws IOException, ClassNotFoundException, CompressionException {
        String inputFile = "data/shakespeare.txt";
        String compressedFile = "data/shakespeare.txt.sc";
        String decompressedFile = compressedFile + ".txt";

        // now read the file that we just wrote
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(compressedFile)); // Reads the compressed file
        Object inputObject = inputStream.readObject(); // Reads the object from the file
        if (!(inputObject instanceof CompressionInfoHolder inputHolder)) { // Checks if the object is of the expected type
            throw new CompressionException("Unexpected type receieved. Program expects " + CompressionInfoHolder.class.getCanonicalName());
        }

        // write text into file
        BufferedWriter decompressingWriter = new BufferedWriter(new FileWriter(decompressedFile)); // Writes the decompressed data to a file
        // decode it
        byte[] codedBytes = inputHolder.getCodedText();// Gets the compressed data
        for (int i = 0; i < codedBytes.length; i += 2) { // every iteration we read 2 bytes at a time
            short high = codedBytes[i]; // Gets the high byte
            // short low = codedBytes[i + 1]; this gives a short with -128 instead of 128
            short low = (short)(codedBytes[i + 1]& 0xFF);// Gets the low byte
            short code = (short) ((high << 8) + low);// Combines the bytes into a short code

            String word = inputHolder.getCodeToWord().get(code);// Looks up the word for the code
            // writing words into file word by word
            if (word != null) decompressingWriter.write(word);
        }
        decompressingWriter.flush();// Ensures all data is written
        decompressingWriter.close();// Closes the file

        System.out.println("Compressed file had: " + (codedBytes.length / 2) + " words.");
        System.out.println("Compressed file has: " + inputHolder.getCodeToWord().size() + " unique words.");

        // Compare the original input and the decompressed output
        BytesComparator comparator = new BytesComparator();
        boolean areFilesIdentical = comparator.compareFiles(inputFile, decompressedFile);
        System.out.println("Are original and decompressed files identical? " + areFilesIdentical);

    }
}
