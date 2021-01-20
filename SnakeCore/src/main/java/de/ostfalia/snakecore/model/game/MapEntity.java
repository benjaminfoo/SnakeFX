package de.ostfalia.snakecore.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.image  .Image;

import java.util.Objects;

/**
 * @author Benjamin Wulfert
 *
 * Model class for a MapEntity.
 * Contains a image which gets drawn on the screen and a position in order to determine its position.
 */
public class MapEntity {

    // the bitmap which gets drawn on the game-canvas
    public Image drawable;

    // the id of the bitmap which gets loaded (and set by the backend)
    public int drawableId;

    // the position where the bitmap gets drawn on the canvas
    public Vector2 position;

    @JsonIgnore
    public transient MapEntityAction mapEntityAction = (spieler, snake, runningGame) -> {

    };

    public MapEntity() {
    }

    public MapEntity(Image drawable, Vector2 position) {
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
        MapEntity mapEntity = (MapEntity) o;
        return drawableId == mapEntity.drawableId && Objects.equals(position, mapEntity.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawableId, position);
    }

    @Override
    public String toString() {
        return "Food{" +
                "drawable=" + drawable +
                ", drawableId=" + drawableId +
                ", position=" + position +
                '}';
    }
}
