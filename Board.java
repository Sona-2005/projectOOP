import java.util.*;

public class Board {

    public enum Color {
        BROWN, SILVER, PINK, ORANGE, RED, YELLOW, GREEN, BLUE
    }

    public static final int boardSize = 40;
    public static final int cardInDeckSize = 16;
    private Tile[] board;
    private Card[] chanceDeck;
    private Card[] communityChestDeck;
    private Player[] propertiesOwned;
    private Player[] players;
    private int indexForChanceCards = 0;
    private int indexForCommunityChestCards = 16;

    public Board(int numOfPlayers) {
        this.board = new Tile[boardSize];
        board[0] = new Tile(0, "Go", 0);
        board[1] = new Property(1, "Mediterranean Avenue", Color.BROWN, 60, 2);
        board[2] = new Tile(2, "Community Chest", 0);
        board[3] = new Property(3, "Baltic Avenue", Color.BROWN, 60, 4);
        board[4] = new Tax(4, "Income Tax", 200);
        board[5] = new Railroad(5, "Reading Railroad",  25);
        board[6] = new Property(6, "Oriental Avenue", Color.SILVER, 100, 6);
        board[7] = new Tile(7, "Chance", 0);
        board[8] = new Property(8, "Vermont Avenue", Color.SILVER, 100, 6);
        board[9] = new Property(9, "Connecticut Avenue", Color.SILVER, 120, 8);
        board[10] = new Tile(10, "Jail", 0);
        board[11] = new Property(11, "St. Charles Place", Color.PINK, 140, 10);
        board[12] = new Utility(12, "Electric Company");
        board[13] = new Property(13, "States Avenue", Color.PINK, 140, 10);
        board[14] = new Property(14, "Virginia Avenue", Color.PINK, 160, 12);
        board[15] = new Railroad(15, "Pennsylvania Railroad", 25);
        board[16] = new Property(16, "St. James Place", Color.ORANGE, 180, 14);
        board[17] = new Tile(17, "Community Chest", 0);
        board[18] = new Property(18, "Tennessee Avenue", Color.ORANGE, 180, 14);
        board[19] = new Property(19, "New York Avenue", Color.ORANGE, 200, 16);
        board[20] = new Tile(20, "Free Parking", 0);
        board[21] = new Property(21, "Kentucky Avenue", Color.RED, 220, 18);
        board[22] = new Tile(22, "Chance", 0);
        board[23] = new Property(23, "Indiana Avenue", Color.RED, 220, 18);
        board[24] = new Property(24, "Illinois Avenue", Color.RED, 240, 20);
        board[25] = new Railroad(25, "B&O Railroad",  25);
        board[26] = new Property(26, "Atlantic Avenue", Color.YELLOW, 260, 22);
        board[27] = new Property(27, "Ventnor Avenue", Color.YELLOW, 260, 22);
        board[28] = new Utility(28, "Water Works");
        board[29] = new Property(29, "Marvin Gardens", Color.YELLOW, 280, 24);
        board[30] = new Tile(30, "Go To Jail", 0);
        board[31] = new Property(31, "Pacific Avenue", Color.GREEN, 300, 26);
        board[32] = new Property(32, "North Carolina Avenue", Color.GREEN, 300, 26);
        board[33] = new Tile(33, "Community Chest", 0);
        board[34] = new Property(34, "Pennsylvania Avenue", Color.GREEN, 320, 28);
        board[35] = new Railroad(35, "Short Line",  25);
        board[36] = new Tile(36, "Chance", 0);
        board[37] = new Property(37, "Park Place", Color.BLUE, 350, 35);
        board[38] = new Tax(38, "Luxury Tax", 100);
        board[39] = new Property(39, "Boardwalk", Color.BLUE, 400, 50);

        this.propertiesOwned = new Player[boardSize];
        this.players = new Player[numOfPlayers];

        this.chanceDeck = new Card[cardInDeckSize];
        this.communityChestDeck = new Card[cardInDeckSize];

        for (int i = 0; i < cardInDeckSize; i++) {
            chanceDeck[i] = Card.values()[i];
            communityChestDeck[i] = Card.values()[i + cardInDeckSize];
        }

        shuffle(chanceDeck);
        shuffle(communityChestDeck);


    }

