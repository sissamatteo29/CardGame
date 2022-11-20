package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player1;

    /**
     * Sets up a basic player with preferred number 3
     */
    @Before
    public void createPlayer(){
        player1 = new Player();
        player1.setPlayerNumber(3);
    }

    /**
     * Tests the reorderHand() method with different initial hands of the player1
     */
    @Test
    public void testReorderHand(){
        /*
        Case 1: random hand with 2 preferred cards to move to the end of the hand
         */
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));  //set the hand of the player
        player1.reorderHand();
        //Checks if the last 2 cards of the player's hand are 3s
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 1).getNumber());
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 2).getNumber());

        /*
        Case 2: hand with no preferred cards
         */
        player1.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(2), new Card(8))));
        player1.reorderHand();

        //Since there are no preferred cards in the hand, the reorderHand() method shouldn't do anything
        assertEquals(4, player1.getHand().get(0).getNumber());
        assertEquals(8, player1.getHand().get(player1.getHand().size() - 1).getNumber());

        /*
        Case 3: hand full of preferred cards
         */
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));
        //The reorderHand() method shouldn't do anything and should not throw any exception
        player1.reorderHand();
        assertEquals(3, player1.getHand().get(0).getNumber());
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 1).getNumber());
    }

    /**
     * Tests if the checkVictory() method works with different initial hands of the player
     */
    @Test
    public void testCheckVictory() {
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));
        assertFalse(player1.checkVictory());

        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));  //set the hand of the player
        assertTrue(player1.checkVictory());
    }

    /**
     * Tests the action of taking a card from the takeDeck and discarding another one to the giveDeck
     */
    @Test
    public void testTakeAndDiscard(){
        /*
        Sets up a player to try the takeAndDiscard() method
         */
        Player player = new Player();
        player.setPlayerNumber(3);
        player.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(3), new Card(3))));       //Already reordered hand
        player.setPreferredCards(2);
        player.setTakeDeck(new CardDeck(3, new LinkedList<>(List.of(new Card(5), new Card(3), new Card(6)))));
        player.setGiveDeck(new CardDeck(4, new LinkedList<>(List.of(new Card(4), new Card(5)))));

        /*
        Calls the method to be tested the first time. The first card in the takeDeck is NOT a preferred one
         */
        player.takeAndDiscard();
        assertEquals(2, player.getTakeDeck().getCards().size());    //Checks decks new sizes
        assertEquals(3, player.getGiveDeck().getCards().size());
        assertEquals(4, player.getHand().size());                   //Checks player's hand size
        assertEquals(5, player.getHand().get(0).getNumber());

        /*
        Calls the method a second time, this time the card drawn is a preferred one
         */
        player.takeAndDiscard();
        assertEquals(1, player.getTakeDeck().getCards().size());
        assertEquals(4, player.getGiveDeck().getCards().size());
        assertEquals(4, player.getHand().size());
        assertEquals(3, player.getHand().get(1).getNumber());       //There should be 3 preferred cards at the end of the hand
        assertEquals(3, player.getHand().get(2).getNumber());
        assertEquals(3, player.getHand().get(3).getNumber());
    }

    /**
     * Tests the conversion of the player's hand into a string format
     */
    @Test
    public void testHandToString(){
        Player player = new Player();
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(4), new Card(5))));
        assertEquals("3 4 5", player.handToString());
        player.setHand(new ArrayList<>());
        assertEquals("", player.handToString());
    }

}
