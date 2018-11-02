import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void whenPlayerCreatedThenPlayerHasOneQueenBee(){
        Tile queenBee = new Tile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE);
        Player p = new Player(Hive.Player.BLACK);
        List<Tile> hand = p.availableTiles();
        int amountQueens = (int) hand.stream()
                .filter(t -> t.sameKind(queenBee))
                .count();
        assertEquals(1, amountQueens);
    }

    @Test
    void whenPlayerCreatedThenPlayerHasElevenTiles(){
        Player p = new Player(Hive.Player.BLACK);
        assertEquals(11, p.availableTiles().size());
    }

    @Test
    void whenPlayerCreatedThenPlayerHasTwoSpider(){
        Tile spider = new Tile(Hive.Player.BLACK, Hive.Tile.SPIDER);
        Player p = new Player(Hive.Player.BLACK);
        List<Tile> hand = p.availableTiles();
        int amountSpider = (int) hand.stream()
                .filter(t -> t.sameKind(spider))
                .count();
        assertEquals(2, amountSpider);
    }

    @Test
    void whenTilePlayedThenTileRemovedFromHand(){
        Player p = new Player(Hive.Player.WHITE);
        int initGrassHopperCount = 3;
        try {
            p.playTile(Hive.Tile.GRASSHOPPER);
        } catch (NoSuchTileException e) {
            fail("Tile Should be available");
        }

        List<Tile> availableTiles = p.availableTiles();

        int actual = (int) availableTiles.stream()
                .filter(t -> t.sameKind(Hive.Tile.GRASSHOPPER))
                .count();

        assertEquals(initGrassHopperCount - 1, actual);

    }

}