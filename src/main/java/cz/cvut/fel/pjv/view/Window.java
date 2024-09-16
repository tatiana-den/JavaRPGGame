package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.game.GamePanel;
import cz.cvut.fel.pjv.model.object.GameObject;
import cz.cvut.fel.pjv.model.object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class Window {

    static GamePanel gamePanel;

    static Font font;
    static Font fontBig;
    private static boolean messageOn = false;
    private static String message = "";
    private static int messageTime;

    public boolean isIsWin() {
        return isWin;
    }

    private static boolean gameFinished = false;

    public static boolean isGameFinished() {
        return gameFinished;
    }

    private static boolean isWin = false;
    static DecimalFormat decimalFormat = new DecimalFormat();
    static long playTime = 0;
    static long timeInGame = 0;
    static BufferedImage fullHeart;
    static BufferedImage emptyHeart;


    public void setGameFinished(boolean gameFinished, boolean isWin) {
        this.gameFinished = gameFinished;
        this.isWin = isWin;
    }


    public Window(GamePanel gamePanel){

        playTime = System.currentTimeMillis()/1000;
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 40);
        fontBig = new Font("Arial", Font.BOLD, 40);

        GameObject heart = new Heart(gamePanel);
        fullHeart = heart.getImage();
        emptyHeart = heart.getImage2();
    }


    public static void showMessage(String text){
        message = text;
        messageOn = true;
    }


    /**
     * rendering
     */
    public static void drawPlayerEnergy(Graphics2D graphics2D){

        int x = gamePanel.getTileSize()/2;
        int y = gamePanel.getTileSize()/2;
        int i = 0;

        //Draws max energy
        while (i < gamePanel.getPlayer().getMaxEnergy()){
            graphics2D.drawImage(emptyHeart, x + gamePanel.getScreenWidth() - 300, y ,50, 50, null);
            i++;
            x += gamePanel.getTileSize();
        }

        //Resetting
        x = gamePanel.getTileSize()/2;
        y = gamePanel.getTileSize()/2;
        i = 0;

        //Drawing current energy
        while(i < gamePanel.getPlayer().getEnergy()){
            graphics2D.drawImage(fullHeart, x + gamePanel.getScreenWidth()- 300, y ,50, 50, null);
            i++;
            x += gamePanel.getTileSize();
        }
    }


    /**
     * rendering
     */
    public static void draw(Graphics2D graphics2D){

        if(!gameFinished){

            //Showing amount of bones
            graphics2D.setFont(font);
            graphics2D.setColor(Color.black);
            graphics2D.drawString("Bones you have: " + gamePanel.getPlayer().getHasBone(), 20, 40);

            //Showing time on the screen
            timeInGame = System.currentTimeMillis()/1000 - playTime;
            graphics2D.drawString("Time: " + timeInGame+ " secs", 20, 90);

            //Showing message on the screen
            if(messageOn){
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.setColor(Color.yellow);
                graphics2D.drawString(message, 25, 130);
                messageTime++;
            }
            if(messageTime > 120){    //2 secs
                messageTime = 0;
                messageOn = false;
            }

            drawPlayerEnergy(graphics2D);
            if(timeInGame < 5){
                String text;
                int textLength;
                int x;
                int y;

                graphics2D.setFont(fontBig);
                graphics2D.setColor(Color.BLACK);
                text = "Welcome to the Game!";
                textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
                x = gamePanel.getScreenWidth()/2 - textLength/2;
                y = gamePanel.getScreenHeight()/2 - gamePanel.getTileSize() * 3;
                graphics2D.drawString(text, x, y);

                graphics2D.setFont(fontBig);
                graphics2D.setColor(Color.BLACK);
                text = "You have to collect all Bones and avoid Enemies ";
                textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
                x = gamePanel.getScreenWidth()/2 - textLength/2;
                y = gamePanel.getScreenHeight()/2 + gamePanel.getTileSize() * 4;
                graphics2D.drawString(text, x, y);

                graphics2D.setFont(fontBig);
                graphics2D.setColor(Color.yellow);
            }
        }

        else if(gameFinished && isWin){

            String text;
            int textLength;
            int x;
            int y;

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.yellow);
            text = "You won!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 - gamePanel.getTileSize() * 3;
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.white);
            text = "Your time is "+ timeInGame + " secs!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 + gamePanel.getTileSize() * 4;
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.yellow);
            text = "You are adorable!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 + gamePanel.getTileSize() * 2;
            graphics2D.drawString(text, x, y);

            gamePanel.setGameThread(null);

        }else {
            String text;
            int textLength;
            int x;
            int y;

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.yellow);
            text = "You Lose!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 - gamePanel.getTileSize() * 3;
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.white);
            text = "Your time is "+ timeInGame + " secs!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 + gamePanel.getTileSize() * 4;
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(fontBig);
            graphics2D.setColor(Color.yellow);
            text = "Try Harder!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth()/2 - textLength/2;
            y = gamePanel.getScreenHeight()/2 + gamePanel.getTileSize() * 2;
            graphics2D.drawString(text, x, y);

            gamePanel.setGameThread(null);
        }
    }
}