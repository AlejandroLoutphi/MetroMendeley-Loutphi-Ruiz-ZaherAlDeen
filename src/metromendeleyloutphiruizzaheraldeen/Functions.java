/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import datastructures.LinkedList;
import datastructures.HashTable;
import datastructures.HashTableEntry;
import datastructures.LinkedListNode;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * Enum for holding the value of what the text parser functions are currently
 * reading.
 */
enum ReadingState {
    TITLE,
    AUTHORS,
    TEXT,
    KEYWORDS,
}

/**
 * App Class for holding the state and unique functions of the
 * {@link MetroMendeleyLoutphiRuizZaherAlDeen} project.
 *
 * @author ayahzaheraldeen
 * @author Alejandro Loutphi
 */
public class Functions {
    private StringBuilder newText;
    private HashTable<Investigation> tableByTitle;
    private HashTable<LinkedList<Investigation>> tableByKeyword;
    private HashTable<LinkedList<Investigation>> tableByAuthor;

    public static final String INPUT_FILE_NAME = "Investigations.txt";

    public Functions() {
        this.newText = new StringBuilder();
        // TODO tests hash table to be the correct size
        this.tableByTitle = new HashTable<>(32);
        this.tableByAuthor = new HashTable<>(32);
        this.tableByKeyword = new HashTable<>(32);
    }

    public StringBuilder getNewText() {
        return newText;
    }

    public HashTable<Investigation> getTableByTitle() {
        return tableByTitle;
    }

    public HashTable<LinkedList<Investigation>> getTableByKeyword() {
        return tableByKeyword;
    }

    public HashTable<LinkedList<Investigation>> getTableByAuthor() {
        return tableByAuthor;
    }

