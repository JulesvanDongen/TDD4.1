import nl.hanze.hive.Hive;

import java.util.Set;

public class QueenBee extends Tile {
    protected QueenBee(Hive.Player player) {
        super(player, Hive.Tile.QUEEN_BEE);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return null;
    }
}
