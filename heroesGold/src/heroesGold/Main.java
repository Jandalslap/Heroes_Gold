/**
 * Created on 16 September 2023
 * @author: Stewart Deane Austin 22532212
 * Version: 5.0
 */

/**
 * Description: This is a role playing fantasy limited turn card game.
 */

package heroesGold;

import static java.lang.System.*;
import java.util.*;
import java.io.PrintStream;

public class Main {
	
	static Scanner input = new Scanner(in);

	public static void main(String[] args) {
		
		boolean playAgain = true;	
// Scoreboard
		List<String> scoreBoard = new ArrayList<>();
		
// Start Game
		displayGameDescription();
		
// Player Name
		out.print("Please enter your name: ");	
		String playerName = input.nextLine();
        out.println();      
        out.println();
        
        while (playAgain) {
        	int turnCount = 0;
        	// Zero out turn count from previous game if playing again.
        	turnCount = 0;
// Character List        	
        	displayCharacterList();

// Character Selection		
        	List<String> spells = new LinkedList<>();
        	List<String> items = new LinkedList<>();        
            int characterNumber = selectCharacter(input, out);
            Hero chosenHero = null;
            
            clearScreen(40);           
            
            chosenHero = createChosenHero(characterNumber, playerName, chosenHero, items, spells);

	    	// Start the running total of gold from character card.
	        int totalGold = chosenHero.getGold();
// First card		        
	        out.print("Press enter to play and draw your first card.");
	        String userInput = input.nextLine();
	        out.println();
// Secret Change Ability	        
	        if (userInput.equals("*")) {
	        	out.println("Would you like to change your characters ability? (y/n)");
	        	userInput = input.nextLine();
	        	if (userInput.equalsIgnoreCase("y")) {	        			        		
	        		secretChangeAbility(input, chosenHero, playerName, items, spells);
	        	}
	        	else {
	        		secretNoChangeAbility(playerName, chosenHero, items, spells);
	            }
	        } 
	        out.println();        
	        clearScreen(40);
	        
	        boolean hasWon = false;
	        // Loop to check Chosen Hero has life left.
	        while (chosenHero != null && chosenHero.getLife() > 0) {	        	
// Turn Count	        	
	        	turnCount += 1;
	        	Card drawnCard = null;	 
// Minimum spell 	        	
	        	if(chosenHero.isMagicalCharacter() == true) {
	            	if(spells.size() == 0) {
	            		addRandomSpells(spells, 1); // Magical characters always have a minimum of 1 spell. 
	            		out.printf("Your %s has received a spell! %s", chosenHero.getName(), spells);
	            		out.println();
	            		out.println();
	            	}
	            }
	        	
// Minimum item	        	
	        	if(chosenHero.isMagicalCharacter() == false) {	        		
	            	if(items.size() == 0) {
	            		addRandomItems(items, 1); // Non-Magical characters always have a minimum of 1 item. 
	            		out.printf("Your %s has received an item! %s", chosenHero.getName(), items);
	            		out.println();
	            		out.println();
	            		addItemBonus(chosenHero, items);
	            	}						
	            }	
	        	
// Drawn Card	            
	        	drawnCard = playLevelCard(turnCount, playerName, chosenHero, items, spells, drawnCard);

// Process Card	
	        	processDrawnCard(drawnCard, chosenHero, items, spells, playerName, totalGold);	        	
    			// Exit if zero life left. Can't put break in method.
    			if(chosenHero.getLife() == 0) {
    				out.println("You are out of life.");
    				break;
    			}
// Final Fight	            
	            if(turnCount == 60) {
            		// Message for Winner. Can't put break in method.
            		out.println("*************************");
                    out.println("Congratulations! You Win!");
            		out.println("       Well Done!");
            		out.println("*************************");
            		out.println();
            		hasWon = true;
                    break;
	            }   			
// End Turn
	            out.println("*".repeat(46));
	            out.print("Press enter to draw another card or q to quit: ");
	            out.println();
	            out.println();
// Quit Game
	            userInput = input.nextLine();
	            if (userInput.equalsIgnoreCase("q")) {
	                break;
	            }else {           	
	            	clearScreen(40);
	            }
	                    
	        }
// Game Over	    	
	    	out.println();
	        out.printf("Game Over! You survived %s turns.", turnCount);
	        out.println();
	        out.println();
// Winner Flag	        
	        String winnerLoser = "Lost";
	        while(hasWon) {
	        	winnerLoser = "Won";
	        	break;
	        } 
	        String score = playerName + ": " + chosenHero.getName() + " " + chosenHero.getAbility() + " " + winnerLoser + " Turns: " + turnCount + " Strength: " + chosenHero.getStrength() + " Skill: " + chosenHero.getSkill() + " Gold: " + chosenHero.getGold() + " Life: " + chosenHero.getLife();
	        scoreBoard.add(score);
	
// Display the scores from the list
	        out.println("Scores:");
	        for (String playerScore : scoreBoard) {
	            out.println(playerScore);
	        }
// Play Again        
	        out.println();
	        out.println();
	        boolean validInput = false;
	        while (!validInput) {
	            out.print("Would you like to play again? (y/n): ");
	            out.println();
	            String playAgainInput = input.nextLine();
	            if (playAgainInput.equalsIgnoreCase("y")) {
	                validInput = true;
	                playAgain = true;
	            } else if (playAgainInput.equalsIgnoreCase("n")) {
	                validInput = true;
	                playAgain = false;
	            } else {
	                out.println("Invalid input. Please enter 'y' or 'n'.");
	            }
	        }
        }
        out.println();
        out.println("Thanks for playing!");	
        
    }
/*	*******
 *  Methods
 *  *******
 */
	
// Create Hero
		// Method to create Chosen Hero object for player.
		public static Hero createChosenHero(int characterNumber, String playerName, Hero chosenHero, List<String> items, List<String> spells) {
			// Character objects get there random starting items and spells.
	        if (characterNumber == 1) {
	        	// (<"name">, <strength>, <skill>, <"items">, <gold>, <life>, <magicalCharacter>, <"ability">, <hasBag>)
	        	//Swordsman Object
	        	chosenHero = new Hero("Swordsman", 8, 4, "null", 100, 5, false, "Fortitude", false);
	            out.printf("Congratulations %s! You have chosen %s.", playerName, chosenHero.getName());
	            out.println();
	            out.println();
	            addRandomItems(items, 1); // Add 1 random item for this character.
	            out.printf("Your %s has received an item! %s", chosenHero.getName(), items);
	    		out.println();
	            addItemBonus(chosenHero, items);
	        } else if (characterNumber == 2) {
	        	//Elf Object           	
	            chosenHero = new Hero("Elf", 5, 6, "null", 100, 5, false, "Mastery", false);
	            out.printf("Congratulations %s! You have chosen %s.", playerName, chosenHero.getName());
	            out.println();
	            out.println();
	            addRandomItems(items, 1); // Add 1 random item for this character.
	            out.printf("Your %s has received an item! %s", chosenHero.getName(), items);
	    		out.println();       		
	            addItemBonus(chosenHero, items);
	            addRandomSpells(spells, 1); // Add 1 random spell for this character.
	            out.printf("Your %s has received a spell! %s", chosenHero.getName(), spells);
	    		out.println();
	        } else if (characterNumber == 3) {
	        	//Dwarf Object            	
	            chosenHero = new Hero("Dwarf", 6, 5, "null", 100, 5,false, "Prosperity", false);
	            out.printf("Congratulations %s! You have chosen %s.", playerName, chosenHero.getName());
	            out.println();
	            out.println();
	            addRandomItems(items, 1); // Add 1 random item for this character.
	            out.printf("Your %s has received an item! %s", chosenHero.getName(), items);
	    		out.println();       		
	            addItemBonus(chosenHero, items);
	            addRandomSpells(spells, 1); // Add 1 random spell for this character.
	            out.printf("Your %s has received a spell! %s", chosenHero.getName(), spells);
	    		out.println();
	        } else if (characterNumber == 4) {
	        	//Sorceress Object            	
	            chosenHero = new Hero("Sorceress", 3, 7, "null", 0, 5, true, "Spellguard", false);
	            out.printf("Congratulations %s! You have chosen %s.", playerName, chosenHero.getName());
	            out.println();
	            out.println();
	            addRandomSpells(spells, 2); // Add 2 random spells for magical character.
	            out.printf("Your %s has received spells! %s", chosenHero.getName(), spells);
	    		out.println();
	        } else if (characterNumber == 5) {
	        	//Wizard Object 
	            chosenHero = new Hero("Wizard", 3, 7, "null", 0, 5, true, "Itemshield", false);
	            out.printf("Congratulations %s! You have chosen %s.", playerName, chosenHero.getName());
	            out.println();
	            out.println();
	            addRandomSpells(spells, 2); // Add 2 random spells for magical character.
	            out.printf("Your %s has received spells! %s", chosenHero.getName(), spells);
	    		out.println();               
	        }
	
	        out.println();
	        displayPlayerCard(playerName, chosenHero, items, spells);
	        out.println("*".repeat(45));
	        return chosenHero;
		}

// Secret Ability No Change	
		//Method to process no change in ability.
		public static void secretNoChangeAbility(String playerName, Hero chosenHero, List<String> items, List<String> spells) {
			out.printf("Your %s's ability %s has not changed.", chosenHero.getName(), chosenHero.getAbility());
	    	out.println();
	    	out.println();
	        displayPlayerCard(playerName, chosenHero, items, spells);
	        out.println("*********************************************");
	        out.print("Press enter to play and draw your first card.");
	        input.nextLine();
	        out.println();
		}
// Secret Ability Change
		// Method to process change ability.
		public static void secretChangeAbility(Scanner input, Hero chosenHero, String playerName, List<String> items, List<String> spells) {
			int newAbility = 0;
        			        		
        		out.println("1. Fortitude   -   Cannot lose strength.");
        		out.println("2. Mastery     -   Cannot lose skill.");
        		out.println("3. Prosperity  -   Cannot lose gold.");
        		out.println("4. Spellguard  -   Cannot lose spells.");
        		out.println("5. Itemshield  -   Cannot lose items (except bag).");
        		out.println();	        		
        		
        		while (true) {
        			out.println("Please select your new ability or 0 to cancel:");
	    	        // Check if the input is an integer.
	    	        if (input.hasNextInt()) {
	    	            int choice = input.nextInt();
	    	            
	    	            if (choice == 0) {
	    	            	newAbility = choice;    	    	            	
                        	break;
                        }
	    	            // Check if the choice is valid.
	    	            if (choice >= 1 && choice <= 5) {
	    	            	newAbility = choice;
	    	                break; // Exit the loop if a valid choice is provided
	    	            } else {
	    	                out.println("Invalid choice. Please enter a valid number.");
	    	            }
	    	        } else {
	    	            out.println("Invalid input. Please enter a valid number.");
	    	            input.next(); // Consume invalid input to avoid an infinite loop.
	    	        }
	    	    }	        			        		        		
        	
	        	out.println();
        		// Set new Ability.
        		if (newAbility == 1) {
                	chosenHero.setAbility("Fortitude");
                	changeAbility(playerName, chosenHero, items, spells);
                } else if (newAbility == 2) {
                	chosenHero.setAbility("Mastery");
                	changeAbility(playerName, chosenHero, items, spells);
                } else if (newAbility == 3) {
                	chosenHero.setAbility("Prosperity");
                	changeAbility(playerName, chosenHero, items, spells);
                } else if (newAbility == 4) {
                	chosenHero.setAbility("Spellguard");   
                	changeAbility(playerName, chosenHero, items, spells);
                } else if (newAbility == 5) {
                	chosenHero.setAbility("Itemshield");  
                	changeAbility(playerName, chosenHero, items, spells);
                } else if (newAbility == 0) {
                	out.printf("Your %s's ability %s has not changed.", chosenHero.getName(), chosenHero.getAbility());
                	out.println();
                	out.println();
                	input.nextLine();
                    displayPlayerCard(playerName, chosenHero, items, spells);
                    out.println("*********************************************");
                    out.print("Press enter to play and draw your first card.");
        	        input.nextLine();
        	        out.println();
                } 
		}
	
// Change Ability
		// Display change character ability message.
		public static void changeAbility(String playerName, Hero chosenHero, List<String> items, List<String> spells) {
			out.println();
			out.printf("Congratulations %s! Your %s's new ability is %s.", playerName, chosenHero.getName(), chosenHero.getAbility());
            out.println();
            out.println();
            displayPlayerCard(playerName, chosenHero, items, spells);
            out.println("*********************************************");
            out.print("Press enter to play and draw your first card.");
	        input.nextLine();
	        input.nextLine();
	        out.println();
	        return;
		}

// Random Spell
		// Method to populate spells list with Random Spells.
		public static void addRandomSpells(List<String> spells, int numberOfSpells) {
	        // Create Level 1 spell descriptions in an array and pass back to List in main.
	        List<String> spellDescriptions = new ArrayList<>();	        	       
	        spellDescriptions.add("Weaken - Strength -2");
	        spellDescriptions.add("Weaken - Strength -2");
	        spellDescriptions.add("Drain - Skill -2");
	        spellDescriptions.add("Drain - Skill -2");
	        spellDescriptions.add("Heal - Life +2");
	        spellDescriptions.add("Energise - Skill +2");
	        spellDescriptions.add("Power - Strength +2");
	        spellDescriptions.add("Death - Life -1");
	        Random random = new Random();
	
	        for (int i = 0; i < numberOfSpells; i++) {
	            int randomIndex = random.nextInt(spellDescriptions.size());
	            // Generate random spell description.
	            String randomSpellDescription = spellDescriptions.get(randomIndex);
	            spells.add(randomSpellDescription);
	        }
	    }
		
// Random Item
		// Method to populate items list with Random Items at start of turn for standard characters.
		public static void addRandomItems(List<String> items, int numberOfItems) {
	        // Create Level 1 spell descriptions in an array and pass back to List in main.
	        List<String> itemDescriptions = new ArrayList<>();	        	       
	        itemDescriptions.add("Broadsword - Strength +2");
	        itemDescriptions.add("Broadsword - Strength +2");
	        itemDescriptions.add("Broadsword - Strength +2");
	        itemDescriptions.add("Shortsword - Strength +1");
	        itemDescriptions.add("Axe - Strength +2");
	        itemDescriptions.add("Mace - Strength +2");
	        itemDescriptions.add("Bow - Strength +1");
	        itemDescriptions.add("Helmet - Strength +1");
	        itemDescriptions.add("Chestplate - Strength +2");
	        itemDescriptions.add("Ring - Skill +1");
	        itemDescriptions.add("Wand - Skill +2");
	        itemDescriptions.add("Staff - Skill +2");
	        itemDescriptions.add("Staff - Skill +2");
	        itemDescriptions.add("Staff - Skill +2");
	        itemDescriptions.add("Crown - Skill +2");
	        itemDescriptions.add("Cloak - Skill +1");
	        itemDescriptions.add("Artifact - Skill +1");
	        itemDescriptions.add("Orb - Skill +2");			        
	        itemDescriptions.add("Magicsword - Strength +3");
	        itemDescriptions.add("Longsword - Strength +2");
	        itemDescriptions.add("Battleaxe - Strength +3");
	        itemDescriptions.add("Pike - Strength +2");
	        itemDescriptions.add("Crossbow - Strength +2");
	        itemDescriptions.add("Chainmail-Armour - Strength +3");			        
	        itemDescriptions.add("Amulet - Skill +2");
	        itemDescriptions.add("Crystal-Ball - Skill +2");
	        itemDescriptions.add("Locket - Skill +2");
	        itemDescriptions.add("Ethereal-Bow - Skill +3");
	        itemDescriptions.add("Whispering-Sword - Skill +3");
	        itemDescriptions.add("Enchanted-Armour - Skill +3");
	        Random random = new Random();
	
	        for (int i = 0; i < numberOfItems; i++) {
	            int randomIndex = random.nextInt(itemDescriptions.size());
	            // Generate random spell description.
	            String randomItemDescription = itemDescriptions.get(randomIndex);
	            items.add(randomItemDescription);
	        }			        
	    }
		
// Item Bonus		
		// Method to add item bonus when minimum item given. Only works for one item at index 0.
		public static void addItemBonus(Hero chosenHero, List<String> items) {
			String freeItem = getItemByIndex(items, 1 - 1);
    		String[] parts = freeItem.split("\\s+"); // Split by whitespace.
    		if (parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
				// Strength item with a bonus. 
				int spellBonus = Integer.parseInt(parts[3]);
				out.printf("You have gained Strength bonus of %d%n", spellBonus);
				out.println();
				int playerStrength = chosenHero.getStrength() + spellBonus;
				chosenHero.setStrength(playerStrength);
			} else if (parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
				// Skill item with a bonus.
				int spellBonus = Integer.parseInt(parts[3]);
				out.printf("You have gained Skill bonus of %d%n", spellBonus);
				out.println();
				int playerSkill = chosenHero.getSkill() + spellBonus;
				chosenHero.setSkill(playerSkill);
			}
		}
		
// Get Card Level 1
        public static Card getCard() {
        	// Create Level 1 card objects in an array.
            ArrayList<Card> cards = new ArrayList<>();
            // ("<cardType>", "<villain>", <strength>, <skill>, <life>, <gold>, "<item*>", <price>, <bonus>)
            // * item - must only contain 4 elements in the format with no spaces within each element <item-name> - <Skill or Strength> +<bonus>
            cards.add(new Card("Strength Fight", "Orc", 4, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Goblin", 3, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Troll", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Giant", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Thief", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Wraith", 0, 5, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Golem", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Witch", 0, 3, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Orc", 4, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Goblin", 3, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Troll", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Giant", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Thief", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Wraith", 0, 5, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Golem", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Witch", 0, 3, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Orc", 4, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Goblin", 3, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Troll", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Giant", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Thief", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Wraith", 0, 5, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Golem", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Witch", 0, 3, 1, 50, null, 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 100, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 100, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 50, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 50, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -100, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -50, "Gold", 0, 0));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Broadsword - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Broadsword - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Shortsword - Strength +1", 100, 1));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Axe - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Mace - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Bow - Strength +1", 100, 1));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Helmet - Strength +1", 100, 1));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Chestplate - Strength +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Ring - Skill +1", 100, 1));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Wand - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Staff - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Staff - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Crown - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Cloak - Skill +1", 100, 1));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Artifact - Skill +1", 150, 1));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Orb - Skill +2", 200, 2));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -1, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -1, 0, "Life", 0, 0));
            cards.add(new Card("Gain Strength", null, 0, 0, 0, 0, "Strength", 0, 1));
            cards.add(new Card("Gain Skill", null, 0, 0, 0, 0, "Skill", 0, 1));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose Spell", null, 0, 0, 0, 0, "Lose Spell", 0, 0));
            cards.add(new Card("Lose Spell", null, 0, 0, 0, 0, "Lose Spell", 0, 0));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Power - Strength +2", 200, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Energise - Skill +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Heal - Life +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Death - Life -1", 200, 1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));            
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));	
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));
            cards.add(new Card("Lose Bag", null, 0, 0, 0, 0, "Lose Bag", 0, 0));

            Random random = new Random();

            int randomIndex = random.nextInt(cards.size());
            // Generate random card.
            Card randomCard = cards.get(randomIndex);
            // Display card method.
            displayCard(randomCard);
            // Return card type.
            return randomCard;
        }
        
