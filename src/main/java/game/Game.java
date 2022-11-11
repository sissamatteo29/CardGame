package game;

import java.util.*;
import java.io.*;

public class Game {
    private int numberOfPlayers;
    private Card[] pack;
    private Player[] players;
    private CardDeck[] decks;
    private File inputFile;

    Game(){

    }

    public Game (int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public void start(){
        askNumberOfPlayers();
        pack = new Card[8*numberOfPlayers];
        askFileName();
        while (!readFile(inputFile.getName())){
            askFileName();
        }
        createPlayersAndDecks();
        distributeCards();
        startThreads();
    }

    public void askNumberOfPlayers(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the number of players: ");
            try {
                String line = scanner.nextLine();
                int number = Integer.parseInt(line);
                if (number > 1) {
                    numberOfPlayers = number;
                    System.out.println("The game will have " + numberOfPlayers + " players!");
                    break;
                } else {
                    System.out.println("Number of players must be more than 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Number of players must be an integer.");
            } catch (NoSuchElementException e) {
                return;
            }
        }
    }

    public void askFileName(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter location of pack to load:");
            String fileName = scanner.nextLine();
            File inputFile = new File(fileName);
            if(inputFile.exists()){
                this.inputFile = inputFile;
                System.out.println("Selected file: " + fileName);
                break;
            }else {
                System.out.printf("The file %s doesn't exist.%n", fileName);
            }
        }
    }

    public boolean readFile(String fileName){
        try {
            int index = 0;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (index < (8 * numberOfPlayers)) {
                String line = reader.readLine();
                if (line == null) {
                    //In this case there are not enough cards so the game cannot start
                    System.out.println("The file " + fileName + " didn't have enough cards!");
                    reader.close();
                    return false;
                } else {
                    int value;
                    try {
                        value = Integer.parseInt(line);
                        if (value > 0) {
                            pack[index] = new Card(value);
                        } else {
                            System.out.println("On line " + (index + 1) + " there was a number less that 1");
                            reader.close();
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("On line " + (index + 1) + " there was a wrong format");
                        reader.close();
                        return false;
                    }
                    
                }
                index++;
                
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            return false;
        }
        return true;
    }

    public void createPlayersAndDecks(){
        players = new Player[numberOfPlayers];
        decks = new CardDeck[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++){
            players[i] = new Player(i + 1, this);
            decks[i] = new CardDeck(i + 1);
        }
    }

    public void distributeCards() {
        //First distribute 4 cards to each player
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < numberOfPlayers; j++){
                players[j].getHand().add(pack[i * numberOfPlayers + j]);
            }
        }
        //Then we need to fill the Decks
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < numberOfPlayers; j++){
                decks[j].giveCard(pack[4 * numberOfPlayers + i * numberOfPlayers + j]);  //just shifted of 4*numberOfPlayers
            }
        }
        //Assign each deck to the respective player
        for (int i = 0; i < numberOfPlayers; i++){
            players[i].setTakeDeck(decks[i]);   //the take deck has the same number as the player
            players[i].setGiveDeck(decks[(i + 1)  % numberOfPlayers]);
        }
    }

    public void startThreads() {
        for(int i = 0; i < numberOfPlayers; i++){
            new Thread(players[i]).start(); //don't need any reference?
        }
    }

    public synchronized void endGame(){
        for (Player player : players) {
            player.setKeepGoing(false);
        }
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Card[] getPack() {
        return pack;
    }

    public void setPack(Card[] pack) {
        this.pack = pack;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public CardDeck[] getDecks() {
        return decks;
    }

    public void setDecks(CardDeck[] decks) {
        this.decks = decks;
    }

    public String winner(){
        return "winner";
    }
}