    public static void main(String[] args) {
        Board b = new Board(3);
//        for (int i = 0; i < cardInDeckSize; i++) {
//            System.out.println(b.chanceDeck[i]);
//            System.out.println(b.communityChestDeck[i]);
//        }
        System.out.println(b.getTilePosition("Tile8"));
    }

    public int getTilePosition(String name) {
        for (Tile tile : this.board) {
            if (name.equalsIgnoreCase(tile.getName())) {
                return tile.getPosition();
            }
        }
        return -1;
    }

    public Tile getTileByPosition(int position){
        return board[position];
    }

    private void shuffle(Card[] deck) {

        List<Card> deckList = Arrays.asList(deck);

        Collections.shuffle(deckList);

        deckList.toArray(deck);

    }

    public void addPlayer(Player.Symbol s) throws DublicatePlayersException{
        int firstNullIndex = 0;
        for(int i = 0; i < players.length; i++) {
            if(players[i] == null) {
                firstNullIndex = i;
                break;
            }
            if(players[i].getSymbol() == s) {
                throw new DublicatePlayersException();
            }
        }
        players[firstNullIndex] = new Player(s);

    }

    public Player[] getPlayers() {
        return players;
    }

    public void performMove(int tileCoordinate, Player p){
        Tile t = board[tileCoordinate];
        Scanner sc = new Scanner(System.in);
        if(t.getClass() == Property.class) {
            if(getPropertyOwner(tileCoordinate) == null) {
                System.out.println("Would you like to buy the property " + t);
                System.out.println("Type 'yes' or 'no'");
                buyTheProperty(p, sc.nextLine());
            }
            else if(getPropertyOwner(tileCoordinate).equals(p)) {
                System.out.println("You own this property");
            }
            else {
                Property property = (Property) t;
                int cost = ((Property) t).calculateRent(areAllPropertiesOwned(property.getColor(), getPropertyOwner(tileCoordinate)));
                p.changeMoney(-cost);
                getPropertyOwner(tileCoordinate).changeMoney(cost);
                System.out.println("You need to pay rent " + cost);

            }
        }

        else if (t.getClass() == Tax.class) {
            p.changeMoney(-((Tax) t).getTax());
        }

        else if (t.getClass() == Railroad.class){
            if (getPropertyOwner(tileCoordinate) == null){
                System.out.println("Would you like to buy the railroad" + t);
                System.out.println("Type 'yes' or 'no'");
                buyTheProperty(p, sc.nextLine());
            }
            else if (getPropertyOwner(tileCoordinate).equals(p)) {
                System.out.println("You own this railroad");
            }
            else {
                Railroad railroad = (Railroad) t;
                int cost = railroad.calculateRent(numberOfRailroadsOwned(getPropertyOwner(tileCoordinate)));
                p.changeMoney(cost);
                getPropertyOwner(tileCoordinate).changeMoney(cost);
                System.out.println("You need to pay rent " + cost);
            }
        }

        else if(t.getClass() == Utility.class){
            if (propertiesOwned[tileCoordinate] == p){
                System.out.println("You own this utility");
            }
            else {
                System.out.println("You stepped on " + t);
                if (propertiesOwned[tileCoordinate] == null) {
                    System.out.println("If you want to buy the utility" + t + " enter 'yes' if not 'no'");
                    String answer = sc.next();
                    buyTheProperty(p, answer);
                }
                else {
                    System.out.println("You need to pay rent");
                    System.out.println("To do so you have to roll the dice. Enter 'roll' to roll it");
                    String command = sc.next();
                    while (!command.equals("roll")){
                        System.out.println("You have to roll the dice. Enter 'roll' to roll it");
                        command = sc.next();
                    }
                    int[] diceRoll =  Dice.rollDice();

                    for (int i = 0; i < players.length; i++) {
                        if(numberOfUtilitiesOwned(players[i]) == 0){
                            continue;
                        } else if ( propertiesOwned[tileCoordinate] == p && numberOfUtilitiesOwned(players[i]) == 1) {
                            p.changeMoney(-(4*(diceRoll[0] + diceRoll[1])));
                            propertiesOwned[tileCoordinate].changeMoney((4*(diceRoll[0] + diceRoll[1])));
                        } else {
                            p.changeMoney(-(10*(diceRoll[0] + diceRoll[1])));
                            propertiesOwned[tileCoordinate].changeMoney((10*(diceRoll[0] + diceRoll[1])));
                        }
                    }
                }
            }

        }
        else {
            if (t.getName().equals("Community Chest")){
                System.out.println("You get a Community Chest Card");
                getCommunityChestCard(p,this);
            }
            if (t.getName().equals("Chance")){
                System.out.println("You get a Chance Card");
                getChanceCards(p,this);
            }

        }

    }