// Get Card Level 2    
        public static Card getCard2() {

            ArrayList<Card> cards = new ArrayList<>();
            // ("<cardType>", "<villain>", <strength>, <skill>, <life>, <gold>, "<item*>", <price>, <bonus>)
            // * item - must only contain 4 elements in the format with no spaces within each element <item-name> - <Skill or Strength> +<bonus>
            cards.add(new Card("Strength Fight", "Orc", 4, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Goblin", 3, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Troll", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Giant", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Thief", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Wraith", 0, 5, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Golem", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Witch", 0, 3, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Warlord", 7, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Beast", 8, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Hunter", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Cannibal", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Druid", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Cultist", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Dark Elf", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Assassin", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Warlord", 7, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Beast", 8, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Hunter", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Cannibal", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Druid", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Cultist", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Dark Elf", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Assassin", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 200, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 200, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 100, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 100, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -200, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -100, "Gold", 0, 0));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Broadsword - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Magicsword - Strength +3", 250, 3));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Longsword - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Battleaxe - Strength +3", 250, 3));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Pike - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Crossbow - Strength +2", 150, 2));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Chainmail-Armour - Strength +3", 250, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Staff - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Amulet - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Crystal-Ball - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Locket - Skill +2", 150, 2));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Ethereal-Bow - Skill +3", 250, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Whispering-Sword - Skill +3", 250, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Enchanted-Armour - Skill +3", 250, 3));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -1, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -1, 0, "Life", 0, 0));
            cards.add(new Card("Gain Strength", null, 0, 0, 0, 0, "Strength", 0, 1));
            cards.add(new Card("Gain Skill", null, 0, 0, 0, 0, "Skill", 0, 1));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose All Items", null, 0, 0, 0, 0, "Lose All Items", 0, 0));
            cards.add(new Card("Lose All Spells", null, 0, 0, 0, 0, "Lose All Spells", 0, 0));
            cards.add(new Card("Lose Spell", null, 0, 0, 0, 0, "Lose Spell", 0, 0));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Power - Strength +2", 200, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Energise - Skill +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Heal - Life +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Death - Life -1", 200, 1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));	
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));	
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));	
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));
            cards.add(new Card("Lose Bag", null, 0, 0, 0, 0, "Lose Bag", 0, 0));
            
            

            Random random = new Random();

            int randomIndex = random.nextInt(cards.size());
            // Generate random card.
            Card randomCard = cards.get(randomIndex);
            // Display card method.
            displayCard(randomCard);
            // Return card type.
            return randomCard;
        }
        
