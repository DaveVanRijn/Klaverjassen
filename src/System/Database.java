/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Object.Player;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Database {

    private java.sql.Connection connection;
    String sql;

    /**
     * Create new instance of database
     */
    public Database() {
        
    }
    
    /**
     * Create new connection with database
     * @param connect
     */
    public Database(boolean connect) {
        this();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/klaverjassen"
                    + "?zeroDateTimeBehavior=convertToNull",
                    "root", "root"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePlayer(Player player) {
//        try {
//            sql = "SELECT MAX(`id`) FROM `player`";
//            Statement stat = connection.createStatement();
//            ResultSet rs = stat.executeQuery(sql);
//            int id;
//            if (rs.next()) {
//                id = rs.getInt(1);
//                id++;
//            } else {
//                id = 1;
//            }
//            p.setId(id);
//            sql = "INSERT INTO `player` VALUES (?, ?, ?, ?";
//            for (int i = 0; i < 26; i++) {
//                sql += ", " + p.getPoints()[i];
//            }
//            for (int i = 0; i < 26; i++) {
//                sql += ", " + p.getMars()[i];
//            }
//            sql += ");";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, p.getId());
//            stmt.setString(2, p.getFirstname());
//            stmt.setString(3, p.getInfix());
//            stmt.setString(4, p.getLastname());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        List<Player> list = Main.getCurrentList();
        int id = 1;
        for(Player p : list){
            if(p.getId() >= id) id++;
        }
        player.setId(id);
        Main.getCurrentList().add(player);
        Main.writeData();
        Main.log("Toegevoegd: #" + player.getId() + ", " + player.getFullname());
    }

    public Player getPlayer(int id) {
//        try {
//            sql = "SELECT * FROM `player` WHERE `id` = ?;";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, id);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                int[] points = new int[26];
//                int[] mars = new int[26];
//                String firstname = rs.getString(2);
//                String infix = rs.getString(3);
//                String lastname = rs.getString(4);
//                int j = 5;
//                for (int i = 0; i < 26; i++) {
//                    points[i] = rs.getInt(j++);
//                }
//                for (int i = 0; i < 26; i++) {
//                    mars[i] = rs.getInt(j++);
//                }
//                Player p = new Player(firstname, infix, lastname, points, mars);
//                p.setId(id);
//                return p;
//            }
//            return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
        List<Player> list = Main.getCurrentList();
        for(Player p : list){
            if(p.getId() == id) return p;
        }
        return null;
    }
    
    public ArrayList<Player> getPlayers(){
//        ArrayList<Player> players = new ArrayList<>();
//        try{
//            sql = "SELECT * FROM `player`";
//            Statement stat = connection.createStatement();
//            ResultSet rs = stat.executeQuery(sql);
//            
//            while(rs.next()){
//                int[] points = new int[26];
//                int[] mars = new int[26];
//                int id = rs.getInt(1);
//                String firstname = rs.getString(2);
//                String infix = rs.getString(3);
//                String lastname = rs.getString(4);
//                int j = 5;
//                for (int i = 0; i < 26; i++) {
//                    points[i] = rs.getInt(j++);
//                }
//                for (int i = 0; i < 26; i++) {
//                    mars[i] = rs.getInt(j++);
//                }
//                Player p = new Player(firstname, infix, lastname, points, mars);
//                p.setId(id);
//                players.add(p);
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return players;
        return Main.getCurrentList();
    }

    public void updatePlayer(Player player) {
//        try {
//            sql = "UPDATE `player` SET `firstname` = ?, `infix` = ?, "
//                    + "`lastname` = ? WHERE `id` = ?;";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setString(1, p.getFirstname());
//            stmt.setString(2, p.getInfix());
//            stmt.setString(3, p.getLastname());
//            stmt.setInt(4, p.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        List<Player> list = Main.getCurrentList();
        for(Player p : list){
            if(p.getId() == player.getId()){
                p.setFirstname(player.getFirstname());
                p.setInfix(player.getInfix());
                p.setLastname(player.getLastname());
                p.setMars(player.getMars());
                p.setPoints(player.getPoints());
                break;
            }
        }
        Main.writeData();
        Main.log("Bijgewerkt: #" + player.getId() + ", " + player.getFullname() 
                + ", Totaal punten " + player.getTotalPoints() + ", Aantal marsen " 
                + player.getTotalMars());
    }

    public void deletePlayer(int id) {
//        try {
//            sql = "DELETE FROM `player` WHERE `id` = ?;";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//            
//            sql = "UPDATE `player` SET `id` = `id` - 1 WHERE `id` > ?";
//            stmt.clearBatch();
//            stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Player player = getPlayer(id);
        Main.getCurrentList().remove(player);
        for(Player p : Main.getCurrentList()){
            if(p.getId() > id){
                p.setId(p.getId() - 1);
            }
        }
        Main.writeData();
        Main.log("Verwijderd: #" + id + ", " + player.getFullname());
    }
    
    public void setScores(int id, int week, int score, int mars){
//        try{
//            sql = "UPDATE `player` SET`pweek" + week + "` = ?, `mweek" + week 
//                    + "` = ? WHERE `id` = ?;";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, score);
//            stmt.setInt(2, mars);
//            stmt.setInt(3, id);
//            stmt.executeUpdate();
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
        List<Player> list = Main.getCurrentList();
        for(Player p : list){
            if(p.getId() == id){
                p.getPoints()[week - 1] = score;
                p.getMars()[week - 1] = mars;
                return;
            }
        }
        Main.writeData();
    }
    
    public void databaseToFile(){
        Main.getCurrentList().clear();
        try{
            sql = "SELECT * FROM `player`;";
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                int[] points = new int[26];
                int[] mars = new int[26];
                int id = rs.getInt(1);
                String firstname = rs.getString(2);
                String infix = rs.getString(3);
                String lastname = rs.getString(4);
                int j = 5;
                for (int i = 0; i < 26; i++) {
                    points[i] = rs.getInt(j++);
                }
                for (int i = 0; i < 26; i++) {
                    mars[i] = rs.getInt(j++);
                }
                Player p = new Player(firstname, infix, lastname, points, mars);
                p.setId(id);
               Main.getCurrentList().add(p);
            }
            Main.writeData();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
