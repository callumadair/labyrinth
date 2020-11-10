package objects;

import javafx.scene.image.Image;

public class FloorCard extends Card{

    //needs to be defined here
    private String straightImagePath;
    private String cornerImagePath;
    private String tshapedImagePath;
    private String goalImagePath;

    private Image image;
    private TileState state = TileState.NORMAL;
    private TileType type;
    private int rotation; //90, 180, 270,
    private int x;
    private int y;

    public FloorCard(String type, int rotation){
        switch(type){
            case "STRAIGHT":
                this.type = TileType.STRAIGHT;
                break;
            case "CORNER":
                this.type = TileType.CORNER;
                break;
            case "T_SHAPED":
                this.type = TileType.T_SHAPED;
                break;
            case "GOAL":
                this.type = TileType.GOAL;
                break;
        }

        this.rotation = rotation;

        switch (this.type){
            case STRAIGHT:
                image = new Image(straightImagePath);
                break;
            case CORNER:
                image = new Image(cornerImagePath);
                break;
            case T_SHAPED:
                image = new Image(tshapedImagePath);
                break;
            case GOAL:
                image = new Image(goalImagePath);
                break;
        }
    }

    public void setRotation(int rotation){
        //if(rotation == 0 | 90 | 180 | 270) {
        //    this.rotation = rotation;
        //}
    }

    public int getRotation(){
        return this.rotation;
    }

    public TileState getState(){
        return state;
    }

    public enum TileState{
        FROZEN, INFLAMED, NORMAL;
    }

    public enum TileType{
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }
}
