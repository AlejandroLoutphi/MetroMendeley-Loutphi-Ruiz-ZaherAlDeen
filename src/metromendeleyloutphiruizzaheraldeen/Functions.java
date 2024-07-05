/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import datastructures.LinkedList;
import datastructures.LinkedListNode;
import datastructures.HashTable;
import datastructures.HashTableEntry;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author ayahzaheraldeen
 * @author Alejandro Loutphi
 */
public class Functions {

    private String newText;
    // TODO tests hash table to be the correct size
    private HashTable<Investigation> tableByTitle;
    private HashTable<LinkedList<Investigation>> tableByKeyword;
    private HashTable<LinkedList<Investigation>> tableByAuthor;

    /**
     * Reads a file named Investigations.txt and stores their contents in this object's HashTable's
     * 
     * @return true if the function succeeded. Otherwise, false
     */
    public boolean buildHashTables() {
        File file = new File("Investigations.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                LinkedList<String> authorList = new LinkedList<>();
                String[] authorArray = new String[0];
                int authorLen = 0;
                String[] keywordArray;
                int state = 0;
                Investigation investigation = new Investigation();

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
                            this.tableByAuthor.add(line, new LinkedList<>());
                            break;

                        case 2:
                            authorArray = new String[authorLen];
                            authorList.putInArray(authorArray);
                            investigation.setAuthors(authorArray);
                            investigation.setText(line);
                            state = 3;
                            break;

                        case 3:
                            String keywordsLine = line.substring("Palabras Claves: ".length());
                            if (keywordsLine.charAt(keywordsLine.length() - 1) == '.') {
                                keywordsLine = keywordsLine.substring(0, keywordsLine.length() - 1);
                            }
                            keywordArray = keywordsLine.split(", ");
                            investigation.setKeywords(keywordArray);
                            for (int i = 0; i < keywordArray.length; i++) {
                                this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                            }
                            state = 0;
                            this.tableByTitle.add(investigation.getTitle(), investigation);
                            for (int i = 0; i < authorArray.length; i++) {
                                this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                            }
                            for (int i = 0; i < this.tableByKeyword.length(); i++) {
                                for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword.get(i).getHead();
                                        j != null;
                                        j = (HashTableEntry<LinkedList<Investigation>>) j.getNext()) {
                                    if (investigation.contains(j.getKey()))
                                        j.getElt().addAtHead(investigation);
                                }
                            }
                            break;

                        default:
                            // TODO change
                            System.err.println("Error: Invalid State at parse");
                            return false;
                    }
                }

                return true;

            } catch (IOException e) {
                // TODO change
                System.err.println("Error reading file: " + e.getMessage());
                return false;
            }
        } else {
            // TODO change
            System.err.println("Error: file not found in the current directory.");
            return false;
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
    public String analysis(Investigation inv) {
        StringBuilder o = new StringBuilder();
        o.append(inv.getTitle());
        o.append("\nAutores: ");
        for (int i = 0; i < inv.getAuthors().length - 1; i++) {
            o.append(inv.getAuthors()[i]);
            o.append(", ");
        }
        o.append(inv.getAuthors()[inv.getAuthors().length - 1]);

        int counter = 1;
        for (int i = 0; i < this.tableByKeyword.length(); i++) {
            for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword.get(i).getHead();
                    j != null;
                    j = (HashTableEntry<LinkedList<Investigation>>) j.getNext()) {
                o.append("\npalabra clave ");
                o.append(counter);
                o.append(" (");
                o.append(j.getKey());
                o.append("): ");
                o.append(inv.frequency(j.getKey()));
                counter++;
            }
        }
        return o.toString();
    }

    public void uploadFile(String filePath) {
        File fileToUpload = new File(filePath);
        if (!fileToUpload.exists()) {
            // TODO replace
            System.err.println("File not found: " + filePath);
            return;
        }
        if (!filePath.toLowerCase().endsWith(".txt")) {
            // TODO replace
            System.err.println("Error: File format must be .txt");
            return;
        }
        addFileToHashTables(fileToUpload);
    }

    public boolean addFileToHashTables(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> authorList = new LinkedList<>();
            String[] authorArray = new String[0];
            int authorLen = 0;
            String[] keywordArray;
            int state = 0;
            Investigation investigation = new Investigation();

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
                        this.tableByAuthor.add(line, new LinkedList<>());
                        break;

                    case 2:
                        authorArray = new String[authorLen];
                        authorList.putInArray(authorArray);
                        investigation.setAuthors(authorArray);
                        investigation.setText(line);
                        state = 3;
                        break;

                    case 3:
                        String keywordsLine = line.substring("Palabras Claves: ".length());
                        if (keywordsLine.charAt(keywordsLine.length() - 1) == '.') {
                            keywordsLine = keywordsLine.substring(0, keywordsLine.length() - 1);
                        }
                        keywordArray = keywordsLine.split(", ");
                        investigation.setKeywords(keywordArray);
                        for (int i = 0; i < keywordArray.length; i++) {
                            this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                        }
                        state = 0;
                        this.tableByTitle.add(investigation.getTitle(), investigation);
                        for (int i = 0; i < authorArray.length; i++) {
                            this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                        }
                        for (int i = 0; i < this.tableByKeyword.length(); i++) {
                            for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword.get(i).getHead();
                                    j != null;
                                    j = (HashTableEntry<LinkedList<Investigation>>) j.getNext()) {
                                if (investigation.contains(j.getKey()))
                                    j.getElt().addAtHead(investigation);
                            }
                        }
                        return true;

                    default:
                        // TODO change
                        System.err.println("Error: Invalid State at parse");
                        return false;
                }
            }
            return false;
        } catch (IOException e) {
            // TODO change
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }
    
