/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package datastructures;

/**
 * Hash Table class, built from an array of elements of type E.
 *
 * @author ayahzaheraldeen
 * @author Alejandro Loutphi
 */
public class HashTable<E> extends GenericArray<LinkedList<HashTableEntry<E>>> {
  
    /**
     * Constructs hash table with passed-in length.
     *
     * @param length length of hash table
     */
    public HashTable(int length) {
        super(length);
        for (int i = 0; i < this.length(); i++) {
            this.set(i, new LinkedList<HashTableEntry<E>>());
        }
    }

    /**
     * Hash function returning position of element with passed-in key.
     * 
     * @param key String to be hashed
     * @return position of element with passed-in key
     */
    public int hash(String key) {
        int len = key.length();
        if (len < 3) {
            return 0;
        }

        // Do something kinda random with the first and last few characters
        int o = key.charAt(0)
                - 3 * key.charAt(1)
                + 5 * key.charAt(2)
                - 9 * key.charAt(len - 3)
                + 17 * key.charAt(len - 2)
                - 33 * key.charAt(len - 1);
        if (o < 0)
            o = -o;
        return o % this.length();
    }

    /**
     * Returns true if there are no elements in the table. Otherwise, false.
     * 
     * @return true if there are no elements in the table. Otherwise, false
     */
    public boolean isEmpty() {
        for (int i = 0; i < this.length(); i++) {
            if (!this.get(i).isEmpty())
                return false;
        }
        return true;
    }

    /**
     * Returns the element of the input list with the passed-in key or null if there
     * were no matches.
     * 
     * @param key       String key to check for matches
     * @param entryList list to search in
     * @return element of the input list with the passed-in key or null if there
     *         were no matches
     */
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

    /**
     * Returns the element in the table with the passed-in key or null if there were
     * no matches.
     * 
     * @param key String key to check for matches
     * @return the element in the table with the passed-in key or null if there were
     *         no matches
     */
    public E lookUp(String key) {
        int hash = this.hash(key);
        return this.lookUpEntryListFor(key, this.get(hash));
    }

    /**
     * Returns true if there's an element of the input list with the passed-in key.
     * Otherwise, false.
     * 
     * @param key       String key to check for matches
     * @param entryList list to search in
     * @return true if there's an element of the input list with the passed-in key.
     *         Otherwise, false
     */
    private boolean entryListContains(String key, LinkedList<HashTableEntry<E>> entryList) {
        return this.lookUpEntryListFor(key, entryList) != null;
    }

    /**
     * Returns true if there's an element in the table with the passed-in key.
     * Otherwise, false.
     * 
     * @param key String key to check for matches
     * @return true if there's an element in the table with the passed-in key.
     *         Otherwise, false
     */
    public boolean contains(String key) {
        int hash = this.hash(key);
        return this.entryListContains(key, this.get(hash));
    }

    /**
     * Adds passed-in element to the table.
     * 
     * @param key key identifying elt
     * @param elt element to add
     * @return true if element could be added. Otherwise, false
     */
    public boolean add(String key, E elt) {
        int hash = this.hash(key);
        LinkedList<HashTableEntry<E>> entryList = this.get(hash);
        if (this.entryListContains(key, entryList)) {
            return false;
        }
        entryList.addAtHead(new HashTableEntry<>(key, elt));
        return true;
    }
}
