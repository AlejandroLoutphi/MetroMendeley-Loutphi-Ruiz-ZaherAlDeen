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
    

    public int generateHash(String input, int tableSize) {
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

    public static void main(String[] args) {
        HashFunction hasher = new HashFunction();
        String researchTitle = "Example Research Title";
        
        int hashValue = hasher.generateHash(researchTitle, 100); // Assuming table size is 100
        System.out.println("Hash value for '" + researchTitle + "': " + hashValue);
    }
}

    
