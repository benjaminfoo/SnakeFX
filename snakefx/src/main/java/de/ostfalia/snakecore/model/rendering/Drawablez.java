package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Drawablez implements Shape {

    Vector2 position;
    String drawablePath;

    private Image javafxImage;

    public Drawablez(Vector2 position, String drawablePath) {
        this.position = position;
        this.drawablePath = drawablePath;
        this.javafxImage = new Image(this.drawablePath);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(javafxImage, position.x, position.y);
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

}
