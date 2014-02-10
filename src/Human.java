// "extends": this is synonymous with inheritance -- it means that
// this class will inherit all attributes from the class that you
// name after that


// so what we want to do is get the strings that are the discards,
// convert that into cards, then remove those cards from the hands.
// Then we can re-populate the hands.

import java.util.*;

public class Human extends Player
{
    ArrayList<String> hDiscardList;

    public Human()
    {
        super();
    }

    public void showHandToPlayer()
    {
        waitTwoSecond();
        System.out.print("\tShuffling...");
        waitTwoSecond();
        System.out.println("\tDealing...\n");
        waitTwoSecond();
        System.out.print("\tYour cards: | ");
        for (int i = 0; i < 5; i++)
        {
            Card newCard = new Card();
            newCard = hand.get(i);
            String cardText = newCard.getCardText();
            System.out.print(cardText + " | ");
        }
        System.out.println();
    }// END SHOW HAND METHOD

    public int humanDiscard()
    {
        Scanner userInput = new Scanner(System.in);
        Scanner stringInput = new Scanner(System.in);
        int numCardsToDiscard = 0;

        // the only function of this while loop is to
        // allow he player to choose another number of cards
        // if they choose to discard four and don;'t have an Ace
        boolean loop = true;
        while (loop)
        {
            System.out.print("\n\tHow many cards would you like to discard? ->: ");
            numCardsToDiscard = userInput.nextInt();

            // check for ace
            if (4 == numCardsToDiscard && !checkForAce())
            {
                System.out.println("\tSorry... you need to have an ace in your hand to discard four cards.");
                System.out.println("\tHow about we try that again?");
            }
            else
                loop = false;
        }

        System.out.println("\n\tPlease enter the cards that you would like to discard.  Use the same format of");
        System.out.println("\tthe display (the Ace of Spades\"would be \"AS\", the Four of Clubs would be \"4C\", etc).\n");

        for (int i = 0; i < numCardsToDiscard; i++)
        {
            loop = true;
            while (loop)
            {
                int count = i+1;
                System.out.print("\t"+ count +"-> ");
                String discardCard = stringInput.nextLine();
                if (!compareStrings(discardCard))
                {
                    System.out.println("\n\t\"" + discardCard + "\" is not a card in your hand.");
                    System.out.println("\tPlease check your hand and try again.\n");
                }
                else
                {
                    for (int j = 0; j < 5; j++)
                    {
                        Card cardToRemove = new Card();
                        cardToRemove = hand.get(j);
                        String textOfCardToRemove = cardToRemove.getCardText();
                        int resultOfComparison = discardCard.compareTo(textOfCardToRemove);
                        if (resultOfComparison == 0)
                        {
                            hand.remove(j);
                            break;
                        }
                    }
                    loop = false;
                }
            }
        }
        return numCardsToDiscard;
    } // END HUMAN DISCARD METHOD



    public Boolean compareStrings(String discardCard)
    {
        for (int i = 0; i < 5; i++)
        {
            Card newCard = new Card();
            newCard = hand.get(i);
            String cardText = newCard.getCardText();
            int result = discardCard.compareTo(cardText);
            if (result == 0)
                return true;
        }
        return false;
    }// END COMPARE STRINGS

    // returns true if hand has an ace, false if not
    public Boolean checkForAce()
    {
        for (int i = 0; i < 5; i++)
        {
            Card card = new Card();
            card = hand.get(i);
            int rank = card.getRank();
            if (14 == rank)
                return true;
        }
        return false;
    } // END CHECK FOR ACE

    public void showDiscardedHandAsCheck()
    {
        for (int i = 0; i < hand.size(); i++)
        {
            Card brandNewCard = new Card();
            brandNewCard = hand.get(i);
            String text = brandNewCard.getCardText();
            System.out.println("Card " + i+1 + ": " + text);
        }
    } // END SHOW DISCARDED HAND

    public void waitTwoSecond()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            //Handle exception
        }
    }
}// END HUMAN CLASS