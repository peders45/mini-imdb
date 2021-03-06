package app;

import java.sql.*;

public class Music extends ActiveDomainObject {
	
	private int musicID;
	private String composedBy;
	private String performedBy;
	
	private static final int NOID = -1;
	
	// constructor for object with new music
	public Music(String composedBy, String performedBy) {
		this.musicID = NOID;
		this.composedBy = composedBy;
		this.performedBy = performedBy;
	}
	
	// constructor for object with existing music
	public Music(int musicID) {
		this.musicID = musicID;
	}
	
	public int getMusicID() {
		return this.musicID;
	}
	
	public void setMusicID(int musicID) {
		this.musicID = musicID;
	}
	
	@Override
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ComposedBy, PerformedBy FROM Music WHERE MusicID=" + musicID);
            while (rs.next()) {
            	composedBy =  rs.getString("ComposedBy");
            	performedBy =  rs.getString("PerformedBy");
            }

        } catch (Exception e) {
            System.out.println("db error during select of music= "+e);
            return;
        }
	}
	@Override
	public void refresh(Connection conn) {
		initialize (conn);
	}
	@Override
	public void save(Connection conn) {
		try {    
            Statement stmt = conn.createStatement(); 
            stmt.executeUpdate("INSERT INTO Music (ComposedBy, PerformedBy) VALUES "
            		+ "('"+composedBy+"','"+performedBy+")", stmt.RETURN_GENERATED_KEYS);
            
            // update object's id to autogenerated id in database
        	ResultSet generatedKeys = stmt.getGeneratedKeys();
        	if (generatedKeys.next()) {
        		this.setMusicID((generatedKeys.getInt(1)));
        	} 
            
        } catch (Exception e) {
            System.out.println("db error during insert of music="+e);
            return;
        }
		
	}

}
