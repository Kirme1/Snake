package com.example.snakegame2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Menu extends Application {

    //the format of the menu box was inspired by this video :https://www.youtube.com/watch?v=N2EmtYGLh4U
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private final SnakeGameNormal normalGame = new SnakeGameNormal();
    private final SnakeGameNoWalls noWallsGame= new SnakeGameNoWalls();
    private final ScoreBoard scoreBoard = new ScoreBoard();

    //here we make it possible to choose from the options in the menu and start the games
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Normal snake game",normalGame
            ),
            new Pair<String, Runnable>("No Walls Snake Game", noWallsGame),
            new Pair<String, Runnable>("Score Board", scoreBoard),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    private Pane root = new Pane();
    private VBox menuBox = new VBox(0);
    private Line line;

    //in this method we draw the background image for the menu page
    //the picture is from :https://twitter.com/namatnieks/status/960133231185203201
    private void drawBackground() throws FileNotFoundException {
        Image image = new Image( new FileInputStream("SnakeGame2/src/main/resources/MainMenuBG.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);
    }

    //creates the menu box and its movement when the game is launched
    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);
            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());
            item.setClip(clip);
            menuBox.getChildren().addAll((item));
        });
        root.getChildren().add(menuBox);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        try {
            drawBackground();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = new Line(WIDTH/2-100, HEIGHT/3+50, WIDTH/2-100, HEIGHT/3+270);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);
        root.getChildren().add(line);
        addMenu(WIDTH/2-95, HEIGHT/3+55);
        for (int i = 0; i < menuBox.getChildren().size(); i++) {
            Node node = menuBox.getChildren().get(i);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), node);
            transition.setToX(0);
            transition.setOnFinished(e2 -> node.setClip(null));
            transition.play();}

        Scene scene = new Scene(root);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        Text title= new Text("SNAKE");
        title.setTranslateX(WIDTH/2-85);
        title.setTranslateY(HEIGHT/3);
        title.setFont(Font.font("",50));
        title.setFill(Color.WHITE);
        root.getChildren().add(title);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
