import nl.hanze.hive.Hive;

import java.util.*;
import java.util.stream.Collectors;

public class SoldierAnt extends Tile {
    protected SoldierAnt(Hive.Player player) {
        super(player, Hive.Tile.SOLDIER_ANT);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        Set<Position> posMoves = getPossibleMovesHelper(board, currentPosition, new HashSet<>());
        posMoves.remove(currentPosition);   // begin positie is geen geldige eind stap
        return posMoves;
    }

    private Set<Position> getPossibleMovesHelper(Board board, Position currentPosition, Set<Position> reachable) {

        // alle lege stenen die aan de hive en aan deze tile liggen
        Set<Position> initReachable = currentPosition.getSurroundingPositions().stream()
                .filter(p -> this.canSlideTo(currentPosition, p, board))
                .filter(board::isTileNearHive)
                .collect(Collectors.toSet());

        // stopconditie is geen punten in initReachable,
        // dus alle surrounding tiles in reachable of ze zijn unreachable
        for (Position pos : initReachable) {
            try {
                board.moveTile(currentPosition, pos);               // move tile to possition to check
                reachable.add(pos);                                 // wordt alleen uitgevoerd als de tile hier heen mag, geen split hive
                this.getPossibleMovesHelper(board, pos, reachable); // find alle bereikbare plekken vanuit nieuwe positie
                board.moveTile(pos, currentPosition);               // bord terug naar oude staat
            } catch (Hive.IllegalMove illegalMove) {

            }
        }

        return reachable;

    }
}
