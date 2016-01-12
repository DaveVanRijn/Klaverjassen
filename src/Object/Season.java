/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import System.Main;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Dave van Rijn, Student 500714558, Klas IS202
 */
public class Season implements Serializable, Comparable {

    private final String name;
    private int id;
    private final ArrayList<Player> players;
    private Date lastEdited;
    private final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public Season(String name) {
        this.name = name;
        players = new ArrayList<>();
        int someId = 1;
        for (Season s : Main.getSeasons()) {
            if (s.getId() >= this.id) {
                someId += 1;
            }
        }
        this.id = someId;
        lastEdited = Calendar.getInstance().getTime();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEdited() {
        lastEdited = Calendar.getInstance().getTime();
    }
    
    public Date getLastEdited(){
        return lastEdited;
    }

    public String getLastEditedString() {
        return format.format(lastEdited);
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class SeasonDateComparator implements Comparator<Season> {

        @Override
        public int compare(Season s1, Season s2) {
            return(s1.getLastEdited().compareTo(s2.getLastEdited()));
        }

    }
}
