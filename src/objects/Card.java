package objects;

import javafx.scene.image.Image;

public abstract class Card {

    private Image image;
    private String png = ".png";

    public abstract boolean useCard(Board board, int x, int y);

    public Image getImage(){
        return this.image;
    }

    public void setImage(String imagePath){
        this.image = new Image(imagePath);
    }

    public void setImage(String imagePath, int rotation){
        this.image = new Image(imagePath + rotation + png);
    }
}
