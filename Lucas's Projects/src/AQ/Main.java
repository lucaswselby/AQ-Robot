package AQ;

import AQOCR.AQOCR;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import nicksapps.MainPlus;
import nicksapps.NumberList;

public class Main implements AQ {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.delay(5000);  //Delay five seconds to open AQ
        int v = 0;
        int d = 0;
        while (true) {
//            battleMonsters(robot);
            long startTime = System.currentTimeMillis();
            Battle battle = new Battle(robot.createScreenCapture(NAME), robot);
            if (!battle.hybeeScout() && !battle.moglinFriends() && !battle.theSeeker() && !battle.rayfish()) {
                if (!battle.isRareTreasureChest() && !battle.ninjaCleric()) {
                    if (battle.getName().equals("O' Meany")) {
                        robot.mouseMove(O_MEANY_FIGHT_X, O_MEANY_FIGHT_Y);
                        click(robot);
                        robot.delay(5000);
                    }
                    NumberList elementList = readMonster(robot);
                    System.out.println(elementList);
                    MainPlus.testStop();
                    chooseWeapon(robot, elementList);
                    choosePet(robot, elementList);
                    if (v + d == 0) {
                        robot.delay(1000);
                        chooseDefense(robot);
                    }
                    if (battle.getName().equals("Drakel War Party")) {
                        attack(robot);
                        robot.delay(5000);
                        robot.mouseMove(DRAKEL_WAR_PARTY_X, DRAKEL_WAR_PARTY_Y);
                        click(robot);
                        robot.delay(2000);
                    }
                    robot.delay(2000);
                    for (int i = 0; i < spellNumber(robot, elementList) && !isOver(robot, battle); i++) {
                        chooseSpell(robot, elementList);
                        robot.delay(17000);
                    }
                }
                while (!isOver(robot, battle)) {
                    attack(robot);
                    robot.delay(5000);
                }
                if (isDead(robot)) {
                    long endTime = System.currentTimeMillis();
                    d++;
                    System.out.println("Battle " + (v + d) + ": Defeat  #" + d + " Time: " + ((endTime - startTime) / 60000) + " min");
                    death(robot);
                }
                else if (isAlive(robot, battle)) {
                    long endTime = System.currentTimeMillis();
                    v++;
                    System.out.println("Battle " + (v + d) + ": Victory #" + v + " Time: " + ((endTime - startTime) / 60000) + " min");
                    life(robot, battle);
                }
            }
        }
    }
    
    static void click(Robot robot) {
        robot.delay(100);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(100);
    }

    static void battleMonsters(Robot robot) {
        robot.mouseMove(BATTLE_X, BATTLE_Y);
        click(robot);
        robot.delay(6000);
    }

    static NumberList readMonster(Robot robot) {
        robot.delay(6000);
        robot.mouseMove(READ_X, READ_Y);
        robot.delay(200); //aq has delay
        BufferedImage elementBox = robot.createScreenCapture(ELEMENT_BOX);
        return AQOCR.recognize(elementBox, AQOCR.BLUEISH);  //record numbers from image into NumberList
    }
    
    static void chooseWeapon(Robot robot, NumberList elementList) {
        robot.delay(500);
        NumberList numList = (NumberList)elementList.clone();
        numList.remove(4);
        robot.mouseMove(ATTACK_X, WEAPONS_Y);
        click(robot);
        robot.mouseMove(WEAPON_X, FIRE_WEAPON_Y + WEAPON_SPACING * numList.indexOf(numList.max().intValue()));
        click(robot);
        robot.delay(1000);
    }
    
    static void chooseDefense(Robot robot) {
        robot.mouseMove(ATTACK_X, SHIELD_Y);
        click(robot);
        robot.mouseMove(WEAPON_X, CHOOSE_SHIELD_Y);
        click(robot);
        robot.delay(200);
        robot.mouseMove(ATTACK_X, ARMOR_Y);
        click(robot);
        robot.mouseMove(WEAPON_X, CHOOSE_ARMOR_Y);
        click(robot);
    }
    
    static void choosePet(Robot robot, NumberList elementList) {
        robot.mouseMove(ATTACK_X, PETS_Y);
        click(robot);
        robot.mouseMove(WEAPON_X, FIRE_PET_Y + PET_SPACING * elementList.indexOf(elementList.max().intValue()));
        click(robot);
        robot.delay(2000);
    }
    
    static void chooseSpell(Robot robot, NumberList elementList) {
        robot.mouseMove(ATTACK_X, SPELLS_Y);
        click(robot);
        robot.mouseMove(WEAPON_X, FIRE_SPELL_Y + PET_SPACING * elementList.indexOf(elementList.max().intValue()));
        click(robot);
        robot.delay(2000);
    }
    
    static void attack(Robot robot) {
        robot.mouseMove(ATTACK_X, ATTACK_Y);
        click(robot);
    }
    
    static boolean isAlive(Robot robot, Battle battle) {
        if (battle.getName().equals("Darksteel Guard") || battle.getName().equals("Dynablade Drakel")) {
            BufferedImage win = robot.createScreenCapture(DARKSTEEL_GUARD);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 250 || tan.getGreen() < 200 || tan.getGreen() > 210 || tan.getBlue() > 5)
                        return false;
                }
            }
            return true;
        }
        
        else if (battle.getName().equals("Uncle Krenos")) {
            BufferedImage win = robot.createScreenCapture(UNCLE_KRENOS);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 250 || tan.getGreen() < 250 || tan.getBlue() > 5)
                        return false;
                }
            }
            return true;
        }
        
        else if (battle.getName().equals("Grabbi")) {
            BufferedImage win = robot.createScreenCapture(GRABBI);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 250 || tan.getGreen() < 200 || tan.getGreen() > 210 || tan.getBlue() > 5)
                        return false;
                }
            }
            return true;
        }
        
        else if (battle.getName().equals("Savvy Merchant")) {
            System.exit(0);
            robot.mouseMove(SAVVY_MERCHANT_MORE_X, SAVVY_MERCHANT_MORE_Y);
            click(robot);
            robot.delay(1000);
            robot.mouseMove(SAVVY_MERCHANT_MORE_X, SAVVY_MERCHANT_HELP_Y);
            click(robot);
            robot.mouseMove(SAVVY_MERCHANT_ROLL_X, SAVVY_MERCHANT_ROLL_Y);
            click(robot);
            // chance
            robot.delay(2000);
            click(robot);
            // win screen
        }
        
        else if (battle.getName().equals("Lord Arrgthas")) {
            BufferedImage win = robot.createScreenCapture(LORD_ARRGTHAS);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 250 || tan.getGreen() < 200 || tan.getGreen() > 210 || tan.getBlue() > 5)
                        return false;
                }
            }
            return true;
        }
        
        else if (battle.getName().equals("The Seeker")) {
            robot.mouseMove(THE_SEEKER_X, THE_SEEKER_Y);
            click(robot);
            robot.delay(100);
            click(robot);
        // skip win screen, back to town
        }
        
        else if (battle.getName().equals("Animal Box")) {
            robot.mouseMove(ANIMAL_BOX_X, ANIMAL_BOX_Y);
            click(robot);
        }
        
        else if (battle.getName().equals("Rare Treasure Chest")) {
            robot.mouseMove(RARE_TREASURE_CHEST_OPEN_X, RARE_TREASURE_CHEST_OPEN_Y);
            click(robot);
            robot.delay(7000);
            robot.mouseMove(RARE_TREASURE_CHEST_DONE_X, RARE_TREASURE_CHEST_DONE_Y);
            click(robot);
        }
        
        else if (battle.getName().equals("Ninja Cleric")) {
            robot.mouseMove(NINJA_CLERIC_X, NINJA_CLERIC_Y);
            click(robot);
            robot.delay(1000);
        }
        
        else if (battle.getName().equals("Gnuvain the Changer")) {
            robot.mouseMove(GNUVAIN_X, GNUVAIN_Y);
            click(robot);
        }
        
        else if (battle.getName().equals("O' Meany")) {
            System.exit(0);
            robot.mouseMove(O_MEANY_FIGHT_X, O_MEANY_FIGHT_Y);
            click(robot);
            robot.delay(5000);
        }
        
