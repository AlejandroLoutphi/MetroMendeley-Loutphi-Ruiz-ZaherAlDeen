/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

/**
 * Class storing the title, authors, text, and keywords of an abstract.
 *
 * @author Alejandro Loutphi
 */
public class Abstract {

    String title;
    String[] authors;
    String text;
    String[] keywords;

    /**
     * Constructs an Abstract with all attributes set to null.
     */
    public Abstract() {
        this.title = null;
        this.authors = null;
        this.text = null;
        this.keywords = null;
    }

    /**
     * Constructs an Abstract with all attributes set to null.
     *
     * @param title abstract title
     * @param authors String[] with abstract authors
     * @param text abstract text
     * @param keywords String[] with abstract keywords
     */
    public Abstract(String title, String[] authors, String text, String[] keywords) {
        this.title = title;
        this.authors = authors;
        this.text = text;
        this.keywords = keywords;
    }

    /**
     * Returns abstract title.
     * @return abstract title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title to the passed title.
     * @param title string with new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns array with abstract authors.
     * @return array with abstract authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Sets the authors to the passed authors.
     * @param authors String[] with new authors
     */
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    /**
     * Returns abstract text.
     * @return abstract text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to the passed text.
     * @param text string with new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns array with abstract keywords.
     * @return array with abstract keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Sets the keywords to the passed keywords.
     * @param keywords String[] with new keywords
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
}
