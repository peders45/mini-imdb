package app;

import java.sql.*;
import java.util.*;

public class TextCtrl extends DBconn{
	
	ArrayList<String> textIDs = new ArrayList<String>();
	ArrayList<String> textAttributes = new ArrayList<String>();

    private PreparedStatement regStatement;

    public void prepareText() {
        try { 
            regStatement = conn.prepareStatement("INSERT INTO Text (TypeOfText, TextContent, Rating, UserID, SeriesID, MovieID) VALUES ( (?), (?), (?), (?), (?), (?))"); 
        } catch (Exception e) { 
            System.out.println("db error during prepare of insert into Text");
        }
    }
    
    public void regTekst(int seriesID, int movieID, int userID, String textContent, int rating) {
        try {
            regStatement.setString(1, "Review");
            regStatement.setString(2, textContent);
            regStatement.setInt(3, rating);
            regStatement.setInt(4, userID);
            regStatement.setInt(5, seriesID);
            regStatement.setInt(6, movieID);
            regStatement.execute();
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("db error during insert of Text textid= 'LAST_INSERT_ID()'");
        }
        
        // clear lists for new insertion
        textIDs.clear();
        textAttributes.clear();
    }
}
