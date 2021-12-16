package com.example.snakegame2;

import javafx.animation.ScaleTransition;
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

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final SnakeGameNormal normalGame = new SnakeGameNormal();
    //private final SnakeGameNoWalls noWallsGame= new SnakeGameNoWalls();

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Normal snake game",normalGame
            ),
            new Pair<String, Runnable>("No Walls Snake Game", () -> {
            }),
            new Pair<String, Runnable>("Score Board",()->{}),
            new Pair<String, Runnable>("User manual", () -> {
            }),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;


    private void addBackground() throws FileNotFoundException {
        Image image = new Image( new FileInputStream("src/main/resources/MainMenuBG.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);
    }




    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

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
            addBackground();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = new Line(WIDTH/2-100, HEIGHT/3+50, WIDTH/2-100, HEIGHT/3+240);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);
        root.getChildren().add(line);
        addMenu(WIDTH/2-95, HEIGHT/3+55);
        startAnimation();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        Text title= new Text("SNAKE");
        title.setTranslateX(WIDTH/2-70);
        title.setTranslateY(HEIGHT/3);
        title.setFont(Font.font("",50));
        title.setFill(Color.WHITE);
        root.getChildren().add(title);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
