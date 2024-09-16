package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.controller.collision.CollisionChecker;
import cz.cvut.fel.pjv.model.entity.Enemy;
import cz.cvut.fel.pjv.model.entity.Player;
import cz.cvut.fel.pjv.model.game.GamePanel;
import cz.cvut.fel.pjv.view.Window;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppLogicTest {
    @Test
    public void TestGamePanel(){
        GamePanel gamePanel = new GamePanel();
        CollisionChecker collisionChecker = new CollisionChecker(gamePanel);
        Enemy[] enemies = new Enemy[1];
        enemies[0] = new Enemy(gamePanel);
        enemies[0].setWorldX(gamePanel.getTileSize() * 14);
        enemies[0].setWorldY(gamePanel.getTileSize() * 5);
        enemies[0].setEnemy(true);
        Player player = new Player(gamePanel,new InputHandler());
        player.setWorldX(gamePanel.getTileSize() * 14);
        player.setWorldY(gamePanel.getTileSize() * 5);
        collisionChecker.checkEntity(player, enemies);
        boolean expectCollision = true;
        Assertions.assertEquals(player.isCollisionOn(), expectCollision);
    }

//    @Test
//    public void checkIsBoosted(){
//        GamePanel gamePanel = new GamePanel();
//        CollisionChecker collisionChecker = new CollisionChecker(gamePanel);
//        Enemy[] enemies = new Enemy[1];
//        Player player = new Player(gamePanel,new InputHandler());
//        player.
//        player.
//        gamePanel.getCollisionChecker().checkObject(player, true);
//        boolean expectedResult = true;
//        player.update();
//        Assertions.assertEquals( expectedResult, player.isBoots());
//    }
    @Test
    public void GameIsFinished(){
        GamePanel gamePanel = new GamePanel();
        Player player = gamePanel.getPlayer();
        player.setEnergy(0);
        player.update();
        gamePanel.update();
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, Window.isGameFinished());

    }

    @Test
    public void GameIsWin(){
        GamePanel gamePanel = new GamePanel();
        Player player = gamePanel.getPlayer();
        gamePanel.getGameObjectCreator().setObject();
        player.setHasBone(5);
        player.setWorldX(6);
        player.setWorldX(7);
        player.pickUpObject(0);
        player.update();
        gamePanel.update();
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, gamePanel.window.isIsWin());

    }

}
