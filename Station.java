public class Station extends Tile{
    private String name;
    private int cost;
    private int rent;

    public Station(int pos, String name, int cost, int rent) {
        super(pos);
        this.name = name;
        this.cost = cost;
        this.rent = rent;

    }
}
