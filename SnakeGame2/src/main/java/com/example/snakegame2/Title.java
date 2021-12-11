package com.example.snakegame2;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Title extends Pane {
    private Text text;

    public void snakeTitle(String name) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }
        text = new Text(spread);
        text.setFont(Font.font("SnakeGame2/src/main/resources/cour.ttf", 40));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(30, Color.BLACK));
        getChildren().addAll(text);
    }
    public double getTitleWidth() {return text.getLayoutBounds().getWidth();}
    public double getTitleHeight() {return text.getLayoutBounds().getHeight();}
}