    /**
     * Reads the program input file and stores their contents in this object's
     * HashTable's.
     * 
     * @throws FileNotFoundException if the input file wasn't found
     * @throws IOException           if input file couldn't be read
     */
    public void buildHashTables() throws FileNotFoundException, IOException {
        File file = new File(INPUT_FILE_NAME);

        if (!file.exists()) {
            System.out.println("bruh");
            throw new FileNotFoundException();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> authorList = new LinkedList<>();
            String[] authorArray = new String[0];
            int authorLen = 0;
            String[] keywordArray;
            ReadingState state = ReadingState.TITLE;
            Investigation investigation = new Investigation();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                switch (state) {
                    case TITLE:
                        investigation = new Investigation();
                        authorList = new LinkedList<>();
                        authorLen = 0;

                        investigation.setTitle(line);
                        state = ReadingState.AUTHORS;
                        break;

                    case AUTHORS:
                        if (line.startsWith("Autores")) {
                            continue;
                        }
                        if (line.startsWith("Resumen")) {
                            state = ReadingState.TEXT;
                            continue;
                        }
                        authorList.add(line);
                        authorLen++;
                        this.tableByAuthor.add(line, new LinkedList<>());
                        break;

                    case TEXT:
                        authorArray = new String[authorLen];
                        authorList.putInArray(authorArray);
                        investigation.setAuthors(authorArray);
                        investigation.setText(line);
                        state = ReadingState.KEYWORDS;
                        break;

                    case KEYWORDS:
                        // Delete initial wording and trailing period
                        line = line.substring("Palabras Claves: ".length());
                        if (line.charAt(line.length() - 1) == '.') {
                            line = line.substring(0, line.length() - 1);
                        }

                        keywordArray = line.split(", ");
                        investigation.setKeywords(keywordArray);
                        for (int i = 0; i < keywordArray.length; i++) {
                            this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                        }
                        state = ReadingState.TITLE;

                        // Add the investigation object to all the hash tables
                        this.tableByTitle.add(investigation.getTitle(), investigation);
                        for (int i = 0; i < authorArray.length; i++) {
                            this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                        }

                        // For each entry in the Keyword Hash Table, add the current investigation if it
                        // contains its keyword
                        for (int i = 0; i < this.tableByKeyword.length(); i++) {
                            for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword
                                    .get(i).getHead(); j != null; j = (HashTableEntry<LinkedList<Investigation>>) j
                                            .getNext()) {
                                if (investigation.contains(j.getKey()))
                                    j.getElt().addAtHead(investigation);
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            throw new IOException();
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
     * @param inv Investigation to analyze
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

        // For each entry in the Hash Table
        for (int i = 0; i < this.tableByKeyword.length(); i++) {
            for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword
                    .get(i).getHead(); j != null; j = (HashTableEntry<LinkedList<Investigation>>) j.getNext()) {
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

    public void uploadFile(String filePath) throws FileNotFoundException, IOException, InvalidParameterException {
        File fileToUpload = new File(filePath);
        if (!fileToUpload.exists()) {
            throw new FileNotFoundException();
        }
        if (!filePath.toLowerCase().endsWith(".txt")) {
            throw new InvalidParameterException();
        }
        addFileToHashTables(fileToUpload);
    }

    private void addFileToHashTables(File file) throws FileNotFoundException, IOException, InvalidParameterException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> authorList = new LinkedList<>();
            String[] authorArray = new String[0];
            int authorLen = 0;
            String[] keywordArray;
            ReadingState state = ReadingState.TITLE;
            Investigation investigation = new Investigation();
            StringBuilder fileText = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                fileText.append("\n").append(line);
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                switch (state) {
                    case TITLE:
                        investigation = new Investigation();
                        authorList = new LinkedList<>();
                        authorLen = 0;

                        investigation.setTitle(line);
                        state = ReadingState.AUTHORS;
                        break;

                    case AUTHORS:
                        if (line.startsWith("Autores")) {
                            continue;
                        }
                        if (line.startsWith("Resumen")) {
                            state = ReadingState.TEXT;
                            continue;
                        }
                        authorList.add(line);
                        authorLen++;
                        this.tableByAuthor.add(line, new LinkedList<>());
                        break;

                    case TEXT:
                        authorArray = new String[authorLen];
                        authorList.putInArray(authorArray);
                        investigation.setAuthors(authorArray);
                        investigation.setText(line);
                        state = ReadingState.KEYWORDS;
                        break;

                    case KEYWORDS:
                        // Delete initial wording and trailing period
                        line = line.substring("Palabras Claves: ".length());
                        if (line.charAt(line.length() - 1) == '.') {
                            line = line.substring(0, line.length() - 1);
                        }

                        keywordArray = line.split(", ");
                        investigation.setKeywords(keywordArray);

                        // Add to hash tables
                        for (int i = 0; i < keywordArray.length; i++) {
                            this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                        }
                        this.tableByTitle.add(investigation.getTitle(), investigation);
                        for (int i = 0; i < authorArray.length; i++) {
                            this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                        }
                        // For each entry in the Keyword Hash Table, add the current investigation if it
                        // contains its keyword
                        for (int i = 0; i < this.tableByKeyword.length(); i++) {
                            for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword
                                    .get(i).getHead(); j != null; j = (HashTableEntry<LinkedList<Investigation>>) j
                                            .getNext()) {
                                if (investigation.contains(j.getKey()))
                                    j.getElt().addAtHead(investigation);
                            }
                        }
                        reader.close();
                        this.newText.append(fileText);
                        return;
                }
            }
            throw new InvalidParameterException();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public boolean parseAndBuildHashTables(String formattedInfo) {
        try {
            String[] lines = formattedInfo.split("\n"); // Split formattedInfo into lines

            String title = "";
            LinkedList<String> authorsList = new LinkedList<>();
            String[] keywords = new String[0];
            StringBuilder abstractText = new StringBuilder();
            String[] authorArray = new String[0];
            int authorlen = 0;
            int state = 0; // Track the state of parsing

            for (String line : lines) {
                line = line.trim();

                switch (state) {
                    case 0:
                        title = line; // First line is the title
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
                            state = 0; // Reset state after processing keywords
                        } else {
                            abstractText.append(line).append("\n"); // Append to abstract text
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
            String author;
            // Iterate over linkedlist
            for (LinkedListNode<String> i = authorsList.getHead(); i != null; i = i.getNext()) {
                author = i.getElt();
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

    public void appendNewTextToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE_NAME, true))) {
            writer.write(this.newText.toString());
        }
    }
      public Investigation[] getSortedInvestigationTitles() {
    int totalEntries = tableByTitle.size();
    Investigation[] titles = new Investigation[totalEntries];
    tableByTitle.putEltsInArray(titles);
    System.out.println("Total entries: " + totalEntries);
    return titles;
}
}