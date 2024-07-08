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
import javax.swing.JOptionPane;
import datastructures.LinkedList;
import datastructures.HashTable;
import datastructures.HashTableEntry;

/**
 * App Class for holding the state and unique functions of the
 * {@link MetroMendeleyLoutphiRuizZaherAlDeen} project.
 *
 * @author ayahzaheraldeen
 * @author Alejandro Loutphi
 */
public class App {

    private StringBuilder newText;
    private HashTable<Investigation> tableByTitle;
    private HashTable<LinkedList<Investigation>> tableByKeyword;
    private HashTable<LinkedList<Investigation>> tableByAuthor;
    private boolean isAdmin;

    public static final String INPUT_FILE_NAME = "Investigations.txt";

    public App() {
        this.newText = new StringBuilder();
        this.newText.append("\n");
        this.tableByTitle = new HashTable<>(64);
        this.tableByAuthor = new HashTable<>(64);
        this.tableByKeyword = new HashTable<>(64);
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
            throw new FileNotFoundException();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> authorList = new LinkedList<>();
            String[] authorArray = new String[0];
            int authorLen = 0;
            String[] keywordArray;
            ReadingState readingState = ReadingState.TITLE;
            Investigation investigation = new Investigation();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                switch (readingState) {
                    case TITLE:
                        investigation = new Investigation();
                        authorList = new LinkedList<>();
                        authorLen = 0;

                        investigation.setTitle(line);
                        readingState = ReadingState.AUTHORS;
                        break;

                    case AUTHORS:
                        if (line.startsWith("Autores")) {
                            continue;
                        }
                        if (line.startsWith("Resumen")) {
                            readingState = ReadingState.TEXT;
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
                        readingState = ReadingState.KEYWORDS;
                        break;

                    case KEYWORDS:
                        // Delete initial wording and trailing period
                        line = line.substring("Palabras Claves: ".length());
                        if (line.charAt(line.length() - 1) == '.') {
                            line = line.substring(0, line.length() - 1);
                        }

                        keywordArray = line.split(", ");
                        investigation.setKeywords(keywordArray);
                        readingState = ReadingState.TITLE;

                        // Add the investigation object to all the hash tables
                        this.tableByTitle.add(investigation.getTitle(), investigation);
                        for (int i = 0; i < authorArray.length; i++) {
                            this.tableByAuthor.add(authorArray[i], new LinkedList<>());
                            this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                        }
                        for (int i = 0; i < keywordArray.length; i++) {
                            this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                            this.tableByKeyword.lookUp(keywordArray[i]).addAtHead(investigation);
                        }
                }
            }
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

    /**
     * Loads the file at the passed-in file path into the hash tables and into the
     * newText buffer for saving.
     *
     * @param filePath path of file to load
     * @return false if operation failed. Otherwise, true.
     * @throws FileNotFoundException     if file doesn't exist
     * @throws IOException               if file couldn't be read
     * @throws InvalidParameterException if file isn't of the valid format
     */
    public boolean uploadFile(String filePath) throws FileNotFoundException, IOException, InvalidParameterException {
        File fileToUpload = new File(filePath);
        if (!fileToUpload.exists()) {
            throw new FileNotFoundException();
        }
        if (!filePath.toLowerCase().endsWith(".txt")) {
            throw new InvalidParameterException();
        }
        return addFileToHashTables(fileToUpload);
    }

    /**
     * Loads the passed-in file into the hash tables and into the newText buffer for
     * saving.
     *
     * @param file file to load
     * @throws FileNotFoundException     if file doesn't exist
     * @throws IOException               if file couldn't be read
     * @throws InvalidParameterException if file isn't of the valid format
     */
    private boolean addFileToHashTables(File file) throws IOException, InvalidParameterException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> authorList = new LinkedList<>();
            String[] authorArray = new String[0];
            int authorLen = 0;
            String[] keywordArray;
            ReadingState readingState = ReadingState.TITLE;
            Investigation investigation = new Investigation();
            StringBuilder fileText = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                fileText.append("\n").append(line);
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                switch (readingState) {
                    case TITLE:
                        investigation = new Investigation();
                        authorList = new LinkedList<>();
                        authorLen = 0;

                        if (this.tableByTitle.contains(line)) {
                            return false;
                        }

                        investigation.setTitle(line);
                        readingState = ReadingState.AUTHORS;
                        break;

                    case AUTHORS:
                        if (line.startsWith("Autores")) {
                            continue;
                        }
                        if (line.startsWith("Resumen")) {
                            readingState = ReadingState.TEXT;
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
                        readingState = ReadingState.KEYWORDS;
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
                            this.tableByKeyword.lookUp(keywordArray[i]).addAtHead(investigation);
                        }
                        for (int i = 0; i < authorArray.length; i++) {
                            this.tableByAuthor.add(authorArray[i], new LinkedList<>());
                            this.tableByAuthor.lookUp(authorArray[i]).addAtHead(investigation);
                        }
                        this.tableByTitle.add(investigation.getTitle(), investigation);

                        reader.close();
                        this.newText.append(fileText);
                        return true;
                }
            }
            throw new InvalidParameterException();
        }
    }

    /**
     * Loads the passed-in string into the hash tables and into the newText buffer
     * for
     * saving.
     *
     * @param info String containing the formatted info for an Investigation object
     * @return false if the input investigation's name already exists. Otherwise,
     *         true.
     */
    public boolean addStringtoHashTables(String info) {
        String[] lines = info.split("\n");

        String title = "";
        LinkedList<String> authorsList = new LinkedList<>();
        String[] keywords = new String[0];
        StringBuilder abstractText = new StringBuilder();
        String[] authorArray = new String[0];
        int authorlen = 0;
        ReadingState readingState = ReadingState.TITLE;

        for (String line : lines) {
            line = line.trim();

            switch (readingState) {
                case TITLE:
                    if (this.tableByTitle.contains(line)) {
                        return false;
                    }
                    title = line; // First line is the title
                    readingState = ReadingState.AUTHORS;
                    break;

                case AUTHORS:
                    if (line.startsWith("Autores")) {
                        continue;
                    }
                    if (line.startsWith("Resumen")) {
                        readingState = ReadingState.TEXT;
                        continue;
                    }
                    // Collect authors until "Resumen" is encountered
                    authorsList.add(line);
                    authorlen++;
                    break;

                case TEXT:
                    authorArray = new String[authorlen];
                    authorsList.putInArray(authorArray);
                    readingState = ReadingState.KEYWORDS;
                    break;

                case KEYWORDS:
                    if (line.startsWith("Palabras Claves")) {
                        String keywordsLine = line.substring("Palabras Claves: ".length());
                        // Delete initial wording and trailing period
                        line = line.substring("Palabras Claves: ".length());
                        if (line.charAt(line.length() - 1) == '.') {
                            line = line.substring(0, line.length() - 1);
                        }
                        keywords = keywordsLine.split(", ");
                    } else {
                        abstractText.append(line).append("\n"); // Append to abstract text
                    }
                    break;
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
        // Iterate over linkedlist
        for (String author : authorArray) {
            this.tableByAuthor.add(author, new LinkedList<>());
            this.tableByAuthor.lookUp(author).addAtHead(investigation);
        }
        for (String keyword : keywords) {
            this.tableByKeyword.add(keyword, new LinkedList<>());
            this.tableByKeyword.lookUp(keyword).addAtHead(investigation);
        }
        this.appendToNewText(info);
        return true;
    }

    /**
     * Appends the newText string to the input file for future use when the app is
     * launched again.
     * 
     * @throws IOException if the file couldn't be written
     */
    public void appendNewTextToFile() throws IOException {

        File fileToWrite = new File(INPUT_FILE_NAME);

        // Append newText to the file using FileWriter with append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            writer.append(newText.toString());
            writer.newLine();
            writer.flush();
        }
    }

    /**
     * Returns true if a comes before b alphabetically.
     * 
     * @param a String
     * @param b String
     * @return true if a comes before b alphabetically
     */
    private static boolean comesFirst(String a, String b) {
        if (a.toLowerCase().compareTo(b.toLowerCase()) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes the tree from the root a max heap based on the titles of the
     * Investigation objects.
     * 
     * @param arr    array to modify
     * @param length modify the array as if it had this length
     * @param root   root of tree to operate on
     */
    private static void maxHeapify(Investigation[] arr, int length, int root) {
        int max = root;
        Investigation temp;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < length && App.comesFirst(arr[max].getTitle(), arr[left].getTitle())) {
            max = left;
        }

        if (right < length && App.comesFirst(arr[max].getTitle(), arr[right].getTitle())) {
            max = right;
        }

        if (root != max) {
            temp = arr[root];
            arr[root] = arr[max];
            arr[max] = temp;
            App.maxHeapify(arr, length, max);
        }
    }

    /**
     * Sorts the array of Investigation objects by their titles aphabetically using
     * heap sort.
     * 
     * @param arr array to sort
     */
    private static void heapSortByTitle(Investigation[] arr) {
        Investigation temp;

        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            App.maxHeapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            App.maxHeapify(arr, i, 0);
        }
    }

    /**
     * Returns an array with all investigations sorted by their title.
     *
     * @return array with all investigations sorted by their title
     */
    public Investigation[] getInvestigationsSortedByTitle() {
        Investigation[] titles = new Investigation[tableByTitle.size()];
        tableByTitle.putEltsInArray(titles);
        App.heapSortByTitle(titles);
        return titles;
    }

    /**
     * Returns an array with all investigations with the passed-in title.
     *
     * @param title keyword to search for
     * @return array with all investigations with the passed-in title
     */
    public Investigation[] getInvestigationsWithTitle(String title) {
        Investigation[] o = new Investigation[] { this.tableByTitle.lookUp(title) };
        if (o[0] == null) {
            return new Investigation[0];
        }
        return o;
    }

    /**
     * Returns an array with all investigations with the passed-in keyword.
     *
     * @param keyword keyword to search for
     * @return array with all investigations with the passed-in keyword
     */
    public Investigation[] getInvestigationsWithKeyword(String keyword) {
        LinkedList<Investigation> list = this.tableByKeyword.lookUp(keyword);
        if (list == null) {
            return new Investigation[0];
        }
        Investigation[] o = new Investigation[list.size()];
        list.putInArray(o);
        return o;
    }

    /**
     * Returns an array with all investigations with the passed-in author.
     *
     * @param author author to search for
     * @return array with all investigations with the passed-in author
     */
    public Investigation[] getInvestigationsWithAuthor(String author) {
        LinkedList<Investigation> list = this.tableByAuthor.lookUp(author);
        if (list == null) {
            return new Investigation[0];
        }
        Investigation[] o = new Investigation[list.size()];
        list.putInArray(o);
        return o;
    }

    /**
     * Appends the new investigation details into a string variable newText.
     *
     * @param formattedInfo new entered investigation details/information
     */
    public void appendToNewText(String formattedInfo) {
        // Append formattedInfo to newText
        newText.append(formattedInfo);

    }

    /**
     * Returns a boolean that checks if the newText string is empty or not.
     * 
     * @return a boolean that checks if newText is filled
     */
    public boolean isStringNotEmpty() {
        return (newText != null) && (newText.length() != 0);
    }

    /**
     * Returns true if the user has entered the password correctly once. Otherwise,
     * false. Prompts the user with JOptionPane to enter the password if he hasn't
     * already.
     * 
     * @return true if the user has entered the password correctly once. Otherwise,
     *         false
     */
    public boolean isAdmin() {
        if (this.isAdmin)
            return true;
        final int correctHash = 23749;

        while (true) {
            String password = JOptionPane.showInputDialog(null, "Insertar contraseña para cargar archivo:");

            if (password == null) {
                return false;
            } else if (HashTable.hashModX(password, 100000) == correctHash) {
                this.isAdmin = true;
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta",
                        "Contraseña Incorrecta", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
