package hello.Models;
public class Wall {
    private int id;
    private int direction;
    private String img;

    public Wall( int id, int direction, String img ){
        this.id = id;
        this.direction = direction;
        this.img = "some random img url for Wall : " + id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
