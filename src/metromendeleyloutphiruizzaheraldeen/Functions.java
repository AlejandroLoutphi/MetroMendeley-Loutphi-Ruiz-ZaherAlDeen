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

                        // Set the title for the next investigation
                        // Title is the last non-empty line before "Autores"
                        title = getLastNonEmptyLineBeforeAutores(reader);
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
                        // If title has not been set yet, treat this line as the title
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
        
        // Print Authors
        System.out.println("Authors:");
        for (String author : investigation.getAuthors()) {
            System.out.println(author);
        }
        
        // Print Abstract
        System.out.println("Abstract: " + investigation.getText());
        
        // Print Keywords
        System.out.print("Keywords: ");
        String[] keywords = investigation.getKeywords();
        for (int i = 0; i < keywords.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(keywords[i]);
        }
        System.out.println("\n--------------------");
    }
}

    // Helper method to get the last non-empty line before "Autores"
    private static String getLastNonEmptyLineBeforeAutores(BufferedReader reader) throws IOException {
        String line;
        String title = null;

        // Read lines until "Autores" or end of file is encountered
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Autores")) {
                break; // Found the line before "Autores", stop reading
            }
            // Store the last non-empty line as potential title
            if (!line.trim().isEmpty()) {
                title = line.trim();
            }
        }

        return title;
    }
}
