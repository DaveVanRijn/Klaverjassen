/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Object.Player;
import System.Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Marsen extends javax.swing.JPanel {

    private static JDialog frame;
    private final DefaultTableModel model;
    private ArrayList<Player> players;
    private final String OPLOPEND = "Oplopend";
    private final String AFLOPEND = "Aflopend";
    private final String NUMMER = "Nummer";
    private final String FIRSTNAME = "Voornaam";
    private final String LASTNAME = "Achternaam";
    private final String MARS = "Totaal marsen";
    private final String DISC_ID_UP = "Gesorteerd op nummer van klein naar groot";
    private final String DISC_ID_DOWN = "Gesorteerd op nummer van groot naar klein";
    private final String DISC_FIRST_UP = "Gesorteerd op voornaam van a - z";
    private final String DISC_FIRST_DOWN = "Gesorteerd op voornaam van z - a";
    private final String DISC_LAST_UP = "Gesorteerd op achternaam van a - z";
    private final String DISC_LAST_DOWN = "Gesorteerd op achternaam van z - a";
    private final String DISC_MARS_UP = "Gesorteerd op aantal marsen van klein naar groot";
    private final String DISC_MARS_DOWN = "Gesorteerd op aantal marsen van groot naar klein";
    private String selected = NUMMER;
    private boolean oplopend = true;

    /**
     * Creates new form Punten
     */
    public Marsen() {
        initComponents();
        tblPlayers.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblPlayers.getColumnModel().getColumn(1).setPreferredWidth(155);
        tblPlayers.getColumnModel().getColumn(2).setPreferredWidth(100);
        model = (DefaultTableModel) tblPlayers.getModel();
        players = Main.getCurrentList();
        buildTable();
        lblDescription.setText(DISC_ID_UP);
    }

    private void buildTable() {
        model.setRowCount(0);
        for (Player p : players) {
            Object[] data = new Object[29];
            data[0] = p.getId();
            data[1] = p.getFullname();
            data[2] = p.getTotalMars();
            for(int i = 0; i < 26; i++){
                int j = i + 3;
                data[j] = p.getMars()[i];
            }
            model.addRow(data);
        }
    }
    
    private void sortList(String selected, boolean oplopend) {
        players = Main.getCurrentList();
        switch (selected) {
            case NUMMER:
                if (oplopend) {
                    players.sort(new Player.PlayerIdComparator());
                    lblDescription.setText(DISC_ID_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerIdComparator()));
                    lblDescription.setText(DISC_ID_DOWN);
                }
                break;
            case FIRSTNAME:
                if (oplopend) {
                    players.sort(new Player.PlayerFirstnameComparator());
                    lblDescription.setText(DISC_FIRST_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerFirstnameComparator()));
                    lblDescription.setText(DISC_FIRST_DOWN);
                }
                break;
            case LASTNAME:
                if (oplopend) {
                    players.sort(new Player.PlayerLastnameComparator());
                    lblDescription.setText(DISC_LAST_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerLastnameComparator()));
                    lblDescription.setText(DISC_LAST_DOWN);
                }
                break;
            case MARS:
                if (oplopend) {
                    players.sort(new Player.PlayerMarsComparator());
                    lblDescription.setText(DISC_MARS_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerMarsComparator()));
                    lblDescription.setText(DISC_MARS_DOWN);
                }
                break;
        }
        buildTable();
    }
    
    private void addEdit(){
        frame = new JDialog();
        frame.setModal(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sortList(selected, oplopend);
            }
        });
        frame.add(new AddScores(false));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void closeFrame(){
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlayers = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cmbOptions = new javax.swing.JComboBox();
        cmbOpAf = new javax.swing.JComboBox();
        lblDescription = new javax.swing.JLabel();
        btnNewEdit = new javax.swing.JButton();
        btnPunten = new javax.swing.JButton();

        tblPlayers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPlayers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nummer", "Naam", "Totaal marsen", "Week 1", "Week 2", "Week 3", "Week 4", "Week 5 ", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10", "Week 11", "Week 12", "Week 13", "Week 14", "Week 15", "Week 16", "Week 17", "Week 18", "Week 19", "Week 20", "Week 21", "Week 22", "Week 23", "Week 24", "Week 25", "Week 26"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPlayers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPlayers.setRowHeight(30);
        tblPlayers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblPlayers);
        if (tblPlayers.getColumnModel().getColumnCount() > 0) {
            tblPlayers.getColumnModel().getColumn(0).setResizable(false);
            tblPlayers.getColumnModel().getColumn(2).setResizable(false);
            tblPlayers.getColumnModel().getColumn(3).setResizable(false);
            tblPlayers.getColumnModel().getColumn(4).setResizable(false);
            tblPlayers.getColumnModel().getColumn(5).setResizable(false);
            tblPlayers.getColumnModel().getColumn(6).setResizable(false);
            tblPlayers.getColumnModel().getColumn(7).setResizable(false);
            tblPlayers.getColumnModel().getColumn(8).setResizable(false);
            tblPlayers.getColumnModel().getColumn(9).setResizable(false);
            tblPlayers.getColumnModel().getColumn(10).setResizable(false);
            tblPlayers.getColumnModel().getColumn(11).setResizable(false);
            tblPlayers.getColumnModel().getColumn(12).setResizable(false);
            tblPlayers.getColumnModel().getColumn(13).setResizable(false);
            tblPlayers.getColumnModel().getColumn(14).setResizable(false);
            tblPlayers.getColumnModel().getColumn(15).setResizable(false);
            tblPlayers.getColumnModel().getColumn(16).setResizable(false);
            tblPlayers.getColumnModel().getColumn(17).setResizable(false);
            tblPlayers.getColumnModel().getColumn(18).setResizable(false);
            tblPlayers.getColumnModel().getColumn(19).setResizable(false);
            tblPlayers.getColumnModel().getColumn(20).setResizable(false);
            tblPlayers.getColumnModel().getColumn(21).setResizable(false);
            tblPlayers.getColumnModel().getColumn(22).setResizable(false);
            tblPlayers.getColumnModel().getColumn(23).setResizable(false);
            tblPlayers.getColumnModel().getColumn(24).setResizable(false);
            tblPlayers.getColumnModel().getColumn(25).setResizable(false);
            tblPlayers.getColumnModel().getColumn(26).setResizable(false);
            tblPlayers.getColumnModel().getColumn(27).setResizable(false);
            tblPlayers.getColumnModel().getColumn(28).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Sorteren op:");

        cmbOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { NUMMER, FIRSTNAME, LASTNAME, MARS }));
        cmbOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOptionsActionPerformed(evt);
            }
        });

        cmbOpAf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { OPLOPEND, AFLOPEND}));
        cmbOpAf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOpAfActionPerformed(evt);
            }
        });

        lblDescription.setText("jLabel2");

        btnNewEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewEdit.setText("Marsen toevoegen/wijzigen");
        btnNewEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewEditActionPerformed(evt);
            }
        });

        btnPunten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPunten.setText("Naar punten");
        btnPunten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNewEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnPunten))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbOpAf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDescription)))
                        .addGap(0, 522, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNewEdit, btnPunten});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOpAf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescription))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPunten, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewEditActionPerformed
        addEdit();
    }//GEN-LAST:event_btnNewEditActionPerformed

    private void cmbOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOptionsActionPerformed
        selected = cmbOptions.getSelectedItem().toString();
        if (selected.equals(FIRSTNAME) || selected.equals(LASTNAME) || selected.equals(NUMMER)) {
            cmbOpAf.setSelectedIndex(0);
        } else {
            cmbOpAf.setSelectedIndex(1);
        }
        oplopend = (cmbOpAf.getSelectedItem().toString().equals(OPLOPEND));
        sortList(selected, oplopend);
    }//GEN-LAST:event_cmbOptionsActionPerformed

    private void cmbOpAfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOpAfActionPerformed
        oplopend = (cmbOpAf.getSelectedItem().toString().equals(OPLOPEND));
        selected = cmbOptions.getSelectedItem().toString();
        sortList(selected, oplopend);
    }//GEN-LAST:event_cmbOpAfActionPerformed

    private void btnPuntenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntenActionPerformed
        Main.setPanel(new Punten(), false);
    }//GEN-LAST:event_btnPuntenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewEdit;
    private javax.swing.JButton btnPunten;
    private javax.swing.JComboBox cmbOpAf;
    private javax.swing.JComboBox cmbOptions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JTable tblPlayers;
    // End of variables declaration//GEN-END:variables
}
