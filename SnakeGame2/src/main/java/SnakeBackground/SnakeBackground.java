package SnakeBackground;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SnakeBackground {
    private static final int WIDTH =800;
    private static final int HEIGHT =WIDTH;
    private static final int ROWS=20;
    private static final int COLUMNS =ROWS;
    private static final int SQUARE_SIZE= WIDTH /ROWS;
    private GraphicsContext gc;
    private Canvas canvas;
    private final int backgroundType = (int) ((Math.random() * (4 - 1)) + 1);

    public SnakeBackground(){
        this.canvas = new Canvas();
        this.gc = canvas.getGraphicsContext2D();

    }

    public void drawBackground(GraphicsContext gc) throws FileNotFoundException {

        Image image = new Image(new FileInputStream("SnakeGame2/src/main/resources/com/example/snakegame2/Background_Green.png"));

        if (backgroundType == 1){
            image = new Image(new FileInputStream("SnakeGame2/src/main/resources/com/example/snakegame2/Background_Green.png"));
        }
        if (backgroundType == 2)
        {
            image = new Image(new FileInputStream("SnakeGame2/src/main/resources/com/example/snakegame2/Background_Blue.png"));
        }
        if (backgroundType == 3)
        {
            image = new Image(new FileInputStream("SnakeGame2/src/main/resources/com/example/snakegame2/Background_Orange.png"));
        }


        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        gc.drawImage(image, 0, 0);
    }

    public double getWidth(){
        return WIDTH;
    }
    public double getHeight(){
        return HEIGHT;
    }
    public double getRows(){
        return ROWS;
    }
    public double getColumns(){
        return COLUMNS;
    }
    public double getSquareSize(){return SQUARE_SIZE;}
}