public boolean parseAndBuildHashTables(String formattedInfo) {
    try {
        String[] lines = formattedInfo.split("\n");  // Split formattedInfo into lines
        
        String title = "";
        LinkedList<String> authorsList = new LinkedList<>();
        String[] keywords = new String[0];
        StringBuilder abstractText = new StringBuilder();
        String[] authorArray = new String[0];
        int authorlen=0;
        int state = 0;  // Track the state of parsing
        
        for (String line : lines) {
            line = line.trim();
            
            switch (state) {
                case 0:
                    title = line;  // First line is the title
                    state = 1;
                    break;
                
                case 1:
                    if (line.startsWith("Autores")) {
                        state = 2;
                        continue;
                    }
                    // Collect authors until "Resumen" is encountered
                    authorsList.add(line);
                    authorlen++;
                    
                    break;
                
                case 2:
                    authorArray = new String[authorlen];
                        authorsList.putInArray(authorArray);
                    if (line.startsWith("Resumen")) {
                        state = 3;
                        continue;
                    }
                    // Handle continuation of authors list if needed
                    authorsList.add(line);
                    break;
                
                case 3:
                    if (line.startsWith("Palabras Claves")) {
                        String keywordsLine = line.substring("Palabras Claves: ".length());
                        keywords = keywordsLine.split(", ");
                        state = 0;  // Reset state after processing keywords
                    } else {
                        abstractText.append(line).append("\n");  // Append to abstract text
                    }
                    break;
                
                default:
                    // Handle invalid state
                    System.err.println("Error: Invalid State at parse");
                    return false;
            }
        }
        
        // Create an Investigation object
        Investigation investigation = new Investigation();
        investigation.setTitle(title);
        investigation.setAuthors(authorArray);
        investigation.setText(abstractText.toString().trim());
        investigation.setKeywords(keywords);
        
        // Populate hash tables
        this.tableByTitle.add(title, investigation);
        for (String author : authorsList) {
            this.tableByAuthor.lookUp(author).addAtHead(investigation);
        }
        for (String keyword : keywords) {
            this.tableByKeyword.lookUp(keyword).addAtHead(investigation);
        }
        
        return true;
        
    } catch (Exception e) {
        // Handle any exceptions
        System.err.println("Error parsing formattedInfo: " + e.getMessage());
        return false;
    }
}

    public static String readFileToString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n"); // Append each line to the StringBuilder
            }
        }
        
        return sb.toString();
    }

    public static void appendToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content); // Write the content to the file
            writer.newLine(); // Add a new line at the end (optional)
        }
    }
}
