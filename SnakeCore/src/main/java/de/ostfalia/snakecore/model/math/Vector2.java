package de.ostfalia.snakecore.model.math;

import java.util.Objects;

/**
 * @author Benjamin Wulfert
 *
 * A two-component vector used to realize position calculation and movement of the snakes, food and powerups.
 */
public class Vector2 {

    public static Vector2 ZERO = new Vector2(0,0);
    public static Vector2 NOT_INITIALIZED = new Vector2(-1,-1);

    // public static Vector2 UP = new Vector2(0,1);
    // public static Vector2 DOWN = new Vector2(0,-1);
    public static Vector2 LEFT = new Vector2( -1,0);
    public static Vector2 RIGHT = new Vector2(1,0);

    // Because the coordinate-systems x-axis is flipped, we also have to flip these
    public static Vector2 UP = new Vector2(0,-1);
    public static Vector2 DOWN = new Vector2(0,1);


    public int x, y;

    public Vector2() {
    }

    public Vector2(Vector2 copy){
        this.x = copy.x;
        this.y = copy.y;
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 add(Vector2 vector2){
        x += vector2.x;
        y += vector2.y;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x &&
                y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
