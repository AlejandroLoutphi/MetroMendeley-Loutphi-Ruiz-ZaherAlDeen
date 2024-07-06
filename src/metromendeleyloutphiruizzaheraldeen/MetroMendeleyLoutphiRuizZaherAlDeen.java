/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Main class for the MetroMendeleyLoutphiRuizZaherAlDeen project.
 *
 * @author Alejandro Loutphi
 */
public class MetroMendeleyLoutphiRuizZaherAlDeen {
            Functions app = new Functions();
    /**
     * Main function for MetroMendeleyLoutphiRuizZaherAlDeen project.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Functions app = new Functions();
        try {
            app.buildHashTables();
            MainFrame mainFrame = new MainFrame(app);
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: archivo de input \"" + Functions.INPUT_FILE_NAME + "\" no existe", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer datos del archivo de input \"" + Functions.INPUT_FILE_NAME + "\"", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
