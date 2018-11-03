import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QueenBeeSpec {

    @Test
    @Tag("8b")
    void whenQueenMovesThenCanOnlyMoveToEmptyPosition() {
        Board b = new Board();

        Set<Position> expectedPositions = new HashSet<>();
        expectedPositions.add(new Position(0, 2));
        expectedPositions.add(new Position(1, 0));

        Tile qBee;

        Map<Position, Stack<Tile>> iState = b.getInternalState();

        Stack<Tile> s1 = new Stack<>();
        s1.push(new Beetle(Hive.Player.WHITE));
        iState.put(new Position(1, 1), s1);

        Stack<Tile> s2 = new Stack<>();
        s2.push(new QueenBee(Hive.Player.BLACK));
        qBee = s2.peek();
        Position qBeePos = new Position(0, 1);
        iState.put(qBeePos, s2);

        b.setInternalState(iState);

        Set<Position> actualPossiblePositions = qBee.getPossibleMoves(b, qBeePos);

        assertAll(
                () -> {
                    Set<Position> positionsWithTiles = iState.keySet();
                    positionsWithTiles.retainAll(actualPossiblePositions);
                    assertTrue(positionsWithTiles.isEmpty());
                }
        );
    }

    @Test
    @Tag("8a")
    void whenQueenSurroundedByTilesThenQueenCanNotMove() {
        Position p = new Position(0,0);
        Tile qBee = new QueenBee(Hive.Player.BLACK);
        List<Position> surroundings = p.getSurroundingPositions();
        Board b = new Board();

        try {
            b.putTile(p, qBee);
            for(Position pos : surroundings) {
                b.putTile(pos, new SoldierAnt(Hive.Player.WHITE));
            }
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        assertTrue(qBee.getPossibleMoves(b, p).isEmpty());
    }

    @Test
    @Tag("8b")
    void whenQueenMovedCanOnlyMoveOnePosition() {
        Position p = new Position(3, 3);
        List<Position> expectedPositions = p.getSurroundingPositions();

        Tile qBee = new QueenBee(Hive.Player.BLACK);
        Board b = new Board();

        Set<Position> actual = qBee.getPossibleMoves(b, p);

        assertTrue( actual.stream().allMatch(expectedPositions::contains) ); // all possible moves should be element of surrounding possitions
    }
}