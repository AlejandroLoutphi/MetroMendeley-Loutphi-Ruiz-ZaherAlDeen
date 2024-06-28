/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructures;

/**
 * Node for linked lists.
 * @param <E> type of element stored in the node
 */
public class LinkedListNode<E> {

    protected E elt;
    protected LinkedListNode<E> next;

    /**
     * Constructs a node with no element null next pointer.
     */
    public LinkedListNode() {
        this.elt = null;
        this.next = null;
    }

    /**
     * Constructs a node with the specified element and next node.
     * @param elt element of node
     * @param next next node
     */
    public LinkedListNode(
            E elt,
            LinkedListNode<E> next) {
        this.elt = elt;
        this.next = next;
    }

    /**
     * Constructs a node with the nth element of the passed array.
     * Next node is new node with (n+1)th element of the passed array, and so on.
     * @param elts array of elements to construct a series of nodes from
     * @param n array index
     */
    public LinkedListNode(
            E[] elts,
            int n) {
        this.elt = elts[n];
        int nextIndex = n + 1;
        if (nextIndex == elts.length) {
            this.next = null;
            return;
        }
        this.next = new LinkedListNode<E>(elts, nextIndex);
    }

    /**
     * Returns the element housed in the node.
     * @return element housed in the node
     */
    public E getElt() {
        return this.elt;
    }

    /**
     * Sets node element to passed element.
     * @param elt element to replace the current node element
     */
    public void setElt(E elt) {
        this.elt = elt;
    }

    /**
     * Returns the next node pointed to by this node.
     * @return next node pointed to by this node
     */
    public LinkedListNode<E> getNext() {
        return this.next;
    }

    /**
     * Sets next node to passed node.
     * @param next node to replace the current next node
     */
    public void setNext(LinkedListNode<E> next) {
        this.next = next;
    }

    /**
     * Returns the element in the next.
     * @return element in the next
     */
    public E next() {
        return this.getNext().getElt();
    }

    /**
     * Returns true if the node points to a next element. Otherwise, false.
     * @return true if the node points to a next element. Otherwise, false
     */
    public boolean hasNext() {
        return this.next != null;
    }

    /**
     * Returns the amount of nodes following this one.
     * @return amount of nodes following this one
     */
    public int size() {
        if (this.next == null) {
            return 1;
        }
        return this.next.size() + 1;
    }

    /**
     * Appends a node with the passed element to the last node following this one.
     * @param elt element to append
     */
    public void add(E elt) {
        if (this.next != null) {
            next.add(elt);
            return;
        }

        this.next = new LinkedListNode<E>(elt, null);
    }

    /**
     * Adds a new node with the passed element as the nth node following this one.
     * @param n node index
     * @param elt element to add
     * @return true if element could be added at the specified index. Otherwise, false.
     */
    public boolean add(int n, E elt) {
        if (n > 1) {
            if (this.next == null)
                return false;
            return this.next.add(n - 1, elt);
        }

        this.next = new LinkedListNode<E>(elt, this.next);
        return true;
    }

    /**
     * Returns the element of the nth node following this one.
     * @param n node index
     * @return null of there's no element at index n. Otherwise, element at index n
     */
    public E get(int n) {
        if (n > 0) {
            if (this.next == null)
                return null;
            return this.next.get(n - 1);
        }

        return this.elt;
    }

    /**
     * Returns the last node following this one.
     * @return last node following this one
     */
    public LinkedListNode<E> getLastNode() {
        if (this.next == null)
            return this;

        return this.getNext().getLastNode();
    }

    /**
     * Returns the element of the last node following this one.
     * @return element of the last node following this one
     */
    public E getLast() {
        return this.getLastNode().getElt();
    }

    /**
     * Removes the nth element of the series of nodes starting from this one.
     * @param n node index
     * @return null if the index was out of bounds. Otherwise, the removed element
     */
    public E remove(int n) {
        if (this.next == null)
            return null;

        if (n > 1)
            return this.getNext().remove(n + 1);

        E o = this.getNext().getElt();
        this.setNext(this.getNext().getNext());
        return o;
    }

    /**
     * Returns the index of the first object of the node series starting from this one identical to the passed object + n.
     * @param elt object to search for
     * @param n number to add to return values
     * @return -1 if object wasn't found. Otherwise, index of the first object identical to the passed object + n
     */
    public int indexOf(Object elt, int n) {
        if (this.elt.equals(elt))
            return n;

        if (this.next == null)
            return -1;

        return this.next.indexOf(elt, n + 1);
    }

    /**
     * If n is 0, set the element of current node to elt.
     * Otherwise, set element of the nth node following this one to elt.
     * @param n node index
     * @param elt element to set as new node element
     * @return false if there isn't an nth node following this one. Otherwise, true.
     */
    public boolean set(int n, E elt) {
        if (n == 0) {
            this.elt = elt;
            return true;
        }

        if (this.next == null)
            return false;

        return this.getNext().set(n - 1, elt);
    }

    /**
     * If n is 0, set the element of current node to elt and delete all subsequent nodes.
     * Otherwise, set element of the nth node following this one to elt and delete all subsequent nodes.
     * @param n node index
     * @param elt element to set as new node element
     * @return false if there isn't an nth node following this one. Otherwise, true.
     */
    public boolean setAsEnd(int n, E elt) {
        if (n == 0) {
            this.elt = elt;
            this.next = null;
            return true;
        }

        if (this.next == null)
            return false;

        return this.getNext().setAsEnd(n - 1, elt);
    }
}
