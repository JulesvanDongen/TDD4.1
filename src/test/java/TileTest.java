import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    @Tag("2d")
    void whenTwoDifferentTilesOfSamePlayerAndSameCharacterThenNotEqual(){
        Tile t1 = new Beetle(Hive.Player.BLACK);
        Tile t2 = new Beetle(Hive.Player.BLACK);

        assertFalse(t1.equals(t2));
    }

}