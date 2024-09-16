package cz.cvut.fel.pjv.model.game;

import cz.cvut.fel.pjv.controller.InputHandler;
import cz.cvut.fel.pjv.controller.collision.CollisionChecker;
import cz.cvut.fel.pjv.model.entity.Enemy;
import cz.cvut.fel.pjv.model.entity.EnemyCreator;
import cz.cvut.fel.pjv.model.entity.Player;
import cz.cvut.fel.pjv.model.object.GameObject;
import cz.cvut.fel.pjv.model.object.GameObjectCreator;
import cz.cvut.fel.pjv.view.map.TileManager;
import cz.cvut.fel.pjv.view.Window;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable {

    private static final String NAME_SAVE_FILE = "src/main/resources/save/save.txt";


    //TILE SIZE
    private static final int ORIGINAL_TILE_SIZE = 16;     // size of one tile will always be 16x16
    private static final int SCALE = 4;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    public int getTileSize() { return TILE_SIZE; }


    //SCREEN SIZE
    private static final int MAX_SCREEN_COLUMN = 16;
    private static final int MAX_SCREEN_ROW = 12;


    //WORLD SIZE
    private static final int MAX_WORLD_ROW = 32;
    private static final int MAX_WORLD_COLUMN = 32;

    public int getMaxWorldRow() { return MAX_WORLD_ROW; }
    public int getMaxWorldColumn() { return MAX_WORLD_COLUMN; }


    //SCREEN SIZE
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN;
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    public int getScreenWidth() { return SCREEN_WIDTH; }
    public int getScreenHeight() { return SCREEN_HEIGHT; }


    //FPS
    private static final int FPS = 60;


    Thread gameThread;
    public void setGameThread(Thread gameThread) { this.gameThread = gameThread; }

    InputHandler inputHandler = new InputHandler();

    private final Player player = new Player(this, inputHandler);
    public Player getPlayer() { return player; }


    TileManager tileManager = new TileManager(this);
    public TileManager getTileManager() { return tileManager; }

    public final GameObject[] object = new GameObject[10];    // displaying 10 objects at the same time
    public Enemy[] enemy;
    GameObjectCreator gameObjectCreator = new GameObjectCreator(this);

    public GameObjectCreator getGameObjectCreator() {
        return gameObjectCreator;
    }

    EnemyCreator enemyCreator;



    private final CollisionChecker collisionChecker = new CollisionChecker(this);
    public CollisionChecker getCollisionChecker() { return collisionChecker; }
    public Window window = new Window(this);

    public Map<String, Integer> savedMap = null; //TODO
    public Map<String, Integer> getSavedMap() { return savedMap; }


    //Set player's default position
    private final int playerX = 100;
    private final int playerY = 100;
    private final int playerSpeed = 4;


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        enemy = new Enemy[4];
        enemyCreator = new EnemyCreator(this);
        setUpEnemy();
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);          //improves rendering
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
        startGameThread();
    }


    //read data from file
    public Map<String, Integer> readSavedData(){
        savedMap = new LinkedHashMap<String, Integer>();

        try {
            Scanner scanner = new Scanner(new FileReader(NAME_SAVE_FILE));
            String line;
            while(scanner.hasNext()) {
                line = scanner.nextLine();
                String splitted[] = line.split("=");
                savedMap.put(splitted[0], Integer.valueOf(splitted[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public void checkForSaveGame() {
        if(inputHandler.getKeySPressed()){
            FileWriter file = null;
            try {
                file = new FileWriter(NAME_SAVE_FILE, false);    //false -> rewrites parameters
                file.write("#PlayerX=" + player.getScreenX()
                        +"\n#PlayerY=" +  player.getScreenY()
                        +"\n#WorldX=" +  player.getWorldX()
                        +"\n#WorldY=" +  player.getWorldY());
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    /**
     * Runs main game thread using render and update methods.
     */
    @Override
    public void run() {

        while(gameThread != null){

            double drawInterval = 1000000000/FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime<0){ remainingTime = 0; }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * paint component
     */
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        tileManager.draw(graphics2D);
        for(int i = 0; i < object.length; i++){
            if(object[i] != null){ object[i].draw(graphics2D, this); }
        }

        for(int i = 0; i < enemy.length; i++){
            if(enemy[i] != null){ enemy[i].draw(graphics2D); }
        }

        player.draw(graphics2D);
        Window.draw(graphics2D);
        graphics2D.dispose();
    }


    public void setUpGame(){ gameObjectCreator.setObject(); }
    public void setUpEnemy(){ enemyCreator.setEnemy(); }


    /**
     * Updates objects state in every rendered frame.
     */
    public void update() {
        player.update();
        Enemy[] newEnemy = new Enemy[4];
        for(int i = 0; i < enemy.length; i++){
            if(enemy[i] != null){
                if(enemy[i].getHealth() <= 0)continue;
                enemy[i].update();
                newEnemy[i] = enemy[i];
            }
        }
        enemy = newEnemy;
        checkForSaveGame(); //checks if the game was saved and splits it by key values to map
    }
}
