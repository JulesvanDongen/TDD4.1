import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.Set;

public class Grasshopper extends Tile {

    public Grasshopper(Hive.Player player) {
        super(player, Hive.Tile.GRASSHOPPER);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return null; // Method stub
    }
}
