package app;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		ResultCtrl resultCtrl = new ResultCtrl ();
        resultCtrl.connect();
    
        TextCtrl textCtrl = new TextCtrl ();
        textCtrl.connect();
        
        NewMovieCtrl newMovieCtrl = new NewMovieCtrl ();
        //newMovieCtrl.connect();
		
		Scanner myInput = new Scanner(System.in);
		
		while (true) {
			
			// the program's options
			System.out.println("You now have 5 options:\n"
					+ "1. See a chosen actor's roles\n"
					+ "2. See a chosen actor's movies\n"
					+ "3. See the publishing companies with the most movies in a chosen category\n"
					+ "4. Insert a new movie\n"
					+ "5. Insert a review of an episode to a series\n"
					+ "Enter number:");
			
			int programOption = Integer.parseInt(myInput.nextLine());
			
			// check valid user input
			if (programOption != 1 && programOption != 2 && programOption != 3 && programOption != 4 && programOption != 5) {
				throw new IllegalArgumentException("Valid numbers are 1-5.");
			}
			// the program's behaviour based on user's input
			else if (programOption == 1) {
				System.out.println("Search for actor (full name):");
				String actor = myInput.nextLine();
						
		        resultCtrl.printRoleResult(actor);
		        
		        System.out.println("Do you want to continue? <y/n>:");
		        String loop = myInput.nextLine();
		        checkContinue(loop);
		        
			}
			else if (programOption == 2) {
				System.out.println("Search for actor (full name)");
				String actor = myInput.nextLine();
				
				resultCtrl.printMovieResult(actor);
				
				System.out.println("Do you want to continue? <y/n>:");
		        String loop = myInput.nextLine();
		        checkContinue(loop);
			}
			else if (programOption == 3) {
				System.out.println("Search for category:");
				String category = myInput.nextLine();
				
				resultCtrl.printCompanyCategoryResult(category);
				
				System.out.println("Do you want to continue? <y/n>:");
		        String loop = myInput.nextLine();
		        checkContinue(loop);
			}
			else if (programOption == 4) {
				// not implemented validation of user input
				
				// input about the movie itself
				System.out.println("Title:");
				String title = myInput.nextLine();
				System.out.println("Length (HH:MM:SS):");
				String length = myInput.nextLine();
				System.out.println("Storyline (max 700 characters):");
				String storyline = myInput.nextLine();
				System.out.println("Release date (YYYY-MM-DD):");
				String releaseDate = myInput.nextLine();
				newMovieCtrl.newMovie(title, length, storyline, releaseDate);
				
				// Must add one/more director(s)
				addDirector(resultCtrl, textCtrl, newMovieCtrl, myInput);
				System.out.println("If you want you can add more information about the movie, both existing and new");
				seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);

			}
			else if (programOption == 5) {
				// not implemented validation of user input or possibility to choose again if error
				
				System.out.println("Series:");
				resultCtrl.printChooseSeriesResult();
				System.out.println("Enter number:");
				int series = Integer.parseInt(myInput.nextLine());
				
				System.out.println("Episodes:");
				resultCtrl.printChooseMovieResult(series);
				System.out.println("Enter number:");
				int episode = Integer.parseInt(myInput.nextLine());
				
				System.out.println("Write text (max 500 characters):");
				String textContent = myInput.nextLine();
				System.out.println("Give rating (1-10):");
				int rating = Integer.parseInt(myInput.nextLine());
				
				textCtrl.prepareText();
				// assuming userID is known from logged in user in the interface, set 2 as an example here
				textCtrl.regTekst( series, episode, 2, textContent, rating);
				
				System.out.println("Do you want to continue? <y/n>:");
		        String loop = myInput.nextLine();
		        checkContinue(loop);
			}
		}
    }
	
	// help method to check if continue program
	static void checkContinue(String loop) {
		if (loop.charAt(0) != 'y') {
			System.exit(0);
		}
	}
	
	// help method for choices when adding movie information
	static void seeNewMovieChoices(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("You have now 8 options:\n"
				+ "1. Add more directors\n"
				+ "2. Add more screenwriters\n"
				+ "3. Add more actors\n"
				+ "4. Add more publishing companies\n"
				+ "5. Add more music\n"
				+ "6. Add more categories\n"
				+ "7. Add series\n"
				+ "8. Complete the insertion of the new movie\n"
				+ "Enter number:");
		
		int movieOption = Integer.parseInt(myInput.nextLine());
		
		// check valid user input
		if (movieOption != 1 && movieOption != 2 && movieOption != 3 && movieOption != 4 && movieOption != 5 && movieOption != 6 && movieOption != 7 && movieOption != 8) {
			throw new IllegalArgumentException("Valid numbers are 1-8.");
		}
		// the program's behaviour based on user's input
		else if (movieOption == 1) {
	        addDirector(resultCtrl, textCtrl, newMovieCtrl, myInput);
	        seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 2) {
			addScreenwriter(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 3) {
			addActor(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 4) {
			addCompany(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 5) {
			addMusic(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 6) {
			addCategory(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 7) {
			addSeries(resultCtrl, textCtrl, newMovieCtrl, myInput);
			seeNewMovieChoices(resultCtrl, textCtrl, newMovieCtrl, myInput);
		}
		else if (movieOption == 8) {
			newMovieCtrl.completeNewMovie();
			System.out.println("Do you want to continue? <y/n>:");
	        String loop = myInput.nextLine();
	        checkContinue(loop);
		}
		
	}
	
	// help method to add more directors
	static void addDirector(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing director or add a new one? <exi/new>:");
		String directorOption = myInput.nextLine();
		
		if (directorOption.equals("exi")) {
			System.out.println("Directors:");
			resultCtrl.printDirectorResult();
			System.out.println("Enter number:");
			int directorID = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerDirector(directorID);
		}
		else if (directorOption.equals("new")) {
			System.out.println("Enter name:");
			String name = myInput.nextLine();
			System.out.println("Enter birth year (YYYY):");
			String birthYear = myInput.nextLine();
			System.out.println("Enter birth country:");
			String birthCountry = myInput.nextLine();
			newMovieCtrl.registerNewDirector(name, birthYear, birthCountry);
		}
	}
	
	// help method to add more screenwriters
	static void addScreenwriter(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing screenwriter or add a new one? <exi/new>:");
		String screenwriterOption = myInput.nextLine();
		
		if (screenwriterOption.equals("exi")) {
			System.out.println("Screenwriters:");
			resultCtrl.printScreenwriterResult();
			System.out.println("Enter number:");
			int screenwriterID = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerScreenwriter(screenwriterID);
		}
		else if (screenwriterOption.equals("new")) {
			System.out.println("Enter name:");
			String name = myInput.nextLine();
			System.out.println("Enter birth year (YYYY):");
			String birthYear = myInput.nextLine();
			System.out.println("Enter birth country:");
			String birthCountry = myInput.nextLine();
			newMovieCtrl.registerNewScreenwriter(name, birthYear, birthCountry);
		}
	}
	
    // help method to add more actors
	static void addActor(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing actor or add a new one? <exi/new>:");
		String actorOption = myInput.nextLine();
		
		if (actorOption.equals("exi")) {
			System.out.println("Actors:");
			resultCtrl.printActorResult();
			System.out.println("Enter number:");
			int actorID = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter role:");
			String role = myInput.nextLine();
			newMovieCtrl.registerActor(actorID, role);
		}
		else if (actorOption.equals("new")) {
			System.out.println("Enter name:");
			String name = myInput.nextLine();
			System.out.println("Enter birth year (YYYY):");
			String birthYear = myInput.nextLine();
			System.out.println("Enter birth country:");
			String birthCountry = myInput.nextLine();
			System.out.println("Enter role:");
			String role = myInput.nextLine();
			newMovieCtrl.registerNewActor(name, birthYear, birthCountry, role);
		}
	}
	
    // help method to add more publishing companies
	static void addCompany(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing publishing company or a new one? <exi/new>:");
		String companyOption = myInput.nextLine();
		
		if (companyOption.equals("exi")) {
			System.out.println("Publishing companies:");
			resultCtrl.printPublishingCompanyResult();
			System.out.println("Enter number:");
			int companyID = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter releaseYear (YYYY):");
			String releaseYear = myInput.nextLine();
			System.out.println("Enter platform:");
			String platform = myInput.nextLine();
			newMovieCtrl.registerCompany(companyID, releaseYear, platform);
		}
		else if (companyOption.equals("new")) {
			System.out.println("Enter url-link (www.***.***):");
			String url = myInput.nextLine();
			System.out.println("Enter address:");
			String address = myInput.nextLine();
			System.out.println("Enter country:");
			String country = myInput.nextLine();
			System.out.println("Enter release year (YYYY):");
			String releaseYear = myInput.nextLine();
			System.out.println("Enter platform:");
			String platform = myInput.nextLine();
			newMovieCtrl.registerNewCompany(url, address, country, releaseYear, platform);
		}
	}
	
    // help method to add more music
	static void addMusic(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add existing music or a new one? <exi/new>:");
		String musicOption = myInput.nextLine();
		
		if (musicOption.equals("exi")) {
			System.out.println("Music:");
			resultCtrl.printMusicResult();
			System.out.println("Enter number:");
			int musicID = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerMusic(musicID);
		}
		else if (musicOption.equals("new")) {
			System.out.println("Enter composer:");
			String composedBy = myInput.nextLine();
			System.out.println("Enter performer:");
			String performedBy = myInput.nextLine();
			newMovieCtrl.registerNewMusic(composedBy, performedBy);
		}
	}
	
	
	// help method to add more categories
	static void addCategory(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing category or a new one? <exi/new>:");
		String categoryOption = myInput.nextLine();
		
		if (categoryOption.equals("exi")) {
			System.out.println("Categories:");
			resultCtrl.printCategoryResult();
			System.out.println("Enter number:");
			int categoryID = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerCategory(categoryID);
		}
		else if (categoryOption.equals("new")) {
			System.out.println("Enter name:");
			String name = myInput.nextLine();
			newMovieCtrl.registerNewCategory(name);
		}
	}
	
	// help method to add more series
	static void addSeries(ResultCtrl resultCtrl, TextCtrl textCtrl, NewMovieCtrl newMovieCtrl, Scanner myInput) {
		System.out.println("Do you want to add an existing series or a new one? <exi/new>:");
		String seriesOption = myInput.nextLine();
		
		if (seriesOption.equals("exi")) {
			System.out.println("Series:");
			resultCtrl.printChooseSeriesResult();
			System.out.println("Enter number:");
			int seriesID = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter season number:");
			int season = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter episode number:");
			int episode = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerSeries(seriesID, season, episode);;
		}
		else if (seriesOption.equals("new")) {
			System.out.println("Enter Name:");
			String name = myInput.nextLine();
			System.out.println("Enter number of seasons:");
			int numberOfSeasons = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter number of episodes:");
			int numberOfEpisodes = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter season number:");
			int season = Integer.parseInt(myInput.nextLine());
			System.out.println("Enter episode number:");
			int episode = Integer.parseInt(myInput.nextLine());
			newMovieCtrl.registerNewSeries(name, numberOfSeasons, numberOfEpisodes, season, episode);
		}
	}
}
