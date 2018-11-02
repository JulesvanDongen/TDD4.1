import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.mockito.Mockito.when;

class HiveGameSpec {

    @Test
    @Tag("4a")
    public void whenGameStartedThenTurnToWhite() {
        HiveGame game = new HiveGame(new Board());

        assertEquals(game.getTurn().getColor(), Hive.Player.WHITE);
    }

    @Test
    @Tag("3b")
    public void whenPassThenOpponentsTurn() {
        HiveGame game = new HiveGame(new Board());

        Player p1 = game.getTurn();

        try {
            game.play(Hive.Tile.BEETLE, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {
            fail("First tile should be able to be played");
        }

        Player p2 = game.getTurn();
        assertFalse(p1 == p2);
    }

    @Test
    @Tag("3b")
    public void whenTilePlayedThenOpponentsTurn() {
        HiveGame game = new HiveGame(new Board());

        Player p1 = game.getTurn();
        try {
            game.play(Hive.Tile.SOLDIER_ANT, 1,1);
        } catch (Hive.IllegalMove illegalMove) {
            fail("this method should be allowed");
        }

        Player p2 = game.getTurn();

        assertFalse(p1 == p2);
    }

    @Test
    @Tag("3b")
    public void whenTileMovedThenOpponentsTurn() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> a = new Stack<>();
        Beetle beetle = mock(Beetle.class);

        when(beetle.samePlayer(any(Hive.Player.class))).thenReturn(true);

        HashSet<Position> retval = new HashSet<Position>();
        retval.add(new Position(1, 0));
        when(beetle.getPossibleMoves(any(Board.class), any(Position.class))).thenReturn(retval);

        a.push(beetle);
        map.put(new Position(1, 1), a);

        Stack<Tile> b = new Stack<>();
        Spider spider = mock(Spider.class);

        b.push(spider);
        map.put(new Position(0, 0), b);

        Player player1 = new Player(Hive.Player.WHITE);
        Player player2 = new Player(Hive.Player.BLACK);

        try {
            player1.playTile(Hive.Tile.QUEEN_BEE); // Play the Queen Bee before moving
        } catch (NoSuchTileException e) {
            fail(e);
        }

        HiveGame game = new HiveGame(new Board(map), player1, player2);
        Player p1 = game.getTurn();
        Player p2 = null;

        try {
            game.move(1,1, 1,0);
        } catch (Hive.IllegalMove illegalMove) {
            fail(illegalMove);
        }

        assertFalse(p2 == p1 && p2 != null);
    }

    @Test
    @Tag("3c")
    public void whenQueenSurroundedByOpponentThenOpponentWins() {
        Board b = new Board();

        Position queenPos = new Position(0, 0);
        try {
            b.putTile(queenPos, new QueenBee(Hive.Player.WHITE));
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        //surround whit queen whith black grasshoppers
        try {
            for (Position p : queenPos.getSurroundingPositions()) {
                b.putTile(p, new Grasshopper(Hive.Player.BLACK));
            }
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }


        HiveGame game = new HiveGame(b);

        // In this case black should have won
        assertTrue(game.isWinner(Hive.Player.BLACK));
    }

    @Test
    public void whenQueenSurroundedByFiveTilesThenNoWin() {
        Board b = new Board();

        Position queenPos = new Position(0, 0);
        try {
            b.putTile(queenPos, new QueenBee(Hive.Player.WHITE));
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        //surround whit queen whith  5 black grasshoppers
        List<Position> posAroundQueen = queenPos.getSurroundingPositions();
        try {
            for(int i = 0; i < 5 && i < posAroundQueen.size(); i++){
                Position p = posAroundQueen.get(i);
                b.putTile(p, new Grasshopper(Hive.Player.BLACK));
            }
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }


        HiveGame game = new HiveGame(b);

        // In this case black should have won
        assertFalse(game.isWinner(Hive.Player.BLACK));
    }

    @Test
    @Tag("3d")
    public void whenBothPlayersWinThenDraw(){
        Board b = new Board();

        Position whiteQueenPos = new Position(0, 0);
        Position blackQueenPos = new Position(3, 0);

        try{
            for(Position p : whiteQueenPos.getSurroundingPositions()){
                b.putTile(p, new Grasshopper(Hive.Player.BLACK));
            }
            for(Position p: blackQueenPos.getSurroundingPositions()) {
                b.putTile(p, new Grasshopper(Hive.Player.WHITE));
            }
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        HiveGame g = new HiveGame(b);
        assertAll(
                () -> g.isWinner(Hive.Player.WHITE),
                () -> g.isWinner(Hive.Player.BLACK),
                () -> g.isDraw()
        );
    }

    @Test
    @Tag("5a")
    public void whenPlayerMovesOpponentTileThenShouldThrowIllegalMoveException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Beetle(Hive.Player.BLACK));
        map.put(new Position(0, 0), a);

        Stack<Tile> b = new Stack<>();
        b.push(new Beetle(Hive.Player.WHITE));
        map.put(new Position(1, 0), b);

        Board board = new Board(map);
        HiveGame hiveGame = new HiveGame(board);

        Player turn = hiveGame.getTurn();

        assertThrows(Hive.IllegalMove.class, () -> {
            hiveGame.move(0, 0, 1, 0);
        });
    }

    @Test
    @Tag("5b")
    public void whenPlayerMovesTileWhileQueenBeeIsNotPlayedThenThrowIllegalMoveException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Beetle(Hive.Player.BLACK));
        map.put(new Position(0, 0), a);

        Stack<Tile> b = new Stack<>();
        b.push(new Beetle(Hive.Player.WHITE));
        map.put(new Position(1, 0), b);

        Board board = new Board(map);
        HiveGame hiveGame = new HiveGame(board);

        assertThrows(Hive.IllegalMove.class, () -> {
            hiveGame.move(1,0, 0,0);
        });
    }

