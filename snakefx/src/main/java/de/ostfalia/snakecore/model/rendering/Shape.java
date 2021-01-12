package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public interface Shape {

    void draw(GraphicsContext gc);

    Vector2 getPosition();

}
