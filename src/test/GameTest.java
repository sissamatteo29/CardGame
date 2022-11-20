package game;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Class containing the methods to test the Game class
 */
public class GameTest {

    GameInterface myTestGame1;
    GameInterface myTestGame2;
    static File cards1, cards2, cards3, cards4;

    /**
     * Creates 4 different files, 3 of them containing errors and the last one correctly formatted
     * to test the Game class
     * @throws IOException
     */
    @BeforeClass
    public static void createFile() throws IOException {

        /*
        First file
         */
        cards1 = new File("cards1.txt");
        try {
            cards1.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards1.txt");
            e.printStackTrace();
        }

        Writer writeFile1 = new FileWriter(cards1);
        String sequence = "1\n5\ng\n9\n";   //g is a wrong format
        for (int i = 0; i < 6; i++){
            writeFile1.write(sequence);
        }
        writeFile1.close();

        /*
        Second file
         */
        cards2 = new File("cards2.txt");
        try {
            cards2.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards2.txt");
            e.printStackTrace();
        }

        Writer writeFile2 = new FileWriter(cards2);
        sequence = "1\n5\n-22\n9\n";   //-22 is negative
        for (int i = 0; i < 6; i++){
            writeFile2.write(sequence);
        }
        writeFile2.close();

        /*
        Third file
         */
        cards3 = new File("cards3.txt");
        try {
            cards3.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards3.txt");
            e.printStackTrace();
        }

        Writer writeFile3 = new FileWriter(cards3);
        sequence = "1\n5\n22\n";        //right format but only 18 cards
        for (int i = 0; i < 6; i++){
            writeFile3.write(sequence);
        }
        writeFile3.close();

        /*
        Fourth file
         */
        cards4 = new File("cards4.txt");
        try {
            cards4.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards4.txt");
            e.printStackTrace();
        }

        Writer writeFile4 = new FileWriter(cards4);
        sequence = "1\n5\n22\n9\n";         //Correct format and 24 cards (3 players)
        for (int i = 0; i < 6; i++){
            writeFile4.write(sequence);
        }
        writeFile4.close();

    }

    /**
     * Creates and sets up the main fields of Game objects to test
     */
    @Before
    public void createTwoNewGames(){

        myTestGame1 = new Game();
        myTestGame2 = new Game();

        Card[] pack1 = new Card[40];
        for (int i = 0; i < 40; i++){
            pack1[i] = new Card(3);
        }

        Player[] players1 = new Player[5];
        CardDeck[] decks1 = new CardDeck[5];
        for (int i = 0; i < 5; i++){
            players1[i] = new Player(i + 1, myTestGame1);
            decks1[i] = new CardDeck(i + 1);
        }

        myTestGame1.setNumberOfPlayers(5);
        myTestGame1.setDecks(decks1);
        myTestGame1.setPack(pack1);
        myTestGame1.setPlayers(players1);

        Card[] pack2 = new Card[16];
        for (int i = 0; i < 16; i++){
            pack2[i] = new Card(8);
        }

        Player[] players2 = new Player[2];
        CardDeck[] decks2 = new CardDeck[2];
        for (int i = 0; i < 2; i++){
            players2[i] = new Player(i + 1, myTestGame2);
            decks2[i] = new CardDeck(i + 1);
        }

        myTestGame2.setPlayers(players2);
        myTestGame2.setPack(pack2);
        myTestGame2.setDecks(decks2);
        myTestGame2.setNumberOfPlayers(2);
    }

    /**
     * Tests the user's input for the number of players
     */
    @Test
    public void testAskNumberOfPlayers(){

        String numberOfPlayerInput = "tt\n7\nC5\n";     //String containing the user's inputs (correct and wrong)

        /*
        Sets the standard input and output to be ByteArrays (memory areas)
         */
        ByteArrayInputStream inputStream = new ByteArrayInputStream(numberOfPlayerInput.getBytes());
        InputStream stdin = System.in;      //Backup of the standard input
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream stdout = System.out;       //Backup of the standard output
        System.setOut(printStream);

        /*
        Calls the method to be tested from the Game class
         */
        Game testGame = new Game();
        testGame.setScanner(new Scanner(System.in));
        testGame.askNumberOfPlayers();

        /*
        Working on the output stream, checks if the program accepted the number 7 as a correct input
         */
        String resultingString = outputStream.toString();
        int index = resultingString.indexOf("The game");
        resultingString = resultingString.substring(index);
        String[] splitResultingString = resultingString.split(" ");     //Splits the string into words
        assertEquals("The initial method to get the user's input for the number of players " +
                "is not working properly", "7", splitResultingString[4]);       //The 5th word in the array is the number of players

        /*
        Restores the standard input and output to keyboard and display
         */
        System.setOut(stdout);
        System.setIn(stdin);
    }

