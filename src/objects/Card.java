package objects;

import javafx.scene.image.Image;

/**
 * The Card class represents the abstract card type.
 *
 * @author Kacper Lisikiewicz
 */
public abstract class Card {

    private Image image;
    private String png = ".png";

    /**
     * Use card boolean.
     *
     * @param board the board.
     * @param x     the x coordinate.
     * @param y     the y coordinate.
     * @return true if the card can be used.
     */
    public abstract boolean useCard(Board board, int x, int y);

    /**
     * Get the image of the card.
     *
     * @return the image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Set image.
     *
     * @param imagePath the image path.
     */
    public void setImage(String imagePath) {
        this.image = new Image(imagePath);
    }

    /**
     * Set image.
     *
     * @param imagePath the image path.
     * @param rotation  the rotation of the image.
     */
    public void setImage(String imagePath, int rotation) {
        this.image = new Image(imagePath + rotation + png);
    }
}