// Get Card Level 3
        public static Card getCard3() {

            ArrayList<Card> cards = new ArrayList<>();
            // ("<cardType>", "<villain>", <strength>, <skill>, <life>, <gold>, "<item*>", <price>, <bonus>)
            // * item - must only contain 4 elements in the format with no spaces within each element <item-name> - <Skill or Strength> +<bonus>
            cards.add(new Card("Strength Fight", "Orc", 4, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Goblin", 3, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Troll", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Giant", 5, 0, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Thief", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Wraith", 0, 5, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Golem", 0, 4, 1, 50, null, 0, 0));
            cards.add(new Card("Skill Fight", "Witch", 0, 3, 1, 50, null, 0, 0));
            cards.add(new Card("Strength Fight", "Warlord", 7, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Beast", 8, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Hunter", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Cannibal", 6, 0, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Druid", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Cultist", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Dark Elf", 0, 6, 1, 100, null, 0, 0));
            cards.add(new Card("Skill Fight", "Assassin", 0, 7, 1, 100, null, 0, 0));
            cards.add(new Card("Strength Fight", "Overlord", 11, 0, 1, 200, null, 0, 0));
            cards.add(new Card("Strength Fight", "Dragon", 10, 0, 1, 200, null, 0, 0));
            cards.add(new Card("Strength Fight", "Cursed Hero", 8, 0, 1, 200, null, 0, 0));
            cards.add(new Card("Strength Fight", "Centaur", 9, 0, 1, 200, null, 0, 0));
            cards.add(new Card("Skill Fight", "Demigod", 0, 11, 1, 200, null, 0, 0));
            cards.add(new Card("Skill Fight", "Warlock", 0, 10, 1, 200, null, 0, 0));
            cards.add(new Card("Skill Fight", "Mastermind", 0, 8, 1, 200, null, 0, 0));
            cards.add(new Card("Skill Fight", "Conjurer", 0, 9, 1, 200, null, 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 300, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 300, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 150, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, 150, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -300, "Gold", 0, 0));
            cards.add(new Card("Treasure", null, 0, 0, 0, -150, "Gold", 0, 0));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Magic-Sword - Strength +3", 250, 3));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Battle-Axe - Strength +3", 250, 3));
            cards.add(new Card("Strength Item", null, 0, 0, 0, 0, "Chainmail-Armour - Strength +3", 250, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Ethereal-Bow - Skill +3", 350, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Whispering-Sword - Skill +3", 250, 3));
            cards.add(new Card("Skill Item", null, 0, 0, 0, 0, "Enchanted-Armour - Skill +3", 250, 3));
            cards.add(new Card("Life Item", null, 0, 0, 3, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 3, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, 3, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -2, 0, "Life", 0, 0));
            cards.add(new Card("Life Item", null, 0, 0, -2, 0, "Life", 0, 0));
            cards.add(new Card("Gain Strength", null, 0, 0, 0, 0, "Strength", 0, 1));
            cards.add(new Card("Gain Skill", null, 0, 0, 0, 0, "Skill", 0, 1));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose Item", null, 0, 0, 0, 0, "Lose Item", 0, 0));
            cards.add(new Card("Lose All Items", null, 0, 0, 0, 0, "Lose All Items", 0, 0));
            cards.add(new Card("Lose All Spells", null, 0, 0, 0, 0, "Lose All Spells", 0, 0));
            cards.add(new Card("Lose Spell", null, 0, 0, 0, 0, "Lose Spell", 0, 0));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Weaken - Strength -2", 100, 2));
            cards.add(new Card("Strength Spell", null, 0, 0, 0, 0, "Power - Strength +2", 200, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Drain - Skill -2", 100, 2));
            cards.add(new Card("Skill Spell", null, 0, 0, 0, 0, "Energise - Skill +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Heal - Life +2", 200, 2));
            cards.add(new Card("Life Spell", null, 0, 0, 0, 0, "Death - Life -1", 200, 1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Skill", null, 0, 0, 0, 0, "Lose Skill", 0, -1));	
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));
            cards.add(new Card("Lose Strength", null, 0, 0, 0, 0, "Lose Strength", 0, -1));
            cards.add(new Card("Bag Item", null, 0, 0, 0, 0, "Bag Item", 50, 0));	
            cards.add(new Card("Lose Bag", null, 0, 0, 0, 0, "Lose Bag", 0, 0));

            Random random = new Random();

            int randomIndex = random.nextInt(cards.size());
            // Generate random card.
            Card randomCard = cards.get(randomIndex);
            // Display card method.
            displayCard(randomCard);
            // Return card type.
            return randomCard;
        }
        
// Get Card Level 4
        public static Card getCard4(Hero chosenHero) {
        	// For game play pick highest Chosen Hero Fight Type points.   
        	// Strength Boss Fight
        	if(chosenHero.getStrength() > chosenHero.getSkill()) {
	            ArrayList<Card> cards = new ArrayList<>();
	            // ("<cardType>", "<villain>", <strength>, <skill>, <life>, <gold>, "<item*>", <price>, <bonus>)
	            // * item - must only contain 4 elements in the format with no spaces within each element <item-name> - <Skill or Strength> +<bonus>
	            cards.add(new Card("Strength Boss", "Betrayer", 15, 0, 3, 500, null, 0, 0));
	
	            Random random = new Random();
	
	            int randomIndex = random.nextInt(cards.size());
	
	            Card randomCard = cards.get(randomIndex);
	            // Display card method.
	            displayCard(randomCard);
	            // Return card type.
	            return randomCard;
	        
	        // Skill Boss Fight    
	        }else if(chosenHero.getSkill() > chosenHero.getStrength()) {
	            ArrayList<Card> cards = new ArrayList<>();
	            // ("<cardType>", "<villain>", "<strength>", "<skill>", "<life>",  "<gold>", "<item>", "<price>", "<bonus>")
	            cards.add(new Card("Skill Boss", "Sorcerer", 0, 15, 3, 500, null, 0, 0));
	
	            Random random = new Random();
	
	            int randomIndex = random.nextInt(cards.size());
	
	            Card randomCard = cards.get(randomIndex);
	            // Display card method.
	            displayCard(randomCard);
	            // Return card type.
	            return randomCard;
	        // Random Fight Pick	            
	        }else if(chosenHero.getStrength() == chosenHero.getSkill()) {
	            ArrayList<Card> cards = new ArrayList<>();
	            // ("<cardType>", "<villain>", "<strength>", "<skill>", "<life>",  "<gold>", "<item>", "<price>", "<bonus>")
	            cards.add(new Card("Strength Boss", "Betrayer", 15, 0, 3, 500, null, 0, 0));
	            cards.add(new Card("Skill Boss", "Sorcerer", 0, 15, 3, 500, null, 0, 0));
	            
	            Random random = new Random();
	
	            int randomIndex = random.nextInt(cards.size());
	
	            Card randomCard = cards.get(randomIndex);
	            
	            // Display card method.
	            displayCard(randomCard);
	            // Return card type.
	            return randomCard;
	        }
			return null;
        }
    	
// Item Index
    	// Converts player number choice to the list index number and returns Item.
    	private static String getItemByIndex(List<String> list, int index) {
    	    int itemNumber = 1;
    	    for (String item : list) {
    	        if (itemNumber - 1 == index) {
    	            return item;
    	        }
    	        itemNumber++;
    	    }
    	    return null; // Handle the case where the index is out of bounds
    	}
    	
// Lose Bag
    	// Removes Bag and player must choose to lose selected items above 3.
    	private static void loseBag(List<String> items, Scanner input, Hero chosenHero) {
    		if(chosenHero.isHasBag() == false) {
    			out.println("You have no bag to lose.");
    	        out.println();
    		}else if (items.isEmpty()) {
    	        out.println("No items inside to lose.");
    	        out.println();
    	        chosenHero.setHasBag(false);
    	        return; // Exit the function if the list is empty.
    	    } else if(items.size() <= 3) {
    	    	out.println("You didnt lose any items.");
    	        out.println();
    	        chosenHero.setHasBag(false);
    	        return; // Exit the function if the list is empty.
    	    } else {
    	    	chosenHero.setHasBag(false);
    	    	while (items.size() > 3) {
    	    		int excessItems = items.size() - 3;
    	    		if(excessItems > 1) {
    	    		out.printf("You must lose %s items.", excessItems);
    	    		out.println();
    	    		} else
    	    			out.printf("You must lose %s item.", excessItems);
	    	    		out.println();
	    	    		out.println();
    	    		// Display the list of items with numbers.
    	    	    int itemNumber = 1;
    	    	    for (String item : items) {
    	    	        out.printf("%d. %s%n", itemNumber++, item);
    	    	        out.println();
    	    	    }
    	    		// Prompt the user to select a number.
    	    	    int choice = -1; // Initialize choice with an invalid value

    	    	    while (true) {
    	    	        out.print("Enter the number of the item to lose: ");
    	    	        out.println();
    	    	        
    	    	        // Check if the input is an integer.
    	    	        if (input.hasNextInt()) {
    	    	            choice = input.nextInt();

    	    	            // Check if the choice is valid.
    	    	            if (choice >= 1 && choice <= items.size()) {
    	    	                break; // Exit the loop if a valid choice is provided
    	    	            } else {
    	    	                out.println("Invalid choice. Please enter a valid number.");
    	    	            }
    	    	        } else {
    	    	            out.println("Invalid input. Please enter a valid number.");
    	    	            input.next(); // Consume invalid input to avoid an infinite loop.
    	    	        }
    	    	    }

    	    	    // Remove the selected item from the list.
    	    	    String removedItem = getItemByIndex(items, choice - 1);
    	    	    items.remove(removedItem);

    	    	    // Parse the removed item to extract the bonus information.
    	    	    String[] parts = removedItem.split("\\s+"); // Split by whitespace.
    	    	    if (parts.length == 4 && parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
    	    	        // Strength item bonus.
    	    	        int bonus = Integer.parseInt(parts[3]);
    	    	        out.printf("You have lost: %s%n", removedItem);
    	    	        out.printf("You will lose a Strength bonus of +%d%n", bonus);
    	    	        input.nextLine();   	                
    	                out.println();
    	    	        chosenHero.setStrength(chosenHero.getStrength() - bonus);
    	    	    } else if (parts.length >= 4 && parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
    	    	        // Skill item bonus.
    	    	        int bonus = Integer.parseInt(parts[3]);
    	    	        out.printf("You have lost: %s%n", removedItem);
    	    	        out.printf("You will lose a Skill bonus of +%d%n", bonus);
    	    	        input.nextLine();   	                
    	                out.println();
    	    	        chosenHero.setSkill(chosenHero.getSkill() - bonus);
    	    	    } else {
    	    	        out.printf("You have lost: %s%n", removedItem);
    	    	        out.println();
    	    	        out.println();
    	    	    }
    	    	} out.println();
    	    }
    		
    	}

// Lose Item  	
    	// Removes Item from List from player choice.    	
    	private static void removeItem(List<String> items, Scanner input, Hero chosenHero) {
// Itemshield 		
    		// Check for character with special ability "Itemshield".
    		if (chosenHero.getAbility() == "Itemshield") {
    			out.println("Your character ability Itemshield protects you from losing items!");
        		out.println();
        		return;
    		}
    		else if (items.isEmpty()) {
    	        out.println("No items to lose.");
    	        out.println();
    	        return; // Exit the function if the list is empty.
    	    }

    	    // Display the list of items with numbers.
    	    int itemNumber = 1;
    	    for (String item : items) {
    	        out.printf("%d. %s%n", itemNumber++, item);
    	        out.println();
    	    }

    	    // Prompt the user to select a number.
    	    int choice = -1; // Initialize choice with an invalid value

    	    while (true) {
    	        out.print("Enter the number of the item to lose: ");
    	        out.println();
    	        
    	        // Check if the input is an integer.
    	        if (input.hasNextInt()) {
    	            choice = input.nextInt();

    	            // Check if the choice is valid.
    	            if (choice >= 1 && choice <= items.size()) {
    	                break; // Exit the loop if a valid choice is provided
    	            } else {
    	                out.println("Invalid choice. Please enter a valid number.");
    	            }
    	        } else {
    	            out.println("Invalid input. Please enter a valid number.");
    	            input.next(); // Consume invalid input to avoid an infinite loop.
    	        }
    	    }

    	    // Remove the selected item from the list.
    	    String removedItem = getItemByIndex(items, choice - 1);
    	    items.remove(removedItem);

    	    // Parse the removed item to extract the bonus information.
    	    String[] parts = removedItem.split("\\s+"); // Split by whitespace.
    	    if (parts.length == 4 && parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
    	        // Strength item bonus.
    	        int bonus = Integer.parseInt(parts[3]);
    	        out.printf("You have lost: %s%n", removedItem);
    	        out.printf("You will lose a Strength bonus of +%d%n", bonus);
    	        input.nextLine();   	                
                out.println();
    	        chosenHero.setStrength(chosenHero.getStrength() - bonus);
    	    } else if (parts.length >= 4 && parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
    	        // Skill item bonus.
    	        int bonus = Integer.parseInt(parts[3]);
    	        out.printf("You have lost: %s%n", removedItem);
    	        out.printf("You will lose a Skill bonus of +%d%n", bonus);
    	        input.nextLine();   	                
                out.println();
    	        chosenHero.setSkill(chosenHero.getSkill() - bonus);
    	    } else {
    	        out.printf("You have lost: %s%n", removedItem);
    	        out.println();
    	        out.println();
    	    }

    	    out.println();
    	}
    	
// Lose Spell 	
    	// Removes Spell from List from player choice.
    	private static void removeSpell(List<String> spells, Scanner input, Hero chosenHero) {
// Spellguard    		
    		// Check for character with special ability "Spellguard".
    		if (chosenHero.getAbility() == "Spellguard") {
    			out.println("Your character ability Spellguard protects you from losing spells!");
        		out.println();
        		return;
    		}
    		else if (spells.isEmpty()) {
    	        out.println("No spells to remove.");
    	        out.println();
    	        return; // Exit the function if the list is empty.
    	    }

    	    // Display the list of spells with numbers.
    	    int itemNumber = 1;
    	    for (String spell : spells) {
    	        out.printf("%d. %s%n", itemNumber++, spell);
    	        out.println();
    	    }

    	    // Prompt the user to select a number.
    	    int choice = -1; // Initialize choice with an invalid value

    	    while (true) {
    	        out.print("Enter the number of the spell to drop: ");
    	        out.println();
    	        
    	        // Check if the input is an integer.
    	        if (input.hasNextInt()) {
    	            choice = input.nextInt();

    	            // Check if the choice is valid.
    	            if (choice >= 1 && choice <= spells.size()) {
    	                break; // Exit the loop if a valid choice is provided.
    	            } else {
    	                out.println("Invalid choice. Please enter a valid number.");
    	            }
    	        } else {
    	            out.println("Invalid input. Please enter a valid number.");
    	            input.next(); // Consume invalid input to avoid an infinite loop.
    	        }
    	    }

    	    // Remove the selected spell from the list.
    	    String removedSpell = getItemByIndex(spells, choice - 1);
    	    spells.remove(removedSpell);

    	    out.printf("You have lost: %s%n", removedSpell);
    	    out.println();
    	    out.println();
    	    input.nextLine();
    	}    	

// Lose All Items
    	// Removes All Items from player List.
    	private static void removeAllItems(List<String> items, Hero chosenHero) {
// Itemshield 		
    		// Check for character with special ability "Itemshield".
    		if (chosenHero.getAbility() == "Itemshield") {
    			out.println("Your character ability Itemshield protects you from losing items!");
        		out.println();
        		return;
    		}  		
    		else if (items.isEmpty()) {
    	        out.println("No items to lose.");
    	        out.println();
    	        return; // Exit the function if the set is empty.
    	    }

    	    // Iterate through all items and deduct their bonuses from chosenHero.
    	    for (String item : items) {
    	        // Parse the item to extract the bonus information.
    	        String[] parts = item.split("\\s+"); // Split by whitespace.
    	        if (parts.length >= 4 && (parts[2].equalsIgnoreCase("Strength") || parts[2].equalsIgnoreCase("Skill")) && parts[3].startsWith("+")) {
    	            int bonus = Integer.parseInt(parts[3].substring(1)); // Remove the '+' and parse bonus.
    	            out.printf("You have lost: %s%n", item);
    	            if (parts[2].equalsIgnoreCase("Strength")) {
    	                // Strength item bonus.
    	                out.printf("You will lose a Strength bonus of +%d", bonus);  	                
    	                out.println();
    	                out.println();
    	                chosenHero.setStrength(chosenHero.getStrength() - bonus);
    	            } else if (parts[2].equalsIgnoreCase("Skill")) {
    	                // Skill item bonus.
    	                out.printf("You will lose a Skill bonus of +%d", bonus); 	                
    	                out.println();
    	                out.println();
    	                chosenHero.setSkill(chosenHero.getSkill() - bonus);
    	            }
    	        } else {
    	            out.printf("You have lost: %s%n", item);
    	            out.println();
    	        }
    	    }
    	    // Clear all items from the list.
    	    chosenHero.setHasBag(false);
    	    items.clear();
    	    out.println();
    	}
 
    	
// Lose All Spells
    	// Removes All Items from player List.
    	private static void removeAllSpells(List<String> spells, Hero chosenHero) {
// Spellguard    		
    		// Check for character with special ability "Spellguard".
    		if (chosenHero.getAbility() == "Spellguard") {
    			out.println("Your character ability Spellguard protects you from losing spells!");
        		out.println();
        		return;
    		}    		
    		else if (spells.isEmpty()) {
    	        out.println("No spells to lose.");
    	        out.println();
    	        return; // Exit the function if the set is empty.
    	    }

    	    // Iterate through all spells and list what is being lost..
    	    for (String spell : spells) {
    	            out.printf("You have lost: %s%n", spell);
    	            out.println();
    	        }
    	    // Clear all spells from the list.
    	    spells.clear();
    	    out.println();
    	}
    	
    	
// Clear Screen
    	// Clear viewable screen method after each turn.
    	public static void clearScreen(int numLines) {
            for (int i = 0; i < numLines; i++) {
                out.println(); // Print an empty line
            }
        }
    	
    	
// Game Description
    	// Method to display title and game description.
    	public static void displayGameDescription() {
    		
    		out.println("********************************************************************************");
    		out.println("                                                                                ");
    		out.println("**  **  ******  ******   ****   ******  ******     ******   ****   **     ***** ");
    		out.println("**  **  **      **  **  **  **  **      **         **      **  **  **     **  **");
    		out.println("******  ****    ******  **  **  ****    ******     **  **  **  **  **     **  **");
    		out.println("**  **  **      **  **  **  **  **         **      **   *  **  **  **     **  **");
    		out.println("**  **  ******  **   **  ****   ******  ******     ******   ****   ****** ***** ");
    		out.println("                                                                                ");
    		out.println("********************************************************************************");
    		out.println("                              Game Description                                  ");
    		out.println("--------------------------------------------------------------------------------");
    		out.println("                                                                                ");
    		out.println("This is a role playing fantasy card game. Select your character and fight foes  ");
    		out.println("to earn gold. Arm yourself with weapons and spells and make it to the end to    ");
    		out.println("                   beat the boss at Turn 60. If you can...                      ");   		
    		out.println("                                                                                ");
    		out.println("                                 Good Luck!                                     ");
    		out.println("                                                                                ");
    		out.println("********************************************************************************");
    		out.println("                                                                                ");
    		
    		out.print("Press Enter to play or 'i' for more information: ");
    	    String userInput = input.nextLine();
    	    out.println();

    	    if (userInput.equals("i")) {
    	        out.println("Game Info: Heroes Gold v5.0");
    	        out.println();
    	        out.println("1.  A character can carry 3 items. A bag enables an additonal 2.");
    	        out.println("2.  A standard character can carry 2 spells. A magical character 3.");
    	        out.println("3.  Standard characters have a minimum item count of 1.");
    	        out.println("4.  Magical characters have a minimum spell count of 1.");
    	        out.println("5.  Spells can only be cast before a fight.");
    	        out.println("6.  Strength spells cannot be used before a skill fight.");
    	        out.println("7.  Skill spells cannot be used before a strength fight.");
    	        out.println("8.  Life spells can be used before any fight.");
    	        out.println("9.  Death spells can be used before any fight on any villain.");
    	        out.println("10.  Spells with a positive effect are used on characters.");
    	        out.println("11. Spells with a negative effect are used on villains.");  	        
    	        out.println("12. Cast spells have lasting effects.");
    	        out.println("13. Special Ability: Fortitude   -   Cannot lose strength.");
    	        out.println("14. Special Ability: Mastery     -   Cannot lose skill.");
    	        out.println("15. Special Ability: Prosperity  -   Cannot lose gold.");
    	        out.println("16. Special Ability: Spellguard  -   Cannot lose spells.");
    	        out.println("17. Special Ability: Itemshield  -   Cannot lose items (except bag).");

    	        out.println();
    	        
    	        out.print("Press Enter to play.");
        	    userInput = input.nextLine();
        	    out.println();
    	    }
    	}
    	
    	
// Player Card
    	// Display Player Card method.
    	public static void displayPlayerCard (String playerName, Hero chosenHero, List<String> items, List<String> spells) { 	
    		if(chosenHero.isHasBag() == true) {
				if(chosenHero.isMagicalCharacter() == true) {
    	    		out.printf("%s: %s", playerName, chosenHero.getName());
    	            out.println();
    	            out.println("-".repeat(31));
    	            out.println("Strength: " + chosenHero.getStrength());
    	            out.println("Skill: " + chosenHero.getSkill());
    	            out.printf("Items: Bag %d/5 %s%n", items.size(), items);
    	            out.printf("Spells: %d/3 %s%n", spells.size(), spells);
    	            out.println("Gold: " + chosenHero.getGold());
    	            out.println("Life: " + chosenHero.getLife());  
    	            out.println("Ability: " + chosenHero.getAbility());
    	            out.println();
	            } else {
	            	out.printf("%s: %s", playerName, chosenHero.getName());
	                out.println();
	                out.println("-".repeat(31));
	                out.println("Strength: " + chosenHero.getStrength());
	                out.println("Skill: " + chosenHero.getSkill());
	                out.printf("Items: Bag %d/5 %s%n", items.size(), items);
	                out.printf("Spells: %d/2 %s%n", spells.size(), spells);
	                out.println("Gold: " + chosenHero.getGold());
	                out.println("Life: " + chosenHero.getLife()); 
	                out.println("Ability: " + chosenHero.getAbility());
	                out.println();
	            }
    		}
    		else {
    			if(chosenHero.isMagicalCharacter() == true) {
	    		out.printf("%s: %s", playerName, chosenHero.getName());
	            out.println();
	            out.println("-".repeat(31));
	            out.println("Strength: " + chosenHero.getStrength());
	            out.println("Skill: " + chosenHero.getSkill());
	            out.printf("Items: %d/3 %s%n", items.size(), items);
	            out.printf("Spells: %d/3 %s%n", spells.size(), spells);
	            out.println("Gold: " + chosenHero.getGold());
	            out.println("Life: " + chosenHero.getLife());  
	            out.println("Ability: " + chosenHero.getAbility());
	            out.println();
	            } else {
	            	out.printf("%s: %s", playerName, chosenHero.getName());
	                out.println();
	                out.println("-".repeat(31));
	                out.println("Strength: " + chosenHero.getStrength());
	                out.println("Skill: " + chosenHero.getSkill());
	                out.printf("Items: %d/3 %s%n", items.size(), items);
	                out.printf("Spells: %d/2 %s%n", spells.size(), spells);
	                out.println("Gold: " + chosenHero.getGold());
	                out.println("Life: " + chosenHero.getLife()); 
	                out.println("Ability: " + chosenHero.getAbility());
	                out.println();
	            }
    		}
    	}
    	
// Character List
    	// Display character card list.
    	public static void displayCharacterList() {
    		
    		String fmt = "%-10s%-10s";
    		
    		out.println("*".repeat(20));
	        out.println("Character List");
			out.println("*".repeat(20));	
			out.println();
			out.println("*".repeat(20));
			out.println("1 = Swordsman");
			out.println("-".repeat(20));
			out.printf(fmt, "Strength", "8");
			out.println();
			out.printf(fmt, "Skill", "4");
			out.println();
			out.printf(fmt, "Items", "1");
			out.println();
			out.printf(fmt, "Spells", "0");
			out.println();
			out.printf(fmt, "Gold", "100");
			out.println();
			out.printf(fmt, "Life", "5");
			out.println();
			out.printf(fmt, "Ability:", "Fortitude");
			out.println();
			out.println("-".repeat(20));
			out.println();
			
			out.println("*".repeat(20));
			out.println("2 = Elf");
			out.println("-".repeat(20));
			out.printf(fmt, "Strength", "5");
			out.println();
			out.printf(fmt, "Skill", "6");
			out.println();
			out.printf(fmt, "Items", "1");
			out.println();
			out.printf(fmt, "Spells", "1");
			out.println();
			out.printf(fmt, "Gold", "100");
			out.println();
			out.printf(fmt, "Life", "5");
			out.println();
			out.printf(fmt, "Ability:", "Mastery");
			out.println();
			out.println("-".repeat(20));
			out.println();
			
			out.println("*".repeat(20));
			out.println("3 = Dwarf");
			out.println("-".repeat(20));
			out.printf(fmt, "Strength", "6");
			out.println();
			out.printf(fmt, "Skill", "5");
			out.println();
			out.printf(fmt, "Items", "1");
			out.println();
			out.printf(fmt, "Spells", "1");
			out.println();
			out.printf(fmt, "Gold", "100");
			out.println();
			out.printf(fmt, "Life", "5");
			out.println();
			out.printf(fmt, "Ability:", "Prosperity");
			out.println();
			out.println("-".repeat(20));
			out.println();
			
			out.println("*".repeat(20));
			out.println("4 = Sorceress");
			out.println("-".repeat(20));
			out.printf(fmt, "Strength", "3");
			out.println();
			out.printf(fmt, "Skill", "7");
			out.println();
			out.printf(fmt, "Items", "0");
			out.println();
			out.printf(fmt, "Spells", "2");
			out.println();
			out.printf(fmt, "Gold", "0");
			out.println();
			out.printf(fmt, "Life", "5");
			out.println();
			out.printf(fmt, "Ability:", "Spellguard");
			out.println();
			out.println("-".repeat(20));
			out.println();
			
			out.println("*".repeat(20));
			out.println("5 = Wizard");
			out.println("-".repeat(20));
			out.printf(fmt, "Strength", "3");
			out.println();
			out.printf(fmt, "Skill", "7");
			out.println();
			out.printf(fmt, "Items", "0");
			out.println();
			out.printf(fmt, "Spells", "2");
			out.println();
			out.printf(fmt, "Gold", "0");
			out.println();
			out.printf(fmt, "Life", "5");
			out.println();
			out.printf(fmt, "Ability:", "Itemshield");
			out.println();
			out.println("-".repeat(20));
			out.println();
    	}
    	
    	
// Select Character
    	// Method to select character and return number.
    	public static int selectCharacter(Scanner input, PrintStream out) {    		
			
    		int characterNumber = 0;
    		
	        while (true) {
	        	out.println();
	        	out.print("Please select a character number to play: ");
	            out.println();
	            String inputLine = input.nextLine();	
	            try {
	                characterNumber = Integer.parseInt(inputLine);              
	                if (characterNumber >= 1 && characterNumber <= 5) {
	                    break;
	                } else {
	                    out.println("Invalid input. Please enter a number from 1 to 4.");
	                }
	            } catch (NumberFormatException e) {
	                out.println("Invalid input. Please enter a valid number.");
	            }
	        }
	        return characterNumber;
    	}
    	
    	
// Game Level
    	// Display Game Level message based on turn number.
    	public static void displayLevel(int turnCount) {
    		if(turnCount == 1) {
    		out.println("**********************");
    		out.println(" You are on Level 1!");
    		out.println("Next Level in 30 Turns");
    		out.println("**********************");
    		out.println();
    		} 
    		else if(turnCount == 30) {
        		out.println("***********************");
        		out.println("You are now on Level 2!");
        		out.println("Next Level in 30 Turns");
        		out.println("***********************");
        		out.println();
    		}
    		else if(turnCount == 40) {
        		out.println("***********************");
        		out.println("You are now on Level 3!");
        		out.println("Final Level in 10 Turns");
        		out.println("***********************");
        		out.println();
    		}
    		else if(turnCount == 50) {
        		out.println("***************************");
        		out.println("You are on the Final Level!");
        		out.println("        Good Luck!");
        		out.println("***************************");
        		out.println();
    		}
    		
    	}
    	
    	
// Card Play
    	// Method to process all drawn cards.
    	public static Card processDrawnCard(Card drawnCard, Hero chosenHero, List<String> items, List<String> spells, String playerName, int totalGold) {

// Lose All Items	        	
			// Check if the drawn card is of type "Lose All Items".
            if (drawnCard.getCardtype().equalsIgnoreCase("Lose All Items")) {
            	// Lose All Items method.
            	removeAllItems(items, chosenHero);               
            	displayPlayerCard (playerName, chosenHero, items, spells);
            }
// Lose All Spells	        	
 			// Check if the drawn card is of type "Lose All Spells".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose All Spells")) {
             	// Lose All Spells method.
             	removeAllSpells(spells, chosenHero);               
             	displayPlayerCard (playerName, chosenHero, items, spells);
            }            
// Lose Item	            
			// Check if the drawn card is of type "Lose Item".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Item")) {
            	// Lose Item method.
                removeItem(items, input, chosenHero);
                displayPlayerCard (playerName, chosenHero, items, spells);
            }
