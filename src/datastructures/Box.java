/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Class that just contains one element. Exists so we can create arrays of
 * pointers for generics.
 *
 * @author Alejandro Loutphi
 * @param <E> type of element to store
 */
public class Box<E> {

    E elt;

    /**
     * Constructs a Box with the passed object.
     *
     * @param elt object to box
     */
    public Box(E elt) {
        this.elt = elt;
    }

    /**
     * Returns boxed object.
     *
     * @return boxed object
     */
    public E get() {
        return elt;
    }
}
