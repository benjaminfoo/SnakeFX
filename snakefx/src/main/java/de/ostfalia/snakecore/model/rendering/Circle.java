package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.game.SnakeColor;
import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Circle implements Shape {

    Vector2 position;
    Vector2 size;
    SnakeColor snakeColor;

    public Circle() {
        size = new Vector2(32, 32);
    }

    public Circle(Vector2 size, Vector2 position, SnakeColor snakeColor) {
        this.position = position;
        this.size = size;
        this.snakeColor = snakeColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(snakeColor.toJavaFxColor());
        gc.fillOval(position.x, position.y, 32, 32);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

}
