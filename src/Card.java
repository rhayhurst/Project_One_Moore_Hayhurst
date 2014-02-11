
/**
 * Created by bob on 2/4/14.
 */
public class Card {
    public String getCardText()
    {
        // class that concatenates strings, has method called append
        StringBuilder cardText = new StringBuilder();

        if      (this.rank == 11)  cardText.append("J");
        else if (this.rank == 12)  cardText.append("Q");
        else if (this.rank == 13)  cardText.append("K");
        else if (this.rank == 14)  cardText.append("A");
        else   //this.rank == whatever the integer is
        {
            cardText.append(rank);
        }

        //System.out.print("\t");
        if      (this.suit == 1)  cardText.append("C");
        else if (this.suit == 2)  cardText.append("D");
        else if (this.suit == 3)  cardText.append("H");
        else                      cardText.append("S");
        return cardText.toString();
    }

    private int rank;
    private int suit;
    private boolean marker;
    public void setRank                 (int rank)                  { this.rank = rank;}
    public void setSuit                 (int suit)                  { this.suit = suit;}
    public void setMarker               (boolean marker)            { this.marker = marker;}
    public int getSuit()                  { return this.suit;}
    public int getRank()                  { return this.rank;}
    public boolean getMarker()            { return this.marker;}


    private int valueTwoOfAKindPair;
    private int twoKindHighest;
    private int twoKindMiddle;
    private int twoKindLower;
    public void setValueTwoOfAKindPair (int valueTwoOfAKindPair)  { this.valueTwoOfAKindPair = valueTwoOfAKindPair;}
    public void setTwoKindHighest      (int twoKindHighest)       { this.twoKindHighest      = twoKindHighest;}
    public void setTwoKindMiddle       (int twoKindMiddle)        { this.twoKindMiddle       = twoKindMiddle;}
    public void setTwoKindLower        (int twoKindLower)         { this.twoKindLower        = twoKindLower;}
    public int getValueTwoOfAKindPair()   { return this.valueTwoOfAKindPair;}
    public int getTwoKindHighest()        { return this.twoKindHighest;}
    public int getTwoKindMiddle()         { return this.twoKindMiddle;}
    public int getTwoKindLower()          { return this.twoKindLower;}

    private int rankThreeOfAKindCard;
    public void setRankThreeOfAKindCard (int rankThreeOfAKindCard)  { this.rankThreeOfAKindCard = rankThreeOfAKindCard;}
    public int getRankThreeOfAKindCard()  { return this.rankThreeOfAKindCard;}

    private int twoPairLowerVal;
    private int twoPairUpperVal;
    private int twoPairCardVal;

    public void setTwoPairLowerVal(int twoPairLowerVal) { this.twoPairLowerVal = twoPairLowerVal;}
    public void setTwoPairUpperVal(int twoPairUpperVal) { this.twoPairUpperVal = twoPairUpperVal;}
    public void setTwoPairCardVal(int twoPairCardVal) { this.twoPairCardVal = twoPairCardVal;}
    public int getTwoPairLowerVal() { return twoPairLowerVal;}
    public int getTwoPairUpperVal() { return twoPairUpperVal;}
    public int getTwoPairCardVal()  { return twoPairCardVal;}

    private int straightHighestCardValue;
    public int getStraightHighestCardValue() { return straightHighestCardValue;}
    public void setStraightHighestCardValue(int straightHighestCardValue) {
        this.straightHighestCardValue = straightHighestCardValue;}

    private int flushCardZero;
    private int flushCardOne;
    private int flushCardTwo;
    private int flushCardThree;
    private int flushCardFour;
    public int getFlushCardZero() {return flushCardZero;}
    public void setFlushCardZero(int flushCardZero) {this.flushCardZero = flushCardZero;}
    public int getFlushCardOne() { return flushCardOne;}
    public void setFlushCardOne(int flushCardOne) { this.flushCardOne = flushCardOne;}
    public int getFlushCardTwo() { return flushCardTwo;}
    public void setFlushCardTwo(int flushCardTwo) { this.flushCardTwo = flushCardTwo;}
    public int getFlushCardThree() { return flushCardThree;}
    public void setFlushCardThree(int flushCardThree) { this.flushCardThree = flushCardThree;}
    public int getFlushCardFour() { return flushCardFour;}
    public void setFlushCardFour(int flushCardFour) { this.flushCardFour = flushCardFour;}

    private int threeOfAKindValue;
    public int getThreeOfAKindValue() { return threeOfAKindValue;}
    public void setThreeOfAKindValue(int threeOfAKindValue) { this.threeOfAKindValue = threeOfAKindValue;}

    private int fullHouseValue;
    public int getFullHouseValue() { return fullHouseValue;}
    public void setFullHouseValue(int fullHouseValue) { this.fullHouseValue = fullHouseValue;}

    public int straightFlushHighestCardValue;
    public int getStraightFlushHighestCardValue() { return straightFlushHighestCardValue;}
    public void setStraightFlushHighestCardValue(int straightFlushHighestCardValue) {
        this.straightFlushHighestCardValue = straightFlushHighestCardValue;}

    private int highCardValueElementFour;
    private int highCardValueElementThree;
    private int highCardValueElementTwo;
    private int highCardValueElementOne;
    private int highCardValueElementZero;
    public int getHighCardValueElementFour() {return highCardValueElementFour;}
    public void setHighCardValueElementFour(int highCardValueElementFour) {
        this.highCardValueElementFour = highCardValueElementFour;}
    public int getHighCardValueElementThree() {return highCardValueElementThree;}
    public void setHighCardValueElementThree(int highCardValueElementThree) {
        this.highCardValueElementThree = highCardValueElementThree;}
    public int getHighCardValueElementTwo() { return highCardValueElementTwo;}
    public void setHighCardValueElementTwo(int highCardValueElementTwo) {
        this.highCardValueElementTwo = highCardValueElementTwo;}
    public int getHighCardValueElementOne() { return highCardValueElementOne;}
    public void setHighCardValueElementOne(int highCardValueElementOne) {
        this.highCardValueElementOne = highCardValueElementOne;}
    public int getHighCardValueElementZero() {  return highCardValueElementZero;}
    public void setHighCardValueElementZero(int highCardValueElementZero) {
        this.highCardValueElementZero = highCardValueElementZero;}
}




