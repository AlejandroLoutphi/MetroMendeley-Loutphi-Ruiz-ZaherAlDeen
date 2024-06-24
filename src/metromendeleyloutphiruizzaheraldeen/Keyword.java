/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import datastructures.LinkedList;

/**
 * Class for storing an abstract keyword, as well as the abstracts in which the
 * keyword appears in.
 *
 * @author Alejandro Loutphi
 */
public class Keyword {

    String keyword;
    LinkedList<Investigation> abstractsWithKeyword;

    /**
     * Construct Keyword with all attributes set to null.
     */
    public Keyword() {
        this.keyword = null;
        this.abstractsWithKeyword = null;
    }

    /**
     * Constructs Keyword with the passed in attributes.
     *
     * @param keyword              String keyword that this object represents
     * @param abstractsWithKeyword LinkedList<Abstract> with all abstracts in which
     *                             the keyword appears
     */
    public Keyword(String keyword, LinkedList<Investigation> abstractsWithKeyword) {
        this.keyword = keyword;
        this.abstractsWithKeyword = abstractsWithKeyword;
    }

    /**
     * Constructs Keyword with an empty abstractsWithKeyword list.
     *
     * @param keyword String keyword that this object represents
     */
    public Keyword(String keyword) {
        this.keyword = keyword;
        this.abstractsWithKeyword = new LinkedList<>();
    }

    /**
     * Returns the keyword represented by this object.
     * 
     * @return the keyword represented by this object
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Sets the keyword attribute to the passed String.
     * 
     * @param keyword String to set as the new keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the LinkedList with all tha Abstracts in which this keyword appears.
     * 
     * @return the LinkedList with all tha Abstracts in which this keyword appears
     */
    public LinkedList<Investigation> getAbstractsWithKeyword() {
        return abstractsWithKeyword;
    }

    /**
     * Sets the abstractsWithKeyword attribute to the passed LinkedList.
     * 
     * @param abstractsWithKeyword LinkedList to set as the new abstractsWithKeyword
     */
    public void setAbstractsWithKeyword(LinkedList<Investigation> abstractsWithKeyword) {
        this.abstractsWithKeyword = abstractsWithKeyword;
    }

    /**
     * Adds a new abstract to the list of abstracts with this keyword.
     * 
     * @param newAbstract new abstract to add to the list
     */
    public void addAbstract(Investigation newAbstract) {
        this.getAbstractsWithKeyword().addAtHead(newAbstract);
    }
}
