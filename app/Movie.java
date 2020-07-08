package app;

import java.sql.*;
import java.util.*;

public class Movie extends ActiveDomainObject{
	
	private int movieID;
	private String title;
	private String length;
	private String storyline;
	private String relaseDate;
	private Series series;
	private int season;
	private int episode;
	
	private ArrayList<Person> directors = new ArrayList<Person>(); 
	private ArrayList<Person> screenwriters  = new ArrayList<Person>(); 
	List<List<Object>> listOfActorsAndRoles = new ArrayList<List<Object>>();
	private ArrayList<Category> categories  = new ArrayList<Category>();
	private ArrayList<Music> music  = new ArrayList<Music>();
	List<List<Object>> listOfPublishingCompaniesWithReleaseYearAndPlatform = new ArrayList<List<Object>>();
//	private ArrayList<Text> texts;
	
	private static final int NOID = -1;
	private String query;
	
	public Movie(String title, String length, String storyline, String releaseDate) {
        this.movieID = NOID;
        this.title = title;
        this.length = length;
        this.storyline = storyline;
        this.relaseDate = releaseDate;
    }
	
    public void regDirector(int directorID, Connection conn) {
        Person p = new Person (directorID);
        p.initialize (conn);
        directors.add(p);
    }
    public void newDirector(String name, String birthYear, String birthCountry, Connection conn) {
    	Person p = new Person (name, birthYear, birthCountry);
        p.save (conn);
        directors.add(p);
    }
    
    public void regScreenwriter(int screenwriterID, Connection conn) {
        Person p = new Person (screenwriterID);
        p.initialize (conn);
        screenwriters.add(p);
    }
    public void newScreenwriter(String name, String birthYear, String birthCountry, Connection conn) {
    	Person p = new Person (name, birthYear, birthCountry);
        p.save (conn);
        screenwriters.add(p);
    }
    
    public void regActor(int actorID, String role, Connection conn) {
        Person p = new Person (actorID);
        p.initialize (conn);
        listOfActorsAndRoles.add(new ArrayList<Object>(Arrays.asList(p.getPersonID(), role)));
    }
    public void newActor(String name, String birthYear, String birthCountry, String role, Connection conn) {
    	Person p = new Person (name, birthYear, birthCountry);
        p.save (conn);
        listOfActorsAndRoles.add(new ArrayList<Object>(Arrays.asList(p.getPersonID(), role)));
    }
    
    public void regCompany(int companyID, String releaseYear, String platform, Connection conn) {
        PublishingCompany u = new PublishingCompany (companyID);
        u.initialize (conn);
        listOfPublishingCompaniesWithReleaseYearAndPlatform.add(new ArrayList<Object>(Arrays.asList(u.getCompanyID(), releaseYear, platform)));
    }
    public void newCompany(String url, String address, String country, String releaseYear, String platform, Connection conn) {
    	PublishingCompany u = new PublishingCompany (url, address, country);
        u.save (conn);
        listOfPublishingCompaniesWithReleaseYearAndPlatform.add(new ArrayList<Object>(Arrays.asList(u.getCompanyID(), releaseYear, platform)));
    }
    
    public void regMusic(int musicID, Connection conn) {
    	Music m = new Music (musicID);
    	m.initialize (conn);
    	music.add(m);
    }
    public void newMusic(String composedBy, String performedBy, Connection conn) {
    	Music m = new Music (composedBy, performedBy);
    	m.initialize (conn);
    	music.add(m);
    }

    public void regCategory(int categoryID, Connection conn) {
    	Category k = new Category (categoryID);
    	k.initialize (conn);
    	categories.add(k);
    }
    public void newCategory(String name, Connection conn) {
    	Category k = new Category (name);
    	k.save (conn);
    	categories.add(k);
    }
    
    public void regSeries(int seriesID, int season, int episode, Connection conn) {
    	series = new Series(seriesID);
        series.initialize(conn);
        this.season = season;
        this.episode = episode;
    }
    public void newSeries(String name, int numberOfSeasons, int numberOfEpisodes, int season, int episode, Connection conn) {
    	series = new Series(name, numberOfSeasons, numberOfEpisodes);
    	series.save(conn);
    	this.season = season;
    	this.episode = episode;
    }
    
    @Override
    public void initialize(Connection conn) {
    	try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Title, Length, Storyline, ReleaseDate FROM Movie WHERE MovieID=" + movieID);
            while (rs.next()) {
            	title =  rs.getString("Title");
            	length = rs.getString("Length");
            	storyline = rs.getString("Storyline");
                relaseDate = rs.getString("ReleaseDate");
            }

        } catch (Exception e) {
            System.out.println("db error during select of movie= "+e);
            return;
        }
    }
    
    @Override
    public void refresh(Connection conn) {
        initialize (conn);
    }
    
    @Override
    public void save(Connection conn) {
    	
    	// check if the movie belong to a series
    	if (series == null) {
			query = "INSERT INTO Movie (Title, Length, Storyline, ReleaseDate, SeriesID, Season, Episode) VALUES "
            		+ "('"+title+"','"+length+"','"+storyline+"','"+relaseDate+"', NULL, NULL, NULL)";
		}
    	else {
    		query = "INSERT INTO Movie (Title, Length, Storyline, ReleaseDate, SeriesID, Season, Episode) VALUES "
            		+ "('"+title+"','"+length+"','"+storyline+"','"+relaseDate+"',"+series.getSeriesID()+","+season+","+episode+")";
    	}
    	
    	try {
    		Statement stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("db error during insert of movie="+e);
            return;
        }
    	
    	// update all relating tables
    	// director
        for (int i=0;i<directors.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO Director VALUES ("+directors.get(i).getPersonID()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of director="+e);
                return;
            }
        }
        
        // screenwriter
        for (int i=0;i<screenwriters.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO Screenwriter VALUES ("+screenwriters.get(i).getPersonID()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of screenwriter="+e);
                return;
            }
        }
        
        // actor
        for (int i=0;i<listOfActorsAndRoles.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO Actor VALUES ("+listOfActorsAndRoles.get(i).get(0)+", LAST_INSERT_ID(), '"+
                		listOfActorsAndRoles.get(i).get(1)+"')");
            } catch (Exception e) {
                System.out.println("db error during insert of actor="+e);
                return;
            }
        }
        
        // publishing company
        for (int i=0;i<listOfPublishingCompaniesWithReleaseYearAndPlatform.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO Published VALUES ("+listOfPublishingCompaniesWithReleaseYearAndPlatform.get(i).get(0)+",LAST_INSERT_ID(), '"+
                		listOfPublishingCompaniesWithReleaseYearAndPlatform.get(i).get(1)+"','"+listOfPublishingCompaniesWithReleaseYearAndPlatform.get(i).get(2)+"')");
            } catch (Exception e) {
                System.out.println("db error during insert of published="+e);
                return;
            }
        }
        
        // music
        for (int i=0;i<music.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO MusicMovie VALUES ("+music.get(i).getMusicID()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of MusicMovie="+e);
                return;
            }
        }
        
    	// category
        for (int i=0;i<categories.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("INSERT INTO CategoryMovie VALUES ("+categories.get(i).getCategoryID()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of CategoryMovie="+e);
                return;
            }
        }
 
    }

}
