/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package datastructures;

/**
 *
 * @author ayahzaheraldeen
 * @author Alejandro Loutphi
 */
public class HashTable<E> {
    private GenericArray<LinkedList<HashTableEntry<E>>> table;

    public HashTable(int length) {
        this.table = new GenericArray<>(length);
        for (int i = 0; i < this.table.length(); i++) {
            this.table.set(i, new LinkedList<HashTableEntry<E>>());
        }
    }

    public int generateHash(String input) {
        // TODO delete
        return 0;

        // int hash = 5381; // Initial prime number hash
        // int n = input.length();

        // for (int i = 0; i < n; i++) {
        //     hash = ((hash << 5) + hash) + input.charAt(i); /* hash * 33 + c */
        // }

        // // Ensure hash fits within tableSize using modulo operation
        // hash = hash % this.table.length();
        // if (hash < 0) {
        //     hash += this.table.length(); // Ensure hash is non-negative
        // }

        // return hash;
    }

    public int length() {
        return this.table.length();
    }

    public GenericArray<LinkedList<HashTableEntry<E>>> getTableArray() {
        return this.table;
    }

    private E lookUpEntryListFor(String key, LinkedList<HashTableEntry<E>> entryList) {
        HashTableEntry<E> entry;
        if (entryList.isEmpty()) {
            return null;
        }
        for (LinkedListNode<HashTableEntry<E>> i = entryList.getHead(); i != null; i = i.getNext()) {
            entry = i.getElt();
            if (entry.getKey().equals(key))
                return entry.getElt();
        }
        return null;
    }

    public E lookUp(String key) {
        int hash = this.generateHash(key);
        return this.lookUpEntryListFor(key, this.table.get(hash));
    }

    private boolean entryListContains(String key, LinkedList<HashTableEntry<E>> entryList) {
        return this.lookUpEntryListFor(key, entryList) != null;
    }

    public boolean contains(String key) {
        return this.lookUp(key) != null;
    }

    public boolean isEmpty() {
        for (int i = 0; i < this.table.length(); i++) {
            if (!this.table.get(i).isEmpty())
                return false;
        }
        return true;
    }

    public boolean add(String key, E elt) {
        int hash = this.generateHash(key);
        LinkedList<HashTableEntry<E>> entryList = this.table.get(hash);
        if (this.entryListContains(key, entryList)) {
            return false;
        }
        entryList.addAtHead(new HashTableEntry<>(key, elt));
        return true;
    }
}
