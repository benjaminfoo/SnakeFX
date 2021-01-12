package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.Set;

public class CompositeShape implements Shape {

    public Vector2 position;
    private Set<Shape> shapes;

    public CompositeShape(Vector2 position) {
        this.position = position;
        this.shapes = new HashSet<>();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public Set<Shape> getShapes() {
        return shapes;
    }

    public int size() {
        return shapes.size();
    }
}
