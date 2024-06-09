package heroesGold;

import java.util.*;

public class Hero {
	
	private String name;
	private int strength;
	private int skill;
	private String items;
	private int gold;
	private int life;
	private boolean magicalCharacter;
	private String ability;
	private boolean hasBag;
	
	private Random random = new Random();
	
	public Hero(String name, int strength, int skill, String items, int gold, int life, boolean magicalCharacter, String ability, boolean hasBag) {
		this.setName(name);
		this.setStrength(strength);
		this.setSkill(skill);
		this.setItems(items);
		this.setGold(gold);
		this.setLife(life);
		this.setMagicalCharacter(magicalCharacter);
		this.setAbility(ability);
		this.setHasBag(hasBag);	
	}
	
	public Hero() {
		name = null;
		strength = 0;
		skill = 0;
		items = null;
		gold = 0;
		life = 0;
		magicalCharacter = false;
		ability = null;
		hasBag = false;
	}
	
	public int fight(int value) {
		int diceRoll = random.nextInt(11) + 2;
		int totalAttack = diceRoll + value;
		return totalAttack;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public boolean isMagicalCharacter() {
		return magicalCharacter;
	}

	public void setMagicalCharacter(boolean magicalCharacter) {
		this.magicalCharacter = magicalCharacter;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public boolean isHasBag() {
		return hasBag;
	}

	public void setHasBag(boolean hasBag) {
		this.hasBag = hasBag;
	}
	
	
}
