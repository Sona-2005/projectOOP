public class Player {
    public enum Symbol {
        IRON,
        PENGUIN,
        HAT,
        SHOE,
        CAT,
        SHIP,
        CAR,
        CHAIR
    }

    private Symbol symbol;
    private int money;
    private int position;
    private Property[] property;

    public Player(Symbol symbol){
        this.symbol = symbol;
        this.money = 1500;
    }

    public Player(Player that){
        this.symbol = that.symbol;
        this.money = that.money;
        this.position = that.position;
        for(int i = 0; i <property.length; i++){
            this.property[i] = new Property(that.property[i]);
        }
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void collectMoney(int amount) {
        this.money +=amount;
    }
    public void payMoney(int amount) {
        this.money -=amount;
    }

    public void moveSpaces(int spaces) {
        if (spaces > 0) {
            this.moveForward(spaces);
        }
        else {
            this.moveBack(spaces);
        }
    }

    private void moveForward(int spaces) {
        this.position += spaces;

        //if(this.position )
    }

    private void moveBack(int spaces) {
        this.position += spaces;

        //if(this.position )
    }

    public void moveTo(int position) {
        this.position = position;
    }

//    private int setPosition(int position, Board board) {
//        if (position >  board.get)
//    }

}
