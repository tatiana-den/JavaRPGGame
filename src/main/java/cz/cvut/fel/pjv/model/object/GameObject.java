package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.model.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected BufferedImage image;
    protected BufferedImage image2;
    protected String name;
    protected boolean collision = false;
    protected int worldX;
    protected int worldY;

    public BufferedImage getImage() { return image; }
    public BufferedImage getImage2() { return image2; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }

    public String getName() { return name; }
    public boolean setCollision(boolean collision) { this.collision = collision; return collision; }


    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public static final int solidAreaDefaultX=0;
    public static final int solidAreaDefaultY=0;


    /**
     * rendering
     */
    public void draw(Graphics2D graphics2D, GamePanel gamePanel){

        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        // for the rendering efficiency
        if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()){

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(),gamePanel.getTileSize(), null);
        }
    }
}
