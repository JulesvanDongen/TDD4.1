
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

        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new SoldierAnt(Hive.Player.WHITE));
        map.put(new Position(0, -1), a);

        Stack<Tile> b = new Stack<>();
        b.push(new SoldierAnt(Hive.Player.BLACK));
        map.put(new Position(0, 0), b);

        Stack<Tile> c = new Stack<>();
        c.push(mock(Beetle.class));
        map.put(new Position(1, 2), c);

        Stack<Tile> d = new Stack<>();
        d.push(mock(Beetle.class));
        map.put(new Position(-1, 1), d);

        Board board = new Board(map);

        Tile antToMove = board.getInternalState().get(new Position(0, 0)).peek();
        assertTrue(antToMove.getPossibleMoves(board, new Position(0,0)).isEmpty());
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