package Snake;
import SnakeBackground.SnakeBackground;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.ArrayList;

public class Snake {
    //with inspiration from this video we have used an arraylist of points which contains the X and Y value of each body part of the snake
    //https://www.youtube.com/watch?v=H9WjCyPFOug
    private ArrayList<Point> snakeBody=new ArrayList();
    private Point snakeHead;
    private SnakeBackground backGround;
    private GraphicsContext gc;
    private Canvas canvas;

    public Snake(){
        this.backGround=new SnakeBackground();
        this.canvas = new Canvas(backGround.getWidth(),backGround.getHeight());
        this.gc = canvas.getGraphicsContext2D();
    }

    //in this method we set the size and the location of our snake when the game starts
    public void initiateSnakeBody() {
        for (int i = 0; i < 4; i++) {
            snakeBody.add(new Point(5, (int) (backGround.getRows() / 2)));
        }
        snakeHead = snakeBody.get(0);
    }

    //in this method we draw the snake with the given information for the color, shape and size
    public void drawSnake(GraphicsContext gc) {
        final double SQUARE_SIZE= backGround.getSquareSize();
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);
        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 20, 20);
        }
    }

    //getters and setters
    public Point getSnakeHead(){
        return snakeHead;
    }
    public ArrayList<Point> getSnakeBody(){
        return  snakeBody;
    }
    public void setSnakeHead(Point newPoint){
        snakeHead=newPoint;
    }
}