// Lose Bag	            
 			// Check if the drawn card is of type "Lose Bag".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Bag")) {
            	 // Lose Bag method.
            	 loseBag(items, input, chosenHero);
                 displayPlayerCard (playerName, chosenHero, items, spells);
             }            
// Lose Spell            
 			// Check if the drawn card is of type "Lose Spell".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Spell")) {
             	 // Lose Item method.
                 removeSpell(spells, input, chosenHero);
                 displayPlayerCard (playerName, chosenHero, items, spells);
             }            
// Life Item	            
         // Check if the drawn card is of type "Life Item".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Life Item")) {
                int lifeChange = drawnCard.getLife();
                int currentLife = chosenHero.getLife();
                
                if (lifeChange > 0) {
                    int newLife = currentLife + lifeChange;
                    out.printf("You have gained %d life.", Math.abs(lifeChange));
                    chosenHero.setLife(newLife);
                } else if (lifeChange < 0) {
                    int newLife = Math.max(currentLife + lifeChange, 0);
                    int lifeLost = currentLife - newLife;
                    out.printf("You have lost %d life.", Math.abs(lifeLost));
                    chosenHero.setLife(newLife);
                }
                
                out.println();
                out.println();
                displayPlayerCard(playerName, chosenHero, items, spells);
            }
