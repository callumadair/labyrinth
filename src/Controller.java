import javafx.animation.AnimationTimer;

public class Controller {

    public static void startGame(){
        //Create board players etc
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
    }

    public static void update(){
        //logic and stuff like this
    }

    public static void render(){
        //render call draw on all Objects
    }
}
