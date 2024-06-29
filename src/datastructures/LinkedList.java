/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Linked List with Head
 * 
 * @param <E> type of elements stored in the list
 * @author Alejandro Loutphi
 */
public class LinkedList<E> {

    protected LinkedListNode<E> head;

    /**
     * Constructs empty list.
     */
    public LinkedList() {
        this.head = null;
    }

    /**
     * Constructs list with the elements in the passed array.
     * 
     * @param elts array of elements to convert to a list
     */
    public LinkedList(E[] elts) {
        if (elts.length == 0) {
            this.head = null;
            return;
        }

        this.head = new LinkedListNode<E>(elts, 0);
    }

    /**
     * Constructs list with a single element.
     * 
     * @param elt element to insert into list
     */
    public LinkedList(E elt) {
        this.head = new LinkedListNode<E>(elt, null);
    }

    /**
     * Returns the first node in the list
     * 
     * @return first node in the list
     */
    public LinkedListNode<E> getHead() {
        return this.head;
    }

    /**
     * Returns true if there are no elements in the list. Otherwise false.
     * 
     * @return true if there are no elements in the list. Otherwise false
     */
    public boolean isEmpty() {
        return this.getHead() == null;
    }

    /**
     * Sets the passed node as the first node in the list.
     * Does not change next attribute of new head to be the one of the old head.
     * 
     * @param head node to set as new head
     */
    public void setHead(LinkedListNode<E> head) {
        this.head = head;
    }

    /**
     * Returns the amount of elements in the list.
     * 
     * @return amount of elements in the list
     */
    public int size() {
        if (this.isEmpty()) {
            return 0;
        }
        return this.getHead().size();
    }

    /**
     * Returns the element at index n.
     * 
     * @param n node index
     * @return null if there's no element at index n. Otherwise, element at index n
     */
    public E get(int n) {
        if (this.isEmpty() || n < 0)
            return null;

        return this.getHead().get(n);
    }

    /**
     * Returns the last node of the list.
     * 
     * @return null if list is empty. Otherwise, last node of the list
     */
    public LinkedListNode<E> getLastNode() {
        if (this.isEmpty())
            return null;

        return this.getHead().getLastNode();
    }

    /**
     * Returns the last element of the list.
     * 
     * @return null if list is empty. Otherwise, last element of the list
     */
    public E getLast() {
        return this.getLastNode().getElt();
    }

    /**
     * Appends an element at the end of the list.
     * 
     * @param elt element to append
     */
    public void add(E elt) {
        if (this.isEmpty()) {
            this.head = new LinkedListNode<E>(elt, null);
            return;
        }

        this.getHead().add(elt);
    }

    /**
     * Adds an element in the specified index.
     * Shifts indeces of all elements after the specified index by 1.
     * 
     * @param n   node index
     * @param elt element to add
     * @return true if element could be added at the specified index. Otherwise,
     *         false.
     */
    public boolean add(int n, E elt) {
        if (n < 0)
            return false;

        if (n == 0) {
            this.setHead(new LinkedListNode<>(elt, this.getHead()));
            return true;
        }

        if (this.isEmpty())
            return false;

        return this.getHead().add(n, elt);
    }

    /**
     * Adds an element at the start of the list.
     * Shifts indeces of all elements by 1.
     * 
     * @param elt element to add
     */
    public void addAtHead(E elt) {
        this.add(0, elt);
    }

    /**
     * Adds passed-in node at the start of the list.
     * Shifts indeces of all elements by 1.
     * 
     * @param node node to add
     */
    public void addAtHead(LinkedListNode<E> node) {
        node.setNext(this.getHead());
        this.setHead(node);
    }

    /**
     * Removes element at the specified index.
     * Shifts indeces of all elements after the specified index by -1.
     * 
     * @param n node index
     * @return null if the index was out of bounds. Otherwise, the removed element
     */
    public E remove(int n) {
        if (n != 0)
            return this.getHead().remove(n);

        E o = this.getHead().getElt();
        this.setHead(this.getHead().getNext());
        return o;
    }

    /**
     * Returns the index of the first object in the list identical to the passed
     * object.
     * 
     * @param elt object to search for
     * @return -1 if object wasn't found in the list. Otherwise, index of the first
     *         object in the list identical to the passed object.
     */
    public int indexOf(Object elt) {
        return this.getHead().indexOf(elt, 0);
    }

    /**
     * Sets the nth element in the list to be the passed element.
     * 
     * @param n   node index
     * @param elt element to be the new nth element of the list
     * @return false if n is not a valid node index. Otherwise, true.
     */
    public boolean set(int n, E elt) {
        if (n < 0)
            return false;
        return this.getHead().set(n, elt);
    }

    /**
     * Sets the nth element in the list to be the passed element.
     * Deletes every element after the set element.
     * 
     * @param n   node index
     * @param elt element to be the new nth element of the list
     * @return false if n is not a valid node index. Otherwise, true.
     */
    public boolean setAsEnd(int n, E elt) {
        if (n < 0)
            return false;
        return this.getHead().setAsEnd(n, elt);
    }
}
