import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void whenTwoDifferentTilesOfSamePlayerAndSameCharacterThenNotEqual(){
        Tile t1 = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);
        Tile t2 = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);

        assertFalse(t1.equals(t2));
    }

}