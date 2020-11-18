import objects.Card;
import java.util.Random;
import java.util.ArrayList;

public class SilkBag {

    private int numOfCards;
    private ArrayList<Card> listOfCards;
    private Random randomGenerator = new Random();

    public SilkBag(int numOfCards) {
        this.numOfCards = numOfCards;
    }
    public int getNumOfCards() {
        return numOfCards;
    }

    public void setNumOfCards(int numOfCards) {
        this.numOfCards = numOfCards;
    }

    public ArrayList<Card> getListOfCards() {
        return listOfCards;
    }

    public void setListOfCards(ArrayList<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }

    public void addACard(Card card){
      //  listOfCards.add()
    }
    public Card drawACard(){
        int card = randomGenerator.nextInt(listOfCards.size());
        Card randomCard =  listOfCards.get(card);
        return randomCard;
    }
}
