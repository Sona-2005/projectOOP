public class Utility extends Tile {
    private String name;
    private int cost;

    public Utility(int position, String name, int cost){
        super(position);
        this.name = name;
        this.cost = cost;
    }
}
