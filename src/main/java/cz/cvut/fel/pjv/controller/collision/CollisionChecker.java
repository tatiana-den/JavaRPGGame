package cz.cvut.fel.pjv.controller.collision;

import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.game.GamePanel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionChecker {

    private final GamePanel gamePanel;


    /**
     * Constructs a CollisionChecker object with the specified GamePanel.
     *
     * @param gamePanel The GamePanel object.
     */
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    /**
     * Checks for collision between an entity and tiles.
     *
     * @param entity The entity to check for collision.
     */
    public void checkTile(Entity entity) {
        // Calculates the world coordinates of the entity's boundaries
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        // Calculates the tile indices for the entity's boundaries
        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNum1;
        int tileNum2;

        entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();
        entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
        entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
        entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
        switch (entity.getDirection()) {
            case "up" -> {

                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision() || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                    log.info("Collision detected between entity and tiles (up)");

                }
            }
            case "down" -> {

                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision() || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                    log.info("Collision detected between entity and tiles (down)");

                }
            }
            case "left" -> {

                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision() || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                    log.info("Collision detected between entity and tiles (left)");

                }
            }
            case "right" -> {

                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision() || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                    log.info("Collision detected between entity and tiles (right)");

                }
            }
        }
    }


    /**
     * Checks for collision between an entity and objects.
     *
     * @param entity The entity to check for collision.
     * @param player Flag indicating if the entity is the player.
     * @return The index of the object with which the entity collided, or 999 if no collision occurred.
     */
    public int checkObject(Entity entity, boolean player) {

        int index = 111; // Default index value indicating no object collision

        for (int i = 0; i < gamePanel.object.length; i++) { // Scanning objects array
            if (gamePanel.object[i] != null) {

                // Get the entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get the object's solid area position
                gamePanel.object[i].solidArea.x = gamePanel.object[i].getWorldX() + gamePanel.object[i].solidArea.x;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].getWorldY() + gamePanel.object[i].solidArea.y;

                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].setCollision(true)) { entity.setCollisionOn(true); }
                            if (player) { index = i; }
                        }
                    }
                    case "down" -> {
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].setCollision(true)) { entity.setCollisionOn(true); }
                            if (player) { index = i; }
                        }
                    }
                    case "left" -> {
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].setCollision(true)) { entity.setCollisionOn(true); }
                            if (player) { index = i; }
                        }
                    }
                    case "right" -> {
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].setCollision(true)) { entity.setCollisionOn(true); }
                            if (player) { index = i; }
                        }
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                gamePanel.object[i].solidArea.x = gamePanel.object[i].solidAreaDefaultX;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Checks if player hits an enemy
     */

    public int checkEntity(Entity entity, Entity[] target){
        int index = 111; // Default index value indicating no object collision

        for (int i = 0; i < target.length; i++) { // Scanning objects array
            if (target[i] != null) {

                // Get the entity's solid area position
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Get the object's solid area position
                target[i].getSolidArea().x = target[i].getWorldX() + target[i].getSolidArea().x;
                target[i].getSolidArea().y = target[i].getWorldY() + target[i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            target[i].setHealth(target[i].getHealth()-1);
                            entity.setEnergy(entity.getEnergy()-1);
                            index = i;
                        }
                    }
                    case "down" -> {
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            target[i].setHealth(target[i].getHealth()-1);
                            entity.setEnergy(entity.getEnergy()-1);
                            index = i;
                        }
                    }
                    case "left" -> {
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            target[i].setHealth(target[i].getHealth()-1);
                            entity.setEnergy(entity.getEnergy()-1);
                            index = i;
                        }
                    }
                    case "right" -> {
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            target[i].setHealth(target[i].getHealth()-1);
                            entity.setEnergy(entity.getEnergy()-1);
                            index = i;
                        }
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();
                target[i].getSolidArea().x = target[i].getSolidAreaDefaultX();
                target[i].getSolidArea().y = target[i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    /**
     * Checks if enemy hits an enemy
     */

    public void checkPlayer(Entity entity){
        // Get the entity's solid area position
        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        // Get the object's solid area position
        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSolidArea().x;
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case "up" -> {
                entity.getSolidArea().y -= entity.getSpeed();
                if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entity.getSolidArea().y += entity.getSpeed();
                if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entity.getSolidArea().x -= entity.getSpeed();
                if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entity.getSolidArea().x += entity.getSpeed();
                if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                    entity.setCollisionOn(true);
                }
            }
        }
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();
        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX();
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY();
    }
}