package heroesGold;

public class Card {
	
	private String cardtype;
	private String villain;
	private int strength;
	private int skill;
	private int life;
	private int gold;
	private String item;
	private int price;
	private int bonus;
	
	public Card(String cardtype, String villain, int strength, int skill, int life, int gold, String item, int price, int bonus) {
		this.setCardtype(cardtype);
		this.setVillain(villain);
		this.setStrength(strength);
		this.setSkill(skill);
		this.setLife(life);
		this.setGold(gold);
		this.setItem(item);
		this.setPrice(price);
		this.setBonus(bonus);
	}
	
	public Card() {
		cardtype = null;
		villain = null;
		strength = 0;
		skill = 0;
		life = 0;
		gold = 0;
		item = null;
		price = 0;
		bonus = 0;
	}
	
	
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getVillain() {
		return villain;
	}
	public void setVillain(String villain) {
		this.villain = villain;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

}
