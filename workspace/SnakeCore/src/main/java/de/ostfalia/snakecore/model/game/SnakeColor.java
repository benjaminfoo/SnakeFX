package de.ostfalia.snakecore.model.game;

import javafx.scene.paint.Color;

/**
 * @author Benjamin Wulfert
 * A special color class because javafx-color objects cant get serialized
 */
public class SnakeColor {

    public double red, green, blue;

    private transient Color javaFXColor;

    public SnakeColor() {
        red = 0.5d;
        green = 0.5d;
        blue = 0.5d;
    }

    public SnakeColor(Color fxColor){
        red = fxColor.getRed();
        green = fxColor.getGreen();
        blue = fxColor.getBlue();
    }

    public SnakeColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public SnakeColor fromJavaFXColor(Color color){
        return new SnakeColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color toJavaFxColor(){
        if(javaFXColor == null){
            javaFXColor = new Color(red, green, blue, 1);
        }
        return javaFXColor;
    }

}
