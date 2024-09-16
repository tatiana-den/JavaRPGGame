package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.model.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Enemy extends Entity {

    Random random = new Random();
    private int actionStopCounter = 0;

    public Enemy(GamePanel gamePanel){

        super(gamePanel);

        worldX = gamePanel.getTileSize() * 16;
        worldY = gamePanel.getTileSize() * 16;
        speed = 2;
        direction = "down";
        int life = 4;

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY=solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getEnemyImage();
    }

    /**
     * loads enemy texture from file
     */
    public void getEnemyImage(){
        try {
            up1 = ImageIO.read(new File("src/main/resources/enemy/enemyback1.png"));
            up2 = ImageIO.read(new File("src/main/resources/enemy/enemyback2.png"));
            down1 = ImageIO.read(new File("src/main/resources/enemy/enemyfront1.png"));
            down2 = ImageIO.read(new File("src/main/resources/enemy/enemyfront2.png"));
            left1 = ImageIO.read(new File("src/main/resources/enemy/enemyleft1.png"));
            left2 = ImageIO.read(new File("src/main/resources/enemy/enemyleft2.png"));
            right1 = ImageIO.read(new File("src/main/resources/enemy/enemyright1.png"));
            right2 = ImageIO.read(new File("src/main/resources/enemy/enemyright2.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * controls the behavior of the enemy
     */
    public void setAction(){

        actionStopCounter++;

        if(actionStopCounter == 100){
            Random random = new Random();
            int i = random.nextInt(100);
            if(i <= 25){ direction = "up"; }
            if(i > 25 && i <= 50){ direction = "down"; }
            if(i > 50 && i <= 75){ direction = "left"; }
            if(i > 75){ direction = "right"; }
            actionStopCounter = 0;
        }


    }
}
