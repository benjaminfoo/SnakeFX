package de.ostfalia.snakecore.model.rendering;

import de.ostfalia.snakecore.model.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Text implements Shape {

    public String text;
    public Vector2 position;

    public Text() {
        text = "Text-Shape";
    }

    public Text(Vector2 position, String text) {
        this.position = position;
        this.text = text;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillText(text, position.x, position.y);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

}