    @Test
    @Tag("5c")
    public void whenTileMovedToPositionNotNextToOtherTileThenThrowIllegalMoveException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Beetle(Hive.Player.WHITE));
        map.put(new Position(0, 0), a);

        Board board = new Board(map);
        HiveGame hiveGame = new HiveGame(board);

        assertThrows(Hive.IllegalMove.class, () -> {
            hiveGame.move(0, 0, 1, 0);
        });
    }

    @Test
    @Tag("4b")
    public void whenTilePlayedThenOnlyOnEmptyPosition() {
        Board b1 = new Board();
        Board b2 = new Board();
        Position p = new Position(13,13);
        try {
            b2.putTile(p, new SoldierAnt(Hive.Player.BLACK));
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }
        HiveGame g1 = new HiveGame(b1); // game where position to be played is empty
        HiveGame g2 = new HiveGame(b2); // game where position to be played is occupied

        try {
            g1.play(Hive.Tile.GRASSHOPPER, 13, 13);
        } catch (Hive.IllegalMove illegalMove) {
            fail(illegalMove);
        }

        assertThrows(Hive.IllegalMove.class, () -> g2.play(Hive.Tile.BEETLE, 13, 13));

    }

    @Test
    @Tag("4c")
    public void whenTilePlayedOnBoardWithTilesThenTileMustBePlayedAdjecentToAtLeastOneTile(){
        Board b = new Board();
        Map<Position, Stack<Tile>> intState = b.getInternalState();

        try {
            Position p1 = new Position(0, 0);
            b.putTile(p1, new QueenBee(Hive.Player.WHITE));

            Position p2 = new Position(0,1);
            b.putTile(p2, new Grasshopper(Hive.Player.BLACK));

        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }

        HiveGame g = new HiveGame(b);
        assertThrows(Hive.IllegalMove.class, () -> g.play(Hive.Tile.GRASSHOPPER, 2,2));

    }
}