// Gain Skill
            // Check if the drawn card is "Gain Skill".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Gain Skill")) {
            	int newSkill = chosenHero.getSkill() + drawnCard.getBonus();
            	chosenHero.setSkill(newSkill);
            	out.printf("You have gained %d Skill.", drawnCard.getBonus());
            	out.println();
            	out.println();
            	displayPlayerCard (playerName, chosenHero, items, spells);
            }
// Gain Strength
            // Check if the drawn card is "Gain Strength".
            else if (drawnCard.getCardtype().equalsIgnoreCase("Gain Strength")) {
            	int newStrength = chosenHero.getStrength() + drawnCard.getBonus();
            	chosenHero.setStrength(newStrength);
            	out.printf("You have gained %d Strength.", drawnCard.getBonus());
            	out.println();
            	out.println();
            	displayPlayerCard (playerName, chosenHero, items, spells);
            }
// Lose Skill
            // Check if the drawn card is type "Lose Skill" and standard character.
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Skill") && chosenHero.getAbility() != "Mastery") {           
             // Calculate the total skill bonus from items list.
                int totalItemBonus = 0;
                for (String item : items) {
                    // Split the item string to extract the skill bonus part.
                    String[] parts = item.split("\\s+");
                    if (parts.length >= 3 && parts[2].equalsIgnoreCase("Skill")) { // Check for "Skill" at index 2.
                        // Extract the bonus (ignoring the '+' sign).
                        int itemBonus = Integer.parseInt(parts[3].replace("+", ""));
                        totalItemBonus += itemBonus;
                    }
                }          
                // Calculate the minimum skill level required to not go below the total skill item bonus.
                int minSkillRequired = Math.max(0, totalItemBonus);
                
                // Check if Chosen Hero has enough skill.
                if (chosenHero.getSkill() <= Math.abs(minSkillRequired)) {
                    out.println("You do not have enough Skill to lose without going below your item bonuses.");
                    out.println();
                } else {
                    int skillToLose = Math.min(Math.abs(drawnCard.getBonus()), chosenHero.getSkill() - minSkillRequired);
                    chosenHero.setSkill(chosenHero.getSkill() - skillToLose);
                    out.printf("You have lost %s Skill.", skillToLose);
                    out.println();
                    out.println();
                    displayPlayerCard(playerName, chosenHero, items, spells);
                }
            }
// Mastery 
            // Check if the drawn card is type "Lose Skill" and special ability "Mastery" character.
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Skill") && chosenHero.getAbility() == "Mastery") {           
             // Calculate the total strength bonus from items list.
            	out.println("Your character ability Mastery protects you from losing skill!");
            	out.println();
            	displayPlayerCard(playerName, chosenHero, items, spells);
            }             
// Lose Strength
            // Check if the drawn card is type "Lose Strength" and standard ability.
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Strength") && chosenHero.getAbility() != "Fortitude") {           
             // Calculate the total strength bonus from items list.
                int totalItemBonus = 0;
                for (String item : items) {
                    // Split the item string to extract the bonus part.
                    String[] parts = item.split("\\s+");
                    if (parts.length >= 3 && parts[2].equalsIgnoreCase("Strength")) { // Check for "Strength" at index 2.
                        // Extract the bonus (ignoring the '+' sign).
                        int itemBonus = Integer.parseInt(parts[3].replace("+", ""));
                        totalItemBonus += itemBonus;
                    }
                }          
                // Calculate the minimum strength level required to not go below the total strength item bonus.
                int minStrengthRequired = Math.max(0, totalItemBonus);
                
                // Check if Chosen Hero has enough strength.
                if (chosenHero.getStrength() <= Math.abs(minStrengthRequired)) {
                    out.println("You do not have enough Strength to lose without going below your item bonuses.");
                    out.println();
                } else {
                    int strengthToLose = Math.min(Math.abs(drawnCard.getBonus()), chosenHero.getStrength() - minStrengthRequired);
                    chosenHero.setStrength(chosenHero.getStrength() - strengthToLose);
                    out.printf("You have lost %s Strength.", strengthToLose);
                    out.println();
                    out.println();
                    displayPlayerCard(playerName, chosenHero, items, spells);
                }
            }
// Fortitude
            // Check if the drawn card is type "Lose Strength" and special ability "Fortitude" character.
            else if (drawnCard.getCardtype().equalsIgnoreCase("Lose Strength") && chosenHero.getAbility() == "Fortitude") {           
             // Calculate the total strength bonus from items list.
            	out.println("Your character ability Fortitude protects you from losing strength!");
            	out.println();
            	displayPlayerCard(playerName, chosenHero, items, spells);
            }             

               
