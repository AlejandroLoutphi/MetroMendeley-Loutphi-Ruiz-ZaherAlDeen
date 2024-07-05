/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import datastructures.LinkedList;
import javax.swing.JFileChooser;

import java.io.File;
import javax.swing.*;


/**
 *
 * @author ayahzaheraldeen
 */
public class MainFrame extends javax.swing.JFrame {
    LinkedList<String>  ObjetosMostrar = new LinkedList<String>(); 
    String[] Prueba = (new String[] { "Titulo", "Autor", "Palabras Clave" });
    private Functions app;

    /**
     * Creates new form MainFrame
     *
     * @param app app object
     */
    public MainFrame(Functions app) {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.app = app;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        AddAbstract = new javax.swing.JButton();
        LoadAbstract = new javax.swing.JButton();
        InstructionsButton = new javax.swing.JButton();
        SearchHash = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        SearchedKeyword = new javax.swing.JLabel();
        SearchedWord = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        DisplayListButton = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Metro Mendeley");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        AddAbstract.setText("Agregar Resumen");
        jPanel1.add(AddAbstract, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        LoadAbstract.setText("Cargar Resumen");
        LoadAbstract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadAbstractActionPerformed(evt);
            }
        });
        jPanel1.add(LoadAbstract, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        InstructionsButton.setText("Instrucciones");
        jPanel1.add(InstructionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        SearchHash.setText("Buscar");
        SearchHash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchHashActionPerformed(evt);
            }
        });
        jPanel1.add(SearchHash, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        jLabel2.setText("Buscar Investigacion Por:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        SearchedKeyword.setText("Nombre del contenido");
        jPanel1.add(SearchedKeyword, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        SearchedWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchedWordActionPerformed(evt);
            }
        });
        jPanel1.add(SearchedWord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 90, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(Prueba));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        DisplayListButton.setText("Mostrar Investigaciones");
        DisplayListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayListButtonActionPerformed(evt);
            }
        });
        jPanel1.add(DisplayListButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoadAbstractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadAbstractActionPerformed
        // TODO add your handling code here:
         Functions functions = new Functions();
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if the file has .txt extension
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(this,
                        "Error: archivo debe ser del formato .txt",
                        "Error Cargando Archivo", JOptionPane.ERROR_MESSAGE);
                return; // Exit method if format is incorrect
            }

            // Call uploadFile method from Functions class
            try {
                app.uploadFile(selectedFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                        "Archivo Cargado Exitosamente!: " + selectedFile.getAbsolutePath(),
                        "Archivo Cargado Exitosamente", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error Cargando archivo", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Selecion de archivo cancelado.");
        }
    
       

    }//GEN-LAST:event_LoadAbstractActionPerformed

    private void SearchHashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchHashActionPerformed
        // TODO add your handling code here:
        
        String Palabra = SearchedWord.getText();
        
        System.out.println(Palabra);
        int count = 0;
        LinkedList<Investigation> text = new LinkedList();
        
        while (count < text.size()){
            System.out.println(text.get(count).getText());
        }
        
        
    }//GEN-LAST:event_SearchHashActionPerformed

    private void SearchedWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchedWordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchedWordActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void DisplayListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayListButtonActionPerformed
        // TODO add your handling code here:
        ShowInvesList displayFrame = new ShowInvesList();
        displayFrame.setVisible(true);

    }//GEN-LAST:event_DisplayListButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Functions functions = new Functions();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(new Functions()).setVisible(true);
            }
        });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddAbstract;
    private javax.swing.JButton DisplayListButton;
    private javax.swing.JButton InstructionsButton;
    private javax.swing.JButton LoadAbstract;
    private javax.swing.JButton SearchHash;
    private javax.swing.JLabel SearchedKeyword;
    private javax.swing.JTextField SearchedWord;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
