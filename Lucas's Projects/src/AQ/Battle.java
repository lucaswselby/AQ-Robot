package AQ;

import static AQ.Main.click;
import java.awt.Color;
import java.awt.Robot;
import java.awt.image.BufferedImage;
//import javax.swing.ImageIcon;
//import javax.swing.JOptionPane;
import nicksapps.MainPlus;
//import java.awt.Graphics;

public class Battle implements AQ {
    public Battle(BufferedImage bimg, Robot aRobot) {
//        System.out.println("W: " + bimg.getWidth() + "\nH: " + bimg.getHeight());
//        bimg = bimg.getSubimage(110, 3, 2, 11);
//        Graphics g = bimg.createGraphics();
//        ImageIcon icon = new ImageIcon();
//        icon.setImage(bimg);
//        JOptionPane.showMessageDialog(null, icon);
//        MainPlus.testStop();
        if (isAllWhite(bimg.getSubimage(30, 3, 2, 11)) && isAllWhite(bimg.getSubimage(98, 3, 2, 11)))
            name = "Darksteel Guard";
        else if (isAllWhite(bimg.getSubimage(42, 3, 2, 11)) && isAllWhite(bimg.getSubimage(63, 3, 2, 11)))
            name = "Hybee Scout";
        else if (isAllWhite(bimg.getSubimage(81, 3, 2, 11)) && isAllWhite(bimg.getSubimage(83, 3, 2, 11)))
            name = "Savvy Merchant";
        else if (isAllWhite(bimg.getSubimage(74, 3, 2, 11)) && isAllWhite(bimg.getSubimage(83, 3, 2, 11)))
            name = "O'Meany";
        else if (isAllWhite(bimg.getSubimage(35, 3, 2, 11)) && isAllWhite(bimg.getSubimage(90, 3, 2, 11)))
            name = "Moglin Friends";
        else if (isAllWhite(bimg.getSubimage(38, 3, 2, 11)) && isAllWhite(bimg.getSubimage(116, 3, 2, 11)))
            name = "Lord Arrgthas";
        else if (isAllWhite(bimg.getSubimage(44, 3, 2, 11)) && isAllWhite(bimg.getSubimage(70, 3, 2, 11)))
            name = "Drakel Militia Group";
        else if (isAllWhite(bimg.getSubimage(13, 3, 2, 11)) && isAllWhite(bimg.getSubimage(137, 3, 2, 11)))
            name = "Rare Treasure Chest";
        else if (isAllWhite(bimg.getSubimage(52, 3, 2, 11)) && isAllWhite(bimg.getSubimage(110, 3, 2, 11)))
            name = "The Seeker";
        else if (isAllWhite(bimg.getSubimage(24, 3, 2, 11)) && isAllWhite(bimg.getSubimage(64, 3, 2, 11)))
            name = "Dynablade Drakel";
        else if (isAllWhite(bimg.getSubimage(93, 3, 2, 11)) && isAllWhite(bimg.getSubimage(103, 3, 2, 11)))
            name = "Grabbi";
        else if (isAllWhite(bimg.getSubimage(96, 3, 2, 11)) && isAllWhite(bimg.getSubimage(104, 3, 2, 11)))
            name = "Animal Box";
        else if (isAllWhite(bimg.getSubimage(83, 3, 2, 11)) && isAllWhite(bimg.getSubimage(118, 3, 2, 11)))
            name = "Gnuvain the Changer";
        else if (isAllWhite(bimg.getSubimage(70, 3, 2, 11)) && isAllWhite(bimg.getSubimage(87, 3, 2, 11)))
            name = "Uncle Krenos";
        else if (isAllWhite(bimg.getSubimage(48, 3, 2, 11)) && isAllWhite(bimg.getSubimage(103, 3, 2, 11)))
            name = "Ninja Cleric";
        else if (isAllWhite(bimg.getSubimage(62, 3, 2, 11)) && isAllWhite(bimg.getSubimage(110, 3, 2, 11)))
            name = "Rayfish";
        //more else if's
        else
            name = REGULAR;
        robot = aRobot;
    }
    
    protected Battle(String aName, Robot aRobot) {    //debugging only
        robot = aRobot;
        name = aName;
    }
    
