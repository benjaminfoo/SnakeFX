package de.ostfalia.teamx.snakorino.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    public Vector2 head;

    public List<Vector2> body = new ArrayList();

    public Snake() {
        for (int i = 0; i < 3; i++) {
            Vector2 bodyPart = new Vector2(0, 0);
            body.add(bodyPart);
        }
        head = body.get(0);
    }

    public void moveright() {
        head.x++;
    }
    public void moveLeft() {
        head.x--;
    }
    public void moveUp() {
        head.y--;
    }
    public void moveDown() {
        head.y++;
    }


}
