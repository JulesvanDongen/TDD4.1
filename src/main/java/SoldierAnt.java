import nl.hanze.hive.Hive;

import java.util.Set;

public class SoldierAnt extends Tile {
    protected SoldierAnt(Hive.Player player) {
        super(player, Hive.Tile.SOLDIER_ANT);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return null;
    }
}
