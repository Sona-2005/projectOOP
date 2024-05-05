import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MonopolyUI extends JFrame {

    public static final int HEIGHT = 800;
    public static final int WIDTH = HEIGHT + 400;

    private Board board;
    BoardTile[][] tilesUI = new BoardTile[11][11];
    private int numberOfPlayers;

    public MonopolyUI() {
        super("Monopoly");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout(new BorderLayout());

        JPanel monopolyBoard = new JPanel( );
        monopolyBoard.setLayout(new BorderLayout());
        monopolyBoard.setBackground(Color.WHITE);
        monopolyBoard.setPreferredSize((new Dimension(HEIGHT,HEIGHT)));

        mainPanel.add(monopolyBoard);

        JPanel assets = new JPanel( );
        assets.setLayout(new BorderLayout());
        assets.setBackground(Color.WHITE);
        assets.setPreferredSize((new Dimension(400,HEIGHT)));

        JTextArea assetsDisplay = new JTextArea();
        assetsDisplay.setFont(assetsDisplay.getFont().deriveFont(Font.BOLD));
        assets.add(assetsDisplay, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new FlowLayout());
        JPanel characterButtonPanel = new JPanel(new GridLayout(4, 2));
        JPanel numberOfPlayersPanel = new JPanel(new GridLayout(2,1));
        JPanel characterChoicePanel = new JPanel(new GridLayout(2,1));

        JPanel southPanel = new JPanel(new GridLayout(3,1));

        JLabel turnDisplay = new JLabel();
        JLabel diceDisplay = new JLabel("Result: ");
        JButton rollDiceButton = new JButton("Roll the dice!");
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diceDisplay.setText("Dice 1: " + Dice.rollDice()[0] + "     Dice 2: " + Dice.rollDice()[1]);
//                diceDisplay.setText("Dice 2: " + Dice.rollDice()[1]);
//                assets.add(diceDisplay);
            }
        });
        southPanel.add(turnDisplay);
        southPanel.add(diceDisplay);
        southPanel.add(rollDiceButton);
        assets.add(southPanel, BorderLayout.SOUTH);
        JLabel question = new JLabel("Do you want to buy this property?");
