package com.example.snakegame2;

import Direction.Direction;
import Fruit.Fruit;
import Snake.Snake;
import SnakeBackground.SnakeBackground;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SnakeGame extends Application implements Runnable{

    private SnakeBackground background;
    private Snake snake;
    private Fruit fruit;
    private GraphicsContext gc;
    private boolean gameOver;
    private Direction currentDirection=Direction.DOWN;
    private int score=0;
    private boolean gamePaused = false;
    private static int cycleCount = Animation.INDEFINITE;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Snake");
        background=new SnakeBackground();
        Group root=new Group();
        Canvas canvas=new Canvas(background.getWidth(),background.getHeight());
        root.getChildren().add(canvas);
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(false);   //false??
        stage.show();
        gc=canvas.getGraphicsContext2D();
        Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            try {
                run(gc);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeLine.setCycleCount(cycleCount);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if(code == KeyCode.SPACE){
                    pauseGame(timeLine);
                }
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event){
                KeyCode code=event.getCode();
                if(code==KeyCode.RIGHT|| code==KeyCode.D){
                    if(!currentDirection.equals(Direction.LEFT)){
                        currentDirection=Direction.RIGHT;
                    }
                }
                else if(code==KeyCode.LEFT||code==KeyCode.A){
                    if(!currentDirection.equals(Direction.RIGHT)){
                        currentDirection=Direction.LEFT;
                    }
                }
                else if(code==KeyCode.UP||code==KeyCode.W){
                    if(!currentDirection.equals(Direction.DOWN)){
                        currentDirection=Direction.UP;
                    }
                }
                else if(code==KeyCode.DOWN||code==KeyCode.S){
                    if(!currentDirection.equals(Direction.UP)){
                        currentDirection=Direction.DOWN;
                    }

                }
                else if(gameOver && code == KeyCode.ENTER){
                    try {
                        restart();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if(code == KeyCode.ESCAPE){
                    stage.close();
                    gameOver = false;
                }
            }
        });
        snake=new Snake();
        fruit = new Fruit();
        fruit.produceFruit();
        snake.drawSnakeBody();
        timeLine.play();

    }
    public void run(GraphicsContext gc) throws IOException {
        if(gameOver){
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7",70));
            gc.fillText("Game Over", background.getWidth()/3.5,background.getHeight()/2);
            return;
        }
        drawBackground(gc);
        fruit.drawFruit(gc);
        drawSnake(gc);
        drawScore();
        for(int i=snake.getSnakeBody().size()-1;i>=1;i--){
            snake.getSnakeBody().get(i).x=snake.getSnakeBody().get(i-1).x;
            snake.getSnakeBody().get(i).y=snake.getSnakeBody().get(i-1).y;
        }


        switch (currentDirection){
            case RIGHT :
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;

        }
        gameOver();
        eatFruit();
    }

    public void drawBackground(GraphicsContext gc){
        final double SQUARE_SIZE=background.getSquareSize();
        for(int i=0; i<background.getRows();i++){
            for(int j=0; j<background.getColumns();j++){
                if((i+j)%2==0){
                    gc.setFill(Color.web("AAD751"));
                }
                else{
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i*SQUARE_SIZE,j*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
            }
        }
    }
    private void drawSnake(GraphicsContext gc){
        final double SQUARE_SIZE=background.getSquareSize();
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(snake.getSnakeHead().getX()*SQUARE_SIZE,snake.getSnakeHead().getY()*SQUARE_SIZE,SQUARE_SIZE-1,SQUARE_SIZE-1,35,35);
        for(int i=1;i<snake.getSnakeBody().size();i++){
            gc.fillRoundRect(snake.getSnakeBody().get(i).getX()*SQUARE_SIZE,snake.getSnakeBody().get(i).getY()*SQUARE_SIZE,SQUARE_SIZE-1,SQUARE_SIZE-1,20,20);
        }
    }
    private void moveRight(){
        snake.getSnakeHead().x++;
    }
    private void moveLeft(){
        snake.getSnakeHead().x--;
    }
    private void moveUp(){
        snake.getSnakeHead().y--;
    }
    private void moveDown(){
        snake.getSnakeHead().y++;
    }

    public void gameOver(){
        Point snakeHead= snake.getSnakeHead();
        final  double SQUARE_SIZE=background.getSquareSize();
        if(snakeHead.x<0||snakeHead.y<0||snakeHead.x*SQUARE_SIZE>=background.getWidth()||snakeHead.y*SQUARE_SIZE>=background.getHeight()){
            gameOver=true;
        }
        for(int i=1; i<snake.getSnakeBody().size();i++){
            if(snakeHead.x==snake.getSnakeBody().get(i).getX()&&snakeHead.getY()==snake.getSnakeBody().get(i).getY()){
                gameOver=true;
                break;
            }
        }
    }
    public void eatFruit() throws IOException {
        if (snake.getSnakeHead().getX() == fruit.getFruitX() && snake.getSnakeHead().getY() == fruit.getFruitY()) {
            snake.getSnakeBody().add(new Point(-1, -1));
            start:
            while (true) {
                fruit.produceFruit();
                for (Point snake : snake.getSnakeBody()) {
                    if (snake.getX() == fruit.getFruitX() && snake.getY() == fruit.getFruitY()) {
                        continue start;
                    }
                }
                break;
            }
            score += 5;
        }
    }
    private void  drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7",35));
        gc.fillText("Score: "+score,10,35);

    }
    private void restart() throws FileNotFoundException {
        gameOver = false;
        currentDirection = Direction.DOWN;
        snake.getSnakeBody().clear();
        score = 0;
        for (int i=0; i<3; i++) {
            snake.getSnakeBody().add(new Point(4, (int) (background.getRows()/2)));
        }
        snake.setSnakeHead(snake.getSnakeBody().get(0));
        cycleCount += 1;
    }
    private void pauseGame(Timeline timeline){
        if(!gamePaused){
            timeline.pause();
            gamePaused = true;
        }
        else {
            timeline.play();
            gamePaused = false;
        }
    }
    public static void main(String[] args) {
        launch();
        // JSONObject obj= new JSONObject();
    }

    @Override
    public void run() {
        Stage stage = new Stage();
        try {
            if(gameOver){
                gameOver = false;
                currentDirection = Direction.RIGHT;
                snake.getSnakeBody().clear();
                cycleCount += 1;
            }
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
