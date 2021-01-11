package de.ostfalia.snakecore.model.game;

import de.ostfalia.snakecore.model.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Benjamin Wulfert
 * @author Lenoard Reidel
 *
 * The model of the snake.
 */
public class Snake {

    // for letting a snake bite of another snakes tail
    public boolean isPredator;

    // The head of the snake <=> the leading position
    public Vector2 head;

    // the body parts of the snake
    public List<Vector2> body = new ArrayList();

    // The current direction gets added to the heads position on every game tick (each iteration of the game-loop)
    public Vector2 currentDirection = Vector2.RIGHT;

    // the color of the snake
    public SnakeColor color;

    // the initial length of the snake
    public int initialLength = 2;

    public Snake() {
    }

    /**
     * Initialize the snakes head and its body parts at @param spawn
     */
    public Snake(Vector2 spawn, SnakeColor color){
        for (int i = 0; i < initialLength; i++) {
            Vector2 bodyPart = new Vector2(-1, -1);
            body.add(bodyPart);
        }
        head = body.get(0);

        head.x = spawn.x;
        head.y = spawn.y;

        this.color = color;

        isPredator = false;
    }


    /**
     * Initialize the snakes head and its body parts at @param spawn
     */
    public Snake(Vector2 spawn, SnakeColor color, int newLength){
        this.initialLength = newLength;

        for (int i = 0; i < initialLength; i++) {
            Vector2 bodyPart = new Vector2(-1, -1);
            body.add(bodyPart);
        }
        head = body.get(0);

        head.x = spawn.x;
        head.y = spawn.y;

        this.color = color;

        isPredator = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return
                Objects.equals(head, snake.head) &&
                        Objects.equals(body, snake.body) &&
                        currentDirection == snake.currentDirection &&
                        Objects.equals(color, snake.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, body, currentDirection, color);
    }

    /**
     * Adds an element to the snakes body
     */
    public void addBodyElement() {
        body.add(new Vector2(-1, -1));
    }
}
