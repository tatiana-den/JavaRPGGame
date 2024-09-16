package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.model.game.GamePanel;

public class GameObjectCreator {

    GamePanel gamePanel;
    public GameObjectCreator(GamePanel gamePanel) { this.gamePanel = gamePanel; }


    public void setObject(){

        //House
        gamePanel.object[0] = new House();
        gamePanel.object[0].worldX = 6 * gamePanel.getTileSize();
        gamePanel.object[0].worldY = 7 * gamePanel.getTileSize();

        //1. bone
        gamePanel.object[1] = new Bone();
        gamePanel.object[1].worldX = 27 * gamePanel.getTileSize();
        gamePanel.object[1].worldY = 17 * gamePanel.getTileSize();

        //2. bone
        gamePanel.object[2] = new Bone();
        gamePanel.object[2].worldX = 14 * gamePanel.getTileSize();
        gamePanel.object[2].worldY = 4 * gamePanel.getTileSize();

        //3. bone
        gamePanel.object[3] = new Bone();
        gamePanel.object[3].worldX = 28 * gamePanel.getTileSize();
        gamePanel.object[3].worldY = 6 * gamePanel.getTileSize();

        //4. bone
        gamePanel.object[4] = new Bone();
        gamePanel.object[4].worldX = 6 * gamePanel.getTileSize();
        gamePanel.object[4].worldY = 27 * gamePanel.getTileSize();

        //5. bone
        gamePanel.object[5] = new Bone();
        gamePanel.object[5].worldX = 26 * gamePanel.getTileSize();
        gamePanel.object[5].worldY = 29 * gamePanel.getTileSize();

        //Roller
        gamePanel.object[6] = new Roller();
        gamePanel.object[6].worldX = 25 * gamePanel.getTileSize();
        gamePanel.object[6].worldY = 29 * gamePanel.getTileSize();
    }
}