//        question.setHorizontalAlignment(SwingConstants.NORTH);
        JButton yesAnswerButton = new JButton("Yes");
        JButton noAnswerButton = new JButton("No");
        yesAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = e.getActionCommand();
                JButton thisButton = (JButton) e.getSource();
                thisButton.setBackground(Color.YELLOW);

              //  board.buyTheProperty(,answer);
            }
        });
        noAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton thisButton = (JButton) e.getSource();
                thisButton.setBackground(Color.YELLOW);
            }
        });


        JFrame numberOfPlayersWindow = new JFrame("Number of Players");
        numberOfPlayersWindow.setSize(300,150);
        numberOfPlayersWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        numberOfPlayersWindow.setLocationRelativeTo(null);

        JFrame characterChoiceWindow = new JFrame("Choose your characters");
        characterChoiceWindow.setSize(300,300);
        characterChoiceWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterChoiceWindow.setLocationRelativeTo(null);

        JLabel message = new JLabel("Welcome! How many players will play?");
        message.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < Player.Symbol.values().length; i++) {
            JButton characterButton = new JButton(Player.Symbol.values()[i].toString());
            characterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = e.getActionCommand();
                    JButton thisButton = (JButton) e.getSource();

                    try {
                        board.addPlayer(Player.Symbol.valueOf(s));
                        numberOfPlayers++;
                        message.setText("Player " + (numberOfPlayers + 1) + " choose your symbol.");
                        thisButton.setBackground(Color.RED);

                        if(numberOfPlayers >= board.getPlayers().length) {
                            updateAssets(assetsDisplay);
                            //message.setText("Let's start the game");

                            characterChoiceWindow.setVisible(false);
                            MonopolyUI.this.setVisible(true);

                            updateBoard(board.getPlayers()[0].getSymbol());


                        }
                    }
                    catch(DublicatePlayersException exception) {
                        message.setText("You can't choose the same character");
                    }
                }
            });
            characterButtonPanel.add(characterButton);
        }


        JTextField field = new JTextField(15);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    numberOfPlayers = Integer.parseInt(field.getText());

                    if(!validNumberOfPlayers(numberOfPlayers)) {
                        message.setText("Number of players should be 2 to 8");
                        return;
                    }
                    board = new Board(numberOfPlayers);
                    numberOfPlayers = 0;

                    characterChoicePanel.add(message);
                    characterChoicePanel.add(characterButtonPanel);

                    characterChoiceWindow.setVisible(true);
                    numberOfPlayersWindow.setVisible(false);

                    int gridDimension = ((board.getBoardSize() - 4) / 4) + 2;

                    Tile[][] reshapedBoard = reshapeBoardArray(board.getBoardTile());
                    JPanel boardContainer = new JPanel(new GridLayout(gridDimension, gridDimension, 0, 0));

                    for (int i = 0; i < gridDimension; i++) {
                        for (int j = 0; j < gridDimension; j++) {
                            BoardTile tile = new BoardTile(reshapedBoard[i][j], "src/images/tile_demo.jpg");
                            tilesUI[i][j] = tile;
                            boardContainer.add(tile);
                        }
                    }

                    monopolyBoard.add(boardContainer);

                    message.setText("Player " + (numberOfPlayers + 1) + " choose your symbol.");

                }
                catch (NumberFormatException exception) {
                    message.setText("Invalid input");
                }

            }
        });
        textPanel.add(field);
        textPanel.add(submitButton);


        numberOfPlayersPanel.add(message);
        numberOfPlayersPanel.add(textPanel);


        numberOfPlayersWindow.add(numberOfPlayersPanel);
        characterChoiceWindow.add(characterChoicePanel);


        mainPanel.add(assets, BorderLayout.EAST);

        this.add(mainPanel);

        numberOfPlayersWindow.setVisible(true);

    }

    public void updateBoard(Player.Symbol turn) {
        emptyAllPlayersOnBoards();
        updatePlayers();
        changeBoardTileIcons(turn);
    }


    private void updatePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for(Player p: board.getPlayers()) {
            getBoardTileByPosition(p.getPosition()).addPlayers(p);
        }

    }

    private void emptyAllPlayersOnBoards() {
        for (int i = 0; i < tilesUI.length; i++) {
            for (int j = 0; j < tilesUI[0].length; j++) {
                tilesUI[i][j].emptyPlayers();
            }
        }
    }

    private void changeBoardTileIcons(Player.Symbol turn) {
        for (int i = 0; i < tilesUI.length; i++) {
            for (int j = 0; j < tilesUI[0].length; j++) {
                tilesUI[i][j].updateIcons(turn);
            }
        }
    }


    private BoardTile getBoardTileByPosition(int position) {
        for (int i = 0; i < tilesUI.length; i++) {
            for (int j = 0; j < tilesUI[0].length; j++) {
                if(position == tilesUI[i][j].getPosition()) {
                    return tilesUI[i][j];
                }
            }
        }
        return null;
    }

    private Player getPlayerBySymbol(Player.Symbol symbol) {
        for(Player p: board.getPlayers()) {
            if(p.getSymbol() == symbol) {
                return p;
            }
        }
    }


    private boolean validNumberOfPlayers(int number) {
        return (number >= 2 && number <= Player.Symbol.values().length);
    }

    private static Tile[][] reshapeBoardArray(Tile[] board) {
        int dimension = ((board.length - 4) / 4) + 2;
        Tile[][] reshaped = new Tile[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                reshaped[i][j] = null;
            }
        }

        int i = dimension - 1;
        int j = dimension - 1;
        int k = 0;
        while (j >= 0) {
            reshaped[i][j] = board[k];
            j--;
            k++;
        }
        j++;
        k--;
        while (i >= 0) {
            reshaped[i][j] = board[k];
            i--;
            k++;
        }
        i++;
        k--;
        while (j < dimension) {
            reshaped[i][j] = board[k];
            j++;
            k++;
        }
        j--;
        k--;
        while (i < dimension - 1) {
            reshaped[i][j] = board[k];
            i++;
            k++;
        }
        return reshaped;
    }

    private void updateAssets(JTextArea assetsDisplay) {
        String s = "";
        for (int i = 0; i < board.getPlayers().length; i++) {
            Player player = board.getPlayers()[i];
            s = s + "Player " + (i+1) + " (" + player + ") has " + board.getPlayers()[i].getMoney() + " money and ownes: \n";
            for(Tile tile: board.tilesOwnedByPlayer(player)) {
//                s = s + tile + "\n";
                s = s + "hey" + "\n";
            }
            s = s + " \n";
        }

        assetsDisplay.setText(s);

    }




    public void actionForStepOnProperty(Player p){
        Tile steppedOn = board.getBoardTile()[p.getPosition()];
        if(board.getPropertyOwner(steppedOn.getPosition()) == null) {
            JPanel ask = new JPanel();


        }
        else if(board.getPropertyOwner(steppedOn.getPosition()).equals(p)) {
            System.out.println("You own this property");
        }
        else {
            Property property = (Property) steppedOn;
            int cost = ((Property) steppedOn).calculateRent(board.areAllPropertiesOwned(property.getColor(), board.getPropertyOwner(steppedOn.getPosition())));
            p.changeMoney(-cost);
            board.getPropertyOwner(steppedOn.getPosition()).changeMoney(cost);
            System.out.println("You need to pay rent " + cost);

        }
    }

//    private Player check(Player[] players, Board board){
//        for (int i = 0; i < players.length; i++) {
//            for (int j = 0; j < ; j++) {
//
//            }
//        }
//    }


}
