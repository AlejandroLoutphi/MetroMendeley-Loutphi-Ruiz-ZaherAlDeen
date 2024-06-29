/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import datastructures.LinkedList;
import datastructures.LinkedListNode;

public class Functions {

    public static void main(String[] args) {
        // Define the filename
        String fileName = "Investigations.txt";

        // Check if the file exists in the current directory
        File file = new File(fileName);

        // List to store Investigation objects
        List<Investigation> investigations = new ArrayList<>();

        if (file.exists()) {
            // File exists, attempt to open and read it
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder authorsBuilder = new StringBuilder();
                StringBuilder abstractBuilder = new StringBuilder();
                List<String> keywordsList = new ArrayList<>();
                String title = null;
                boolean readingAbstract = false;

                while ((line = reader.readLine()) != null) {
                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Check for markers and collect data accordingly
                    if (line.startsWith("Autores")) {
                        // If title has been set, create previous investigation
                        if (title != null) {
                            // Convert keywordsList to String[]
                            String[] keywordsArray = keywordsList.toArray(new String[0]);

                            // Create Investigation object and add to list
                            Investigation investigation = new Investigation(title.trim(),
                                    parseAuthors(authorsBuilder.toString().trim()),
                                    abstractBuilder.toString().trim(),
                                    keywordsArray);
                            investigations.add(investigation);

                            // Reset StringBuilder and List for next investigation
                            authorsBuilder.setLength(0);
                            abstractBuilder.setLength(0);
                            keywordsList.clear();
                        }

                        // Set the title for the new investigation
                        title = null;
                        readingAbstract = false;
                    } else if (line.startsWith("Palabras Claves")) {
                        // Start collecting keywords
                        String keywordsLine = line.substring("Palabras Claves:".length()).trim();
                        String[] keywordsArray = keywordsLine.split(",");
                        for (String keyword : keywordsArray) {
                            keywordsList.add(keyword.trim());
                        }
                    } else if (line.startsWith("Resumen")) {
                        // Start collecting abstract
                        abstractBuilder.append(line.substring("Resumen".length())).append("\n");
                        readingAbstract = true;
                    } else {
                        // If title is not set, treat line as title
                        if (title == null) {
                            title = line.trim();
                        } else if (readingAbstract) {
                            // Continue collecting abstract
                            abstractBuilder.append(line).append("\n");
                        } else {
                            // Append to authorsBuilder until a new section begins
                            authorsBuilder.append(line.trim()).append("\n");
                        }
                    }
                }

                // Add the last investigation after exiting the loop
                if (title != null) {
                    // Convert keywordsList to String[]
                    String[] keywordsArray = keywordsList.toArray(new String[0]);

                    Investigation investigation = new Investigation(title.trim(),
                            parseAuthors(authorsBuilder.toString().trim()),
                            abstractBuilder.toString().trim(),
                            keywordsArray);
                    investigations.add(investigation);
                }

                // Print all investigations for testing
                printInvestigations(investigations);

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            // File does not exist, print an error message
            System.err.println("Error: " + fileName + " not found in the current directory.");
        }
    }

    // Helper method to parse authors and return as String array
    private static String[] parseAuthors(String authorsString) {
        return authorsString.split("\n");
    }

    // Helper method to print investigations (for testing)
    private static void printInvestigations(List<Investigation> investigations) {
        for (Investigation investigation : investigations) {
            System.out.println("Title: " + investigation.getTitle());
            System.out.println("Authors:");
            for (String author : investigation.getAuthors()) {
                System.out.println(author);
            }
            System.out.println("Abstract: " + investigation.getText());
            System.out.print("Keywords: ");
            for (int i = 0; i < investigation.getKeywords().length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(investigation.getKeywords()[i]);
            }
            System.out.println("\n--------------------");
        }
    }

    /**
     * Returns a String with an analysis of the passed-in Investigation using the following format:
     * <pre>
     * Investigation Title
     * Autores: (authors)
     * palabra clave 1: (frequency of keyword 1)
     * palabra clave 2: (frequency of keyword 2)
     * ...
     * </pre>
     *
     * @param inv Investigation to analyze
     * @param keywords list of all keywords to present
     * @return String with the analysis
     */
    public static String analysis(Investigation inv, LinkedList<String> keywords) {
        StringBuilder o = new StringBuilder();
        o.append(inv.title);
        o.append("\nAutores: ");
        for (int i = 0; i < inv.authors.length-1; i++) {
            o.append(inv.authors[i]);
            o.append(", ");
        }
        o.append(inv.authors[inv.authors.length-1]);

        int counter = 1;
        for (LinkedListNode<String> i = keywords.getHead();
                i != null;
                i = i.getNext()) {
            o.append("\npalabra clave ");
            o.append(counter);
            o.append(": ");
            o.append(inv.frequency(i.getElt()));
            counter++;
        }
        return o.toString();
    }
}
