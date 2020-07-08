package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultCtrl extends DBconn{
	
	ArrayList<String> companyIDs = new ArrayList<String>();
	List<List<String>> listOfCompaniesAndNumberOfMovies = new ArrayList<List<String>>();
	
	public void printRoleResult(String actor) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Role FROM Actor INNER JOIN Person ON (Actor.PersonID = "
           		+ "Person.PersonID) WHERE Name = '"+actor+"';";
           
            ResultSet rs = stmt.executeQuery(query);
            int nr = 1;
            System.out.println("Rolelist for actor "+actor);
            while (rs.next()) {
            	System.out.println(" " + nr++ + " "+ rs.getString("Role"));
            }
            
     	} catch (Exception e) {
            System.out.println("db error during select of actor = "+e);
     	}
	}
	
	public void printMovieResult(String actor) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Title FROM (Actor INNER JOIN Person ON (Actor.PersonID = "
           		+ "Person.PersonID)) INNER JOIN Movie ON (Actor.MovieID = Movie.MovieID) WHERE Name = '"+actor+"';";
            
            ResultSet rs = stmt.executeQuery(query);
            int nr = 1;
            System.out.println("Movielist for Actor "+actor);
            while (rs.next()) {
            	System.out.println(" " + nr++ + " "+ rs.getString("Title"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of actor = "+e);
     	}
	}
	
	public void printCompanyCategoryResult(String category) {
		// get companyID to check all companies
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT CompanyID FROM PublishingCompany;";
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
		    	companyIDs.add(rs.getString("CompanyID"));
            }	    
     	} catch (Exception e) {
            System.out.println("db error during select of CompanyIDs = "+e);
     	}
        
        // iterate through the companies to find the number of movies in chosen category
        try {
        	System.out.println("Companies with most movies in the category "+category);
        	for (String companyID : companyIDs) {
	            Statement stmt = conn.createStatement();	            
	            String query = "SELECT Published.CompanyID, urlLink, Name, COUNT(*)" +  
	            		"FROM (((Category INNER JOIN CategoryMovie ON (Category.CategoryID = CategoryMovie.CategoryID))" + 
	            		"INNER JOIN Movie ON (CategoryMovie.MovieID = Movie.MovieID))" + 
	            		"INNER JOIN Published ON (Movie.MovieID = Published.MovieID))" + 
	            		"INNER JOIN PublishingCompany ON (Published.CompanyID = PublishingCompany.CompanyID)" + 
	            		"WHERE Name = '"+category+"' AND Published.CompanyID = '"+companyID+"';";
	            
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	            	// if company have more than 0 movies, their url and number are added to a 2-dimensional array
	            	if (Integer.parseInt(rs.getString("COUNT(*)")) != 0) {	            		
	            		listOfCompaniesAndNumberOfMovies.add(new ArrayList<String>(Arrays.asList(rs.getString("urlLink"), rs.getString("COUNT(*)"))));
					}
	            }	    
        	}
     	} catch (Exception e) {
            System.out.println("db error during select of selskap med category = "+e);
     	}
        
        if (listOfCompaniesAndNumberOfMovies.isEmpty() == false) {
	        // find the highest number of movies in category
	        int best = -1;
	        for(int i = 0; i < listOfCompaniesAndNumberOfMovies.size(); i++){
	            int temp = Integer.parseInt(listOfCompaniesAndNumberOfMovies.get(i).get(1));
	            if (temp > best) {
	    			best = temp;
	    		}
	        }
	        
	        // if companies have equal highest number --> print both/all
        	for (int i = 0; i < listOfCompaniesAndNumberOfMovies.size(); i++) {
        		if (Integer.parseInt(listOfCompaniesAndNumberOfMovies.get(i).get(1)) == best) {
        			System.out.println(listOfCompaniesAndNumberOfMovies.get(i).get(0) + ": "+ listOfCompaniesAndNumberOfMovies.get(i).get(1));
        		}
        	}
        } 
        else {
        	System.out.println("No result");
        }
        // clear lists for new query
        companyIDs.clear();
        listOfCompaniesAndNumberOfMovies.clear();
	}
	
	// some help methods for the textual interface
	// assume the series and episode a text is regarding + which user is logged in is known from the interface
	
	public void printChooseSeriesResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT SeriesID, Name FROM Series;";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("SeriesID") + ". "+ rs.getString("Name"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of series = "+e);
     	}
	}
	
	public void printChooseMovieResult(int seriesID) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT MovieID, Title FROM Series INNER JOIN Movie ON (Series.SeriesID = Movie.SeriesID)"
            		+ " WHERE Movie.SeriesID = '"+seriesID+"';";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("MovieID") + ". "+ rs.getString("Title"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of movie (episode) = "+e);
     	}
	}
	
	public void printDirectorResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT DISTINCT Director.PersonID, Name FROM Person INNER JOIN Director ON (Person.PersonID = Director.PersonID)";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("PersonID") + ". "+ rs.getString("Name"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of director = "+e);
     	}
	}
	
	public void printScreenwriterResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT DISTINCT Screenwriter.PersonID, Name FROM Person INNER JOIN Screenwriter ON (Person.PersonID = Screenwriter.PersonID)";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("PersonID") + ". "+ rs.getString("Name"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of screenwriter = "+e);
     	}
	}
	
	public void printActorResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT DISTINCT Actor.PersonID, Name FROM Person INNER JOIN Actor ON (Person.PersonID = Actor.PersonID)";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("PersonID") + ". "+ rs.getString("Name"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of actor = "+e);
     	}
	}
	
	public void printPublishingCompanyResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT CompanyID, urlLink FROM PublishingCompany";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("CompanyID") + ". "+ rs.getString("urlLink"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of publishing company = "+e);
     	}
	}
	
	public void printMusicResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT MusicID, ComposedBy FROM Music";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("MusicID") + ". "+ rs.getString("ComposedBy"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of music = "+e);
     	}
	}
	
	public void printCategoryResult() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT CategoryID, Name FROM Category";
           
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	System.out.println(rs.getString("CategoryID") + ". "+ rs.getString("Name"));
            }	
            
     	} catch (Exception e) {
            System.out.println("db error during select of category = "+e);
     	}
	}
}

