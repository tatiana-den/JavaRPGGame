package cz.cvut.fel.pjv.model.object;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bone extends GameObject{

    public Bone (){
        name = "Bone";

        try{
            image = ImageIO.read(new File("src/main/resources/objects/bone.png"));
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }
}
