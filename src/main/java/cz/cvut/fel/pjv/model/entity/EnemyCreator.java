package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.model.game.GamePanel;

public class EnemyCreator {

    GamePanel gamePanel;
    public EnemyCreator(GamePanel gamePanel) { this.gamePanel = gamePanel; }
    /**
     * sets enemy position
     */
    public void setEnemy(){

        gamePanel.enemy[0] = new Enemy(gamePanel);
        gamePanel.enemy[0].worldX = gamePanel.getTileSize() * 14;
        gamePanel.enemy[0].worldY = gamePanel.getTileSize() * 5;
        gamePanel.enemy[0].setEnemy(true);

        gamePanel.enemy[1] = new Enemy(gamePanel);
        gamePanel.enemy[1].worldX = gamePanel.getTileSize() * 5;
        gamePanel.enemy[1].worldY = gamePanel.getTileSize() * 14;
        gamePanel.enemy[1].setEnemy(true);
    }


}
