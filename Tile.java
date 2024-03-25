public class Tile {
    private int position;

    public Tile(int position) {
        this.position = position;
    }

    public Tile(Tile that) {
        this.position = that.position;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }


}