//        else {
            BufferedImage win = robot.createScreenCapture(FTW);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 230 || tan.getRed() > 240 || tan.getGreen() < 200 || tan.getGreen() > 210 || tan.getBlue() < 160 || tan.getBlue() > 170)
                        return false;
                }
            }
            return true;
//        }
    }
    
    
    
    static boolean isDead(Robot robot) {
        BufferedImage lose = robot.createScreenCapture(FTL);
        for (int x = 0; x < lose.getWidth(); x++) {
            for (int y = 0; y < lose.getHeight(); y++) {
                Color red = new Color(lose.getRGB(x, y));
                if (red.getRed() < 210 || red.getRed() > 220 || red.getGreen() > 5 || red.getBlue() > 5)
                    return false;
            }
        }
        return true;
    }
    
    static boolean isOver(Robot robot,Battle battle) {
        return isDead(robot) || isAlive(robot, battle);
    }
    
    static int spellNumber(Robot robot, NumberList elementList) {
        return MP / SPELL_COST_ARRAY[elementList.indexOf(elementList.max().intValue())];
    }
    
    static void death(Robot robot) {
        robot.mouseMove(ATTACK_X, DEATH_NEXT_Y);
        robot.delay(1000);
        click(robot);
        robot.delay(5000);
        robot.mouseMove(DEATH_X, DEATH_Y);
        click(robot);
        click(robot);
    }
    
    static void life(Robot robot, Battle battle) {
        battle.darksteelGuard();
        battle.lordArrgthas();
        battle.grabbi();
        battle.uncleKrenos();
        robot.mouseMove(ATTACK_X, NEXT_Y);
        robot.delay(1500);
        click(robot);    
        robot.delay(1000);
        if (areZTokens(robot))
            click(robot);
        robot.delay(2000);
        if (isLevelUp(robot)) {
            robot.mouseMove(LEVEL_UP_NEXT_X, LEVEL_UP_NEXT_Y);
            click(robot);
        }
        robot.mouseMove(TWILLY_X, TWILLY_Y);
        click(robot);
        robot.delay(7500);
        robot.mouseMove(TWILLY_BACK_X, TWILLY_BACK_Y);
        click(robot);
    }
    
    public static boolean areZTokens(Robot robot) {
        BufferedImage win = robot.createScreenCapture(Z_TOKENS);
        for (int x = 0; x < win.getWidth(); x++) {
            for (int y = 0; y < win.getHeight(); y++) {
                Color tan = new Color(win.getRGB(x, y));
                if (tan.getRed() < 230 || tan.getRed() > 240 || tan.getGreen() < 200 || tan.getGreen() > 210 || tan.getBlue() < 160 || tan.getBlue() > 170)
                    return false;
            }
        }
        return true;
    }
    
    public static boolean isLevelUp(Robot robot) {
        BufferedImage win = robot.createScreenCapture(LEVEL_UP);
            for (int x = 0; x < win.getWidth(); x++) {
                for (int y = 0; y < win.getHeight(); y++) {
                    Color tan = new Color(win.getRGB(x, y));
                    if (tan.getRed() < 245 || tan.getGreen() < 245 || tan.getBlue() < 95 || tan.getBlue() > 110)
                        return false;
                }
            }
            return true;
    }
}