package cz.cvut.fel.pjv.model.object;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class House extends GameObject{

    public House (){
        name = "House";

        try{
            image = ImageIO.read(new File("src/main/resources/objects/house.png"));
        }catch(IOException ie){
            ie.printStackTrace();
        }
        collision = true;
    }
}
