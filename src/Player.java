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


    //----------------------------------------------------------------------------------//
    //----------------------------------------------------------------------------------//
    //----------------- everything from here down is the hand evaluator-----------------//
    //----------------------------------------------------------------------------------//
    public void EvaluateTheFinalHands(int numPlayers,
                                      Player h,
                                      Player a1,
                                      Player a2,
                                      Player a3)
    {
        // so we create scores.  The highest score immedietly wins the game.
        // a tie will be dealt with using "smarts"

        int hScore;

        /*  Uncomment when the time comes
        for (int i = 0; i < numPlayers; i++)
        {
            if      (0 == i)  hScore = h.EvaluateHand();
            else if (1 == i) a1Score = a1.EvaluateHand();
            else if (2 == i) a2Score = a2.EvaluateHAnd();
            else             a3Score = a3.EvaluateHand();
        }
        */
        hScore = h.EvaluateHand();
        System.out.println("\n\n\t\tThe score of the hand is " + hScore);
    }
    public int EvaluateHand()
    {
        boolean isStraight      = false;
        boolean isFlush         = false;
        boolean isTwoOfAKind    = false;
        boolean isThreeOfAKind  = false;
        boolean isFullHouse     = false;
        boolean isStraightFlush = false;
        int score = 0;
        if (TwoOfAKind())   { isTwoOfAKind = true;   score = 1;}
        if (TwoPair())      {                        score = 2;}
        if (ThreeOfAKind()) { isThreeOfAKind = true; score = 3;}
        if (Straight())     {  isStraight = true;    score = 4;}
        if (Flush())        { isFlush = true;        score = 5;}
        if (FullHouse(isTwoOfAKind,isThreeOfAKind)) { isFullHouse = true;     score = 6;}
        if (StraightFlush(isFlush,isStraight))      { isStraightFlush = true; score = 7;}
        return score;
    }
    public boolean TwoOfAKind()
    {
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 5; j++)
            {
                int formerCardRank = getRank(i);
                int latterCardRank = getRank(j);
                if (formerCardRank == latterCardRank && i != j)
                {
                    Card c1 = createCard(i);
                    Card c2 = createCard(j);
                    c1.setValueTwoOfAKindPair(formerCardRank);
                    int [] threeLowestCards = new int[3];
                    int lowest, middle, highest;
                    lowest = middle = highest = -999;
                    Card cardA = hand.get(i);
                    Card cardB = hand.get(i);
                    Card cardC = hand.get(i);
                    for (int k = 0; k < 5; k++)
                    {
                        cardA = hand.get(k);
                        int num = cardA.getRank();
                        if (num != formerCardRank)
                        {
                            lowest = num;

                            break;
                        }
                    }
                    for (int l = 0; l < 5; l++)
                    {
                        cardB = hand.get(l);
                        int num = cardB.getRank();
                        if (num != formerCardRank && num != lowest)
                        {
                            middle = num;

                            break;
                        }
                    }
                    for (int m = 0; m < 5; m++)
                    {
                        cardC = hand.get(m);
                        int num = cardC.getRank();
                        if (num != formerCardRank && num != lowest && num != middle)
                        {
                            highest = num;

                            break;
                        }
                    }
                    for (int o = 0; o < 5; o++)
                    {
                        Card card = hand.get(o);
                        card.setTwoKindLower(lowest);
                        card.setTwoKindMiddle(middle);
                        card.setTwoKindHighest(highest);
                        card.setValueTwoOfAKindPair(formerCardRank);
                    }
                    return true;
                }
            }
        return false;
    }
    public boolean TwoPair()
    {
        boolean hasTwoPair = false;
        int valFirstPair, valSecondPair, finalCard, lowerPair, higherPair;
        valFirstPair = valSecondPair = finalCard = lowerPair = higherPair = 0;
        // first find the first pair. Then find the second.
        for (int i = 0; i < 5; i++)
            for (int j = 1; j < 5; j++)
            {
                int formerCardPairOneRank = getRank(i);
                int latterCardPairOneRank = getRank(j);
                if (formerCardPairOneRank == latterCardPairOneRank && i != j)
                {
                    valFirstPair = formerCardPairOneRank;
                    for (int k = 0; k < 5; k++)
                        for (int l = 1; l < 5; l++)
                        {
                            int formerCardPairTwoRank = getRank(k);
                            int latterCardPairTwoRank = getRank(l);
                            if (formerCardPairTwoRank == latterCardPairTwoRank &&
                                formerCardPairTwoRank != formerCardPairOneRank &&
                                k != l)
                            {
                                hasTwoPair = true;
                                valSecondPair = formerCardPairTwoRank;
                            }
                        }
                }
            }
        if (hasTwoPair)
        {
            for (int i = 0; i < 5; i++)
            {
                int rank = getRank(i);
                if (rank != valFirstPair && rank != valSecondPair) finalCard = rank;
            }
            if (valFirstPair > valSecondPair)
            {
                higherPair = valFirstPair;
                lowerPair = valSecondPair;
            }
            else
            {
                higherPair = valSecondPair;
                lowerPair = valFirstPair;
            }
            for (int j = 0; j < 5; j++)
            {
                Card card = hand.get(j);
                card.setTwoPairLowerVal(lowerPair);
                card.setTwoPairUpperVal(higherPair);
                card.setTwoPairCardVal(finalCard);
            }
            return hasTwoPair;
        }
        return false;
    }

    public boolean ThreeOfAKind()
    {
        int z = getRank(0); Card zc = createCard(0);
        int o = getRank(1); Card oc = createCard(1);
        int t = getRank(2); Card tc = createCard(2);
        int T = getRank(3); Card Tc = createCard(3);
        int f = getRank(4); Card fc = createCard(4);
        if (z == o && z == t)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (z == t && z == T)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (z == T && z == f)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (z == t && z == f)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (z == o && z == T)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (z == o && z == f)
        {
            SetThreeOfAKindCard(z);
            return true;
        }
        if (o == t && o == T)
        {
            SetThreeOfAKindCard(o);
            return true;
        }
        if (o == T && o == f)
        {
            SetThreeOfAKindCard(o);
            return true;}
        if (t == T && t == f)
        {
            SetThreeOfAKindCard(t);
            return true;
        }
        return false;
    }
    public void SetThreeOfAKindCard(int n)
    {
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            card.setRankThreeOfAKindCard(n);
        }
    }

    public boolean Straight()
    {
        // check if they are in order.  Then count.  If it's five cards,
        // then they all have to be in consecutive order
        int count = 0;
        for (int i = 0; i < 4; i++)
        {
            int formerCardRank = getRank(i);
            int latterCardRank = getRank(i+1);
            if (formerCardRank+1 == latterCardRank)
            {
                Card former = createCard(i);
                Card latter = createCard(i+1);
                if (0 == i) { Mark(former); Mark(latter);}
                else                      { Mark(latter);}
            }
        }
        for (int i = 0; i < 5; i++)
        {
            Card c = createCard(i);
            if (c.getMarker()) count++;
        }
        if (5 == count)
        {
            Card card = hand.get(4);
            int highestVal = card.getRank();
            for (int i = 0; i < 5; i++)
            {
                Card c = hand.get(i);
                c.setStraightHighestCardValue(highestVal);
            }
            return true;
        }
        return false;
    }
    public boolean Flush()
    {
        int v0, v1, v2, v3, v4;
        v0 = v1 = v2 = v3 = v4 = 0;
        Card zero  = hand.get(0); int zSuit =  zero.getSuit();
        Card one   = hand.get(1); int oSuit =   one.getSuit();
        Card two   = hand.get(2); int tSuit =   two.getSuit();
        Card three = hand.get(3); int TSuit = three.getSuit();
        Card four  = hand.get(4); int fSuit =  four.getSuit();
        if (zSuit == oSuit && zSuit == tSuit && zSuit == TSuit && zSuit == fSuit)
        {

            for (int i = 0; i < 5; i++)
            {
                if (0 == i)
                {
                    for (int a = 0; a < 5; a++)
                    {
                        Card c = hand.get(a);
                        int valCardZero = getRank(i);
                        c.setFlushCardZero(valCardZero);
                     }
                }
                if (1 == i)
                    for (int b = 0; b < 5; b++)
                    {
                        Card c = hand.get(b);
                        int valCardOne = getRank(i);
                        c.setFlushCardOne(valCardOne);
                    }
                if (2 == i)
                    for (int c = 0; c < 5; c++)
                    {
                        Card d = hand.get(c);
                        int valCardTwo = getRank(i);
                        d.setFlushCardTwo(valCardTwo);
                    }
                if (3 == i)
                    for (int e = 0; e < 5; e++)
                    {
                        Card c = hand.get(e);
                        int valCardThree = getRank(i);
                        c.setFlushCardThree(valCardThree);
                    }
                else
                    for (int f = 0; f < 5; f++)
                    {
                        Card c = hand.get(f);
                        int valCardFour = getRank(i);
                        c.setFlushCardFour(valCardFour);
                    }
            }
            return true;
        }
        return false;
    }
    public boolean FullHouse(boolean isTwoOfAKind,
                             boolean isThreeOfAKind)
    {
        int threeKindNum, twoKindNum;
        threeKindNum = twoKindNum = 0;
        if (isTwoOfAKind && isThreeOfAKind)
        {
            Card c = hand.get(0);
            twoKindNum = c.getValueTwoOfAKindPair();
            Card d = hand.get(0);
            threeKindNum = d.getRankThreeOfAKindCard();
            if (twoKindNum == threeKindNum) { return false;}
            else
            {
                for (int i = 0; i < 5; i++)
                {
                    Card e = hand.get(i);
                    e.setFullHouseValue(threeKindNum);
                }
            }
        }
        return true;
    }
    public boolean StraightFlush(boolean isFlush, boolean isStraight)
    {
        if (isFlush && isStraight)
        {
            Card c = hand.get(4);
            int straightFlushHighestCard = c.getRank();
            for (int i = 0; i < 5; i++)
            {
                Card d = hand.get(i);
                d.setStraightHighestCardValue(straightFlushHighestCard);
            }
            return true;
        }
        return false;
    }
    public int getRank(int c)
    {
        Card card = hand.get(c);
        int rank = card.getRank();
        return rank;
    }
    public Card createCard(int c)
    {
        Card card = hand.get(c);
        return card;
    }
    public void  setHandToFalse()
    {
        for (int i = 0; i < 5; i++)
        {
            Card card = hand.get(i);
            card.setMarker(false);
        }
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
}

    /*
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

