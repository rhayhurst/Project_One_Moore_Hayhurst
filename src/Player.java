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

    private String name;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    private int Score;
    public int getScore() {return Score;}
    public void setScore(int Score) {this.Score = Score;}


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
      //createHand();  // for debugging
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

        //int hScore, a1Score, a2Score, a3Score;
        //hScore = a1Score = a2Score = a3Score = 0

         // Uncomment when the time comes
        boolean a2IsUsed = false;
        boolean a3IsUsed = false;
        for (int i = 0; i < numPlayers; i++)
        {
            if      (0 == i)
            {
                //int score = ;
                h.setScore(h.EvaluateHand());
            }
            else if (1 == i)
            {
                int score = a1.EvaluateHand();
                a1.setScore(score);
            }
            else if (2 == i)
            {
                a2IsUsed = true;
                int score = a2.EvaluateHand();
                a2.setScore(score);
            }
            else
            {
                a3IsUsed = true;
                int score = a3.EvaluateHand();
                a3.setScore(score);}
        }


/*
        int hScore = 0;
        h.EvaluateHand();
        System.out.println("THe score is: " + hScore);*/
        determineWinner(numPlayers, h, a1, a2, a3, a2IsUsed, a3IsUsed);
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
        if (TwoOfAKind())
        {
            isTwoOfAKind = true;
            score = 1;
        }
        if (TwoPair())      {                        score = 2;}
        if (ThreeOfAKind()) { isThreeOfAKind = true; score = 3;}
        if (HasStraight())     {  isStraight = true;    score = 4;}
        if (HasFlush())        { isFlush = true;        score = 5;}
        if (FullHouse(isTwoOfAKind, isThreeOfAKind)) { isFullHouse = true;     score = 6;}
        if (StraightFlush(isFlush,isStraight))
        {
            isStraightFlush = true;
            score = 7;
        }
        if (score == 0)
        {
            score = 0;
            setHighCardValues();
        }
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
        int z = getRank(0);
        int o = getRank(1);
        int t = getRank(2);
        int T = getRank(3);
        int f = getRank(4);
        if (z == o && z == t) { SetThreeOfAKindCard(z); return true;}
        if (z == t && z == T) { SetThreeOfAKindCard(z); return true;}
        if (z == T && z == f) { SetThreeOfAKindCard(z); return true;}
        if (z == t && z == f) { SetThreeOfAKindCard(z); return true;}
        if (z == o && z == T) { SetThreeOfAKindCard(z); return true;}
        if (z == o && z == f) { SetThreeOfAKindCard(z); return true;}
        if (o == t && o == T) { SetThreeOfAKindCard(o); return true;}
        if (o == T && o == f) { SetThreeOfAKindCard(o); return true;}
        if (t == T && t == f) { SetThreeOfAKindCard(t); return true;}
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

    public boolean HasStraight()
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
    public boolean HasFlush()
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
                return true;
            }
        }
        return false;
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
                d.setStraightFlushHighestCardValue(straightFlushHighestCard);
            }
            return true;
        }
        return false;
    }

    public void setHighCardValues()
    {
        for (int i = 0; i < 5; i++)
        {
            if (0 == i)
            {
                for (int a = 0; a < 5; a++)
                {
                    Card x = hand.get(a);
                    int valCardZero = getRank(i);
                    x.setHighCardValueElementZero(valCardZero);
                }
            }
            else if (1 == i)
            {
                for (int b = 0; b < 5; b++)
                {
                    Card x = hand.get(b);
                    int valCardOne = getRank(i);
                    x.setHighCardValueElementOne(valCardOne);
                }
            }
            else if (2 == i)
            {
                for (int c = 0; c < 5; c++)
                {
                    Card x = hand.get(c);
                    int valCardTwo = getRank(i);
                    x.setHighCardValueElementTwo(valCardTwo);
                }
            }
            else if (3 == i)
            {
                for (int d = 0; d < 5; d++)
                {
                    Card x = hand.get(d);
                    int valCardThree = getRank(i);
                    x.setHighCardValueElementThree(valCardThree);
                }
            }
            else
            {
                for (int e = 0; e < 5; e++)
                {
                    Card x = hand.get(e);
                    int valCardFour = getRank(i);
                    x.setHighCardValueElementFour(valCardFour);
                }
            }
        }
    }
               /* else if (1 == i) card.setHighCardValueElementOne(rank);
            else if (2 == i) card.setHighCardValueElementTwo(rank);
            else if (3 == i) card.setHighCardValueElementThree(rank);
            else             card.setHighCardValueElementFour(rank);*/
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
    public void determineWinner(int numPlayers,
                                 Player h,
                                 Player a1,
                                 Player a2,
                                 Player a3,
                                 boolean a2IsUsed,
                                 boolean a3IsUsed)
    {
        if (a2IsUsed == false) a2.setScore(-999);
        if (a3IsUsed == false) a3.setScore(-999);
        ArrayList<Integer> playerScores = new ArrayList<Integer>(numPlayers);
        int tieHandValue;
        for (int i = 0; i < numPlayers; i++)
        {
            if      (i == 0) playerScores.add(h.getScore());
            else if (i == 1) playerScores.add(a1.getScore());
            else if (i == 2) playerScores.add(a2.getScore());
            else             playerScores.add(a3.getScore());
        }
        Collections.sort(playerScores);

        int last = playerScores.get(numPlayers-1);
        int nextToLast = playerScores.get(numPlayers-2);
        if (last != nextToLast)
        {
            if      ( h.getScore() == last) winner(h);
            else if (a1.getScore() == last) winner(a1);
            else if (a2.getScore() == last) winner(a2);
            else                            winner(a3);
        }
        else
        {
            tieHandValue = last;
            tieBreaker(tieHandValue, h, a1, a2, a3);
        }
    }

    public void tieBreaker(int tieHandValue,
                           Player h,
                           Player a1,
                           Player a2,
                           Player a3)
    {

        ArrayList<Player> tieList = new ArrayList<Player>();
        if (tieHandValue ==  h.getScore())
        {
            tieList.add(h);
        }
        if (tieHandValue == a1.getScore())
        {
            tieList.add(a1);
        }
        if (tieHandValue == a2.getScore())
        {
            tieList.add(a2);
        }
        if (tieHandValue == a3.getScore())
        {
            tieList.add(a3);
        }
        //---------------------------------//

        if (0 == tieHandValue)
        {
            tieHighCard(tieList);
        }
        else if (1 == tieHandValue)
        {
            tieTwoKind(tieList);
        }
        else if (2 == tieHandValue)
        {
            tieTwoPair(tieList);
        }
        else if (3 == tieHandValue)
        {
            tieThreeKind(tieList);
        }
        else if (4 == tieHandValue)
        {
            tieStraight(tieList);
        }
        else if (5 == tieHandValue)
        {
            tieFlush(tieList);
        }
        else if (6 == tieHandValue)
        {
            tieFullHouse(tieList);
        }
    }
    public void tieHighCard(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        ArrayList<Integer> cardVal2 = new ArrayList<Integer>();
        ArrayList<Integer> cardVal3 = new ArrayList<Integer>();
        ArrayList<Integer> cardVal4 = new ArrayList<Integer>();
        ArrayList<Integer> cardVal5 = new ArrayList<Integer>();
         ArrayList<Player> fourthHighestCard = new ArrayList<Player>();
        ArrayList<Player> thirdHighestCard = new ArrayList<Player>();
        ArrayList<Player> secondHighestCard = new ArrayList<Player>();
        ArrayList<Player> firstHighestCard = new ArrayList<Player>();
        ArrayList<Player> zerothHighestCard = new ArrayList<Player>();

        for (int i = 0; i < tieList.size(); i++)
        {
            Player c = tieList.get(i);
            Card cCard = c.hand.get(0);
            cardVal.add(cCard.getHighCardValueElementFour());
        }
        Collections.sort(cardVal);
        int highestCard = cardVal.get(cardVal.size() - 1);

        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card dCard = p.hand.get(0);
            int dCardVal = dCard.getHighCardValueElementFour();
            if (highestCard == dCardVal)
            {
                fourthHighestCard.add(p);
            }
        }

        /*****************************************************************************/
        if (fourthHighestCard.size() == 1) winner(fourthHighestCard.get(0));
        else
        {
            for (int i = 0; i < fourthHighestCard.size(); i++)
            {
                Player p = fourthHighestCard.get(i);
                Card cCard = p.hand.get(i);
                cardVal2.add(cCard.getHighCardValueElementThree());
            }
            Collections.sort(cardVal2);
            int element2Val = cardVal2.get(cardVal2.size()-1);
            for (int i = 0; i < fourthHighestCard.size(); i++)
            {
                Player p = fourthHighestCard.get(i);
                Card dCard = p.hand.get(0);
                int dCardVal2 = dCard.getHighCardValueElementThree();
                if (element2Val == dCardVal2)
                {
                    thirdHighestCard.add(p);
                }
            }

            /*****************************************************************************/

            if (thirdHighestCard.size() == 1) winner(thirdHighestCard.get(0));
            else
            {
                for (int i = 0; i < thirdHighestCard.size(); i++)
                {
                    Player p = thirdHighestCard.get(i);
                    Card dCard = p.hand.get(0);
                    cardVal3.add(dCard.getHighCardValueElementTwo());
                }
                Collections.sort(cardVal3);
                int element3Val = cardVal3.get(cardVal3.size()-1);
                for (int i = 0; i < thirdHighestCard.size(); i++)
                {
                    Player p = thirdHighestCard.get(i);
                    Card dCard = p.hand.get(0);
                    int dCardVal3 = dCard.getHighCardValueElementTwo();
                    if (element3Val == dCardVal3)
                    {
                        secondHighestCard.add(p);
                    }
                }

                /*****************************************************************************/

                if (secondHighestCard.size() == 1) winner(secondHighestCard.get(0));
                else
                {
                    for (int i = 0; i < secondHighestCard.size(); i++)
                    {
                        Player p = secondHighestCard.get(i);
                        Card dCard = p.hand.get(0);
                        cardVal4.add(dCard.getHighCardValueElementOne());
                    }
                    Collections.sort(cardVal4);
                    int element4Val = cardVal4.get(cardVal4.size()-1);
                    for (int i = 0; i < secondHighestCard.size(); i++)
                    {
                        Player p = secondHighestCard.get(i);
                        Card dCard = p.hand.get(0);
                        int dCardVal4 = dCard.getHighCardValueElementOne();
                        if (element4Val == dCardVal4)
                        {
                            firstHighestCard.add(p);
                        }
                    }

                    /*****************************************************************************/

                    if (firstHighestCard.size() == 1) winner(firstHighestCard.get(0));
                    else
                    {
                        for (int i = 0; i < firstHighestCard.size(); i++)
                        {
                            Player p = firstHighestCard.get(i);
                            Card dCard = p.hand.get(0);
                            cardVal5.add(dCard.getHighCardValueElementZero());
                        }
                        Collections.sort(cardVal5);
                        int element5Val = cardVal5.get(cardVal5.size()-1);
                        for (int i = 0; i < firstHighestCard.size(); i++)
                        {
                            Player p = firstHighestCard.get(i);
                            Card dCard = p.hand.get(0);
                            int dCardVal5 = dCard.getHighCardValueElementZero();
                            if (element5Val == dCardVal5)
                            {
                                zerothHighestCard.add(p);
                            }
                        }

                        /*****************************************************************************/

                        if (zerothHighestCard.size() == 1) winner(zerothHighestCard.get(0));
                        else
                        {
                            for (int i = 0; i < zerothHighestCard.size(); i++)
                            {
                                Player p = zerothHighestCard.get(i);
                                System.out.println("\n\tWell, a tie.  You are both sharing nothing.  No pairs.  No nothing.");
                                tie(p);
                            }
                        }
                    }
                }
            }
        }
    }
    public void  tieTwoKind(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        ArrayList<Integer> cardVal2 = new ArrayList<Integer>();
        ArrayList<Integer> cardVal3 = new ArrayList<Integer>();

        ArrayList<Player> secondHighestCard = new ArrayList<Player>();
        ArrayList<Player> firstHighestCard = new ArrayList<Player>();
        ArrayList<Player> zerothHighestCard = new ArrayList<Player>();

        for (int i = 0; i < tieList.size(); i++)
        {
            Player c = tieList.get(i);
            Card cCard = c.hand.get(0);
            cardVal.add(cCard.getTwoKindHighest());
        }
        Collections.sort(cardVal);
        int highestCard = cardVal.get(cardVal.size() - 1);

        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card dCard = p.hand.get(0);
            int dCardVal = dCard.getTwoKindHighest();
            if (highestCard == dCardVal)
            {
                secondHighestCard.add(p);
            }
        }
        if (secondHighestCard.size() == 1) winner(secondHighestCard.get(0));
        else
        {
            for (int i = 0; i < secondHighestCard.size(); i++)
            {
                Player c = secondHighestCard.get(i);
                Card cCard = c.hand.get(0);
                cardVal2.add(cCard.getTwoKindMiddle());
            }
            Collections.sort(cardVal2);
            int middleHighestCard = cardVal2.get(cardVal2.size() - 1);
            for (int i = 0; i < secondHighestCard.size(); i++)
            {
                Player p = secondHighestCard.get(i);
                Card dCard = p.hand.get(0);
                int dCardVal2 = dCard.getTwoKindMiddle();
                if (middleHighestCard == dCardVal2)
                {
                    firstHighestCard.add(p);
                }
            }
            if (firstHighestCard.size() == 1) winner(firstHighestCard.get(0));
            else
            {
                for (int i = 0; i < firstHighestCard.size(); i++)
                {
                    Player c = firstHighestCard.get(i);
                    Card cCard = c.hand.get(0);
                    cardVal3.add(cCard.getTwoKindLower());
                }
                Collections.sort(cardVal3);
                int lowestCard = cardVal3.get(cardVal3.size() - 1);
                for (int i = 0; i < firstHighestCard.size(); i++)
                {
                    Player p = firstHighestCard.get(i);
                    Card dCard = p.hand.get(0);
                    int dCardVal3 = dCard.getTwoKindLower();
                    if (lowestCard == dCardVal3)
                    {
                        zerothHighestCard.add(p);
                    }
                }
                if (zerothHighestCard.size() == 1) winner(zerothHighestCard.get(0));
                else
                {
                    for (int i = 0; i < zerothHighestCard.size(); i++)
                    {
                        Player p = zerothHighestCard.get(i);
                        System.out.println("\n\tA tie with a pair.  Small battles win big wars.");
                        tie(p);
                    }
                }
            }
        }
    }
    public void tieThreeKind(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            //int val = c.getThreeOfAKindValue();
            cardVal.add(c.getRankThreeOfAKindCard());
        }
        Collections.sort(cardVal);
        int highestCardThreeOfAKind = cardVal.get(cardVal.size()-1);
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            int val = c.getRankThreeOfAKindCard();
            if (highestCardThreeOfAKind == val)
            {
                System.out.println("\tWins with a three of a kind!!");
                winner(p);
            }
        }
    }
    public void tieTwoPair(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        ArrayList<Integer> cardVal2 = new ArrayList<Integer>();
        ArrayList<Integer> cardVal3 = new ArrayList<Integer>();

        ArrayList<Player> secondHighestCard = new ArrayList<Player>();
        ArrayList<Player> firstHighestCard = new ArrayList<Player>();
        ArrayList<Player> zerothHighestCard = new ArrayList<Player>();

        for (int i = 0; i < tieList.size(); i++)
        {
            Player c = tieList.get(i);
            Card cCard = c.hand.get(0);
            cardVal.add(cCard.getTwoPairUpperVal());
        }
        Collections.sort(cardVal);
        int highestCard = cardVal.get(cardVal.size() - 1);

        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card dCard = p.hand.get(0);
            int dCardVal = dCard.getTwoPairUpperVal();
            if (highestCard == dCardVal)
            {
                secondHighestCard.add(p);
            }
        }
        if (secondHighestCard.size() == 1) winner(secondHighestCard.get(0));
        else
        {
            for (int i = 0; i < secondHighestCard.size(); i++)
            {
                Player c = secondHighestCard.get(i);
                Card cCard = c.hand.get(0);
                cardVal2.add(cCard.getTwoPairLowerVal());
            }
            Collections.sort(cardVal2);
            int secondPairValue = cardVal2.get(cardVal2.size() - 1);

            for (int i = 0; i < secondHighestCard.size(); i++)
            {
                Player p = secondHighestCard.get(i);
                Card dCard = p.hand.get(0);
                int dCardVal = dCard.getTwoPairLowerVal();
                if (secondPairValue == dCardVal)
                {
                    firstHighestCard.add(p);
                }
            }
            if (firstHighestCard.size() == 1) winner(firstHighestCard.get(0));
            else
            {
                for (int i = 0; i < firstHighestCard.size(); i++)
                {
                    Player c = firstHighestCard.get(i);
                    Card cCard = c.hand.get(0);
                    cardVal3.add(cCard.getTwoPairCardVal());
                }
                Collections.sort(cardVal3);
                int twoPairSingleCardValue = cardVal3.get(cardVal3.size() - 1);

                for (int i = 0; i < firstHighestCard.size(); i++)
                {
                    Player p = firstHighestCard.get(i);
                    Card dCard = p.hand.get(0);
                    int dCardVal = dCard.getTwoPairCardVal();
                    if (twoPairSingleCardValue == dCardVal)
                    {
                        zerothHighestCard.add(p);
                    }
                }
                if (zerothHighestCard.size() == 1) winner(zerothHighestCard.get(0));
                else
                {
                    for (int i = 0; i < zerothHighestCard.size(); i++)
                    {
                        Player p = zerothHighestCard.get(i);
                        System.out.println("\n\tA Two Pair Tie.  Unlikely.  I think you're in cahoots.");
                        tie(p);
                    }
                }
            }
        }




    }
    public void tieStraight(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        ArrayList<Player> FinalTiePlayers = new ArrayList<Player>();
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            cardVal.add(c.getStraightHighestCardValue());
        }
        Collections.sort(cardVal);
        int highestOfTheStraightHighCards = cardVal.get(cardVal.size()-1);
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            int val = c.getStraightHighestCardValue();
            if (val == highestOfTheStraightHighCards)
            {
                FinalTiePlayers.add(p);
            }
        }
        if (FinalTiePlayers.size() == 1)
        {
            Player p = FinalTiePlayers.get(0);
            winner(p);
        }
        for (int i = 0; i < FinalTiePlayers.size(); i++)
        {
            Player p = FinalTiePlayers.get(i);
            System.out.println("\t\nA Straight tie.  A true rarity.  Go Play the lottery.");
            tie(p);
        }
        System.exit(0);
    }
    public void tieFlush(ArrayList<Player> tieList)
    {

    }
    public void tieFullHouse(ArrayList<Player> tieList)
    {
        ArrayList<Integer> cardVal = new ArrayList<Integer>();
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            cardVal.add(c.getFullHouseValue());
        }
        Collections.sort(cardVal);
        int highestFullHouseValue = cardVal.get(cardVal.size()-1);
        for (int i = 0; i < tieList.size(); i++)
        {
            Player p = tieList.get(i);
            Card c = p.hand.get(0);
            int val = c.getRankThreeOfAKindCard();
            if (highestFullHouseValue == val)
            {
                System.out.println("\tOh, the excitement!  The humanity! A Full House!");
                winner(p);
            }
        }
    }
    public void winner(Player winner)
    {
        System.out.println("\n\tCongratulations.  " + winner.getName() + " wins the game!");
        System.exit(0);
    }
    public void tie(Player p)
    {
        System.out.println("You are not quite a winner, " + p.getName() + ".  You have to share.");
        System.out.println("Sharing is caring.");
    }
}// END PLAYER CLASS
/*
Collections.sort(hand, new Comparator<Card>()
        {
@Override
public int compare(Card card, Card card2) {
        return card.getRank() - card2.getRank();
        }
/*
int hCard = 0;
int twoPair = 1;
int threeKind = 2;
int straight = 3;
int flush = 4;
int fullHouse = 5;
int straightFlush = 6;
*/