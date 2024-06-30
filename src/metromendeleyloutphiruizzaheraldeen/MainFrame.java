/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package metromendeleyloutphiruizzaheraldeen;

import javax.swing.JFileChooser;

import java.io.File;
import javax.swing.*;
import java.io.File;


/**
 *
 * @author ayahzaheraldeen
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        AddAbstract = new javax.swing.JButton();
        LoadAbstract = new javax.swing.JButton();
        InstructionsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Metro Mendeley");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        AddAbstract.setText("Agregar Resumen");
        jPanel1.add(AddAbstract, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        LoadAbstract.setText("Cargar Resumen");
        LoadAbstract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadAbstractActionPerformed(evt);
            }
        });
        jPanel1.add(LoadAbstract, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, -1, -1));

        InstructionsButton.setText("Instrucciones");
        jPanel1.add(InstructionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoadAbstractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadAbstractActionPerformed
        // TODO add your handling code here:
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
                Functions.uploadFile(selectedFile.getAbsolutePath());
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddAbstract;
    private javax.swing.JButton InstructionsButton;
    private javax.swing.JButton LoadAbstract;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
