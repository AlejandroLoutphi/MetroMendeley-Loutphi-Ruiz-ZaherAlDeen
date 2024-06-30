/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import datastructures.LinkedList;
import datastructures.LinkedListNode;
import datastructures.HashTable;

public class Functions {

    /**
     * Reads a file named Investigations.txt and returns a HashTable of the
     * Investigation objects it describes.
     * 
     * @return a HashTable of the Investigation objects it describes
     */
    public static HashTable<Investigation> buildHashTable() {
        File file = new File("Investigations.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int counter = 0;
                LinkedList<String> authorList = new LinkedList<>();
                String[] authorArray;
                int authorLen = 0;
                int state = 0;
                Investigation investigation = new Investigation();
                // TODO tests hash table to be the correct size
                HashTable<Investigation> o = new HashTable<>(32);

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    // Skip empty lines
                    if (line.isEmpty()) {
                        continue;
                    }

                    switch (state) {
                        case 0:
                            investigation = new Investigation();
                            authorList = new LinkedList<>();
                            authorLen = 0;

                            investigation.setTitle(line);
                            state = 1;
                            break;

                        case 1:
                            if (line.startsWith("Autores")) {
                                continue;
                            }
                            if (line.startsWith("Resumen")) {
                                state = 2;
                                continue;
                            }
                            authorList.add(line);
                            authorLen++;
                            break;

                        case 2:
                            authorArray = new String[authorLen];
                            counter = 0;
                            for (LinkedListNode<String> i = authorList.getHead(); i != null; i = i.getNext()) {
                                if (counter >= authorLen)
                                    break;
                                authorArray[counter] = i.getElt();
                                counter++;
                            }
                            investigation.setAuthors(authorArray);
                            investigation.setText(line);
                            state = 3;
                            break;

                        case 3:
                            String keywordsLine = line.substring("Palabras Claves: ".length());
                            if (keywordsLine.charAt(keywordsLine.length() - 1) == '.') {
                                keywordsLine = keywordsLine.substring(0, keywordsLine.length() - 1);
                            }
                            investigation.setKeywords(keywordsLine.split(", "));

                            state = 0;
                            o.add(investigation.getTitle(), investigation);
                            break;

                        default:
                            // TODO change
                            System.err.println("Error: Invalid State at parse");
                            return null;
                    }
                }

                return o;

            } catch (IOException e) {
                // TODO change
                System.err.println("Error reading file: " + e.getMessage());
                return null;
            }
        } else {
            // TODO change
            System.err.println("Error: file not found in the current directory.");
            return null;
        }
    }

    /**
     * Returns a String with an analysis of the passed-in Investigation using the
     * following format:
     * 
     * <pre>
     * Investigation Title
     * Autores: (authors)
     * palabra clave 1: (frequency of keyword 1)
     * palabra clave 2: (frequency of keyword 2)
     * ...
     * </pre>
     *
     * @param inv      Investigation to analyze
     * @param keywords list of all keywords to present
     * @return String with the analysis
     */
    public static String analysis(Investigation inv, LinkedList<String> keywords) {
        StringBuilder o = new StringBuilder();
        o.append(inv.getTitle());
        o.append("\nAutores: ");
        for (int i = 0; i < inv.getAuthors().length - 1; i++) {
            o.append(inv.getAuthors()[i]);
            o.append(", ");
        }
        o.append(inv.getAuthors()[inv.getAuthors().length - 1]);

        int counter = 1;
        for (LinkedListNode<String> i = keywords.getHead(); i != null; i = i.getNext()) {
            o.append("\npalabra clave ");
            o.append(counter);
            o.append(": ");
            o.append(inv.frequency(i.getElt()));
            counter++;
        }
        return o.toString();
    }


      public static void uploadFile(String filePath) {
        File fileToUpload = new File(filePath);
        if (!fileToUpload.exists()) {
            System.err.println("File not found: " + filePath);
            return;
        }
        if (!filePath.toLowerCase().endsWith(".txt")) {
            System.err.println("Error: File format must be .txt");
            return;
        }
        // Perform further operations with the file as needed
        processFile(fileToUpload); 
        
        System.out.println("File uploaded successfully: " + filePath);
    // Example: Call a method to process the file
    }

    public static void processFile(File file) {
        // Implement file processing logic here
        try {
            // Example: Read lines from the file
            java.nio.file.Path path = file.toPath();
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
            for (String line : lines) {
                System.out.println(line);
                // Process each line of the file as needed
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
