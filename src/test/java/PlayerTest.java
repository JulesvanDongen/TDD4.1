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
                .filter(t -> t.equals(queenBee))
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
                .filter(t -> t.equals(spider))
                .count();
        assertEquals(1, amountSpider);
    }

    @Test
    void whenCardPlayedThenCardRemovedFromHand(){

    }

}
