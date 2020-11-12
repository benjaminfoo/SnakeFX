package de.ostfalia.teamx.snakorino.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    public Point head;
    public List<Point> body = new ArrayList();
    {
        for (int i = 0; i < 3; i++) {
            Point bodyPart = new Point(0, 0);
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
