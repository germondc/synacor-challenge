package test.clyde;

public enum Direction {
    north(0, -1),
    south(0, 1),
    east(1, 0),
    west(-1, 0);

    public int x;
    public int y;
    
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
