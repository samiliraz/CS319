package hello.Models;
import java.util.*;

public class Map {

    private int type;
    private List<BlueKnight> blue;
    private List<RedKnight> red;
    private WhiteKnight white;
    private int difficulty;
    private List<Wall> walls;

    public Map(int type, List<BlueKnight> blue, List<RedKnight> red, WhiteKnight white, int difficulty, List<Wall> walls) {
        this.type = type;
        this.blue = blue;
        this.red = red;
        this.white = white;
        this.difficulty = difficulty;
        this.walls = walls;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public WhiteKnight getWhite() {
        return white;
    }

    public void setWhite(WhiteKnight white) {
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
}

