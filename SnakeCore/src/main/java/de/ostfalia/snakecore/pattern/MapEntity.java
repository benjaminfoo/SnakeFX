package de.ostfalia.snakecore.pattern;

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

    // the type of entity which an instance of this represents
    public int kindOf = -1;

    // a callback which is used to execute some kind of action within the snake-game
    // NOTE: callbacks cant get serialized, so we have to map them to some kind of ID
    // the ID gets generated within the backend's StompServiceController.java instance
    @JsonIgnore
    public transient MapEntityAction mapEntityAction = (spieler, snake, runningGame) -> {
        System.out.println("Default action - nothing more is done");
    };

    // the kinds of different map entities are defined by generating a random number from 1 - Max number of Kinds
    // this value is generated by the backend and synchronized across all recieving clients
    public static final int MAP_ENTITY_KIND_NOT_INITIALIZED = 0;
    public static final int MAP_ENTITY_KIND_FOOD = 1;
    public static final int MAP_ENTITY_KIND_PREDATOR_ENTITY = 2;
    public static final int MAP_ENTITY_KIND_FREEZE_OTHER_PLAYERS_ENTITY = 3;

    public static final int[] MAP_ENTITY_KINDS = {
            MAP_ENTITY_KIND_FOOD,
            MAP_ENTITY_KIND_PREDATOR_ENTITY,
            MAP_ENTITY_KIND_FREEZE_OTHER_PLAYERS_ENTITY
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