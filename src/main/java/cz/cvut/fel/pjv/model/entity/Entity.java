package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.model.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private int Health = 2;

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    private boolean isEnemy = false;

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    protected int worldX;

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    protected int worldY;
    protected int speed;
    protected String direction;
    protected int maxEnergy;
    protected int energy;


    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public Integer getSpeed() { return speed; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getEnergy() { return energy; }
    public void setEnergy(int energy){
        this.energy = energy;
    }

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    protected int spriteCounter = 0;
    protected int spriteNumber = 1;

    protected Rectangle solidArea;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collisionOn = false;
    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }
    public Rectangle getSolidArea() { return solidArea; }
    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }


    GamePanel gamePanel;
    public Entity(GamePanel gamePanel){ this.gamePanel = gamePanel; }
    /**
     * draw entity
     */
    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        // for the rendering efficiency
        if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()){


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

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(),gamePanel.getTileSize(), null);
        }
    }


    /**
     * update position of the entity
     */

    public void update(){

        collisionOn = false;
        gamePanel.getCollisionChecker().checkTile(this);
        gamePanel.getCollisionChecker().checkObject(this, false);
        gamePanel.getCollisionChecker().checkPlayer(this);

        spriteCounter++;
        if(spriteCounter > 10){
            if(spriteNumber == 1){ spriteNumber = 2; }
            else if(spriteNumber == 2){ spriteNumber = 1; }
            spriteCounter = 0;
        }

        if(!collisionOn){

            switch (direction) {

                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }
}
