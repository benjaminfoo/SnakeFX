package de.ostfalia.snakecore.model.game;

import de.ostfalia.snakecore.model.math.Vector2;

import java.util.LinkedList;
import java.util.List;

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
    public List<Vector2> body = new LinkedList<>();

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


    /**
     * Adds an element to the snakes body
     */
    public void addBodyElement() {
        body.add(new Vector2(-1, -1));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snake snake = (Snake) o;

        if (isPredator != snake.isPredator) return false;
        if (initialLength != snake.initialLength) return false;
        if (head != null ? !head.equals(snake.head) : snake.head != null) return false;
        if (body != null ? !body.equals(snake.body) : snake.body != null) return false;
        if (currentDirection != null ? !currentDirection.equals(snake.currentDirection) : snake.currentDirection != null)
            return false;
        return color != null ? color.equals(snake.color) : snake.color == null;
    }

    @Override
    public int hashCode() {
        int result = (isPredator ? 1 : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (currentDirection != null ? currentDirection.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + initialLength;
        return result;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "isPredator=" + isPredator +
                ", head=" + head +
                ", body=" + body +
                ", currentDirection=" + currentDirection +
                ", color=" + color +
                ", initialLength=" + initialLength +
                '}';
    }
}
