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

    public Color toJavaFxColor() {
        if (javaFXColor == null) {
            javaFXColor = new Color(red, green, blue, 1);
        }
        return javaFXColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnakeColor that = (SnakeColor) o;

        if (Double.compare(that.red, red) != 0) return false;
        if (Double.compare(that.green, green) != 0) return false;
        if (Double.compare(that.blue, blue) != 0) return false;
        return javaFXColor != null ? javaFXColor.equals(that.javaFXColor) : that.javaFXColor == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(red);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(green);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(blue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (javaFXColor != null ? javaFXColor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SnakeColor{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", javaFXColor=" + javaFXColor +
                '}';
    }
}
