/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Class for storing a key, and a pointer to an object stored in a hash table.
 * Part of a LinkedList.
 *
 * @author Alejandro Loutphi
 * @param <E> type of element to store
 */
public class HashTableEntry<E> extends LinkedListNode<E> {

    String key;

    /**
     * Construct a HashTableEntry with all attributes set to null.
     */
    public HashTableEntry() {
        super();
        this.key = null;
    }

    /**
     * Constructs a HashTableEntry with the passed in attributes.
     *
     * @param key String key representing this object
     * @param elt element stored in the HashTableEntry
     */
    public HashTableEntry(String key, E elt) {
        super(elt, null);
        this.key = key;
    }

    /**
     * Returns the key that identifies this object.
     * 
     * @return the key that identifies this object.
     */
    public String getKey() {
        return key;
    }
}
