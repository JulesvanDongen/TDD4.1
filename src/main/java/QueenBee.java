import nl.hanze.hive.Hive;

import java.util.Set;
import java.util.stream.Collectors;

public class QueenBee extends Tile {
    protected QueenBee(Hive.Player player) {
        super(player, Hive.Tile.QUEEN_BEE);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return currentPosition.getSurroundingPositions().stream()   // alle posities die aanliggend zijn
                .filter(p -> !board.positionHasTile(p))             // alle posities die leeg zijn
                .filter(p -> this.canSlideTo(currentPosition, p, board)) // requirement voor alle tiles
                .collect(Collectors.toSet());
    }
}
