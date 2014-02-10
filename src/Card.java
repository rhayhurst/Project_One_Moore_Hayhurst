
/**
 * Created by bob on 2/4/14.
 */
public class Card {

    // knows
    private int rank;
    private int suit;
    private boolean marker;
    private boolean highCard;
    private boolean twoOfAKindCard;
    private boolean twoPairCard;
    private boolean threeOfAKindCard;
    private boolean straightCard;
    private boolean flushCard;
    private boolean fullHouseCard;
    private boolean fourOfAKindCard;
    private boolean straightFlushCard;

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

    public void setRank              (int rank)                  { this.rank = rank;}
    public void setSuit              (int suit)                  { this.suit = suit;}
    public void setMarker            (boolean marker)            { this.marker = marker;}
    public void highCard             (boolean highCard)          { this.highCard = highCard;}
    public void setTwoOfAKindCard    (boolean twoOfAKindCard)    { this.twoOfAKindCard = twoOfAKindCard;}
    public void setTwoPairCard       (boolean twoPairCard)       { this.twoPairCard = twoPairCard; }
    public void setThreeOfAKindCard  (boolean threeOfAKindCard)  { this.threeOfAKindCard = threeOfAKindCard;}
    public void setStraightCard      (boolean straightCard)      { this.straightCard = straightCard;}
    public void setFlushCard         (boolean flushCard)         { this.flushCard = flushCard;}
    public void setFullHouseCard     (boolean fullHouseCard)     { this.fullHouseCard = fullHouseCard;}
    public void setFourOfAKindCard   (boolean fourOfAKindCard)   { this.fourOfAKindCard = fourOfAKindCard;}
    public void setStraightFlushCard (boolean straightFlushCard) { this.straightFlushCard = straightFlushCard;}


    public int getSuit()                  { return this.suit;}
    public int getRank()                  { return this.rank;}
    public boolean getMarker()            { return this.marker;}
    public boolean highCard()             { return this.highCard;}
    public boolean getTwoOfAKindCard()    { return this.twoOfAKindCard;}
    public boolean getTwoPairCard()       { return this.twoPairCard;}
    public boolean getThreeOfAKindCard()  { return this.threeOfAKindCard;}
    public boolean getStraightCard()      { return this.straightCard;}
    public boolean getFlushCard()         { return this.flushCard;}
    public boolean getFullHouseCard()     { return this.fullHouseCard;}
    public boolean getFourOfAKindCard()   { return this.fourOfAKindCard;}
    public boolean getStraightFlushCard() { return this.straightFlushCard;}
}