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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
                        for (int i = 0; i < keywordArray.length; i++) {
                            this.tableByKeyword.add(keywordArray[i], new LinkedList<>());
                        }
                        readingState = ReadingState.TITLE;

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
     * @throws FileNotFoundException     if file doesn't exist
     * @throws IOException               if file couldn't be read
     * @throws InvalidParameterException if file isn't of the valid format
     */
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

    /**
     * Loads the passed-in file into the hash tables and into the newText buffer for
     * saving.
     *
     * @param file file to load
     * @throws FileNotFoundException     if file doesn't exist
     * @throws IOException               if file couldn't be read
     * @throws InvalidParameterException if file isn't of the valid format
     */
    private void addFileToHashTables(File file) throws IOException, InvalidParameterException {
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
        }
    }
    //Aquí abajo se encuentra todo para traversar el archivo
    
    public  String ArticulosPorAutor(String autor){ //Esto será utilizado para el serch UI de keywords y autores
    String documento = "";
    for (int i = 0; i < this.tableByAuthor.length(); i++) {
        for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByAuthor
            .get(i).getHead(); j != null; j = (HashTableEntry<LinkedList<Investigation>>) j
            .getNext()) {
                LinkedList<Investigation> key = j.getElt();
                                
                if(j.getKey().contains(autor)){ //Lo muestra si es True
                    System.out.println(j.getKey());
                    LinkedListNode<Investigation> variable = key.getHead();
                                
                        while (variable != null){ //Recorre la lista
                            System.out.println( variable.getElt().GetArticleString()
                                                    );
                            documento += variable.getElt().GetArticleString()+ "\n";
                            variable = variable.getNext();
                                }
                                }
                                
                            }
                            
                        }
    return documento;
}
    
    public  String ArticulosPorKeyword(String autor){ //Esto será utilizado para el serch UI de keywords y autores
    String documento = "";
    for (int i = 0; i < this.tableByKeyword.length(); i++) {
        for (HashTableEntry<LinkedList<Investigation>> j = (HashTableEntry<LinkedList<Investigation>>) tableByKeyword
            .get(i).getHead(); j != null; j = (HashTableEntry<LinkedList<Investigation>>) j
            .getNext()) {
                LinkedList<Investigation> key = j.getElt();
                                
                if(j.getKey().contains(autor)){ //Lo muestra si es True
                    System.out.println(j.getKey());
                    LinkedListNode<Investigation> variable = key.getHead();
                                
                        while (variable != null){ //Recorre la lista
                            documento += variable.getElt().GetArticleString()+ "\n";
                            System.out.println( variable.getElt().GetArticleString()
                                                    );
                            
                            variable = variable.getNext();
                                }
                                }
                                
                            }
                            
                        }
    return documento;
}
    
    public  String ArticulosPorTitle(String Titulo){ //Esto será utilizado para el serch UI de keywords y autores
    String documento = "";

    for (int i = 0; i < this.tableByTitle.length(); i++) {
        for (HashTableEntry<Investigation>j = (HashTableEntry<Investigation>) tableByTitle
            .get(i).getHead(); j != null; j = (HashTableEntry<Investigation>) j
            .getNext()) {
                String key = j.getElt().getTitle();;
                                
                if(j.getKey().contains(Titulo)){ //Lo muestra si es True
                    System.out.println(j.getKey());
                    
                            String str = String.join(" , ", j.getElt().getAuthors());    
                            documento += j.getElt().GetArticleString()+ "\n";
                            System.out.println(j.getElt().GetArticleString()
                                                    );
                            
                                
                                }
                                
                            }
                            
                        }
    return documento;
}

    public  String LeerArticulos(){ //Esto será utilizado para el serch UI de keywords y autores
    String documento = "";

    for (int i = 0; i < this.tableByTitle.length(); i++) {
        for (HashTableEntry<Investigation>j = (HashTableEntry<Investigation>) tableByTitle
            .get(i).getHead(); j != null; j = (HashTableEntry<Investigation>) j
            .getNext()) {
                String key = j.getElt().getTitle();;
                                
                //Lo muestra si es True
                    System.out.println(j.getKey());
                    
                            String str = String.join(" , ", j.getElt().getAuthors());    
                            documento += j.getElt().GetArticleString()+ "\n";
                            System.out.println(j.getElt().GetArticleString()
                                                    );
                            
                                
                                
                                
                            }
                            
                        }
    return documento;
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
    public boolean parseAndBuildHashTables(String info) {
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
                    authorsList.add(line);
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
        boolean o = this.tableByTitle.add(title, investigation);
        String author;
        // Iterate over linkedlist
        for (LinkedListNode<String> i = authorsList.getHead(); i != null; i = i.getNext()) {
            author = i.getElt();
            this.tableByAuthor.lookUp(author).addAtHead(investigation);
        }
        for (String keyword : keywords) {
            this.tableByKeyword.lookUp(keyword).addAtHead(investigation);
        }

        return o;
    }

    /**
     * Appendds the newText string to the input file for future use when the app is
     * launched again.
     * 
     * @throws IOException if the file couldn't be written
     */
    public void appendNewTextToFile() throws IOException {
        File fileToWrite = new File(INPUT_FILE_NAME);
        if (!fileToWrite.exists()) {
            throw new FileNotFoundException();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite))) {
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
      public boolean isHashTablePopulated() {
    // Check if tableByTitle is initialized and not null
    if (tableByTitle == null) {
        System.out.println("tableByTitle is not initialized!");
        return false;
    }
    
    // Check if tableByTitle has entries
    int size = tableByTitle.size();
    if (size == 0) {
        System.out.println("tableByTitle is empty!");
        return false;
    }
    
    // Optionally, you can print or log the content of the hash table
    System.out.println("tableByTitle size: " + size);
    // You may print or log more details about the entries if needed
    
    return true;
}
}