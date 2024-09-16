package cz.cvut.fel.pjv.view.map;

import cz.cvut.fel.pjv.model.game.GamePanel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

@Slf4j
public class TileManager extends Tile{

    private final GamePanel gamePanel;
    private final Tile[] tile;

    private final int[][] mapTileNum;

    public Tile[] getTile() { return tile; }
    public int[][] getMapTileNum() { return mapTileNum; }


    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        mapTileNum = new int[gamePanel.getMaxWorldColumn()][gamePanel.getMaxWorldRow()];

        fillTileImage();
        loadMap("src/main/resources/maps/map0.txt");
    }


    /**
     * loading the images of textures
     */
    public void fillTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new File("src/main/resources/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new File("src/main/resources/tiles/fence.png")));
            tile[1].setCollision(true);

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new File("src/main/resources/tiles/water.png")));
            tile[2].setCollision(true);

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new File("src/main/resources/tiles/bridge.png")));
        } catch (IOException exception){
            log.error("Tile image not found.");
            exception.printStackTrace();
        }
    }


    /**
     * Loads a map.
     * @param filePath
     */
    public void loadMap(String filePath){
        try{
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            while(column < gamePanel.getMaxWorldColumn() && row < gamePanel.getMaxWorldRow()){
                String line = bufferedReader.readLine();

                while (column < gamePanel.getMaxWorldColumn()){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    mapTileNum[column][row] = num;
                    column++;
                }
                if (column == gamePanel.getMaxWorldColumn()){
                    column = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch(Exception ignored){

        }
    }


    /**
     * rendering
     */
    public void draw(Graphics2D graphics2D){
        int column = 0;
        int row = 0;

        while(column < gamePanel.getMaxWorldColumn() && row < gamePanel.getMaxWorldRow()){

            int tileNumber = mapTileNum[column][row];
            int worldX = column * gamePanel.getTileSize();
            int worldY = row * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

            if(worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                    worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                    worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                    worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

                graphics2D.drawImage(tile[tileNumber].getImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }

            column++;
            worldX += gamePanel.getTileSize();
            if(column == gamePanel.getMaxWorldColumn()){
                column = 0;
                worldX = 0;
                row++;
                worldY += gamePanel.getTileSize();
            }
        }
    }
}
