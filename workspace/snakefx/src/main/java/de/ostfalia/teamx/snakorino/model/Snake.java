package de.ostfalia.teamx.snakorino.model;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lenoard Reidel
 * @author Benjamin Wulfert
 *
 * The model of the snake.
 */
public class Snake {

    public Vector2 head;

    public List<Vector2> body = new ArrayList();

    // The current direction gets added to the heads position on every game tick (each iteration of the game-loop)
    public Vector2 currentDirection = Vector2.RIGHT; // Snake property

    public Color color;

    public boolean isNPC;
    public int currentInputButton = 0;

    public int initialLength = 10;

    /**
     * Initialize the snakes head and its body parts at @param spawn
     */
    public Snake(Vector2 spawn, Color color){
        for (int i = 0; i < initialLength; i++) {
            Vector2 bodyPart = new Vector2(-1, -1);
            body.add(bodyPart);
        }
        head = body.get(0);

        head.x = spawn.x;
        head.y = spawn.y;

        /*
        for (Vector2 bodyPartPosition : body) {
            bodyPartPosition.x = spawn.x;
            bodyPartPosition.y = spawn.y;
        }
        */

        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return isNPC == snake.isNPC &&
                currentInputButton == snake.currentInputButton &&
                Objects.equals(head, snake.head) &&
                Objects.equals(body, snake.body) &&
                currentDirection == snake.currentDirection &&
                Objects.equals(color, snake.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, body, currentDirection, color, isNPC, currentInputButton);
    }
}
