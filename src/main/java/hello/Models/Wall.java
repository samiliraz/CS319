package hello.Models;
public class Wall {
    private String direction;
    private String img;

    public Wall( String direction ){
        this.direction = direction;
        this.img = "" + direction + ".png";
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
