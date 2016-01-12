/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import System.Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Startpage extends javax.swing.JPanel {

    private static JDialog frame;

    /**
     * Creates new form Startpage
     */
    public Startpage() {
        initComponents();
        setLabel();
    }

    private void setLabel() {
        if (Main.getCurrentSeason() == null) {
            lblSeason.setText("Selecteer een seizoen");
            int x = (int) (getSize().getWidth() - lblSeason.getSize().getWidth()) / 2;
            lblSeason.setLocation(x, (int) lblSeason.getLocation().getY());
            btnDeelnemers.setEnabled(false);
            btnPunten.setEnabled(false);
            btnMarsen.setEnabled(false);
            btnSeason.setEnabled(false);
        } else {
            lblSeason.setText(Main.getCurrentSeason().getName());
            int x = (int) (getSize().getWidth() - lblSeason.getSize().getWidth()) / 2;
            lblSeason.setLocation(x, (int) lblSeason.getLocation().getY());
            btnDeelnemers.setEnabled(true);
            btnPunten.setEnabled(true);
            btnMarsen.setEnabled(true);
            btnSeason.setEnabled(true);
        }
    }

    private void selectSeason() {
        frame = new JDialog();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setLabel();
            }
        });
        frame.setModal(true);
        frame.add(new SeasonPicker());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void newSeason(){
        frame = new JDialog();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setLabel();
            }
        });
        frame.setModal(true);
        frame.add(new AddSeason());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void printOptions(){
        frame = new JDialog();
        frame.setModal(true);
        frame.add(new PrintPage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void closeFrame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDeelnemers = new javax.swing.JButton();
        btnPunten = new javax.swing.JButton();
        btnMarsen = new javax.swing.JButton();
        lblSeason = new javax.swing.JLabel();
        btnSeason = new javax.swing.JButton();
        btnNieuwSeizoen = new javax.swing.JButton();
        btnPrinten = new javax.swing.JButton();

        btnDeelnemers.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDeelnemers.setText("Deelnemers");
        btnDeelnemers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeelnemersActionPerformed(evt);
            }
        });

        btnPunten.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnPunten.setText("Punten overzicht");
        btnPunten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntenActionPerformed(evt);
            }
        });

        btnMarsen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnMarsen.setText("Marsen overzicht");
        btnMarsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarsenActionPerformed(evt);
            }
        });

        lblSeason.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSeason.setText("jLabel1");

        btnSeason.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnSeason.setText("Selecteer seizoen");
        btnSeason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeasonActionPerformed(evt);
            }
        });

        btnNieuwSeizoen.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNieuwSeizoen.setText("Nieuw seizoen");
        btnNieuwSeizoen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNieuwSeizoenActionPerformed(evt);
            }
        });

        btnPrinten.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnPrinten.setText("Printen");
        btnPrinten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPunten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDeelnemers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMarsen))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSeason)
                                .addComponent(btnNieuwSeizoen))
                            .addComponent(btnPrinten)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(lblSeason)))
                .addContainerGap(134, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeelnemers, btnMarsen, btnNieuwSeizoen, btnPrinten, btnPunten, btnSeason});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSeason)
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDeelnemers, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPunten, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSeason, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNieuwSeizoen, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMarsen, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrinten, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(152, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeelnemers, btnMarsen, btnNieuwSeizoen, btnPrinten, btnPunten, btnSeason});

    }// </editor-fold>//GEN-END:initComponents

    private void btnDeelnemersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeelnemersActionPerformed
        Main.setPanel(new Deelnemers(), true);
    }//GEN-LAST:event_btnDeelnemersActionPerformed

    private void btnPuntenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntenActionPerformed
        Main.setPanel(new Punten(), true);
    }//GEN-LAST:event_btnPuntenActionPerformed

    private void btnMarsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarsenActionPerformed
        Main.setPanel(new Marsen(), true);
    }//GEN-LAST:event_btnMarsenActionPerformed

    private void btnSeasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeasonActionPerformed
        selectSeason();
    }//GEN-LAST:event_btnSeasonActionPerformed

    private void btnNieuwSeizoenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNieuwSeizoenActionPerformed
        newSeason();
    }//GEN-LAST:event_btnNieuwSeizoenActionPerformed

    private void btnPrintenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintenActionPerformed
        printOptions();
    }//GEN-LAST:event_btnPrintenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeelnemers;
    private javax.swing.JButton btnMarsen;
    private javax.swing.JButton btnNieuwSeizoen;
    private javax.swing.JButton btnPrinten;
    private javax.swing.JButton btnPunten;
    private javax.swing.JButton btnSeason;
    private javax.swing.JLabel lblSeason;
    // End of variables declaration//GEN-END:variables
}
