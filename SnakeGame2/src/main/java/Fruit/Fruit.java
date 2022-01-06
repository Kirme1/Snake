package Fruit;

import SnakeBackground.SnakeBackground;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Fruit {

    private ArrayList<Image> fruits;
    private Image fruitImage;
    private int fruitX;
    private int fruitY;
    private GraphicsContext gc;
    private Canvas canvas;
    private SnakeBackground backGround;

    public Fruit(){
        this.fruits = new ArrayList<>();
        this.canvas= new Canvas();
        this.gc= canvas.getGraphicsContext2D();
        this.backGround= new SnakeBackground();
    }

    //in this method we add all the pictures of fruits to the arraylist of images
    //the pictures are from : https://www.youtube.com/watch?v=H9WjCyPFOug
    public void addFruits() throws FileNotFoundException {
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_apple.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_berry.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_cherry.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_coconut_.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_orange.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_peach.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_pomegranate.png")));
        fruits.add(new Image(new FileInputStream("SnakeGame2/src/main/java/Fruit/images/ic_watermelon.png")));
    }

    //in this method we randomly set a X and a Y value for the coordinate of the fruit
    //and also we choose a random fruit out of our arraylist of fruits
    public void produceFruit() throws FileNotFoundException {
        fruitX = (int) (Math.random() * backGround.getRows());
        fruitY = (int) (Math.random() * backGround.getColumns());
        addFruits();
        fruitImage = fruits.get((int) (Math.random() * fruits.size()));
    }

    //in this method we draw the selected fruit with given information for the size and location of the fruit
    public void drawFruit(GraphicsContext gc) {
        final double SQUARE_SIZE = backGround.getSquareSize();
        gc.drawImage(fruitImage, fruitX * SQUARE_SIZE, fruitY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    //getters
    public int getFruitX(){
        return fruitX;
    }
    public int getFruitY(){
        return fruitY;
    }
}
