/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import System.Database;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Player implements Comparable, Serializable{

    private int id;
    private String firstname;
    private String infix;
    private String lastname;
    private int[] points;
    private int[] mars;

    public Player(String firstname, String infix, String lastname) {
        this.firstname = firstname;
        this.infix = infix;
        this.lastname = lastname;
        points = new int[26];
        mars = new int[26];

        for (int i = 0; i < 26; i++) {
            points[i] = 0;
            mars[i] = 0;
        }
    }

    public Player(String firstname, String infix, String lastname, int[] points,
            int[] mars) {
        this.firstname = firstname;
        this.infix = infix;
        this.lastname = lastname;
        this.points = points;
        this.mars = mars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public int[] getMars() {
        return mars;
    }

    public void setMars(int[] mars) {
        this.mars = mars;
    }

    public int getTotalPoints() {
        int total = 0;
        for (int p : points) {
            total += p;
        }
        return total;
    }

    public int getTotalMars() {
        int total = 0;
        for (int m : mars) {
            total += m;
        }
        return total;
    }

    public int getAmountOfPlayedGames() {
        int games = 0;
        for (int p : points) {
            if (p > 0) {
                games++;
            }
        }
        return games;
    }

    public String getFullname() {
        String full = firstname + " ";
        full += (infix == null) ? "" : infix + " ";
        full += lastname;
        return full;
    }

    public void save() {
        Database db = new Database();
        db.savePlayer(this);
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class PlayerIdComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return (p1.getId() - p2.getId());
        }

    }

    public static class PlayerPointsComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return (p1.getTotalPoints() - p2.getTotalPoints());
        }

    }

    public static class PlayerFirstnameComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return (p1.getFirstname().compareTo(p2.getFirstname()));
        }

    }

    public static class PlayerLastnameComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return (p1.getLastname().compareTo(p2.getLastname()));
        }

    }

    public static class PlayerMarsComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return (p1.getTotalMars() - p2.getTotalMars());
        }

    }

    public static class PlayerGamesComparator implements Comparator<Player> {

        @Override
        public int compare(Player p1, Player p2) {
            return(p1.getAmountOfPlayedGames() - p2.getAmountOfPlayedGames());
        }

    }
}
