import java.util.*;
import java.lang.*;

public class AI extends Player
{
    public AI ()
    {
        super();
    }

    public int aiDiscard(int i)
    {
        waitAMoment();
        Hyperbole(i);
        waitAMoment();
        int numCardsToDiscard = 0;
        if (OnePairOrBetter())
        {
            numCardsToDiscard = Discard();
            System.out.println("\tat least a pair... ");
            //checkPrintHand();  // for debugging
        }
        else if (Flush())
        {
            numCardsToDiscard = Discard();
            System.out.println("\ta chance at a flush.... ");
            //checkPrintHand();   // for debugging
        }
        else
        {
            setHandToFalse();
            if (Straight())
            {
                System.out.println("\tthe possibility of a straight...");
                numCardsToDiscard = Discard();
                //checkPrintHand();   // for debugging
            }
            else
            {
                setHandToFalse();
                if (CheckForAce())
                {
                    System.out.println("\tan ace, so it'll keep it and discard the rest of its hand...");
                    markFourCards();
                    numCardsToDiscard = Discard();
                    //checkPrintHand();   // for debugging
                }
                else
                {
                    System.out.println("\tnothing much, so it'll keep its two highest cards...");
                    setHandToFalse();
                    KeepHighestTwoCards();
                    numCardsToDiscard = Discard();
                    //checkPrintHand();   // for debugging
                }
            }
        }
        //setHandToFalse();
        return numCardsToDiscard;
    }
    public boolean OnePairOrBetter()
    {
        boolean pairFound = false;
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 5; j++)
            {
                if (i == j) break;
                Card cardToCheck1 = hand.get(i);
                Card cardToCheck2 = hand.get(j);
                if (cardToCheck1.getRank() == cardToCheck2.getRank())
                {
                    markTwoCards(i,j);
                    pairFound = true;
                }
            }
        if (pairFound) { return true;}
        else           { return false;}
    }

    public boolean Flush()
    {
        for (int s = 4; s > 0; s--)
        {
            int count = 0;
            for (int i = 0; i < 5; i++)
            {
                Card newCard = hand.get(i);
                int suit = newCard.getSuit();
                if (s == suit) count++;
                if (4 == count)
                {
                    for (int j = 0; j < 5; j++)
                    {
                        Card anotherCard = hand.get(j);
                        if (anotherCard.getSuit() == s)
                        {
                            anotherCard.setMarker(true);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Straight()
    {
        // the problem is that we are setting BOTH cards marked as true.
        // when we go through the cards, we should only mark two cards as
        // true if we're dealing with i = 0
        //
        for (int i = 0; i < 4; i++)
        {
            Card formerCard = hand.get(i);
            Card latterCard = hand.get(i+1);
            int formerCardRank = formerCard.getRank();
            int latterCardRank = latterCard.getRank();
            if (formerCardRank+1 == latterCardRank)
            {
                if (0 == i) markTwoCards(i, i+1);
                else if (1 == i)
                {
                    Card zerothCard = hand.get(0);
                    if (!zerothCard.getMarker()) markTwoCards(i, i+1);
                    else latterCard.setMarker(true);
                }
                else latterCard.setMarker(true);
            }
        }
        // next determine if four or more cards are marked
        // if they are, then we got the possibility of a straight
        int count = 0;
        for (int i = 0; i < 5; i++)
        {
            Card maybeMarkedCard = hand.get(i);
            if (maybeMarkedCard.getMarker()) count++;
        }
        if (count >= 4) return true;
        else            return false;
    }

    public boolean CheckForAce()
    {
        for (int i= 0; i < 5; i++)
        {
            Card card = hand.get(i);
            int rank = card.getRank();
            if (14 == rank) return true;
        }
        return false;
    }

    public void KeepHighestTwoCards()
    {
        for (int i = 0; i < 5; i++)
        {
            if (i >= 3)
            {
                Card card = hand.get(i);
                card.setMarker(true);
            }
        }
    }

    public void markFourCards()
    {
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            int rank = card.getRank();
            if (14 == rank) card.setMarker(true);
        }
    }
    public void markTwoCards(int i, int j)
    {
        Card markedCard1 = hand.get(i);
        Card markedCard2 = hand.get(j);
        markedCard1.setMarker(true);
        markedCard2.setMarker(true);
    }

    public int Discard()
    {
        int numCardsDiscarded = 0;
        for (int i = 0; i < hand.size(); i++)
        {
            Card toDiscard = hand.get(i);
            if (toDiscard.getMarker() == false)
            {
                numCardsDiscarded++;
                hand.remove(i);
                i--;
            }
        }
        return numCardsDiscarded;
    }

    public void setHandToFalse()
    {
        for (int i = 0; i < 5; i++)
        {
            Card setCard = hand.get(i);
            setCard.setMarker(false);
        }
    }




    public void checkPrintHand()
    {
        for (int i = 0; i < hand.size(); i++)
        {
            System.out.println();
            Card newCard = new Card();
            newCard = hand.get(i);
            String cardText = newCard.getCardText();
            System.out.println("After discard: " + cardText);
        }
    }

    public void waitAMoment()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            //Handle exception
        }
    }

    public void Hyperbole(int i)
    {
        if      (0 == i) System.out.print("[unbearably ");
        else if (1 == i) System.out.print("[utterly   ");
        else             System.out.print("[unbelievably ");
        System.out.print("complicated algorithm in progress...]\t");
    }
}// END AI CLASS