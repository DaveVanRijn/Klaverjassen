/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Object.Player;
import Object.Season;
import View.Startpage;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Main extends javax.swing.JFrame {

    private static Main main;
    private final Stack<JPanel> panels;
    private static ArrayList<Season> seasons;
    private static Season currentSeason;
//    private final static File datafile = new File(System.getProperty("user.home")
//            + File.separator + "Google Drive" + File.separator + "KlaverjassenData.dat");
    private final static File datafile = new File("KlaverjassenData.dat");
//    private final static File logfile = new File(System.getProperty("user.home")
//            + File.separator + "Google Drive" + File.separator + "log.txt");
    private final static File logfile = new File("log.txt");
    private static PrintWriter logger;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                writeData();
            }
        });
        try {
            if (!datafile.createNewFile()) {
                readData();
            } else {
                seasons = new ArrayList<>();
                writeData();
            }
            logfile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

//        Database db = new Database(true);
//        db.databaseToFile();
        setTitle("Klaverjassen Dave");
        panels = new Stack<>();
        pnlMain.setLayout(new BorderLayout());
        JPanel panel = new Startpage();
        pnlMain.add(panel, BorderLayout.CENTER);
        panels.push(panel);
        btnBack.setEnabled(false);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void setPanel(Object o, boolean push) {
        JPanel panel = (JPanel) o;
        if (push) {
            main.panels.push(panel);
        }
        main.pnlMain.removeAll();
        main.pnlMain.add(panel, BorderLayout.CENTER);
        if (panel instanceof Startpage) {
            main.btnBack.setEnabled(false);
        } else {
            main.btnBack.setEnabled(true);
        }
        main.setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    public static void setPrevPanel() {
        if (!main.panels.isEmpty()) {
            main.panels.pop();
            setPanel(main.panels.pop(), true);
            main.pack();
            main.setLocationRelativeTo(null);
        }
    }

    private static void readData() {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(datafile));
            seasons = (ArrayList<Season>) input.readObject();
            if (!seasons.isEmpty()) {
                Collections.sort(seasons, Collections.reverseOrder(new Season.SeasonDateComparator()));
                currentSeason = seasons.get(0);
            }
            input.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeData() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(datafile));
            output.writeObject(seasons);
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void log(String message) {
        try {
            logger = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateForm = new SimpleDateFormat("EE dd-MM-yyyy HH:mm:ss");
            logger.println(dateForm.format(date) + " " + message);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (logger != null) {
                logger.close();
            }
        }
    }

    public static ArrayList<Player> getCurrentList() {
        return currentSeason.getPlayers();
    }

    public static void setCurrentSeason(int id) {
        for (Season s : seasons) {
            if (s.getId() == id) {
                currentSeason = s;
                s.setEdited();
                Collections.sort(seasons, Collections.reverseOrder(new Season.SeasonDateComparator()));
                return;
            }
        }
    }

    public static ArrayList<Season> getSeasons() {
        return seasons;
    }

    public static void addSeason(Season season) {
        seasons.add(season);
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }

    public static void printSpeelSchema() {
        naarPDF(true);
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

    public static void printTable() {
        naarPDF(false);
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

    private static void naarPDF(boolean speelschema) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("print.pdf"));
            document.open();
            if (!speelschema) {
                addPDFTable(document);
            } else {
                int tabellen = currentSeason.getPlayers().size() / 4;
                if (currentSeason.getPlayers().size() % 4 > 0) {
                    tabellen++;
                }
                int startplayer = 0;
                for (int i = 0; i < tabellen; i++) {
                    document.add(addSpeelTable(startplayer));
                    document.add(new Phrase("\n"));
                    startplayer += 4;
                }
                document.close();
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Voeg de tabel toe aan het pfd bestand
     *
     * @param doc document dat word aangemaakt
     */
    private static void addPDFTable(Document doc) {
        try {
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new float[]{2.5f, 1, 1, 1, 1, 1});
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell("De Merel 2015/2016\n\n");
            table.addCell("Week 1");
            table.addCell("Week 2");
            table.addCell("Week 3");
            table.addCell("Week 4");
            table.addCell("Week 5");
            table.getDefaultCell().setBackgroundColor(null);

            List<Player> players = getCurrentList();
            Collections.sort(players, new Player.PlayerIdComparator());
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                PdfPCell id = new PdfPCell(new Phrase(player.getId() + ") " + player.getFullname()));
                PdfPCell kolom1 = new PdfPCell(new Phrase());
                PdfPCell kolom2 = new PdfPCell(new Phrase());
                PdfPCell kolom3 = new PdfPCell(new Phrase());
                PdfPCell kolom4 = new PdfPCell(new Phrase());
                PdfPCell kolom5 = new PdfPCell(new Phrase());

                id.setFixedHeight(18f);
                kolom1.setFixedHeight(18f);
                kolom2.setFixedHeight(18f);
                kolom3.setFixedHeight(18f);
                kolom4.setFixedHeight(18f);
                kolom5.setFixedHeight(18f);

                table.addCell(id);
                table.addCell(kolom1);
                table.addCell(kolom2);
                table.addCell(kolom3);
                table.addCell(kolom4);
                table.addCell(kolom5);
                if (i % 40 == 0 && i != 0) {
                    table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell("De Merel 2015/2016\n\n");
                    table.addCell("Week 1");
                    table.addCell("Week 2");
                    table.addCell("Week 3");
                    table.addCell("Week 4");
                    table.addCell("Week 5");
                    table.getDefaultCell().setBackgroundColor(null);
                }
            }
            table.setKeepTogether(true);
            doc.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();
    }

    /**
     * Voeg de tabel toe aan het pfd bestand
     *
     * @param doc document dat word aangemaakt
     */
    private static PdfPTable addSpeelTable(int startPlayer) {
        try {
            List<Player> players = Main.getCurrentList();
            Collections.sort(players, new Player.PlayerIdComparator());
            PdfPTable table = new PdfPTable(5);
            table.setWidths(new float[]{0.5f, 2.5f, 2.5f, 2.5f, 2.5f});
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell("");
            int count = 0;
            for (int i = startPlayer; i < startPlayer + 4 && i < currentSeason.getPlayers().size(); i++) {
                table.addCell(players.get(i).getId() + ") " + players.get(i).getFullname());
                count++;
            }
            int rest;
            if ((rest = count - 4) < 0) {
                rest = rest * -1;
                for (int i = 0; i < rest; i++) {
                    table.addCell(new Phrase());
                }
            }
            table.getDefaultCell().setBackgroundColor(null);

            PdfPCell kolom1 = new PdfPCell(new Phrase());
            kolom1.setFixedHeight(25f);
            for (int i = 0; i < 4; i++) {
                table.addCell(Integer.toString(i + 1));
                table.addCell(kolom1);
                table.addCell(kolom1);
                table.addCell(kolom1);
                table.addCell(kolom1);
            }
            table.addCell("T");
            table.addCell(kolom1);
            table.addCell(kolom1);
            table.addCell(kolom1);
            table.addCell(kolom1);

            table.setKeepTogether(true);
            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        pnlHeader = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setText("Terug");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(321, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addComponent(btnBack))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        setPrevPanel();
    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                main = new Main();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
