package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Circle implements Shape {

    Vector2 position;
    Vector2 size;

    public Circle() {
        size = new Vector2(32, 32);
    }

    public Circle(Vector2 size, Vector2 position) {
        this.position = position;
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillOval(position.x, position.y, 32, 32);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

}