    private Player getPropertyOwner(int tileCoordinate) {
        return propertiesOwned[tileCoordinate];
    }

    private boolean areAllPropertiesOwned(Color color, Player p) {;
        for (int i = 0; i < board.length; i++) {

            if(board[i].getClass() == Property.class && (color == ((Property) board[i]).getColor())){
                if (!getPropertyOwner(i).equals(p)) {
                    return false;
                }
            }

        }
        return true;
    }

    private int numberOfRailroadsOwned(Player p) {
        int numberOfRailroads = 0;
        String[] array = {"Reading Railroad","Pennsylvania Railroad","B&O Railroad", "Short Line"};
        for (int i = 0; i < array.length; i++) {
            if (propertiesOwned[getTilePosition(array[i])] == p){
                numberOfRailroads ++;
            }
        }
        return numberOfRailroads;
    }

    private int numberOfUtilitiesOwned(Player p){
            if( propertiesOwned[getTilePosition("Electric Company")] == p &&
                propertiesOwned[getTilePosition("Water Works")] == p) {
                return 2;
            } else if(propertiesOwned[getTilePosition("Electric Company")] == p ||
                propertiesOwned[getTilePosition("Water Works")] == p){
                return 1;
            }
            else return 0;
    }

    private void buyTheProperty(Player p, String answer){
        if (answer.equals("yes")){
            propertiesOwned[p.getPosition()] = p;
            p.changeMoney(-getTileByPosition(p.getPosition()).getPrice());
        }
    }

    public Tile[] getBoardTile() {

        Tile[] newArr =  new Tile[board.length];
        for (int i = 0; i < board.length; i++) {
            newArr[i] = board[i].clone();
        }
        return newArr;

    }
    public void getCommunityChestCard(Player player, Board board){
        Card card = board.communityChestDeck[indexForCommunityChestCards];
        System.out.println(card.getMessage());
        card.execute(player,board);

        indexForCommunityChestCards++;

        if (indexForCommunityChestCards >= 16){
            indexForCommunityChestCards = indexForCommunityChestCards - 16;
        }

    }
    public void getChanceCards(Player player, Board board){
        if (indexForChanceCards >= 32){
            indexForChanceCards = indexForChanceCards - 16;
        }
        Card card = board.chanceDeck[indexForChanceCards];
        System.out.println(card.getMessage());
        card.execute(player, board);

        indexForChanceCards++;

        if (indexForChanceCards >= 16){
            indexForChanceCards = indexForChanceCards - 16;
        }
    }

    public ArrayList<Tile> tilesOwnedByPlayer(Player p){
        ArrayList<Tile> tilesOwned = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
           if( propertiesOwned[i] == p ){
               tilesOwned.add(board[i]);
           }
        }
        return tilesOwned;
    }
}

