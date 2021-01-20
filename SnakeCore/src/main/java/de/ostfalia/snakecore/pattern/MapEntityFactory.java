package de.ostfalia.snakecore.pattern;

import de.ostfalia.snakecore.model.math.Vector2;
import de.ostfalia.snakecore.util.GameResources;
import javafx.scene.image.Image;

/**
 * @author Benjamin Wulfert
 *
 * The Factory which generates instances of MapEntities (food-elements or power-ups)
 * This factory recieves pre-generated data from the backend.
 */
public class MapEntityFactory {

    /**
     * Creates a map-entity instance with a specialized derived class instance
     * @param kindOfEntity - the pre computed kind of entity
     * @param drawableId - the bitmap id which is used to select the drawable from the resource folder
     * @param preGeneratedPositionVector - the vector which is calculated by the backend to determine a unique / collision-free position (non overlapping with other entities or players)
     * @return mapEntity with some kind of derived class instance
     */
    public MapEntity create(int kindOfEntity, int drawableId, Vector2 preGeneratedPositionVector){

        MapEntity result = null;


        // because callbacks and their kind cant get serialized with json / jackson we have to reconstruct them based on their entity kind
        if(kindOfEntity == MapEntity.MAP_ENTITY_KIND_FOOD ){ // = 1
            result = new FoodEntity();
            result.position = preGeneratedPositionVector;
            result.drawable = new Image(GameResources.FOOD_IMAGE_PATHS[drawableId]);
        }

        if(kindOfEntity == MapEntity.MAP_ENTITY_KIND_PREDATOR_ENTITY ){ // = 2
            result = new PredatorEntity();
            result.position = preGeneratedPositionVector;
            result.drawable = new Image(GameResources.POWER_UP_PREDATOR);
        }

        if(kindOfEntity == MapEntity.MAP_ENTITY_KIND_FREEZE_OTHER_PLAYERS_ENTITY ){ // = 3
            result = new FreezeOtherPlayersEntity();
            result.position = preGeneratedPositionVector;
            result.drawable = new Image(GameResources.POWER_UP_FREEZE_OTHERS);
        }

        return result;
    }

}
