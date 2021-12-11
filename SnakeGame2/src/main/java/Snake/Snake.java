package Snake;

import SnakeBackground.SnakeBackground;
import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Point> snakeBody=new ArrayList();
    private Point snakeHead;
    private SnakeBackground background;
    public Snake(){
        background=new SnakeBackground();
    }
    public void drawSnakeBody() {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, (int) (background.getRows() / 2)));
        }
        snakeHead = snakeBody.get(0);
    }
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
