package AQOCR;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
import nicksapps.NumberList;

public class OCRTest
{
    public static void main(String[] args) throws AWTException
    {
//        BufferedImage img = null;   //initialize image
//        try {
//            img = ImageIO.read(new File("test2.png"));  //read image from file
//        } catch (IOException e) { }
        
        Robot robot = new Robot();
        robot.delay(3000);
        BufferedImage img = robot.createScreenCapture(ELEMENT_BOX);
        
        NumberList numList = AQOCR.recognize(img, AQOCR.BLUEISH);  //record numbers from image into NumberList
        System.out.println(numList);    //display NumberList
    }
    
    public static final Rectangle ELEMENT_BOX = new Rectangle(1175, 225, 27, 143);
}