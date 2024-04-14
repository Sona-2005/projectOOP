public class Utility extends Tile {
    private final int PRICE = 150;


   // private int cost;

    public Utility(int position, String name){
        super(position, name, 150);
       // this.cost = cost;

    }

    public int calculateRent(int num, boolean bothOwned) {
        if(bothOwned) {
            return 10 * num;
        }
        else {
            return 4 * num;
        }
    }

    public String toString() {
        return super.toString() + " " + getPrice();
    }

    /*
      private final int PRICE = 150;
    private final int IF_ONE_OWNED_TIMES = 4;
    private final int IF_BOTH_OWNED_TIMES = 10;
     */

    public int getPrice() {
        return super.getPrice();
    }
}

