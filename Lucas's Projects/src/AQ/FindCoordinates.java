package AQ;

import AQOCR.AQOCR;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class FindCoordinates implements AQ{
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.delay(3000);
        
//        Battle battle = new Battle("The Seeker", robot);
        
//        robot.mouseMove(650, 591);
//        robot.delay(100);
//        System.out.println(robot.getPixelColor(WEAPON_X, FLEE_2_Y));
        
        BufferedImage win = robot.createScreenCapture(TEST);
        Graphics g = win.createGraphics();
        ImageIcon icon = new ImageIcon();
        icon.setImage(win);
        JOptionPane.showMessageDialog(null, icon);
        
        System.out.println(AQOCR.recognize(win, Color.WHITE));
    }
    
    public static final Rectangle TEST = new Rectangle(650, 591, 80, 50);
}