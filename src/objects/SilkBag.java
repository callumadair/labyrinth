package objects;

import java.util.Random;
import java.util.ArrayList;

public class SilkBag {

    private int numOfCards;
    private ArrayList<Card> listOfCards;
    private Random randomGenerator = new Random();

    public SilkBag(int numOfCards) {
        this.numOfCards = numOfCards;
        listOfCards = new ArrayList<>();
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

    public void addACard(Card card) {
        listOfCards.add(card);
    }

    public Card drawACard() {
        int card = randomGenerator.nextInt(listOfCards.size());
        Card randomCard = listOfCards.get(card);
        listOfCards.remove(card);
        return randomCard;
    }

    public FloorCard drawFloorCard() {
        int card = randomGenerator.nextInt(listOfCards.size());
        Card randomCard = listOfCards.get(card);
        while (!randomCard.getClass().getSimpleName().equals("FloorCard")) {
            card = randomGenerator.nextInt(listOfCards.size());
            randomCard = listOfCards.get(card);
        }
        listOfCards.remove(card);
        FloorCard floorCard = (FloorCard) randomCard;
        floorCard.setRandomRotation();
        return floorCard;
    }
}
