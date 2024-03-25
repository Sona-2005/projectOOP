public class Property extends Tile{
    public enum Color {
        BROWN, SILVER, PINK, ORANGE, RED, YELLOW, GREEN, BLUE
    }

    private Color color;
    private String name;
    private int cost;
    private int rentCost;

    public Property(int pos, Color color, String name, int cost, int rentCost) {
        super(pos);
        this.color = color;
        this.name = name;
        this.cost = cost;
        this.rentCost = rentCost;
    }

    public Property(Property that){
        super(that.getPosition());
        this.color = that.color;
        this.name = that.name;
        this.cost = that.cost;
        this.rentCost = that.rentCost;
    }
}
