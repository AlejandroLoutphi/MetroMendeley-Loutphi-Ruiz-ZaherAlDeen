/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DataStructures;

/**
 *
 * @author ayahzaheraldeen
 */
import java.util.HashMap;
import metromendeleyloutphiruizzaheraldeen.Investigation;

public class HashFunction {
    // Example: Simulating a hash table with an array
    private HashMap<Integer, Investigation> hashTable;
    private int tableSize;

    public HashFunction(int size) {
        tableSize = size;
        hashTable = new HashMap<>();
    }

    public int generateHash(String input) {
        int hash = 5381; // Initial prime number hash
        int n = input.length();

        for (int i = 0; i < n; i++) {
            hash = ((hash << 5) + hash) + input.charAt(i); /* hash * 33 + c */
        }

        // Ensure hash fits within tableSize using modulo operation
        hash = hash % tableSize;
        if (hash < 0) {
            hash += tableSize; // Ensure hash is non-negative
        }

        return hash;
    }

    // Method to add an entry (Investigation object) to the hash table
    public void addEntry(String key, Investigation value) {
        int hash = generateHash(key);
        hashTable.put(hash, value);
    }

    // Method to retrieve an Investigation object from the hash table by title
    public Investigation getAbstract(String key) {
        int hash = generateHash(key);
        return hashTable.get(hash);
    }

    // Method to check if the hash table contains a specific investigation title
    public boolean containsAbstract(String key) {
        int hash = generateHash(key);
        return hashTable.containsKey(hash);
    }

    // Example method to check if the hash table is empty
    public boolean isEmpty() {
        return hashTable.isEmpty();
    }

    public static void main(String[] args) {
        HashFunction hasher = new HashFunction(100); // Assuming table size is 100

        // Example investigation
        String abstractTitle = "Example Investigation 1";
        String[] authors = {"Author 1", "Author 2"};
        String abstractText = "This is the abstract of Example Investigation 1.";
        String[] keywords = {"Keyword 1", "Keyword 2"};

        // Create an Investigation object
        Investigation investigation = new Investigation(abstractTitle, authors, abstractText, keywords);

        // Generate hash and add entry to hash table
        int hashValue = hasher.generateHash(abstractTitle);
        hasher.addEntry(abstractTitle, investigation);

        // Retrieve Investigation object by title
        Investigation retrievedInvestigation = hasher.getAbstract(abstractTitle);

        // Print investigation details
        if (retrievedInvestigation != null) {
            System.out.println("Investigation Title: " + retrievedInvestigation.getTitle());
            System.out.println("Authors: " + String.join(", ", retrievedInvestigation.getAuthors()));
            System.out.println("Abstract: " + retrievedInvestigation.getText());
            System.out.println("Keywords: " + String.join(", ", retrievedInvestigation.getKeywords()));
        } else {
            System.out.println("Investigation with title '" + abstractTitle + "' not found.");
        }

        // Check if hash table is empty
        System.out.println("Is hash table empty? " + hasher.isEmpty()); // Should print false
    }
}

    
