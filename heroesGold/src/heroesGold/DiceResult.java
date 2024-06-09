package heroesGold;

import java.util.Random;
import java.util.*;
import static java.lang.System.*;

public class DiceResult {
    private int heroDice1;
    private int heroDice2;
    private int villainDice1;
    private int villainDice2;
    private Random random = new Random();
    Scanner input = new Scanner(in);

    public DiceResult(int heroDice1, int heroDice2, int villainDice1, int villainDice2) {
        // Initialize with the provided values
        this.heroDice1 = heroDice1;
        this.heroDice2 = heroDice2;
        this.villainDice1 = villainDice1;
        this.villainDice2 = villainDice2;
    }

    public DiceResult fight() {
        this.heroDice1 = random.nextInt(6) + 1; // Random number between 1 and 6
        this.heroDice2 = random.nextInt(6) + 1; // Random number between 1 and 6
        this.villainDice1 = random.nextInt(6) + 1; // Random number between 1 and 6
        this.villainDice2 = random.nextInt(6) + 1; // Random number between 1 and 6
        return this;
    }

    public int getHeroDice1() {
        return heroDice1;
    }

    public int getHeroDice2() {
        return heroDice2;
    }

    public int getVillainDice1() {
        return villainDice1;
    }

    public int getVillainDice2() {
        return villainDice2;
    }
}
