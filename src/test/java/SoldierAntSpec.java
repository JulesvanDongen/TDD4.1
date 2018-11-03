
//import javafx.geometry.Pos;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SoldierAntSpec {

    @Test
    void whenSoldierAntMovesThenNoStepInMoveCanSplitHive() {
        Board b = new Board();

        try {
            b.putTile(new Position(0, -1), new SoldierAnt(Hive.Player.WHITE));
            b.putTile(new Position(0, 0), new SoldierAnt(Hive.Player.BLACK));
            b.putTile(new Position(1, 02), new Beetle(Hive.Player.BLACK));
            b.putTile(new Position(-1, 1), new Beetle(Hive.Player.WHITE));
        } catch (Hive.IllegalMove illegalMove) {
            fail("");
        }

        Tile antToMove = b.getInternalState().get(new Position(0, 0)).peek();

        assertAll(
                () -> antToMove.getPossibleMoves(b, new Position(0, 0)).isEmpty()
        );
    }

    @Test
    void whenSoldierAntMovesThenCanNotMoveToStartingPosition() {
        Board b = new Board();
        Tile ant = new SoldierAnt(Hive.Player.WHITE);
        Position antStart = new Position(-2, 0);

        try {
            for (Position p : new Position(0, 0).getSurroundingPositions()) {
                b.putTile(p, new Grasshopper(Hive.Player.WHITE));
            }
            b.putTile(antStart, ant);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertFalse(ant.getPossibleMoves(b, antStart).contains(antStart));
    }

    @Test
    void whenSoldierAntMovesThenEachStepHasToSlide() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        for (Position surroundingPosition : new Position(0, 0).getSurroundingPositions()) {
            Stack<Tile> tiles = new Stack<>();
            tiles.push(new SoldierAnt(Hive.Player.WHITE));
            map.put(surroundingPosition, tiles);
        }

        Board board = new Board(map);

        assertThrows(Hive.IllegalMove.class, () -> {
            board.moveTile(new Position(0,1), new Position(0,0));
        });
    }

}