// Strength/Skill Item	            
            // Check if the drawn card is of type "Strength Item" or "Skill Item".
            else if(drawnCard.getCardtype().equalsIgnoreCase("Strength Item") || drawnCard.getCardtype().equalsIgnoreCase("Skill Item")) {
            	// Check if Chosen Hero has a bag.
            	if(chosenHero.isHasBag() == true) {
	            	// Check if Chosen Hero has enough gold.
	            	if(chosenHero.getGold() < drawnCard.getPrice()) {
	            		out.println("Sorry you do not have enough gold to buy this item.");
	            		out.println();
	            	// Check Item Carry Count. Max 5 Items.
	            	} else if (items.size() >= 5) {
	                    out.println("You have reached the maximum limit for items. Cannot add more items.");
	                    // Loop to give option to Drop Item and enforce choice (y/n).
	                    while (true) {
	                        out.println("Would you like to drop an item? (y/n)");
	                        String dropItem = input.nextLine();
	                        if (dropItem.equalsIgnoreCase("y")) {
	                            // Display the list of items with numbers.
	                            int itemNumber = 1;
	                            for (String item : items) {
	                                out.printf("%d. %s%n", itemNumber++, item);
	                                out.println();
	                            }
	
	                            boolean itemRemoved = false; // Flag to track if an item has been removed.
	
	                            do {
	                                try {
	                                    // Prompt the user to select a number.
	                                    out.print("Enter the number of the item to drop or 0 to cancel: ");
	                                    out.println();
	                                    int choice = input.nextInt();
	                                    
	                                    input.nextLine();
	                                    if (choice == 0) {
	                                    	break;
	                                    }
	                                    // Check if the choice is valid.
	                                    else if (choice >= 1 && choice <= items.size()) {
	                                        // Remove the selected spell from the list using getItemByIndex method.
	                                        String removedItem = getItemByIndex(items, choice - 1);
	                                        items.remove(removedItem);
	                                        // Parse the removed item to extract the bonus information.
	   	                             	    String[] parts = removedItem.split("\\s+"); // Split by whitespace.
	   	                             	    if (parts.length == 4 && parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
	   	                             	        // Strength item bonus.
	   	                             	        int bonus = Integer.parseInt(parts[3]);
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.printf("You will lose a Strength bonus of +%d%n", bonus);   	                
	   	                                        out.println();
	   	                             	        chosenHero.setStrength(chosenHero.getStrength() - bonus);
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } else if (parts.length >= 4 && parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
	   	                             	        // Skill item bonus.
	   	                             	        int bonus = Integer.parseInt(parts[3]);
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.printf("You will lose a Skill bonus of +%d%n", bonus);                          	          	                
	   	                                        out.println();
	   	                             	        chosenHero.setSkill(chosenHero.getSkill() - bonus);
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } else {
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.println();
	   	                             	        out.println();
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } 	                          	      	                                        
	                                        
	                                    } else {
	                                        out.println("Invalid choice.");
	                                    }
	                                } catch (InputMismatchException e) {
	                                    out.println("Invalid input. Please enter a valid number.");
	                                 input.nextLine();
	                                }
	                            } while (true); // This loop will continue until a valid choice is made.
	
	                            // Check if an item was removed and break the outer loop
	                            if (itemRemoved) {
	                            	out.println("----------------------");
	                            	out.printf("Item: %s", drawnCard.getItem() + " Price: " + drawnCard.getPrice());
	                            	out.println();
	                            	out.println();
	                                break;
	                            }
	                        } else if (dropItem.equalsIgnoreCase("n")) {
	                            break; // Exit the outer loop if the player chooses not to drop an item.
	                        } else {
	                            out.println("Invalid choice. Please enter 'y' or 'n'.");
	                        }
	                    }
	                }
	            	// Choice to buy Card Item.
	            	while (items.size() < 5 && chosenHero.getGold() >= drawnCard.getPrice()) {
	            	    out.println("Would you like to buy this item? (y/n)");
	            	    out.println();
	            	    String purchase = input.nextLine();
	            	    
	            	    if (purchase.equalsIgnoreCase("y")) {
	            	    	if(drawnCard.getCardtype().equalsIgnoreCase("Strength Item")) {
	            	    		String itemToAdd = drawnCard.getItem();
	              	            items.add(itemToAdd);
	            	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	            	            chosenHero.setGold(newGold);
	            	            totalGold = chosenHero.getGold();
	            	            int bonus = drawnCard.getBonus();
	            	            chosenHero.setStrength(chosenHero.getStrength() + bonus); 
	            	            displayPlayerCard (playerName, chosenHero, items, spells);
	            	    	} else if(drawnCard.getCardtype().equalsIgnoreCase("Skill Item")) {
	            	    		String itemToAdd = drawnCard.getItem();
	              	            items.add(itemToAdd);
	            	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	            	            chosenHero.setGold(newGold);
	            	            totalGold = chosenHero.getGold();
	            	            int bonus = drawnCard.getBonus();
	            	            chosenHero.setSkill(chosenHero.getSkill() + bonus); 
	            	            displayPlayerCard (playerName, chosenHero, items, spells);
	            	    	}
	            	        // If 'y' is entered, exit the loop.
	            	        break;
	            	    } else if (purchase.equalsIgnoreCase("n")) {
	            	        // If 'n' is entered, exit the loop without buying the Item.
	            	        break;
	            	    } else {
	            	        // Invalid input, prompt the user to enter 'y' or 'n' again.
	            	        out.println("Invalid input. Please enter 'y' or 'n'.");
	            	    }
	            	}	            	
	            } else {
	            	// Check if Chosen Hero has enough gold.
	            	if(chosenHero.getGold() < drawnCard.getPrice()) {
	            		out.println("Sorry you do not have enough gold to buy this item.");
	            		out.println();
	            	// Check Item Carry Count. Max 3 Items.
	            	} else if (items.size() >= 3) {
	                    out.println("You have reached the maximum limit for items. Cannot add more items.");
	                    // Loop to give option to Drop Item and enforce choice (y/n).
	                    while (true) {
	                        out.println("Would you like to drop an item? (y/n)");
	                        String dropItem = input.nextLine();
	                        if (dropItem.equalsIgnoreCase("y")) {
	                            // Display the list of items with numbers.
	                            int itemNumber = 1;
	                            for (String item : items) {
	                                out.printf("%d. %s%n", itemNumber++, item);
	                                out.println();
	                            }
	
	                            boolean itemRemoved = false; // Flag to track if an item has been removed.
	
	                            do {
	                                try {
	                                	// Prompt the user to select a number.
	                                    out.print("Enter the number of the item to drop or 0 to cancel: ");
	                                    out.println();
	                                    int choice = input.nextInt();
	                                    
	                                    input.nextLine();
	                                    if (choice == 0) {
	                                    	break;
	                                    }
	                                    // Check if the choice is valid.
	                                    else if (choice >= 1 && choice <= items.size()) {
	                                        // Remove the selected spell from the list using getItemByIndex method.
	                                        String removedItem = getItemByIndex(items, choice - 1);
	                                        items.remove(removedItem);
	                                        // Parse the removed item to extract the bonus information.
	   	                             	    String[] parts = removedItem.split("\\s+"); // Split by whitespace.
	   	                             	    if (parts.length == 4 && parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
	   	                             	        // Strength item bonus.
	   	                             	        int bonus = Integer.parseInt(parts[3]);
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.printf("You will lose a Strength bonus of +%d%n", bonus);   	                
	   	                                        out.println();
	   	                             	        chosenHero.setStrength(chosenHero.getStrength() - bonus);
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } else if (parts.length >= 4 && parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
	   	                             	        // Skill item bonus.
	   	                             	        int bonus = Integer.parseInt(parts[3]);
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.printf("You will lose a Skill bonus of +%d%n", bonus);                          	          	                
	   	                                        out.println();
	   	                             	        chosenHero.setSkill(chosenHero.getSkill() - bonus);
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } else {
	   	                             	        out.printf("You have dropped: %s%n", removedItem);
	   	                             	        out.println();
	   	                             	        out.println();
	   	                             	        itemRemoved = true; // Set the flag to true
	   	                             	        break; // Exit the loop if an item has been removed.
	   	                             	    } 	                          	      	                                        
	                                        
	                                    } else {
	                                        out.println("Invalid choice.");
	                                    }
	                                } catch (InputMismatchException e) {
	                                    out.println("Invalid input. Please enter a valid number.");
	                                 input.nextLine();
	                                }
	                            } while (true); // This loop will continue until a valid choice is made.
	
	                            // Check if an item was removed and break the outer loop
	                            if (itemRemoved) {
	                            	out.println("----------------------");
	                            	out.printf("Item: %s", drawnCard.getItem() + " Price: " + drawnCard.getPrice());
	                            	out.println();
	                            	out.println();
	                                break;
	                            }
	                        } else if (dropItem.equalsIgnoreCase("n")) {
	                            break; // Exit the outer loop if the player chooses not to drop an item.
	                        } else {
	                            out.println("Invalid choice. Please enter 'y' or 'n'.");
	                        }
	                    }
	                }
	            	// Choice to buy Card Item.
	            	while (items.size() < 3 && chosenHero.getGold() >= drawnCard.getPrice()) {
	            	    out.println("Would you like to buy this item? (y/n)");
	            	    out.println();
	            	    String purchase = input.nextLine();
	            	    
	            	    if (purchase.equalsIgnoreCase("y")) {
	            	    	if(drawnCard.getCardtype().equalsIgnoreCase("Strength Item")) {
	            	    		String itemToAdd = drawnCard.getItem();
	              	            items.add(itemToAdd);
	            	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	            	            chosenHero.setGold(newGold);
	            	            totalGold = chosenHero.getGold();
	            	            int bonus = drawnCard.getBonus();
	            	            chosenHero.setStrength(chosenHero.getStrength() + bonus); 
	            	            displayPlayerCard (playerName, chosenHero, items, spells);
	            	    	} else if(drawnCard.getCardtype().equalsIgnoreCase("Skill Item")) {
	            	    		String itemToAdd = drawnCard.getItem();
	              	            items.add(itemToAdd);
	            	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	            	            chosenHero.setGold(newGold);
	            	            totalGold = chosenHero.getGold();
	            	            int bonus = drawnCard.getBonus();
	            	            chosenHero.setSkill(chosenHero.getSkill() + bonus); 
	            	            displayPlayerCard (playerName, chosenHero, items, spells);
	            	    	}
	            	        // If 'y' is entered, exit the loop.
	            	        break;
	            	    } else if (purchase.equalsIgnoreCase("n")) {
	            	        // If 'n' is entered, exit the loop without buying the Item.
	            	        break;
	            	    } else {
	            	        // Invalid input, prompt the user to enter 'y' or 'n' again.
	            	        out.println("Invalid input. Please enter 'y' or 'n'.");
	            	    }
	            	}            	
	            }
            }

// Bag Item	            
            // Check if the drawn card is of type "Bag Item".
            else if(drawnCard.getCardtype().equalsIgnoreCase("Bag Item")) {
            	// Check if Chosen Hero already has a bag.
            	if(chosenHero.isHasBag() == true) {
            		out.println("Sorry you already have a bag.");
            		out.println();
            	} else {
	            	// Check if Chosen Hero already has enough gold.
	            	if(chosenHero.getGold() < drawnCard.getPrice()) {
	            		out.println("Sorry you do not have enough gold to buy this item.");
	            		out.println();
	            	// Check Item Carry Count. Max 5 Items.
	            	} else {
	            		// Choice to buy Card Item.
		            	while (chosenHero.getGold() >= drawnCard.getPrice()) {
		            	    out.println("Would you like to buy this item? (y/n)");
		            	    out.println();
		            	    String purchase = input.nextLine();
		            	    
		            	    if (purchase.equalsIgnoreCase("y")) {
		            	        chosenHero.setHasBag(true);   
		            	        displayPlayerCard (playerName, chosenHero, items, spells);
		            	        // If 'y' is entered, exit the loop.
		            	        break;
		            	    } else if (purchase.equalsIgnoreCase("n")) {
		            	        // If 'n' is entered, exit the loop without buying the Item.
		            	        break;
		            	    } else {
		            	        // Invalid input, prompt the user to enter 'y' or 'n' again.
		            	        out.println("Invalid input. Please enter 'y' or 'n'.");
		            	    }
		            	}
		            }    
            	}
            }
            
// Treasure Item	                    
            // Check if the drawn card is of type "Treasure" and standard character.
            else if(drawnCard.getCardtype().equalsIgnoreCase("Treasure") && chosenHero.getAbility() != "Prosperity") {
            	totalGold = chosenHero.getGold();
            	
            	if (drawnCard.getGold() < 0) {
            	    int goldAmount = Math.abs(drawnCard.getGold()); // Get the positive amount of gold lost.
            	    if (chosenHero.getGold() >= goldAmount) {
            	        out.printf("You have lost %s gold.", goldAmount);
            	        out.println();
            	        out.println();
            	        totalGold -= goldAmount; // Deduct the gold
            	        chosenHero.setGold(totalGold);
            	    } else {
            	        out.printf("You have lost %s gold, but you don't have enough.", goldAmount);
            	        out.println();
            	        out.println();
            	        totalGold = 0; // Set the total gold to 0 if the Chosen Hero doesn't have enough.
            	        chosenHero.setGold(totalGold);
            	    }
            	} else {
            	    totalGold += drawnCard.getGold(); // Add the gold.
            	               	    
            	    chosenHero.setGold(totalGold);
            	    out.printf("You have found %s gold.", Math.abs(drawnCard.getGold())); // Use Math.abs to display the positive amount.
            	    out.println();
            	    out.println();
            	}
            	displayPlayerCard (playerName, chosenHero, items, spells);

            }
            
// Prosperity                   
            // Check if the drawn card is of type "Treasure" and special ability Prosperity character.
            else if(drawnCard.getCardtype().equalsIgnoreCase("Treasure") && chosenHero.getAbility() == "Prosperity") {
            	totalGold = chosenHero.getGold();
            	
            	if (drawnCard.getGold() < 0) {
            		out.println("Your character ability Prosperity protects you from losing gold!");
            		out.println();
            	} else {
            	    totalGold += drawnCard.getGold(); // Add the gold.         	               	    
            	    chosenHero.setGold(totalGold);
            	    out.printf("You have found %s gold.", Math.abs(drawnCard.getGold())); // Use Math.abs to display the positive amount.
            	    out.println();
            	    out.println();
            	}
            	displayPlayerCard (playerName, chosenHero, items, spells);

            }            
            
