package game;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public interface GameInterface {

    /**
     * The main function of the Game.
     * 
     * Asks the user  for the number of players as well as the location of the text file.
     * 
     * Creates the players and decks, distributes the cards.
     * 
     * Starts the threads.
     * 
     * Once a player wins, it creates log files as well as prints the winner to the console.
     */
    public void run();

    /**
     * Gets the number of players the user wants to play.
     * 
     * The number of players must be an integer greater than 2, therefore the input question
     * is repeated until a suitable number is inputted.
     */
    public void askNumberOfPlayers();

    /**
     * Gets the name of the file that the user wants to be used to create the cards.
     * 
     * The file must exist, therefore the input question is repeated until a suitable number
     * is inputted.
     */
    public void askFileName();

    /**
     * Reads through the file given, checking if there are enough cards in the file and checking
     * to make sure that every character is a positive integer. If both of those conditions are 
     * satisfied the cards are created.
     * 
     * @param fileName  The name of the file that will be read.
     * @return True if the file satisfied the conditions, false otherwise.
     */
    public boolean readFile(String fileName);

    /**
     * Creates the correct number of players and decks, creates Arrays to store the players and 
     * decks as well as assigns the correct decks to the correct players.
     */
    public void createPlayersAndDecks();

    /**
     * Using round robin dealing the cards get distributed to the correct players and decks. 
     */
    public void distributeCards();

    /**
     * Starts all of the player threads and creates an Array for the player threads.
     */
    public void startThreads();

    /**
     * Returns the value of the Atomic integer "winner".
     * @return The number of the winner or 0 if no one has won yet.
     */
    public int returnWinner();

    /**
     * If the expected value is correct sets the Atomic Integer "winner" to the value entered.
     * @param expected The value the Atomic Integer is being compared to.
     * @param value The value the Atomic Integer will be set to.
     */
    public void compareAndSet(int expected, int value);

    /**
     * Stops all of the player threads waiting on decks.
     */
    public void stopDeckMonitor();

    /**
     * Creates a text file, and writes to the text file.
     * @param fileName Name of the file being created.
     * @param log The contents of the file.
     */
    public void generateLog(String fileName, String log);

    /**
     * Returns the file that the user has used to create the cards. Used
     * for testing purposes.
     * @return The file the user has inputted to create the cards.
     */
    public File getInputFile();

    /**
     * Sets the file the cards will be read from. Used for testing purposes.
     * @param inputFile The file that will be used to create the cards for the test.
     */
    public void setInputFile(File inputFile);

    /**
     * Returns the number of players in the game which is set by the user or by a test.
     * @return The number of players in current the game.
     */
    public int getNumberOfPlayers();

    /**
     * Sets the number of players for the game, used for test purposes.
     * @param numberOfPlayers Number of players for the test.
     */
    public void setNumberOfPlayers(int numberOfPlayers);

    /**
     * Returns the pack of card objects created. Used for test purposes.
     * @return An array of card objects.
     */
    public Card[] getPack();

    /**
     * Sets the card pack of the game, used for test purposes.
     * @param pack An array of card objects. 
     */
    public void setPack(Card[] pack);

    /**
     * Returns the array of player objects in the game, used for test purposes.
     * @return An array of player objects.
     */
    public Player[] getPlayers() ;

    /**
     * Sets the array of players in the game, used for test purposes.
     * @param players  An array of player objects.
     */
    public void setPlayers(Player[] players);

    /**
     * Returns the array of player threads in the game, used for test purposes.
     * @return An array of player threads.
     */
    public Thread[] getPlayerThreads();

    /**
     * Sets the array of player threads in the game, used for test purposes.
     * @param playerThreads An array of player threads.
     */
    public void setPlayerThreads(Thread[] playerThreads);

    /**
     * Returns the array of card decks in the game, used for test purposes.
     * @return An array of card deck objects.
     */
    public CardDeck[] getDecks();

    /**
     * Sets the array of card decks in the game, used for test purposes.
     * @param decks An array of card deck objects.
     */
    public void setDecks(CardDeck[] decks);

    /**
     * Returns the AtomicInteger "winner", used for test purposes.
     * @return The AtomicInteger "winner", 0 if no one has won the game,
     * or the player number of the winner.
     */
    public AtomicInteger getWinner();

    /**
     * Sets the AtomicInteger "winner", used for test purposes.
     * @param winner An AtomicInteger
     */
    public void setWinner(AtomicInteger winner);
}

