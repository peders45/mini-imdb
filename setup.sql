CREATE TABLE Series (
    SeriesID             INT NOT NULL AUTO_INCREMENT,
    Name                 VARCHAR(30),
    NumberOfSeasons      INT,
    NumberOfEpisodes     INT,
    PRIMARY KEY (SeriesID)
);

CREATE TABLE Movie (
    MovieID             INT NOT NULL AUTO_INCREMENT,
    Title               VARCHAR(30),
    Length              TIME,
    Storyline           VARCHAR(700),
    ReleaseDate         DATE,
    SeriesID            INT,
    Season              INT,
    Episode             INT,
    PRIMARY KEY (MovieID),
    FOREIGN KEY (SeriesID) REFERENCES Series(SeriesID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE Category (
    CategoryID          INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(30),
    PRIMARY KEY (CategoryID)
);

CREATE TABLE Music (
    MusicID            INT NOT NULL AUTO_INCREMENT,
    ComposedBy         VARCHAR(60),
    PerformedBy         VARCHAR(60),
    PRIMARY KEY (MusicID)
);

CREATE TABLE PublishingCompany (
    CompanyID           INT NOT NULL AUTO_INCREMENT,
    urlLink             VARCHAR(250),
    Address             VARCHAR(50),
    Country             VARCHAR(56),
    PRIMARY KEY (CompanyID)
);

CREATE TABLE Person (
    PersonID            INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(30),
    BirthYear           CHAR(4),
    BirthCountry        VARCHAR(56),
    PRIMARY KEY (PersonID)
);

CREATE TABLE User (
    UserID              INT NOT NULL AUTO_INCREMENT,
    Email               VARCHAR(30),
    PRIMARY KEY (UserID)
);

CREATE TABLE Text (
    TextID              INT NOT NULL AUTO_INCREMENT,
    TypeOfText          VARCHAR(14),
    TextContent         VARCHAR(500),
    Rating              INT,
    UserID              INT NOT NULL,
    SeriesID            INT,
    MovieID             INT,
    PRIMARY KEY (TextID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (SeriesID) REFERENCES Series(SeriesID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    CHECK (Rating >= 1 AND Rating <= 10),
    CHECK (TypeOfText="Review" OR TypeOfText="Comment" OR TypeOfText="Recommendation")           
);

CREATE TABLE Actor (
    PersonID            INT NOT NULL,
    MovieID             INT NOT NULL,
    Role                VARCHAR(30),
    PRIMARY KEY (PersonID, MovieID, Role),
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE Screenwriter (
    PersonID            INT NOT NULL,
    MovieID             INT NOT NULL,
    PRIMARY KEY (PersonID, MovieID),
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE Director (
    PersonID            INT NOT NULL,
    MovieID             INT NOT NULL,
    PRIMARY KEY (PersonID, MovieID),
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE Published (
    CompanyID           INT NOT NULL,
    MovieID             INT NOT NULL,
    ReleaseYear         CHAR(4),
    Platform            VARCHAR(30),
    PRIMARY KEY (CompanyID, MovieID, Platform),
    FOREIGN KEY (CompanyID) REFERENCES PublishingCompany(CompanyID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE MusicMovie (
    MusicID            INT NOT NULL,
    MovieID             INT NOT NULL,
    PRIMARY KEY (MusicID, MovieID),
    FOREIGN KEY (MusicID) REFERENCES Music(MusicID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

CREATE TABLE CategoryMovie (
    CategoryID          INT NOT NULL,
    MovieID             INT NOT NULL,
    PRIMARY KEY (CategoryID, MovieID),
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
                             ON UPDATE CASCADE
                             ON DELETE CASCADE
);

-- DATE: YYYY-MM-DD
-- TIME: HH:MM:SS

-- format: SeriesID, name, seasons, episodes
INSERT INTO Series values(1, "Breaking Bad", 5, 62), (2, "Game of Thrones", 8, 73);

-- format: MovieID, title, length, storyline, LaunchDate, SeriesID, season, episode
INSERT INTO Movie values(1, "Yes Man", "01:44:00", "Carl, a lonely man with low self-esteem, participates in a self-help programme that 
challenges him to say yes to everything in life for an entire year.", "2008-12-26", NULL, NULL, NULL),(2, "Ace Ventura: When Nature Calls",
"01:40:00", "Pet detective Ace Ventura comes out of retirement to investigate the disappearance of a rare white bat, the symbol of an 
African tribe. Needless to say, when Ace gets involved, all hell breaks loose.", "1995-11-10", NULL, NULL, NULL),(3, "Ace Ventura: Pet Detective", 
"01:27:00", "Ace Ventura, a private detective, specialises in finding lost animals. When Snowflake, a football team's aquatic mascot, 
goes missing, he is called to investigate", "1994-02-04", NULL, NULL, NULL),(4, "Bruce Almighty", "01:41:00", "Bruce Nolan synes ikke at verden 
behandler ham rettferdig. Den han klandrer aller mest, er Gud.", "2003-05-14", NULL, NULL, NULL), (5, "The Dark Knight", "02:32:00", "After Gordon,
 Dent and Batman begin an assault on Gotham's organised crime, the mobs hire the Joker, a psychopathic criminal mastermind who wants to bring all 
 the heroes down to his level.", "2008-07-25", NULL, NULL, NULL),(6, "Batman Begins", "02:20:00", "After witnessing his parents' death, Bruce learns the 
 art of fighting to confront injustice. When he returns to Gotham as Batman, he must stop a secret society that intends to destroy the city.", "2005-07-15", NULL, NULL, NULL), 
(7, "En eksamenvakts dagbok", "01:12:00", "Grethe kjeder seg som eksamenvakt, men plutselig skjer det noe uventet", "2014-01-03", NULL, NULL, NULL),
(8, "Grilled", "00:46:00", "Having been kidnapped by a crazed Tuco, Walt and Jesse are held prisoner in a desert hideout.", "2009-03-15", 1, 2, 2),
(9, "Down", "00:47:00", "Walt and Jesse meet at a gas station to discuss their next move.", "2009-03-29", 1, 2, 4),
(10, "Two Swords", "00:58:00", "Tywin melts down Stark's greatsword Ice and forges it into two new swords.", "2014-04-06", 2, 4, 1);

-- format: CategoryID, name
INSERT INTO Category values(1, "Comedy"), (2, "Romance"),(3, "Adventure"),(4, "Crime"), (5,"Fantasy"), (6, "Drama"),
(7, "Action"), (8, "Thriller");

-- format: MusicID, compposed, performed
INSERT INTO Music values(1, "Ramin Djawadi", "The Czech Movie Orchestra and Choir"), (2, "Dave Porter", "Madison Gate Records"), 
(3, "The Walkmen", "The Walkmen"), (4, "Hans Zimmer ", "Batman Band");

-- format: CompanyID, url, address, country
INSERT INTO PublishingCompany values(1, "www.warnerbros.com", "Burbank, California", "USA"),(2, "www.universalpictures.com", "Universal City, California", "USA"),
(3, "www.hbo.com", "New York, New York", "USA"),(4, "www.amc.com", "New York, New York", "USA");

-- format: PersonID, name, year, country
INSERT INTO Person values(1, "Jim Carrey", 1962, "Canada"),(2, "Zooey Deschanel", 1980, "USA"), (3, "Bradley Cooper", 1975, "USA"),
(4, "John Michael Higgins", 1963, "USA"), (5, "Rhys Darby", 1974, "New Zealand"), (6, "Ian McNeice", 1950, "UK"), (7, "Simon Callow", 1949, "UK"),
(8, "Maynard Eziashi", 1965, "UK"), (9, "Bob Gunton", 1945, "USA"), (10, "Courtney Cox", 1964, "USA"), (11, "Sean Young", 1959, "USA"), (12, "Tone Loc", 1966, "USA"),
(13, "Dan Marino", 1961, "USA"), (14,"Morgan Freeman", 1937, "USA"), (15, "Jennifer Aniston", 1969, "USA"),(16, "Philip Baker Hall", 1931, "USA"),
(17, "Catherine Bell", 1968, "UK"), (18, "Christian Bale", 1974, "UK"), (19, "Heath Ledger", 1979, "USA"), (20, "Aaron Eckhart", 1968, "USA"),
(21, "Michael Caine", 1933, "UK"), (22, "Liam Neeson", 1952, "UK"), (23, "Katie Holmes", 1978, "USA"), (24, "Gary Oldman", 1958, "UK"), (25, "Gunhild Nordmann", 1941, "Norge"),
(26, "Peyton Reed", 1964, "USA"),(27, "Tom Shadyac", 1958, "USA"),(28, "Steve Oedekerk", 1961, "USA"),(29, "Christopher Nolan", 1970, "UK"),
(30, "Charles Haid", 1943, "USA"),(31, "George Mastras", 1996, "USA"),(32, "John Dahl", 1956, "USA"),(33, "Sam Catlin", 1956, "USA"),
(34, "D. B. Weiss", 1971, "USA"),(35, "David Benioff", 1970, "USA"),(36, "Bryan Cranston", 1956, "USA"),(37, "Emilia Clarke", 1986, "UK");

-- format: UserID, email
INSERT INTO User values(1, "janne123@gmail.com"),(2, "per@start.no");

-- format: TextID, type (Comment/Review/Recommendation), TextContent, rating, UserID, SeriesID, MovieID
INSERT INTO Text values(1, "Recommendation", "Top show", 10, 1, 2, NULL),(2, "Comment", "Very exciting episode", 8, 1, 2, 10),
(3, "Review", "Morsom for hele familien. Absolutt noe en bør få med seg", 7, 2, NULL, 4),(4, "Recommendation", "Bad movie", 2, 1, NULL, 7);

-- format: PersonID, MovieID, role
INSERT INTO Actor values(1,1,"Carl"), (2,1,"Allison"), (3,1,"Peter"), (4,1,"Nick"), (5,1, "Norman"), (1,2,"Ace Ventura"),
(6,2,"Fulton Greenwall"), (7,2,"Vincent Cadby"), (8,2,"Ouda"), (9,2,"Burton Quinn"), (1,3,"Ace Ventura"), (10,3, "Melissa"),
(11,3, "Lt. Lois Einhorn"), (12,3, "Emilio"), (13,3, "Dan Marino"), (1,4,"Bruce Nolan"), (14,4, "God"),(15,4,"Grace"),
(16,4, "Jack"), (17, 4, "Susan"), (18, 5, "Bruce Wayne"), (19, 5, "Joker"), (20,5, "Harvey Dent"), (21, 5, "Alfred"), (14,5, "Lucius Fox"),
(24,5, "Gordon"), (18,6, "Bruce Wayne"), (14,6, "Lucius Fox"), (21,6, "Alfred"), (22, 6, "Ducard"), (23,6, "Rachel Dawes"), 
(24,6, "Gordon"),(25,7,"Hovedperson"), (25, 7, "Biperson"), (36, 8, "Walter White"), (36, 9, "Walter White"),(37,10,"Daenerys Targaryan");

-- format: PersonID, MovieID
INSERT INTO Screenwriter values(31, 8),(33,9),(34,10),(35,10);

-- format: PersonID, MovieID
INSERT INTO Director VALUES(26, 1),(27, 3),(27, 4),(28, 2),(29, 5),(29, 6), (25, 7),(30, 8),(32,9),(34,10);

-- format: CompanyID, MovieID, year, platform (cinema/Tv/Streaming/Video)
INSERT INTO Published values(1,1,2008,"Cinema"),(1,2,1995,"Cinema"),(1,3,1994,"Cinema"),(2,4,2003,"Cinema"),(1,5,2008,"Cinema"),(1,6,2005,"Cinema"),
(2,7,2014,"Video"),(3,10,2014,"Streaming"),(3,10,2014,"Tv"),(4,8,2009,"Tv"),(4,9,2009,"Tv"),(4,8,2011,"Video"),(4,9,2011,"Video");

-- format: MusicID, MovieID
INSERT INTO MusicMovie values(1,10),(2,9),(3,8),(4,5),(4,6); 

-- format: CategoryID, MovieID
INSERT INTO CategoryMovie values(1,1),(1,2),(1,3),(1,4),(2,1),(3,2),(3,6),(4,2),(4,5),(5,4),(6,4),(6,5),(7,5),(7,6),(8,6),
(1,7),(2,7),(3,7),(4,7),(5,7),(6,7),(7,7),(6,8),(6,9),(5,10),(5,6);