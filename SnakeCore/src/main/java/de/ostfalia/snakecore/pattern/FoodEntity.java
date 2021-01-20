package de.ostfalia.snakecore.pattern;

import de.ostfalia.snakecore.model.RunningGame;
import de.ostfalia.snakecore.model.Spieler;
import de.ostfalia.snakecore.model.game.Snake;

public class FoodEntity extends MapEntity {

    public FoodEntity(){
        this.mapEntityAction = new MapEntityAction() {
            @Override
            public void onExecute(Spieler spieler, Snake snake, RunningGame runningGame) {

            }
        };
    }

}
