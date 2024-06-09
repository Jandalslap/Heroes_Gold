package heroesGold;

import static java.lang.System.*;

public class DiceRollSimulation {	

    // Shared lock objects for synchronization (From previous sim with the dice rolls).
    private static final Object heroLock = new Object();

    public static DiceResult simulateDiceRolls(Hero chosenHero, Card drawnCard) {
        int numFrames = 20; // Total number of frames
        int frameDelay = 50; // Delay in milliseconds between frames
       
        // Create thread.
        Thread heroThread = new Thread(() -> simulateDiceRoll(numFrames, frameDelay, chosenHero.getName(), heroLock));
        
        // Start thread.
        heroThread.start();
        
        // Create instances for Hero and Villain DiceResult
        DiceResult heroDiceRoll = new DiceResult(0, 0, 0, 0);
        DiceResult villainDiceRoll = new DiceResult(0, 0, 0, 0);

        // At this point, both dice roll animations are running concurrently
        for (int frame = 0; frame < numFrames; frame++) {
            synchronized (heroLock) {          
                clearScreen(50);
                drawDice(frame, chosenHero.getName());                   
            }

            try {
                Thread.sleep(frameDelay); // Delay to control animation speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for thread to finish.
        try {
            heroThread.join();
         
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the final dice results
        heroDiceRoll.fight();
        villainDiceRoll.fight();

        // Print the final dice patterns
        clearScreen(50);     
        printDicePattern(heroDiceRoll, "Hero", chosenHero, null);       
        
        try {
            Thread.sleep(1000); // 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println();     
        printDicePattern(villainDiceRoll, "Villain", null, drawnCard);
        out.println();
        
        try {
            Thread.sleep(2000); // 2 seconds (2000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return new DiceResult(heroDiceRoll.getHeroDice1(), heroDiceRoll.getHeroDice2(), villainDiceRoll.getVillainDice1(), villainDiceRoll.getVillainDice2());
    }


// Method to simulate dice roll animation.
    private static void simulateDiceRoll(int numFrames, int frameDelay, String character, Object lock) {
        for (int frame = 0; frame < numFrames; frame++) {
            synchronized (lock) {
                clearScreen(50);
                drawDice(frame, character);
            }

            try {
                Thread.sleep(frameDelay); // Delay to control animation speed.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
 // Clear viewable screen method after each turn.
    public static void clearScreen(int numLines) {
        for (int i = 0; i < numLines; i++) {
            out.println(); // Print an empty line
        }
    }

    // Draw the dice based on the current frame.
    public static void drawDice(int frame, String character) {
        // Define the frames for the rolling animation.
        String[] diceFrames = {
            " _____   _____ ",
            "|*    | |*    |",
            "|  *  | |  *  |",
            "|____*| |____*|",
            " _____   _____ ",
            "|  *  | |  *  |",
            "|  *  | |  *  |",
            "|__*__| |__*__|",
            " _____   _____ ",
            "|    *| |    *|",
            "|  *  | |  *  |",
            "|*____| |*____|",
            " _____   _____ ",
            "|     | |     |",
            "|* * *| |* * *|",
            "|_____| |_____|"
        };

        // Calculate the frame to display based on the current frame.
        int currentFrameIndex = frame % (diceFrames.length / 4) * 4;

        // Display the current frame.
        out.println(character + ":");
        // Display the current frame.
        for (int i = 0; i < 4; i++) {
            out.println(diceFrames[currentFrameIndex + i]);
        }
        
    }
    
    // Method to print the dice pattern.
    public static void printDicePattern(DiceResult result, String character, Hero chosenHero, Card drawnCard) {
        
        
    	int heroDice1 = result.getHeroDice1();
        int heroDice2 = result.getHeroDice2();
        int villainDice1 = result.getVillainDice1();
        int villainDice2 = result.getVillainDice2();
        // For Hero
        if (character.equals("Hero")) {
        	out.printf("%s: %s", chosenHero.getName(), result.getHeroDice1() + result.getHeroDice2());
        	out.println();
	        if (heroDice1 == 1 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |_____|\n");
	        } else if (heroDice1 == 1 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |____*|\n");
	        } else if (heroDice1 == 1 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |____*|\n");
	        } else if (heroDice1 == 1 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |*___*|\n");
	        } else if (heroDice1 == 1 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |*___*|\n");
	        } else if (heroDice1 == 1 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |*_*_*|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |_____|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (heroDice1 == 2 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |*_*_*|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |_____|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (heroDice1 == 3 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |*_*_*|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |_____|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (heroDice1 == 4 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |*_*_*|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |_____|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (heroDice1 == 5 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |*_*_*|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |_____|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |____*|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |____*|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |*___*|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |*___*|\n");
	        } else if (heroDice1 == 6 && heroDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |*_*_*|\n");
	        } // For Villain
        } else if (character.equals("Villain")) {
        	out.printf("%s: %s", drawnCard.getVillain(), result.getVillainDice1() + result.getVillainDice2());
        	out.println();
        	if (villainDice1 == 1 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |_____|\n");
	        } else if (villainDice1 == 1 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |____*|\n");
	        } else if (villainDice1 == 1 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |____*|\n");
	        } else if (villainDice1 == 1 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |*___*|\n");
	        } else if (villainDice1 == 1 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|_____| |*___*|\n");
	        } else if (villainDice1 == 1 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|     | |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|_____| |*_*_*|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |_____|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (villainDice1 == 2 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|____*| |*_*_*|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |_____|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |____*|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|____*| |*___*|\n");
	        } else if (villainDice1 == 3 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*    | |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|____*| |*_*_*|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |_____|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (villainDice1 == 4 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*___*| |*_*_*|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |     |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |_____|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*    |\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |____*|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |*   *|\n");
	            out.printf("|  *  | |  *  |\n");
	            out.printf("|*___*| |*___*|\n");
	        } else if (villainDice1 == 5 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|*   *| |* * *|\n");
	            out.printf("|  *  | |     |\n");
	            out.printf("|*___*| |*_*_*|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 1) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |     |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |_____|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 2) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*    |\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |____*|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 3) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*    |\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |____*|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 4) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*   *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |*___*|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 5) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |*   *|\n");
	            out.printf("|     | |  *  |\n");
	            out.printf("|*_*_*| |*___*|\n");
	        } else if (villainDice1 == 6 && villainDice2 == 6) {
	            out.printf(" _____   _____ \n");
	            out.printf("|* * *| |* * *|\n");
	            out.printf("|     | |     |\n");
	            out.printf("|*_*_*| |*_*_*|\n");
	        }
        }       
        return;
    }

}
