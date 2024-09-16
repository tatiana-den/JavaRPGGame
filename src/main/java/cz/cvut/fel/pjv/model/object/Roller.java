package cz.cvut.fel.pjv.model.object;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Roller extends GameObject{
    public Roller (){
        name = "Roller";

        try{
            image = ImageIO.read(new File("src/main/resources/objects/roller.png"));
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }
}
