package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.model.game.GamePanel;
import cz.cvut.fel.pjv.view.Window;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class Player extends Entity {

    InputHandler inputHandler;
    private boolean isBoots = false;

    public boolean isBoots() {
        return isBoots;
    }

    private final int screenX;
    private final int screenY;
    private int hasBone = 0;
    private int energyLevel = 3;

    public void setHasBone(int hasBone) {
        this.hasBone = hasBone;
    }

    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }
    public int getHasBone() { return hasBone; }


    public Player(GamePanel gamePanel, InputHandler inputHandler) {

        super(gamePanel);
        this.inputHandler = inputHandler;

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY=solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        //Returns to saved position if it exists
        gamePanel.readSavedData();
        Map<String, Integer> savedMap = gamePanel.getSavedMap();
        if(!savedMap.isEmpty()){
            screenX = savedMap.get("#PlayerX");
            screenY = savedMap.get("#PlayerY");
            worldX = savedMap.get("#WorldX");
            worldY = savedMap.get("#WorldY");
        }else {
            screenX = gamePanel.getScreenWidth() / 2 - gamePanel.getTileSize() / 2;
            screenY = gamePanel.getScreenHeight() / 2 - gamePanel.getTileSize() / 2;
            worldX = gamePanel.getTileSize() * 16;
            worldY = gamePanel.getTileSize() * 16;
        }

        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues(){
        speed = 4;
        direction = "down";
        maxEnergy = 3;
        energy = maxEnergy;
    }

    /**
     * load the images of player's hero
     */
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(new File("src/main/resources/player/dogback1.png"));
            up2 = ImageIO.read(new File("src/main/resources/player/dogback2.png"));
            down1 = ImageIO.read(new File("src/main/resources/player/dogfront1.png"));
            down2 = ImageIO.read(new File("src/main/resources/player/dogfront2.png"));
            left1 = ImageIO.read(new File("src/main/resources/player/dogleft1.png"));
            left2 = ImageIO.read(new File("src/main/resources/player/dogleft2.png"));
            right1 = ImageIO.read(new File("src/main/resources/player/dogright1.png"));
            right2 = ImageIO.read(new File("src/main/resources/player/dogright2.png"));
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }


    public void pickUpObject(int i){
        if(i != 111){
            String objectName = gamePanel.object[i].getName();
            switch (objectName) {
                case "Bone" -> {
                    hasBone++;
                    gamePanel.object[i] = null;
                    Window.showMessage("Wow! You got a bone!");
                }
                case "Roller" -> {
                    isBoots = true;
                    speed += 4;
                    gamePanel.object[i] = null;
                    Window.showMessage("You are running faster!");
                }
                case "House" -> {
                    if (hasBone == 5) {
                        gamePanel.window.setGameFinished(true, true);
                    } else {
                        Window.showMessage("You haven't collected all bones!");
                    }
                }
            }
        }
    }

    public void interactEnemy(int i){
        if(i != 111){
            energyLevel -= 1;
            log.info("You've hit another doggo, be careful!");
        }
    }


    /**
     * Updates objects state in every rendered frame.
     */
    public void update() {

        if(energy <= 0) gamePanel.window.setGameFinished(true, false);
        if(inputHandler.getUpPressed() || inputHandler.getDownPressed() ||
                inputHandler.getRightPressed() || inputHandler.getLeftPressed()){
            if(inputHandler.getUpPressed()){ direction = "up"; }
            if(inputHandler.getDownPressed()){ direction = "down"; }
            if(inputHandler.getRightPressed()){ direction = "right"; }
            if(inputHandler.getLeftPressed()){ direction = "left"; }


            collisionOn = false;
            gamePanel.getCollisionChecker().checkTile(this);
            int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
            pickUpObject(objectIndex);
            int enemyIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.enemy);
            interactEnemy(enemyIndex);

            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNumber == 1){ spriteNumber = 2; }
                else if(spriteNumber == 2){ spriteNumber = 1; }
                spriteCounter = 0;
            }

            if(!collisionOn){
                switch (direction) {

                    case "up" -> {
                        if (!isBoots)
                            speed=4;
                        else
                            speed=8;
                        worldY -= speed;
                    }
                    case "down" -> {
                        if (!isBoots)
                            speed=4;
                        else
                            speed=8;
                        worldY += speed;
                    }
                    case "left" -> {
                        if (!isBoots)
                            speed=4;
                        else
                            speed=8;
                        worldX -= speed;
                    }
                    case "right" -> {
                        if (!isBoots)
                            speed=4;
                        else
                            speed=8;
                        worldX += speed;
                    }
                }
            }
        }
    }


    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNumber == 1) { image = up1; }
                if (spriteNumber == 2) { image = up2; }
            }
            case "down" -> {
                if (spriteNumber == 1) { image = down1; }
                if (spriteNumber == 2) { image = down2; }
            }
            case "left" -> {
                if (spriteNumber == 1) { image = left1; }
                if (spriteNumber == 2) { image = left2; }
            }
            case "right" -> {
                if (spriteNumber == 1) { image = right1; }
                if (spriteNumber == 2) { image = right2; }
            }
            default -> image = down1;
        }
        graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}
