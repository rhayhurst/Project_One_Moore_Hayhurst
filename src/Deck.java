import java.util.*;

/**
 * Created by bob on 2/4/14.
 */
public class Deck
{
    ArrayList<Card> deck; // this is our deck of cards

    Deck()   // constructor constructs the deck
    {
        //deck = new Card[52];
        deck = new ArrayList<Card>(52);
        int k = 1;
        for (int i = 1; i < 5; i++)
            for (int j = 2; j < 15; j++)
            {
                Card card = new Card();
                // deck[k++] = card;
                deck.add(card);
                card.setRank(j);
                card.setSuit(i);
            }
    }

    public void shuffle()
    {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    // this method can be used for debugging
    public void printDeck()
    {
        System.out.println("Shuffled deck of cards:");
        for (int i = 0; i < 52; i++)
        {
            Card card = deck.get(i);
            String cardText = card.getCardText();
            System.out.println(cardText);
        }
    }

    public Card getCard(int i)
    {
        Card card = deck.get(i);
        return card;
    }

}