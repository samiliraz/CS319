package hello.Models;

public class Hint {

    private int wall;
    private int rotation;
    private int X;
    private int Y;

    public Hint(int wall, int rotation, int x, int y) {
        this.wall = wall;
        this.rotation = rotation;
        X = x;
        Y = y;
    }

    public int getWall() {
        return wall;
    }

    public void setWall(int wall) {
        this.wall = wall;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
