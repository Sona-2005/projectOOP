import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardTile extends JLabel{
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private BufferedImage image;

    private ArrayList<Player> playersOnTheSquare = new ArrayList<>();
    private int position;


    public BoardTile(Tile template, String path) {
        if(path != null) {
            try{
                image = ImageIO.read(new File(path));
            }
            catch(IOException e) {

            }
        }

        if(template == null) {
            this.position = -1;
            setBackground(Color.WHITE);
        }
        else {
            this.position = template.getPosition();
//            setText(template.toString());
            setBackground(Color.WHITE);
//            ImageIcon imageIcon = new ImageIcon(path);
//            Image image = imageIcon.getImage();
//            Image newimg = image.getScaledInstance(71, 71,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//            imageIcon = new ImageIcon(newimg);
//            setIcon(imageIcon);
        }

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
//        this.setIcon(new ImageIcon(path));

    }

    public void addPlayers(Player p) {
        playersOnTheSquare.add(p);

//        ImageIcon imageIcon = new ImageIcon("src/images/" + p.getSymbol().toString());
//        Image newimg = imageIcon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//        imageIcon = new ImageIcon(newimg);
//        setIcon(imageIcon);
    }

    public void emptyPlayers() {
        playersOnTheSquare.clear();
    }

    public void updateIcons(Player.Symbol turn) {
        if(playersOnTheSquare.size() > 1) {
            for(Player p: playersOnTheSquare) {
                if(p.getSymbol() == turn) {
                    ImageIcon imageIcon = new ImageIcon("src/images/" + p.getSymbol().toString() + ".png");
                    System.out.println("src/images/" + p.getSymbol().toString() + ".png");
                    Image newimg = imageIcon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);
                    setIcon(imageIcon);
                }
            }
        }
        else if(playersOnTheSquare.size() == 1) {
            ImageIcon imageIcon = new ImageIcon("src/images/" + playersOnTheSquare.get(0).getSymbol().toString());
            Image newimg = imageIcon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newimg);
            setIcon(imageIcon);
        }
        else {
            setIcon(null);
        }
    }


//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (image != null)
//        {
//
//            int x = getX();
//            int y = getY();
//            g.drawImage(image,x,y, getWidth(), getHeight(),null);
//        }
//    }


    public int getPosition() {
        return position;
    }

}
