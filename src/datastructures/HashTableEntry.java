/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Class for storing a key, and a pointer to an object stored in a hash table.
 *
 * @author Alejandro Loutphi
 */
public class HashTableEntry<E> {

    String key;
    E elt;

    /**
     * Construct a HashTableEntry with all attributes set to null.
     */
    public HashTableEntry() {
        this.key = null;
        this.elt = null;
    }

    /**
     * Constructs a HashTableEntry with the passed in attributes.
     *
     * @param key String key representing this object
     * @param elt element stored in the HashTableEntry
     */
    public HashTableEntry(String key, E elt) {
        this.key = key;
        this.elt = elt;
    }

    /**
     * Returns the key that identifies this object.
     * 
     * @return the key that identifies this object.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the element stored in this object.
     * 
     * @return the element stored in this object
     */
    public E getElt() {
        return elt;
    }
}
