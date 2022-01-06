package com.example.snakegame2;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends Pane {
    private Text text;
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1,1,3);

    public MenuItem(String name) {
        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.font(("SnakeGame2/src/main/resources/cour.ttf"), 20));
        text.setFill(Color.WHITE);
        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );
        getChildren().addAll(text);
    }
    public void setOnAction(Runnable action){
        setOnMouseClicked(e -> action.run());
    }
}