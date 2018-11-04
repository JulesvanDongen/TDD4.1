import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Spider extends Tile {

    protected Spider(Hive.Player player) {
        super(player, Hive.Tile.SPIDER);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        return getPossibleMovesHelper(board, currentPosition, currentPosition, new HashSet<Position>(), 0);
    }

    private Set<Position> getPossibleMovesHelper(Board board, Position startingPosition, Position currentPosition, Set<Position> passedPositions, int depth) {
        ArrayList<Position> surroundingPositions = currentPosition.getSurroundingPositions();
        Set<Position> possiblePositions = surroundingPositions.stream()
                .filter(p -> !board.getInternalState().keySet().contains(p)) // Tile is not occupied
                .filter(p -> !passedPositions.contains(p)) // We have not passed this tile already
                .filter(p ->  {
                    Set<Position> surroundingTilePositions = board.getSurroundingTiles(p).keySet();
                    if (surroundingTilePositions.contains(startingPosition)) {
                        return surroundingTilePositions.size() - 1 > 0;
                    } else {
                        return surroundingTilePositions.size() > 0;
                    }
                }) // The resulting position is next to a tile
                .filter(p -> canSlideTo(currentPosition, p, board)) // We can slide to this tile
                .collect(Collectors.toSet());

        if (depth == 2) {
            return possiblePositions;
        } else {
            HashSet<Position> endPositions = new HashSet<>();
            for (Position possiblePosition : possiblePositions) {
                HashSet<Position> passedPositionsCopy = new HashSet<>(passedPositions);
                passedPositionsCopy.add(possiblePosition);
                Set<Position> possibleEndPositions = getPossibleMovesHelper(board, startingPosition, possiblePosition, passedPositionsCopy, depth + 1);
                endPositions.addAll(possibleEndPositions);
            }
            return endPositions;
        }
    }
}
