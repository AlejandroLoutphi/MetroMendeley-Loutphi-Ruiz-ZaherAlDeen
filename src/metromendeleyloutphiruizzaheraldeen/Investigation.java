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
public class Investigation {

    private String title;
    private String[] authors;
    private String text;
    private String[] keywords;

    /**
     * Constructs an Abstract with all attributes set to null.
     */
    public Investigation() {
        this.title = null;
        this.authors = null;
        this.text = null;
        this.keywords = null; // Hola
    }

    /**
     * Constructs an Abstract with all attributes set to null.
     *
     * @param title        abstract title
     * @param authors      String[] with abstract authors
     * @param abstractBody abstract text
     * @param keywords     String[] with abstract keywords
     */
    public Investigation(String title, String[] authors, String abstractBody, String[] keywords) {
        this.title = title;
        this.authors = authors;
        this.text = abstractBody;
        this.keywords = keywords;
    }

    /**
     * Returns abstract title.
     * 
     * @return abstract title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title to the passed title.
     * 
     * @param title string with new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns array with abstract authors.
     * 
     * @return array with abstract authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Sets the authors to the passed authors.
     * 
     * @param authors String[] with new authors
     */
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    /**
     * Returns abstract text.
     * 
     * @return abstract text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to the passed text.
     * 
     * @param text string with new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns array with abstract keywords.
     * 
     * @return array with abstract keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Sets the keywords to the passed keywords.
     * 
     * @param keywords String[] with new keywords
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns a String with a formatted version of this object's attributes.
     *
     * @return String with a formatted version of this object's attributes
     */
    public String getAbstractInfo() {
        String[] authors = this.authors;
        String abstractBody = this.text;
        String[] keywords = this.keywords;
        return ("Autores: " + String.join(" , ", authors) + "\n\n"
                + "Resumen:\n" + abstractBody + "\n\n"
                + "Palabras Claves: " + String.join(" , ", keywords) + "\n");
    }

    /**
     * Returns the amount of times the passed-in word appears in the investigation
     * abstract text.
     * 
     * @param word String with the word to look for
     * @return the amount of times the passed-in word appears in the investigation
     *         abstract text
     */
    public int frequency(String word) {
        int wordLen = word.length();
        int o = 0;
        for (int i = 0; i < this.text.length() - wordLen + 1; i++) {
            if (this.text.substring(i, i + wordLen).equals(word))
                o++;
        }
        return o;
    }

    /**
     * Returns true if the word appears in the title, text, or keywords of the
     * abstract. Otherwise, false.
     *
     * @param word String with the word to look for
     * @return true if the word appears in the title, text, or keywords of the
     *         abstract. Otherwise, false
     */
    public boolean contains(String word) {
        int wordLen = word.length();
        for (int i = 0; i < this.keywords.length; i++) {
            if (this.keywords[i].equals(word))
                return true;
        }
        for (int i = 0; i < this.title.length() - wordLen + 1; i++) {
            if (this.title.substring(i, i + wordLen).equals(word))
                return true;
        }
        for (int i = 0; i < this.text.length() - wordLen + 1; i++) {
            if (this.text.substring(i, i + wordLen).equals(word))
                return true;
        }
        return false;
    }

    /**
     * Returns true if the passed-in word is one of this object's keywords.
     *
     * @param word String with word to compare
     * @return true if the passed-in word is oe of this object's keywords
     */
    public boolean isKeyword(String word) {
        for (int i = 0; i < keywords.length; i++) {
            if (word == keywords[i]) {
                return true;
            }
        }
        return false;
    }
}