    /**
     * Tests the user's input for the file name
     */
    @Test
    public void testAskFileName(){

        String numberOfPlayerInput = "Rome\ntt\ncards1.txt\nC5\n";      //String with the possible user's inputs (correct and wrong)

        /*
        Modifies the standard input and output to control the inputs and the outputs of the method to test
         */
        ByteArrayInputStream inputStream = new ByteArrayInputStream(numberOfPlayerInput.getBytes());
        InputStream stdin = System.in;      //Backup of the standard input
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream stdout = System.out;    //Backup of the standard output
        System.setOut(printStream);

        /*
        Calls the method to be tested in the Game class
         */
        Game testGame = new Game();
        testGame.setScanner(new Scanner(System.in));
        testGame.askFileName();

        /*
        Manipulates the output string generated by the method to check if the "cards.txt" file was the one accepted as the input file
         */
        String resultingString = outputStream.toString();
        int index = resultingString.indexOf("Selected");
        resultingString = resultingString.substring(index);
        String[] splitResultingString = resultingString.split(" ");     //Splits the string into words
        assertEquals("The initial method to get the user's input for the textfile" +
                " is not working properly", "cards1.txt", splitResultingString[2].strip());

        /*
        Restores the standard input and output to keyboard and display
         */
        System.setIn(stdin);
        System.setOut(stdout);
    }

    /*
    Tests the readFile method which reads the input file and detects errors in it
     */
    @Test
    public void testReadFile(){
        /*
        Sets up an instance of the Game class with the few necessary members
         */
        Game game = new Game();
        game.setNumberOfPlayers(3);
        game.setPack(new Card[3 * 8]);

        /*
        Modifies the standard output to avoid error messages on the console every time the testing method runs
         */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream saveOut = System.out;       //Backup of the standard output
        System.setOut(printStream);

        /*
        Calls the method to be tested
         */
        assertFalse(game.readFile(cards1.getName()));
        assertFalse(game.readFile(cards2.getName()));
        assertFalse(game.readFile(cards3.getName()));
        assertTrue(game.readFile(cards4.getName()));

        /*
        Restores standard output
         */
        System.setOut(saveOut);
    }

    /**
     * Tests the createPlayersAndDecks method by checking if random elements inside the Players[] and Decks[] arrays are null
     */
    @Test
    public void testCreatePlayersAndDecks() {
        Game trialGame = new Game();
        trialGame.setNumberOfPlayers(4);
        trialGame.createPlayersAndDecks();
        assertNotEquals(trialGame.getPlayers()[3], null);
        assertNotEquals(trialGame.getPlayers()[0], null);
        assertNotEquals(trialGame.getDecks()[2], null);
        assertNotEquals(trialGame.getDecks()[1], null);
    }

    /*
    Tests the distributeCards method starting from the Game1 object set up in the @Before method of this class
     */
    @Test
    public void testDistributeCardsForGame1() {    //5 players

        /*
        Calls the method of the Game class to test
         */
        myTestGame1.distributeCards();

        /*
        Checks the size of some random players' hands
         */
        assertEquals(4, myTestGame1.getPlayers()[1].getHand().size());
        assertEquals(4, myTestGame1.getPlayers()[0].getHand().size());

        /*
        Verifies that some cards in players' hands are equal to 3 (Game1 was set up with a deck of cards with number 3 to simplify)
         */
        assertEquals(3, myTestGame1.getPlayers()[3].getHand().get(2).getNumber());
        assertEquals(3, myTestGame1.getPlayers()[0].getHand().get(3).getNumber());

        /*
        Same process with the decks
         */
        assertEquals(4, myTestGame1.getDecks()[0].getCards().size());
        assertEquals(4, myTestGame1.getDecks()[3].getCards().size());

        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(3).getNumber());
    }

    /**
     * Another set of test equal to the previous method but with a Game object set up to have only 2 players
     */
    @Test
    public void testDistributeCardsForGame2(){
        myTestGame2.distributeCards();
        /*
        Verifies the size of some random players' hands
         */
        assertEquals(4, myTestGame2.getPlayers()[1].getHand().size());
        assertEquals(4, myTestGame2.getPlayers()[0].getHand().size());

        /*
        Verifies that some cards in players' hands are equal to 8
         */
        assertEquals(8, myTestGame2.getPlayers()[1].getHand().get(2).getNumber());
        assertEquals(8, myTestGame2.getPlayers()[0].getHand().get(3).getNumber());

        /*
        Verifies the same things for the decks
         */
        assertEquals(4, myTestGame2.getDecks()[0].getCards().size());
        assertEquals(4, myTestGame2.getDecks()[1].getCards().size());

        assertEquals(8, myTestGame2.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(8, myTestGame2.getDecks()[0].getCards().get(3).getNumber());
    }

    /**
     * Test method to verify if the testGenerateLog method can write on the file
     * @throws Exception
     */
    @Test
    public void testGenerateLog() throws Exception {
        Game game = new Game();
        File testGenerateLog = new File("testGenerateLog.txt");
        try {
            testGenerateLog.createNewFile();
            game.generateLog("testGenerateLog.txt", "Hello World!");
        } catch (Exception e){
            System.out.println("Test testGenerateLog has failed to" +
                    " create the file or something went wrong with the generateLog method in the Game class");
            throw new Exception();
        }
    }

    /**
     * After all the tests, delete the files created to set up the tests
     */
    @AfterClass
    public static void afterClass(){
        cards1.delete();
        cards2.delete();
        cards3.delete();
        cards4.delete();
    }
}
