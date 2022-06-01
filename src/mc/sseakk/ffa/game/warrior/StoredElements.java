package mc.sseakk.ffa.game;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class StoredElements {
	
	private ItemStack[] storedInventory, storedArmor;
	private GameMode gamemode;
	private float storedExp;
	private int storedLevel, storedHunger;
	private double storedHealth, storedMaxHealth;
	
	public StoredElements(ItemStack[] storedInventory, ItemStack[] storedArmor, GameMode gamemode, float storedExp,
			int storedLevel, int storedHunger, double storedHealth, double storedMaxHealth) {
		this.storedInventory = storedInventory;
		this.storedArmor = storedArmor;
		this.gamemode = gamemode;
		this.storedExp = storedExp;
		this.storedLevel = storedLevel;
		this.storedHunger = storedHunger;
		this.storedHealth = storedHealth;
		this.storedMaxHealth = storedMaxHealth;
	}

	public ItemStack[] getStoredInventory() {
		return storedInventory;
	}

	public void setStoredInventory(ItemStack[] storedInventory) {
		this.storedInventory = storedInventory;
	}

	public ItemStack[] getStoredArmor() {
		return storedArmor;
	}

	public void setStoredArmort(ItemStack[] storedEquipment) {
		this.storedArmor = storedEquipment;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public float getStoredExp() {
		return storedExp;
	}

	public void setStoredExp(float storedExp) {
		this.storedExp = storedExp;
	}

	public int getStoredLevel() {
		return storedLevel;
	}

	public void setStoredLevel(int storedLevel) {
		this.storedLevel = storedLevel;
	}

	public int getStoredHunger() {
		return storedHunger;
	}

	public void setStoredHunger(int storedHunger) {
		this.storedHunger = storedHunger;
	}

	public double getStoredHealth() {
		return storedHealth;
	}

	public void setStoredHealth(double storedHealth) {
		this.storedHealth = storedHealth;
	}

	public double getStoredMaxHealth() {
		return storedMaxHealth;
	}

	public void setStoredMaxHealth(double storedMaxHealth) {
		this.storedMaxHealth = storedMaxHealth;
	}
}