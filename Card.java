public enum Card{

    //chance cards
    CARD1("Advance to Boardwalk", Keyword.MOVE_TO, 39),
    CARD2("Advance to Go (Collect $200)", Keyword.MOVE_TO, 0),
    CARD3("Advance to Illinois Avenue. If you pass Go, collect $200", Keyword.MOVE_TO, 24),
    CARD4("Advance to St. Charles Place. If you pass Go, collect $200", Keyword.MOVE_TO, 11),
    CARD5("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled",
            Keyword.RAILROAD, 0),
    CARD6("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled",
            Keyword.RAILROAD, 0),
    CARD7("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.",
            Keyword.RAILROAD, 0),
    CARD8("Bank pays you dividend of $50", Keyword.CHANGE_MONEY, 50),
    CARD9("Get Out of Jail Free", Keyword.JAIL_FREE, 0),
    CARD10("Go Back 3 Spaces", Keyword.MOVE, -3),
    CARD11("Go to Jail. Go directly to Jail, do not pass Go, do not collect $200", Keyword.JAIL, 0),
    CARD12("Make general repairs on all your property. For each house pay $25. For each hotel pay $100", Keyword.PAY_FOR_HOUSES, -25),
    CARD13("Speeding fine $15", Keyword.CHANGE_MONEY, -15),
    CARD14("Take a trip to Reading Railroad. If you pass Go, collect $200", Keyword.MOVE_TO, 0),
    CARD15("You have been elected Chairman of the Board. Pay each player $50", Keyword.CHANGE_MONEY_EACH, -50),
    CARD16("Your building loan matures. Collect $150", Keyword.CHANGE_MONEY, 150),

    // community chest cards
    CARD17("Advance to Go (Collect $200)", Keyword.MOVE_TO,0),
    CARD18("Bank error in your favor. Collect $200", Keyword.CHANGE_MONEY, 200),
    CARD19("Doctor’s fee. Pay $50", Keyword.CHANGE_MONEY, -50),
    CARD20("From sale of stock you get $50", Keyword.CHANGE_MONEY, 50),
    CARD21("Get Out of Jail Free", Keyword.JAIL_FREE, 0),
    CARD22("Go to Jail. Go directly to jail, do not pass Go, do not collect $200", Keyword.JAIL, 0),
    CARD23("Holiday fund matures. Receive $100", Keyword.CHANGE_MONEY, 100),
    CARD24("Income tax refund. Collect $20", Keyword.CHANGE_MONEY, 20),
    CARD25("It is your birthday. Collect $10 from every player", Keyword.CHANGE_MONEY_EACH, 10),
    CARD26("Life insurance matures. Collect $100", Keyword.CHANGE_MONEY, 100),
    CARD27("Pay hospital fees of $100", Keyword.CHANGE_MONEY, -100),
    CARD28("Pay school fees of $50", Keyword.CHANGE_MONEY_EACH, -50),
    CARD29("Receive $25 consultancy fee", Keyword.CHANGE_MONEY, 25),
    CARD30("You are assessed for street repair. $40 per house. $115 per hotel", Keyword.PAY_FOR_HOUSES, -40),
    CARD31("You have won second prize in a beauty contest. Collect $10", Keyword.CHANGE_MONEY, 10),
    CARD32("You inherit $100", Keyword.CHANGE_MONEY, 100);

    public enum Keyword{
        JAIL, JAIL_FREE, CHANGE_MONEY, CHANGE_MONEY_EACH, PAY_FOR_HOUSES, MOVE, MOVE_TO, RAILROAD;  //to think about railroad
    }

    private String message;
    private Keyword keyword;
    private int value;

    private Card(String message, Keyword keyword, int value){
        this.message = message;
        this.keyword = keyword;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void execute(Player p, Board b) {

        switch (keyword) {
            case Keyword.JAIL:
                p.goToJail(b);
            case Keyword.JAIL_FREE:
                p.addHasJailFreeCard();
            case Keyword.CHANGE_MONEY:
                p.changeMoney(value);
            case Keyword.CHANGE_MONEY_EACH:
                Player[] otherPlayers = b.getPlayers();
                p.changeMoney(otherPlayers.length * value);
                for (Player player : otherPlayers) {
                    if(player.equals(p)){
                        continue;
                    }
                    player.changeMoney(-value);
                }
            case Keyword.PAY_FOR_HOUSES:
                p.changeMoney(value);
            case Keyword.MOVE:
                p.moveSpaces(value);
            case Keyword.MOVE_TO:
                p.moveTo(value);
            case Keyword.RAILROAD:
                p.moveTo(value); // to change
        }
    }

}