    static boolean isAllWhite(BufferedImage bimg) {
        for (int x = 0; x < bimg.getWidth(); x++) {
            for (int y = 0; y < bimg.getHeight(); y++) {
                Color white = new Color(bimg.getRGB(x, y));
                if (white.getRed() < 240 || white.getBlue() < 240 || white.getGreen() < 240)
                    return false;
            }
        }
        return true;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean rayfish() {
        if (this.getName().equals("Rayfish")) {
            robot.mouseMove(ATTACK_X, FLEE_Y);
            click(robot);
            robot.mouseMove(WEAPON_X, FLEE_2_Y);
            robot.delay(100);
            while (!robot.getPixelColor(WEAPON_X, FLEE_2_Y).equals(Color.GREEN)) {
                Main.attack(robot);
                robot.mouseMove(ATTACK_X, FLEE_Y);
                click(robot);
                robot.mouseMove(WEAPON_X, FLEE_2_Y);
                robot.delay(100);
            }
            click(robot);
            robot.mouseMove(ATTACK_X, DEATH_NEXT_Y);
            click(robot);
            return true;
        }
        return false;
    }
    
    public boolean hybeeScout() {
        if (this.getName().equals("Hybee Scout")) {
            robot.mouseMove(HYBEE_SCOUT_MORE_X, HYBEE_SCOUT_MORE_Y);
            click(robot);
            click(robot);
            robot.delay(1000);
            robot.mouseMove(HYBEE_SCOUT_NO_X, HYBEE_SCOUT_NO_Y);
            click(robot);
            return true;
        }
        return false;
    }
    
    public boolean moglinFriends() {
        if (this.getName().equals("Moglin Friends")) {
            robot.mouseMove(MOGLIN_FRIENDS_X, MOGLIN_FRIENDS_POTIONS_Y);
            click(robot);
            robot.mouseMove(MOGLIN_FRIENDS_X, MOGLIN_FRIENDS_GOODBYE_Y);
            click(robot);
            return true;
        }
        return false;
    }
    
    public boolean theSeeker() {
        if (this.getName().equals("The Seeker")) {
            robot.mouseMove(THE_SEEKER_X, THE_SEEKER_Y);
            click(robot);
            robot.delay(100);
            click(robot);
            return true;
        }
        return false;
    }
    
    public void darksteelGuard() {
        if (this.getName().equals("Darksteel Guard") || this.getName().equals("Dynablade Drakel")) {
            robot.mouseMove(DARKSTEEL_GUARD_X, DARKSTEEL_GUARD_Y);
            click(robot);
            robot.delay(1000);
        }
    }
    
    public void lordArrgthas() {
        if (this.getName().equals("Lord Arrgthas")) {
            robot.mouseMove(LORD_ARRGTHAS_X, LORD_ARRGTHAS_Y);
            click(robot);
            robot.delay(1000);
        }
    }
    
    public void grabbi() {
        if (this.getName().equals("Grabbi")) {
            robot.mouseMove(DARKSTEEL_GUARD_X, GRABBI_Y);
            click(robot);
            robot.delay(1000);
        }
    }
    
    public void uncleKrenos() {
        if (this.getName().equals("Uncle Krenos")) {
            robot.mouseMove(ATTACK_X, UNCLE_KRENOS_Y);
            click(robot);
            robot.delay(1000);
        }
    }
    
    public void drakelWarParty() {
        if (this.getName().equals("Drakel Militia Group")) {
            System.exit(0);
            robot.mouseMove(ATTACK_X, FLEE_Y);
            click(robot);
            robot.mouseMove(WEAPON_X, FLEE_2_Y);
            click(robot);
        }
    }
    
    public void savvyMerchant() {
        if (this.getName().equals("Savvy Merchant")) {
        System.exit(0);
        robot.mouseMove(SAVVY_MERCHANT_MORE_X, SAVVY_MERCHANT_MORE_Y);
            click(robot);
            robot.delay(1000);
            robot.mouseMove(SAVVY_MERCHANT_MORE_X, SAVVY_MERCHANT_HELP_Y);
            click(robot);
            robot.mouseMove(SAVVY_MERCHANT_ROLL_X, SAVVY_MERCHANT_ROLL_Y);
            click(robot);
            robot.delay(6000);
            if (isSavvyMerchantSuccess()) {
                robot.delay(2000);
                robot.mouseMove(SAVVY_MERCHANT_NEXT_X, SAVVY_MERCHANT_NEXT_Y);
                click(robot);
                // other stuff before win screen
            }
            if (!isSavvyMerchantSuccess()) {
                robot.delay(2000);
                robot.mouseMove(SAVVY_MERCHANT_NEXT_X, SAVVY_MERCHANT_NEXT_Y);
                click(robot);
                robot.mouseMove(SAVVY_MERCHANT_MORE_X, SAVVY_MERCHANT_MORE_Y);
                click(robot);
                // skip win screen
            }
        }
    }
    
    public boolean isSavvyMerchantSuccess() {
        if (this.getName().equals("Savvy Merchant")) {
            BufferedImage win = robot.createScreenCapture(FAILURE);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 150 || tan.getRed() > 155 || tan.getGreen() > 5 || tan.getBlue() > 5)
                        return true;
                }
            }
        }
        return false;
    }
    
    public boolean isRareTreasureChest() {
        if (this.getName().equals("Rare Treasure Chest")) {
            return true;
        }
        return false;
    }
    
    public boolean ninjaCleric() {
        if (this.getName().equals("Ninja Cleric")) {
            return true;
        }
        return false;
    }
    
    private String name;
    private Robot robot;
    
    public static final String REGULAR = "Regular Monster";
}