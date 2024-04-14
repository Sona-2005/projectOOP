public class Railroad extends Tile{
    private final int PRICE = 200;

   // private int cost;
    private int rentCost;

    public Railroad(int position, String name, int rent) {
        super(position, name, 200);
       // this.cost = cost;
        this.rentCost = rent;

    }

    public int calculateRent(int numberOfOwned) {
        if(numberOfOwned == 4) {
            return 8 * rentCost;
        }
        else if (numberOfOwned == 3){
            return 4 * rentCost;
        }
        else if (numberOfOwned == 2) {
            return 2 * rentCost;
        }
        else {
            return rentCost;
        }
    }

    public String toString() {
        return super.toString() + " " + getPrice();
    }

    public int getPrice() {
        return super.getPrice();
    }

   /*
    public class Railroad {
    private final int PRICE = 200;
    private final int RENT = 25;
    private final int RENT_IF_TWO_OWNED = 50;
    private final int RENT_IF_THREE_OWNED = 100;
    private final int RENT_IF_FOUR_OWNED = 200;
    private final int MORTGAGE_VALUE = 100;


}
     */



}
