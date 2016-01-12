/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Object.Player;
import System.Database;
import System.Main;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Deelnemers extends javax.swing.JPanel {

    private ArrayList<Player> players;
    private final Database db = new Database();
    private final DefaultTableModel model;
    private int selectedId = 0;
    private String selectedName = "";
    private static JDialog frame;
    private final String NUMMER = "Nummer";
    private final String VOORNAAM = "Voornaam";
    private final String ACHTERNAAM = "Achternaam";
    private final String AANTAL_PUNTEN = "Aantal punten";
    private final String AANTAL_MARSEN = "Aantal marsen";
    private final String AANTAL_WEDSTRIJDEN = "Aantal wedstrijden";
    private final String OPLOPEND = "Oplopend";
    private final String AFLOPEND = "Aflopend";
    private final String DISC_FIRST_UP = "Gesorteerd op voornaam van a - z";
    private final String DISC_FIRST_DOWN = "Gesorteerd op voornaam van z - a";
    private final String DISC_LAST_UP = "Gesorteerd op achternaam van a - z";
    private final String DISC_LAST_DOWN = "Gesorteerd op achternaam van z - a";
    private final String DISC_POINTS_UP = "Gesorteerd op aantal punten van klein naar groot";
    private final String DISC_POINTS_DOWN = "Gesorteerd op aantal punten van groot naar klein";
    private final String DISC_MARS_UP = "Gesorteerd op aantal marsen van klein naar groot";
    private final String DISC_MARS_DOWN = "Gesorteerd op aantal marsen van groot naar klein";
    private final String DISC_GAMES_UP = "Gesorteerd op aantal wedstrijden van klein naar groot";
    private final String DISC_GAMES_DOWN = "Gesorteerd op aantal wedstrijden van groot naar klein";
    private final String DISC_ID_UP = "Gesorteerd op nummer van klein naar groot";
    private final String DISC_ID_DOWN = "Gesorteerd op nummer van groot naar klein";
    private String selected = NUMMER;
    private boolean oplopend = true;

    /**
     * Creates new form Deelnemers
     */
    public Deelnemers() {
        initComponents();
        tblPlayers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tblPlayers.getSelectedRow();
                    selectedId = (int) tblPlayers.getValueAt(row, 0);
                    selectedName = (String) tblPlayers.getValueAt(row, 1);
                    btnWijzig.setEnabled(true);
                    btnVerwijder.setEnabled(true);
                }
                if (e.getClickCount() == 2) {
                    editPlayer(selectedId);
                }
            }
        });
        lblDescription.setText(DISC_ID_UP);
        players = Main.getCurrentList();
        model = (DefaultTableModel) tblPlayers.getModel();
        buildTable();
    }

    private void buildTable() {
        model.setRowCount(0);
        for (Player p : players) {
            Object[] data = {p.getId(), p.getFullname(),
                p.getAmountOfPlayedGames(), p.getTotalPoints(), p.getTotalMars()};
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
            case VOORNAAM:
                if (oplopend) {
                    players.sort(new Player.PlayerFirstnameComparator());
                    lblDescription.setText(DISC_FIRST_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerFirstnameComparator()));
                    lblDescription.setText(DISC_FIRST_DOWN);
                }
                break;
            case ACHTERNAAM:
                if (oplopend) {
                    players.sort(new Player.PlayerLastnameComparator());
                    lblDescription.setText(DISC_LAST_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerLastnameComparator()));
                    lblDescription.setText(DISC_LAST_DOWN);
                }
                break;
            case AANTAL_PUNTEN:
                if (oplopend) {
                    players.sort(new Player.PlayerPointsComparator());
                    lblDescription.setText(DISC_POINTS_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerPointsComparator()));
                    lblDescription.setText(DISC_POINTS_DOWN);
                }
                break;
            case AANTAL_MARSEN:
                if (oplopend) {
                    players.sort(new Player.PlayerMarsComparator());
                    lblDescription.setText(DISC_MARS_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerMarsComparator()));
                    lblDescription.setText(DISC_MARS_DOWN);
                }
                break;
            case AANTAL_WEDSTRIJDEN:
                if (oplopend) {
                    players.sort(new Player.PlayerGamesComparator());
                    lblDescription.setText(DISC_GAMES_UP);
                } else {
                    players.sort(Collections.reverseOrder(new Player.PlayerGamesComparator()));
                    lblDescription.setText(DISC_GAMES_DOWN);
                }
                break;
        }
        buildTable();
    }

    private void addPlayer() {
        frame = new JDialog();
        frame.setModal(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sortList(selected, oplopend);
            }
        });
        frame.add(new PlayerPage(true, null));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void editPlayer(int id) {
        Player player = db.getPlayer(id);
        frame = new JDialog();
        frame.setModal(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sortList(selected, oplopend);
            }
        });
        frame.add(new PlayerPage(false, player));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void deletePlayer(int id, String name) {
        int option = JOptionPane.showOptionDialog(null, "Weet je zeker dat je deelnemer '"
                + name + "' wil verwijderen?", "Bevestig", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Ja", "Nee"}, "Nee");

        if (option == JOptionPane.YES_OPTION) {
            db.deletePlayer(id);
            JOptionPane.showMessageDialog(null, "Deelnemer '" + name + "' is verwijderd.",
                    "Succes", JOptionPane.INFORMATION_MESSAGE);
            sortList(selected, oplopend);
        }
    }

    public static void closeFrame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void print() {
        naarPDF();
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        paper.setSize(612.0, 832.0);
        double margin = 10;
        paper.setImageableArea(margin, margin, paper.getWidth() - margin, paper.getHeight() - margin);
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.PORTRAIT);

        try {
            PDDocument document = PDDocument.load("print.pdf");
            job.setPageable(new PDPageable(document, job));
            boolean print = job.printDialog();
            if(print){
                job.print();
            }
        } catch (IOException | IllegalArgumentException | PrinterException ex) {
            ex.printStackTrace();
        }
        File file = new File("print.pdf");
        file.delete();
    }

    private void naarPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("print.pdf"));
            document.open();
            addPDFTable(document);
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Voeg de tabel toe aan het pfd bestand
     *
     * @param doc document dat word aangemaakt
     */
    private void addPDFTable(Document doc) {
        try {
            PdfPTable table = new PdfPTable(5);
            table.setWidths(new float[]{1, 2f, 1.5f, 1, 1});
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell("Nummer");
            table.addCell("Naam");
            table.addCell("Aantal wedstrijden");
            table.addCell("Punten");
            table.addCell("Marsen");
            table.getDefaultCell().setBackgroundColor(null);

            for (int i = 0; i < tblPlayers.getRowCount(); i++) {
                PdfPCell id = new PdfPCell(new Phrase(tblPlayers.getValueAt(i, 0).toString()));
                PdfPCell naam = new PdfPCell(new Phrase(tblPlayers.getValueAt(i, 1).toString()));
                PdfPCell wedstrijden = new PdfPCell(new Phrase(tblPlayers.getValueAt(i, 2).toString()));
                PdfPCell punten = new PdfPCell(new Phrase(tblPlayers.getValueAt(i, 3).toString()));
                PdfPCell marsen = new PdfPCell(new Phrase(tblPlayers.getValueAt(i, 4).toString()));

                id.setFixedHeight(18f);
                naam.setFixedHeight(18f);
                wedstrijden.setFixedHeight(18f);
                punten.setFixedHeight(18f);
                marsen.setFixedHeight(18f);

                table.addCell(id);
                table.addCell(naam);
                table.addCell(wedstrijden);
                table.addCell(punten);
                table.addCell(marsen);
                if (i % 40 == 0 && i != 0) {
                    table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell("Nummer");
                    table.addCell("Naam");
                    table.addCell("Aantal wedstrijden");
                    table.addCell("Punten");
                    table.addCell("Marsen");
                    table.getDefaultCell().setBackgroundColor(null);
                }
            }
            doc.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();
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
        lblSorterenOp = new javax.swing.JLabel();
        cmbOptions = new javax.swing.JComboBox();
        cmbOpAf = new javax.swing.JComboBox();
        btnNieuw = new javax.swing.JButton();
        lblDescription = new javax.swing.JLabel();
        btnWijzig = new javax.swing.JButton();
        btnVerwijder = new javax.swing.JButton();
        btnPrinten = new javax.swing.JButton();

        tblPlayers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPlayers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nummer", "Naam", "Aantal wedstrijden", "Totaal punten", "Marsen"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPlayers.setRowHeight(30);
        tblPlayers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblPlayers);
        if (tblPlayers.getColumnModel().getColumnCount() > 0) {
            tblPlayers.getColumnModel().getColumn(0).setResizable(false);
            tblPlayers.getColumnModel().getColumn(2).setResizable(false);
            tblPlayers.getColumnModel().getColumn(3).setResizable(false);
            tblPlayers.getColumnModel().getColumn(4).setResizable(false);
        }

        lblSorterenOp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSorterenOp.setText("Sorteren op:");

        cmbOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { NUMMER, VOORNAAM, ACHTERNAAM, AANTAL_PUNTEN, AANTAL_MARSEN, AANTAL_WEDSTRIJDEN }));
        cmbOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOptionsActionPerformed(evt);
            }
        });

        cmbOpAf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { OPLOPEND, AFLOPEND }));
        cmbOpAf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOpAfActionPerformed(evt);
            }
        });

        btnNieuw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNieuw.setText("Nieuwe deelnemer");
        btnNieuw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNieuwActionPerformed(evt);
            }
        });

        lblDescription.setText("jLabel1");

        btnWijzig.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnWijzig.setText("Wijzig deelnemer");
        btnWijzig.setEnabled(false);
        btnWijzig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWijzigActionPerformed(evt);
            }
        });

        btnVerwijder.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVerwijder.setText("Verwijder deelnemer");
        btnVerwijder.setEnabled(false);
        btnVerwijder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerwijderActionPerformed(evt);
            }
        });

        btnPrinten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSorterenOp)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbOpAf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDescription)
                        .addGap(0, 817, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNieuw)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnVerwijder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnWijzig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPrinten))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE))
                        .addGap(35, 35, 35))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNieuw, btnPrinten, btnVerwijder, btnWijzig});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSorterenOp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOpAf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescription))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNieuw, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnWijzig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrinten, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerwijder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOptionsActionPerformed
        selected = cmbOptions.getSelectedItem().toString();
        if (selected.equals(VOORNAAM) || selected.equals(ACHTERNAAM) || selected.equals(NUMMER)) {
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

    private void btnNieuwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNieuwActionPerformed
        addPlayer();
    }//GEN-LAST:event_btnNieuwActionPerformed

    private void btnWijzigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWijzigActionPerformed
        editPlayer(selectedId);
    }//GEN-LAST:event_btnWijzigActionPerformed

    private void btnVerwijderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerwijderActionPerformed
        deletePlayer(selectedId, selectedName);
    }//GEN-LAST:event_btnVerwijderActionPerformed

    private void btnPrintenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintenActionPerformed
        print();
    }//GEN-LAST:event_btnPrintenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNieuw;
    private javax.swing.JButton btnPrinten;
    private javax.swing.JButton btnVerwijder;
    private javax.swing.JButton btnWijzig;
    private javax.swing.JComboBox cmbOpAf;
    private javax.swing.JComboBox cmbOptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblSorterenOp;
    private javax.swing.JTable tblPlayers;
    // End of variables declaration//GEN-END:variables
}
