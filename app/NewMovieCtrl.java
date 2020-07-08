package app;

import java.sql.*;

public class NewMovieCtrl extends DBconn {
	private Movie movie;

    public NewMovieCtrl() {
        connect();
        // Let the making of a new movie be a transaction
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAutoCommit of NewMovieCtrl="+e);
            return;
        }
    }
    
    public void newMovie(String title, String length, String storyline, String releaseDate){
    	movie = new Movie(title,length,storyline,releaseDate);
    }
    
    // director
    public void registerDirector(int directorID) {
    	movie.regDirector(directorID, conn);
    }
    public void registerNewDirector(String name, String birthYear, String birthCountry) {
    	movie.newDirector(name, birthYear, birthCountry, conn);
    }
    
    // screenwriter
    public void registerScreenwriter(int screenwriterID) {
    	movie.regScreenwriter(screenwriterID, conn);
    }
    public void registerNewScreenwriter(String name, String birthYear, String birthCountry) {
    	movie.newScreenwriter(name, birthYear, birthCountry, conn);
    }
    
    // actor
    public void registerActor(int actorID, String role) {
    	movie.regActor(actorID, role, conn);
    }
    public void registerNewActor(String name, String birthYear, String birthCountry, String role) {
    	movie.newActor(name, birthYear, birthCountry, role, conn);
    }
    
    // publishing company
    public void registerCompany(int companyID, String releaseYear, String platform) {
    	movie.regCompany(companyID, releaseYear, platform, conn);
    }
    public void registerNewCompany(String urlLink, String address, String country, String releaseYear, String platform) {
    	movie.newCompany(urlLink, address, country, releaseYear, platform, conn);
    }
    
    // music
    public void registerMusic(int musicID) {
    	movie.regMusic(musicID, conn);
    }
    public void registerNewMusic(String composedBy, String performedBy) {
    	movie.newMusic(composedBy, performedBy, conn);
    }
    
    // category
    public void registerCategory(int categoryID) {
    	movie.regCategory(categoryID, conn);
    }
    public void registerNewCategory(String name) {
    	movie.newCategory(name, conn);
    }
    
    // series
    public void registerSeries(int seriesID, int season, int episode) {
    	movie.regSeries(seriesID, season, episode, conn);
    }
    public void registerNewSeries(String name, int numberOfSeasons, int numberOfEpisodes, int season, int episode) {
    	movie.newSeries(name, numberOfSeasons, numberOfEpisodes, season, episode, conn);
    }
    
    public void completeNewMovie() {
        movie.save(conn);
        try {
            conn.commit();
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("db error during commit of NewMovieCtrl="+e);
            return;
        }
    }

}
