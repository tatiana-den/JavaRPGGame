package cz.cvut.fel.pjv.controller.collision;


import cz.cvut.fel.pjv.model.entity.Enemy;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.entity.Player;
import cz.cvut.fel.pjv.model.game.GamePanel;
import cz.cvut.fel.pjv.view.map.Tile;
import cz.cvut.fel.pjv.view.map.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CollisionCheckerTest {

    @Mock
    GamePanel gamePanel;

    @Mock
    Player player;

    @Mock
    TileManager tileManager;

    int[][] map = new int[3][3];

    Tile[] tile = new Tile[1];

    @Mock
    Tile tileExample;
    private CollisionChecker collisionChecker;

    @BeforeEach
    public void init() {
        Rectangle rectangle = new Rectangle(1,1);
        Mockito.when(player.getWorldX()).thenReturn(1);
        Mockito.when(player.getSolidArea()).thenReturn(rectangle);
        Mockito.when(player.getWorldY()).thenReturn(1);

        Mockito.when(gamePanel.getTileSize()).thenReturn(1);

        Mockito.when(gamePanel.getTileManager()).thenReturn(tileManager);
        Mockito.when(tileManager.getMapTileNum()).thenReturn(map);


        collisionChecker = new CollisionChecker(gamePanel);
    }

    @Test
    void checkTile_UP_collision() {
        Mockito.when(player.getDirection()).thenReturn("up");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(true);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player).setCollisionOn(true);
    }

    @Test
    void checkTile_UP_noCollision() {
        Mockito.when(player.getDirection()).thenReturn("up");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(false);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player, never()).setCollisionOn(true);
    }

    @Test
    void checkTile_DOWN_collision() {
        Mockito.when(player.getDirection()).thenReturn("down");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(true);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player).setCollisionOn(true);
    }

    @Test
    void checkTile_DOWN_noCollision() {
        Mockito.when(player.getDirection()).thenReturn("down");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(false);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player, never()).setCollisionOn(true);
    }

    @Test
    void checkTile_LEFT_collision() {
        Mockito.when(player.getDirection()).thenReturn("left");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(true);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player).setCollisionOn(true);
    }

    @Test
    void checkTile_LEFT_noCollision() {
        Mockito.when(player.getDirection()).thenReturn("left");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(false);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player, never()).setCollisionOn(true);
    }

    @Test
    void checkTile_RIGHT_collision() {
        Mockito.when(player.getDirection()).thenReturn("right");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(true);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player).setCollisionOn(true);
    }

    @Test
    void checkTile_RIGHT_noCollision() {
        Mockito.when(player.getDirection()).thenReturn("right");

        tile[0] = tileExample;
        Mockito.when(tileExample.isCollision()).thenReturn(false);
        Mockito.when(tileManager.getTile()).thenReturn(tile);

        collisionChecker.checkTile(player);

        Mockito.verify(player, never()).setCollisionOn(true);
    }
}