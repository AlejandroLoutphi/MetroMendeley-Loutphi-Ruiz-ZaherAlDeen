/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DataStructures;

/**
 *
 * @author ayahzaheraldeen
 */
public class HashFunction {
    
    // Example: Simulating a hash table with an array
    private int[] hashTable;
    private int tableSize;

    public HashFunction(int size) {
        tableSize = size;
        hashTable = new int[tableSize];
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

    // Example method to add an entry to the hash table
    public void addEntry(String key, int value) {
        int hash = generateHash(key);
        hashTable[hash] = value;
    }

    // Example method to check if the hash table is empty
    public boolean isEmpty() {
        for (int i = 0; i < tableSize; i++) {
            if (hashTable[i] != 0) { // Assuming 0 means no entry at that index
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        HashFunction hasher = new HashFunction(100); // Assuming table size is 100
        String researchTitle = "ayah";
        
        int hashValue = hasher.generateHash(researchTitle);
        System.out.println("Hash value for '" + researchTitle + "': " + hashValue);

        // Add an entry to the hash table
        hasher.addEntry(researchTitle, hashValue);

        // Check if hash table is empty
        System.out.println("Is hash table empty? " + hasher.isEmpty()); // Should print false

        // Clear the hash table (not a standard method, just for demonstration)
        for (int i = 0; i < hasher.tableSize; i++) {
            hasher.hashTable[i] = 0;
        }

        // Check again if hash table is empty
        System.out.println("Is hash table empty? " + hasher.isEmpty()); // Should print true
    }
}

    