// Fight Card	
            // Check if the drawn card is of type "Fight". 1 Spell can be cast at the start of each fight sequence.
            int totalGoldEarned = 0;
            String fightType = null;
            boolean hasSpells = !spells.isEmpty();
            int spellCastSkill = 0; // Zero out Skill spell effects.
            int spellCastStrength = 0; // Zero out Strength spell effects. 
            int spellCastLife = 0;
            boolean fightAgain = true;    			
			int villainBase = 0;
			int characterBase = 0;  			
			int heroDiceRoll = 0;
			int villainDiceRoll = 0;
			int villainAttack = 0;
			int characterAttack = 0;
			String fmt2 = "%-10s%-20s%-20s";
			int villainLife = drawnCard.getLife() - spellCastLife;   
            
			if (drawnCard.getCardtype().equalsIgnoreCase("Fight") || drawnCard.getCardtype().equalsIgnoreCase("Strength Fight") || drawnCard.getCardtype().equalsIgnoreCase("Skill Fight") || drawnCard.getCardtype().equalsIgnoreCase("Skill Boss") || drawnCard.getCardtype().equalsIgnoreCase("Strength Boss")) {                               			
// Fight Loop    			
    			// Loop to keep fighting until Chosen Hero or Villain is out of life. Fight Again flag.
    			while (fightAgain) {
    				boolean deathSpellUsedLastTurn = false; // Flag to track if a death spell was used in the previous turn.
        			if(villainLife == 0) {
        				out.printf("You earned %d gold.",drawnCard.getGold());
        				out.println();
        				out.println();
        				totalGoldEarned += drawnCard.getGold();
	                    chosenHero.setGold(chosenHero.getGold() + totalGoldEarned);
        				displayPlayerCard (playerName, chosenHero, items, spells);
        				break;
        			} else { 
        				out.println("Fight To The Death!");
        				out.println();
// Cast Spell        				
        				hasSpells = !spells.isEmpty();
        				// Check if has spells. One Spell can be cast at the start of each fight sequence.
                		if (hasSpells) {
                			boolean validSpell = false;
                	
                			while (!validSpell) {
                				out.print("Press Enter to Fight the " + drawnCard.getVillain() + " or 's' to use a spell: ");
                				String choice = input.nextLine();
                				out.println();
                	
                				if (choice.equalsIgnoreCase("s")) {
                					// Display the list of spells with numbers.
                					int itemNumber = 1;
                					for (String spell : spells) {
                						out.printf("%d. %s%n", itemNumber++, spell);
                					}
                	
                					// Prompt the user to select a number.
                					out.println();
                					out.print("Enter the number of the spell to use or 0 to cancel: ");
                					out.println();
                					
                					int spellChoice = 0; // Initialize spellChoice
                					boolean validInput = false;

                					while (!validInput) {
                					    try {
                					        spellChoice = Integer.parseInt(input.nextLine()); // Read input as a string and parse it as an integer
                					        validInput = true; // Input is valid, exit the loop
                					    } catch (NumberFormatException e) {
                					        out.println("Invalid input. Please enter a valid number.");
                					        out.print("Enter the number of the spell to use or 0 to cancel:");
                					        out.println();
                					    }
                					}
                					               						                                     
                                    if (spellChoice == 0) {
                                    	break;
                                    }
                	
                					// Check if the choice is valid.
                					if (spellChoice >= 1 && spellChoice <= spells.size()) {
                						String selectedSpell = spells.get(spellChoice - 1);
                						String[] parts = selectedSpell.split("\\s+");
                	
                						// Check if the spell is appropriate for the situation.
                						boolean spellValid = true;
                	
                						if (parts[2].equalsIgnoreCase("Strength") && drawnCard.getStrength() == 0) {
                							out.println("You can't use a Strength spell in a Skill Fight.");
                							out.println();
                							spellValid = false;
                						} else if (parts[2].equalsIgnoreCase("Skill") && drawnCard.getSkill() == 0) {
                							out.println("You can't use a Skill spell in a Strength Fight.");
                							out.println();
                							spellValid = false;
                						}
                	
                						if (spellValid) {
                							validSpell = true; // Only set to true if a valid spell is chosen
                	
                							// Use the selected spell...
                							if (parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("-")) {
                								// Strength spell with a bonus. Used on villain.
                								int spellBonus = Integer.parseInt(parts[3]);
                								out.printf("You have used: %s%n", selectedSpell);
                								out.printf("%s will lose a Strength bonus of %d%n", drawnCard.getVillain(), Math.abs(spellBonus));
                								out.println();
                								spellCastStrength -= spellBonus;
                								// Remove the used spell from the list
                    							spells.remove(spellChoice - 1);
                							} else if (parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("-")) {
                								// Skill spell with a bonus. Used on villain.
                								int spellBonus = Integer.parseInt(parts[3]);
                								out.printf("You have used: %s%n", selectedSpell);
                								out.printf("%s will lose a Skill bonus of %d%n", drawnCard.getVillain(), Math.abs(spellBonus));
                								out.println();
                								spellCastSkill -= spellBonus;
                								// Remove the used spell from the list
                    							spells.remove(spellChoice - 1);
                							}else if (parts[2].equalsIgnoreCase("Strength") && parts[3].startsWith("+")) {
            			                        // Strength spell with a bonus. Used on the player.
            			                        int spellBonus = Integer.parseInt(parts[3]);
            			                        out.printf("You have used: %s%n", selectedSpell);
            			                        out.printf("You have gained Strength bonus of %d%n", spellBonus);
            			                        out.println();
            			                        int playerStrength = chosenHero.getStrength() + spellBonus;
            			                        chosenHero.setStrength(playerStrength);
            			                        // Remove the used spell from the list
                    							spells.remove(spellChoice - 1);
                    							displayPlayerCard (playerName, chosenHero, items, spells);
            			                    } else if (parts[2].equalsIgnoreCase("Skill") && parts[3].startsWith("+")) {
            			                        // Skill spell with a bonus. Used on the player.
            			                        int spellBonus = Integer.parseInt(parts[3]);
            			                        out.printf("You have used: %s%n", selectedSpell);
            			                        out.printf("You have gained Skill bonus of %d%n", spellBonus);
            			                        out.println();
            			                        int playerSkill = chosenHero.getSkill() + spellBonus;
            			                        chosenHero.setSkill(playerSkill);
            			                        spells.remove(spellChoice - 1);
                    							displayPlayerCard (playerName, chosenHero, items, spells);
                							} else if (parts[2].equalsIgnoreCase("Life") && parts[3].startsWith("+")) {
                								// Life spell with a bonus. Used on player.
                								int spellBonus = Integer.parseInt(parts[3]);
                								out.printf("You have used: %s%n", selectedSpell);
                								out.printf("You have gained Life bonus of %d%n", spellBonus);
                								out.println();
                								int spellLife = chosenHero.getLife() + spellBonus;
                								chosenHero.setLife(spellLife);
                								spells.remove(spellChoice - 1);
                								displayPlayerCard (playerName, chosenHero, items, spells);
                							} else if (parts[2].equalsIgnoreCase("Life") && parts[3].startsWith("-")) {
                								// Life spell with a bonus. Used on villain.
                								int spellBonus = Integer.parseInt(parts[3]);
                								out.printf("You have used: %s%n", selectedSpell);
                								out.printf("%s has lost a Life bonus of %d%n", drawnCard.getVillain(), Math.abs(spellBonus));
                								out.println();
                								villainLife -= 1;
                								spells.remove(spellChoice - 1);
                								hasSpells = !spells.isEmpty();
                								out.printf("Your spell defeated the %s! They have %s life left.", drawnCard.getVillain(), villainLife);
                	                    		out.println();
                	                    		out.println();
                	                    		deathSpellUsedLastTurn = true; // Set the flag to true when a death spell is used 				
                								if(villainLife == 0) {
                			        				out.printf("You earned %d gold.", drawnCard.getGold());
                			        				out.println();
                			        				out.println();
                			        				totalGoldEarned += drawnCard.getGold();
                				                    chosenHero.setGold(chosenHero.getGold() + totalGoldEarned);
                			        				displayPlayerCard (playerName, chosenHero, items, spells);
                			        				fightAgain = false;
                								}
                							}else {
                								out.printf("You have used: %s%n", selectedSpell);
                								spells.remove(spellChoice - 1);
                							}
                						}
                					} else {
                						out.println("Invalid choice. Please enter a valid spell number.");
                						out.println();
                					}
                				} else {
                					validSpell = true; // Player chose not to use a spell
                				}
                			}
                		} 
        			} 
        			if (!deathSpellUsedLastTurn) {
	                    // Check if fight will be with Skill or Strength points. Alternatively could have used if(drawnCard.getCardtype().equalsIgnoreCase("Strength Fight")) etc.
// Set Fight Type.
	    				if(drawnCard.getStrength() == 0) { // Skill Fight
	    					characterBase = chosenHero.getSkill();
	    					villainBase = drawnCard.getSkill() - spellCastSkill;
	                    	fightType = "Skill";
	    					
	    				}else if(drawnCard.getSkill() == 0) { // Strength Fight
	    					characterBase = chosenHero.getStrength();
	    					villainBase = drawnCard.getStrength() - spellCastStrength;
	                    	fightType = "Strength";
	    				}    					    				
// Fight Table
	    				
	    				out.printf("The %s has a %s of %d\n", chosenHero.getName(), fightType, characterBase);
	                    out.printf("The %s has a %s of %d\n", drawnCard.getVillain(), fightType, villainBase);	                    
	                    out.println();
	                    out.printf("Players roll two dice and add to %s attack.", fightType);
	                    out.println();
	                    out.println();
// Dice Sequence		                    
	                    out.println("Press Enter to roll the dice");
	                    input.nextLine();	    				
	    				clearScreen(40);  

	    				DiceResult finalDiceResult = DiceRollSimulation.simulateDiceRolls(chosenHero, drawnCard);
	    				
	    				// Retrieve the individual dice values from the finalDiceResult
	    			    int heroDice1 = finalDiceResult.getHeroDice1();
	    			    int heroDice2 = finalDiceResult.getHeroDice2();
	    			    int villainDice1 = finalDiceResult.getVillainDice1();
	    			    int villainDice2 = finalDiceResult.getVillainDice2();
	    				
	    			    heroDiceRoll = finalDiceResult.getHeroDice1() + finalDiceResult.getHeroDice2();	    			    
	    			    villainDiceRoll = finalDiceResult.getVillainDice1() + finalDiceResult.getVillainDice2();
	    			    
	    			    // Calculate character attacks
	    			    characterAttack = characterBase + heroDice1 + heroDice2;
	    			    villainAttack = villainBase + villainDice1 + villainDice2;

	    			    
	    				out.printf(fmt2, "", chosenHero.getName(), drawnCard.getVillain());
	    				out.println();
	    				out.println("*".repeat(45));
	    				out.printf(fmt2, "Dice", heroDiceRoll, villainDiceRoll);
	    				out.println();
	    				out.printf(fmt2, fightType, characterBase, villainBase);
	    				out.println();
	    				out.println("-".repeat(45));
	    				out.printf(fmt2, "Attack", characterAttack, villainAttack);
	    				out.println();
	    				out.println();
	                    out.printf("%s attacks with a %s of " + characterAttack, chosenHero.getName(), fightType);  
	                    out.println();
	                    out.printf("%s attacks with a %s of " + villainAttack, drawnCard.getVillain(), fightType);
	                    out.println();
	                    out.println();
// Player Win
	                    if (characterAttack > villainAttack) {                  	
	                    	villainLife -= 1;
	                    	if(villainLife == 0) {
		                    	out.println();
		                        out.printf("You defeated the %s! You earned %d gold.\n", drawnCard.getVillain(), drawnCard.getGold());
		                        out.println();
		                        totalGoldEarned += drawnCard.getGold();
			                    chosenHero.setGold(chosenHero.getGold() + totalGoldEarned);
		                        displayPlayerCard (playerName, chosenHero, items, spells);
		                        fightAgain = false;
	                    	}else {
	                    		out.printf("You defeated the %s! They have %s life left.", drawnCard.getVillain(), villainLife);
	                    		out.println();
	                    		out.println();
	                    		displayPlayerCard (playerName, chosenHero, items, spells);                   		                   	
	                    	}
// Villain Win                    	
	                    } else if (characterAttack < villainAttack) {
	                    	out.println();
	                        out.printf("The %s defeated your %s! You lose 1 life point.", drawnCard.getVillain(), chosenHero.getName());
	                        out.println();
	                        out.println();
	                        out.println("Fight To The Death!");
	                        out.println();
	                        chosenHero.setLife(chosenHero.getLife() - 1);
	                        displayPlayerCard (playerName, chosenHero, items, spells);
	                        if(chosenHero.getLife() == 0) {
	                        	fightAgain = false;
	                        }
// Tie                        
	                    } else {
	                    	out.println();
	                        out.println("It's a tie!");
	                        out.println();
	                        out.println("Fight Again!");
	                        out.println();
	                        displayPlayerCard (playerName, chosenHero, items, spells);                       	                        
	                    }
	                    
	                } 
    			}
            }
            
