/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Class for storing arrays that involve generics in their definition, because
 * Java doesn't support that.
 *
 * @author Alejandro Loutphi
 * @param <E> type of elements to store
 */
public class GenericArray<E> {

    private Object[] arr;

    /**
     * Constructs an array of null.
     *
     * @param length length of the array
     */
    public GenericArray(int length) {
        this.arr = new Object[length];
    }

    /**
     * Returns length of the array.
     *
     * @return length of the array
     */
    public int length() {
        return this.arr.length;
    }

    /**
     * Returns element at array position n.
     * 
     * @param n array index
     * @return element at array position n
     */
    public E get(int n) {
        @SuppressWarnings("unchecked")
        E o = (E) this.arr[n];
        return o;
    }

    /**
     * Sets element n of the array to the passed-in element
     * 
     * @param n   array index
     * @param elt element to replace the current one in the array
     */
    public void set(int n, E elt) {
        this.arr[n] = elt;
    }
}
