import nl.hanze.hive.Hive;

import java.util.Set;

public class Spider extends Tile {

    protected Spider(Hive.Player player) {
        super(player, Hive.Tile.SPIDER);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return null;
    }
}