// Magic Card
            // For magical characters with Max Spell count 3.
            if(chosenHero.isMagicalCharacter() == true) {
	            // Check if the drawn card is of type "Strength Spell" or "Skill Spell" or "Life Spell".  
	            if(drawnCard.getCardtype().equalsIgnoreCase("Strength Spell") || drawnCard.getCardtype().equalsIgnoreCase("Skill Spell") || drawnCard.getCardtype().equalsIgnoreCase("Life Spell")) {
	                	// Check if Chosen Hero has enough gold.
	                	if(chosenHero.getGold() < drawnCard.getPrice()) {
	                		out.println("Sorry you do not have enough gold to buy this spell.");
	                		out.println();
	                	// Check Spell Carry Count. Max 3 Spells.
	                	} else if (spells.size() >= 3) {
	                        out.println("You have reached the maximum limit for spells. Cannot add more spells.");
	                        // Loop to give option to Drop Spell and enforce choice (y/n).
	                        while (true) {
	                            out.println("Would you like to drop a spell? (y/n)");
	                            String dropItem = input.nextLine();
	                            if (dropItem.equalsIgnoreCase("y")) {
	                                // Display the list of spells with numbers.
	                                int itemNumber = 1;
	                                for (String spell : spells) {
	                                    out.printf("%d. %s%n", itemNumber++, spell);
	                                    out.println();
	                                }
	
	                                boolean spellRemoved = false; // Flag to track if a spell has been removed
	
	                                do {
	                                    try {
	                                        // Prompt the user to select a number.
	                                        out.print("Enter the number of the spell to drop or 0 to cancel: ");
	                                        out.println();
	                                        int choice = input.nextInt();
	                                        
	                                        input.nextLine();	                                     
		                                    if (choice == 0) {
		                                    	break;
		                                    }
	                                        // Check if the choice is valid.
		                                    else if (choice >= 1 && choice <= spells.size()) {
	                                            // Remove the selected spell from the list using getItemByIndex method.
	                                            String removedItem = getItemByIndex(spells, choice - 1);
	                                            spells.remove(removedItem);
	                                            out.printf("You have dropped: %s%n", removedItem);
	                                            out.println();
	                                            spellRemoved = true; // Set the flag to true
	                                            break; // Exit the loop if a spell has been removed.
	                                        } else {
	                                            out.println("Invalid choice.");
	                                        }
	                                    } catch (InputMismatchException e) {
	                                        out.println("Invalid input. Please enter a valid number.");
	                                        input.nextLine(); // Consume the invalid input
	                                    }
	                                } while (true); // This loop will continue until a valid choice is made
	
	                                // Check if a spell was removed and break the outer loop
	                                if (spellRemoved) {
	                                	out.println("----------------------");
	                                	out.printf("Spell: %s", drawnCard.getItem() + " Price: " + drawnCard.getPrice());
	                                	out.println();
	                                	out.println();
	                                    break;
	                                }
	                            } else if (dropItem.equalsIgnoreCase("n")) {
	                                break; // Exit the outer loop if the player chooses not to drop a spell
	                            } else {
	                                out.println("Invalid choice. Please enter 'y' or 'n'.");
	                            }
	                        }
	                    }
	                	// Choice to buy Card Spell.
	                	while (spells.size() < 3 && chosenHero.getGold() >= drawnCard.getPrice()) {
	                	    out.print("Would you like to buy this Spell? (y/n)");
	                	    out.println();
	                	    String purchase = input.nextLine();
	                	    
	                	    if (purchase.equalsIgnoreCase("y")) {
	                	        String itemToAdd = drawnCard.getItem();
	                	            out.println();
	                	            spells.add(itemToAdd);
	                	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	                	            chosenHero.setGold(newGold);
	                	            totalGold = chosenHero.getGold();
	                	            displayPlayerCard (playerName, chosenHero, items, spells);
	                	        // If 'y' is entered, exit the loop.
	                	        break;
	                	    } else if (purchase.equalsIgnoreCase("n")) {
	                	        // If 'n' is entered, exit the loop without buying the Item.
	                	        break;
	                	    } else {
	                	        // Invalid input, prompt the user to enter 'y' or 'n' again.
	                	        out.println("Invalid input. Please enter 'y' or 'n'.");
	                	    }
	                	}                	
	                }            
	            
	            return drawnCard;
	    	} else {
	    		// For regular characters Max Spell count 2.
	    		// Check if the drawn card is of type "Strength Spell" or "Skill Spell" or "Life Spell".  
	            if(drawnCard.getCardtype().equalsIgnoreCase("Strength Spell") || drawnCard.getCardtype().equalsIgnoreCase("Skill SPell") || drawnCard.getCardtype().equalsIgnoreCase("Life Spell")) {
	                	// Check if Chosen Hero has enough gold.
	                	if(chosenHero.getGold() < drawnCard.getPrice()) {
	                		out.println("Sorry you do not have enough gold to buy this spell.");
	                		out.println();
	                	// Check Spell Carry Count. Max 2 Spells.
	                	} else if (spells.size() >= 2) {
	                        out.println("You have reached the maximum limit for spells. Cannot add more spells.");
	                     // Loop to give option to Drop Spell and enforce choice (y/n).
	                        while (true) {
	                            out.println("Would you like to drop a spell? (y/n)");
	                            String dropItem = input.nextLine();
	                            if (dropItem.equalsIgnoreCase("y")) {
	                                // Display the list of spells with numbers.
	                                int itemNumber = 1;
	                                for (String spell : spells) {
	                                    out.printf("%d. %s%n", itemNumber++, spell);
	                                    out.println();
	                                }
	
	                                boolean spellRemoved = false; // Flag to track if a spell has been removed
	
	                                do {
	                                    try {
	                                        // Prompt the user to select a number.
	                                        out.print("Enter the number of the spell to drop or 0 to cancel: ");
	                                        out.println();
	                                        int choice = input.nextInt();
	                                        
	                                        input.nextLine();	                                     
		                                    if (choice == 0) {
		                                    	break;
		                                    }
	                                        // Check if the choice is valid.
		                                    else if (choice >= 1 && choice <= spells.size()) {
	                                            // Remove the selected spell from the list using getItemByIndex method.
	                                            String removedItem = getItemByIndex(spells, choice - 1);
	                                            spells.remove(removedItem);
	                                            out.printf("You have dropped: %s%n", removedItem);
	                                            out.println();
	                                            spellRemoved = true; // Set the flag to true
	                                            break; // Exit the loop if a spell has been removed.
	                                        } else {
	                                            out.println("Invalid choice.");
	                                        }
	                                    } catch (InputMismatchException e) {
	                                        out.println("Invalid input. Please enter a valid number.");
	                                        input.nextLine(); // Consume the invalid input
	                                    }
	                                } while (true); // This loop will continue until a valid choice is made
	
	                                // Check if a spell was removed and break the outer loop
	                                if (spellRemoved) {
	                                	out.println("----------------------");
	                                	out.printf("Spell: %s", drawnCard.getItem() + " Price: " + drawnCard.getPrice());
	                                	out.println();
	                                	out.println();
	                                    break;
	                                }
	                            } else if (dropItem.equalsIgnoreCase("n")) {
	                                break; // Exit the outer loop if the player chooses not to drop a spell
	                            } else {
	                                out.println("Invalid choice. Please enter 'y' or 'n'.");
	                            }
	                        }
	                    }
	                	// Choice to buy Card Spell.
	                	while (spells.size() < 2 && chosenHero.getGold() >= drawnCard.getPrice()) {
	                	    out.print("Would you like to buy this Spell? (y/n)");
	                	    out.println();
	                	    String purchase = input.nextLine();
	                	    
	                	    if (purchase.equalsIgnoreCase("y")) {
	                	        String itemToAdd = drawnCard.getItem();
	                	            out.println();
	                	            spells.add(itemToAdd);
	                	            int newGold = chosenHero.getGold() - drawnCard.getPrice();
	                	            chosenHero.setGold(newGold);
	                	            totalGold = chosenHero.getGold();
	                	            displayPlayerCard (playerName, chosenHero, items, spells);
	                	        // If 'y' is entered, exit the loop.
	                	        break;
	                	    } else if (purchase.equalsIgnoreCase("n")) {
	                	        // If 'n' is entered, exit the loop without buying the Item.
	                	        break;
	                	    } else {
	                	        // Invalid input, prompt the user to enter 'y' or 'n' again.
	                	        out.println("Invalid input. Please enter 'y' or 'n'.");
	                	    }
	                	}	                	
	                }            
	            
	            return drawnCard;
	    	}	    	
    	}
    	
// Display Card
    	// Method to determine and display the card action type.
    	public static void displayCard(Card randomCard) {
    		String cardType = randomCard.getCardtype();
    		
    		out.println("*******************************");
    		out.printf("You drew a %s card:\n", randomCard.getCardtype());
    		out.println("-------------------------------");
// Fight card   		
            if(cardType.equalsIgnoreCase("Fight")) {            	
            	out.println("Villain: " + randomCard.getVillain());
            	out.println("Strength: " + randomCard.getStrength());
            	out.println("Skill: " + randomCard.getSkill());
            	out.println("Life: " + randomCard.getLife());
            	out.println("Gold: " + randomCard.getGold());           	
            }
// Strength Fight           
            else if(cardType.equalsIgnoreCase("Strength Fight") || cardType.equalsIgnoreCase("Strength Boss")) {
            	out.println("Villain: " + randomCard.getVillain());
            	out.println("Strength: " + randomCard.getStrength());
            	out.println("Life: " + randomCard.getLife());
            	out.println("Gold: " + randomCard.getGold());
            }
// Skill Fight           
            else if(cardType.equalsIgnoreCase("Skill Fight") || cardType.equalsIgnoreCase("Skill Boss")) {
            	out.println("Villain: " + randomCard.getVillain());
            	out.println("Skill: " + randomCard.getSkill());
            	out.println("Life: " + randomCard.getLife());
            	out.println("Gold: " + randomCard.getGold());
            }           
// Treasure      
            else if(cardType.equalsIgnoreCase("Treasure")) {	
            	out.println("Gold: " + randomCard.getGold());
            }
// Strength/Skill/Bag Item            
            else if(cardType.equalsIgnoreCase("Strength Item") || cardType.equalsIgnoreCase("Skill Item") || randomCard.getCardtype().equalsIgnoreCase("Bag Item")) {	
                out.println("Item: " + randomCard.getItem());
                out.println("Price: " + randomCard.getPrice());
            }     
// Lose Bag            
            else if(cardType.equalsIgnoreCase("Lose Bag")) {
            	out.println("You have lost your bag.");
            }             
// Lose Skill          
            else if(cardType.equalsIgnoreCase("Lose Skill")) {	
            	out.println("Lose Skill: " + randomCard.getBonus());
            } 
// Gain Strength          
            else if(cardType.equalsIgnoreCase("Gain Strength")) {	
            	out.println("Gain Strength: " + randomCard.getBonus());
            } 
// Gain Skill          
            else if(cardType.equalsIgnoreCase("Gain Skill")) {	
            	out.println("Gain Skill: " + randomCard.getBonus());
            } 
// Lose Strength          
            else if(cardType.equalsIgnoreCase("Lose Strength")) {	
            	out.println("Lose Strength: " + randomCard.getBonus());
            }
// Life Item
            else if(cardType.equalsIgnoreCase("Life Item")) {	
                out.println("Item: " + randomCard.getItem() + " " + randomCard.getLife());
            }
// Lose Item            
            else if(cardType.equalsIgnoreCase("Lose Item")) {	
            	out.println("You must lose an item.");
            }
// Lose Spell           
            else if(cardType.equalsIgnoreCase("Lose Spell")) {	
            	out.println("You must lose a Spell.");
            }    
// Lose Skill          
            else if(cardType.equalsIgnoreCase("Lose Skill")) {	
            	out.println("Skill: " + randomCard.getBonus());
            } 
// Lose Strength          
            else if(cardType.equalsIgnoreCase("Lose Strength")) {	
            	out.println("Strength: " + randomCard.getBonus());
            }             
// Lose All Items            
            else if(cardType.equalsIgnoreCase("Lose All Items")) {	
            	out.println("You lose all items.");
            }
// Lose All Spells           
            else if(cardType.equalsIgnoreCase("Lose All Spells")) {	
            	out.println("You lose all Spells.");
            }            
// Spell Items            
            else if(cardType.equalsIgnoreCase("Strength Spell") || cardType.equals("Skill Spell") || randomCard.getCardtype().equals("Life Spell")) {	
            	out.println("Spell: " + randomCard.getItem());
            	out.println("Price: " + randomCard.getPrice());
            }
            out.println("-------------------------------");
        	out.println();
    	}
    	
// Play Card
    	// Check level and play correct card for that level.
    	public static Card playLevelCard(int turnCount, String playerName, Hero chosenHero, List<String> items, List<String> spells, Card drawnCard) {
// Level 1    		
    		if(turnCount < 30) {
        		displayLevel(turnCount);
        		out.printf("Turn: %d", turnCount);
        		out.println();
        		out.println();
        		displayPlayerCard (playerName, chosenHero, items, spells);
        		drawnCard = getCard();            
        	}
// Level 2	        	
            else if(turnCount >= 30 && turnCount <= 49) {
        		displayLevel(turnCount);
        		out.printf("Turn: %d", turnCount);
        		out.println();
        		out.println();
        		displayPlayerCard (playerName, chosenHero, items, spells);
    			drawnCard = getCard2();
            }
// Level 3	            
            else if(turnCount >= 50 && turnCount <= 59) {
        		displayLevel(turnCount);
        		out.printf("Turn: %d", turnCount);
        		out.println();
        		out.println();
        		displayPlayerCard (playerName, chosenHero, items, spells);
        		drawnCard = getCard3();
            }
// Level 4	        	
        	// Final turn count needs to be amended to match "Final Fight" in main for winners message.
            else if(turnCount == 60) {
            	displayLevel(turnCount);
        		out.printf("Turn: %d", turnCount);
        		out.println();
        		out.println();
        		displayPlayerCard (playerName, chosenHero, items, spells);
            	drawnCard = getCard4(chosenHero);
            }
    		return drawnCard;
    	}   		
    	
    	
}

