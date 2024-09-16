package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.model.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Heart extends GameObject{

    GamePanel gamePanel;

    public Heart (GamePanel gamePanel){
        name = "Heart";
        this.gamePanel = gamePanel;

        try{
            image = ImageIO.read(new File("src/main/resources/objects/fullheart.png"));
            image2 = ImageIO.read(new File("src/main/resources/objects/emptyheart.png"));
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }
}
