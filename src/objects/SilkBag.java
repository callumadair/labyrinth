package objects;

import java.util.Random;
import java.util.ArrayList;

/**
 * This class represents the SilkBag item in a Night Crawlers' game.
 *
 * @author Stefani Dimitrova
 */
public class SilkBag {

    private int numOfCards;
    private ArrayList<Card> listOfCards;
    private Random randomGenerator = new Random();

    /**
     * Instantiates a new SilkBag.
     *
     * @param numOfCards the number of cards inside the bag.
     */
    public SilkBag(int numOfCards) {
        this.numOfCards = numOfCards;
        listOfCards = new ArrayList<>();
    }

    /**
     * Gets list of cards.
     *
     * @return the list of cards.
     */
    public ArrayList<Card> getListOfCards() {
        return listOfCards;
    }

    /**
     * Sets list of cards.
     *
     * @param listOfCards the list of cards.
     */
    public void setListOfCards(ArrayList<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }

    /**
     * Add a card.
     *
     * @param card the card.
     */
    public void addACard(Card card) {
        listOfCards.add(card);
    }

    /**
     * Draw a card from the bag.
     *
     * @return the card.
     */
    public Card drawACard() {
        int card = randomGenerator.nextInt(listOfCards.size());
        Card randomCard = listOfCards.get(card);
        listOfCards.remove(card);
        return randomCard;
    }

    /**
     * Draw floor card from the bag for the board with random rotation.
     *
     * @return the floor card.
     */
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
