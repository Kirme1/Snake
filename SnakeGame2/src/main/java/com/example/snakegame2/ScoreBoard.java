package com.example.snakegame2;

import Score.ScoreManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ScoreBoard extends Application implements Runnable{

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    ScoreManager scoreManager = new ScoreManager();

    private void addMenu(VBox menuVBox) {
        ArrayList<Long> scores = scoreManager.readScores();
        for (Long score : scores) {
            Text text = new Text(score.toString());
            menuVBox.getChildren().add(text);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        addMenu(menuBox);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        Scene scene = new Scene(menuBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void run() {
        Stage primaryStage = new Stage();
        start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
