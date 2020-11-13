package de.ostfalia.teamx.snakorino.model;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * @author Benjamin Wulfert
 * Model class for a food.
 * Contains a image which gets drawn on the screen and a position in order to determine its position.
 */
public class Food {

    public Image drawable;
    public Vector2 position;

    public Food() {
    }

    public Food(Image drawable, Vector2 position) {
        this.drawable = drawable;
        this.position = position;
    }

    public Image getDrawable() {
        return drawable;
    }

    public void setDrawable(Image drawable) {
        this.drawable = drawable;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(drawable, food.drawable) &&
                Objects.equals(position, food.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawable, position);
    }

    @Override
    public String toString() {
        return "Food{" +
                "drawable=" + drawable +
                ", position=" + position +
                '}';
    }

}
