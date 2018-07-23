package AQ;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Ballyhoo {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.delay(3000);
        openChest(robot);
        robot.delay(22000);
        if (isGrey(robot)) {
            getPrizeGrey(robot);
//            savePrize(robot);
        }
        if (!isGrey(robot)) {
            getPrizeNotGrey(robot);
//            savePrize(robot);
        }
    }
    
    public static void openChest(Robot robot) {
        robot.mouseMove(OPEN_CHEST_X, OPEN_CHEST_Y);
        System.exit(0); //stops program to get previous xy coordinates
        Main.click(robot);
}
    
    public static boolean isGrey(Robot robot) {
        BufferedImage win = robot.createScreenCapture(GREYNESS);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 45 || tan.getRed() > 55 || tan.getGreen() < 45 || tan.getGreen() > 55 || tan.getBlue() > 45 || tan.getBlue() < 55)
                        return false;
                }
            }
            return true;
    }
    
    public static void getPrizeGrey(Robot robot) {
        robot.mouseMove(MORE_X, GET_PRIZE_GREY_Y);
        Main.click(robot);
    }
    
    public static void getPrizeNotGrey(Robot robot) {
        robot.mouseMove(GET_PRIZE_NOT_GREY_X, GET_PRIZE_NOT_GREY_Y);
        Main.click(robot);
    }
    
    public static void savePrize(Robot robot) {
        robot.delay(1000);
        robot.mouseMove(RETURN_TO_GAME_X, RETURN_TO_GAME_Y);
        Main.click(robot);
        robot.delay(500);
        robot.mouseMove(MORE_X, MORE_Y);
        Main.click(robot);
        Main.click(robot);
        Main.click(robot);
        robot.delay(1000);
    }
    
    public static final int OPEN_CHEST_X = 300;
    public static final int OPEN_CHEST_Y = 40;
    public static final Rectangle GREYNESS = new Rectangle (650, 270, 200, 200);
    public static final int GET_PRIZE_NOT_GREY_X = 1000;
    public static final int GET_PRIZE_NOT_GREY_Y = 630;
    public static final int RETURN_TO_GAME_X = 650;
    public static final int RETURN_TO_GAME_Y = 300;
    public static final int MORE_X = 850;
    public static final int MORE_Y = 360;
    public static final int GET_PRIZE_GREY_Y = 160;
}


