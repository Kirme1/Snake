package com.example.snakegame2;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    private final SnakeGame normalGame = new SnakeGame();

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Normal snake game",normalGame
            ),
            new Pair<String, Runnable>("Windowless snake game", () -> {
            }),
            new Pair<String, Runnable>("User manual", () -> {
            }),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;


    private void addBackground() throws FileNotFoundException {
        Image image = new Image( new FileInputStream("SnakeGame2/src/main/resources/MainMenuBG.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        root.getChildren().add(imageView);
    }

    public void addTitle() {
        Title title = new Title();
        title.snakeTitle("Snake");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);
        root.getChildren().add(title);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 170);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);
        root.getChildren().add(line);
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

    private Parent createContent() throws FileNotFoundException {
        addBackground();
        addTitle();
        int lineX = WIDTH/2 - 100;
        int lineY = HEIGHT/3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);
        startAnimation();
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
