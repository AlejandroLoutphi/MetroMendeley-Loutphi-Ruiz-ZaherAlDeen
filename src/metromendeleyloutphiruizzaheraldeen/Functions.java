/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ayahzaheraldeen
 */
public class Functions {


    public static void main(String[] args) {
        // Define the filename
        String fileName = "Investigations.txt";
        
        // Check if the file exists in the current directory
        File file = new File(fileName);
        


        
        if (file.exists()) {
            // File exists, attempt to open and read it
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Process each line as needed
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            // File does not exist, print an error message
            System.err.println("Error: " + fileName + " not found in the current directory.");
        }
    }
}

}
    
   