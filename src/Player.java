import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Player
{
    // create the spots for each hand
    ArrayList<Card> hand;
    ArrayList<Card> hDiscardArr;
    ArrayList<Card> ai1DiscardArr;
    ArrayList<Card> ai2DiscardArr;
    ArrayList<Card> ai3DiscardArr;

    public Player()
    {
        hand          = new ArrayList<Card>(5);
        hDiscardArr   = new ArrayList<Card>();
        ai1DiscardArr = new ArrayList<Card>();
        ai2DiscardArr = new ArrayList<Card>();
        ai3DiscardArr = new ArrayList<Card>();
    }

    public void sortHand()
    {
        Collections.sort(hand, new Comparator<Card>()
        {
            @Override
            public int compare(Card card, Card card2) {
                return card.getRank() - card2.getRank();
            }
        });
    }

    public int PostDiscardDealCards(Deck deck,
                                    int currentCardInDeck,
                                    int numCardsToReDeal)
    {
        int newCurrentCardInDeck = 0;
        for (int i = 0; i < numCardsToReDeal; i++)
        {
            currentCardInDeck = currentCardInDeck + i;
            Card card = deck.getCard(currentCardInDeck);
            hand.add(card);
        }
        sortHand();
        return newCurrentCardInDeck = currentCardInDeck;
    }

    public void showFinalHand(int numAIPlayers,
                              Player h,
                              Player a1,
                              Player a2,
                              Player a3)
    {
        System.out.println("\n\tThe final hand is:\n");
        for (int i = 0; i < numAIPlayers+1; i++)
        {
            if      (0 == i) System.out.print("\tYour hand: \t\t\t\t\t\t\t|");
            else if (1 == i) System.out.print("\tThe first computer player's hand:\t|");
            else if (2 == i) System.out.print("\tThe second computer player's hand:\t|");
            else             System.out.print("\tThe third computer player's hand:\t|");
            for (int j = 0; j < 5; j++)
            {
                Card card = new Card();
                if      (0 == i) card =  h.hand.get(j);
                else if (1 == i) card = a1.hand.get(j);
                else if (2 == i) card = a2.hand.get(j);
                else             card = a3.hand.get(j);
                String text = card.getCardText();
                System.out.print(text + " | ");
            }
            System.out.println();
        }
    }

    public void EvaluateTheFinalHands(int numPlayers,
                                      Player h,
                                      Player a1,
                                      Player a2,
                                      Player a3)
    {
        int hScore, a1score, a2score, a3score;
        hScore = h.EvaluateHand();

    }

    public int EvaluateHand()
    {
        int score = 0;
        if (TwoOfAKind())   { setHandToFalse(); score = 1;}
        if (TwoPair())      { setHandToFalse(); score = 2;}
        if (ThreeOfAKind()) /*hard coded*/     {score = 3;}
        if (Straight())     { setHandToFalse(); score = 4;}
        if (Flush())        /*hard coded*/     {score = 5;}
        if (FullHouse())    { setHandToFalse(); score = 6;
            System.out.println("asdgasdfgbzxcvasefawef asdfv asdfg awe fasdf qtasdf ");} else {
            System.out.println("NEIN");}




        return score;
    }

    public boolean TwoOfAKind()
    {
        // mark the cards that have the same rank
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 5; j++)
            {
                Card formerCard = hand.get(i);
                Card latterCard = hand.get(j);
                int formerRank = formerCard.getRank();
                int latterRank = latterCard.getRank();
                if (formerRank == latterRank && i != j)
                {
                    Mark(formerCard);
                    Mark(latterCard);
                }
            }
        // if there are more than 1 card marked, the hand is TwoOfAKind
        int count = 0;
        for (int i = 0; i < 5; i++)
        {
            Card markedCard = hand.get(i);
            if (markedCard.getMarker()) count++;
        }
        return count > 1 ? true : false;
    }
    public boolean TwoPair()
    {
        boolean hasSinglePair = false;
        int rank = 0;

        // find a single pair
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 0; j++)
            {
                Card formerCard = hand.get(i);
                Card latterCard = hand.get(j);
                int formerRank = formerCard.getRank();
                int latterRank = latterCard.getRank();
                if (formerRank == latterRank && i != j)
                {
                    hasSinglePair = true;
                    rank = formerRank;
                    break;
                }
            }

        // find another single pair of a different rank
        if (hasSinglePair)
        {
            for (int i = 0; i < 5; i++)
                for (int j = 1; j < 5; j++)
                {
                    Card formerCard = hand.get(i);
                    Card latterCard = hand.get(j);
                    int formerRank = formerCard.getRank();
                    int latterRank = latterCard.getRank();
                    if (formerRank == latterRank &&   formerRank != rank && i != j)
                        return true;
                }
        }
        return false;
    }
    public boolean ThreeOfAKind()  // ugly. I'm tired.
    {
        Card zero  = hand.get(0); int zRank =  zero.getRank();
        Card one   = hand.get(1); int oRank =   one.getRank();
        Card two   = hand.get(2); int tRank =   two.getRank();
        Card three = hand.get(3); int TRank = three.getRank();
        Card four  = hand.get(4); int fRank =  four.getRank();
        if      (zRank == oRank && zRank == tRank) return true;
        else if (zRank == tRank && zRank == TRank) return true;
        else if (zRank == TRank && zRank == fRank) return true;
        else if (oRank == tRank && oRank == TRank) return true;
        else if (oRank == TRank && oRank == fRank) return true;
        else if (tRank == TRank && tRank == fRank) return true;
        else if (zRank == tRank && zRank == fRank) return true;
        else return false;
    }
    public boolean Straight()
    {
        int count = 0;
        for (int i = 0; i < 4; i++)
        {
            Card formerCard = hand.get(i);
            Card latterCard = hand.get(i+1);
            int formerCardRank = formerCard.getRank();
            int latterCardRank = latterCard.getRank();
            if (formerCardRank+1 == latterCardRank)
            {
                if (0 == i) { Mark(formerCard);Mark(latterCard);}
                else                           Mark(latterCard);
            }
        }
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            if (card.getMarker()) count++;
        }
        return 5 == count ? true : false;
    }
    public boolean Flush()
    {
        Card zero  = hand.get(0); int zSuit =  zero.getSuit();
        Card one   = hand.get(1); int oSuit =   one.getSuit();
        Card two   = hand.get(2); int tSuit =   two.getSuit();
        Card three = hand.get(3); int TSuit = three.getSuit();
        Card four  = hand.get(4); int fSuit =  four.getSuit();
        return zSuit == oSuit && zSuit == tSuit && zSuit == TSuit && zSuit == fSuit ? true : false;
    }
    public boolean FullHouse()
    {
        int rankTripleOne = 0;
        int positionTripleOne   = 0;
        int positionTripleTwo   = 0;
        int positionTripleThree = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 5; j++)
            {
                Card formerCard = hand.get(i);
                Card latterCard = hand.get(j);
                int formerRank = formerCard.getRank();
                int latterRank = latterCard.getRank();
                if (formerRank == latterRank)
                {
                    positionTripleOne = i;
                    positionTripleTwo = j;
                    rankTripleOne = formerRank;
                    for (int k = 0; k < 5; k++)
                    {
                        Card tripleCard = hand.get(i);
                        int possibleTripleRank = tripleCard.getRank();
                        if (rankTripleOne == possibleTripleRank && k != i && k != j)
                        {
                            for (int l = 0; l < 0; l++)
                                for (int m = 1; m < 5; m++)
                                {
                                    Card formerDoubleCard = hand.get(l);
                                    Card latterDoubleCard = hand.get(m);
                                    int possibleFormerDoubleRank = formerDoubleCard.getRank();
                                    int possibleLatterDoubleRank = latterDoubleCard.getRank();
                                    if (possibleFormerDoubleRank == possibleLatterDoubleRank &&
                                            possibleFormerDoubleRank != rankTripleOne)
                                        return true;
                                }
                        }
                    }
                }
            }
        return false;
    }
    public void Mark(Card c)
    {
        c.setMarker(true);
    }
    public void createHand()
    {
        Scanner intInput = new Scanner(System.in);
        for (int i = 0; i < 5; i++)
        {
            int count = i+1;
            System.out.print("Rank of card " + count + ": ");
            int rank = intInput.nextInt();
            System.out.print("Suit of card" + count + ": ");
            int suit = intInput.nextInt();
            Card card = hand.get(i);
            card.setRank(rank);
            card.setSuit(suit);
            System.out.println();
        }
    }
    public void  setHandToFalse()
    {
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            card.setMarker(false);
        }
    }
}// END PLAYER CLASS



    /*
    public void printHand()
    {
        System.out.println("\n\n\n");
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            String text = card.getCardText();
            System.out.println(text);
        }
    }
*/