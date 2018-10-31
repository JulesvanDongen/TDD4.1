import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    @Tag("2d")
    void whenTwoDifferentTilesOfSamePlayerAndSameCharacterThenNotEqual(){
        Tile t1 = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);
        Tile t2 = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);

        assertFalse(t1.equals(t2));
    }

}