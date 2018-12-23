package hello.Models;
import java.util.*;

public class Map {

    private List<Wall> walls;

    private List<RedKnight> red;
    private List<BlueKnight> blue;
    private List<WhiteKnight> white;
    private List<Hint> hint;

    private int difficulty;


    public Map( List<Wall> walls , List<RedKnight> red, List<BlueKnight> blue, List<WhiteKnight> white, List<Hint> hint, int difficulty) {
        this.blue = blue;
        this.red = red;
        this.white = white;
        this.walls = walls;
        this.hint = hint;
        this.difficulty = difficulty;
    }


    public List<BlueKnight> getBlue() {
        return blue;
    }

    public void setBlue(List<BlueKnight> blue) {
        this.blue = blue;
    }

    public List<RedKnight> getRed() {
        return red;
    }

    public void setRed(List<RedKnight> red) {
        this.red = red;
    }

    public List<WhiteKnight> getWhite() {
        return white;
    }

    public void setWhite(List<WhiteKnight> white) {
        this.white = white;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<Hint> getHint() {
        return hint;
    }

    public void setHint(List<Hint> hint) {
        this.hint = hint;
    }
}

