
/**
 * Created by bob on 2/2/14.
 */


import java.util.*;

public class Game
{
    public static void main (String[] argv)
    {
        // create and shuffle the deck
        Deck deck = new Deck();
        deck.shuffle();
        //deck.printDeck(); // for debugging

        // create the players and get the number of players
        Human h  = new Human();
        AI a1 = new AI(); AI a2 = new AI(); AI a3 = new AI();
        int numAIPlayers = getNumPlayers();

        // deal and display the cards
        System.out.print("\tDealer cracks open a fresh pack of cards...\t");
        int currentCardInDeck = dealHands(deck, h, a1, a2, a3, numAIPlayers);
        h.showHandToPlayer();
        displayHands(a1, a2, a3, numAIPlayers);

        // discard and re-deal for human player
        int numCardsToReDeal = h.humanDiscard();
        //a1.createHand();    // for debugging
        int newCurrentCardInDeck = h.PostDiscardDealCards(deck, currentCardInDeck, numCardsToReDeal);
        System.out.print("\n\tDealer takes your discarded cards...\t");
        h.showHandToPlayer();

        // if player elects to play against no one, the game is over
        if (numAIPlayers == 0)
        {
            System.out.println("\n\tAnd that's it.  Since you elected not to play against anyone, this game is over.");
            System.out.println("\tI hereby pronounce you the winner. Hurray. For you.");
            System.out.println("\t(Perhaps next time you'll take on a challenger, even if it is just a computer algorithm...)");
        }
        else // play the rest of the game
        {
            // AIs discard and the hands are re-dealt
            for (int i = 0; i < numAIPlayers; i++)
            {
                if (0 == i)
                {
                    System.out.print("\n\tThe first computer player has...\t");
                    numCardsToReDeal = a1.aiDiscard(i);
                    newCurrentCardInDeck = a1.PostDiscardDealCards(deck, newCurrentCardInDeck, numCardsToReDeal);
                }
                else if (1 == i)
                {
                    System.out.print("\tThe second computer player has...\t");
                    numCardsToReDeal = a2.aiDiscard(i);
                    newCurrentCardInDeck = a2.PostDiscardDealCards(deck, newCurrentCardInDeck, numCardsToReDeal);
                }
                else
                {
                    System.out.print("\tThe  last computer player has...\t");
                    numCardsToReDeal = a3.aiDiscard(i);
                    newCurrentCardInDeck = a3.PostDiscardDealCards(deck, newCurrentCardInDeck, numCardsToReDeal);
                }
            }

            // We can show the final hand and determine the winner
            Player p = new Player();
            p.showFinalHand(numAIPlayers, h, a1, a2, a3);
            h.createHand();
            p.EvaluateTheFinalHands(numAIPlayers+1, h, a1, a2, a3);



            // note: the "evaluate the final hands" will eventually be in a loop

        }
        /*
         * DO NOT ERASE THE COMMENTS BELOW!!!
         */
        //   GameResults results = game.play(players);
        //   results.showStats();

    }

    public static Integer getNumPlayers()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n\n\t\t\t    Welcome to \"One Hand of Poker\", a single hand of five-card stud,");
        System.out.println("\t\t\tcreated by Amber Moore and Bob Hayhurst for Professor Troy's CS 342 class.");
        System.out.println("\t\t\t    You can either play by yourself or up to three computer opponents.\n");
        System.out.print("\tPlease enter the number of opponents that you would like to play against. ->: ");
        int numAIPlayers = scan.nextInt();
        System.out.println();
        return numAIPlayers;
    }

    public static Integer dealHands (Deck deck,
                                     Player h,
                                     Player a1,
                                     Player a2,
                                     Player a3,
                                     int numAIPlayers)
    {
        // the number of cards dealt is a function of the number of players
        int numPlayers = numAIPlayers+1;
        int numCards = numPlayers * 5;
        for (int i = 0; i < numCards; i++)
        {
            Card card = deck.getCard(i);
            if      (i < 5)     h.hand.add(card);
            else if (i < 10)   a1.hand.add(card);
            else if (i < 15)   a2.hand.add(card);
            else               a3.hand.add(card);
        }

        // organize the cards from lowest to highest
        for (int i = 0; i < numPlayers; i++)
        {
            if      (0 == i)    h.sortHand();
            else if (1 == i)   a1.sortHand();
            else if (2 == i)   a2.sortHand();
            else               a3.sortHand();
        }
        return numCards;
    }// END DEAL CARDS

    public static void displayHands(Player a1,
                                    Player a2,
                                    Player a3,
                                    int numAIPlayers)
    {
        Scanner stringInput = new Scanner(System.in);
        System.out.println("\n\tIf you'd like to see your opponent's card (and who wouldn't?), just ");
        System.out.print("\tenter the letter 'Y'.  Otherwise, enter any other character. ->:" );
        String seeCards = stringInput.nextLine();
        if (seeCards.equals("Y") && numAIPlayers != 0)
        {
            for (int i = 0; i < numAIPlayers; i++)
            {
                if      (0 == i) System.out.print("\n\tThe first AI's cards:\t");
                else if (1 == i) System.out.print("\tThe second AI's cards:\t");
                else             System.out.print("\tThe third AI's cards:\t");
                for (int j = 0; j < 5; j++)
                {
                    Card card = new Card();
                    if      (0 == i) card = a1.hand.get(j);
                    else if (1 == i) card = a2.hand.get(j);
                    else             card = a3.hand.get(j);
                    String cardText = card.getCardText();
                    System.out.print(" | " + cardText);
                }
                System.out.println();
            }
        }
        else if (0 == numAIPlayers)
            System.out.println("\n\tYou are not playing against anyone.  So that doesn't... really... work...");
    }
}// END DISPLAY HAND