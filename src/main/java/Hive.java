public class Hive implements nl.hanze.hive.Hive {

    private HiveGame game = new HiveGame(new Board());

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        game.play(tile, q, r);
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        game.move(fromQ, fromR, toQ, toR);
    }

    @Override
    public void pass() throws IllegalMove {
        game.pass();
    }

    @Override
    public boolean isWinner(Player player) {
        return game.isWinner(player);
    }

    @Override
    public boolean isDraw() {
        return game.isDraw();
    }
}