import nl.hanze.hive.Hive;

import java.util.*;

public class Beetle extends Tile {

    public Beetle(Hive.Player player) {
        super(player, Hive.Tile.BEETLE);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        ArrayList<Position> surroundingPositions = currentPosition.getSurroundingPositions();

        HashSet<Position> slidablePositions = new HashSet<>();
        for (Position surroundingPosition : surroundingPositions) {
            if (canSlideTo(currentPosition, surroundingPosition, board)) {
                slidablePositions.add(surroundingPosition);
            }
        }

        return slidablePositions;
    }
}